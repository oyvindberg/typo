package typo

import typo.db.Type

case class TableComputed(options: Options, default: DefaultComputed, dbTable: db.Table) {
  val allKeyNames: Set[db.ColName] =
    (dbTable.primaryKey.toList.flatMap(_.colNames) ++ dbTable.uniqueKeys.flatMap(_.cols) ++ dbTable.foreignKeys.flatMap(_.cols)).toSet
  val pointsTo: Map[db.ColName, (db.RelationName, db.ColName)] =
    dbTable.foreignKeys.flatMap(fk => fk.cols.zip(fk.otherCols.map(cn => (fk.otherTable, cn)))).toMap
  val dbColsByName: Map[db.ColName, db.Col] =
    dbTable.cols.map(col => (col.name, col)).toMap

  def scalaType(pkg: sc.QIdent, col: db.Col): sc.Type = {
    def go(tpe: db.Type): sc.Type = tpe match {
      case Type.BigInt           => sc.Type.Long
      case Type.Text             => sc.Type.String
      case Type.AnyArray         => sc.Type.String // wip
      case Type.Boolean          => sc.Type.Boolean
      case Type.Char             => sc.Type.String
      case Type.Name             => sc.Type.String
      case Type.StringEnum(name) => sc.Type.Qualified(names.EnumName(pkg, name))
      case Type.Hstore           => sc.Type.JavaMap.of(sc.Type.String, sc.Type.String)
      case Type.Inet             => sc.Type.String // wip
      case Type.Oid              => sc.Type.Long // wip
      case Type.VarChar(_)       => sc.Type.String
      case Type.Float4           => sc.Type.Float
      case Type.Float8           => sc.Type.Double
      case Type.Int2             => sc.Type.Short
      case Type.Int4             => sc.Type.Int
      case Type.Int8             => sc.Type.Long
      case Type.Json             => sc.Type.String // wip
      case Type.Numeric          => sc.Type.BigDecimal
      case Type.Timestamp        => sc.Type.LocalDateTime
      case Type.TimestampTz      => sc.Type.ZonedDateTime
      case Type.Array(tpe)       => sc.Type.Array.of(go(tpe))
      case Type.Vector(tpe)      => sc.Type.Array.of(go(tpe))
    }
    val baseTpe = go(col.tpe)

    if (col.isNotNull) baseTpe else sc.Type.Option.of(baseTpe)
  }

  val maybeId: Option[IdComputed] =
    dbTable.primaryKey.flatMap { pk =>
      val qident = names.titleCase(options.pkg, dbTable.name, "Id")
      pk.colNames match {
        case Nil => None
        case colName :: Nil =>
          val dbCol = dbColsByName(colName)
          val col = ColumnComputed(pointsTo.get(dbCol.name), names.field(dbCol.name), scalaType(options.pkg, dbCol), dbCol.name, dbCol.hasDefault)
          Some(IdComputed.Unary(col, qident))

        case colNames =>
          val cols: List[ColumnComputed] = colNames.map { colName =>
            val fieldName = names.field(colName)
            val dbCol = dbColsByName(colName)
            val underlying = scalaType(options.pkg, dbCol)
            ColumnComputed(None, fieldName, underlying, dbCol.name, dbCol.hasDefault)
          }
          val paramName = sc.Ident("compositeId")
          Some(IdComputed.Composite(cols, qident, paramName))
      }
    }

  val dbColsAndCols: List[(db.Col, ColumnComputed)] = {
    dbTable.cols.map {
      case dbCol @ db.Col(colName, _, isNotNull, _) if dbTable.primaryKey.exists(_.colNames == List(colName)) =>
        if (!isNotNull) {
          sys.error(s"assumption: id column in ${dbTable.name} should be not null")
        }
        dbCol -> ColumnComputed(pointsTo.get(colName), names.field(colName), maybeId.get.tpe, dbCol.name, dbCol.hasDefault)
      case dbCol =>
        val finalType: sc.Type = scalaType(options.pkg, dbCol)

        dbCol -> ColumnComputed(pointsTo.get(dbCol.name), names.field(dbCol.name), finalType, dbCol.name, dbCol.hasDefault)
    }
  }

  val cols: List[ColumnComputed] = dbColsAndCols.map { case (_, col) => col }

  val relation = RelationComputed(options.pkg, dbTable.name, cols, maybeId)

  val colsUnsaved: Option[List[ColumnComputed]] = maybeId.map { _ =>
    dbColsAndCols
      .filterNot { case (_, col) => dbTable.primaryKey.exists(_.colNames.contains(col.dbName)) }
      .map { case (dbCol, col) =>
        val newType = if (dbCol.hasDefault) sc.Type.TApply(default.DefaultedType, List(col.tpe)) else col.tpe
        col.copy(tpe = newType)
      }
  }.filter(_.nonEmpty)

  val RowUnsavedName: Option[sc.QIdent] =
    if (colsUnsaved.nonEmpty) Some(names.titleCase(options.pkg, dbTable.name, "RowUnsaved")) else None

  val repoMethods: Option[List[RepoMethod]] = {
    val RowType = sc.Type.Qualified(relation.RowName)

    val fieldValuesParam = sc.Param(
      sc.Ident("fieldValues"),
      sc.Type.List.of(sc.Type.Qualified(relation.FieldValueName).of(sc.Type.Wildcard))
    )

    val foo = List(
      maybeId match {
        case Some(id) =>
          val updateMethod = RowUnsavedName.zip(colsUnsaved).map { case (unsaved, colsUnsaved) =>
            val unsavedParam = sc.Param(sc.Ident("unsaved"), sc.Type.Qualified(unsaved))

            if (id.cols.forall(_.hasDefault))
              RepoMethod.InsertDbGeneratedKey(id, colsUnsaved, unsavedParam, default)
            else
              RepoMethod.InsertProvidedKey(id, colsUnsaved, unsavedParam, default)
          }

          List(
            Some(RepoMethod.SelectAll(RowType)),
            Some(RepoMethod.SelectById(id, RowType)),
            id match {
              case unary: IdComputed.Unary =>
                Some(RepoMethod.SelectAllByIds(unary, sc.Param(id.paramName.appended("s"), sc.Type.List.of(id.tpe)), RowType))
              case IdComputed.Composite(_, _, _) =>
                None
            },
            Some(RepoMethod.SelectByFieldValues(fieldValuesParam, RowType)),
            Some(RepoMethod.UpdateFieldValues(id, fieldValuesParam)),
            updateMethod,
            Some(RepoMethod.Delete(id))
          ).flatten
        case None =>
          List(
            RepoMethod.SelectAll(RowType),
            RepoMethod.SelectByFieldValues(fieldValuesParam, RowType)
          )
      },
      dbTable.uniqueKeys.map { uk =>
        val params = uk.cols.map(colName => sc.Param(names.field(colName), scalaType(options.pkg, dbColsByName(colName))))
        RepoMethod.SelectByUnique(params, RowType)
      }
    )
    Some(foo.flatten).filter(_.nonEmpty)
  }
}

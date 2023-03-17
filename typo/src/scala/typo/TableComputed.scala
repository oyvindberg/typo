package typo

case class TableComputed(pkg: sc.QIdent, default: DefaultComputed, dbTable: db.Table) {
  val allKeyNames: Set[db.ColName] =
    (dbTable.primaryKey.toList.flatMap(_.colNames) ++ dbTable.uniqueKeys.flatMap(_.cols) ++ dbTable.foreignKeys.map(_.col)).toSet

  val dbColsByName: Map[db.ColName, db.Col] =
    dbTable.cols.map(col => (col.name, col)).toMap

  def scalaType(pkg: sc.QIdent, col: db.Col): sc.Type = {
    val baseTpe = col.tpe match {
      case db.Type.BigInt           => sc.Type.Long
      case db.Type.VarChar(_)       => sc.Type.String
      case db.Type.Boolean          => sc.Type.Boolean
      case db.Type.Text             => sc.Type.String
      case db.Type.StringEnum(name) => sc.Type.Qualified(names.EnumName(pkg, name))
    }
    if (col.isNotNull) baseTpe else sc.Type.Option.of(baseTpe)
  }

  val maybeId: Option[IdComputed] =
    dbTable.primaryKey.flatMap { pk =>
      val qident = names.titleCase(pkg, dbTable.name, "Id")
      pk.colNames match {
        case Nil => None
        case colName :: Nil =>
          val dbCol = dbColsByName(colName)
          val col = ColumnComputed(names.field(dbCol.name), scalaType(pkg, dbCol), dbCol.name, dbCol.hasDefault)
          Some(IdComputed.Unary(col, qident))

        case colNames =>
          val cols: List[ColumnComputed] = colNames.map { colName =>
            val fieldName = names.field(colName)
            val dbCol = dbColsByName(colName)
            val underlying = scalaType(pkg, dbCol)
            ColumnComputed(fieldName, underlying, dbCol.name, dbCol.hasDefault)
          }
          val paramName = names.field(db.ColName(colNames.map(_.value).mkString("_and_")))
          Some(IdComputed.Composite(cols, qident, paramName))
      }
    }

  val dbColsAndCols: List[(db.Col, ColumnComputed)] = {
    dbTable.cols.map {
      case dbCol @ db.Col(colName, _, isNotNull, _) if dbTable.primaryKey.exists(_.colNames == List(colName)) =>
        if (!isNotNull) {
          sys.error(s"assumption: id column in ${dbTable.name} should be not null")
        }
        dbCol -> ColumnComputed(names.field(colName), maybeId.get.tpe, dbCol.name, dbCol.hasDefault)
      case dbCol =>
        val finalType: sc.Type = scalaType(pkg, dbCol)
        dbCol -> ColumnComputed(names.field(dbCol.name), finalType, dbCol.name, dbCol.hasDefault)
    }
  }

  val cols: List[ColumnComputed] = dbColsAndCols.map { case (_, col) => col }

  val relation = RelationComputed(pkg, dbTable.name, cols, maybeId)

  val colsUnsaved: List[ColumnComputed] =
    dbColsAndCols
      .filterNot { case (_, col) => dbTable.primaryKey.exists(_.colNames.contains(col.dbName)) }
      .map { case (dbCol, col) =>
        val newType = if (dbCol.hasDefault) sc.Type.TApply(default.DefaultedType, List(col.tpe)) else col.tpe
        col.copy(tpe = newType)
      }

  val RowUnsavedName: Option[sc.QIdent] =
    if (colsUnsaved.nonEmpty) Some(names.titleCase(pkg, dbTable.name, "RowUnsaved")) else None

  val repoMethods: Option[List[RepoMethod]] = {
    val RowType = sc.Type.Qualified(relation.RowName)

    val fieldValuesParam = sc.Param(
      sc.Ident("fieldValues"),
      sc.Type.List.of(sc.Type.Qualified(relation.FieldValueName).of(sc.Type.Wildcard))
    )

    val foo = List(
      maybeId match {
        case Some(id) =>
          val updateMethod = RowUnsavedName.map { unsaved =>
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
        val params = uk.cols.map(colName => sc.Param(names.field(colName), scalaType(pkg, dbColsByName(colName))))
        RepoMethod.SelectByUnique(params, RowType)
      }
    )
    Some(foo.flatten).filter(_.nonEmpty)
  }
}

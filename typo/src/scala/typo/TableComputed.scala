package typo

import typo.doobie.Nullability

case class TableComputed(options: Options, default: DefaultComputed, dbTable: db.Table) {
  val allKeyNames: Set[db.ColName] =
    (dbTable.primaryKey.toList.flatMap(_.colNames) ++ dbTable.uniqueKeys.flatMap(_.cols) ++ dbTable.foreignKeys.flatMap(_.cols)).toSet
  val pointsTo: Map[db.ColName, (db.RelationName, db.ColName)] =
    dbTable.foreignKeys.flatMap(fk => fk.cols.zip(fk.otherCols.map(cn => (fk.otherTable, cn)))).toMap
  val dbColsByName: Map[db.ColName, db.Col] =
    dbTable.cols.map(col => (col.name, col)).toMap

  val maybeId: Option[IdComputed] =
    dbTable.primaryKey.flatMap { pk =>
      val qident = names.titleCase(options.pkg, dbTable.name, "Id")
      pk.colNames match {
        case Nil => None
        case colName :: Nil =>
          val dbCol = dbColsByName(colName)
          val col = ColumnComputed(
            pointsTo.get(dbCol.name),
            names.field(dbCol.name),
            typeMapper.scalaType(options.pkg, dbCol),
            dbCol.name,
            dbCol.hasDefault,
            dbCol.jsonDescription
          )
          Some(IdComputed.Unary(col, qident))

        case colNames =>
          val cols: List[ColumnComputed] = colNames.map { colName =>
            val fieldName = names.field(colName)
            val dbCol = dbColsByName(colName)
            val underlying = typeMapper.scalaType(options.pkg, dbCol)
            ColumnComputed(None, fieldName, underlying, dbCol.name, dbCol.hasDefault, dbCol.jsonDescription)
          }
          val paramName = sc.Ident("compositeId")
          Some(IdComputed.Composite(cols, qident, paramName))
      }
    }

  val dbColsAndCols: List[(db.Col, ColumnComputed)] = {
    dbTable.cols.map {
      case dbCol @ db.Col(colName, _, nullability, _, _) if dbTable.primaryKey.exists(_.colNames == List(colName)) =>
        nullability match {
          case Nullability.NoNulls =>
            ()
          case other =>
            sys.error(s"assumption: id column in ${dbTable.name} should be `NoNulls`, got $other")
        }
        dbCol -> ColumnComputed(pointsTo.get(colName), names.field(colName), maybeId.get.tpe, dbCol.name, dbCol.hasDefault, dbCol.jsonDescription)
      case dbCol =>
        val finalType: sc.Type = typeMapper.scalaType(options.pkg, dbCol)

        dbCol -> ColumnComputed(pointsTo.get(dbCol.name), names.field(dbCol.name), finalType, dbCol.name, dbCol.hasDefault, dbCol.jsonDescription)
    }
  }

  val cols: List[ColumnComputed] = dbColsAndCols.map { case (_, col) => col }

  val relation = RelationComputed(options.pkg, dbTable.name, cols, maybeId)

  val colsUnsaved: Option[List[ColumnComputed]] = maybeId
    .map { _ =>
      dbColsAndCols
        .filterNot { case (_, col) => dbTable.primaryKey.exists(_.colNames.contains(col.dbName)) }
        .map { case (dbCol, col) =>
          val newType = if (dbCol.hasDefault) sc.Type.TApply(default.DefaultedType, List(col.tpe)) else col.tpe
          col.copy(tpe = newType)
        }
    }
    .filter(_.nonEmpty)

  val RowUnsavedName: Option[sc.QIdent] =
    if (colsUnsaved.nonEmpty) Some(names.titleCase(options.pkg, dbTable.name, "RowUnsaved")) else None

  val repoMethods: Option[List[RepoMethod]] = {
    val RowType = sc.Type.Qualified(relation.RowName)

    val fieldValuesParam = sc.Param(
      sc.Ident("fieldValues"),
      sc.Type.List.of(sc.Type.Qualified(relation.FieldValueName).of(sc.Type.Wildcard))
    )

    val maybeMethods = List(
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
        val params = uk.cols.map(colName => cols.find(_.dbName == colName).get)
        RepoMethod.SelectByUnique(params, RowType)
      }
    )
    Some(maybeMethods.flatten).filter(_.nonEmpty)
  }
}

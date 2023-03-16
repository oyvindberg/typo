package typo

case class TableComputed(pkg: sc.QIdent, default: DefaultComputed, table: db.Table) {
  val allKeyNames: Set[db.ColName] =
    (table.primaryKey.toList.flatMap(_.colNames) ++ table.uniqueKeys.flatMap(_.cols) ++ table.foreignKeys.map(_.col)).toSet

  val colsByName: Map[db.ColName, db.Col] =
    table.cols.map(col => (col.name, col)).toMap

  def scalaType(pkg: sc.QIdent, col: db.Col): sc.Type = {
    val baseTpe = col.tpe match {
      case db.Type.BigInt           => sc.Type.Long
      case db.Type.VarChar(_)       => sc.Type.String
      case db.Type.Boolean          => sc.Type.Boolean
      case db.Type.Text             => sc.Type.String
      case db.Type.StringEnum(name) => sc.Type.Qualified(names.EnumName(pkg, name))
    }
    if (col.isNotNull) baseTpe else sc.Type.Option(baseTpe)
  }

  val maybeId: Option[IdComputed] =
    table.primaryKey.flatMap { pk =>
      val qident = names.titleCase(pkg, table.name, "Id")
      pk.colNames match {
        case Nil => None
        case colName :: Nil =>
          val dbCol = colsByName(colName)
          val col = ColumnComputed(names.field(dbCol.name), scalaType(pkg, dbCol), dbCol)
          Some(IdComputed.Unary(col, qident))

        case colNames =>
          val cols: List[ColumnComputed] = colNames.map { colName =>
            val fieldName = names.field(colName)
            val col = colsByName(colName)
            val underlying = scalaType(pkg, col)
            ColumnComputed(fieldName, underlying, col)
          }
          val paramName = names.field(db.ColName(colNames.map(_.value).mkString("_and_")))
          Some(IdComputed.Composite(cols, qident, paramName))
      }
    }

  val cols: Seq[ColumnComputed] = {
    table.cols.map {
      case col @ db.Col(colName, _, isNotNull, _) if table.primaryKey.exists(_.colNames == List(colName)) =>
        if (!isNotNull) {
          sys.error(s"assumption: id column in ${table.name} should be not null")
        }
        ColumnComputed(names.field(colName), maybeId.get.tpe, col)
      case col =>
        val finalType: sc.Type = scalaType(pkg, col)
        ColumnComputed(names.field(col.name), finalType, col)
    }
  }

  val colsUnsaved: Seq[ColumnComputed] =
    cols
      .filterNot { case ColumnComputed(_, _, col) => table.primaryKey.exists(_.colNames.contains(col.name)) }
      .map { case ColumnComputed(name, tpe, col) =>
        val newType = if (col.hasDefault) sc.Type.TApply(default.DefaultedType, List(tpe)) else tpe
        ColumnComputed(name, newType, col)
      }

  val RepoName: sc.QIdent = names.titleCase(pkg, table.name, "Repo")
  val RepoImplName: sc.QIdent = names.titleCase(pkg, table.name, "RepoImpl")
  val RowName: sc.QIdent = names.titleCase(pkg, table.name, "Row")
  val RowUnsavedName: Option[sc.QIdent] = if (colsUnsaved.nonEmpty) Some(names.titleCase(pkg, table.name, "RowUnsaved")) else None
  val FieldValueName: sc.QIdent = names.titleCase(pkg, table.name, "FieldValue")

  val repoMethods: Option[List[RepoMethod]] = {
    val RowType = sc.Type.Qualified(RowName)

    val foo = List(
      maybeId match {
        case Some(id) =>
          val idParam = sc.Param(id.paramName, id.tpe)

          val updateMethod = RowUnsavedName.map { unsaved =>
            val unsavedParam = sc.Param(sc.Ident("unsaved"), sc.Type.Qualified(unsaved))

            if (id.cols.forall(_.dbCol.hasDefault))
              RepoMethod.InsertDbGeneratedKey(unsavedParam, id.tpe)
            else
              RepoMethod.InsertProvidedKey(idParam, unsavedParam)
          }

          val fieldValuesParam = sc.Param(
            sc.Ident("fieldValues"),
            sc.Type.List(sc.Type.TApply(sc.Type.Qualified(FieldValueName), List(sc.Type.Wildcard)))
          )

          List(
            Some(RepoMethod.SelectAll(RowType)),
            Some(RepoMethod.SelectById(idParam, RowType)),
            id match {
              case unary: IdComputed.Unary =>
                Some(RepoMethod.SelectAllByIds(unary, sc.Param(id.paramName.appended("s"), sc.Type.List(id.tpe)), RowType))
              case IdComputed.Composite(_, _, _) =>
                None
            },
            Some(RepoMethod.SelectByFieldValues(fieldValuesParam, RowType)),
            Some(RepoMethod.UpdateFieldValues(idParam, fieldValuesParam)),
            updateMethod,
            Some(RepoMethod.Delete(idParam))
          ).flatten
        case None => Nil
      },
      table.uniqueKeys.map { uk =>
        val params = uk.cols.map(colName => sc.Param(names.field(colName), scalaType(pkg, colsByName(colName))))
        RepoMethod.SelectByUnique(params, RowType)
      }
    )
    Some(foo.flatten).filter(_.nonEmpty)
  }
}

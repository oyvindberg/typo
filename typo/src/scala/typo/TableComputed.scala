package typo

case class TableComputed(pkg: sc.QIdent, table: db.Table) {
  val allKeyNames: Set[db.ColName] =
    (table.primaryKey.map(_.colName) ++ table.uniqueKeys.flatMap(_.cols) ++ table.foreignKeys.map(_.col)).toSet

  val colsByName: Map[db.ColName, db.Col] =
    table.cols.map(col => (col.name, col)).toMap

  def scalaType(pkg: sc.QIdent, col: db.Col): sc.Type = {
    val baseTpe = col.tpe match {
      case db.Type.BigInt              => sc.Type.Long
      case db.Type.VarChar(_)          => sc.Type.String
      case db.Type.Boolean             => sc.Type.Boolean
      case db.Type.StringEnum(name, _) => sc.Type.Qualified(names.EnumName(pkg, name))
    }
    if (col.isNotNull) baseTpe else sc.Type.Option(baseTpe)
  }

  val maybeId: Option[TableComputed.Id] =
    table.primaryKey.map { pk =>
      val col = colsByName(pk.colName)
      val qident = names.titleCase(pkg, table.name.value, "Id")
      val underlying = scalaType(pkg, col)
      val paramName = names.field(col.name)
      TableComputed.Id(col, qident, underlying, paramName)
    }

  val scalaFields: Seq[(sc.Ident, sc.Type, db.Col)] = {
    table.cols.map {
      case col @ db.Col(colName, _, isNotNull, _) if table.primaryKey.exists(_.colName == colName) =>
        if (!isNotNull) {
          sys.error(s"assumption: id column in ${table.name.value} should be not null")
        }
        (names.field(colName), maybeId.get.tpe, col)
      case col =>
        val finalType: sc.Type = scalaType(pkg, col)
        (names.field(col.name), finalType, col)
    }
  }

  val scalaFieldsNotId: Seq[(sc.Ident, sc.Type, db.Col)] =
    scalaFields.filterNot { case (_, _, col) => table.primaryKey.exists(_.colName == col.name) }

  val RepoName: sc.QIdent = names.titleCase(pkg, table.name.value, "Repo")
  val RepoImplName: sc.QIdent = names.titleCase(pkg, table.name.value, "RepoImpl")
  val RowName: sc.QIdent = names.titleCase(pkg, table.name.value, "Row")
  val RowUnsavedName: Option[sc.QIdent] = if (scalaFieldsNotId.nonEmpty) Some(names.titleCase(pkg, table.name.value, "RowUnsaved")) else None
  val FieldValueName: sc.QIdent = names.titleCase(pkg, table.name.value, "FieldValue")

  val repoMethods: Option[List[RepoMethod]] = {
    val RowType = sc.Type.Qualified(RowName)

    val foo = List(
      maybeId match {
        case Some(id) =>
          val idParam = sc.Param(id.paramName, id.tpe)

          val updateMethod = RowUnsavedName.map { unsaved =>
            val unsavedParam = sc.Param(sc.Ident("unsaved"), sc.Type.Qualified(unsaved))

            if (id.col.hasDefault) RepoMethod.InsertDbGeneratedKey(unsavedParam, id.tpe)
            else RepoMethod.InsertProvidedKey(idParam, unsavedParam)
          }

          val fieldValuesParam = sc.Param(
            sc.Ident("fieldValues"),
            sc.Type.List(sc.Type.TApply(sc.Type.Qualified(FieldValueName), List(sc.Type.Wildcard)))
          )

          List(
            Some(RepoMethod.SelectAll(RowType)),
            Some(RepoMethod.SelectById(idParam, RowType)),
            Some(RepoMethod.SelectAllByIds(sc.Param(id.paramName.appended("s"), sc.Type.List(id.tpe)), RowType)),
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

object TableComputed {
  case class Id(col: db.Col, qident: sc.QIdent, underlying: sc.Type, paramName: sc.Ident) {
    def name = qident.last
    def tpe = sc.Type.Qualified(qident)
  }
}

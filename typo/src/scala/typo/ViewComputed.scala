package typo

import typo.internal.rewriteDependentData.Eval

case class ViewComputed(pkg: sc.QIdent, view: db.View, eval: Eval[db.RelationName, Either[ViewComputed, TableComputed]]) {
  val dbColsAndCols: List[(db.Col, ColumnComputed)] = {
    view.cols.map { dbCol =>
      val columnComputed = ColumnComputed(
        pointsTo = view.dependencies.get(dbCol.name),
        name = names.field(dbCol.name),
        tpe = deriveType(dbCol),
        dbName = dbCol.name,
        hasDefault = dbCol.hasDefault,
        jsonDescription = dbCol.jsonDescription
      )

      dbCol -> columnComputed
    }
  }

  def deriveType(dbCol: db.Col) = {
    // we let types flow through constraints down to this column, the point is to reuse id types downstream
    val typeFromFk: Option[sc.Type] =
      view.dependencies.get(dbCol.name) match {
        case Some((otherTableName, otherColName)) if otherTableName != view.name =>
          eval(otherTableName).get match {
            case Some(otherTable) =>
              val cols = otherTable match {
                case Left(view)   => view.cols
                case Right(table) => table.cols
              }
              cols.find(_.dbName == otherColName).map(x => typeMapper.reapplyNullability(x.tpe, dbCol.nullability))
            case None =>
              System.err.println(s"Unexpected circular dependency involving ${view.name.value} => ${otherTableName.value}")
              None
          }

        case _ => None
      }

    val tpe = typeFromFk.getOrElse {
      typeMapper.scalaType(pkg, dbCol)
    }
    tpe
  }

  val cols: List[ColumnComputed] = dbColsAndCols.map { case (_, col) => col }
  val relation = RelationComputed(pkg, view.name, cols, maybeId = None)
  val RepoName: sc.QIdent = names.titleCase(pkg, view.name, "Repo")
  val RepoImplName: sc.QIdent = names.titleCase(pkg, view.name, "RepoImpl")
  val RowName: sc.QIdent = names.titleCase(pkg, view.name, "Row")
  val FieldValueName: sc.QIdent = names.titleCase(pkg, view.name, "FieldValue")

  val repoMethods: List[RepoMethod] = {
    val RowType = sc.Type.Qualified(RowName)
    val fieldValuesParam = sc.Param(
      sc.Ident("fieldValues"),
      sc.Type.List.of(sc.Type.Qualified(FieldValueName).of(sc.Type.Wildcard))
    )

    List(
      RepoMethod.SelectAll(RowType),
      RepoMethod.SelectByFieldValues(fieldValuesParam, RowType)
    )
  }
}

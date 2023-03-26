package typo.codegen

import typo._

case class ViewComputed(pkg: sc.QIdent, view: db.View) {
  val dbColsAndCols: List[(db.Col, ColumnComputed)] = {
    view.cols.map { dbCol =>
      val finalType: sc.Type = typeMapper.scalaType(pkg, dbCol)

      val pointsTo: Option[(db.RelationName, db.ColName)] = None
//        (col.baseColumnName, col.baseRelationName) match {
//        case (Some(colName), Some(relationName)) if relationName != view.name =>
//          Some((relationName, colName))
//        case _ =>
//          None
//      }
      dbCol -> ColumnComputed(pointsTo, names.field(dbCol.name), finalType, dbCol.name, dbCol.hasDefault, dbCol.jsonDescription)
    }
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

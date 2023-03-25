package typo

import typo.sqlscripts.SqlScript

import scala.jdk.CollectionConverters.IteratorHasAsScala

case class SqlScriptComputed(pkg0: sc.QIdent, script: SqlScript) {
  val segments: List[sc.Ident] = script.relPath.iterator().asScala.map(p => sc.Ident(p.toString)).toList
  val relationName = db.RelationName(None, segments.last.value.replace(".sql", ""))
  val pkg1 = pkg0 / segments.dropRight(1)

  val dbColsAndCols: List[(db.Col, ColumnComputed)] = {
    script.cols.map { dbCol =>
      val finalType: sc.Type = typeMapper.scalaType(pkg0, dbCol)

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
  val relation = RelationComputed(pkg1, relationName, cols, maybeId = None)
  val RepoName: sc.QIdent = names.titleCase(pkg1, relationName, "Repo")
  val RepoImplName: sc.QIdent = names.titleCase(pkg1, relationName, "RepoImpl")
  val RowName: sc.QIdent = names.titleCase(pkg1, relationName, "Row")
  val FieldValueName: sc.QIdent = names.titleCase(pkg1, relationName, "FieldValue")

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

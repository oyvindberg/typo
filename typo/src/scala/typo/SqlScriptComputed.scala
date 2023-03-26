package typo

import typo.sqlscripts.SqlScript

import scala.jdk.CollectionConverters.IteratorHasAsScala

case class SqlScriptComputed(pkg0: sc.QIdent, script: SqlScript) {
  val pathSegments: List[sc.Ident] = script.relPath.iterator().asScala.map(p => sc.Ident(p.toString)).toList
  val relationName = db.RelationName(None, pathSegments.last.value.replace(".sql", ""))
  val pkg1 = pkg0 / pathSegments.dropRight(1)

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

  val repoMethods: List[RepoMethod] = {
    List(
      RepoMethod.SqlScript(this),
    )
  }
}

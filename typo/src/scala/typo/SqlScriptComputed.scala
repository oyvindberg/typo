package typo

import typo.internal.rewriteDependentData.Eval
import typo.sqlscripts.SqlScript

import scala.jdk.CollectionConverters.IteratorHasAsScala

case class SqlScriptComputed(pkg0: sc.QIdent, script: SqlScript, eval: Eval[db.RelationName, Either[ViewComputed, TableComputed]]) {
  val pathSegments: List[sc.Ident] = script.relPath.iterator().asScala.map(p => sc.Ident(p.toString)).toList
  val relationName = db.RelationName(None, pathSegments.last.value.replace(".sql", ""))
  val pkg1 = pkg0 / pathSegments.dropRight(1)

  val dbColsAndCols: List[(db.Col, ColumnComputed)] = {
    script.cols.map { dbCol =>
      val columnComputed = ColumnComputed(
        pointsTo = script.dependencies.get(dbCol.name),
        name = names.field(dbCol.name),
        tpe = deriveType(dbCol),
        dbName = dbCol.name,
        hasDefault = dbCol.hasDefault,
        jsonDescription = dbCol.jsonDescription
      )
      dbCol -> columnComputed
    }
  }

  def deriveType(dbCol: db.Col): sc.Type = {
    // we let types flow through constraints down to this column, the point is to reuse id types downstream
    val typeFromFk: Option[sc.Type] =
      script.dependencies.get(dbCol.name) match {
        case Some((otherTableName, otherColName)) =>
          val otherTable = eval(otherTableName).forceGet
          val cols = otherTable match {
            case Left(view)   => view.cols
            case Right(table) => table.cols
          }
          cols.find(_.dbName == otherColName).map(_.tpe)
        case _ => None
      }

    val tpe = typeFromFk.getOrElse {
      typeMapper.scalaType(pkg0, dbCol)
    }
    tpe
  }

  val cols: List[ColumnComputed] = dbColsAndCols.map { case (_, col) => col }
  val relation = RelationComputed(pkg1, relationName, cols, maybeId = None)
  val RepoName: sc.QIdent = names.titleCase(pkg1, relationName, "Repo")
  val RepoImplName: sc.QIdent = names.titleCase(pkg1, relationName, "RepoImpl")
  val RowName: sc.QIdent = names.titleCase(pkg1, relationName, "Row")

  val repoMethods: List[RepoMethod] = {
    List(
      RepoMethod.SqlScript(this)
    )
  }
}

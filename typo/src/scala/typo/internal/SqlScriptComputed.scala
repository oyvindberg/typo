package typo
package internal

import typo.internal.rewriteDependentData.Eval
import typo.internal.sqlscripts.{DecomposedSql, SqlScript}

case class SqlScriptComputed(
    script: SqlScript,
    pkg0: sc.QIdent,
    mkNaming: sc.QIdent => Naming,
    scalaTypeMapper: TypeMapperScala,
    eval: Eval[db.RelationName, Either[ViewComputed, TableComputed]]
) {
  val pathSegments: List[sc.Ident] = script.relPath.segments.map(sc.Ident.apply)
  val relationName = db.RelationName(None, pathSegments.last.value.replace(".sql", ""))
  val naming = mkNaming(pkg0 / pathSegments.dropRight(1))

  val dbColsAndCols: NonEmptyList[(db.Col, ColumnComputed)] = {
    script.cols.map { dbCol =>
      val columnComputed = ColumnComputed(
        pointsTo = script.dependencies.get(dbCol.name),
        name = naming.field(dbCol.name),
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
      scalaTypeMapper(Left(script.relPath), dbCol, typeFromFk)
    }
    tpe
  }

  val params: List[SqlScriptComputed.ParamComputed] =
    script.params.map { param =>
      val maybeNameInScript: Option[db.ColName] =
        param.maybeName match {
          case DecomposedSql.NotNamedParam    => None
          case DecomposedSql.NamedParam(name) => Some(db.ColName(name))
        }

      val scalaName = maybeNameInScript match {
        case None       => sc.Ident(s"param${param.indices.head}")
        case Some(name) => naming.field(name)
      }
      val tpe = scalaTypeMapper.param(script.relPath, maybeNameInScript, param.tpe, param.nullability)
      SqlScriptComputed.ParamComputed(scalaName, tpe, param)
    }

  val cols: NonEmptyList[ColumnComputed] = dbColsAndCols.map { case (_, col) => col }
  val relation = RelationComputed(naming, relationName, cols, maybeId = None)

  val repoMethods: NonEmptyList[RepoMethod] = {
    NonEmptyList(
      RepoMethod.SqlScript(this)
    )
  }
}

object SqlScriptComputed {
  case class ParamComputed(name: sc.Ident, tpe: sc.Type, underlying: SqlScript.Param)
}

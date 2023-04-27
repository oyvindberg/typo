package typo
package internal

import typo.internal.rewriteDependentData.Eval
import typo.internal.sqlfiles.{DecomposedSql, SqlFile}

case class SqlFileComputed(
    sqlFile: SqlFile,
    pkg0: sc.QIdent,
    mkNaming: sc.QIdent => Naming,
    scalaTypeMapper: TypeMapperScala,
    eval: Eval[db.RelationName, Either[ViewComputed, TableComputed]]
) {
  val pathSegments: List[sc.Ident] = sqlFile.relPath.segments.map(sc.Ident.apply)
  val relationName = db.RelationName(None, pathSegments.last.value.replace(".sql", ""))
  val naming = mkNaming(pkg0 / pathSegments.dropRight(1))

  val cols: NonEmptyList[ColumnComputed] =
    sqlFile.cols.map { dbCol =>
      ColumnComputed(
        pointsTo = sqlFile.dependencies.get(dbCol.name),
        name = naming.field(dbCol.name),
        tpe = deriveType(dbCol),
        dbCol = dbCol
      )
    }

  def deriveType(dbCol: db.Col): sc.Type = {
    // we let types flow through constraints down to this column, the point is to reuse id types downstream
    val typeFromFk: Option[sc.Type] =
      sqlFile.dependencies.get(dbCol.name) match {
        case Some((otherTableName, otherColName)) =>
          val otherTable = eval(otherTableName).forceGet
          val cols = otherTable match {
            case Left(view)   => view.cols
            case Right(table) => table.cols
          }
          cols.find(_.dbName == otherColName).map(_.tpe)
        case _ => None
      }

    val tpe = scalaTypeMapper.col(OverrideFrom.SqlFile(sqlFile.relPath), dbCol, typeFromFk)

    tpe
  }

  val params: List[SqlFileComputed.ParamComputed] = {
    val from = OverrideFrom.SqlFileParam(sqlFile.relPath)

    sqlFile.params.map { param =>
      val maybeNameInScript: Option[db.ColName] =
        param.maybeName match {
          case DecomposedSql.NotNamedParam    => None
          case DecomposedSql.NamedParam(name) => Some(db.ColName(name))
        }

      val scalaName = maybeNameInScript match {
        case None       => sc.Ident(s"param${param.indices.head}")
        case Some(name) => naming.field(name)
      }
      val tpe = scalaTypeMapper.param(from, maybeNameInScript, param.tpe, param.nullability)
      SqlFileComputed.ParamComputed(scalaName, tpe, param)
    }
  }

  val relation = RelationComputed(naming, relationName, cols, maybeId = None)

  val repoMethods: NonEmptyList[RepoMethod] = {
    NonEmptyList(
      RepoMethod.SqlFile(this)
    )
  }
}

object SqlFileComputed {
  case class ParamComputed(name: sc.Ident, tpe: sc.Type, underlying: SqlFile.Param)
}

package typo
package internal

import typo.internal.rewriteDependentData.EvalMaybe
import typo.internal.sqlfiles.{DecomposedSql, SqlFile}

case class ComputedSqlFile(
    sqlFile: SqlFile,
    pkg0: sc.QIdent,
    naming: Naming,
    scalaTypeMapper: TypeMapperScala,
    eval: EvalMaybe[db.RelationName, HasSource]
) {
  val source = Source.SqlFile(sqlFile.relPath)

  val cols: NonEmptyList[ComputedColumn] =
    sqlFile.cols.map { dbCol =>
      ComputedColumn(
        pointsTo = sqlFile.dependencies.get(dbCol.name).flatMap { case (relName, colName) => eval(relName).flatMap(_.get.map(foo => foo.source -> colName)) },
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
          for {
            existingTable <- eval(otherTableName)
            nonCircular <- existingTable.get
            col <- nonCircular.cols.find(_.dbName == otherColName)
          } yield col.tpe
        case _ => None
      }

    val tpe = scalaTypeMapper.col(Source.SqlFile(sqlFile.relPath), dbCol, typeFromFk)

    tpe
  }

  val params: List[ComputedSqlFile.ParamComputed] = {
    val source = Source.SqlFileParam(sqlFile.relPath)

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
      val tpe = scalaTypeMapper.param(source, maybeNameInScript, param.tpe, param.nullability)
      ComputedSqlFile.ParamComputed(scalaName, tpe, param)
    }
  }

  val names = ComputedNames(naming, source, cols, maybeId = None, enableFieldValue = false, enableDsl = false)

  val RowName: sc.Type.Qualified = names.RowName

  val repoMethods: NonEmptyList[RepoMethod] =
    NonEmptyList(RepoMethod.SqlFile(this))
}

object ComputedSqlFile {
  case class ParamComputed(name: sc.Ident, tpe: sc.Type, underlying: SqlFile.Param)
}

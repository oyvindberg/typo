package typo
package internal
package sqlfiles

import anorm.*
import org.postgresql.core.SqlCommandType
import org.postgresql.jdbc.PgConnection
import org.postgresql.util.PSQLException
import typo.generated.custom.view_column_dependencies.ViewColumnDependenciesSqlRepoImpl

import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.{FileVisitResult, Files, Path, SimpleFileVisitor}
import java.sql.Connection

object readSqlFileDirectories {
  def apply(scriptsPath: Path)(implicit c: Connection): List[SqlFile] =
    findSqlFilesUnder(scriptsPath).flatMap { sqlFile =>
      val sqlContent = Files.readString(sqlFile)
      val decomposedSql = DecomposedSql.parse(sqlContent)
      val relativePath = RelPath.relativeTo(scriptsPath, sqlFile)

      println(s"Parsing $sqlFile")

      val queryType = queryTypeFor(decomposedSql, c)
      queryType match {
        case SqlCommandType.BLANK =>
          System.err.println(s"Skipping $sqlFile because it's empty")
          None
        case _ =>
          try {
            val maybeSqlData = JdbcMetadata.from(decomposedSql.sqlWithQuestionMarks)
            maybeSqlData match {
              case Left(msg) =>
                System.err.println(s"Error while parsing $sqlFile : $msg. Will ignore the file.")
                None
              case Right(jdbcMetadata) =>
                val deps: Map[db.ColName, (db.RelationName, db.ColName)] =
                  queryType match {
                    case SqlCommandType.SELECT => fetchDepsViaTemporaryView(decomposedSql, relativePath)
                    case _                     => Map.empty
                  }
                val nullableColumnsFromJoins =
                  queryType match {
                    case SqlCommandType.SELECT => NullabilityFromExplain.from(decomposedSql, jdbcMetadata.params).nullableIndices
                    case _                     => None
                  }

                Some(SqlFile(RelPath.relativeTo(scriptsPath, sqlFile), decomposedSql, jdbcMetadata, nullableColumnsFromJoins, deps))
            }
          } catch {
            case e: PSQLException =>
              System.err.println(s"Error while parsing $sqlFile : ${e.getMessage}. Will ignore the file.")
              None
          }
      }
    }

  def queryTypeFor(decomposedSql: DecomposedSql, c: Connection): SqlCommandType = {
    val pc = c.unwrap(classOf[PgConnection])
    val q = pc.createQuery(decomposedSql.sqlWithNulls, true, false)
    q.query.getSqlCommand.getType
  }

  /** believe it or not the dependency information we get through prepared statements and for views are not the same. It's too valuable information to leave out, so let's try to read it from a
    * temporary view.
    */
  def fetchDepsViaTemporaryView(decomposedSql: DecomposedSql, relativePath: RelPath)(implicit c: Connection): Map[db.ColName, (db.RelationName, db.ColName)] = {
    val viewName = relativePath.segments.mkString("_").replace(".sql", "")
    val sql = s"""create temporary view $viewName as (${decomposedSql.sqlWithNulls})"""
    SQL(sql).execute()
    val ret = ViewColumnDependenciesSqlRepoImpl(Some(viewName)).map { row =>
      val table = db.RelationName(row.tableSchema.map(_.value), row.tableName)
      (db.ColName(row.columnName), (table, db.ColName(row.columnName)))
    }.toMap
    SQL(s"drop view $viewName").execute()
    ret
  }

  def findSqlFilesUnder(scriptsPath: Path): List[Path] = {
    val found = List.newBuilder[Path]
    Files.walkFileTree(
      scriptsPath,
      new SimpleFileVisitor[Path] {
        override def visitFile(file: Path, attrs: BasicFileAttributes): FileVisitResult = {
          if (file.toString.endsWith(".sql")) found += file
          FileVisitResult.CONTINUE
        }
      }
    )
    found.result()
  }
}

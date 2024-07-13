package typo
package internal
package sqlfiles

import org.postgresql.core.SqlCommandType
import org.postgresql.jdbc.PgConnection
import org.postgresql.util.PSQLException
import typo.internal.analysis.{DecomposedSql, JdbcMetadata, NullabilityFromExplain}

import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.{FileVisitResult, Files, Path, SimpleFileVisitor}
import java.sql.Connection
import scala.concurrent.{ExecutionContext, Future}

object readSqlFileDirectories {
  def apply(logger: TypoLogger, scriptsPath: Path, ds: TypoDataSource)(implicit ec: ExecutionContext): Future[List[SqlFile]] = {
    val eventualMaybeFiles: List[Future[Option[SqlFile]]] =
      findSqlFilesUnder(scriptsPath).map { sqlFile =>
        logger.timed(s"analyze $sqlFile") {

          val sqlContent = Files.readString(sqlFile)
          val decomposedSql = DecomposedSql.parse(sqlContent)

          ds.run { implicit c =>
            val queryType = queryTypeFor(decomposedSql, c)
            queryType match {
              case SqlCommandType.BLANK =>
                logger.info(s"Skipping $sqlFile because it's empty")
                None
              case _ =>
                try {
                  val maybeSqlData = JdbcMetadata.from(decomposedSql.sqlWithQuestionMarks)
                  maybeSqlData match {
                    case Left(msg) =>
                      logger.warn(s"Error while parsing $sqlFile : $msg. Will ignore the file.")
                      None
                    case Right(jdbcMetadata) =>
                      val nullableColumnsFromJoins =
                        queryType match {
                          case SqlCommandType.SELECT => NullabilityFromExplain.from(decomposedSql, jdbcMetadata.params).nullableIndices
                          case _                     => None
                        }

                      Some(SqlFile(RelPath.relativeTo(scriptsPath, sqlFile), decomposedSql, jdbcMetadata, nullableColumnsFromJoins))
                  }
                } catch {
                  case e: PSQLException =>
                    logger.warn(s"Error while parsing $sqlFile : ${e.getMessage}. Will ignore the file.")
                    None
                }
            }
          }
        }
      }

    Future.sequence(eventualMaybeFiles).map(_.flatten)
  }

  def queryTypeFor(decomposedSql: DecomposedSql, c: Connection): SqlCommandType = {
    val pc = c.unwrap(classOf[PgConnection])
    val q = pc.createQuery(decomposedSql.sqlWithNulls, true, false)
    Option(q.query.getSqlCommand).map(_.getType).getOrElse(SqlCommandType.BLANK)
  }

  def findSqlFilesUnder(scriptsPath: Path): List[Path] = {
    if (!Files.exists(scriptsPath)) Nil
    else {
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
}

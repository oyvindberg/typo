package typo
package internal
package sqlscripts

import org.postgresql.util.PSQLException

import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.{FileVisitResult, Files, Path, SimpleFileVisitor}
import java.sql.Connection

object Load {
  def apply(scriptsPath: Path, typeMapperDb: TypeMapperDb)(implicit c: Connection): List[SqlScript] =
    findSqlFilesUnder(scriptsPath).flatMap { sqlFile =>
      val sqlContent = Files.readString(sqlFile)
      val decomposedSql = DecomposedSql.parse(sqlContent)
      try {
        val analyzed = JdbcMetadata.from(decomposedSql.sqlWithQuestionMarks)
        val maybeScript = parseSqlScript(typeMapperDb, RelPath.relativeTo(scriptsPath, sqlFile), decomposedSql, analyzed)
        maybeScript
      } catch {
        case e: PSQLException =>
          System.err.println(s"Error while parsing $sqlFile : ${e.getMessage}. SQL: ${decomposedSql.sqlWithQuestionMarks}")
          None
      }
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

  def parseSqlScript(typeMapperDb: TypeMapperDb, relativePath: RelPath, decomposedSql: DecomposedSql, jdbcMetadata: JdbcMetadata): Option[SqlScript] = {
    val cols = jdbcMetadata.columns.map { col =>
      val jsonDescription = minimalJson(col)
      db.Col(
        name = col.name,
        tpe = typeMapperDb.dbTypeFrom(col.columnTypeName, Some(col.precision)).getOrElse {
          System.err.println(s"$relativePath Couldn't translate type from column $jsonDescription")
          db.Type.Text
        },
        hasDefault = col.isAutoIncrement,
        jsonDescription = jsonDescription,
        nullability = col.isNullable.toNullability
      )
    }

    val deps: Map[db.ColName, (db.RelationName, db.ColName)] =
      jdbcMetadata.columns.flatMap { col =>
        col.baseRelationName.zip(col.baseColumnName).map(col.name -> _)
      }.toMap

    val params = decomposedSql.paramNamesWithIndices.map { case (maybeName, indices) =>
      val jdbcParam = jdbcMetadata.params(indices.head)
      val tpe = typeMapperDb.dbTypeFrom(jdbcParam.parameterTypeName, Some(jdbcParam.precision)).getOrElse {
        System.err.println(s"$relativePath: Couldn't translate type from param $maybeName")
        db.Type.Text
      }
      SqlScript.Param(maybeName, indices, tpe, jdbcParam.isNullable.toNullability)
    }

    for {
      cols <- NonEmptyList.fromList(cols)
    } yield SqlScript(relativePath, decomposedSql, params, cols, deps)
  }
}

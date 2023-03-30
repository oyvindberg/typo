package typo
package sqlscripts

import org.postgresql.util.PSQLException
import typo.internal.minimalJson

import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.{FileVisitResult, Files, Path, SimpleFileVisitor}
import java.sql.Connection

object Load {
  def apply(scriptsPath: Path, enums: Map[String, db.StringEnum])(implicit c: Connection): List[SqlScript] =
    findSqlFilesUnder(scriptsPath).flatMap { sqlFile =>
      val sqlContent = Files.readString(sqlFile)
      val decomposedSql = DecomposedSql.parse(sqlContent)
      try {
        val analyzed = JdbcMetadata.from(decomposedSql.sqlWithQuestionMarks)
        val script = parseSqlScript(enums, scriptsPath.relativize(sqlFile), decomposedSql, analyzed)
        Some(script)
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

  def parseSqlScript(enums: Map[String, db.StringEnum], relativePath: Path, decomposedSql: DecomposedSql, jdbcMetadata: JdbcMetadata): SqlScript = {
    val cols = jdbcMetadata.columns.map { col =>
      val jsonDescription = minimalJson(col)
      db.Col(
        name = col.name,
        tpe = TypeMapperDb.dbTypeFrom(enums, col.columnTypeName, Some(col.precision)).getOrElse {
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
      val tpe = TypeMapperDb.dbTypeFrom(enums, jdbcParam.parameterTypeName, Some(jdbcParam.precision)).getOrElse {
        System.err.println(s"$relativePath: Couldn't translate type from param $maybeName")
        db.Type.Text
      }
      SqlScript.Param(maybeName, indices, tpe, jdbcParam.isNullable.toNullability)
    }

    SqlScript(relativePath, decomposedSql, params, cols, deps)
  }
}

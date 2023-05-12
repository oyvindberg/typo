package typo.internal.sqlfiles

import anorm.*
import org.postgresql.util.PSQLException
import typo.generated.custom.view_column_dependencies.ViewColumnDependenciesSqlRepoImpl
import typo.internal.{TypeMapperDb, minimalJson}
import typo.{NonEmptyList, RelPath, db}

import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.{FileVisitResult, Files, Path, SimpleFileVisitor}
import java.sql.Connection

object Load {
  def apply(scriptsPath: Path, typeMapperDb: TypeMapperDb)(implicit c: Connection): List[SqlFile] =
    findSqlFilesUnder(scriptsPath).flatMap { sqlFile =>
      val sqlContent = Files.readString(sqlFile)
      val decomposedSql = DecomposedSql.parse(sqlContent)
      val relativePath = RelPath.relativeTo(scriptsPath, sqlFile)

      val jdbcMetadata: Option[JdbcMetadata] =
        try {
          Some(JdbcMetadata.from(decomposedSql.sqlWithQuestionMarks))
        } catch {
          case e: PSQLException =>
            System.err.println(s"Error while parsing $sqlFile : ${e.getMessage}. SQL: ${decomposedSql.sqlWithQuestionMarks}")
            None
        }

      val maybeScript = jdbcMetadata.flatMap { jdbcMetadata =>
        val deps: Map[db.ColName, (db.RelationName, db.ColName)] =
          readDepsFromTemporaryView(sqlFile, decomposedSql, relativePath)

        parseSqlFile(
          typeMapperDb,
          RelPath.relativeTo(scriptsPath, sqlFile),
          decomposedSql,
          jdbcMetadata,
          deps
        )
      }

      maybeScript
    }

  /** believe it or not the dependency information we get through prepared statements and for views are not the same. It's too valuable information to leave
    * out, so let's try to read it from a temporary view.
    */
  def readDepsFromTemporaryView(
      sqlFile: Path,
      decomposedSql: DecomposedSql,
      relativePath: RelPath
  )(implicit c: Connection): Map[db.ColName, (db.RelationName, db.ColName)] = {
    val viewName = relativePath.segments.mkString("_").replace(".sql", "")
    val sql = s"""create temporary view $viewName as (${decomposedSql.sqlWithNulls})"""
    try {
      SQL(sql).execute()
      ViewColumnDependenciesSqlRepoImpl(Some(viewName)).map { row =>
        val table = db.RelationName(row.tableSchema.map(_.getValue), row.tableName)
        (db.ColName(row.columnName), (table, db.ColName(row.columnName)))
      }.toMap
    } catch {
      case e: PSQLException =>
        System.err.println(s"Couldn't read dependencies for $sqlFile through a temporary view: ${e.getMessage}. SQL: $sql")
        Map.empty
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

  def parseSqlFile(
      typeMapperDb: TypeMapperDb,
      relativePath: RelPath,
      decomposedSql: DecomposedSql,
      jdbcMetadata: JdbcMetadata,
      depsFromView: Map[db.ColName, (db.RelationName, db.ColName)]
  ): Option[SqlFile] = {
    val columns = jdbcMetadata.columns

    val cols = columns.map { col =>
      val jsonDescription = minimalJson(col)
      db.Col(
        name = col.name,
        tpe = typeMapperDb.dbTypeFrom(col.columnTypeName, Some(col.precision)).getOrElse {
          System.err.println(s"$relativePath Couldn't translate type from column $jsonDescription")
          db.Type.Text
        },
        udtName = None,
        columnDefault = if (col.isAutoIncrement) Some("auto-increment") else None,
        comment = None,
        jsonDescription = jsonDescription,
        nullability = col.isNullable.toNullability
      )
    }

    val deps: Map[db.ColName, (db.RelationName, db.ColName)] =
      depsFromView ++ columns.flatMap { col =>
        col.baseRelationName.zip(col.baseColumnName).map(col.name -> _)
      }.toMap

    val params = decomposedSql.paramNamesWithIndices.map { case (maybeName, indices) =>
      val jdbcParam = jdbcMetadata.params(indices.head)
      val tpe = typeMapperDb.dbTypeFrom(jdbcParam.parameterTypeName, Some(jdbcParam.precision)).getOrElse {
        System.err.println(s"$relativePath: Couldn't translate type from param $maybeName")
        db.Type.Text
      }
      SqlFile.Param(maybeName, indices, tpe, jdbcParam.isNullable.toNullability)
    }

    for {
      cols <- NonEmptyList.fromList(cols)
    } yield SqlFile(relativePath, decomposedSql, params, cols, deps)
  }
}

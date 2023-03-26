package typo
package sqlscripts

import play.api.libs.json.Json

import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.{FileVisitResult, Files, Path, SimpleFileVisitor}
import java.sql.Connection

object Load {
  def apply(scriptsPath: Path, enums: Map[String, db.StringEnum])(implicit c: Connection): List[SqlScript] = {
    val found = List.newBuilder[SqlScript]
    Files.walkFileTree(
      scriptsPath,
      new SimpleFileVisitor[Path] {
        override def visitFile(file: Path, attrs: BasicFileAttributes): FileVisitResult = {
          if (file.toString.endsWith(".sql")) {
            val relativePath = scriptsPath.relativize(file)
            val content = Files.readString(file)
            val analyzed = Analyzed.from(content)

            val cols = analyzed.columns.map { col =>
              db.Col(
                name = col.name,
                tpe = typeMapper.dbTypeFrom(enums, col.columnTypeName, Some(col.precision)).getOrElse {
                  System.err.println(s"Couldn't translate type from column $col")
                  db.Type.Text
                },
                hasDefault = col.isAutoIncrement,
                jsonDescription = Json.toJson(col),
                nullability = col.isNullable.toNullability
              )
            }

            val deps: Map[db.ColName, (db.RelationName, db.ColName)] =
              analyzed.columns.flatMap { col =>
                col.baseRelationName.zip(col.baseColumnName).map(col.name -> _)
              }.toMap

            val sqlFile = SqlScript(relativePath, content, analyzed.params, cols, deps)
            found += sqlFile
          }
          FileVisitResult.CONTINUE
        }
      }
    )

    found.result()
  }
}

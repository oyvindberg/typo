package typo.sqlscripts

import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.{FileVisitResult, Files, Path, SimpleFileVisitor}
import java.sql.Connection

object Load {
  def apply(scriptsPath: Path)(implicit c: Connection): List[SqlFile] = {
    // create temporary views for all the sql files in the scripts directory
    val found = List.newBuilder[SqlFile]
    Files.walkFileTree(
      scriptsPath,
      new SimpleFileVisitor[Path] {
        override def visitFile(file: Path, attrs: BasicFileAttributes): FileVisitResult = {
          if (file.toString.endsWith(".sql")) {
            val relativePath = scriptsPath.relativize(file)
            val content = Files.readString(file)
            val analyzed = Analyzed.from(content)
            val sqlFile = SqlFile(relativePath, content, analyzed.params, analyzed.columns)
            found += sqlFile
          }
          FileVisitResult.CONTINUE
        }
      }
    )

    found.result()
  }
}

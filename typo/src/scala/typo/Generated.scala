package typo

import typo.internal.FileSync
import typo.internal.FileSync.SoftWrite

import java.nio.file.Path

case class Generated(files: Map[RelPath, String]) {
  def mapFiles(f: Map[RelPath, String] => Map[RelPath, String]): Generated =
    copy(files = f(files))

  def overwriteFolder(folder: Path, adjustPaths: RelPath => RelPath = identity, softWrite: SoftWrite = SoftWrite.Yes(Set.empty)): Map[Path, FileSync.Synced] =
    FileSync.syncStrings(
      folder = folder,
      fileRelMap = files.map { case (relPath, content) => adjustPaths(relPath) -> content },
      deleteUnknowns = FileSync.DeleteUnknowns.Yes(maxDepth = None),
      softWrite = softWrite
    )
}
object Generated {
  def apply(files: List[sc.File]): Generated = {
    val asRelativePaths: Map[RelPath, String] =
      files.map { case sc.File(sc.Type.Qualified(sc.QIdent(idents)), content, _) =>
        val path = idents.init
        val name = idents.last
        val relPath = RelPath(path.map(_.value) :+ (name.value + ".scala"))
        relPath -> content.render.asString
      }.toMap
    new Generated(asRelativePaths)
  }

}

package typo

import typo.internal.FileSync
import typo.internal.FileSync.SoftWrite

import java.nio.file.Path

case class Generated(files: Map[RelPath, sc.Code]) {
  def mapFiles(f: Map[RelPath, sc.Code] => Map[RelPath, sc.Code]): Generated =
    copy(files = f(files))

  def overwriteFolder(folder: Path, softWrite: SoftWrite = SoftWrite.Yes(Set.empty)): Map[Path, FileSync.Synced] =
    FileSync.syncStrings(
      folder = folder,
      fileRelMap = files.map { case (relPath, code) => (relPath, code.render.asString) },
      deleteUnknowns = FileSync.DeleteUnknowns.Yes(maxDepth = None),
      softWrite = softWrite
    )
}
object Generated {
  def apply(files: Iterator[sc.File]): Generated = {
    val asRelativePaths: Map[RelPath, sc.Code] =
      files.map { case sc.File(sc.Type.Qualified(sc.QIdent(idents)), code, _) =>
        val path = idents.init
        val name = idents.last
        val relPath = RelPath(path.map(_.value) :+ s"${name.value}.scala")
        relPath -> code
      }.toMap
    new Generated(asRelativePaths)
  }

}

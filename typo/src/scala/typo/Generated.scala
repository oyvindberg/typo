package typo

import typo.internal.FileSync

import java.nio.file.Path

case class Generated(files: List[sc.File]) {
  val asRelativePaths: Map[RelPath, String] =
    files.map { case sc.File(sc.Type.Qualified(sc.QIdent(idents)), content, _) =>
      val path = idents.init
      val name = idents.last
      val relPath = RelPath(path.map(_.value) :+ (name.value + ".scala"))
      relPath -> content.render
    }.toMap

  def overwriteFolder(folder: Path, soft: Boolean, adjustPaths: RelPath => RelPath = identity): Map[Path, FileSync.Synced] =
    FileSync.syncStrings(
      folder = folder,
      fileRelMap = asRelativePaths.map { case (relPath, content) => adjustPaths(relPath) -> content },
      deleteUnknowns = FileSync.DeleteUnknowns.Yes(maxDepth = None),
      soft = soft
    )
}

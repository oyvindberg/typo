package typo

import typo.internal.sqlfiles.readSqlFileDirectories

import java.nio.file.Path
import java.sql.Connection

/** Main entry-point for generating code from a database.
  */
object generateFromDb {

  /** Allows you to generate code into *one* folder
    */
  def apply(
      options: Options,
      targetFolder: Path,
      testTargetFolder: Option[Path],
      selector: Selector = Selector.ExcludePostgresInternal,
      scriptsPaths: List[Path] = Nil
  )(implicit c: Connection): Generated =
    apply(options, ProjectGraph(name = "", targetFolder, testTargetFolder, selector, scriptsPaths, Nil)).head

  /** Allows you to generate code into multiple folders
    */
  def apply(
      options: Options,
      graph: ProjectGraph[Selector, List[Path]]
  )(implicit c: Connection): List[Generated] = {
    Banner.maybePrint(options)
    internal.generate(
      options,
      MetaDb.fromDb(options.logger),
      graph.mapScripts(paths => paths.flatMap(p => readSqlFileDirectories(options.logger, p)))
    )
  }
}

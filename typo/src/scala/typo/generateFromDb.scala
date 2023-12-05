package typo

import typo.internal.sqlfiles.readSqlFileDirectories

import java.nio.file.Path
import java.sql.Connection

object generateFromDb {
  def apply(options: Options, folder: Path, selector: Selector = Selector.ExcludePostgresInternal, scriptsPaths: List[Path] = Nil)(implicit c: Connection): Generated = {
    apply(options, ProjectGraph(name = "", folder, selector, scriptsPaths, Nil)).head
  }

  def apply(options: Options, graph: ProjectGraph[Selector, List[Path]])(implicit c: Connection): List[Generated] = {
    Banner.maybePrint(options)
    internal.generate(
      options,
      MetaDb.fromDb(options.logger),
      graph.mapScripts(paths => paths.flatMap(p => readSqlFileDirectories(options.logger, p)))
    )
  }
}

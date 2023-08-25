package typo

import typo.internal.*
import typo.internal.sqlfiles.readSqlFileDirectories

import java.nio.file.Path
import java.sql.Connection

object generateFromDb {
  def apply(options: Options, selector: Selector = Selector.ExcludePostgresInternal, scriptsPaths: List[Path] = Nil)(implicit c: Connection): Generated = {
    Banner.maybePrint(options)
    generate(options, MetaDb.fromDb, scriptsPaths.flatMap(p => readSqlFileDirectories(p)), selector)
  }
}

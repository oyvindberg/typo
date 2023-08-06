package typo

import typo.internal.TypeMapperDb
import typo.internal.metadb.load
import typo.internal.sqlfiles.SqlFile

import java.nio.file.Path
import java.sql.Connection

case class MetaDb(
    relations: List[db.Relation],
    enums: List[db.StringEnum],
    domains: List[db.Domain],
    sqlFiles: List[SqlFile],
    typeMapperDb: TypeMapperDb
)
object MetaDb {
  def fromDb(implicit c: Connection): MetaDb =
    load(maybeScriptPath = None)

  def fromDbAndScripts(scriptPath: Path)(implicit c: Connection): MetaDb =
    load(maybeScriptPath = Some(scriptPath))
}

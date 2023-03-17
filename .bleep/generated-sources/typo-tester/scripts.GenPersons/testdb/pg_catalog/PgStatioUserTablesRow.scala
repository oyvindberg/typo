package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgStatioUserTablesRow(
  /** Points to [[testdb.pg_catalog.PgStatioAllTablesRow.relid]] */
  relid: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatioAllTablesRow.schemaname]] */
  schemaname: Option[String],
  /** Points to [[testdb.pg_catalog.PgStatioAllTablesRow.relname]] */
  relname: Option[String],
  /** Points to [[testdb.pg_catalog.PgStatioAllTablesRow.heapBlksRead]] */
  heapBlksRead: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatioAllTablesRow.heapBlksHit]] */
  heapBlksHit: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatioAllTablesRow.idxBlksRead]] */
  idxBlksRead: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatioAllTablesRow.idxBlksHit]] */
  idxBlksHit: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatioAllTablesRow.toastBlksRead]] */
  toastBlksRead: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatioAllTablesRow.toastBlksHit]] */
  toastBlksHit: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatioAllTablesRow.tidxBlksRead]] */
  tidxBlksRead: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatioAllTablesRow.tidxBlksHit]] */
  tidxBlksHit: Option[Long]
)

object PgStatioUserTablesRow {
  implicit val rowParser: RowParser[PgStatioUserTablesRow] = { row =>
    Success(
      PgStatioUserTablesRow(
        relid = row[Option[Long]]("relid"),
        schemaname = row[Option[String]]("schemaname"),
        relname = row[Option[String]]("relname"),
        heapBlksRead = row[Option[Long]]("heap_blks_read"),
        heapBlksHit = row[Option[Long]]("heap_blks_hit"),
        idxBlksRead = row[Option[Long]]("idx_blks_read"),
        idxBlksHit = row[Option[Long]]("idx_blks_hit"),
        toastBlksRead = row[Option[Long]]("toast_blks_read"),
        toastBlksHit = row[Option[Long]]("toast_blks_hit"),
        tidxBlksRead = row[Option[Long]]("tidx_blks_read"),
        tidxBlksHit = row[Option[Long]]("tidx_blks_hit")
      )
    )
  }

  implicit val oFormat: OFormat[PgStatioUserTablesRow] = Json.format
}

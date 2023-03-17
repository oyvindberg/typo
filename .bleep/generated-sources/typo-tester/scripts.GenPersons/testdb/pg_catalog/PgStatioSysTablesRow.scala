package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgStatioSysTablesRow(
  relid: Option[Long],
  schemaname: Option[String],
  relname: Option[String],
  heapBlksRead: Option[Long],
  heapBlksHit: Option[Long],
  idxBlksRead: Option[Long],
  idxBlksHit: Option[Long],
  toastBlksRead: Option[Long],
  toastBlksHit: Option[Long],
  tidxBlksRead: Option[Long],
  tidxBlksHit: Option[Long]
)

object PgStatioSysTablesRow {
  implicit val rowParser: RowParser[PgStatioSysTablesRow] = { row =>
    Success(
      PgStatioSysTablesRow(
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

  implicit val oFormat: OFormat[PgStatioSysTablesRow] = Json.format
}

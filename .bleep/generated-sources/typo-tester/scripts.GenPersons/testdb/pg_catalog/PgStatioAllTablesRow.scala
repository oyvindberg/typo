package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgStatioAllTablesRow(
  relid: Long,
  schemaname: String,
  relname: String,
  heapBlksRead: /* unknown nullability */ Option[Long],
  heapBlksHit: /* unknown nullability */ Option[Long],
  idxBlksRead: /* unknown nullability */ Option[Long],
  idxBlksHit: /* unknown nullability */ Option[Long],
  toastBlksRead: /* unknown nullability */ Option[Long],
  toastBlksHit: /* unknown nullability */ Option[Long],
  tidxBlksRead: /* unknown nullability */ Option[Long],
  tidxBlksHit: /* unknown nullability */ Option[Long]
)

object PgStatioAllTablesRow {
  implicit val rowParser: RowParser[PgStatioAllTablesRow] = { row =>
    Success(
      PgStatioAllTablesRow(
        relid = row[Long]("relid"),
        schemaname = row[String]("schemaname"),
        relname = row[String]("relname"),
        heapBlksRead = row[/* unknown nullability */ Option[Long]]("heap_blks_read"),
        heapBlksHit = row[/* unknown nullability */ Option[Long]]("heap_blks_hit"),
        idxBlksRead = row[/* unknown nullability */ Option[Long]]("idx_blks_read"),
        idxBlksHit = row[/* unknown nullability */ Option[Long]]("idx_blks_hit"),
        toastBlksRead = row[/* unknown nullability */ Option[Long]]("toast_blks_read"),
        toastBlksHit = row[/* unknown nullability */ Option[Long]]("toast_blks_hit"),
        tidxBlksRead = row[/* unknown nullability */ Option[Long]]("tidx_blks_read"),
        tidxBlksHit = row[/* unknown nullability */ Option[Long]]("tidx_blks_hit")
      )
    )
  }

  implicit val oFormat: OFormat[PgStatioAllTablesRow] = Json.format
}

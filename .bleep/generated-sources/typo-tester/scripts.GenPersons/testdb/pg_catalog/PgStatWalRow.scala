package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import java.time.LocalDateTime
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.math.BigDecimal

case class PgStatWalRow(
  walRecords: /* unknown nullability */ Option[Long],
  walFpi: /* unknown nullability */ Option[Long],
  walBytes: /* unknown nullability */ Option[BigDecimal],
  walBuffersFull: /* unknown nullability */ Option[Long],
  walWrite: /* unknown nullability */ Option[Long],
  walSync: /* unknown nullability */ Option[Long],
  walWriteTime: /* unknown nullability */ Option[Double],
  walSyncTime: /* unknown nullability */ Option[Double],
  statsReset: /* unknown nullability */ Option[LocalDateTime]
)

object PgStatWalRow {
  implicit val rowParser: RowParser[PgStatWalRow] = { row =>
    Success(
      PgStatWalRow(
        walRecords = row[/* unknown nullability */ Option[Long]]("wal_records"),
        walFpi = row[/* unknown nullability */ Option[Long]]("wal_fpi"),
        walBytes = row[/* unknown nullability */ Option[BigDecimal]]("wal_bytes"),
        walBuffersFull = row[/* unknown nullability */ Option[Long]]("wal_buffers_full"),
        walWrite = row[/* unknown nullability */ Option[Long]]("wal_write"),
        walSync = row[/* unknown nullability */ Option[Long]]("wal_sync"),
        walWriteTime = row[/* unknown nullability */ Option[Double]]("wal_write_time"),
        walSyncTime = row[/* unknown nullability */ Option[Double]]("wal_sync_time"),
        statsReset = row[/* unknown nullability */ Option[LocalDateTime]]("stats_reset")
      )
    )
  }

  implicit val oFormat: OFormat[PgStatWalRow] = Json.format
}

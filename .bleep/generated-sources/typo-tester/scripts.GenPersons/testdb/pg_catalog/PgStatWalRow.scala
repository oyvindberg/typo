package testdb
package pg_catalog

import anorm.RowParser
import anorm.Success
import java.time.LocalDateTime
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.math.BigDecimal
import scala.util.Try

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

  implicit val oFormat: OFormat[PgStatWalRow] = new OFormat[PgStatWalRow]{
    override def writes(o: PgStatWalRow): JsObject =
      Json.obj(
        "wal_records" -> o.walRecords,
      "wal_fpi" -> o.walFpi,
      "wal_bytes" -> o.walBytes,
      "wal_buffers_full" -> o.walBuffersFull,
      "wal_write" -> o.walWrite,
      "wal_sync" -> o.walSync,
      "wal_write_time" -> o.walWriteTime,
      "wal_sync_time" -> o.walSyncTime,
      "stats_reset" -> o.statsReset
      )

    override def reads(json: JsValue): JsResult[PgStatWalRow] = {
      JsResult.fromTry(
        Try(
          PgStatWalRow(
            walRecords = json.\("wal_records").toOption.map(_.as[Long]),
            walFpi = json.\("wal_fpi").toOption.map(_.as[Long]),
            walBytes = json.\("wal_bytes").toOption.map(_.as[BigDecimal]),
            walBuffersFull = json.\("wal_buffers_full").toOption.map(_.as[Long]),
            walWrite = json.\("wal_write").toOption.map(_.as[Long]),
            walSync = json.\("wal_sync").toOption.map(_.as[Long]),
            walWriteTime = json.\("wal_write_time").toOption.map(_.as[Double]),
            walSyncTime = json.\("wal_sync_time").toOption.map(_.as[Double]),
            statsReset = json.\("stats_reset").toOption.map(_.as[LocalDateTime])
          )
        )
      )
    }
  }
}

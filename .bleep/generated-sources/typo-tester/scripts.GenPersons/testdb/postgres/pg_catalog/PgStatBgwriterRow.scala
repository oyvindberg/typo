package testdb
package postgres
package pg_catalog

import anorm.RowParser
import anorm.Success
import java.time.LocalDateTime
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgStatBgwriterRow(
  checkpointsTimed: /* unknown nullability */ Option[Long],
  checkpointsReq: /* unknown nullability */ Option[Long],
  checkpointWriteTime: /* unknown nullability */ Option[Double],
  checkpointSyncTime: /* unknown nullability */ Option[Double],
  buffersCheckpoint: /* unknown nullability */ Option[Long],
  buffersClean: /* unknown nullability */ Option[Long],
  maxwrittenClean: /* unknown nullability */ Option[Long],
  buffersBackend: /* unknown nullability */ Option[Long],
  buffersBackendFsync: /* unknown nullability */ Option[Long],
  buffersAlloc: /* unknown nullability */ Option[Long],
  statsReset: /* unknown nullability */ Option[LocalDateTime]
)

object PgStatBgwriterRow {
  implicit val rowParser: RowParser[PgStatBgwriterRow] = { row =>
    Success(
      PgStatBgwriterRow(
        checkpointsTimed = row[/* unknown nullability */ Option[Long]]("checkpoints_timed"),
        checkpointsReq = row[/* unknown nullability */ Option[Long]]("checkpoints_req"),
        checkpointWriteTime = row[/* unknown nullability */ Option[Double]]("checkpoint_write_time"),
        checkpointSyncTime = row[/* unknown nullability */ Option[Double]]("checkpoint_sync_time"),
        buffersCheckpoint = row[/* unknown nullability */ Option[Long]]("buffers_checkpoint"),
        buffersClean = row[/* unknown nullability */ Option[Long]]("buffers_clean"),
        maxwrittenClean = row[/* unknown nullability */ Option[Long]]("maxwritten_clean"),
        buffersBackend = row[/* unknown nullability */ Option[Long]]("buffers_backend"),
        buffersBackendFsync = row[/* unknown nullability */ Option[Long]]("buffers_backend_fsync"),
        buffersAlloc = row[/* unknown nullability */ Option[Long]]("buffers_alloc"),
        statsReset = row[/* unknown nullability */ Option[LocalDateTime]]("stats_reset")
      )
    )
  }

  implicit val oFormat: OFormat[PgStatBgwriterRow] = new OFormat[PgStatBgwriterRow]{
    override def writes(o: PgStatBgwriterRow): JsObject =
      Json.obj(
        "checkpoints_timed" -> o.checkpointsTimed,
      "checkpoints_req" -> o.checkpointsReq,
      "checkpoint_write_time" -> o.checkpointWriteTime,
      "checkpoint_sync_time" -> o.checkpointSyncTime,
      "buffers_checkpoint" -> o.buffersCheckpoint,
      "buffers_clean" -> o.buffersClean,
      "maxwritten_clean" -> o.maxwrittenClean,
      "buffers_backend" -> o.buffersBackend,
      "buffers_backend_fsync" -> o.buffersBackendFsync,
      "buffers_alloc" -> o.buffersAlloc,
      "stats_reset" -> o.statsReset
      )

    override def reads(json: JsValue): JsResult[PgStatBgwriterRow] = {
      JsResult.fromTry(
        Try(
          PgStatBgwriterRow(
            checkpointsTimed = json.\("checkpoints_timed").toOption.map(_.as[Long]),
            checkpointsReq = json.\("checkpoints_req").toOption.map(_.as[Long]),
            checkpointWriteTime = json.\("checkpoint_write_time").toOption.map(_.as[Double]),
            checkpointSyncTime = json.\("checkpoint_sync_time").toOption.map(_.as[Double]),
            buffersCheckpoint = json.\("buffers_checkpoint").toOption.map(_.as[Long]),
            buffersClean = json.\("buffers_clean").toOption.map(_.as[Long]),
            maxwrittenClean = json.\("maxwritten_clean").toOption.map(_.as[Long]),
            buffersBackend = json.\("buffers_backend").toOption.map(_.as[Long]),
            buffersBackendFsync = json.\("buffers_backend_fsync").toOption.map(_.as[Long]),
            buffersAlloc = json.\("buffers_alloc").toOption.map(_.as[Long]),
            statsReset = json.\("stats_reset").toOption.map(_.as[LocalDateTime])
          )
        )
      )
    }
  }
}

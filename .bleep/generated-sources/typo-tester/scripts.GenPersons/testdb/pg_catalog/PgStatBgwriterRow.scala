package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import java.time.LocalDateTime
import play.api.libs.json.Json
import play.api.libs.json.OFormat

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

  implicit val oFormat: OFormat[PgStatBgwriterRow] = Json.format
}

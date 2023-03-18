package testdb
package postgres
package pg_catalog

import java.time.LocalDateTime

sealed abstract class PgStatBgwriterFieldValue[T](val name: String, val value: T)

object PgStatBgwriterFieldValue {
  case class checkpointsTimed(override val value: /* unknown nullability */ Option[Long]) extends PgStatBgwriterFieldValue("checkpoints_timed", value)
  case class checkpointsReq(override val value: /* unknown nullability */ Option[Long]) extends PgStatBgwriterFieldValue("checkpoints_req", value)
  case class checkpointWriteTime(override val value: /* unknown nullability */ Option[Double]) extends PgStatBgwriterFieldValue("checkpoint_write_time", value)
  case class checkpointSyncTime(override val value: /* unknown nullability */ Option[Double]) extends PgStatBgwriterFieldValue("checkpoint_sync_time", value)
  case class buffersCheckpoint(override val value: /* unknown nullability */ Option[Long]) extends PgStatBgwriterFieldValue("buffers_checkpoint", value)
  case class buffersClean(override val value: /* unknown nullability */ Option[Long]) extends PgStatBgwriterFieldValue("buffers_clean", value)
  case class maxwrittenClean(override val value: /* unknown nullability */ Option[Long]) extends PgStatBgwriterFieldValue("maxwritten_clean", value)
  case class buffersBackend(override val value: /* unknown nullability */ Option[Long]) extends PgStatBgwriterFieldValue("buffers_backend", value)
  case class buffersBackendFsync(override val value: /* unknown nullability */ Option[Long]) extends PgStatBgwriterFieldValue("buffers_backend_fsync", value)
  case class buffersAlloc(override val value: /* unknown nullability */ Option[Long]) extends PgStatBgwriterFieldValue("buffers_alloc", value)
  case class statsReset(override val value: /* unknown nullability */ Option[LocalDateTime]) extends PgStatBgwriterFieldValue("stats_reset", value)
}

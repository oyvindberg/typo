package testdb
package pg_catalog

import java.time.LocalDateTime

sealed abstract class PgStatDatabaseFieldValue[T](val name: String, val value: T)

object PgStatDatabaseFieldValue {
  case class datid(override val value: /* unknown nullability */ Option[Long]) extends PgStatDatabaseFieldValue("datid", value)
  case class datname(override val value: /* unknown nullability */ Option[String]) extends PgStatDatabaseFieldValue("datname", value)
  case class numbackends(override val value: /* unknown nullability */ Option[Int]) extends PgStatDatabaseFieldValue("numbackends", value)
  case class xactCommit(override val value: /* unknown nullability */ Option[Long]) extends PgStatDatabaseFieldValue("xact_commit", value)
  case class xactRollback(override val value: /* unknown nullability */ Option[Long]) extends PgStatDatabaseFieldValue("xact_rollback", value)
  case class blksRead(override val value: /* unknown nullability */ Option[Long]) extends PgStatDatabaseFieldValue("blks_read", value)
  case class blksHit(override val value: /* unknown nullability */ Option[Long]) extends PgStatDatabaseFieldValue("blks_hit", value)
  case class tupReturned(override val value: /* unknown nullability */ Option[Long]) extends PgStatDatabaseFieldValue("tup_returned", value)
  case class tupFetched(override val value: /* unknown nullability */ Option[Long]) extends PgStatDatabaseFieldValue("tup_fetched", value)
  case class tupInserted(override val value: /* unknown nullability */ Option[Long]) extends PgStatDatabaseFieldValue("tup_inserted", value)
  case class tupUpdated(override val value: /* unknown nullability */ Option[Long]) extends PgStatDatabaseFieldValue("tup_updated", value)
  case class tupDeleted(override val value: /* unknown nullability */ Option[Long]) extends PgStatDatabaseFieldValue("tup_deleted", value)
  case class conflicts(override val value: /* unknown nullability */ Option[Long]) extends PgStatDatabaseFieldValue("conflicts", value)
  case class tempFiles(override val value: /* unknown nullability */ Option[Long]) extends PgStatDatabaseFieldValue("temp_files", value)
  case class tempBytes(override val value: /* unknown nullability */ Option[Long]) extends PgStatDatabaseFieldValue("temp_bytes", value)
  case class deadlocks(override val value: /* unknown nullability */ Option[Long]) extends PgStatDatabaseFieldValue("deadlocks", value)
  case class checksumFailures(override val value: /* unknown nullability */ Option[Long]) extends PgStatDatabaseFieldValue("checksum_failures", value)
  case class checksumLastFailure(override val value: /* unknown nullability */ Option[LocalDateTime]) extends PgStatDatabaseFieldValue("checksum_last_failure", value)
  case class blkReadTime(override val value: /* unknown nullability */ Option[Double]) extends PgStatDatabaseFieldValue("blk_read_time", value)
  case class blkWriteTime(override val value: /* unknown nullability */ Option[Double]) extends PgStatDatabaseFieldValue("blk_write_time", value)
  case class sessionTime(override val value: /* unknown nullability */ Option[Double]) extends PgStatDatabaseFieldValue("session_time", value)
  case class activeTime(override val value: /* unknown nullability */ Option[Double]) extends PgStatDatabaseFieldValue("active_time", value)
  case class idleInTransactionTime(override val value: /* unknown nullability */ Option[Double]) extends PgStatDatabaseFieldValue("idle_in_transaction_time", value)
  case class sessions(override val value: /* unknown nullability */ Option[Long]) extends PgStatDatabaseFieldValue("sessions", value)
  case class sessionsAbandoned(override val value: /* unknown nullability */ Option[Long]) extends PgStatDatabaseFieldValue("sessions_abandoned", value)
  case class sessionsFatal(override val value: /* unknown nullability */ Option[Long]) extends PgStatDatabaseFieldValue("sessions_fatal", value)
  case class sessionsKilled(override val value: /* unknown nullability */ Option[Long]) extends PgStatDatabaseFieldValue("sessions_killed", value)
  case class statsReset(override val value: /* unknown nullability */ Option[LocalDateTime]) extends PgStatDatabaseFieldValue("stats_reset", value)
}

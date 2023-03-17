package testdb
package pg_catalog

import java.time.LocalDateTime

sealed abstract class PgStatArchiverFieldValue[T](val name: String, val value: T)

object PgStatArchiverFieldValue {
  case class archivedCount(override val value: /* unknown nullability */ Option[Long]) extends PgStatArchiverFieldValue("archived_count", value)
  case class lastArchivedWal(override val value: /* unknown nullability */ Option[String]) extends PgStatArchiverFieldValue("last_archived_wal", value)
  case class lastArchivedTime(override val value: /* unknown nullability */ Option[LocalDateTime]) extends PgStatArchiverFieldValue("last_archived_time", value)
  case class failedCount(override val value: /* unknown nullability */ Option[Long]) extends PgStatArchiverFieldValue("failed_count", value)
  case class lastFailedWal(override val value: /* unknown nullability */ Option[String]) extends PgStatArchiverFieldValue("last_failed_wal", value)
  case class lastFailedTime(override val value: /* unknown nullability */ Option[LocalDateTime]) extends PgStatArchiverFieldValue("last_failed_time", value)
  case class statsReset(override val value: /* unknown nullability */ Option[LocalDateTime]) extends PgStatArchiverFieldValue("stats_reset", value)
}

package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import java.time.LocalDateTime
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgStatArchiverRow(
  archivedCount: /* unknown nullability */ Option[Long],
  lastArchivedWal: /* unknown nullability */ Option[String],
  lastArchivedTime: /* unknown nullability */ Option[LocalDateTime],
  failedCount: /* unknown nullability */ Option[Long],
  lastFailedWal: /* unknown nullability */ Option[String],
  lastFailedTime: /* unknown nullability */ Option[LocalDateTime],
  statsReset: /* unknown nullability */ Option[LocalDateTime]
)

object PgStatArchiverRow {
  implicit val rowParser: RowParser[PgStatArchiverRow] = { row =>
    Success(
      PgStatArchiverRow(
        archivedCount = row[/* unknown nullability */ Option[Long]]("archived_count"),
        lastArchivedWal = row[/* unknown nullability */ Option[String]]("last_archived_wal"),
        lastArchivedTime = row[/* unknown nullability */ Option[LocalDateTime]]("last_archived_time"),
        failedCount = row[/* unknown nullability */ Option[Long]]("failed_count"),
        lastFailedWal = row[/* unknown nullability */ Option[String]]("last_failed_wal"),
        lastFailedTime = row[/* unknown nullability */ Option[LocalDateTime]]("last_failed_time"),
        statsReset = row[/* unknown nullability */ Option[LocalDateTime]]("stats_reset")
      )
    )
  }

  implicit val oFormat: OFormat[PgStatArchiverRow] = Json.format
}

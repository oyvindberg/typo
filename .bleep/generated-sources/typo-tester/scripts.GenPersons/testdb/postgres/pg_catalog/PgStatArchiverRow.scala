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

  implicit val oFormat: OFormat[PgStatArchiverRow] = new OFormat[PgStatArchiverRow]{
    override def writes(o: PgStatArchiverRow): JsObject =
      Json.obj(
        "archived_count" -> o.archivedCount,
      "last_archived_wal" -> o.lastArchivedWal,
      "last_archived_time" -> o.lastArchivedTime,
      "failed_count" -> o.failedCount,
      "last_failed_wal" -> o.lastFailedWal,
      "last_failed_time" -> o.lastFailedTime,
      "stats_reset" -> o.statsReset
      )

    override def reads(json: JsValue): JsResult[PgStatArchiverRow] = {
      JsResult.fromTry(
        Try(
          PgStatArchiverRow(
            archivedCount = json.\("archived_count").toOption.map(_.as[Long]),
            lastArchivedWal = json.\("last_archived_wal").toOption.map(_.as[String]),
            lastArchivedTime = json.\("last_archived_time").toOption.map(_.as[LocalDateTime]),
            failedCount = json.\("failed_count").toOption.map(_.as[Long]),
            lastFailedWal = json.\("last_failed_wal").toOption.map(_.as[String]),
            lastFailedTime = json.\("last_failed_time").toOption.map(_.as[LocalDateTime]),
            statsReset = json.\("stats_reset").toOption.map(_.as[LocalDateTime])
          )
        )
      )
    }
  }
}

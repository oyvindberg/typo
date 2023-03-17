package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import java.time.LocalDateTime
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgStatSlruRow(
  name: /* unknown nullability */ Option[String],
  blksZeroed: /* unknown nullability */ Option[Long],
  blksHit: /* unknown nullability */ Option[Long],
  blksRead: /* unknown nullability */ Option[Long],
  blksWritten: /* unknown nullability */ Option[Long],
  blksExists: /* unknown nullability */ Option[Long],
  flushes: /* unknown nullability */ Option[Long],
  truncates: /* unknown nullability */ Option[Long],
  statsReset: /* unknown nullability */ Option[LocalDateTime]
)

object PgStatSlruRow {
  implicit val rowParser: RowParser[PgStatSlruRow] = { row =>
    Success(
      PgStatSlruRow(
        name = row[/* unknown nullability */ Option[String]]("name"),
        blksZeroed = row[/* unknown nullability */ Option[Long]]("blks_zeroed"),
        blksHit = row[/* unknown nullability */ Option[Long]]("blks_hit"),
        blksRead = row[/* unknown nullability */ Option[Long]]("blks_read"),
        blksWritten = row[/* unknown nullability */ Option[Long]]("blks_written"),
        blksExists = row[/* unknown nullability */ Option[Long]]("blks_exists"),
        flushes = row[/* unknown nullability */ Option[Long]]("flushes"),
        truncates = row[/* unknown nullability */ Option[Long]]("truncates"),
        statsReset = row[/* unknown nullability */ Option[LocalDateTime]]("stats_reset")
      )
    )
  }

  implicit val oFormat: OFormat[PgStatSlruRow] = Json.format
}

package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import java.time.LocalDateTime
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

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

  implicit val oFormat: OFormat[PgStatSlruRow] = new OFormat[PgStatSlruRow]{
    override def writes(o: PgStatSlruRow): JsObject =
      Json.obj(
        "name" -> o.name,
      "blks_zeroed" -> o.blksZeroed,
      "blks_hit" -> o.blksHit,
      "blks_read" -> o.blksRead,
      "blks_written" -> o.blksWritten,
      "blks_exists" -> o.blksExists,
      "flushes" -> o.flushes,
      "truncates" -> o.truncates,
      "stats_reset" -> o.statsReset
      )

    override def reads(json: JsValue): JsResult[PgStatSlruRow] = {
      JsResult.fromTry(
        Try(
          PgStatSlruRow(
            name = json.\("name").toOption.map(_.as[String]),
            blksZeroed = json.\("blks_zeroed").toOption.map(_.as[Long]),
            blksHit = json.\("blks_hit").toOption.map(_.as[Long]),
            blksRead = json.\("blks_read").toOption.map(_.as[Long]),
            blksWritten = json.\("blks_written").toOption.map(_.as[Long]),
            blksExists = json.\("blks_exists").toOption.map(_.as[Long]),
            flushes = json.\("flushes").toOption.map(_.as[Long]),
            truncates = json.\("truncates").toOption.map(_.as[Long]),
            statsReset = json.\("stats_reset").toOption.map(_.as[LocalDateTime])
          )
        )
      )
    }
  }
}

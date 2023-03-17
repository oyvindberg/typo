package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgSequencesRow(
  schemaname: String,
  sequencename: String,
  sequenceowner: /* unknown nullability */ Option[String],
  dataType: /* unknown nullability */ Option[/* regtype */ String],
  startValue: Long,
  minValue: Long,
  maxValue: Long,
  incrementBy: Long,
  cycle: Boolean,
  cacheSize: Long,
  lastValue: /* unknown nullability */ Option[Long]
)

object PgSequencesRow {
  implicit val rowParser: RowParser[PgSequencesRow] = { row =>
    Success(
      PgSequencesRow(
        schemaname = row[String]("schemaname"),
        sequencename = row[String]("sequencename"),
        sequenceowner = row[/* unknown nullability */ Option[String]]("sequenceowner"),
        dataType = row[/* unknown nullability */ Option[/* regtype */ String]]("data_type"),
        startValue = row[Long]("start_value"),
        minValue = row[Long]("min_value"),
        maxValue = row[Long]("max_value"),
        incrementBy = row[Long]("increment_by"),
        cycle = row[Boolean]("cycle"),
        cacheSize = row[Long]("cache_size"),
        lastValue = row[/* unknown nullability */ Option[Long]]("last_value")
      )
    )
  }

  implicit val oFormat: OFormat[PgSequencesRow] = Json.format
}

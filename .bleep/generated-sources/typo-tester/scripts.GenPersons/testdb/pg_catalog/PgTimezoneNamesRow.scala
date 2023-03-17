package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import org.postgresql.util.PGInterval
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgTimezoneNamesRow(
  name: /* unknown nullability */ Option[String],
  abbrev: /* unknown nullability */ Option[String],
  utcOffset: /* unknown nullability */ Option[/* interval */ PGInterval],
  isDst: /* unknown nullability */ Option[Boolean]
)

object PgTimezoneNamesRow {
  implicit val rowParser: RowParser[PgTimezoneNamesRow] = { row =>
    Success(
      PgTimezoneNamesRow(
        name = row[/* unknown nullability */ Option[String]]("name"),
        abbrev = row[/* unknown nullability */ Option[String]]("abbrev"),
        utcOffset = row[/* unknown nullability */ Option[/* interval */ PGInterval]]("utc_offset"),
        isDst = row[/* unknown nullability */ Option[Boolean]]("is_dst")
      )
    )
  }

  implicit val oFormat: OFormat[PgTimezoneNamesRow] = Json.format
}

package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import org.postgresql.util.PGInterval
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgTimezoneAbbrevsRow(
  abbrev: /* unknown nullability */ Option[String],
  utcOffset: /* unknown nullability */ Option[/* interval */ PGInterval],
  isDst: /* unknown nullability */ Option[Boolean]
)

object PgTimezoneAbbrevsRow {
  implicit val rowParser: RowParser[PgTimezoneAbbrevsRow] = { row =>
    Success(
      PgTimezoneAbbrevsRow(
        abbrev = row[/* unknown nullability */ Option[String]]("abbrev"),
        utcOffset = row[/* unknown nullability */ Option[/* interval */ PGInterval]]("utc_offset"),
        isDst = row[/* unknown nullability */ Option[Boolean]]("is_dst")
      )
    )
  }

  implicit val oFormat: OFormat[PgTimezoneAbbrevsRow] = Json.format
}

package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgConfigRow(
  name: /* unknown nullability */ Option[String],
  setting: /* unknown nullability */ Option[String]
)

object PgConfigRow {
  implicit val rowParser: RowParser[PgConfigRow] = { row =>
    Success(
      PgConfigRow(
        name = row[/* unknown nullability */ Option[String]]("name"),
        setting = row[/* unknown nullability */ Option[String]]("setting")
      )
    )
  }

  implicit val oFormat: OFormat[PgConfigRow] = Json.format
}

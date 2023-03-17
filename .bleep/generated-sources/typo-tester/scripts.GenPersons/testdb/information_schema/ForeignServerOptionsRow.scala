package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class ForeignServerOptionsRow(
  foreignServerCatalog: Option[String],
  foreignServerName: Option[String],
  optionName: /* unknown nullability */ Option[String],
  optionValue: /* unknown nullability */ Option[String]
)

object ForeignServerOptionsRow {
  implicit val rowParser: RowParser[ForeignServerOptionsRow] = { row =>
    Success(
      ForeignServerOptionsRow(
        foreignServerCatalog = row[Option[String]]("foreign_server_catalog"),
        foreignServerName = row[Option[String]]("foreign_server_name"),
        optionName = row[/* unknown nullability */ Option[String]]("option_name"),
        optionValue = row[/* unknown nullability */ Option[String]]("option_value")
      )
    )
  }

  implicit val oFormat: OFormat[ForeignServerOptionsRow] = Json.format
}

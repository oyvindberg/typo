package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class ForeignDataWrapperOptionsRow(
  foreignDataWrapperCatalog: Option[String],
  foreignDataWrapperName: Option[String],
  optionName: /* unknown nullability */ Option[String],
  optionValue: /* unknown nullability */ Option[String]
)

object ForeignDataWrapperOptionsRow {
  implicit val rowParser: RowParser[ForeignDataWrapperOptionsRow] = { row =>
    Success(
      ForeignDataWrapperOptionsRow(
        foreignDataWrapperCatalog = row[Option[String]]("foreign_data_wrapper_catalog"),
        foreignDataWrapperName = row[Option[String]]("foreign_data_wrapper_name"),
        optionName = row[/* unknown nullability */ Option[String]]("option_name"),
        optionValue = row[/* unknown nullability */ Option[String]]("option_value")
      )
    )
  }

  implicit val oFormat: OFormat[ForeignDataWrapperOptionsRow] = Json.format
}

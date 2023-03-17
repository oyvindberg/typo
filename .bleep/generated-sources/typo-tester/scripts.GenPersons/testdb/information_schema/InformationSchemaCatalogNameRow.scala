package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class InformationSchemaCatalogNameRow(
  catalogName: /* unknown nullability */ Option[String]
)

object InformationSchemaCatalogNameRow {
  implicit val rowParser: RowParser[InformationSchemaCatalogNameRow] = { row =>
    Success(
      InformationSchemaCatalogNameRow(
        catalogName = row[/* unknown nullability */ Option[String]]("catalog_name")
      )
    )
  }

  implicit val oFormat: OFormat[InformationSchemaCatalogNameRow] = Json.format
}

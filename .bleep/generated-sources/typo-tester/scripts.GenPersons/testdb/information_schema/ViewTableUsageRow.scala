package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class ViewTableUsageRow(
  viewCatalog: /* unknown nullability */ Option[String],
  viewSchema: /* unknown nullability */ Option[String],
  viewName: /* unknown nullability */ Option[String],
  tableCatalog: /* unknown nullability */ Option[String],
  tableSchema: /* unknown nullability */ Option[String],
  tableName: /* unknown nullability */ Option[String]
)

object ViewTableUsageRow {
  implicit val rowParser: RowParser[ViewTableUsageRow] = { row =>
    Success(
      ViewTableUsageRow(
        viewCatalog = row[/* unknown nullability */ Option[String]]("view_catalog"),
        viewSchema = row[/* unknown nullability */ Option[String]]("view_schema"),
        viewName = row[/* unknown nullability */ Option[String]]("view_name"),
        tableCatalog = row[/* unknown nullability */ Option[String]]("table_catalog"),
        tableSchema = row[/* unknown nullability */ Option[String]]("table_schema"),
        tableName = row[/* unknown nullability */ Option[String]]("table_name")
      )
    )
  }

  implicit val oFormat: OFormat[ViewTableUsageRow] = Json.format
}

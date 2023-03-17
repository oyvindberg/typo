package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class ColumnColumnUsageRow(
  tableCatalog: /* unknown nullability */ Option[String],
  tableSchema: /* unknown nullability */ Option[String],
  tableName: /* unknown nullability */ Option[String],
  columnName: /* unknown nullability */ Option[String],
  dependentColumn: /* unknown nullability */ Option[String]
)

object ColumnColumnUsageRow {
  implicit val rowParser: RowParser[ColumnColumnUsageRow] = { row =>
    Success(
      ColumnColumnUsageRow(
        tableCatalog = row[/* unknown nullability */ Option[String]]("table_catalog"),
        tableSchema = row[/* unknown nullability */ Option[String]]("table_schema"),
        tableName = row[/* unknown nullability */ Option[String]]("table_name"),
        columnName = row[/* unknown nullability */ Option[String]]("column_name"),
        dependentColumn = row[/* unknown nullability */ Option[String]]("dependent_column")
      )
    )
  }

  implicit val oFormat: OFormat[ColumnColumnUsageRow] = Json.format
}

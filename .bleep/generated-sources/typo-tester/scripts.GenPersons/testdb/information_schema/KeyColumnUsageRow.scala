package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class KeyColumnUsageRow(
  constraintCatalog: /* unknown nullability */ Option[String],
  constraintSchema: /* unknown nullability */ Option[String],
  constraintName: /* unknown nullability */ Option[String],
  tableCatalog: /* unknown nullability */ Option[String],
  tableSchema: /* unknown nullability */ Option[String],
  tableName: /* unknown nullability */ Option[String],
  columnName: /* unknown nullability */ Option[String],
  ordinalPosition: /* unknown nullability */ Option[Int],
  positionInUniqueConstraint: /* unknown nullability */ Option[Int]
)

object KeyColumnUsageRow {
  implicit val rowParser: RowParser[KeyColumnUsageRow] = { row =>
    Success(
      KeyColumnUsageRow(
        constraintCatalog = row[/* unknown nullability */ Option[String]]("constraint_catalog"),
        constraintSchema = row[/* unknown nullability */ Option[String]]("constraint_schema"),
        constraintName = row[/* unknown nullability */ Option[String]]("constraint_name"),
        tableCatalog = row[/* unknown nullability */ Option[String]]("table_catalog"),
        tableSchema = row[/* unknown nullability */ Option[String]]("table_schema"),
        tableName = row[/* unknown nullability */ Option[String]]("table_name"),
        columnName = row[/* unknown nullability */ Option[String]]("column_name"),
        ordinalPosition = row[/* unknown nullability */ Option[Int]]("ordinal_position"),
        positionInUniqueConstraint = row[/* unknown nullability */ Option[Int]]("position_in_unique_constraint")
      )
    )
  }

  implicit val oFormat: OFormat[KeyColumnUsageRow] = Json.format
}

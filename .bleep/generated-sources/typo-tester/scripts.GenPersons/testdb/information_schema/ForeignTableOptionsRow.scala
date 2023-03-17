package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class ForeignTableOptionsRow(
  /** Points to [[testdb.information_schema.PgForeignTablesRow.foreignTableCatalog]] */
  foreignTableCatalog: Option[String],
  /** Points to [[testdb.information_schema.PgForeignTablesRow.foreignTableSchema]] */
  foreignTableSchema: Option[String],
  /** Points to [[testdb.information_schema.PgForeignTablesRow.foreignTableName]] */
  foreignTableName: Option[String],
  optionName: /* unknown nullability */ Option[String],
  optionValue: /* unknown nullability */ Option[String]
)

object ForeignTableOptionsRow {
  implicit val rowParser: RowParser[ForeignTableOptionsRow] = { row =>
    Success(
      ForeignTableOptionsRow(
        foreignTableCatalog = row[Option[String]]("foreign_table_catalog"),
        foreignTableSchema = row[Option[String]]("foreign_table_schema"),
        foreignTableName = row[Option[String]]("foreign_table_name"),
        optionName = row[/* unknown nullability */ Option[String]]("option_name"),
        optionValue = row[/* unknown nullability */ Option[String]]("option_value")
      )
    )
  }

  implicit val oFormat: OFormat[ForeignTableOptionsRow] = Json.format
}

package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class ForeignTablesRow(
  /** Points to [[testdb.information_schema.PgForeignTablesRow.foreignTableCatalog]] */
  foreignTableCatalog: Option[String],
  /** Points to [[testdb.information_schema.PgForeignTablesRow.foreignTableSchema]] */
  foreignTableSchema: Option[String],
  /** Points to [[testdb.information_schema.PgForeignTablesRow.foreignTableName]] */
  foreignTableName: Option[String],
  /** Points to [[testdb.information_schema.PgForeignTablesRow.foreignServerCatalog]] */
  foreignServerCatalog: Option[String],
  /** Points to [[testdb.information_schema.PgForeignTablesRow.foreignServerName]] */
  foreignServerName: Option[String]
)

object ForeignTablesRow {
  implicit val rowParser: RowParser[ForeignTablesRow] = { row =>
    Success(
      ForeignTablesRow(
        foreignTableCatalog = row[Option[String]]("foreign_table_catalog"),
        foreignTableSchema = row[Option[String]]("foreign_table_schema"),
        foreignTableName = row[Option[String]]("foreign_table_name"),
        foreignServerCatalog = row[Option[String]]("foreign_server_catalog"),
        foreignServerName = row[Option[String]]("foreign_server_name")
      )
    )
  }

  implicit val oFormat: OFormat[ForeignTablesRow] = Json.format
}

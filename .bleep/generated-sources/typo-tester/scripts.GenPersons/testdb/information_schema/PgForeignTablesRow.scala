package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgForeignTablesRow(
  foreignTableCatalog: /* unknown nullability */ Option[String],
  foreignTableSchema: /* unknown nullability */ Option[String],
  foreignTableName: /* unknown nullability */ Option[String],
  /** Points to [[testdb.pg_catalog.PgForeignTableRow.ftoptions]] */
  ftoptions: Option[Array[String]],
  foreignServerCatalog: /* unknown nullability */ Option[String],
  foreignServerName: /* unknown nullability */ Option[String],
  authorizationIdentifier: /* unknown nullability */ Option[String]
)

object PgForeignTablesRow {
  implicit val rowParser: RowParser[PgForeignTablesRow] = { row =>
    Success(
      PgForeignTablesRow(
        foreignTableCatalog = row[/* unknown nullability */ Option[String]]("foreign_table_catalog"),
        foreignTableSchema = row[/* unknown nullability */ Option[String]]("foreign_table_schema"),
        foreignTableName = row[/* unknown nullability */ Option[String]]("foreign_table_name"),
        ftoptions = row[Option[Array[String]]]("ftoptions"),
        foreignServerCatalog = row[/* unknown nullability */ Option[String]]("foreign_server_catalog"),
        foreignServerName = row[/* unknown nullability */ Option[String]]("foreign_server_name"),
        authorizationIdentifier = row[/* unknown nullability */ Option[String]]("authorization_identifier")
      )
    )
  }

  implicit val oFormat: OFormat[PgForeignTablesRow] = Json.format
}

package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class ForeignServersRow(
  /** Points to [[testdb.information_schema.PgForeignServersRow.foreignServerCatalog]] */
  foreignServerCatalog: Option[String],
  /** Points to [[testdb.information_schema.PgForeignServersRow.foreignServerName]] */
  foreignServerName: Option[String],
  /** Points to [[testdb.information_schema.PgForeignServersRow.foreignDataWrapperCatalog]] */
  foreignDataWrapperCatalog: Option[String],
  /** Points to [[testdb.information_schema.PgForeignServersRow.foreignDataWrapperName]] */
  foreignDataWrapperName: Option[String],
  /** Points to [[testdb.information_schema.PgForeignServersRow.foreignServerType]] */
  foreignServerType: Option[String],
  /** Points to [[testdb.information_schema.PgForeignServersRow.foreignServerVersion]] */
  foreignServerVersion: Option[String],
  /** Points to [[testdb.information_schema.PgForeignServersRow.authorizationIdentifier]] */
  authorizationIdentifier: Option[String]
)

object ForeignServersRow {
  implicit val rowParser: RowParser[ForeignServersRow] = { row =>
    Success(
      ForeignServersRow(
        foreignServerCatalog = row[Option[String]]("foreign_server_catalog"),
        foreignServerName = row[Option[String]]("foreign_server_name"),
        foreignDataWrapperCatalog = row[Option[String]]("foreign_data_wrapper_catalog"),
        foreignDataWrapperName = row[Option[String]]("foreign_data_wrapper_name"),
        foreignServerType = row[Option[String]]("foreign_server_type"),
        foreignServerVersion = row[Option[String]]("foreign_server_version"),
        authorizationIdentifier = row[Option[String]]("authorization_identifier")
      )
    )
  }

  implicit val oFormat: OFormat[ForeignServersRow] = Json.format
}

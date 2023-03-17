package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class ForeignServersRow(
  foreignServerCatalog: Option[String],
  foreignServerName: Option[String],
  foreignDataWrapperCatalog: Option[String],
  foreignDataWrapperName: Option[String],
  foreignServerType: Option[String],
  foreignServerVersion: Option[String],
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

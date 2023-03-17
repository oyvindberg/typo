package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class UserMappingsRow(
  /** Points to [[testdb.information_schema.PgUserMappingsRow.authorizationIdentifier]] */
  authorizationIdentifier: Option[String],
  /** Points to [[testdb.information_schema.PgUserMappingsRow.foreignServerCatalog]] */
  foreignServerCatalog: Option[String],
  /** Points to [[testdb.information_schema.PgUserMappingsRow.foreignServerName]] */
  foreignServerName: Option[String]
)

object UserMappingsRow {
  implicit val rowParser: RowParser[UserMappingsRow] = { row =>
    Success(
      UserMappingsRow(
        authorizationIdentifier = row[Option[String]]("authorization_identifier"),
        foreignServerCatalog = row[Option[String]]("foreign_server_catalog"),
        foreignServerName = row[Option[String]]("foreign_server_name")
      )
    )
  }

  implicit val oFormat: OFormat[UserMappingsRow] = Json.format
}

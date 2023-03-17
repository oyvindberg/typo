package testdb
package information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

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

  implicit val oFormat: OFormat[UserMappingsRow] = new OFormat[UserMappingsRow]{
    override def writes(o: UserMappingsRow): JsObject =
      Json.obj(
        "authorization_identifier" -> o.authorizationIdentifier,
      "foreign_server_catalog" -> o.foreignServerCatalog,
      "foreign_server_name" -> o.foreignServerName
      )

    override def reads(json: JsValue): JsResult[UserMappingsRow] = {
      JsResult.fromTry(
        Try(
          UserMappingsRow(
            authorizationIdentifier = json.\("authorization_identifier").toOption.map(_.as[String]),
            foreignServerCatalog = json.\("foreign_server_catalog").toOption.map(_.as[String]),
            foreignServerName = json.\("foreign_server_name").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}

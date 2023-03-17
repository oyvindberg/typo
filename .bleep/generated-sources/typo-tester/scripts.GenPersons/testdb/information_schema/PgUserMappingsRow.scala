package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgUserMappingsRow(
  oid: Long,
  umoptions: Option[Array[String]],
  umuser: Long,
  authorizationIdentifier: /* unknown nullability */ Option[String],
  foreignServerCatalog: Option[String],
  foreignServerName: Option[String],
  srvowner: Option[String]
)

object PgUserMappingsRow {
  implicit val rowParser: RowParser[PgUserMappingsRow] = { row =>
    Success(
      PgUserMappingsRow(
        oid = row[Long]("oid"),
        umoptions = row[Option[Array[String]]]("umoptions"),
        umuser = row[Long]("umuser"),
        authorizationIdentifier = row[/* unknown nullability */ Option[String]]("authorization_identifier"),
        foreignServerCatalog = row[Option[String]]("foreign_server_catalog"),
        foreignServerName = row[Option[String]]("foreign_server_name"),
        srvowner = row[Option[String]]("srvowner")
      )
    )
  }

  implicit val oFormat: OFormat[PgUserMappingsRow] = Json.format
}

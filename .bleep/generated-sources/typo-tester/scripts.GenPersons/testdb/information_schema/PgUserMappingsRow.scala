package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgUserMappingsRow(
  /** Points to [[testdb.pg_catalog.PgUserMappingRow.oid]] */
  oid: Long,
  /** Points to [[testdb.pg_catalog.PgUserMappingRow.umoptions]] */
  umoptions: Option[Array[String]],
  /** Points to [[testdb.pg_catalog.PgUserMappingRow.umuser]] */
  umuser: Long,
  authorizationIdentifier: /* unknown nullability */ Option[String],
  /** Points to [[testdb.information_schema.PgForeignServersRow.foreignServerCatalog]] */
  foreignServerCatalog: Option[String],
  /** Points to [[testdb.information_schema.PgForeignServersRow.foreignServerName]] */
  foreignServerName: Option[String],
  /** Points to [[testdb.information_schema.PgForeignServersRow.authorizationIdentifier]] */
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

  implicit val oFormat: OFormat[PgUserMappingsRow] = new OFormat[PgUserMappingsRow]{
    override def writes(o: PgUserMappingsRow): JsObject =
      Json.obj(
        "oid" -> o.oid,
      "umoptions" -> o.umoptions,
      "umuser" -> o.umuser,
      "authorization_identifier" -> o.authorizationIdentifier,
      "foreign_server_catalog" -> o.foreignServerCatalog,
      "foreign_server_name" -> o.foreignServerName,
      "srvowner" -> o.srvowner
      )

    override def reads(json: JsValue): JsResult[PgUserMappingsRow] = {
      JsResult.fromTry(
        Try(
          PgUserMappingsRow(
            oid = json.\("oid").as[Long],
            umoptions = json.\("umoptions").toOption.map(_.as[Array[String]]),
            umuser = json.\("umuser").as[Long],
            authorizationIdentifier = json.\("authorization_identifier").toOption.map(_.as[String]),
            foreignServerCatalog = json.\("foreign_server_catalog").toOption.map(_.as[String]),
            foreignServerName = json.\("foreign_server_name").toOption.map(_.as[String]),
            srvowner = json.\("srvowner").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}

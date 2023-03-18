package testdb
package postgres
package information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try
import testdb.postgres.pg_catalog.PgForeignServerRow

case class PgForeignServersRow(
  /** Points to [[PgForeignServerRow.oid]] */
  oid: Long,
  /** Points to [[PgForeignServerRow.srvoptions]] */
  srvoptions: Option[Array[String]],
  foreignServerCatalog: /* unknown nullability */ Option[String],
  foreignServerName: /* unknown nullability */ Option[String],
  foreignDataWrapperCatalog: /* unknown nullability */ Option[String],
  foreignDataWrapperName: /* unknown nullability */ Option[String],
  foreignServerType: /* unknown nullability */ Option[String],
  foreignServerVersion: /* unknown nullability */ Option[String],
  authorizationIdentifier: /* unknown nullability */ Option[String]
)

object PgForeignServersRow {
  implicit val rowParser: RowParser[PgForeignServersRow] = { row =>
    Success(
      PgForeignServersRow(
        oid = row[Long]("oid"),
        srvoptions = row[Option[Array[String]]]("srvoptions"),
        foreignServerCatalog = row[/* unknown nullability */ Option[String]]("foreign_server_catalog"),
        foreignServerName = row[/* unknown nullability */ Option[String]]("foreign_server_name"),
        foreignDataWrapperCatalog = row[/* unknown nullability */ Option[String]]("foreign_data_wrapper_catalog"),
        foreignDataWrapperName = row[/* unknown nullability */ Option[String]]("foreign_data_wrapper_name"),
        foreignServerType = row[/* unknown nullability */ Option[String]]("foreign_server_type"),
        foreignServerVersion = row[/* unknown nullability */ Option[String]]("foreign_server_version"),
        authorizationIdentifier = row[/* unknown nullability */ Option[String]]("authorization_identifier")
      )
    )
  }

  implicit val oFormat: OFormat[PgForeignServersRow] = new OFormat[PgForeignServersRow]{
    override def writes(o: PgForeignServersRow): JsObject =
      Json.obj(
        "oid" -> o.oid,
      "srvoptions" -> o.srvoptions,
      "foreign_server_catalog" -> o.foreignServerCatalog,
      "foreign_server_name" -> o.foreignServerName,
      "foreign_data_wrapper_catalog" -> o.foreignDataWrapperCatalog,
      "foreign_data_wrapper_name" -> o.foreignDataWrapperName,
      "foreign_server_type" -> o.foreignServerType,
      "foreign_server_version" -> o.foreignServerVersion,
      "authorization_identifier" -> o.authorizationIdentifier
      )

    override def reads(json: JsValue): JsResult[PgForeignServersRow] = {
      JsResult.fromTry(
        Try(
          PgForeignServersRow(
            oid = json.\("oid").as[Long],
            srvoptions = json.\("srvoptions").toOption.map(_.as[Array[String]]),
            foreignServerCatalog = json.\("foreign_server_catalog").toOption.map(_.as[String]),
            foreignServerName = json.\("foreign_server_name").toOption.map(_.as[String]),
            foreignDataWrapperCatalog = json.\("foreign_data_wrapper_catalog").toOption.map(_.as[String]),
            foreignDataWrapperName = json.\("foreign_data_wrapper_name").toOption.map(_.as[String]),
            foreignServerType = json.\("foreign_server_type").toOption.map(_.as[String]),
            foreignServerVersion = json.\("foreign_server_version").toOption.map(_.as[String]),
            authorizationIdentifier = json.\("authorization_identifier").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}

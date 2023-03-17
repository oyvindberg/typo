package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgForeignServersRow(
  oid: Long,
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

  implicit val oFormat: OFormat[PgForeignServersRow] = Json.format
}

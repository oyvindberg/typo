package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgUserMappingsRow(
  umid: Long,
  srvid: Long,
  srvname: String,
  umuser: Long,
  usename: /* unknown nullability */ Option[String],
  umoptions: /* unknown nullability */ Option[Array[String]]
)

object PgUserMappingsRow {
  implicit val rowParser: RowParser[PgUserMappingsRow] = { row =>
    Success(
      PgUserMappingsRow(
        umid = row[Long]("umid"),
        srvid = row[Long]("srvid"),
        srvname = row[String]("srvname"),
        umuser = row[Long]("umuser"),
        usename = row[/* unknown nullability */ Option[String]]("usename"),
        umoptions = row[/* unknown nullability */ Option[Array[String]]]("umoptions")
      )
    )
  }

  implicit val oFormat: OFormat[PgUserMappingsRow] = Json.format
}

package testdb.pg_catalog

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
  umid: Long,
  /** Points to [[testdb.pg_catalog.PgForeignServerRow.oid]] */
  srvid: Long,
  /** Points to [[testdb.pg_catalog.PgForeignServerRow.srvname]] */
  srvname: String,
  /** Points to [[testdb.pg_catalog.PgUserMappingRow.umuser]] */
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

  implicit val oFormat: OFormat[PgUserMappingsRow] = new OFormat[PgUserMappingsRow]{
    override def writes(o: PgUserMappingsRow): JsObject =
      Json.obj(
        "umid" -> o.umid,
      "srvid" -> o.srvid,
      "srvname" -> o.srvname,
      "umuser" -> o.umuser,
      "usename" -> o.usename,
      "umoptions" -> o.umoptions
      )

    override def reads(json: JsValue): JsResult[PgUserMappingsRow] = {
      JsResult.fromTry(
        Try(
          PgUserMappingsRow(
            umid = json.\("umid").as[Long],
            srvid = json.\("srvid").as[Long],
            srvname = json.\("srvname").as[String],
            umuser = json.\("umuser").as[Long],
            usename = json.\("usename").toOption.map(_.as[String]),
            umoptions = json.\("umoptions").toOption.map(_.as[Array[String]])
          )
        )
      )
    }
  }
}

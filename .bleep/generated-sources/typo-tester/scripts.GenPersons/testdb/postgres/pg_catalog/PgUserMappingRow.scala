package testdb
package postgres
package pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgUserMappingRow(
  oid: PgUserMappingId,
  umuser: Long,
  umserver: Long,
  umoptions: Option[Array[String]]
)

object PgUserMappingRow {
  implicit val rowParser: RowParser[PgUserMappingRow] = { row =>
    Success(
      PgUserMappingRow(
        oid = row[PgUserMappingId]("oid"),
        umuser = row[Long]("umuser"),
        umserver = row[Long]("umserver"),
        umoptions = row[Option[Array[String]]]("umoptions")
      )
    )
  }

  implicit val oFormat: OFormat[PgUserMappingRow] = new OFormat[PgUserMappingRow]{
    override def writes(o: PgUserMappingRow): JsObject =
      Json.obj(
        "oid" -> o.oid,
      "umuser" -> o.umuser,
      "umserver" -> o.umserver,
      "umoptions" -> o.umoptions
      )

    override def reads(json: JsValue): JsResult[PgUserMappingRow] = {
      JsResult.fromTry(
        Try(
          PgUserMappingRow(
            oid = json.\("oid").as[PgUserMappingId],
            umuser = json.\("umuser").as[Long],
            umserver = json.\("umserver").as[Long],
            umoptions = json.\("umoptions").toOption.map(_.as[Array[String]])
          )
        )
      )
    }
  }
}

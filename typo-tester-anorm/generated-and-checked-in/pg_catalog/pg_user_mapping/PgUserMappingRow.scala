/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_user_mapping

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
  umuser: /* oid */ Long,
  umserver: /* oid */ Long,
  umoptions: Option[Array[String]]
)

object PgUserMappingRow {
  def rowParser(idx: Int): RowParser[PgUserMappingRow] =
    RowParser[PgUserMappingRow] { row =>
      Success(
        PgUserMappingRow(
          oid = row[PgUserMappingId](idx + 0),
          umuser = row[/* oid */ Long](idx + 1),
          umserver = row[/* oid */ Long](idx + 2),
          umoptions = row[Option[Array[String]]](idx + 3)
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
            umuser = json.\("umuser").as[/* oid */ Long],
            umserver = json.\("umserver").as[/* oid */ Long],
            umoptions = json.\("umoptions").toOption.map(_.as[Array[String]])
          )
        )
      )
    }
  }
}
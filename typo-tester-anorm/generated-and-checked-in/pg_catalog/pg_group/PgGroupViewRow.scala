/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_group

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgGroupViewRow(
  groname: Option[String],
  grosysid: Option[/* oid */ Long],
  grolist: Option[Array[/* oid */ Long]]
)

object PgGroupViewRow {
  def rowParser(idx: Int): RowParser[PgGroupViewRow] =
    RowParser[PgGroupViewRow] { row =>
      Success(
        PgGroupViewRow(
          groname = row[Option[String]](idx + 0),
          grosysid = row[Option[/* oid */ Long]](idx + 1),
          grolist = row[Option[Array[/* oid */ Long]]](idx + 2)
        )
      )
    }
  implicit val oFormat: OFormat[PgGroupViewRow] = new OFormat[PgGroupViewRow]{
    override def writes(o: PgGroupViewRow): JsObject =
      Json.obj(
        "groname" -> o.groname,
        "grosysid" -> o.grosysid,
        "grolist" -> o.grolist
      )
  
    override def reads(json: JsValue): JsResult[PgGroupViewRow] = {
      JsResult.fromTry(
        Try(
          PgGroupViewRow(
            groname = json.\("groname").toOption.map(_.as[String]),
            grosysid = json.\("grosysid").toOption.map(_.as[/* oid */ Long]),
            grolist = json.\("grolist").toOption.map(_.as[Array[/* oid */ Long]])
          )
        )
      )
    }
  }
}
package testdb
package postgres
package pg_catalog

import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgAmRowUnsaved(
  amname: String,
  amhandler: String,
  amtype: String
)
object PgAmRowUnsaved {
  implicit val oFormat: OFormat[PgAmRowUnsaved] = new OFormat[PgAmRowUnsaved]{
    override def writes(o: PgAmRowUnsaved): JsObject =
      Json.obj(
        "amname" -> o.amname,
      "amhandler" -> o.amhandler,
      "amtype" -> o.amtype
      )

    override def reads(json: JsValue): JsResult[PgAmRowUnsaved] = {
      JsResult.fromTry(
        Try(
          PgAmRowUnsaved(
            amname = json.\("amname").as[String],
            amhandler = json.\("amhandler").as[String],
            amtype = json.\("amtype").as[String]
          )
        )
      )
    }
  }
}

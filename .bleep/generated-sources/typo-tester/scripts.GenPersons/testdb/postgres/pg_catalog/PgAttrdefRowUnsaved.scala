package testdb
package postgres
package pg_catalog

import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgAttrdefRowUnsaved(
  adrelid: Long,
  adnum: Short,
  adbin: String
)
object PgAttrdefRowUnsaved {
  implicit val oFormat: OFormat[PgAttrdefRowUnsaved] = new OFormat[PgAttrdefRowUnsaved]{
    override def writes(o: PgAttrdefRowUnsaved): JsObject =
      Json.obj(
        "adrelid" -> o.adrelid,
      "adnum" -> o.adnum,
      "adbin" -> o.adbin
      )

    override def reads(json: JsValue): JsResult[PgAttrdefRowUnsaved] = {
      JsResult.fromTry(
        Try(
          PgAttrdefRowUnsaved(
            adrelid = json.\("adrelid").as[Long],
            adnum = json.\("adnum").as[Short],
            adbin = json.\("adbin").as[String]
          )
        )
      )
    }
  }
}

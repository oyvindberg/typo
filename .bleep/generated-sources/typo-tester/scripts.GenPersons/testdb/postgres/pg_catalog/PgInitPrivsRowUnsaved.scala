package testdb
package postgres
package pg_catalog

import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgInitPrivsRowUnsaved(
  privtype: String,
  initprivs: Array[String]
)
object PgInitPrivsRowUnsaved {
  implicit val oFormat: OFormat[PgInitPrivsRowUnsaved] = new OFormat[PgInitPrivsRowUnsaved]{
    override def writes(o: PgInitPrivsRowUnsaved): JsObject =
      Json.obj(
        "privtype" -> o.privtype,
      "initprivs" -> o.initprivs
      )

    override def reads(json: JsValue): JsResult[PgInitPrivsRowUnsaved] = {
      JsResult.fromTry(
        Try(
          PgInitPrivsRowUnsaved(
            privtype = json.\("privtype").as[String],
            initprivs = json.\("initprivs").as[Array[String]]
          )
        )
      )
    }
  }
}

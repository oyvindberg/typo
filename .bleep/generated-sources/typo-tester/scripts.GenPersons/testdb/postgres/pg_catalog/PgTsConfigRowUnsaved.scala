package testdb
package postgres
package pg_catalog

import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgTsConfigRowUnsaved(
  cfgname: String,
  cfgnamespace: Long,
  cfgowner: Long,
  cfgparser: Long
)
object PgTsConfigRowUnsaved {
  implicit val oFormat: OFormat[PgTsConfigRowUnsaved] = new OFormat[PgTsConfigRowUnsaved]{
    override def writes(o: PgTsConfigRowUnsaved): JsObject =
      Json.obj(
        "cfgname" -> o.cfgname,
      "cfgnamespace" -> o.cfgnamespace,
      "cfgowner" -> o.cfgowner,
      "cfgparser" -> o.cfgparser
      )

    override def reads(json: JsValue): JsResult[PgTsConfigRowUnsaved] = {
      JsResult.fromTry(
        Try(
          PgTsConfigRowUnsaved(
            cfgname = json.\("cfgname").as[String],
            cfgnamespace = json.\("cfgnamespace").as[Long],
            cfgowner = json.\("cfgowner").as[Long],
            cfgparser = json.\("cfgparser").as[Long]
          )
        )
      )
    }
  }
}

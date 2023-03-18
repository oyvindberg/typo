package testdb
package postgres
package pg_catalog

import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgRangeRowUnsaved(
  rngsubtype: Long,
  rngmultitypid: Long,
  rngcollation: Long,
  rngsubopc: Long,
  rngcanonical: String,
  rngsubdiff: String
)
object PgRangeRowUnsaved {
  implicit val oFormat: OFormat[PgRangeRowUnsaved] = new OFormat[PgRangeRowUnsaved]{
    override def writes(o: PgRangeRowUnsaved): JsObject =
      Json.obj(
        "rngsubtype" -> o.rngsubtype,
      "rngmultitypid" -> o.rngmultitypid,
      "rngcollation" -> o.rngcollation,
      "rngsubopc" -> o.rngsubopc,
      "rngcanonical" -> o.rngcanonical,
      "rngsubdiff" -> o.rngsubdiff
      )

    override def reads(json: JsValue): JsResult[PgRangeRowUnsaved] = {
      JsResult.fromTry(
        Try(
          PgRangeRowUnsaved(
            rngsubtype = json.\("rngsubtype").as[Long],
            rngmultitypid = json.\("rngmultitypid").as[Long],
            rngcollation = json.\("rngcollation").as[Long],
            rngsubopc = json.\("rngsubopc").as[Long],
            rngcanonical = json.\("rngcanonical").as[String],
            rngsubdiff = json.\("rngsubdiff").as[String]
          )
        )
      )
    }
  }
}

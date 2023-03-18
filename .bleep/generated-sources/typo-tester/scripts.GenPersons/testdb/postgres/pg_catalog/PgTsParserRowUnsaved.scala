package testdb
package postgres
package pg_catalog

import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgTsParserRowUnsaved(
  prsname: String,
  prsnamespace: Long,
  prsstart: String,
  prstoken: String,
  prsend: String,
  prsheadline: String,
  prslextype: String
)
object PgTsParserRowUnsaved {
  implicit val oFormat: OFormat[PgTsParserRowUnsaved] = new OFormat[PgTsParserRowUnsaved]{
    override def writes(o: PgTsParserRowUnsaved): JsObject =
      Json.obj(
        "prsname" -> o.prsname,
      "prsnamespace" -> o.prsnamespace,
      "prsstart" -> o.prsstart,
      "prstoken" -> o.prstoken,
      "prsend" -> o.prsend,
      "prsheadline" -> o.prsheadline,
      "prslextype" -> o.prslextype
      )

    override def reads(json: JsValue): JsResult[PgTsParserRowUnsaved] = {
      JsResult.fromTry(
        Try(
          PgTsParserRowUnsaved(
            prsname = json.\("prsname").as[String],
            prsnamespace = json.\("prsnamespace").as[Long],
            prsstart = json.\("prsstart").as[String],
            prstoken = json.\("prstoken").as[String],
            prsend = json.\("prsend").as[String],
            prsheadline = json.\("prsheadline").as[String],
            prslextype = json.\("prslextype").as[String]
          )
        )
      )
    }
  }
}

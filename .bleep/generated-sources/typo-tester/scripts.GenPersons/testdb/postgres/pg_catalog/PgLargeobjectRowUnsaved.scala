package testdb
package postgres
package pg_catalog

import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgLargeobjectRowUnsaved(
  data: String
)
object PgLargeobjectRowUnsaved {
  implicit val oFormat: OFormat[PgLargeobjectRowUnsaved] = new OFormat[PgLargeobjectRowUnsaved]{
    override def writes(o: PgLargeobjectRowUnsaved): JsObject =
      Json.obj(
        "data" -> o.data
      )

    override def reads(json: JsValue): JsResult[PgLargeobjectRowUnsaved] = {
      JsResult.fromTry(
        Try(
          PgLargeobjectRowUnsaved(
            data = json.\("data").as[String]
          )
        )
      )
    }
  }
}

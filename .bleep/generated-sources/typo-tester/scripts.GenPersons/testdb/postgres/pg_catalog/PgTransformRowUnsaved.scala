package testdb
package postgres
package pg_catalog

import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgTransformRowUnsaved(
  trftype: Long,
  trflang: Long,
  trffromsql: String,
  trftosql: String
)
object PgTransformRowUnsaved {
  implicit val oFormat: OFormat[PgTransformRowUnsaved] = new OFormat[PgTransformRowUnsaved]{
    override def writes(o: PgTransformRowUnsaved): JsObject =
      Json.obj(
        "trftype" -> o.trftype,
      "trflang" -> o.trflang,
      "trffromsql" -> o.trffromsql,
      "trftosql" -> o.trftosql
      )

    override def reads(json: JsValue): JsResult[PgTransformRowUnsaved] = {
      JsResult.fromTry(
        Try(
          PgTransformRowUnsaved(
            trftype = json.\("trftype").as[Long],
            trflang = json.\("trflang").as[Long],
            trffromsql = json.\("trffromsql").as[String],
            trftosql = json.\("trftosql").as[String]
          )
        )
      )
    }
  }
}

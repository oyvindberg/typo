package testdb
package postgres
package pg_catalog

import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgNamespaceRowUnsaved(
  nspname: String,
  nspowner: Long,
  nspacl: Option[Array[String]]
)
object PgNamespaceRowUnsaved {
  implicit val oFormat: OFormat[PgNamespaceRowUnsaved] = new OFormat[PgNamespaceRowUnsaved]{
    override def writes(o: PgNamespaceRowUnsaved): JsObject =
      Json.obj(
        "nspname" -> o.nspname,
      "nspowner" -> o.nspowner,
      "nspacl" -> o.nspacl
      )

    override def reads(json: JsValue): JsResult[PgNamespaceRowUnsaved] = {
      JsResult.fromTry(
        Try(
          PgNamespaceRowUnsaved(
            nspname = json.\("nspname").as[String],
            nspowner = json.\("nspowner").as[Long],
            nspacl = json.\("nspacl").toOption.map(_.as[Array[String]])
          )
        )
      )
    }
  }
}

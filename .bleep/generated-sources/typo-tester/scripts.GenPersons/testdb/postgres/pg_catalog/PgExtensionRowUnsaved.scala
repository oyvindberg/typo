package testdb
package postgres
package pg_catalog

import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgExtensionRowUnsaved(
  extname: String,
  extowner: Long,
  extnamespace: Long,
  extrelocatable: Boolean,
  extversion: String,
  extconfig: Option[Array[Long]],
  extcondition: Option[Array[String]]
)
object PgExtensionRowUnsaved {
  implicit val oFormat: OFormat[PgExtensionRowUnsaved] = new OFormat[PgExtensionRowUnsaved]{
    override def writes(o: PgExtensionRowUnsaved): JsObject =
      Json.obj(
        "extname" -> o.extname,
      "extowner" -> o.extowner,
      "extnamespace" -> o.extnamespace,
      "extrelocatable" -> o.extrelocatable,
      "extversion" -> o.extversion,
      "extconfig" -> o.extconfig,
      "extcondition" -> o.extcondition
      )

    override def reads(json: JsValue): JsResult[PgExtensionRowUnsaved] = {
      JsResult.fromTry(
        Try(
          PgExtensionRowUnsaved(
            extname = json.\("extname").as[String],
            extowner = json.\("extowner").as[Long],
            extnamespace = json.\("extnamespace").as[Long],
            extrelocatable = json.\("extrelocatable").as[Boolean],
            extversion = json.\("extversion").as[String],
            extconfig = json.\("extconfig").toOption.map(_.as[Array[Long]]),
            extcondition = json.\("extcondition").toOption.map(_.as[Array[String]])
          )
        )
      )
    }
  }
}

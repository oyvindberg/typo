package testdb
package postgres
package pg_catalog

import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgRewriteRowUnsaved(
  rulename: String,
  evClass: Long,
  evType: String,
  evEnabled: String,
  isInstead: Boolean,
  evQual: String,
  evAction: String
)
object PgRewriteRowUnsaved {
  implicit val oFormat: OFormat[PgRewriteRowUnsaved] = new OFormat[PgRewriteRowUnsaved]{
    override def writes(o: PgRewriteRowUnsaved): JsObject =
      Json.obj(
        "rulename" -> o.rulename,
      "ev_class" -> o.evClass,
      "ev_type" -> o.evType,
      "ev_enabled" -> o.evEnabled,
      "is_instead" -> o.isInstead,
      "ev_qual" -> o.evQual,
      "ev_action" -> o.evAction
      )

    override def reads(json: JsValue): JsResult[PgRewriteRowUnsaved] = {
      JsResult.fromTry(
        Try(
          PgRewriteRowUnsaved(
            rulename = json.\("rulename").as[String],
            evClass = json.\("ev_class").as[Long],
            evType = json.\("ev_type").as[String],
            evEnabled = json.\("ev_enabled").as[String],
            isInstead = json.\("is_instead").as[Boolean],
            evQual = json.\("ev_qual").as[String],
            evAction = json.\("ev_action").as[String]
          )
        )
      )
    }
  }
}

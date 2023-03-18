package testdb
package postgres
package pg_catalog

import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgSubscriptionRelRowUnsaved(
  srsubstate: String,
  srsublsn: Option[String]
)
object PgSubscriptionRelRowUnsaved {
  implicit val oFormat: OFormat[PgSubscriptionRelRowUnsaved] = new OFormat[PgSubscriptionRelRowUnsaved]{
    override def writes(o: PgSubscriptionRelRowUnsaved): JsObject =
      Json.obj(
        "srsubstate" -> o.srsubstate,
      "srsublsn" -> o.srsublsn
      )

    override def reads(json: JsValue): JsResult[PgSubscriptionRelRowUnsaved] = {
      JsResult.fromTry(
        Try(
          PgSubscriptionRelRowUnsaved(
            srsubstate = json.\("srsubstate").as[String],
            srsublsn = json.\("srsublsn").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}

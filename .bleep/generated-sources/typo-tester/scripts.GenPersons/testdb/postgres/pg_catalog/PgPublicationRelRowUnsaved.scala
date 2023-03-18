package testdb
package postgres
package pg_catalog

import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgPublicationRelRowUnsaved(
  prpubid: Long,
  prrelid: Long
)
object PgPublicationRelRowUnsaved {
  implicit val oFormat: OFormat[PgPublicationRelRowUnsaved] = new OFormat[PgPublicationRelRowUnsaved]{
    override def writes(o: PgPublicationRelRowUnsaved): JsObject =
      Json.obj(
        "prpubid" -> o.prpubid,
      "prrelid" -> o.prrelid
      )

    override def reads(json: JsValue): JsResult[PgPublicationRelRowUnsaved] = {
      JsResult.fromTry(
        Try(
          PgPublicationRelRowUnsaved(
            prpubid = json.\("prpubid").as[Long],
            prrelid = json.\("prrelid").as[Long]
          )
        )
      )
    }
  }
}

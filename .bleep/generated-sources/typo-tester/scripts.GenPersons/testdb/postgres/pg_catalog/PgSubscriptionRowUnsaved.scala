package testdb
package postgres
package pg_catalog

import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgSubscriptionRowUnsaved(
  subdbid: Long,
  subname: String,
  subowner: Long,
  subenabled: Boolean,
  subbinary: Boolean,
  substream: Boolean,
  subconninfo: String,
  subslotname: Option[String],
  subsynccommit: String,
  subpublications: Array[String]
)
object PgSubscriptionRowUnsaved {
  implicit val oFormat: OFormat[PgSubscriptionRowUnsaved] = new OFormat[PgSubscriptionRowUnsaved]{
    override def writes(o: PgSubscriptionRowUnsaved): JsObject =
      Json.obj(
        "subdbid" -> o.subdbid,
      "subname" -> o.subname,
      "subowner" -> o.subowner,
      "subenabled" -> o.subenabled,
      "subbinary" -> o.subbinary,
      "substream" -> o.substream,
      "subconninfo" -> o.subconninfo,
      "subslotname" -> o.subslotname,
      "subsynccommit" -> o.subsynccommit,
      "subpublications" -> o.subpublications
      )

    override def reads(json: JsValue): JsResult[PgSubscriptionRowUnsaved] = {
      JsResult.fromTry(
        Try(
          PgSubscriptionRowUnsaved(
            subdbid = json.\("subdbid").as[Long],
            subname = json.\("subname").as[String],
            subowner = json.\("subowner").as[Long],
            subenabled = json.\("subenabled").as[Boolean],
            subbinary = json.\("subbinary").as[Boolean],
            substream = json.\("substream").as[Boolean],
            subconninfo = json.\("subconninfo").as[String],
            subslotname = json.\("subslotname").toOption.map(_.as[String]),
            subsynccommit = json.\("subsynccommit").as[String],
            subpublications = json.\("subpublications").as[Array[String]]
          )
        )
      )
    }
  }
}

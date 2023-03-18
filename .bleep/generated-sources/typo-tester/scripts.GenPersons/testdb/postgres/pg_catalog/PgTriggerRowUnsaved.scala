package testdb
package postgres
package pg_catalog

import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgTriggerRowUnsaved(
  tgrelid: Long,
  tgparentid: Long,
  tgname: String,
  tgfoid: Long,
  tgtype: Short,
  tgenabled: String,
  tgisinternal: Boolean,
  tgconstrrelid: Long,
  tgconstrindid: Long,
  tgconstraint: Long,
  tgdeferrable: Boolean,
  tginitdeferred: Boolean,
  tgnargs: Short,
  tgattr: String,
  tgargs: String,
  tgqual: Option[String],
  tgoldtable: Option[String],
  tgnewtable: Option[String]
)
object PgTriggerRowUnsaved {
  implicit val oFormat: OFormat[PgTriggerRowUnsaved] = new OFormat[PgTriggerRowUnsaved]{
    override def writes(o: PgTriggerRowUnsaved): JsObject =
      Json.obj(
        "tgrelid" -> o.tgrelid,
      "tgparentid" -> o.tgparentid,
      "tgname" -> o.tgname,
      "tgfoid" -> o.tgfoid,
      "tgtype" -> o.tgtype,
      "tgenabled" -> o.tgenabled,
      "tgisinternal" -> o.tgisinternal,
      "tgconstrrelid" -> o.tgconstrrelid,
      "tgconstrindid" -> o.tgconstrindid,
      "tgconstraint" -> o.tgconstraint,
      "tgdeferrable" -> o.tgdeferrable,
      "tginitdeferred" -> o.tginitdeferred,
      "tgnargs" -> o.tgnargs,
      "tgattr" -> o.tgattr,
      "tgargs" -> o.tgargs,
      "tgqual" -> o.tgqual,
      "tgoldtable" -> o.tgoldtable,
      "tgnewtable" -> o.tgnewtable
      )

    override def reads(json: JsValue): JsResult[PgTriggerRowUnsaved] = {
      JsResult.fromTry(
        Try(
          PgTriggerRowUnsaved(
            tgrelid = json.\("tgrelid").as[Long],
            tgparentid = json.\("tgparentid").as[Long],
            tgname = json.\("tgname").as[String],
            tgfoid = json.\("tgfoid").as[Long],
            tgtype = json.\("tgtype").as[Short],
            tgenabled = json.\("tgenabled").as[String],
            tgisinternal = json.\("tgisinternal").as[Boolean],
            tgconstrrelid = json.\("tgconstrrelid").as[Long],
            tgconstrindid = json.\("tgconstrindid").as[Long],
            tgconstraint = json.\("tgconstraint").as[Long],
            tgdeferrable = json.\("tgdeferrable").as[Boolean],
            tginitdeferred = json.\("tginitdeferred").as[Boolean],
            tgnargs = json.\("tgnargs").as[Short],
            tgattr = json.\("tgattr").as[String],
            tgargs = json.\("tgargs").as[String],
            tgqual = json.\("tgqual").toOption.map(_.as[String]),
            tgoldtable = json.\("tgoldtable").toOption.map(_.as[String]),
            tgnewtable = json.\("tgnewtable").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}

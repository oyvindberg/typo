package testdb
package postgres
package pg_catalog

import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgAmopRowUnsaved(
  amopfamily: Long,
  amoplefttype: Long,
  amoprighttype: Long,
  amopstrategy: Short,
  amoppurpose: String,
  amopopr: Long,
  amopmethod: Long,
  amopsortfamily: Long
)
object PgAmopRowUnsaved {
  implicit val oFormat: OFormat[PgAmopRowUnsaved] = new OFormat[PgAmopRowUnsaved]{
    override def writes(o: PgAmopRowUnsaved): JsObject =
      Json.obj(
        "amopfamily" -> o.amopfamily,
      "amoplefttype" -> o.amoplefttype,
      "amoprighttype" -> o.amoprighttype,
      "amopstrategy" -> o.amopstrategy,
      "amoppurpose" -> o.amoppurpose,
      "amopopr" -> o.amopopr,
      "amopmethod" -> o.amopmethod,
      "amopsortfamily" -> o.amopsortfamily
      )

    override def reads(json: JsValue): JsResult[PgAmopRowUnsaved] = {
      JsResult.fromTry(
        Try(
          PgAmopRowUnsaved(
            amopfamily = json.\("amopfamily").as[Long],
            amoplefttype = json.\("amoplefttype").as[Long],
            amoprighttype = json.\("amoprighttype").as[Long],
            amopstrategy = json.\("amopstrategy").as[Short],
            amoppurpose = json.\("amoppurpose").as[String],
            amopopr = json.\("amopopr").as[Long],
            amopmethod = json.\("amopmethod").as[Long],
            amopsortfamily = json.\("amopsortfamily").as[Long]
          )
        )
      )
    }
  }
}

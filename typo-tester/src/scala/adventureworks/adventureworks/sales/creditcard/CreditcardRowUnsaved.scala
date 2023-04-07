/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package creditcard

import adventureworks.Defaulted
import java.time.LocalDateTime
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class CreditcardRowUnsaved(
  cardtype: String,
  cardnumber: String,
  expmonth: Int,
  expyear: Int,
  modifieddate: Defaulted[LocalDateTime]
)
object CreditcardRowUnsaved {
  implicit val oFormat: OFormat[CreditcardRowUnsaved] = new OFormat[CreditcardRowUnsaved]{
    override def writes(o: CreditcardRowUnsaved): JsObject =
      Json.obj(
        "cardtype" -> o.cardtype,
        "cardnumber" -> o.cardnumber,
        "expmonth" -> o.expmonth,
        "expyear" -> o.expyear,
        "modifieddate" -> o.modifieddate
      )
  
    override def reads(json: JsValue): JsResult[CreditcardRowUnsaved] = {
      JsResult.fromTry(
        Try(
          CreditcardRowUnsaved(
            cardtype = json.\("cardtype").as[String],
            cardnumber = json.\("cardnumber").as[String],
            expmonth = json.\("expmonth").as[Int],
            expyear = json.\("expyear").as[Int],
            modifieddate = json.\("modifieddate").as[Defaulted[LocalDateTime]]
          )
        )
      )
    }
  }
}
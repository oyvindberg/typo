/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package location

import adventureworks.Defaulted
import java.time.LocalDateTime
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class LocationRowUnsaved(
  name: String,
  costrate: Defaulted[BigDecimal],
  availability: Defaulted[BigDecimal],
  modifieddate: Defaulted[LocalDateTime]
)
object LocationRowUnsaved {
  implicit val oFormat: OFormat[LocationRowUnsaved] = new OFormat[LocationRowUnsaved]{
    override def writes(o: LocationRowUnsaved): JsObject =
      Json.obj(
        "name" -> o.name,
        "costrate" -> o.costrate,
        "availability" -> o.availability,
        "modifieddate" -> o.modifieddate
      )
  
    override def reads(json: JsValue): JsResult[LocationRowUnsaved] = {
      JsResult.fromTry(
        Try(
          LocationRowUnsaved(
            name = json.\("name").as[String],
            costrate = json.\("costrate").as[Defaulted[BigDecimal]],
            availability = json.\("availability").as[Defaulted[BigDecimal]],
            modifieddate = json.\("modifieddate").as[Defaulted[LocalDateTime]]
          )
        )
      )
    }
  }
}
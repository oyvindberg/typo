/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package salesterritoryhistory

import adventureworks.person.businessentity.BusinessentityId
import adventureworks.sales.salesterritory.SalesterritoryId
import anorm.RowParser
import anorm.Success
import java.time.LocalDateTime
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class SalesterritoryhistoryId(businessentityid: BusinessentityId, startdate: LocalDateTime, territoryid: SalesterritoryId)
object SalesterritoryhistoryId {
  implicit def ordering: Ordering[SalesterritoryhistoryId] = Ordering.by(x => (x.businessentityid, x.startdate, x.territoryid))
  implicit val oFormat: OFormat[SalesterritoryhistoryId] = new OFormat[SalesterritoryhistoryId]{
    override def writes(o: SalesterritoryhistoryId): JsObject =
      Json.obj(
        "businessentityid" -> o.businessentityid,
        "startdate" -> o.startdate,
        "territoryid" -> o.territoryid
      )
  
    override def reads(json: JsValue): JsResult[SalesterritoryhistoryId] = {
      JsResult.fromTry(
        Try(
          SalesterritoryhistoryId(
            businessentityid = json.\("businessentityid").as[BusinessentityId],
            startdate = json.\("startdate").as[LocalDateTime],
            territoryid = json.\("territoryid").as[SalesterritoryId]
          )
        )
      )
    }
  }
  def rowParser(prefix: String): RowParser[SalesterritoryhistoryId] = { row =>
    Success(
      SalesterritoryhistoryId(
        businessentityid = row[BusinessentityId](prefix + "businessentityid"),
        startdate = row[LocalDateTime](prefix + "startdate"),
        territoryid = row[SalesterritoryId](prefix + "territoryid")
      )
    )
  }

}
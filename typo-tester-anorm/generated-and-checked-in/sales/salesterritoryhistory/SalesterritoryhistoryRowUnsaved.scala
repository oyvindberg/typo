/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package salesterritoryhistory

import adventureworks.Defaulted
import adventureworks.person.businessentity.BusinessentityId
import adventureworks.sales.salesterritory.SalesterritoryId
import java.time.LocalDateTime
import java.util.UUID
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

/** This class corresponds to a row in table `sales.salesterritoryhistory` which has not been persisted yet */
case class SalesterritoryhistoryRowUnsaved(
  /** Primary key. The sales rep.  Foreign key to SalesPerson.BusinessEntityID.
      Points to [[salesperson.SalespersonRow.businessentityid]] */
  businessentityid: BusinessentityId,
  /** Primary key. Territory identification number. Foreign key to SalesTerritory.SalesTerritoryID.
      Points to [[salesterritory.SalesterritoryRow.territoryid]] */
  territoryid: SalesterritoryId,
  /** Primary key. Date the sales representive started work in the territory. */
  startdate: LocalDateTime,
  /** Date the sales representative left work in the territory. */
  enddate: Option[LocalDateTime],
  /** Default: uuid_generate_v1() */
  rowguid: Defaulted[UUID] = Defaulted.UseDefault,
  /** Default: now() */
  modifieddate: Defaulted[LocalDateTime] = Defaulted.UseDefault
) {
  def toRow(rowguidDefault: => UUID, modifieddateDefault: => LocalDateTime): SalesterritoryhistoryRow =
    SalesterritoryhistoryRow(
      businessentityid = businessentityid,
      territoryid = territoryid,
      startdate = startdate,
      enddate = enddate,
      rowguid = rowguid match {
                  case Defaulted.UseDefault => rowguidDefault
                  case Defaulted.Provided(value) => value
                },
      modifieddate = modifieddate match {
                       case Defaulted.UseDefault => modifieddateDefault
                       case Defaulted.Provided(value) => value
                     }
    )
}
object SalesterritoryhistoryRowUnsaved {
  implicit val oFormat: OFormat[SalesterritoryhistoryRowUnsaved] = new OFormat[SalesterritoryhistoryRowUnsaved]{
    override def writes(o: SalesterritoryhistoryRowUnsaved): JsObject =
      Json.obj(
        "businessentityid" -> o.businessentityid,
        "territoryid" -> o.territoryid,
        "startdate" -> o.startdate,
        "enddate" -> o.enddate,
        "rowguid" -> o.rowguid,
        "modifieddate" -> o.modifieddate
      )
  
    override def reads(json: JsValue): JsResult[SalesterritoryhistoryRowUnsaved] = {
      JsResult.fromTry(
        Try(
          SalesterritoryhistoryRowUnsaved(
            businessentityid = json.\("businessentityid").as[BusinessentityId],
            territoryid = json.\("territoryid").as[SalesterritoryId],
            startdate = json.\("startdate").as[LocalDateTime],
            enddate = json.\("enddate").toOption.map(_.as[LocalDateTime]),
            rowguid = json.\("rowguid").as[Defaulted[UUID]],
            modifieddate = json.\("modifieddate").as[Defaulted[LocalDateTime]]
          )
        )
      )
    }
  }
}
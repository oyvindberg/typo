/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package vproductmodelinstructions

import adventureworks.production.productmodel.ProductmodelId
import adventureworks.public.Name
import java.time.LocalDateTime
import java.util.UUID
import org.postgresql.jdbc.PgSQLXML
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class VproductmodelinstructionsViewRow(
  /** Points to [[productmodel.ProductmodelRow.productmodelid]] */
  productmodelid: Option[ProductmodelId],
  /** Points to [[productmodel.ProductmodelRow.name]] */
  name: Option[Name],
  /** Points to [[productmodel.ProductmodelRow.instructions]] */
  instructions: Option[PgSQLXML],
  LocationID: Option[Int],
  SetupHours: Option[BigDecimal],
  MachineHours: Option[BigDecimal],
  LaborHours: Option[BigDecimal],
  LotSize: Option[Int],
  Step: Option[String],
  /** Points to [[productmodel.ProductmodelRow.rowguid]] */
  rowguid: Option[UUID],
  /** Points to [[productmodel.ProductmodelRow.modifieddate]] */
  modifieddate: Option[LocalDateTime]
)

object VproductmodelinstructionsViewRow {
  implicit val oFormat: OFormat[VproductmodelinstructionsViewRow] = new OFormat[VproductmodelinstructionsViewRow]{
    override def writes(o: VproductmodelinstructionsViewRow): JsObject =
      Json.obj(
        "productmodelid" -> o.productmodelid,
        "name" -> o.name,
        "instructions" -> o.instructions,
        "LocationID" -> o.LocationID,
        "SetupHours" -> o.SetupHours,
        "MachineHours" -> o.MachineHours,
        "LaborHours" -> o.LaborHours,
        "LotSize" -> o.LotSize,
        "Step" -> o.Step,
        "rowguid" -> o.rowguid,
        "modifieddate" -> o.modifieddate
      )
  
    override def reads(json: JsValue): JsResult[VproductmodelinstructionsViewRow] = {
      JsResult.fromTry(
        Try(
          VproductmodelinstructionsViewRow(
            productmodelid = json.\("productmodelid").toOption.map(_.as[ProductmodelId]),
            name = json.\("name").toOption.map(_.as[Name]),
            instructions = json.\("instructions").toOption.map(_.as[PgSQLXML]),
            LocationID = json.\("LocationID").toOption.map(_.as[Int]),
            SetupHours = json.\("SetupHours").toOption.map(_.as[BigDecimal]),
            MachineHours = json.\("MachineHours").toOption.map(_.as[BigDecimal]),
            LaborHours = json.\("LaborHours").toOption.map(_.as[BigDecimal]),
            LotSize = json.\("LotSize").toOption.map(_.as[Int]),
            Step = json.\("Step").toOption.map(_.as[String]),
            rowguid = json.\("rowguid").toOption.map(_.as[UUID]),
            modifieddate = json.\("modifieddate").toOption.map(_.as[LocalDateTime])
          )
        )
      )
    }
  }
}
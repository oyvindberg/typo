/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package workorderrouting

import adventureworks.Defaulted
import adventureworks.TypoLocalDateTime
import adventureworks.production.location.LocationId
import adventureworks.production.workorder.WorkorderId
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.OWrites
import play.api.libs.json.Reads
import play.api.libs.json.Writes
import scala.collection.immutable.ListMap
import scala.util.Try

/** This class corresponds to a row in table `production.workorderrouting` which has not been persisted yet */
case class WorkorderroutingRowUnsaved(
  /** Primary key. Foreign key to WorkOrder.WorkOrderID.
      Points to [[workorder.WorkorderRow.workorderid]] */
  workorderid: WorkorderId,
  /** Primary key. Foreign key to Product.ProductID. */
  productid: Int,
  /** Primary key. Indicates the manufacturing process sequence. */
  operationsequence: Int,
  /** Manufacturing location where the part is processed. Foreign key to Location.LocationID.
      Points to [[location.LocationRow.locationid]] */
  locationid: LocationId,
  /** Planned manufacturing start date. */
  scheduledstartdate: TypoLocalDateTime,
  /** Planned manufacturing end date. */
  scheduledenddate: TypoLocalDateTime,
  /** Actual start date. */
  actualstartdate: Option[TypoLocalDateTime],
  /** Actual end date. */
  actualenddate: Option[TypoLocalDateTime],
  /** Number of manufacturing hours used. */
  actualresourcehrs: Option[BigDecimal],
  /** Estimated manufacturing cost. */
  plannedcost: BigDecimal,
  /** Actual manufacturing cost. */
  actualcost: Option[BigDecimal],
  /** Default: now() */
  modifieddate: Defaulted[TypoLocalDateTime] = Defaulted.UseDefault
) {
  def toRow(modifieddateDefault: => TypoLocalDateTime): WorkorderroutingRow =
    WorkorderroutingRow(
      workorderid = workorderid,
      productid = productid,
      operationsequence = operationsequence,
      locationid = locationid,
      scheduledstartdate = scheduledstartdate,
      scheduledenddate = scheduledenddate,
      actualstartdate = actualstartdate,
      actualenddate = actualenddate,
      actualresourcehrs = actualresourcehrs,
      plannedcost = plannedcost,
      actualcost = actualcost,
      modifieddate = modifieddate match {
                       case Defaulted.UseDefault => modifieddateDefault
                       case Defaulted.Provided(value) => value
                     }
    )
}
object WorkorderroutingRowUnsaved {
  implicit lazy val reads: Reads[WorkorderroutingRowUnsaved] = Reads[WorkorderroutingRowUnsaved](json => JsResult.fromTry(
      Try(
        WorkorderroutingRowUnsaved(
          workorderid = json.\("workorderid").as(WorkorderId.reads),
          productid = json.\("productid").as(Reads.IntReads),
          operationsequence = json.\("operationsequence").as(Reads.IntReads),
          locationid = json.\("locationid").as(LocationId.reads),
          scheduledstartdate = json.\("scheduledstartdate").as(TypoLocalDateTime.reads),
          scheduledenddate = json.\("scheduledenddate").as(TypoLocalDateTime.reads),
          actualstartdate = json.\("actualstartdate").toOption.map(_.as(TypoLocalDateTime.reads)),
          actualenddate = json.\("actualenddate").toOption.map(_.as(TypoLocalDateTime.reads)),
          actualresourcehrs = json.\("actualresourcehrs").toOption.map(_.as(Reads.bigDecReads)),
          plannedcost = json.\("plannedcost").as(Reads.bigDecReads),
          actualcost = json.\("actualcost").toOption.map(_.as(Reads.bigDecReads)),
          modifieddate = json.\("modifieddate").as(Defaulted.reads(TypoLocalDateTime.reads))
        )
      )
    ),
  )
  implicit lazy val writes: OWrites[WorkorderroutingRowUnsaved] = OWrites[WorkorderroutingRowUnsaved](o =>
    new JsObject(ListMap[String, JsValue](
      "workorderid" -> WorkorderId.writes.writes(o.workorderid),
      "productid" -> Writes.IntWrites.writes(o.productid),
      "operationsequence" -> Writes.IntWrites.writes(o.operationsequence),
      "locationid" -> LocationId.writes.writes(o.locationid),
      "scheduledstartdate" -> TypoLocalDateTime.writes.writes(o.scheduledstartdate),
      "scheduledenddate" -> TypoLocalDateTime.writes.writes(o.scheduledenddate),
      "actualstartdate" -> Writes.OptionWrites(TypoLocalDateTime.writes).writes(o.actualstartdate),
      "actualenddate" -> Writes.OptionWrites(TypoLocalDateTime.writes).writes(o.actualenddate),
      "actualresourcehrs" -> Writes.OptionWrites(Writes.BigDecimalWrites).writes(o.actualresourcehrs),
      "plannedcost" -> Writes.BigDecimalWrites.writes(o.plannedcost),
      "actualcost" -> Writes.OptionWrites(Writes.BigDecimalWrites).writes(o.actualcost),
      "modifieddate" -> Defaulted.writes(TypoLocalDateTime.writes).writes(o.modifieddate)
    ))
  )
}
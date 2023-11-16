/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package workorderrouting

import adventureworks.customtypes.TypoShort
import adventureworks.production.workorder.WorkorderId
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.OWrites
import play.api.libs.json.Reads
import play.api.libs.json.Writes
import scala.collection.immutable.ListMap
import scala.util.Try

/** Type for the composite primary key of table `production.workorderrouting` */
case class WorkorderroutingId(workorderid: WorkorderId, productid: Int, operationsequence: TypoShort)
object WorkorderroutingId {
  implicit def ordering(implicit O0: Ordering[TypoShort]): Ordering[WorkorderroutingId] = Ordering.by(x => (x.workorderid, x.productid, x.operationsequence))
  implicit lazy val reads: Reads[WorkorderroutingId] = Reads[WorkorderroutingId](json => JsResult.fromTry(
      Try(
        WorkorderroutingId(
          workorderid = json.\("workorderid").as(WorkorderId.reads),
          productid = json.\("productid").as(Reads.IntReads),
          operationsequence = json.\("operationsequence").as(TypoShort.reads)
        )
      )
    ),
  )
  implicit lazy val writes: OWrites[WorkorderroutingId] = OWrites[WorkorderroutingId](o =>
    new JsObject(ListMap[String, JsValue](
      "workorderid" -> WorkorderId.writes.writes(o.workorderid),
      "productid" -> Writes.IntWrites.writes(o.productid),
      "operationsequence" -> TypoShort.writes.writes(o.operationsequence)
    ))
  )
}
/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package salesorderdetail

import adventureworks.sales.salesorderheader.SalesorderheaderId
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

/** Type for the composite primary key of table `sales.salesorderdetail` */
case class SalesorderdetailId(salesorderid: SalesorderheaderId, salesorderdetailid: Int)
object SalesorderdetailId {
  implicit def ordering: Ordering[SalesorderdetailId] = Ordering.by(x => (x.salesorderid, x.salesorderdetailid))
  implicit val oFormat: OFormat[SalesorderdetailId] = new OFormat[SalesorderdetailId]{
    override def writes(o: SalesorderdetailId): JsObject =
      Json.obj(
        "salesorderid" -> o.salesorderid,
        "salesorderdetailid" -> o.salesorderdetailid
      )
  
    override def reads(json: JsValue): JsResult[SalesorderdetailId] = {
      JsResult.fromTry(
        Try(
          SalesorderdetailId(
            salesorderid = json.\("salesorderid").as[SalesorderheaderId],
            salesorderdetailid = json.\("salesorderdetailid").as[Int]
          )
        )
      )
    }
  }
}
/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package productcosthistory

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.production.product.ProductId
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.OWrites
import play.api.libs.json.Reads
import scala.collection.immutable.ListMap
import scala.util.Try

/** Type for the composite primary key of table `production.productcosthistory` */
case class ProductcosthistoryId(productid: ProductId, startdate: TypoLocalDateTime)
object ProductcosthistoryId {
  implicit def ordering(implicit O0: Ordering[TypoLocalDateTime]): Ordering[ProductcosthistoryId] = Ordering.by(x => (x.productid, x.startdate))
  implicit lazy val reads: Reads[ProductcosthistoryId] = Reads[ProductcosthistoryId](json => JsResult.fromTry(
      Try(
        ProductcosthistoryId(
          productid = json.\("productid").as(ProductId.reads),
          startdate = json.\("startdate").as(TypoLocalDateTime.reads)
        )
      )
    ),
  )
  implicit lazy val writes: OWrites[ProductcosthistoryId] = OWrites[ProductcosthistoryId](o =>
    new JsObject(ListMap[String, JsValue](
      "productid" -> ProductId.writes.writes(o.productid),
      "startdate" -> TypoLocalDateTime.writes.writes(o.startdate)
    ))
  )
}
/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package specialofferproduct

import adventureworks.production.product.ProductId
import adventureworks.sales.specialoffer.SpecialofferId
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.OWrites
import play.api.libs.json.Reads
import scala.collection.immutable.ListMap
import scala.util.Try

/** Type for the composite primary key of table `sales.specialofferproduct` */
case class SpecialofferproductId(
  specialofferid: SpecialofferId,
  productid: ProductId
)
object SpecialofferproductId {
  implicit lazy val ordering: Ordering[SpecialofferproductId] = Ordering.by(x => (x.specialofferid, x.productid))
  implicit lazy val reads: Reads[SpecialofferproductId] = Reads[SpecialofferproductId](json => JsResult.fromTry(
      Try(
        SpecialofferproductId(
          specialofferid = json.\("specialofferid").as(SpecialofferId.reads),
          productid = json.\("productid").as(ProductId.reads)
        )
      )
    ),
  )
  implicit lazy val writes: OWrites[SpecialofferproductId] = OWrites[SpecialofferproductId](o =>
    new JsObject(ListMap[String, JsValue](
      "specialofferid" -> SpecialofferId.writes.writes(o.specialofferid),
      "productid" -> ProductId.writes.writes(o.productid)
    ))
  )
}

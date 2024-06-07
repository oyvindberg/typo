/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package productmodelillustration

import adventureworks.production.illustration.IllustrationId
import adventureworks.production.productmodel.ProductmodelId
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.OWrites
import play.api.libs.json.Reads
import scala.collection.immutable.ListMap
import scala.util.Try

/** Type for the composite primary key of table `production.productmodelillustration` */
case class ProductmodelillustrationId(
  productmodelid: ProductmodelId,
  illustrationid: IllustrationId
)
object ProductmodelillustrationId {
  implicit lazy val ordering: Ordering[ProductmodelillustrationId] = Ordering.by(x => (x.productmodelid, x.illustrationid))
  implicit lazy val reads: Reads[ProductmodelillustrationId] = Reads[ProductmodelillustrationId](json => JsResult.fromTry(
      Try(
        ProductmodelillustrationId(
          productmodelid = json.\("productmodelid").as(ProductmodelId.reads),
          illustrationid = json.\("illustrationid").as(IllustrationId.reads)
        )
      )
    ),
  )
  implicit lazy val writes: OWrites[ProductmodelillustrationId] = OWrites[ProductmodelillustrationId](o =>
    new JsObject(ListMap[String, JsValue](
      "productmodelid" -> ProductmodelId.writes.writes(o.productmodelid),
      "illustrationid" -> IllustrationId.writes.writes(o.illustrationid)
    ))
  )
}
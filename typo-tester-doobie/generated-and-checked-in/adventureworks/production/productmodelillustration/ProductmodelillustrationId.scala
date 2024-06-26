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
import io.circe.Decoder
import io.circe.Encoder

/** Type for the composite primary key of table `production.productmodelillustration` */
case class ProductmodelillustrationId(
  productmodelid: ProductmodelId,
  illustrationid: IllustrationId
)
object ProductmodelillustrationId {
  implicit lazy val decoder: Decoder[ProductmodelillustrationId] = Decoder.forProduct2[ProductmodelillustrationId, ProductmodelId, IllustrationId]("productmodelid", "illustrationid")(ProductmodelillustrationId.apply)(ProductmodelId.decoder, IllustrationId.decoder)
  implicit lazy val encoder: Encoder[ProductmodelillustrationId] = Encoder.forProduct2[ProductmodelillustrationId, ProductmodelId, IllustrationId]("productmodelid", "illustrationid")(x => (x.productmodelid, x.illustrationid))(ProductmodelId.encoder, IllustrationId.encoder)
  implicit lazy val ordering: Ordering[ProductmodelillustrationId] = Ordering.by(x => (x.productmodelid, x.illustrationid))
}

/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package productlistpricehistory

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.production.product.ProductId
import io.circe.Decoder
import io.circe.Encoder

/** Type for the composite primary key of table `production.productlistpricehistory` */
case class ProductlistpricehistoryId(
  productid: ProductId,
  startdate: TypoLocalDateTime
)
object ProductlistpricehistoryId {
  implicit lazy val decoder: Decoder[ProductlistpricehistoryId] = Decoder.forProduct2[ProductlistpricehistoryId, ProductId, TypoLocalDateTime]("productid", "startdate")(ProductlistpricehistoryId.apply)(ProductId.decoder, TypoLocalDateTime.decoder)
  implicit lazy val encoder: Encoder[ProductlistpricehistoryId] = Encoder.forProduct2[ProductlistpricehistoryId, ProductId, TypoLocalDateTime]("productid", "startdate")(x => (x.productid, x.startdate))(ProductId.encoder, TypoLocalDateTime.encoder)
  implicit def ordering(implicit O0: Ordering[TypoLocalDateTime]): Ordering[ProductlistpricehistoryId] = Ordering.by(x => (x.productid, x.startdate))
}
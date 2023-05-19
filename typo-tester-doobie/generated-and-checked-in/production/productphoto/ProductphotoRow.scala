/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package productphoto

import io.circe.Decoder
import io.circe.Encoder
import io.circe.HCursor
import io.circe.Json
import java.time.LocalDateTime

case class ProductphotoRow(
  /** Primary key for ProductPhoto records. */
  productphotoid: ProductphotoId,
  /** Small image of the product. */
  thumbnailphoto: Option[Array[Byte]],
  /** Small image file name. */
  thumbnailphotofilename: Option[String],
  /** Large image of the product. */
  largephoto: Option[Array[Byte]],
  /** Large image file name. */
  largephotofilename: Option[String],
  modifieddate: LocalDateTime
)

object ProductphotoRow {
  implicit val decoder: Decoder[ProductphotoRow] =
    (c: HCursor) =>
      for {
        productphotoid <- c.downField("productphotoid").as[ProductphotoId]
        thumbnailphoto <- c.downField("thumbnailphoto").as[Option[Array[Byte]]]
        thumbnailphotofilename <- c.downField("thumbnailphotofilename").as[Option[String]]
        largephoto <- c.downField("largephoto").as[Option[Array[Byte]]]
        largephotofilename <- c.downField("largephotofilename").as[Option[String]]
        modifieddate <- c.downField("modifieddate").as[LocalDateTime]
      } yield ProductphotoRow(productphotoid, thumbnailphoto, thumbnailphotofilename, largephoto, largephotofilename, modifieddate)
  implicit val encoder: Encoder[ProductphotoRow] = {
    import io.circe.syntax._
    row =>
      Json.obj(
        "productphotoid" := row.productphotoid,
        "thumbnailphoto" := row.thumbnailphoto,
        "thumbnailphotofilename" := row.thumbnailphotofilename,
        "largephoto" := row.largephoto,
        "largephotofilename" := row.largephotofilename,
        "modifieddate" := row.modifieddate
      )}
}
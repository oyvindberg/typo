/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package pdoc

import adventureworks.production.document.DocumentId
import adventureworks.production.product.ProductId
import io.circe.Decoder
import io.circe.Encoder
import io.circe.HCursor
import io.circe.Json
import java.time.LocalDateTime

case class PdocViewRow(
  id: Option[Int],
  /** Points to [[production.productdocument.ProductdocumentRow.productid]] */
  productid: Option[ProductId],
  /** Points to [[production.productdocument.ProductdocumentRow.modifieddate]] */
  modifieddate: Option[LocalDateTime],
  /** Points to [[production.productdocument.ProductdocumentRow.documentnode]] */
  documentnode: Option[DocumentId]
)

object PdocViewRow {
  implicit val decoder: Decoder[PdocViewRow] =
    (c: HCursor) =>
      for {
        id <- c.downField("id").as[Option[Int]]
        productid <- c.downField("productid").as[Option[ProductId]]
        modifieddate <- c.downField("modifieddate").as[Option[LocalDateTime]]
        documentnode <- c.downField("documentnode").as[Option[DocumentId]]
      } yield PdocViewRow(id, productid, modifieddate, documentnode)
  implicit val encoder: Encoder[PdocViewRow] = {
    import io.circe.syntax._
    row =>
      Json.obj(
        "id" := row.id,
        "productid" := row.productid,
        "modifieddate" := row.modifieddate,
        "documentnode" := row.documentnode
      )}
}
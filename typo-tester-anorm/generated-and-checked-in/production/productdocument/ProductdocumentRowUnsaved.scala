/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package productdocument

import adventureworks.Defaulted
import adventureworks.TypoLocalDateTime
import adventureworks.production.document.DocumentId
import adventureworks.production.product.ProductId
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.OWrites
import play.api.libs.json.Reads
import scala.collection.immutable.ListMap
import scala.util.Try

/** This class corresponds to a row in table `production.productdocument` which has not been persisted yet */
case class ProductdocumentRowUnsaved(
  /** Product identification number. Foreign key to Product.ProductID.
      Points to [[product.ProductRow.productid]] */
  productid: ProductId,
  /** Default: now() */
  modifieddate: Defaulted[TypoLocalDateTime] = Defaulted.UseDefault,
  /** Default: '/'::character varying
      Document identification number. Foreign key to Document.DocumentNode.
      Points to [[document.DocumentRow.documentnode]] */
  documentnode: Defaulted[DocumentId] = Defaulted.UseDefault
) {
  def toRow(modifieddateDefault: => TypoLocalDateTime, documentnodeDefault: => DocumentId): ProductdocumentRow =
    ProductdocumentRow(
      productid = productid,
      modifieddate = modifieddate match {
                       case Defaulted.UseDefault => modifieddateDefault
                       case Defaulted.Provided(value) => value
                     },
      documentnode = documentnode match {
                       case Defaulted.UseDefault => documentnodeDefault
                       case Defaulted.Provided(value) => value
                     }
    )
}
object ProductdocumentRowUnsaved {
  implicit lazy val reads: Reads[ProductdocumentRowUnsaved] = Reads[ProductdocumentRowUnsaved](json => JsResult.fromTry(
      Try(
        ProductdocumentRowUnsaved(
          productid = json.\("productid").as(ProductId.reads),
          modifieddate = json.\("modifieddate").as(Defaulted.reads(TypoLocalDateTime.reads)),
          documentnode = json.\("documentnode").as(Defaulted.reads(DocumentId.reads))
        )
      )
    ),
  )
  implicit lazy val writes: OWrites[ProductdocumentRowUnsaved] = OWrites[ProductdocumentRowUnsaved](o =>
    new JsObject(ListMap[String, JsValue](
      "productid" -> ProductId.writes.writes(o.productid),
      "modifieddate" -> Defaulted.writes(TypoLocalDateTime.writes).writes(o.modifieddate),
      "documentnode" -> Defaulted.writes(DocumentId.writes).writes(o.documentnode)
    ))
  )
}
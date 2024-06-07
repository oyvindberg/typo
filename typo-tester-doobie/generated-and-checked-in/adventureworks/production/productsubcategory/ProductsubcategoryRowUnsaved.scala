/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package productsubcategory

import adventureworks.customtypes.Defaulted
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoUUID
import adventureworks.production.productcategory.ProductcategoryId
import adventureworks.public.Name
import doobie.postgres.Text
import io.circe.Decoder
import io.circe.Encoder

/** This class corresponds to a row in table `production.productsubcategory` which has not been persisted yet */
case class ProductsubcategoryRowUnsaved(
  /** Product category identification number. Foreign key to ProductCategory.ProductCategoryID.
      Points to [[productcategory.ProductcategoryRow.productcategoryid]] */
  productcategoryid: ProductcategoryId,
  /** Subcategory description. */
  name: Name,
  /** Default: nextval('production.productsubcategory_productsubcategoryid_seq'::regclass)
      Primary key for ProductSubcategory records. */
  productsubcategoryid: Defaulted[ProductsubcategoryId] = Defaulted.UseDefault,
  /** Default: uuid_generate_v1() */
  rowguid: Defaulted[TypoUUID] = Defaulted.UseDefault,
  /** Default: now() */
  modifieddate: Defaulted[TypoLocalDateTime] = Defaulted.UseDefault
) {
  def toRow(productsubcategoryidDefault: => ProductsubcategoryId, rowguidDefault: => TypoUUID, modifieddateDefault: => TypoLocalDateTime): ProductsubcategoryRow =
    ProductsubcategoryRow(
      productcategoryid = productcategoryid,
      name = name,
      productsubcategoryid = productsubcategoryid match {
                               case Defaulted.UseDefault => productsubcategoryidDefault
                               case Defaulted.Provided(value) => value
                             },
      rowguid = rowguid match {
                  case Defaulted.UseDefault => rowguidDefault
                  case Defaulted.Provided(value) => value
                },
      modifieddate = modifieddate match {
                       case Defaulted.UseDefault => modifieddateDefault
                       case Defaulted.Provided(value) => value
                     }
    )
}
object ProductsubcategoryRowUnsaved {
  implicit lazy val decoder: Decoder[ProductsubcategoryRowUnsaved] = Decoder.forProduct5[ProductsubcategoryRowUnsaved, ProductcategoryId, Name, Defaulted[ProductsubcategoryId], Defaulted[TypoUUID], Defaulted[TypoLocalDateTime]]("productcategoryid", "name", "productsubcategoryid", "rowguid", "modifieddate")(ProductsubcategoryRowUnsaved.apply)(ProductcategoryId.decoder, Name.decoder, Defaulted.decoder(ProductsubcategoryId.decoder), Defaulted.decoder(TypoUUID.decoder), Defaulted.decoder(TypoLocalDateTime.decoder))
  implicit lazy val encoder: Encoder[ProductsubcategoryRowUnsaved] = Encoder.forProduct5[ProductsubcategoryRowUnsaved, ProductcategoryId, Name, Defaulted[ProductsubcategoryId], Defaulted[TypoUUID], Defaulted[TypoLocalDateTime]]("productcategoryid", "name", "productsubcategoryid", "rowguid", "modifieddate")(x => (x.productcategoryid, x.name, x.productsubcategoryid, x.rowguid, x.modifieddate))(ProductcategoryId.encoder, Name.encoder, Defaulted.encoder(ProductsubcategoryId.encoder), Defaulted.encoder(TypoUUID.encoder), Defaulted.encoder(TypoLocalDateTime.encoder))
  implicit lazy val text: Text[ProductsubcategoryRowUnsaved] = Text.instance[ProductsubcategoryRowUnsaved]{ (row, sb) =>
    ProductcategoryId.text.unsafeEncode(row.productcategoryid, sb)
    sb.append(Text.DELIMETER)
    Name.text.unsafeEncode(row.name, sb)
    sb.append(Text.DELIMETER)
    Defaulted.text(ProductsubcategoryId.text).unsafeEncode(row.productsubcategoryid, sb)
    sb.append(Text.DELIMETER)
    Defaulted.text(TypoUUID.text).unsafeEncode(row.rowguid, sb)
    sb.append(Text.DELIMETER)
    Defaulted.text(TypoLocalDateTime.text).unsafeEncode(row.modifieddate, sb)
  }
}
/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package productcategory

import adventureworks.customtypes.Defaulted
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoUUID
import adventureworks.public.Name
import doobie.postgres.Text
import doobie.util.Read
import doobie.util.Write
import io.circe.Decoder
import io.circe.Encoder

/** Table: production.productcategory
    High-level product categorization.
    Primary key: productcategoryid */
case class ProductcategoryRow(
  /** Primary key for ProductCategory records.
      Default: nextval('production.productcategory_productcategoryid_seq'::regclass) */
  productcategoryid: ProductcategoryId,
  /** Category description. */
  name: Name,
  /** Default: uuid_generate_v1() */
  rowguid: TypoUUID,
  /** Default: now() */
  modifieddate: TypoLocalDateTime
){
   val id = productcategoryid
   def toUnsavedRow(productcategoryid: Defaulted[ProductcategoryId], rowguid: Defaulted[TypoUUID] = Defaulted.Provided(this.rowguid), modifieddate: Defaulted[TypoLocalDateTime] = Defaulted.Provided(this.modifieddate)): ProductcategoryRowUnsaved =
     ProductcategoryRowUnsaved(name, productcategoryid, rowguid, modifieddate)
 }

object ProductcategoryRow {
  implicit lazy val decoder: Decoder[ProductcategoryRow] = Decoder.forProduct4[ProductcategoryRow, ProductcategoryId, Name, TypoUUID, TypoLocalDateTime]("productcategoryid", "name", "rowguid", "modifieddate")(ProductcategoryRow.apply)(ProductcategoryId.decoder, Name.decoder, TypoUUID.decoder, TypoLocalDateTime.decoder)
  implicit lazy val encoder: Encoder[ProductcategoryRow] = Encoder.forProduct4[ProductcategoryRow, ProductcategoryId, Name, TypoUUID, TypoLocalDateTime]("productcategoryid", "name", "rowguid", "modifieddate")(x => (x.productcategoryid, x.name, x.rowguid, x.modifieddate))(ProductcategoryId.encoder, Name.encoder, TypoUUID.encoder, TypoLocalDateTime.encoder)
  implicit lazy val read: Read[ProductcategoryRow] = new Read.CompositeOfInstances(Array(
    new Read.Single(ProductcategoryId.get).asInstanceOf[Read[Any]],
      new Read.Single(Name.get).asInstanceOf[Read[Any]],
      new Read.Single(TypoUUID.get).asInstanceOf[Read[Any]],
      new Read.Single(TypoLocalDateTime.get).asInstanceOf[Read[Any]]
  ))(using scala.reflect.ClassTag.Any).map { arr =>
    ProductcategoryRow(
      productcategoryid = arr(0).asInstanceOf[ProductcategoryId],
          name = arr(1).asInstanceOf[Name],
          rowguid = arr(2).asInstanceOf[TypoUUID],
          modifieddate = arr(3).asInstanceOf[TypoLocalDateTime]
    )
  }
  implicit lazy val text: Text[ProductcategoryRow] = Text.instance[ProductcategoryRow]{ (row, sb) =>
    ProductcategoryId.text.unsafeEncode(row.productcategoryid, sb)
    sb.append(Text.DELIMETER)
    Name.text.unsafeEncode(row.name, sb)
    sb.append(Text.DELIMETER)
    TypoUUID.text.unsafeEncode(row.rowguid, sb)
    sb.append(Text.DELIMETER)
    TypoLocalDateTime.text.unsafeEncode(row.modifieddate, sb)
  }
  implicit lazy val write: Write[ProductcategoryRow] = new Write.Composite[ProductcategoryRow](
    List(new Write.Single(ProductcategoryId.put),
         new Write.Single(Name.put),
         new Write.Single(TypoUUID.put),
         new Write.Single(TypoLocalDateTime.put)),
    a => List(a.productcategoryid, a.name, a.rowguid, a.modifieddate)
  )
}

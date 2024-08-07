/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package productlistpricehistory

import adventureworks.customtypes.Defaulted
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.production.product.ProductId
import doobie.enumerated.Nullability
import doobie.postgres.Text
import doobie.util.Read
import doobie.util.Write
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.Encoder
import java.sql.ResultSet

/** Table: production.productlistpricehistory
    Changes in the list price of a product over time.
    Composite primary key: productid, startdate */
case class ProductlistpricehistoryRow(
  /** Product identification number. Foreign key to Product.ProductID
      Points to [[product.ProductRow.productid]] */
  productid: ProductId,
  /** List price start date.
      Constraint CK_ProductListPriceHistory_EndDate affecting columns enddate, startdate: (((enddate >= startdate) OR (enddate IS NULL))) */
  startdate: TypoLocalDateTime,
  /** List price end date
      Constraint CK_ProductListPriceHistory_EndDate affecting columns enddate, startdate: (((enddate >= startdate) OR (enddate IS NULL))) */
  enddate: Option[TypoLocalDateTime],
  /** Product list price.
      Constraint CK_ProductListPriceHistory_ListPrice affecting columns listprice: ((listprice > 0.00)) */
  listprice: BigDecimal,
  /** Default: now() */
  modifieddate: TypoLocalDateTime
){
   val compositeId: ProductlistpricehistoryId = ProductlistpricehistoryId(productid, startdate)
   val id = compositeId
   def toUnsavedRow(modifieddate: Defaulted[TypoLocalDateTime] = Defaulted.Provided(this.modifieddate)): ProductlistpricehistoryRowUnsaved =
     ProductlistpricehistoryRowUnsaved(productid, startdate, enddate, listprice, modifieddate)
 }

object ProductlistpricehistoryRow {
  def apply(compositeId: ProductlistpricehistoryId, enddate: Option[TypoLocalDateTime], listprice: BigDecimal, modifieddate: TypoLocalDateTime) =
    new ProductlistpricehistoryRow(compositeId.productid, compositeId.startdate, enddate, listprice, modifieddate)
  implicit lazy val decoder: Decoder[ProductlistpricehistoryRow] = Decoder.forProduct5[ProductlistpricehistoryRow, ProductId, TypoLocalDateTime, Option[TypoLocalDateTime], BigDecimal, TypoLocalDateTime]("productid", "startdate", "enddate", "listprice", "modifieddate")(ProductlistpricehistoryRow.apply)(ProductId.decoder, TypoLocalDateTime.decoder, Decoder.decodeOption(TypoLocalDateTime.decoder), Decoder.decodeBigDecimal, TypoLocalDateTime.decoder)
  implicit lazy val encoder: Encoder[ProductlistpricehistoryRow] = Encoder.forProduct5[ProductlistpricehistoryRow, ProductId, TypoLocalDateTime, Option[TypoLocalDateTime], BigDecimal, TypoLocalDateTime]("productid", "startdate", "enddate", "listprice", "modifieddate")(x => (x.productid, x.startdate, x.enddate, x.listprice, x.modifieddate))(ProductId.encoder, TypoLocalDateTime.encoder, Encoder.encodeOption(TypoLocalDateTime.encoder), Encoder.encodeBigDecimal, TypoLocalDateTime.encoder)
  implicit lazy val read: Read[ProductlistpricehistoryRow] = new Read[ProductlistpricehistoryRow](
    gets = List(
      (ProductId.get, Nullability.NoNulls),
      (TypoLocalDateTime.get, Nullability.NoNulls),
      (TypoLocalDateTime.get, Nullability.Nullable),
      (Meta.ScalaBigDecimalMeta.get, Nullability.NoNulls),
      (TypoLocalDateTime.get, Nullability.NoNulls)
    ),
    unsafeGet = (rs: ResultSet, i: Int) => ProductlistpricehistoryRow(
      productid = ProductId.get.unsafeGetNonNullable(rs, i + 0),
      startdate = TypoLocalDateTime.get.unsafeGetNonNullable(rs, i + 1),
      enddate = TypoLocalDateTime.get.unsafeGetNullable(rs, i + 2),
      listprice = Meta.ScalaBigDecimalMeta.get.unsafeGetNonNullable(rs, i + 3),
      modifieddate = TypoLocalDateTime.get.unsafeGetNonNullable(rs, i + 4)
    )
  )
  implicit lazy val text: Text[ProductlistpricehistoryRow] = Text.instance[ProductlistpricehistoryRow]{ (row, sb) =>
    ProductId.text.unsafeEncode(row.productid, sb)
    sb.append(Text.DELIMETER)
    TypoLocalDateTime.text.unsafeEncode(row.startdate, sb)
    sb.append(Text.DELIMETER)
    Text.option(TypoLocalDateTime.text).unsafeEncode(row.enddate, sb)
    sb.append(Text.DELIMETER)
    Text.bigDecimalInstance.unsafeEncode(row.listprice, sb)
    sb.append(Text.DELIMETER)
    TypoLocalDateTime.text.unsafeEncode(row.modifieddate, sb)
  }
  implicit lazy val write: Write[ProductlistpricehistoryRow] = new Write[ProductlistpricehistoryRow](
    puts = List((ProductId.put, Nullability.NoNulls),
                (TypoLocalDateTime.put, Nullability.NoNulls),
                (TypoLocalDateTime.put, Nullability.Nullable),
                (Meta.ScalaBigDecimalMeta.put, Nullability.NoNulls),
                (TypoLocalDateTime.put, Nullability.NoNulls)),
    toList = x => List(x.productid, x.startdate, x.enddate, x.listprice, x.modifieddate),
    unsafeSet = (rs, i, a) => {
                  ProductId.put.unsafeSetNonNullable(rs, i + 0, a.productid)
                  TypoLocalDateTime.put.unsafeSetNonNullable(rs, i + 1, a.startdate)
                  TypoLocalDateTime.put.unsafeSetNullable(rs, i + 2, a.enddate)
                  Meta.ScalaBigDecimalMeta.put.unsafeSetNonNullable(rs, i + 3, a.listprice)
                  TypoLocalDateTime.put.unsafeSetNonNullable(rs, i + 4, a.modifieddate)
                },
    unsafeUpdate = (ps, i, a) => {
                     ProductId.put.unsafeUpdateNonNullable(ps, i + 0, a.productid)
                     TypoLocalDateTime.put.unsafeUpdateNonNullable(ps, i + 1, a.startdate)
                     TypoLocalDateTime.put.unsafeUpdateNullable(ps, i + 2, a.enddate)
                     Meta.ScalaBigDecimalMeta.put.unsafeUpdateNonNullable(ps, i + 3, a.listprice)
                     TypoLocalDateTime.put.unsafeUpdateNonNullable(ps, i + 4, a.modifieddate)
                   }
  )
}

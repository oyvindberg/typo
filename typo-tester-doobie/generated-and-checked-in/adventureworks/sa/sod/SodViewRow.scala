/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package sod

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoShort
import adventureworks.customtypes.TypoUUID
import adventureworks.production.product.ProductId
import adventureworks.sales.salesorderheader.SalesorderheaderId
import adventureworks.sales.specialoffer.SpecialofferId
import doobie.enumerated.Nullability
import doobie.util.Read
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.Encoder
import java.sql.ResultSet

case class SodViewRow(
  /** Points to [[sales.salesorderdetail.SalesorderdetailRow.salesorderdetailid]] */
  id: Int,
  /** Points to [[sales.salesorderdetail.SalesorderdetailRow.salesorderid]] */
  salesorderid: SalesorderheaderId,
  /** Points to [[sales.salesorderdetail.SalesorderdetailRow.salesorderdetailid]] */
  salesorderdetailid: Int,
  /** Points to [[sales.salesorderdetail.SalesorderdetailRow.carriertrackingnumber]] */
  carriertrackingnumber: Option[/* max 25 chars */ String],
  /** Points to [[sales.salesorderdetail.SalesorderdetailRow.orderqty]] */
  orderqty: TypoShort,
  /** Points to [[sales.salesorderdetail.SalesorderdetailRow.productid]] */
  productid: ProductId,
  /** Points to [[sales.salesorderdetail.SalesorderdetailRow.specialofferid]] */
  specialofferid: SpecialofferId,
  /** Points to [[sales.salesorderdetail.SalesorderdetailRow.unitprice]] */
  unitprice: BigDecimal,
  /** Points to [[sales.salesorderdetail.SalesorderdetailRow.unitpricediscount]] */
  unitpricediscount: BigDecimal,
  /** Points to [[sales.salesorderdetail.SalesorderdetailRow.rowguid]] */
  rowguid: TypoUUID,
  /** Points to [[sales.salesorderdetail.SalesorderdetailRow.modifieddate]] */
  modifieddate: TypoLocalDateTime
)

object SodViewRow {
  implicit lazy val decoder: Decoder[SodViewRow] = Decoder.forProduct11[SodViewRow, Int, SalesorderheaderId, Int, Option[/* max 25 chars */ String], TypoShort, ProductId, SpecialofferId, BigDecimal, BigDecimal, TypoUUID, TypoLocalDateTime]("id", "salesorderid", "salesorderdetailid", "carriertrackingnumber", "orderqty", "productid", "specialofferid", "unitprice", "unitpricediscount", "rowguid", "modifieddate")(SodViewRow.apply)(Decoder.decodeInt, SalesorderheaderId.decoder, Decoder.decodeInt, Decoder.decodeOption(Decoder.decodeString), TypoShort.decoder, ProductId.decoder, SpecialofferId.decoder, Decoder.decodeBigDecimal, Decoder.decodeBigDecimal, TypoUUID.decoder, TypoLocalDateTime.decoder)
  implicit lazy val encoder: Encoder[SodViewRow] = Encoder.forProduct11[SodViewRow, Int, SalesorderheaderId, Int, Option[/* max 25 chars */ String], TypoShort, ProductId, SpecialofferId, BigDecimal, BigDecimal, TypoUUID, TypoLocalDateTime]("id", "salesorderid", "salesorderdetailid", "carriertrackingnumber", "orderqty", "productid", "specialofferid", "unitprice", "unitpricediscount", "rowguid", "modifieddate")(x => (x.id, x.salesorderid, x.salesorderdetailid, x.carriertrackingnumber, x.orderqty, x.productid, x.specialofferid, x.unitprice, x.unitpricediscount, x.rowguid, x.modifieddate))(Encoder.encodeInt, SalesorderheaderId.encoder, Encoder.encodeInt, Encoder.encodeOption(Encoder.encodeString), TypoShort.encoder, ProductId.encoder, SpecialofferId.encoder, Encoder.encodeBigDecimal, Encoder.encodeBigDecimal, TypoUUID.encoder, TypoLocalDateTime.encoder)
  implicit lazy val read: Read[SodViewRow] = new Read[SodViewRow](
    gets = List(
      (Meta.IntMeta.get, Nullability.NoNulls),
      (SalesorderheaderId.get, Nullability.NoNulls),
      (Meta.IntMeta.get, Nullability.NoNulls),
      (Meta.StringMeta.get, Nullability.Nullable),
      (TypoShort.get, Nullability.NoNulls),
      (ProductId.get, Nullability.NoNulls),
      (SpecialofferId.get, Nullability.NoNulls),
      (Meta.ScalaBigDecimalMeta.get, Nullability.NoNulls),
      (Meta.ScalaBigDecimalMeta.get, Nullability.NoNulls),
      (TypoUUID.get, Nullability.NoNulls),
      (TypoLocalDateTime.get, Nullability.NoNulls)
    ),
    unsafeGet = (rs: ResultSet, i: Int) => SodViewRow(
      id = Meta.IntMeta.get.unsafeGetNonNullable(rs, i + 0),
      salesorderid = SalesorderheaderId.get.unsafeGetNonNullable(rs, i + 1),
      salesorderdetailid = Meta.IntMeta.get.unsafeGetNonNullable(rs, i + 2),
      carriertrackingnumber = Meta.StringMeta.get.unsafeGetNullable(rs, i + 3),
      orderqty = TypoShort.get.unsafeGetNonNullable(rs, i + 4),
      productid = ProductId.get.unsafeGetNonNullable(rs, i + 5),
      specialofferid = SpecialofferId.get.unsafeGetNonNullable(rs, i + 6),
      unitprice = Meta.ScalaBigDecimalMeta.get.unsafeGetNonNullable(rs, i + 7),
      unitpricediscount = Meta.ScalaBigDecimalMeta.get.unsafeGetNonNullable(rs, i + 8),
      rowguid = TypoUUID.get.unsafeGetNonNullable(rs, i + 9),
      modifieddate = TypoLocalDateTime.get.unsafeGetNonNullable(rs, i + 10)
    )
  )
}
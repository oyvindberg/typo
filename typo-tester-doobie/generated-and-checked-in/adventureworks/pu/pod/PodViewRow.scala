/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pu
package pod

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.production.product.ProductId
import adventureworks.purchasing.purchaseorderheader.PurchaseorderheaderId
import doobie.enumerated.Nullability
import doobie.util.Read
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.Encoder
import java.sql.ResultSet

case class PodViewRow(
  /** Points to [[purchasing.purchaseorderdetail.PurchaseorderdetailRow.purchaseorderdetailid]] */
  id: Int,
  /** Points to [[purchasing.purchaseorderdetail.PurchaseorderdetailRow.purchaseorderid]] */
  purchaseorderid: PurchaseorderheaderId,
  /** Points to [[purchasing.purchaseorderdetail.PurchaseorderdetailRow.purchaseorderdetailid]] */
  purchaseorderdetailid: Int,
  /** Points to [[purchasing.purchaseorderdetail.PurchaseorderdetailRow.duedate]] */
  duedate: TypoLocalDateTime,
  /** Points to [[purchasing.purchaseorderdetail.PurchaseorderdetailRow.orderqty]] */
  orderqty: Int,
  /** Points to [[purchasing.purchaseorderdetail.PurchaseorderdetailRow.productid]] */
  productid: ProductId,
  /** Points to [[purchasing.purchaseorderdetail.PurchaseorderdetailRow.unitprice]] */
  unitprice: BigDecimal,
  /** Points to [[purchasing.purchaseorderdetail.PurchaseorderdetailRow.receivedqty]] */
  receivedqty: BigDecimal,
  /** Points to [[purchasing.purchaseorderdetail.PurchaseorderdetailRow.rejectedqty]] */
  rejectedqty: BigDecimal,
  /** Points to [[purchasing.purchaseorderdetail.PurchaseorderdetailRow.modifieddate]] */
  modifieddate: TypoLocalDateTime
)

object PodViewRow {
  implicit lazy val decoder: Decoder[PodViewRow] = Decoder.forProduct10[PodViewRow, Int, PurchaseorderheaderId, Int, TypoLocalDateTime, Int, ProductId, BigDecimal, BigDecimal, BigDecimal, TypoLocalDateTime]("id", "purchaseorderid", "purchaseorderdetailid", "duedate", "orderqty", "productid", "unitprice", "receivedqty", "rejectedqty", "modifieddate")(PodViewRow.apply)(Decoder.decodeInt, PurchaseorderheaderId.decoder, Decoder.decodeInt, TypoLocalDateTime.decoder, Decoder.decodeInt, ProductId.decoder, Decoder.decodeBigDecimal, Decoder.decodeBigDecimal, Decoder.decodeBigDecimal, TypoLocalDateTime.decoder)
  implicit lazy val encoder: Encoder[PodViewRow] = Encoder.forProduct10[PodViewRow, Int, PurchaseorderheaderId, Int, TypoLocalDateTime, Int, ProductId, BigDecimal, BigDecimal, BigDecimal, TypoLocalDateTime]("id", "purchaseorderid", "purchaseorderdetailid", "duedate", "orderqty", "productid", "unitprice", "receivedqty", "rejectedqty", "modifieddate")(x => (x.id, x.purchaseorderid, x.purchaseorderdetailid, x.duedate, x.orderqty, x.productid, x.unitprice, x.receivedqty, x.rejectedqty, x.modifieddate))(Encoder.encodeInt, PurchaseorderheaderId.encoder, Encoder.encodeInt, TypoLocalDateTime.encoder, Encoder.encodeInt, ProductId.encoder, Encoder.encodeBigDecimal, Encoder.encodeBigDecimal, Encoder.encodeBigDecimal, TypoLocalDateTime.encoder)
  implicit lazy val read: Read[PodViewRow] = new Read[PodViewRow](
    gets = List(
      (Meta.IntMeta.get, Nullability.NoNulls),
      (PurchaseorderheaderId.get, Nullability.NoNulls),
      (Meta.IntMeta.get, Nullability.NoNulls),
      (TypoLocalDateTime.get, Nullability.NoNulls),
      (Meta.IntMeta.get, Nullability.NoNulls),
      (ProductId.get, Nullability.NoNulls),
      (Meta.ScalaBigDecimalMeta.get, Nullability.NoNulls),
      (Meta.ScalaBigDecimalMeta.get, Nullability.NoNulls),
      (Meta.ScalaBigDecimalMeta.get, Nullability.NoNulls),
      (TypoLocalDateTime.get, Nullability.NoNulls)
    ),
    unsafeGet = (rs: ResultSet, i: Int) => PodViewRow(
      id = Meta.IntMeta.get.unsafeGetNonNullable(rs, i + 0),
      purchaseorderid = PurchaseorderheaderId.get.unsafeGetNonNullable(rs, i + 1),
      purchaseorderdetailid = Meta.IntMeta.get.unsafeGetNonNullable(rs, i + 2),
      duedate = TypoLocalDateTime.get.unsafeGetNonNullable(rs, i + 3),
      orderqty = Meta.IntMeta.get.unsafeGetNonNullable(rs, i + 4),
      productid = ProductId.get.unsafeGetNonNullable(rs, i + 5),
      unitprice = Meta.ScalaBigDecimalMeta.get.unsafeGetNonNullable(rs, i + 6),
      receivedqty = Meta.ScalaBigDecimalMeta.get.unsafeGetNonNullable(rs, i + 7),
      rejectedqty = Meta.ScalaBigDecimalMeta.get.unsafeGetNonNullable(rs, i + 8),
      modifieddate = TypoLocalDateTime.get.unsafeGetNonNullable(rs, i + 9)
    )
  )
}
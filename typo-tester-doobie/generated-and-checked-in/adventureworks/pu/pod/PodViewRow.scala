/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pu
package pod

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoShort
import adventureworks.production.product.ProductId
import adventureworks.purchasing.purchaseorderheader.PurchaseorderheaderId
import doobie.util.Read
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.Encoder

/** View: pu.pod */
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
  orderqty: TypoShort,
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
  implicit lazy val decoder: Decoder[PodViewRow] = Decoder.forProduct10[PodViewRow, Int, PurchaseorderheaderId, Int, TypoLocalDateTime, TypoShort, ProductId, BigDecimal, BigDecimal, BigDecimal, TypoLocalDateTime]("id", "purchaseorderid", "purchaseorderdetailid", "duedate", "orderqty", "productid", "unitprice", "receivedqty", "rejectedqty", "modifieddate")(PodViewRow.apply)(Decoder.decodeInt, PurchaseorderheaderId.decoder, Decoder.decodeInt, TypoLocalDateTime.decoder, TypoShort.decoder, ProductId.decoder, Decoder.decodeBigDecimal, Decoder.decodeBigDecimal, Decoder.decodeBigDecimal, TypoLocalDateTime.decoder)
  implicit lazy val encoder: Encoder[PodViewRow] = Encoder.forProduct10[PodViewRow, Int, PurchaseorderheaderId, Int, TypoLocalDateTime, TypoShort, ProductId, BigDecimal, BigDecimal, BigDecimal, TypoLocalDateTime]("id", "purchaseorderid", "purchaseorderdetailid", "duedate", "orderqty", "productid", "unitprice", "receivedqty", "rejectedqty", "modifieddate")(x => (x.id, x.purchaseorderid, x.purchaseorderdetailid, x.duedate, x.orderqty, x.productid, x.unitprice, x.receivedqty, x.rejectedqty, x.modifieddate))(Encoder.encodeInt, PurchaseorderheaderId.encoder, Encoder.encodeInt, TypoLocalDateTime.encoder, TypoShort.encoder, ProductId.encoder, Encoder.encodeBigDecimal, Encoder.encodeBigDecimal, Encoder.encodeBigDecimal, TypoLocalDateTime.encoder)
  implicit lazy val read: Read[PodViewRow] = new Read.CompositeOfInstances(Array(
    new Read.Single(Meta.IntMeta.get).asInstanceOf[Read[Any]],
      new Read.Single(PurchaseorderheaderId.get).asInstanceOf[Read[Any]],
      new Read.Single(Meta.IntMeta.get).asInstanceOf[Read[Any]],
      new Read.Single(TypoLocalDateTime.get).asInstanceOf[Read[Any]],
      new Read.Single(TypoShort.get).asInstanceOf[Read[Any]],
      new Read.Single(ProductId.get).asInstanceOf[Read[Any]],
      new Read.Single(Meta.ScalaBigDecimalMeta.get).asInstanceOf[Read[Any]],
      new Read.Single(Meta.ScalaBigDecimalMeta.get).asInstanceOf[Read[Any]],
      new Read.Single(Meta.ScalaBigDecimalMeta.get).asInstanceOf[Read[Any]],
      new Read.Single(TypoLocalDateTime.get).asInstanceOf[Read[Any]]
  ))(using scala.reflect.ClassTag.Any).map { arr =>
    PodViewRow(
      id = arr(0).asInstanceOf[Int],
          purchaseorderid = arr(1).asInstanceOf[PurchaseorderheaderId],
          purchaseorderdetailid = arr(2).asInstanceOf[Int],
          duedate = arr(3).asInstanceOf[TypoLocalDateTime],
          orderqty = arr(4).asInstanceOf[TypoShort],
          productid = arr(5).asInstanceOf[ProductId],
          unitprice = arr(6).asInstanceOf[BigDecimal],
          receivedqty = arr(7).asInstanceOf[BigDecimal],
          rejectedqty = arr(8).asInstanceOf[BigDecimal],
          modifieddate = arr(9).asInstanceOf[TypoLocalDateTime]
    )
  }
}

/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pu
package pod

import adventureworks.TypoLocalDateTime
import adventureworks.production.product.ProductId
import adventureworks.purchasing.purchaseorderheader.PurchaseorderheaderId
import anorm.Column
import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.OWrites
import play.api.libs.json.Reads
import play.api.libs.json.Writes
import scala.collection.immutable.ListMap
import scala.util.Try

case class PodViewRow(
  id: Option[Int],
  /** Points to [[purchasing.purchaseorderdetail.PurchaseorderdetailRow.purchaseorderid]] */
  purchaseorderid: Option[PurchaseorderheaderId],
  /** Points to [[purchasing.purchaseorderdetail.PurchaseorderdetailRow.purchaseorderdetailid]] */
  purchaseorderdetailid: Option[Int],
  /** Points to [[purchasing.purchaseorderdetail.PurchaseorderdetailRow.duedate]] */
  duedate: Option[TypoLocalDateTime],
  /** Points to [[purchasing.purchaseorderdetail.PurchaseorderdetailRow.orderqty]] */
  orderqty: Option[Int],
  /** Points to [[purchasing.purchaseorderdetail.PurchaseorderdetailRow.productid]] */
  productid: Option[ProductId],
  /** Points to [[purchasing.purchaseorderdetail.PurchaseorderdetailRow.unitprice]] */
  unitprice: Option[BigDecimal],
  /** Points to [[purchasing.purchaseorderdetail.PurchaseorderdetailRow.receivedqty]] */
  receivedqty: Option[BigDecimal],
  /** Points to [[purchasing.purchaseorderdetail.PurchaseorderdetailRow.rejectedqty]] */
  rejectedqty: Option[BigDecimal],
  /** Points to [[purchasing.purchaseorderdetail.PurchaseorderdetailRow.modifieddate]] */
  modifieddate: Option[TypoLocalDateTime]
)

object PodViewRow {
  implicit lazy val reads: Reads[PodViewRow] = Reads[PodViewRow](json => JsResult.fromTry(
      Try(
        PodViewRow(
          id = json.\("id").toOption.map(_.as(Reads.IntReads)),
          purchaseorderid = json.\("purchaseorderid").toOption.map(_.as(PurchaseorderheaderId.reads)),
          purchaseorderdetailid = json.\("purchaseorderdetailid").toOption.map(_.as(Reads.IntReads)),
          duedate = json.\("duedate").toOption.map(_.as(TypoLocalDateTime.reads)),
          orderqty = json.\("orderqty").toOption.map(_.as(Reads.IntReads)),
          productid = json.\("productid").toOption.map(_.as(ProductId.reads)),
          unitprice = json.\("unitprice").toOption.map(_.as(Reads.bigDecReads)),
          receivedqty = json.\("receivedqty").toOption.map(_.as(Reads.bigDecReads)),
          rejectedqty = json.\("rejectedqty").toOption.map(_.as(Reads.bigDecReads)),
          modifieddate = json.\("modifieddate").toOption.map(_.as(TypoLocalDateTime.reads))
        )
      )
    ),
  )
  def rowParser(idx: Int): RowParser[PodViewRow] = RowParser[PodViewRow] { row =>
    Success(
      PodViewRow(
        id = row(idx + 0)(Column.columnToOption(Column.columnToInt)),
        purchaseorderid = row(idx + 1)(Column.columnToOption(PurchaseorderheaderId.column)),
        purchaseorderdetailid = row(idx + 2)(Column.columnToOption(Column.columnToInt)),
        duedate = row(idx + 3)(Column.columnToOption(TypoLocalDateTime.column)),
        orderqty = row(idx + 4)(Column.columnToOption(Column.columnToInt)),
        productid = row(idx + 5)(Column.columnToOption(ProductId.column)),
        unitprice = row(idx + 6)(Column.columnToOption(Column.columnToScalaBigDecimal)),
        receivedqty = row(idx + 7)(Column.columnToOption(Column.columnToScalaBigDecimal)),
        rejectedqty = row(idx + 8)(Column.columnToOption(Column.columnToScalaBigDecimal)),
        modifieddate = row(idx + 9)(Column.columnToOption(TypoLocalDateTime.column))
      )
    )
  }
  implicit lazy val writes: OWrites[PodViewRow] = OWrites[PodViewRow](o =>
    new JsObject(ListMap[String, JsValue](
      "id" -> Writes.OptionWrites(Writes.IntWrites).writes(o.id),
      "purchaseorderid" -> Writes.OptionWrites(PurchaseorderheaderId.writes).writes(o.purchaseorderid),
      "purchaseorderdetailid" -> Writes.OptionWrites(Writes.IntWrites).writes(o.purchaseorderdetailid),
      "duedate" -> Writes.OptionWrites(TypoLocalDateTime.writes).writes(o.duedate),
      "orderqty" -> Writes.OptionWrites(Writes.IntWrites).writes(o.orderqty),
      "productid" -> Writes.OptionWrites(ProductId.writes).writes(o.productid),
      "unitprice" -> Writes.OptionWrites(Writes.BigDecimalWrites).writes(o.unitprice),
      "receivedqty" -> Writes.OptionWrites(Writes.BigDecimalWrites).writes(o.receivedqty),
      "rejectedqty" -> Writes.OptionWrites(Writes.BigDecimalWrites).writes(o.rejectedqty),
      "modifieddate" -> Writes.OptionWrites(TypoLocalDateTime.writes).writes(o.modifieddate)
    ))
  )
}
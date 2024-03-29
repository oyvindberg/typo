/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package transactionhistory

import adventureworks.Text
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.production.product.ProductId
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

case class TransactionhistoryRow(
  /** Primary key for TransactionHistory records.
      Default: nextval('production.transactionhistory_transactionid_seq'::regclass) */
  transactionid: TransactionhistoryId,
  /** Product identification number. Foreign key to Product.ProductID.
      Points to [[product.ProductRow.productid]] */
  productid: ProductId,
  /** Purchase order, sales order, or work order identification number. */
  referenceorderid: Int,
  /** Line number associated with the purchase order, sales order, or work order.
      Default: 0 */
  referenceorderlineid: Int,
  /** Date and time of the transaction.
      Default: now() */
  transactiondate: TypoLocalDateTime,
  /** W = WorkOrder, S = SalesOrder, P = PurchaseOrder
      Constraint CK_TransactionHistory_TransactionType affecting columns transactiontype: ((upper((transactiontype)::text) = ANY (ARRAY['W'::text, 'S'::text, 'P'::text]))) */
  transactiontype: /* bpchar, max 1 chars */ String,
  /** Product quantity. */
  quantity: Int,
  /** Product cost. */
  actualcost: BigDecimal,
  /** Default: now() */
  modifieddate: TypoLocalDateTime
)

object TransactionhistoryRow {
  implicit lazy val reads: Reads[TransactionhistoryRow] = Reads[TransactionhistoryRow](json => JsResult.fromTry(
      Try(
        TransactionhistoryRow(
          transactionid = json.\("transactionid").as(TransactionhistoryId.reads),
          productid = json.\("productid").as(ProductId.reads),
          referenceorderid = json.\("referenceorderid").as(Reads.IntReads),
          referenceorderlineid = json.\("referenceorderlineid").as(Reads.IntReads),
          transactiondate = json.\("transactiondate").as(TypoLocalDateTime.reads),
          transactiontype = json.\("transactiontype").as(Reads.StringReads),
          quantity = json.\("quantity").as(Reads.IntReads),
          actualcost = json.\("actualcost").as(Reads.bigDecReads),
          modifieddate = json.\("modifieddate").as(TypoLocalDateTime.reads)
        )
      )
    ),
  )
  def rowParser(idx: Int): RowParser[TransactionhistoryRow] = RowParser[TransactionhistoryRow] { row =>
    Success(
      TransactionhistoryRow(
        transactionid = row(idx + 0)(TransactionhistoryId.column),
        productid = row(idx + 1)(ProductId.column),
        referenceorderid = row(idx + 2)(Column.columnToInt),
        referenceorderlineid = row(idx + 3)(Column.columnToInt),
        transactiondate = row(idx + 4)(TypoLocalDateTime.column),
        transactiontype = row(idx + 5)(Column.columnToString),
        quantity = row(idx + 6)(Column.columnToInt),
        actualcost = row(idx + 7)(Column.columnToScalaBigDecimal),
        modifieddate = row(idx + 8)(TypoLocalDateTime.column)
      )
    )
  }
  implicit lazy val text: Text[TransactionhistoryRow] = Text.instance[TransactionhistoryRow]{ (row, sb) =>
    TransactionhistoryId.text.unsafeEncode(row.transactionid, sb)
    sb.append(Text.DELIMETER)
    ProductId.text.unsafeEncode(row.productid, sb)
    sb.append(Text.DELIMETER)
    Text.intInstance.unsafeEncode(row.referenceorderid, sb)
    sb.append(Text.DELIMETER)
    Text.intInstance.unsafeEncode(row.referenceorderlineid, sb)
    sb.append(Text.DELIMETER)
    TypoLocalDateTime.text.unsafeEncode(row.transactiondate, sb)
    sb.append(Text.DELIMETER)
    Text.stringInstance.unsafeEncode(row.transactiontype, sb)
    sb.append(Text.DELIMETER)
    Text.intInstance.unsafeEncode(row.quantity, sb)
    sb.append(Text.DELIMETER)
    Text.bigDecimalInstance.unsafeEncode(row.actualcost, sb)
    sb.append(Text.DELIMETER)
    TypoLocalDateTime.text.unsafeEncode(row.modifieddate, sb)
  }
  implicit lazy val writes: OWrites[TransactionhistoryRow] = OWrites[TransactionhistoryRow](o =>
    new JsObject(ListMap[String, JsValue](
      "transactionid" -> TransactionhistoryId.writes.writes(o.transactionid),
      "productid" -> ProductId.writes.writes(o.productid),
      "referenceorderid" -> Writes.IntWrites.writes(o.referenceorderid),
      "referenceorderlineid" -> Writes.IntWrites.writes(o.referenceorderlineid),
      "transactiondate" -> TypoLocalDateTime.writes.writes(o.transactiondate),
      "transactiontype" -> Writes.StringWrites.writes(o.transactiontype),
      "quantity" -> Writes.IntWrites.writes(o.quantity),
      "actualcost" -> Writes.BigDecimalWrites.writes(o.actualcost),
      "modifieddate" -> TypoLocalDateTime.writes.writes(o.modifieddate)
    ))
  )
}

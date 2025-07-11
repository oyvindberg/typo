/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package transactionhistory

import adventureworks.customtypes.Defaulted
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.production.product.ProductId
import doobie.postgres.Text
import doobie.util.Read
import doobie.util.Write
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.Encoder

/** Table: production.transactionhistory
    Record of each purchase order, sales order, or work order transaction year to date.
    Primary key: transactionid */
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
){
   val id = transactionid
   def toUnsavedRow(transactionid: Defaulted[TransactionhistoryId], referenceorderlineid: Defaulted[Int] = Defaulted.Provided(this.referenceorderlineid), transactiondate: Defaulted[TypoLocalDateTime] = Defaulted.Provided(this.transactiondate), modifieddate: Defaulted[TypoLocalDateTime] = Defaulted.Provided(this.modifieddate)): TransactionhistoryRowUnsaved =
     TransactionhistoryRowUnsaved(productid, referenceorderid, transactiontype, quantity, actualcost, transactionid, referenceorderlineid, transactiondate, modifieddate)
 }

object TransactionhistoryRow {
  implicit lazy val decoder: Decoder[TransactionhistoryRow] = Decoder.forProduct9[TransactionhistoryRow, TransactionhistoryId, ProductId, Int, Int, TypoLocalDateTime, /* bpchar, max 1 chars */ String, Int, BigDecimal, TypoLocalDateTime]("transactionid", "productid", "referenceorderid", "referenceorderlineid", "transactiondate", "transactiontype", "quantity", "actualcost", "modifieddate")(TransactionhistoryRow.apply)(TransactionhistoryId.decoder, ProductId.decoder, Decoder.decodeInt, Decoder.decodeInt, TypoLocalDateTime.decoder, Decoder.decodeString, Decoder.decodeInt, Decoder.decodeBigDecimal, TypoLocalDateTime.decoder)
  implicit lazy val encoder: Encoder[TransactionhistoryRow] = Encoder.forProduct9[TransactionhistoryRow, TransactionhistoryId, ProductId, Int, Int, TypoLocalDateTime, /* bpchar, max 1 chars */ String, Int, BigDecimal, TypoLocalDateTime]("transactionid", "productid", "referenceorderid", "referenceorderlineid", "transactiondate", "transactiontype", "quantity", "actualcost", "modifieddate")(x => (x.transactionid, x.productid, x.referenceorderid, x.referenceorderlineid, x.transactiondate, x.transactiontype, x.quantity, x.actualcost, x.modifieddate))(TransactionhistoryId.encoder, ProductId.encoder, Encoder.encodeInt, Encoder.encodeInt, TypoLocalDateTime.encoder, Encoder.encodeString, Encoder.encodeInt, Encoder.encodeBigDecimal, TypoLocalDateTime.encoder)
  implicit lazy val read: Read[TransactionhistoryRow] = new Read.CompositeOfInstances(Array(
    new Read.Single(TransactionhistoryId.get).asInstanceOf[Read[Any]],
      new Read.Single(ProductId.get).asInstanceOf[Read[Any]],
      new Read.Single(Meta.IntMeta.get).asInstanceOf[Read[Any]],
      new Read.Single(Meta.IntMeta.get).asInstanceOf[Read[Any]],
      new Read.Single(TypoLocalDateTime.get).asInstanceOf[Read[Any]],
      new Read.Single(Meta.StringMeta.get).asInstanceOf[Read[Any]],
      new Read.Single(Meta.IntMeta.get).asInstanceOf[Read[Any]],
      new Read.Single(Meta.ScalaBigDecimalMeta.get).asInstanceOf[Read[Any]],
      new Read.Single(TypoLocalDateTime.get).asInstanceOf[Read[Any]]
  ))(using scala.reflect.ClassTag.Any).map { arr =>
    TransactionhistoryRow(
      transactionid = arr(0).asInstanceOf[TransactionhistoryId],
          productid = arr(1).asInstanceOf[ProductId],
          referenceorderid = arr(2).asInstanceOf[Int],
          referenceorderlineid = arr(3).asInstanceOf[Int],
          transactiondate = arr(4).asInstanceOf[TypoLocalDateTime],
          transactiontype = arr(5).asInstanceOf[/* bpchar, max 1 chars */ String],
          quantity = arr(6).asInstanceOf[Int],
          actualcost = arr(7).asInstanceOf[BigDecimal],
          modifieddate = arr(8).asInstanceOf[TypoLocalDateTime]
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
  implicit lazy val write: Write[TransactionhistoryRow] = new Write.Composite[TransactionhistoryRow](
    List(new Write.Single(TransactionhistoryId.put),
         new Write.Single(ProductId.put),
         new Write.Single(Meta.IntMeta.put),
         new Write.Single(Meta.IntMeta.put),
         new Write.Single(TypoLocalDateTime.put),
         new Write.Single(Meta.StringMeta.put),
         new Write.Single(Meta.IntMeta.put),
         new Write.Single(Meta.ScalaBigDecimalMeta.put),
         new Write.Single(TypoLocalDateTime.put)),
    a => List(a.transactionid, a.productid, a.referenceorderid, a.referenceorderlineid, a.transactiondate, a.transactiontype, a.quantity, a.actualcost, a.modifieddate)
  )
}

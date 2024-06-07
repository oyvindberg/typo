/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package th

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.production.product.ProductId
import adventureworks.production.transactionhistory.TransactionhistoryId
import java.sql.ResultSet
import zio.jdbc.JdbcDecoder
import zio.json.JsonDecoder
import zio.json.JsonEncoder
import zio.json.ast.Json
import zio.json.internal.Write

/** View: pr.th */
case class ThViewRow(
  /** Points to [[production.transactionhistory.TransactionhistoryRow.transactionid]] */
  id: TransactionhistoryId,
  /** Points to [[production.transactionhistory.TransactionhistoryRow.transactionid]] */
  transactionid: TransactionhistoryId,
  /** Points to [[production.transactionhistory.TransactionhistoryRow.productid]] */
  productid: ProductId,
  /** Points to [[production.transactionhistory.TransactionhistoryRow.referenceorderid]] */
  referenceorderid: Int,
  /** Points to [[production.transactionhistory.TransactionhistoryRow.referenceorderlineid]] */
  referenceorderlineid: Int,
  /** Points to [[production.transactionhistory.TransactionhistoryRow.transactiondate]] */
  transactiondate: TypoLocalDateTime,
  /** Points to [[production.transactionhistory.TransactionhistoryRow.transactiontype]] */
  transactiontype: /* bpchar, max 1 chars */ String,
  /** Points to [[production.transactionhistory.TransactionhistoryRow.quantity]] */
  quantity: Int,
  /** Points to [[production.transactionhistory.TransactionhistoryRow.actualcost]] */
  actualcost: BigDecimal,
  /** Points to [[production.transactionhistory.TransactionhistoryRow.modifieddate]] */
  modifieddate: TypoLocalDateTime
)

object ThViewRow {
  implicit lazy val jdbcDecoder: JdbcDecoder[ThViewRow] = new JdbcDecoder[ThViewRow] {
    override def unsafeDecode(columIndex: Int, rs: ResultSet): (Int, ThViewRow) =
      columIndex + 9 ->
        ThViewRow(
          id = TransactionhistoryId.jdbcDecoder.unsafeDecode(columIndex + 0, rs)._2,
          transactionid = TransactionhistoryId.jdbcDecoder.unsafeDecode(columIndex + 1, rs)._2,
          productid = ProductId.jdbcDecoder.unsafeDecode(columIndex + 2, rs)._2,
          referenceorderid = JdbcDecoder.intDecoder.unsafeDecode(columIndex + 3, rs)._2,
          referenceorderlineid = JdbcDecoder.intDecoder.unsafeDecode(columIndex + 4, rs)._2,
          transactiondate = TypoLocalDateTime.jdbcDecoder.unsafeDecode(columIndex + 5, rs)._2,
          transactiontype = JdbcDecoder.stringDecoder.unsafeDecode(columIndex + 6, rs)._2,
          quantity = JdbcDecoder.intDecoder.unsafeDecode(columIndex + 7, rs)._2,
          actualcost = JdbcDecoder.bigDecimalDecoderScala.unsafeDecode(columIndex + 8, rs)._2,
          modifieddate = TypoLocalDateTime.jdbcDecoder.unsafeDecode(columIndex + 9, rs)._2
        )
  }
  implicit lazy val jsonDecoder: JsonDecoder[ThViewRow] = JsonDecoder[Json.Obj].mapOrFail { jsonObj =>
    val id = jsonObj.get("id").toRight("Missing field 'id'").flatMap(_.as(TransactionhistoryId.jsonDecoder))
    val transactionid = jsonObj.get("transactionid").toRight("Missing field 'transactionid'").flatMap(_.as(TransactionhistoryId.jsonDecoder))
    val productid = jsonObj.get("productid").toRight("Missing field 'productid'").flatMap(_.as(ProductId.jsonDecoder))
    val referenceorderid = jsonObj.get("referenceorderid").toRight("Missing field 'referenceorderid'").flatMap(_.as(JsonDecoder.int))
    val referenceorderlineid = jsonObj.get("referenceorderlineid").toRight("Missing field 'referenceorderlineid'").flatMap(_.as(JsonDecoder.int))
    val transactiondate = jsonObj.get("transactiondate").toRight("Missing field 'transactiondate'").flatMap(_.as(TypoLocalDateTime.jsonDecoder))
    val transactiontype = jsonObj.get("transactiontype").toRight("Missing field 'transactiontype'").flatMap(_.as(JsonDecoder.string))
    val quantity = jsonObj.get("quantity").toRight("Missing field 'quantity'").flatMap(_.as(JsonDecoder.int))
    val actualcost = jsonObj.get("actualcost").toRight("Missing field 'actualcost'").flatMap(_.as(JsonDecoder.scalaBigDecimal))
    val modifieddate = jsonObj.get("modifieddate").toRight("Missing field 'modifieddate'").flatMap(_.as(TypoLocalDateTime.jsonDecoder))
    if (id.isRight && transactionid.isRight && productid.isRight && referenceorderid.isRight && referenceorderlineid.isRight && transactiondate.isRight && transactiontype.isRight && quantity.isRight && actualcost.isRight && modifieddate.isRight)
      Right(ThViewRow(id = id.toOption.get, transactionid = transactionid.toOption.get, productid = productid.toOption.get, referenceorderid = referenceorderid.toOption.get, referenceorderlineid = referenceorderlineid.toOption.get, transactiondate = transactiondate.toOption.get, transactiontype = transactiontype.toOption.get, quantity = quantity.toOption.get, actualcost = actualcost.toOption.get, modifieddate = modifieddate.toOption.get))
    else Left(List[Either[String, Any]](id, transactionid, productid, referenceorderid, referenceorderlineid, transactiondate, transactiontype, quantity, actualcost, modifieddate).flatMap(_.left.toOption).mkString(", "))
  }
  implicit lazy val jsonEncoder: JsonEncoder[ThViewRow] = new JsonEncoder[ThViewRow] {
    override def unsafeEncode(a: ThViewRow, indent: Option[Int], out: Write): Unit = {
      out.write("{")
      out.write(""""id":""")
      TransactionhistoryId.jsonEncoder.unsafeEncode(a.id, indent, out)
      out.write(",")
      out.write(""""transactionid":""")
      TransactionhistoryId.jsonEncoder.unsafeEncode(a.transactionid, indent, out)
      out.write(",")
      out.write(""""productid":""")
      ProductId.jsonEncoder.unsafeEncode(a.productid, indent, out)
      out.write(",")
      out.write(""""referenceorderid":""")
      JsonEncoder.int.unsafeEncode(a.referenceorderid, indent, out)
      out.write(",")
      out.write(""""referenceorderlineid":""")
      JsonEncoder.int.unsafeEncode(a.referenceorderlineid, indent, out)
      out.write(",")
      out.write(""""transactiondate":""")
      TypoLocalDateTime.jsonEncoder.unsafeEncode(a.transactiondate, indent, out)
      out.write(",")
      out.write(""""transactiontype":""")
      JsonEncoder.string.unsafeEncode(a.transactiontype, indent, out)
      out.write(",")
      out.write(""""quantity":""")
      JsonEncoder.int.unsafeEncode(a.quantity, indent, out)
      out.write(",")
      out.write(""""actualcost":""")
      JsonEncoder.scalaBigDecimal.unsafeEncode(a.actualcost, indent, out)
      out.write(",")
      out.write(""""modifieddate":""")
      TypoLocalDateTime.jsonEncoder.unsafeEncode(a.modifieddate, indent, out)
      out.write("}")
    }
  }
}
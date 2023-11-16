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
import java.sql.ResultSet
import zio.jdbc.JdbcDecoder
import zio.json.JsonDecoder
import zio.json.JsonEncoder
import zio.json.ast.Json
import zio.json.internal.Write

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
  implicit lazy val jdbcDecoder: JdbcDecoder[PodViewRow] = new JdbcDecoder[PodViewRow] {
    override def unsafeDecode(columIndex: Int, rs: ResultSet): (Int, PodViewRow) =
      columIndex + 9 ->
        PodViewRow(
          id = JdbcDecoder.intDecoder.unsafeDecode(columIndex + 0, rs)._2,
          purchaseorderid = PurchaseorderheaderId.jdbcDecoder.unsafeDecode(columIndex + 1, rs)._2,
          purchaseorderdetailid = JdbcDecoder.intDecoder.unsafeDecode(columIndex + 2, rs)._2,
          duedate = TypoLocalDateTime.jdbcDecoder.unsafeDecode(columIndex + 3, rs)._2,
          orderqty = TypoShort.jdbcDecoder.unsafeDecode(columIndex + 4, rs)._2,
          productid = ProductId.jdbcDecoder.unsafeDecode(columIndex + 5, rs)._2,
          unitprice = JdbcDecoder.bigDecimalDecoderScala.unsafeDecode(columIndex + 6, rs)._2,
          receivedqty = JdbcDecoder.bigDecimalDecoderScala.unsafeDecode(columIndex + 7, rs)._2,
          rejectedqty = JdbcDecoder.bigDecimalDecoderScala.unsafeDecode(columIndex + 8, rs)._2,
          modifieddate = TypoLocalDateTime.jdbcDecoder.unsafeDecode(columIndex + 9, rs)._2
        )
  }
  implicit lazy val jsonDecoder: JsonDecoder[PodViewRow] = JsonDecoder[Json.Obj].mapOrFail { jsonObj =>
    val id = jsonObj.get("id").toRight("Missing field 'id'").flatMap(_.as(JsonDecoder.int))
    val purchaseorderid = jsonObj.get("purchaseorderid").toRight("Missing field 'purchaseorderid'").flatMap(_.as(PurchaseorderheaderId.jsonDecoder))
    val purchaseorderdetailid = jsonObj.get("purchaseorderdetailid").toRight("Missing field 'purchaseorderdetailid'").flatMap(_.as(JsonDecoder.int))
    val duedate = jsonObj.get("duedate").toRight("Missing field 'duedate'").flatMap(_.as(TypoLocalDateTime.jsonDecoder))
    val orderqty = jsonObj.get("orderqty").toRight("Missing field 'orderqty'").flatMap(_.as(TypoShort.jsonDecoder))
    val productid = jsonObj.get("productid").toRight("Missing field 'productid'").flatMap(_.as(ProductId.jsonDecoder))
    val unitprice = jsonObj.get("unitprice").toRight("Missing field 'unitprice'").flatMap(_.as(JsonDecoder.scalaBigDecimal))
    val receivedqty = jsonObj.get("receivedqty").toRight("Missing field 'receivedqty'").flatMap(_.as(JsonDecoder.scalaBigDecimal))
    val rejectedqty = jsonObj.get("rejectedqty").toRight("Missing field 'rejectedqty'").flatMap(_.as(JsonDecoder.scalaBigDecimal))
    val modifieddate = jsonObj.get("modifieddate").toRight("Missing field 'modifieddate'").flatMap(_.as(TypoLocalDateTime.jsonDecoder))
    if (id.isRight && purchaseorderid.isRight && purchaseorderdetailid.isRight && duedate.isRight && orderqty.isRight && productid.isRight && unitprice.isRight && receivedqty.isRight && rejectedqty.isRight && modifieddate.isRight)
      Right(PodViewRow(id = id.toOption.get, purchaseorderid = purchaseorderid.toOption.get, purchaseorderdetailid = purchaseorderdetailid.toOption.get, duedate = duedate.toOption.get, orderqty = orderqty.toOption.get, productid = productid.toOption.get, unitprice = unitprice.toOption.get, receivedqty = receivedqty.toOption.get, rejectedqty = rejectedqty.toOption.get, modifieddate = modifieddate.toOption.get))
    else Left(List[Either[String, Any]](id, purchaseorderid, purchaseorderdetailid, duedate, orderqty, productid, unitprice, receivedqty, rejectedqty, modifieddate).flatMap(_.left.toOption).mkString(", "))
  }
  implicit lazy val jsonEncoder: JsonEncoder[PodViewRow] = new JsonEncoder[PodViewRow] {
    override def unsafeEncode(a: PodViewRow, indent: Option[Int], out: Write): Unit = {
      out.write("{")
      out.write(""""id":""")
      JsonEncoder.int.unsafeEncode(a.id, indent, out)
      out.write(",")
      out.write(""""purchaseorderid":""")
      PurchaseorderheaderId.jsonEncoder.unsafeEncode(a.purchaseorderid, indent, out)
      out.write(",")
      out.write(""""purchaseorderdetailid":""")
      JsonEncoder.int.unsafeEncode(a.purchaseorderdetailid, indent, out)
      out.write(",")
      out.write(""""duedate":""")
      TypoLocalDateTime.jsonEncoder.unsafeEncode(a.duedate, indent, out)
      out.write(",")
      out.write(""""orderqty":""")
      TypoShort.jsonEncoder.unsafeEncode(a.orderqty, indent, out)
      out.write(",")
      out.write(""""productid":""")
      ProductId.jsonEncoder.unsafeEncode(a.productid, indent, out)
      out.write(",")
      out.write(""""unitprice":""")
      JsonEncoder.scalaBigDecimal.unsafeEncode(a.unitprice, indent, out)
      out.write(",")
      out.write(""""receivedqty":""")
      JsonEncoder.scalaBigDecimal.unsafeEncode(a.receivedqty, indent, out)
      out.write(",")
      out.write(""""rejectedqty":""")
      JsonEncoder.scalaBigDecimal.unsafeEncode(a.rejectedqty, indent, out)
      out.write(",")
      out.write(""""modifieddate":""")
      TypoLocalDateTime.jsonEncoder.unsafeEncode(a.modifieddate, indent, out)
      out.write("}")
    }
  }
}
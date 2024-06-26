/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package bom

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoShort
import adventureworks.production.product.ProductId
import adventureworks.production.unitmeasure.UnitmeasureId
import java.sql.ResultSet
import zio.jdbc.JdbcDecoder
import zio.json.JsonDecoder
import zio.json.JsonEncoder
import zio.json.ast.Json
import zio.json.internal.Write

/** View: pr.bom */
case class BomViewRow(
  /** Points to [[production.billofmaterials.BillofmaterialsRow.billofmaterialsid]] */
  id: Int,
  /** Points to [[production.billofmaterials.BillofmaterialsRow.billofmaterialsid]] */
  billofmaterialsid: Int,
  /** Points to [[production.billofmaterials.BillofmaterialsRow.productassemblyid]] */
  productassemblyid: Option[ProductId],
  /** Points to [[production.billofmaterials.BillofmaterialsRow.componentid]] */
  componentid: ProductId,
  /** Points to [[production.billofmaterials.BillofmaterialsRow.startdate]] */
  startdate: TypoLocalDateTime,
  /** Points to [[production.billofmaterials.BillofmaterialsRow.enddate]] */
  enddate: Option[TypoLocalDateTime],
  /** Points to [[production.billofmaterials.BillofmaterialsRow.unitmeasurecode]] */
  unitmeasurecode: UnitmeasureId,
  /** Points to [[production.billofmaterials.BillofmaterialsRow.bomlevel]] */
  bomlevel: TypoShort,
  /** Points to [[production.billofmaterials.BillofmaterialsRow.perassemblyqty]] */
  perassemblyqty: BigDecimal,
  /** Points to [[production.billofmaterials.BillofmaterialsRow.modifieddate]] */
  modifieddate: TypoLocalDateTime
)

object BomViewRow {
  implicit lazy val jdbcDecoder: JdbcDecoder[BomViewRow] = new JdbcDecoder[BomViewRow] {
    override def unsafeDecode(columIndex: Int, rs: ResultSet): (Int, BomViewRow) =
      columIndex + 9 ->
        BomViewRow(
          id = JdbcDecoder.intDecoder.unsafeDecode(columIndex + 0, rs)._2,
          billofmaterialsid = JdbcDecoder.intDecoder.unsafeDecode(columIndex + 1, rs)._2,
          productassemblyid = JdbcDecoder.optionDecoder(ProductId.jdbcDecoder).unsafeDecode(columIndex + 2, rs)._2,
          componentid = ProductId.jdbcDecoder.unsafeDecode(columIndex + 3, rs)._2,
          startdate = TypoLocalDateTime.jdbcDecoder.unsafeDecode(columIndex + 4, rs)._2,
          enddate = JdbcDecoder.optionDecoder(TypoLocalDateTime.jdbcDecoder).unsafeDecode(columIndex + 5, rs)._2,
          unitmeasurecode = UnitmeasureId.jdbcDecoder.unsafeDecode(columIndex + 6, rs)._2,
          bomlevel = TypoShort.jdbcDecoder.unsafeDecode(columIndex + 7, rs)._2,
          perassemblyqty = JdbcDecoder.bigDecimalDecoderScala.unsafeDecode(columIndex + 8, rs)._2,
          modifieddate = TypoLocalDateTime.jdbcDecoder.unsafeDecode(columIndex + 9, rs)._2
        )
  }
  implicit lazy val jsonDecoder: JsonDecoder[BomViewRow] = JsonDecoder[Json.Obj].mapOrFail { jsonObj =>
    val id = jsonObj.get("id").toRight("Missing field 'id'").flatMap(_.as(JsonDecoder.int))
    val billofmaterialsid = jsonObj.get("billofmaterialsid").toRight("Missing field 'billofmaterialsid'").flatMap(_.as(JsonDecoder.int))
    val productassemblyid = jsonObj.get("productassemblyid").fold[Either[String, Option[ProductId]]](Right(None))(_.as(JsonDecoder.option(using ProductId.jsonDecoder)))
    val componentid = jsonObj.get("componentid").toRight("Missing field 'componentid'").flatMap(_.as(ProductId.jsonDecoder))
    val startdate = jsonObj.get("startdate").toRight("Missing field 'startdate'").flatMap(_.as(TypoLocalDateTime.jsonDecoder))
    val enddate = jsonObj.get("enddate").fold[Either[String, Option[TypoLocalDateTime]]](Right(None))(_.as(JsonDecoder.option(using TypoLocalDateTime.jsonDecoder)))
    val unitmeasurecode = jsonObj.get("unitmeasurecode").toRight("Missing field 'unitmeasurecode'").flatMap(_.as(UnitmeasureId.jsonDecoder))
    val bomlevel = jsonObj.get("bomlevel").toRight("Missing field 'bomlevel'").flatMap(_.as(TypoShort.jsonDecoder))
    val perassemblyqty = jsonObj.get("perassemblyqty").toRight("Missing field 'perassemblyqty'").flatMap(_.as(JsonDecoder.scalaBigDecimal))
    val modifieddate = jsonObj.get("modifieddate").toRight("Missing field 'modifieddate'").flatMap(_.as(TypoLocalDateTime.jsonDecoder))
    if (id.isRight && billofmaterialsid.isRight && productassemblyid.isRight && componentid.isRight && startdate.isRight && enddate.isRight && unitmeasurecode.isRight && bomlevel.isRight && perassemblyqty.isRight && modifieddate.isRight)
      Right(BomViewRow(id = id.toOption.get, billofmaterialsid = billofmaterialsid.toOption.get, productassemblyid = productassemblyid.toOption.get, componentid = componentid.toOption.get, startdate = startdate.toOption.get, enddate = enddate.toOption.get, unitmeasurecode = unitmeasurecode.toOption.get, bomlevel = bomlevel.toOption.get, perassemblyqty = perassemblyqty.toOption.get, modifieddate = modifieddate.toOption.get))
    else Left(List[Either[String, Any]](id, billofmaterialsid, productassemblyid, componentid, startdate, enddate, unitmeasurecode, bomlevel, perassemblyqty, modifieddate).flatMap(_.left.toOption).mkString(", "))
  }
  implicit lazy val jsonEncoder: JsonEncoder[BomViewRow] = new JsonEncoder[BomViewRow] {
    override def unsafeEncode(a: BomViewRow, indent: Option[Int], out: Write): Unit = {
      out.write("{")
      out.write(""""id":""")
      JsonEncoder.int.unsafeEncode(a.id, indent, out)
      out.write(",")
      out.write(""""billofmaterialsid":""")
      JsonEncoder.int.unsafeEncode(a.billofmaterialsid, indent, out)
      out.write(",")
      out.write(""""productassemblyid":""")
      JsonEncoder.option(using ProductId.jsonEncoder).unsafeEncode(a.productassemblyid, indent, out)
      out.write(",")
      out.write(""""componentid":""")
      ProductId.jsonEncoder.unsafeEncode(a.componentid, indent, out)
      out.write(",")
      out.write(""""startdate":""")
      TypoLocalDateTime.jsonEncoder.unsafeEncode(a.startdate, indent, out)
      out.write(",")
      out.write(""""enddate":""")
      JsonEncoder.option(using TypoLocalDateTime.jsonEncoder).unsafeEncode(a.enddate, indent, out)
      out.write(",")
      out.write(""""unitmeasurecode":""")
      UnitmeasureId.jsonEncoder.unsafeEncode(a.unitmeasurecode, indent, out)
      out.write(",")
      out.write(""""bomlevel":""")
      TypoShort.jsonEncoder.unsafeEncode(a.bomlevel, indent, out)
      out.write(",")
      out.write(""""perassemblyqty":""")
      JsonEncoder.scalaBigDecimal.unsafeEncode(a.perassemblyqty, indent, out)
      out.write(",")
      out.write(""""modifieddate":""")
      TypoLocalDateTime.jsonEncoder.unsafeEncode(a.modifieddate, indent, out)
      out.write("}")
    }
  }
}

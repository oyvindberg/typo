/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package product

import adventureworks.customtypes.Defaulted
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoShort
import adventureworks.customtypes.TypoUUID
import adventureworks.production.productmodel.ProductmodelId
import adventureworks.production.productsubcategory.ProductsubcategoryId
import adventureworks.production.unitmeasure.UnitmeasureId
import adventureworks.public.Flag
import adventureworks.public.Name
import java.sql.ResultSet
import zio.jdbc.JdbcDecoder
import zio.json.JsonDecoder
import zio.json.JsonEncoder
import zio.json.ast.Json
import zio.json.internal.Write

/** Table: production.product
    Products sold or used in the manfacturing of sold products.
    Primary key: productid */
case class ProductRow(
  /** Primary key for Product records.
      Default: nextval('production.product_productid_seq'::regclass) */
  productid: ProductId,
  /** Name of the product. */
  name: Name,
  /** Unique product identification number. */
  productnumber: /* max 25 chars */ String,
  /** 0 = Product is purchased, 1 = Product is manufactured in-house.
      Default: true */
  makeflag: Flag,
  /** 0 = Product is not a salable item. 1 = Product is salable.
      Default: true */
  finishedgoodsflag: Flag,
  /** Product color. */
  color: Option[/* max 15 chars */ String],
  /** Minimum inventory quantity.
      Constraint CK_Product_SafetyStockLevel affecting columns safetystocklevel: ((safetystocklevel > 0)) */
  safetystocklevel: TypoShort,
  /** Inventory level that triggers a purchase order or work order.
      Constraint CK_Product_ReorderPoint affecting columns reorderpoint: ((reorderpoint > 0)) */
  reorderpoint: TypoShort,
  /** Standard cost of the product.
      Constraint CK_Product_StandardCost affecting columns standardcost: ((standardcost >= 0.00)) */
  standardcost: BigDecimal,
  /** Selling price.
      Constraint CK_Product_ListPrice affecting columns listprice: ((listprice >= 0.00)) */
  listprice: BigDecimal,
  /** Product size. */
  size: Option[/* max 5 chars */ String],
  /** Unit of measure for Size column.
      Points to [[unitmeasure.UnitmeasureRow.unitmeasurecode]] */
  sizeunitmeasurecode: Option[UnitmeasureId],
  /** Unit of measure for Weight column.
      Points to [[unitmeasure.UnitmeasureRow.unitmeasurecode]] */
  weightunitmeasurecode: Option[UnitmeasureId],
  /** Product weight.
      Constraint CK_Product_Weight affecting columns weight: ((weight > 0.00)) */
  weight: Option[BigDecimal],
  /** Number of days required to manufacture the product.
      Constraint CK_Product_DaysToManufacture affecting columns daystomanufacture: ((daystomanufacture >= 0)) */
  daystomanufacture: Int,
  /** R = Road, M = Mountain, T = Touring, S = Standard
      Constraint CK_Product_ProductLine affecting columns productline: (((upper((productline)::text) = ANY (ARRAY['S'::text, 'T'::text, 'M'::text, 'R'::text])) OR (productline IS NULL))) */
  productline: Option[/* bpchar, max 2 chars */ String],
  /** H = High, M = Medium, L = Low
      Constraint CK_Product_Class affecting columns class: (((upper((class)::text) = ANY (ARRAY['L'::text, 'M'::text, 'H'::text])) OR (class IS NULL))) */
  `class`: Option[/* bpchar, max 2 chars */ String],
  /** W = Womens, M = Mens, U = Universal
      Constraint CK_Product_Style affecting columns style: (((upper((style)::text) = ANY (ARRAY['W'::text, 'M'::text, 'U'::text])) OR (style IS NULL))) */
  style: Option[/* bpchar, max 2 chars */ String],
  /** Product is a member of this product subcategory. Foreign key to ProductSubCategory.ProductSubCategoryID.
      Points to [[productsubcategory.ProductsubcategoryRow.productsubcategoryid]] */
  productsubcategoryid: Option[ProductsubcategoryId],
  /** Product is a member of this product model. Foreign key to ProductModel.ProductModelID.
      Points to [[productmodel.ProductmodelRow.productmodelid]] */
  productmodelid: Option[ProductmodelId],
  /** Date the product was available for sale.
      Constraint CK_Product_SellEndDate affecting columns sellenddate, sellstartdate: (((sellenddate >= sellstartdate) OR (sellenddate IS NULL))) */
  sellstartdate: TypoLocalDateTime,
  /** Date the product was no longer available for sale.
      Constraint CK_Product_SellEndDate affecting columns sellenddate, sellstartdate: (((sellenddate >= sellstartdate) OR (sellenddate IS NULL))) */
  sellenddate: Option[TypoLocalDateTime],
  /** Date the product was discontinued. */
  discontinueddate: Option[TypoLocalDateTime],
  /** Default: uuid_generate_v1() */
  rowguid: TypoUUID,
  /** Default: now() */
  modifieddate: TypoLocalDateTime
){
   val id = productid
   def toUnsavedRow(productid: Defaulted[ProductId], makeflag: Defaulted[Flag] = Defaulted.Provided(this.makeflag), finishedgoodsflag: Defaulted[Flag] = Defaulted.Provided(this.finishedgoodsflag), rowguid: Defaulted[TypoUUID] = Defaulted.Provided(this.rowguid), modifieddate: Defaulted[TypoLocalDateTime] = Defaulted.Provided(this.modifieddate)): ProductRowUnsaved =
     ProductRowUnsaved(name, productnumber, color, safetystocklevel, reorderpoint, standardcost, listprice, size, sizeunitmeasurecode, weightunitmeasurecode, weight, daystomanufacture, productline, `class`, style, productsubcategoryid, productmodelid, sellstartdate, sellenddate, discontinueddate, productid, makeflag, finishedgoodsflag, rowguid, modifieddate)
 }

object ProductRow {
  implicit lazy val jdbcDecoder: JdbcDecoder[ProductRow] = new JdbcDecoder[ProductRow] {
    override def unsafeDecode(columIndex: Int, rs: ResultSet): (Int, ProductRow) =
      columIndex + 24 ->
        ProductRow(
          productid = ProductId.jdbcDecoder.unsafeDecode(columIndex + 0, rs)._2,
          name = Name.jdbcDecoder.unsafeDecode(columIndex + 1, rs)._2,
          productnumber = JdbcDecoder.stringDecoder.unsafeDecode(columIndex + 2, rs)._2,
          makeflag = Flag.jdbcDecoder.unsafeDecode(columIndex + 3, rs)._2,
          finishedgoodsflag = Flag.jdbcDecoder.unsafeDecode(columIndex + 4, rs)._2,
          color = JdbcDecoder.optionDecoder(JdbcDecoder.stringDecoder).unsafeDecode(columIndex + 5, rs)._2,
          safetystocklevel = TypoShort.jdbcDecoder.unsafeDecode(columIndex + 6, rs)._2,
          reorderpoint = TypoShort.jdbcDecoder.unsafeDecode(columIndex + 7, rs)._2,
          standardcost = JdbcDecoder.bigDecimalDecoderScala.unsafeDecode(columIndex + 8, rs)._2,
          listprice = JdbcDecoder.bigDecimalDecoderScala.unsafeDecode(columIndex + 9, rs)._2,
          size = JdbcDecoder.optionDecoder(JdbcDecoder.stringDecoder).unsafeDecode(columIndex + 10, rs)._2,
          sizeunitmeasurecode = JdbcDecoder.optionDecoder(UnitmeasureId.jdbcDecoder).unsafeDecode(columIndex + 11, rs)._2,
          weightunitmeasurecode = JdbcDecoder.optionDecoder(UnitmeasureId.jdbcDecoder).unsafeDecode(columIndex + 12, rs)._2,
          weight = JdbcDecoder.optionDecoder(JdbcDecoder.bigDecimalDecoderScala).unsafeDecode(columIndex + 13, rs)._2,
          daystomanufacture = JdbcDecoder.intDecoder.unsafeDecode(columIndex + 14, rs)._2,
          productline = JdbcDecoder.optionDecoder(JdbcDecoder.stringDecoder).unsafeDecode(columIndex + 15, rs)._2,
          `class` = JdbcDecoder.optionDecoder(JdbcDecoder.stringDecoder).unsafeDecode(columIndex + 16, rs)._2,
          style = JdbcDecoder.optionDecoder(JdbcDecoder.stringDecoder).unsafeDecode(columIndex + 17, rs)._2,
          productsubcategoryid = JdbcDecoder.optionDecoder(ProductsubcategoryId.jdbcDecoder).unsafeDecode(columIndex + 18, rs)._2,
          productmodelid = JdbcDecoder.optionDecoder(ProductmodelId.jdbcDecoder).unsafeDecode(columIndex + 19, rs)._2,
          sellstartdate = TypoLocalDateTime.jdbcDecoder.unsafeDecode(columIndex + 20, rs)._2,
          sellenddate = JdbcDecoder.optionDecoder(TypoLocalDateTime.jdbcDecoder).unsafeDecode(columIndex + 21, rs)._2,
          discontinueddate = JdbcDecoder.optionDecoder(TypoLocalDateTime.jdbcDecoder).unsafeDecode(columIndex + 22, rs)._2,
          rowguid = TypoUUID.jdbcDecoder.unsafeDecode(columIndex + 23, rs)._2,
          modifieddate = TypoLocalDateTime.jdbcDecoder.unsafeDecode(columIndex + 24, rs)._2
        )
  }
  implicit lazy val jsonDecoder: JsonDecoder[ProductRow] = JsonDecoder[Json.Obj].mapOrFail { jsonObj =>
    val productid = jsonObj.get("productid").toRight("Missing field 'productid'").flatMap(_.as(ProductId.jsonDecoder))
    val name = jsonObj.get("name").toRight("Missing field 'name'").flatMap(_.as(Name.jsonDecoder))
    val productnumber = jsonObj.get("productnumber").toRight("Missing field 'productnumber'").flatMap(_.as(JsonDecoder.string))
    val makeflag = jsonObj.get("makeflag").toRight("Missing field 'makeflag'").flatMap(_.as(Flag.jsonDecoder))
    val finishedgoodsflag = jsonObj.get("finishedgoodsflag").toRight("Missing field 'finishedgoodsflag'").flatMap(_.as(Flag.jsonDecoder))
    val color = jsonObj.get("color").fold[Either[String, Option[String]]](Right(None))(_.as(JsonDecoder.option(using JsonDecoder.string)))
    val safetystocklevel = jsonObj.get("safetystocklevel").toRight("Missing field 'safetystocklevel'").flatMap(_.as(TypoShort.jsonDecoder))
    val reorderpoint = jsonObj.get("reorderpoint").toRight("Missing field 'reorderpoint'").flatMap(_.as(TypoShort.jsonDecoder))
    val standardcost = jsonObj.get("standardcost").toRight("Missing field 'standardcost'").flatMap(_.as(JsonDecoder.scalaBigDecimal))
    val listprice = jsonObj.get("listprice").toRight("Missing field 'listprice'").flatMap(_.as(JsonDecoder.scalaBigDecimal))
    val size = jsonObj.get("size").fold[Either[String, Option[String]]](Right(None))(_.as(JsonDecoder.option(using JsonDecoder.string)))
    val sizeunitmeasurecode = jsonObj.get("sizeunitmeasurecode").fold[Either[String, Option[UnitmeasureId]]](Right(None))(_.as(JsonDecoder.option(using UnitmeasureId.jsonDecoder)))
    val weightunitmeasurecode = jsonObj.get("weightunitmeasurecode").fold[Either[String, Option[UnitmeasureId]]](Right(None))(_.as(JsonDecoder.option(using UnitmeasureId.jsonDecoder)))
    val weight = jsonObj.get("weight").fold[Either[String, Option[BigDecimal]]](Right(None))(_.as(JsonDecoder.option(using JsonDecoder.scalaBigDecimal)))
    val daystomanufacture = jsonObj.get("daystomanufacture").toRight("Missing field 'daystomanufacture'").flatMap(_.as(JsonDecoder.int))
    val productline = jsonObj.get("productline").fold[Either[String, Option[String]]](Right(None))(_.as(JsonDecoder.option(using JsonDecoder.string)))
    val `class` = jsonObj.get("class").fold[Either[String, Option[String]]](Right(None))(_.as(JsonDecoder.option(using JsonDecoder.string)))
    val style = jsonObj.get("style").fold[Either[String, Option[String]]](Right(None))(_.as(JsonDecoder.option(using JsonDecoder.string)))
    val productsubcategoryid = jsonObj.get("productsubcategoryid").fold[Either[String, Option[ProductsubcategoryId]]](Right(None))(_.as(JsonDecoder.option(using ProductsubcategoryId.jsonDecoder)))
    val productmodelid = jsonObj.get("productmodelid").fold[Either[String, Option[ProductmodelId]]](Right(None))(_.as(JsonDecoder.option(using ProductmodelId.jsonDecoder)))
    val sellstartdate = jsonObj.get("sellstartdate").toRight("Missing field 'sellstartdate'").flatMap(_.as(TypoLocalDateTime.jsonDecoder))
    val sellenddate = jsonObj.get("sellenddate").fold[Either[String, Option[TypoLocalDateTime]]](Right(None))(_.as(JsonDecoder.option(using TypoLocalDateTime.jsonDecoder)))
    val discontinueddate = jsonObj.get("discontinueddate").fold[Either[String, Option[TypoLocalDateTime]]](Right(None))(_.as(JsonDecoder.option(using TypoLocalDateTime.jsonDecoder)))
    val rowguid = jsonObj.get("rowguid").toRight("Missing field 'rowguid'").flatMap(_.as(TypoUUID.jsonDecoder))
    val modifieddate = jsonObj.get("modifieddate").toRight("Missing field 'modifieddate'").flatMap(_.as(TypoLocalDateTime.jsonDecoder))
    if (productid.isRight && name.isRight && productnumber.isRight && makeflag.isRight && finishedgoodsflag.isRight && color.isRight && safetystocklevel.isRight && reorderpoint.isRight && standardcost.isRight && listprice.isRight && size.isRight && sizeunitmeasurecode.isRight && weightunitmeasurecode.isRight && weight.isRight && daystomanufacture.isRight && productline.isRight && `class`.isRight && style.isRight && productsubcategoryid.isRight && productmodelid.isRight && sellstartdate.isRight && sellenddate.isRight && discontinueddate.isRight && rowguid.isRight && modifieddate.isRight)
      Right(ProductRow(productid = productid.toOption.get, name = name.toOption.get, productnumber = productnumber.toOption.get, makeflag = makeflag.toOption.get, finishedgoodsflag = finishedgoodsflag.toOption.get, color = color.toOption.get, safetystocklevel = safetystocklevel.toOption.get, reorderpoint = reorderpoint.toOption.get, standardcost = standardcost.toOption.get, listprice = listprice.toOption.get, size = size.toOption.get, sizeunitmeasurecode = sizeunitmeasurecode.toOption.get, weightunitmeasurecode = weightunitmeasurecode.toOption.get, weight = weight.toOption.get, daystomanufacture = daystomanufacture.toOption.get, productline = productline.toOption.get, `class` = `class`.toOption.get, style = style.toOption.get, productsubcategoryid = productsubcategoryid.toOption.get, productmodelid = productmodelid.toOption.get, sellstartdate = sellstartdate.toOption.get, sellenddate = sellenddate.toOption.get, discontinueddate = discontinueddate.toOption.get, rowguid = rowguid.toOption.get, modifieddate = modifieddate.toOption.get))
    else Left(List[Either[String, Any]](productid, name, productnumber, makeflag, finishedgoodsflag, color, safetystocklevel, reorderpoint, standardcost, listprice, size, sizeunitmeasurecode, weightunitmeasurecode, weight, daystomanufacture, productline, `class`, style, productsubcategoryid, productmodelid, sellstartdate, sellenddate, discontinueddate, rowguid, modifieddate).flatMap(_.left.toOption).mkString(", "))
  }
  implicit lazy val jsonEncoder: JsonEncoder[ProductRow] = new JsonEncoder[ProductRow] {
    override def unsafeEncode(a: ProductRow, indent: Option[Int], out: Write): Unit = {
      out.write("{")
      out.write(""""productid":""")
      ProductId.jsonEncoder.unsafeEncode(a.productid, indent, out)
      out.write(",")
      out.write(""""name":""")
      Name.jsonEncoder.unsafeEncode(a.name, indent, out)
      out.write(",")
      out.write(""""productnumber":""")
      JsonEncoder.string.unsafeEncode(a.productnumber, indent, out)
      out.write(",")
      out.write(""""makeflag":""")
      Flag.jsonEncoder.unsafeEncode(a.makeflag, indent, out)
      out.write(",")
      out.write(""""finishedgoodsflag":""")
      Flag.jsonEncoder.unsafeEncode(a.finishedgoodsflag, indent, out)
      out.write(",")
      out.write(""""color":""")
      JsonEncoder.option(using JsonEncoder.string).unsafeEncode(a.color, indent, out)
      out.write(",")
      out.write(""""safetystocklevel":""")
      TypoShort.jsonEncoder.unsafeEncode(a.safetystocklevel, indent, out)
      out.write(",")
      out.write(""""reorderpoint":""")
      TypoShort.jsonEncoder.unsafeEncode(a.reorderpoint, indent, out)
      out.write(",")
      out.write(""""standardcost":""")
      JsonEncoder.scalaBigDecimal.unsafeEncode(a.standardcost, indent, out)
      out.write(",")
      out.write(""""listprice":""")
      JsonEncoder.scalaBigDecimal.unsafeEncode(a.listprice, indent, out)
      out.write(",")
      out.write(""""size":""")
      JsonEncoder.option(using JsonEncoder.string).unsafeEncode(a.size, indent, out)
      out.write(",")
      out.write(""""sizeunitmeasurecode":""")
      JsonEncoder.option(using UnitmeasureId.jsonEncoder).unsafeEncode(a.sizeunitmeasurecode, indent, out)
      out.write(",")
      out.write(""""weightunitmeasurecode":""")
      JsonEncoder.option(using UnitmeasureId.jsonEncoder).unsafeEncode(a.weightunitmeasurecode, indent, out)
      out.write(",")
      out.write(""""weight":""")
      JsonEncoder.option(using JsonEncoder.scalaBigDecimal).unsafeEncode(a.weight, indent, out)
      out.write(",")
      out.write(""""daystomanufacture":""")
      JsonEncoder.int.unsafeEncode(a.daystomanufacture, indent, out)
      out.write(",")
      out.write(""""productline":""")
      JsonEncoder.option(using JsonEncoder.string).unsafeEncode(a.productline, indent, out)
      out.write(",")
      out.write(""""class":""")
      JsonEncoder.option(using JsonEncoder.string).unsafeEncode(a.`class`, indent, out)
      out.write(",")
      out.write(""""style":""")
      JsonEncoder.option(using JsonEncoder.string).unsafeEncode(a.style, indent, out)
      out.write(",")
      out.write(""""productsubcategoryid":""")
      JsonEncoder.option(using ProductsubcategoryId.jsonEncoder).unsafeEncode(a.productsubcategoryid, indent, out)
      out.write(",")
      out.write(""""productmodelid":""")
      JsonEncoder.option(using ProductmodelId.jsonEncoder).unsafeEncode(a.productmodelid, indent, out)
      out.write(",")
      out.write(""""sellstartdate":""")
      TypoLocalDateTime.jsonEncoder.unsafeEncode(a.sellstartdate, indent, out)
      out.write(",")
      out.write(""""sellenddate":""")
      JsonEncoder.option(using TypoLocalDateTime.jsonEncoder).unsafeEncode(a.sellenddate, indent, out)
      out.write(",")
      out.write(""""discontinueddate":""")
      JsonEncoder.option(using TypoLocalDateTime.jsonEncoder).unsafeEncode(a.discontinueddate, indent, out)
      out.write(",")
      out.write(""""rowguid":""")
      TypoUUID.jsonEncoder.unsafeEncode(a.rowguid, indent, out)
      out.write(",")
      out.write(""""modifieddate":""")
      TypoLocalDateTime.jsonEncoder.unsafeEncode(a.modifieddate, indent, out)
      out.write("}")
    }
  }
  implicit lazy val text: Text[ProductRow] = Text.instance[ProductRow]{ (row, sb) =>
    ProductId.text.unsafeEncode(row.productid, sb)
    sb.append(Text.DELIMETER)
    Name.text.unsafeEncode(row.name, sb)
    sb.append(Text.DELIMETER)
    Text.stringInstance.unsafeEncode(row.productnumber, sb)
    sb.append(Text.DELIMETER)
    Flag.text.unsafeEncode(row.makeflag, sb)
    sb.append(Text.DELIMETER)
    Flag.text.unsafeEncode(row.finishedgoodsflag, sb)
    sb.append(Text.DELIMETER)
    Text.option(Text.stringInstance).unsafeEncode(row.color, sb)
    sb.append(Text.DELIMETER)
    TypoShort.text.unsafeEncode(row.safetystocklevel, sb)
    sb.append(Text.DELIMETER)
    TypoShort.text.unsafeEncode(row.reorderpoint, sb)
    sb.append(Text.DELIMETER)
    Text.bigDecimalInstance.unsafeEncode(row.standardcost, sb)
    sb.append(Text.DELIMETER)
    Text.bigDecimalInstance.unsafeEncode(row.listprice, sb)
    sb.append(Text.DELIMETER)
    Text.option(Text.stringInstance).unsafeEncode(row.size, sb)
    sb.append(Text.DELIMETER)
    Text.option(UnitmeasureId.text).unsafeEncode(row.sizeunitmeasurecode, sb)
    sb.append(Text.DELIMETER)
    Text.option(UnitmeasureId.text).unsafeEncode(row.weightunitmeasurecode, sb)
    sb.append(Text.DELIMETER)
    Text.option(Text.bigDecimalInstance).unsafeEncode(row.weight, sb)
    sb.append(Text.DELIMETER)
    Text.intInstance.unsafeEncode(row.daystomanufacture, sb)
    sb.append(Text.DELIMETER)
    Text.option(Text.stringInstance).unsafeEncode(row.productline, sb)
    sb.append(Text.DELIMETER)
    Text.option(Text.stringInstance).unsafeEncode(row.`class`, sb)
    sb.append(Text.DELIMETER)
    Text.option(Text.stringInstance).unsafeEncode(row.style, sb)
    sb.append(Text.DELIMETER)
    Text.option(ProductsubcategoryId.text).unsafeEncode(row.productsubcategoryid, sb)
    sb.append(Text.DELIMETER)
    Text.option(ProductmodelId.text).unsafeEncode(row.productmodelid, sb)
    sb.append(Text.DELIMETER)
    TypoLocalDateTime.text.unsafeEncode(row.sellstartdate, sb)
    sb.append(Text.DELIMETER)
    Text.option(TypoLocalDateTime.text).unsafeEncode(row.sellenddate, sb)
    sb.append(Text.DELIMETER)
    Text.option(TypoLocalDateTime.text).unsafeEncode(row.discontinueddate, sb)
    sb.append(Text.DELIMETER)
    TypoUUID.text.unsafeEncode(row.rowguid, sb)
    sb.append(Text.DELIMETER)
    TypoLocalDateTime.text.unsafeEncode(row.modifieddate, sb)
  }
}

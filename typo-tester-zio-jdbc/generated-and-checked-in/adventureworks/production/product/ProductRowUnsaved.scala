/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package product

import adventureworks.Text
import adventureworks.customtypes.Defaulted
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoShort
import adventureworks.customtypes.TypoUUID
import adventureworks.production.productmodel.ProductmodelId
import adventureworks.production.productsubcategory.ProductsubcategoryId
import adventureworks.production.unitmeasure.UnitmeasureId
import adventureworks.public.Flag
import adventureworks.public.Name
import zio.json.JsonDecoder
import zio.json.JsonEncoder
import zio.json.ast.Json
import zio.json.internal.Write

/** This class corresponds to a row in table `production.product` which has not been persisted yet */
case class ProductRowUnsaved(
  /** Name of the product. */
  name: Name,
  /** Unique product identification number. */
  productnumber: /* max 25 chars */ String,
  /** Product color. */
  color: Option[/* max 15 chars */ String],
  /** Minimum inventory quantity.
      Constraint CK_Product_SafetyStockLevel affecting columns s, a, f, e, t, y, s, t, o, c, k, l, e, v, e, l:  ((safetystocklevel > 0)) */
  safetystocklevel: TypoShort,
  /** Inventory level that triggers a purchase order or work order.
      Constraint CK_Product_ReorderPoint affecting columns r, e, o, r, d, e, r, p, o, i, n, t:  ((reorderpoint > 0)) */
  reorderpoint: TypoShort,
  /** Standard cost of the product.
      Constraint CK_Product_StandardCost affecting columns s, t, a, n, d, a, r, d, c, o, s, t:  ((standardcost >= 0.00)) */
  standardcost: BigDecimal,
  /** Selling price.
      Constraint CK_Product_ListPrice affecting columns l, i, s, t, p, r, i, c, e:  ((listprice >= 0.00)) */
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
      Constraint CK_Product_Weight affecting columns w, e, i, g, h, t:  ((weight > 0.00)) */
  weight: Option[BigDecimal],
  /** Number of days required to manufacture the product.
      Constraint CK_Product_DaysToManufacture affecting columns d, a, y, s, t, o, m, a, n, u, f, a, c, t, u, r, e:  ((daystomanufacture >= 0)) */
  daystomanufacture: Int,
  /** R = Road, M = Mountain, T = Touring, S = Standard
      Constraint CK_Product_ProductLine affecting columns p, r, o, d, u, c, t, l, i, n, e:  (((upper((productline)::text) = ANY (ARRAY['S'::text, 'T'::text, 'M'::text, 'R'::text])) OR (productline IS NULL))) */
  productline: Option[/* bpchar, max 2 chars */ String],
  /** H = High, M = Medium, L = Low
      Constraint CK_Product_Class affecting columns c, l, a, s, s:  (((upper((class)::text) = ANY (ARRAY['L'::text, 'M'::text, 'H'::text])) OR (class IS NULL))) */
  `class`: Option[/* bpchar, max 2 chars */ String],
  /** W = Womens, M = Mens, U = Universal
      Constraint CK_Product_Style affecting columns s, t, y, l, e:  (((upper((style)::text) = ANY (ARRAY['W'::text, 'M'::text, 'U'::text])) OR (style IS NULL))) */
  style: Option[/* bpchar, max 2 chars */ String],
  /** Product is a member of this product subcategory. Foreign key to ProductSubCategory.ProductSubCategoryID.
      Points to [[productsubcategory.ProductsubcategoryRow.productsubcategoryid]] */
  productsubcategoryid: Option[ProductsubcategoryId],
  /** Product is a member of this product model. Foreign key to ProductModel.ProductModelID.
      Points to [[productmodel.ProductmodelRow.productmodelid]] */
  productmodelid: Option[ProductmodelId],
  /** Date the product was available for sale.
      Constraint CK_Product_SellEndDate affecting columns s, e, l, l, e, n, d, d, a, t, e, ,,  , s, e, l, l, s, t, a, r, t, d, a, t, e:  (((sellenddate >= sellstartdate) OR (sellenddate IS NULL))) */
  sellstartdate: TypoLocalDateTime,
  /** Date the product was no longer available for sale.
      Constraint CK_Product_SellEndDate affecting columns s, e, l, l, e, n, d, d, a, t, e, ,,  , s, e, l, l, s, t, a, r, t, d, a, t, e:  (((sellenddate >= sellstartdate) OR (sellenddate IS NULL))) */
  sellenddate: Option[TypoLocalDateTime],
  /** Date the product was discontinued. */
  discontinueddate: Option[TypoLocalDateTime],
  /** Default: nextval('production.product_productid_seq'::regclass)
      Primary key for Product records. */
  productid: Defaulted[ProductId] = Defaulted.UseDefault,
  /** Default: true
      0 = Product is purchased, 1 = Product is manufactured in-house. */
  makeflag: Defaulted[Flag] = Defaulted.UseDefault,
  /** Default: true
      0 = Product is not a salable item. 1 = Product is salable. */
  finishedgoodsflag: Defaulted[Flag] = Defaulted.UseDefault,
  /** Default: uuid_generate_v1() */
  rowguid: Defaulted[TypoUUID] = Defaulted.UseDefault,
  /** Default: now() */
  modifieddate: Defaulted[TypoLocalDateTime] = Defaulted.UseDefault
) {
  def toRow(productidDefault: => ProductId, makeflagDefault: => Flag, finishedgoodsflagDefault: => Flag, rowguidDefault: => TypoUUID, modifieddateDefault: => TypoLocalDateTime): ProductRow =
    ProductRow(
      name = name,
      productnumber = productnumber,
      color = color,
      safetystocklevel = safetystocklevel,
      reorderpoint = reorderpoint,
      standardcost = standardcost,
      listprice = listprice,
      size = size,
      sizeunitmeasurecode = sizeunitmeasurecode,
      weightunitmeasurecode = weightunitmeasurecode,
      weight = weight,
      daystomanufacture = daystomanufacture,
      productline = productline,
      `class` = `class`,
      style = style,
      productsubcategoryid = productsubcategoryid,
      productmodelid = productmodelid,
      sellstartdate = sellstartdate,
      sellenddate = sellenddate,
      discontinueddate = discontinueddate,
      productid = productid match {
                    case Defaulted.UseDefault => productidDefault
                    case Defaulted.Provided(value) => value
                  },
      makeflag = makeflag match {
                   case Defaulted.UseDefault => makeflagDefault
                   case Defaulted.Provided(value) => value
                 },
      finishedgoodsflag = finishedgoodsflag match {
                            case Defaulted.UseDefault => finishedgoodsflagDefault
                            case Defaulted.Provided(value) => value
                          },
      rowguid = rowguid match {
                  case Defaulted.UseDefault => rowguidDefault
                  case Defaulted.Provided(value) => value
                },
      modifieddate = modifieddate match {
                       case Defaulted.UseDefault => modifieddateDefault
                       case Defaulted.Provided(value) => value
                     }
    )
}
object ProductRowUnsaved {
  implicit lazy val jsonDecoder: JsonDecoder[ProductRowUnsaved] = JsonDecoder[Json.Obj].mapOrFail { jsonObj =>
    val name = jsonObj.get("name").toRight("Missing field 'name'").flatMap(_.as(Name.jsonDecoder))
    val productnumber = jsonObj.get("productnumber").toRight("Missing field 'productnumber'").flatMap(_.as(JsonDecoder.string))
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
    val productid = jsonObj.get("productid").toRight("Missing field 'productid'").flatMap(_.as(Defaulted.jsonDecoder(ProductId.jsonDecoder)))
    val makeflag = jsonObj.get("makeflag").toRight("Missing field 'makeflag'").flatMap(_.as(Defaulted.jsonDecoder(Flag.jsonDecoder)))
    val finishedgoodsflag = jsonObj.get("finishedgoodsflag").toRight("Missing field 'finishedgoodsflag'").flatMap(_.as(Defaulted.jsonDecoder(Flag.jsonDecoder)))
    val rowguid = jsonObj.get("rowguid").toRight("Missing field 'rowguid'").flatMap(_.as(Defaulted.jsonDecoder(TypoUUID.jsonDecoder)))
    val modifieddate = jsonObj.get("modifieddate").toRight("Missing field 'modifieddate'").flatMap(_.as(Defaulted.jsonDecoder(TypoLocalDateTime.jsonDecoder)))
    if (name.isRight && productnumber.isRight && color.isRight && safetystocklevel.isRight && reorderpoint.isRight && standardcost.isRight && listprice.isRight && size.isRight && sizeunitmeasurecode.isRight && weightunitmeasurecode.isRight && weight.isRight && daystomanufacture.isRight && productline.isRight && `class`.isRight && style.isRight && productsubcategoryid.isRight && productmodelid.isRight && sellstartdate.isRight && sellenddate.isRight && discontinueddate.isRight && productid.isRight && makeflag.isRight && finishedgoodsflag.isRight && rowguid.isRight && modifieddate.isRight)
      Right(ProductRowUnsaved(name = name.toOption.get, productnumber = productnumber.toOption.get, color = color.toOption.get, safetystocklevel = safetystocklevel.toOption.get, reorderpoint = reorderpoint.toOption.get, standardcost = standardcost.toOption.get, listprice = listprice.toOption.get, size = size.toOption.get, sizeunitmeasurecode = sizeunitmeasurecode.toOption.get, weightunitmeasurecode = weightunitmeasurecode.toOption.get, weight = weight.toOption.get, daystomanufacture = daystomanufacture.toOption.get, productline = productline.toOption.get, `class` = `class`.toOption.get, style = style.toOption.get, productsubcategoryid = productsubcategoryid.toOption.get, productmodelid = productmodelid.toOption.get, sellstartdate = sellstartdate.toOption.get, sellenddate = sellenddate.toOption.get, discontinueddate = discontinueddate.toOption.get, productid = productid.toOption.get, makeflag = makeflag.toOption.get, finishedgoodsflag = finishedgoodsflag.toOption.get, rowguid = rowguid.toOption.get, modifieddate = modifieddate.toOption.get))
    else Left(List[Either[String, Any]](name, productnumber, color, safetystocklevel, reorderpoint, standardcost, listprice, size, sizeunitmeasurecode, weightunitmeasurecode, weight, daystomanufacture, productline, `class`, style, productsubcategoryid, productmodelid, sellstartdate, sellenddate, discontinueddate, productid, makeflag, finishedgoodsflag, rowguid, modifieddate).flatMap(_.left.toOption).mkString(", "))
  }
  implicit lazy val jsonEncoder: JsonEncoder[ProductRowUnsaved] = new JsonEncoder[ProductRowUnsaved] {
    override def unsafeEncode(a: ProductRowUnsaved, indent: Option[Int], out: Write): Unit = {
      out.write("{")
      out.write(""""name":""")
      Name.jsonEncoder.unsafeEncode(a.name, indent, out)
      out.write(",")
      out.write(""""productnumber":""")
      JsonEncoder.string.unsafeEncode(a.productnumber, indent, out)
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
      out.write(""""productid":""")
      Defaulted.jsonEncoder(ProductId.jsonEncoder).unsafeEncode(a.productid, indent, out)
      out.write(",")
      out.write(""""makeflag":""")
      Defaulted.jsonEncoder(Flag.jsonEncoder).unsafeEncode(a.makeflag, indent, out)
      out.write(",")
      out.write(""""finishedgoodsflag":""")
      Defaulted.jsonEncoder(Flag.jsonEncoder).unsafeEncode(a.finishedgoodsflag, indent, out)
      out.write(",")
      out.write(""""rowguid":""")
      Defaulted.jsonEncoder(TypoUUID.jsonEncoder).unsafeEncode(a.rowguid, indent, out)
      out.write(",")
      out.write(""""modifieddate":""")
      Defaulted.jsonEncoder(TypoLocalDateTime.jsonEncoder).unsafeEncode(a.modifieddate, indent, out)
      out.write("}")
    }
  }
  implicit lazy val text: Text[ProductRowUnsaved] = Text.instance[ProductRowUnsaved]{ (row, sb) =>
    Name.text.unsafeEncode(row.name, sb)
    sb.append(Text.DELIMETER)
    Text.stringInstance.unsafeEncode(row.productnumber, sb)
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
    Defaulted.text(ProductId.text).unsafeEncode(row.productid, sb)
    sb.append(Text.DELIMETER)
    Defaulted.text(Flag.text).unsafeEncode(row.makeflag, sb)
    sb.append(Text.DELIMETER)
    Defaulted.text(Flag.text).unsafeEncode(row.finishedgoodsflag, sb)
    sb.append(Text.DELIMETER)
    Defaulted.text(TypoUUID.text).unsafeEncode(row.rowguid, sb)
    sb.append(Text.DELIMETER)
    Defaulted.text(TypoLocalDateTime.text).unsafeEncode(row.modifieddate, sb)
  }
}
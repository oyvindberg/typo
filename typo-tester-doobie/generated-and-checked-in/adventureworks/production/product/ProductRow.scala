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
import doobie.postgres.Text
import doobie.util.Read
import doobie.util.Write
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.DecodingFailure
import io.circe.Encoder
import io.circe.HCursor
import io.circe.Json
import scala.util.Try

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
  implicit lazy val decoder: Decoder[ProductRow] = Decoder.instanceTry[ProductRow]((c: HCursor) =>
    Try {
      def orThrow[R](either: Either[DecodingFailure, R]): R = either match {
        case Left(err) => throw err
        case Right(r)  => r
      }
      ProductRow(
        productid = orThrow(c.get("productid")(ProductId.decoder)),
        name = orThrow(c.get("name")(Name.decoder)),
        productnumber = orThrow(c.get("productnumber")(Decoder.decodeString)),
        makeflag = orThrow(c.get("makeflag")(Flag.decoder)),
        finishedgoodsflag = orThrow(c.get("finishedgoodsflag")(Flag.decoder)),
        color = orThrow(c.get("color")(Decoder.decodeOption(Decoder.decodeString))),
        safetystocklevel = orThrow(c.get("safetystocklevel")(TypoShort.decoder)),
        reorderpoint = orThrow(c.get("reorderpoint")(TypoShort.decoder)),
        standardcost = orThrow(c.get("standardcost")(Decoder.decodeBigDecimal)),
        listprice = orThrow(c.get("listprice")(Decoder.decodeBigDecimal)),
        size = orThrow(c.get("size")(Decoder.decodeOption(Decoder.decodeString))),
        sizeunitmeasurecode = orThrow(c.get("sizeunitmeasurecode")(Decoder.decodeOption(UnitmeasureId.decoder))),
        weightunitmeasurecode = orThrow(c.get("weightunitmeasurecode")(Decoder.decodeOption(UnitmeasureId.decoder))),
        weight = orThrow(c.get("weight")(Decoder.decodeOption(Decoder.decodeBigDecimal))),
        daystomanufacture = orThrow(c.get("daystomanufacture")(Decoder.decodeInt)),
        productline = orThrow(c.get("productline")(Decoder.decodeOption(Decoder.decodeString))),
        `class` = orThrow(c.get("class")(Decoder.decodeOption(Decoder.decodeString))),
        style = orThrow(c.get("style")(Decoder.decodeOption(Decoder.decodeString))),
        productsubcategoryid = orThrow(c.get("productsubcategoryid")(Decoder.decodeOption(ProductsubcategoryId.decoder))),
        productmodelid = orThrow(c.get("productmodelid")(Decoder.decodeOption(ProductmodelId.decoder))),
        sellstartdate = orThrow(c.get("sellstartdate")(TypoLocalDateTime.decoder)),
        sellenddate = orThrow(c.get("sellenddate")(Decoder.decodeOption(TypoLocalDateTime.decoder))),
        discontinueddate = orThrow(c.get("discontinueddate")(Decoder.decodeOption(TypoLocalDateTime.decoder))),
        rowguid = orThrow(c.get("rowguid")(TypoUUID.decoder)),
        modifieddate = orThrow(c.get("modifieddate")(TypoLocalDateTime.decoder))
      )
    }
  )
  implicit lazy val encoder: Encoder[ProductRow] = Encoder[ProductRow](row =>
    Json.obj(
      "productid" -> ProductId.encoder.apply(row.productid),
      "name" -> Name.encoder.apply(row.name),
      "productnumber" -> Encoder.encodeString.apply(row.productnumber),
      "makeflag" -> Flag.encoder.apply(row.makeflag),
      "finishedgoodsflag" -> Flag.encoder.apply(row.finishedgoodsflag),
      "color" -> Encoder.encodeOption(Encoder.encodeString).apply(row.color),
      "safetystocklevel" -> TypoShort.encoder.apply(row.safetystocklevel),
      "reorderpoint" -> TypoShort.encoder.apply(row.reorderpoint),
      "standardcost" -> Encoder.encodeBigDecimal.apply(row.standardcost),
      "listprice" -> Encoder.encodeBigDecimal.apply(row.listprice),
      "size" -> Encoder.encodeOption(Encoder.encodeString).apply(row.size),
      "sizeunitmeasurecode" -> Encoder.encodeOption(UnitmeasureId.encoder).apply(row.sizeunitmeasurecode),
      "weightunitmeasurecode" -> Encoder.encodeOption(UnitmeasureId.encoder).apply(row.weightunitmeasurecode),
      "weight" -> Encoder.encodeOption(Encoder.encodeBigDecimal).apply(row.weight),
      "daystomanufacture" -> Encoder.encodeInt.apply(row.daystomanufacture),
      "productline" -> Encoder.encodeOption(Encoder.encodeString).apply(row.productline),
      "class" -> Encoder.encodeOption(Encoder.encodeString).apply(row.`class`),
      "style" -> Encoder.encodeOption(Encoder.encodeString).apply(row.style),
      "productsubcategoryid" -> Encoder.encodeOption(ProductsubcategoryId.encoder).apply(row.productsubcategoryid),
      "productmodelid" -> Encoder.encodeOption(ProductmodelId.encoder).apply(row.productmodelid),
      "sellstartdate" -> TypoLocalDateTime.encoder.apply(row.sellstartdate),
      "sellenddate" -> Encoder.encodeOption(TypoLocalDateTime.encoder).apply(row.sellenddate),
      "discontinueddate" -> Encoder.encodeOption(TypoLocalDateTime.encoder).apply(row.discontinueddate),
      "rowguid" -> TypoUUID.encoder.apply(row.rowguid),
      "modifieddate" -> TypoLocalDateTime.encoder.apply(row.modifieddate)
    )
  )
  implicit lazy val read: Read[ProductRow] = new Read.CompositeOfInstances(Array(
    new Read.Single(ProductId.get).asInstanceOf[Read[Any]],
      new Read.Single(Name.get).asInstanceOf[Read[Any]],
      new Read.Single(Meta.StringMeta.get).asInstanceOf[Read[Any]],
      new Read.Single(Flag.get).asInstanceOf[Read[Any]],
      new Read.Single(Flag.get).asInstanceOf[Read[Any]],
      new Read.SingleOpt(Meta.StringMeta.get).asInstanceOf[Read[Any]],
      new Read.Single(TypoShort.get).asInstanceOf[Read[Any]],
      new Read.Single(TypoShort.get).asInstanceOf[Read[Any]],
      new Read.Single(Meta.ScalaBigDecimalMeta.get).asInstanceOf[Read[Any]],
      new Read.Single(Meta.ScalaBigDecimalMeta.get).asInstanceOf[Read[Any]],
      new Read.SingleOpt(Meta.StringMeta.get).asInstanceOf[Read[Any]],
      new Read.SingleOpt(UnitmeasureId.get).asInstanceOf[Read[Any]],
      new Read.SingleOpt(UnitmeasureId.get).asInstanceOf[Read[Any]],
      new Read.SingleOpt(Meta.ScalaBigDecimalMeta.get).asInstanceOf[Read[Any]],
      new Read.Single(Meta.IntMeta.get).asInstanceOf[Read[Any]],
      new Read.SingleOpt(Meta.StringMeta.get).asInstanceOf[Read[Any]],
      new Read.SingleOpt(Meta.StringMeta.get).asInstanceOf[Read[Any]],
      new Read.SingleOpt(Meta.StringMeta.get).asInstanceOf[Read[Any]],
      new Read.SingleOpt(ProductsubcategoryId.get).asInstanceOf[Read[Any]],
      new Read.SingleOpt(ProductmodelId.get).asInstanceOf[Read[Any]],
      new Read.Single(TypoLocalDateTime.get).asInstanceOf[Read[Any]],
      new Read.SingleOpt(TypoLocalDateTime.get).asInstanceOf[Read[Any]],
      new Read.SingleOpt(TypoLocalDateTime.get).asInstanceOf[Read[Any]],
      new Read.Single(TypoUUID.get).asInstanceOf[Read[Any]],
      new Read.Single(TypoLocalDateTime.get).asInstanceOf[Read[Any]]
  ))(using scala.reflect.ClassTag.Any).map { arr =>
    ProductRow(
      productid = arr(0).asInstanceOf[ProductId],
          name = arr(1).asInstanceOf[Name],
          productnumber = arr(2).asInstanceOf[/* max 25 chars */ String],
          makeflag = arr(3).asInstanceOf[Flag],
          finishedgoodsflag = arr(4).asInstanceOf[Flag],
          color = arr(5).asInstanceOf[Option[/* max 15 chars */ String]],
          safetystocklevel = arr(6).asInstanceOf[TypoShort],
          reorderpoint = arr(7).asInstanceOf[TypoShort],
          standardcost = arr(8).asInstanceOf[BigDecimal],
          listprice = arr(9).asInstanceOf[BigDecimal],
          size = arr(10).asInstanceOf[Option[/* max 5 chars */ String]],
          sizeunitmeasurecode = arr(11).asInstanceOf[Option[UnitmeasureId]],
          weightunitmeasurecode = arr(12).asInstanceOf[Option[UnitmeasureId]],
          weight = arr(13).asInstanceOf[Option[BigDecimal]],
          daystomanufacture = arr(14).asInstanceOf[Int],
          productline = arr(15).asInstanceOf[Option[/* bpchar, max 2 chars */ String]],
          `class` = arr(16).asInstanceOf[Option[/* bpchar, max 2 chars */ String]],
          style = arr(17).asInstanceOf[Option[/* bpchar, max 2 chars */ String]],
          productsubcategoryid = arr(18).asInstanceOf[Option[ProductsubcategoryId]],
          productmodelid = arr(19).asInstanceOf[Option[ProductmodelId]],
          sellstartdate = arr(20).asInstanceOf[TypoLocalDateTime],
          sellenddate = arr(21).asInstanceOf[Option[TypoLocalDateTime]],
          discontinueddate = arr(22).asInstanceOf[Option[TypoLocalDateTime]],
          rowguid = arr(23).asInstanceOf[TypoUUID],
          modifieddate = arr(24).asInstanceOf[TypoLocalDateTime]
    )
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
  implicit lazy val write: Write[ProductRow] = new Write.Composite[ProductRow](
    List(new Write.Single(ProductId.put),
         new Write.Single(Name.put),
         new Write.Single(Meta.StringMeta.put),
         new Write.Single(Flag.put),
         new Write.Single(Flag.put),
         new Write.Single(Meta.StringMeta.put).toOpt,
         new Write.Single(TypoShort.put),
         new Write.Single(TypoShort.put),
         new Write.Single(Meta.ScalaBigDecimalMeta.put),
         new Write.Single(Meta.ScalaBigDecimalMeta.put),
         new Write.Single(Meta.StringMeta.put).toOpt,
         new Write.Single(UnitmeasureId.put).toOpt,
         new Write.Single(UnitmeasureId.put).toOpt,
         new Write.Single(Meta.ScalaBigDecimalMeta.put).toOpt,
         new Write.Single(Meta.IntMeta.put),
         new Write.Single(Meta.StringMeta.put).toOpt,
         new Write.Single(Meta.StringMeta.put).toOpt,
         new Write.Single(Meta.StringMeta.put).toOpt,
         new Write.Single(ProductsubcategoryId.put).toOpt,
         new Write.Single(ProductmodelId.put).toOpt,
         new Write.Single(TypoLocalDateTime.put),
         new Write.Single(TypoLocalDateTime.put).toOpt,
         new Write.Single(TypoLocalDateTime.put).toOpt,
         new Write.Single(TypoUUID.put),
         new Write.Single(TypoLocalDateTime.put)),
    a => List(a.productid, a.name, a.productnumber, a.makeflag, a.finishedgoodsflag, a.color, a.safetystocklevel, a.reorderpoint, a.standardcost, a.listprice, a.size, a.sizeunitmeasurecode, a.weightunitmeasurecode, a.weight, a.daystomanufacture, a.productline, a.`class`, a.style, a.productsubcategoryid, a.productmodelid, a.sellstartdate, a.sellenddate, a.discontinueddate, a.rowguid, a.modifieddate)
  )
}

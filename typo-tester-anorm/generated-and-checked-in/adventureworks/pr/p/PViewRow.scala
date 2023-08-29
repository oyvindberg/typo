/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package p

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.production.product.ProductId
import adventureworks.production.productmodel.ProductmodelId
import adventureworks.production.productsubcategory.ProductsubcategoryId
import adventureworks.production.unitmeasure.UnitmeasureId
import adventureworks.public.Flag
import adventureworks.public.Name
import anorm.Column
import anorm.RowParser
import anorm.Success
import java.util.UUID
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.OWrites
import play.api.libs.json.Reads
import play.api.libs.json.Writes
import scala.collection.immutable.ListMap
import scala.util.Try

case class PViewRow(
  /** Points to [[production.product.ProductRow.productid]] */
  id: ProductId,
  /** Points to [[production.product.ProductRow.productid]] */
  productid: ProductId,
  /** Points to [[production.product.ProductRow.name]] */
  name: Name,
  /** Points to [[production.product.ProductRow.productnumber]] */
  productnumber: /* max 25 chars */ String,
  /** Points to [[production.product.ProductRow.makeflag]] */
  makeflag: Flag,
  /** Points to [[production.product.ProductRow.finishedgoodsflag]] */
  finishedgoodsflag: Flag,
  /** Points to [[production.product.ProductRow.color]] */
  color: Option[/* max 15 chars */ String],
  /** Points to [[production.product.ProductRow.safetystocklevel]] */
  safetystocklevel: Int,
  /** Points to [[production.product.ProductRow.reorderpoint]] */
  reorderpoint: Int,
  /** Points to [[production.product.ProductRow.standardcost]] */
  standardcost: BigDecimal,
  /** Points to [[production.product.ProductRow.listprice]] */
  listprice: BigDecimal,
  /** Points to [[production.product.ProductRow.size]] */
  size: Option[/* max 5 chars */ String],
  /** Points to [[production.product.ProductRow.sizeunitmeasurecode]] */
  sizeunitmeasurecode: Option[UnitmeasureId],
  /** Points to [[production.product.ProductRow.weightunitmeasurecode]] */
  weightunitmeasurecode: Option[UnitmeasureId],
  /** Points to [[production.product.ProductRow.weight]] */
  weight: Option[BigDecimal],
  /** Points to [[production.product.ProductRow.daystomanufacture]] */
  daystomanufacture: Int,
  /** Points to [[production.product.ProductRow.productline]] */
  productline: Option[/* bpchar, max 2 chars */ String],
  /** Points to [[production.product.ProductRow.class]] */
  `class`: Option[/* bpchar, max 2 chars */ String],
  /** Points to [[production.product.ProductRow.style]] */
  style: Option[/* bpchar, max 2 chars */ String],
  /** Points to [[production.product.ProductRow.productsubcategoryid]] */
  productsubcategoryid: Option[ProductsubcategoryId],
  /** Points to [[production.product.ProductRow.productmodelid]] */
  productmodelid: Option[ProductmodelId],
  /** Points to [[production.product.ProductRow.sellstartdate]] */
  sellstartdate: TypoLocalDateTime,
  /** Points to [[production.product.ProductRow.sellenddate]] */
  sellenddate: Option[TypoLocalDateTime],
  /** Points to [[production.product.ProductRow.discontinueddate]] */
  discontinueddate: Option[TypoLocalDateTime],
  /** Points to [[production.product.ProductRow.rowguid]] */
  rowguid: UUID,
  /** Points to [[production.product.ProductRow.modifieddate]] */
  modifieddate: TypoLocalDateTime
)

object PViewRow {
  implicit lazy val reads: Reads[PViewRow] = Reads[PViewRow](json => JsResult.fromTry(
      Try(
        PViewRow(
          id = json.\("id").as(ProductId.reads),
          productid = json.\("productid").as(ProductId.reads),
          name = json.\("name").as(Name.reads),
          productnumber = json.\("productnumber").as(Reads.StringReads),
          makeflag = json.\("makeflag").as(Flag.reads),
          finishedgoodsflag = json.\("finishedgoodsflag").as(Flag.reads),
          color = json.\("color").toOption.map(_.as(Reads.StringReads)),
          safetystocklevel = json.\("safetystocklevel").as(Reads.IntReads),
          reorderpoint = json.\("reorderpoint").as(Reads.IntReads),
          standardcost = json.\("standardcost").as(Reads.bigDecReads),
          listprice = json.\("listprice").as(Reads.bigDecReads),
          size = json.\("size").toOption.map(_.as(Reads.StringReads)),
          sizeunitmeasurecode = json.\("sizeunitmeasurecode").toOption.map(_.as(UnitmeasureId.reads)),
          weightunitmeasurecode = json.\("weightunitmeasurecode").toOption.map(_.as(UnitmeasureId.reads)),
          weight = json.\("weight").toOption.map(_.as(Reads.bigDecReads)),
          daystomanufacture = json.\("daystomanufacture").as(Reads.IntReads),
          productline = json.\("productline").toOption.map(_.as(Reads.StringReads)),
          `class` = json.\("class").toOption.map(_.as(Reads.StringReads)),
          style = json.\("style").toOption.map(_.as(Reads.StringReads)),
          productsubcategoryid = json.\("productsubcategoryid").toOption.map(_.as(ProductsubcategoryId.reads)),
          productmodelid = json.\("productmodelid").toOption.map(_.as(ProductmodelId.reads)),
          sellstartdate = json.\("sellstartdate").as(TypoLocalDateTime.reads),
          sellenddate = json.\("sellenddate").toOption.map(_.as(TypoLocalDateTime.reads)),
          discontinueddate = json.\("discontinueddate").toOption.map(_.as(TypoLocalDateTime.reads)),
          rowguid = json.\("rowguid").as(Reads.uuidReads),
          modifieddate = json.\("modifieddate").as(TypoLocalDateTime.reads)
        )
      )
    ),
  )
  def rowParser(idx: Int): RowParser[PViewRow] = RowParser[PViewRow] { row =>
    Success(
      PViewRow(
        id = row(idx + 0)(ProductId.column),
        productid = row(idx + 1)(ProductId.column),
        name = row(idx + 2)(Name.column),
        productnumber = row(idx + 3)(Column.columnToString),
        makeflag = row(idx + 4)(Flag.column),
        finishedgoodsflag = row(idx + 5)(Flag.column),
        color = row(idx + 6)(Column.columnToOption(Column.columnToString)),
        safetystocklevel = row(idx + 7)(Column.columnToInt),
        reorderpoint = row(idx + 8)(Column.columnToInt),
        standardcost = row(idx + 9)(Column.columnToScalaBigDecimal),
        listprice = row(idx + 10)(Column.columnToScalaBigDecimal),
        size = row(idx + 11)(Column.columnToOption(Column.columnToString)),
        sizeunitmeasurecode = row(idx + 12)(Column.columnToOption(UnitmeasureId.column)),
        weightunitmeasurecode = row(idx + 13)(Column.columnToOption(UnitmeasureId.column)),
        weight = row(idx + 14)(Column.columnToOption(Column.columnToScalaBigDecimal)),
        daystomanufacture = row(idx + 15)(Column.columnToInt),
        productline = row(idx + 16)(Column.columnToOption(Column.columnToString)),
        `class` = row(idx + 17)(Column.columnToOption(Column.columnToString)),
        style = row(idx + 18)(Column.columnToOption(Column.columnToString)),
        productsubcategoryid = row(idx + 19)(Column.columnToOption(ProductsubcategoryId.column)),
        productmodelid = row(idx + 20)(Column.columnToOption(ProductmodelId.column)),
        sellstartdate = row(idx + 21)(TypoLocalDateTime.column),
        sellenddate = row(idx + 22)(Column.columnToOption(TypoLocalDateTime.column)),
        discontinueddate = row(idx + 23)(Column.columnToOption(TypoLocalDateTime.column)),
        rowguid = row(idx + 24)(Column.columnToUUID),
        modifieddate = row(idx + 25)(TypoLocalDateTime.column)
      )
    )
  }
  implicit lazy val writes: OWrites[PViewRow] = OWrites[PViewRow](o =>
    new JsObject(ListMap[String, JsValue](
      "id" -> ProductId.writes.writes(o.id),
      "productid" -> ProductId.writes.writes(o.productid),
      "name" -> Name.writes.writes(o.name),
      "productnumber" -> Writes.StringWrites.writes(o.productnumber),
      "makeflag" -> Flag.writes.writes(o.makeflag),
      "finishedgoodsflag" -> Flag.writes.writes(o.finishedgoodsflag),
      "color" -> Writes.OptionWrites(Writes.StringWrites).writes(o.color),
      "safetystocklevel" -> Writes.IntWrites.writes(o.safetystocklevel),
      "reorderpoint" -> Writes.IntWrites.writes(o.reorderpoint),
      "standardcost" -> Writes.BigDecimalWrites.writes(o.standardcost),
      "listprice" -> Writes.BigDecimalWrites.writes(o.listprice),
      "size" -> Writes.OptionWrites(Writes.StringWrites).writes(o.size),
      "sizeunitmeasurecode" -> Writes.OptionWrites(UnitmeasureId.writes).writes(o.sizeunitmeasurecode),
      "weightunitmeasurecode" -> Writes.OptionWrites(UnitmeasureId.writes).writes(o.weightunitmeasurecode),
      "weight" -> Writes.OptionWrites(Writes.BigDecimalWrites).writes(o.weight),
      "daystomanufacture" -> Writes.IntWrites.writes(o.daystomanufacture),
      "productline" -> Writes.OptionWrites(Writes.StringWrites).writes(o.productline),
      "class" -> Writes.OptionWrites(Writes.StringWrites).writes(o.`class`),
      "style" -> Writes.OptionWrites(Writes.StringWrites).writes(o.style),
      "productsubcategoryid" -> Writes.OptionWrites(ProductsubcategoryId.writes).writes(o.productsubcategoryid),
      "productmodelid" -> Writes.OptionWrites(ProductmodelId.writes).writes(o.productmodelid),
      "sellstartdate" -> TypoLocalDateTime.writes.writes(o.sellstartdate),
      "sellenddate" -> Writes.OptionWrites(TypoLocalDateTime.writes).writes(o.sellenddate),
      "discontinueddate" -> Writes.OptionWrites(TypoLocalDateTime.writes).writes(o.discontinueddate),
      "rowguid" -> Writes.UuidWrites.writes(o.rowguid),
      "modifieddate" -> TypoLocalDateTime.writes.writes(o.modifieddate)
    ))
  )
}
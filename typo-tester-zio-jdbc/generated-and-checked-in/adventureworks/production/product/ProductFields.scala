/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package product

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoShort
import adventureworks.customtypes.TypoUUID
import adventureworks.production.productmodel.ProductmodelId
import adventureworks.production.productsubcategory.ProductsubcategoryId
import adventureworks.production.unitmeasure.UnitmeasureId
import adventureworks.public.Flag
import adventureworks.public.Name
import typo.dsl.SqlExpr.Field
import typo.dsl.SqlExpr.FieldLikeNoHkt
import typo.dsl.SqlExpr.IdField
import typo.dsl.SqlExpr.OptField
import typo.dsl.Structure.Relation

trait ProductFields[Row] {
  val productid: IdField[ProductId, Row]
  val name: Field[Name, Row]
  val productnumber: Field[/* max 25 chars */ String, Row]
  val makeflag: Field[Flag, Row]
  val finishedgoodsflag: Field[Flag, Row]
  val color: OptField[/* max 15 chars */ String, Row]
  val safetystocklevel: Field[TypoShort, Row]
  val reorderpoint: Field[TypoShort, Row]
  val standardcost: Field[BigDecimal, Row]
  val listprice: Field[BigDecimal, Row]
  val size: OptField[/* max 5 chars */ String, Row]
  val sizeunitmeasurecode: OptField[UnitmeasureId, Row]
  val weightunitmeasurecode: OptField[UnitmeasureId, Row]
  val weight: OptField[BigDecimal, Row]
  val daystomanufacture: Field[Int, Row]
  val productline: OptField[/* bpchar, max 2 chars */ String, Row]
  val `class`: OptField[/* bpchar, max 2 chars */ String, Row]
  val style: OptField[/* bpchar, max 2 chars */ String, Row]
  val productsubcategoryid: OptField[ProductsubcategoryId, Row]
  val productmodelid: OptField[ProductmodelId, Row]
  val sellstartdate: Field[TypoLocalDateTime, Row]
  val sellenddate: OptField[TypoLocalDateTime, Row]
  val discontinueddate: OptField[TypoLocalDateTime, Row]
  val rowguid: Field[TypoUUID, Row]
  val modifieddate: Field[TypoLocalDateTime, Row]
}

object ProductFields {
  val structure: Relation[ProductFields, ProductRow, ProductRow] = 
    new Impl(None, identity, (_, x) => x)
    
  private final class Impl[Row](val prefix: Option[String], val extract: Row => ProductRow, val merge: (Row, ProductRow) => Row)
    extends Relation[ProductFields, ProductRow, Row] { 
  
    override val fields: ProductFields[Row] = new ProductFields[Row] {
      override val productid = new IdField[ProductId, Row](prefix, "productid", None, Some("int4"))(x => extract(x).productid, (row, value) => merge(row, extract(row).copy(productid = value)))
      override val name = new Field[Name, Row](prefix, "name", None, Some("varchar"))(x => extract(x).name, (row, value) => merge(row, extract(row).copy(name = value)))
      override val productnumber = new Field[/* max 25 chars */ String, Row](prefix, "productnumber", None, None)(x => extract(x).productnumber, (row, value) => merge(row, extract(row).copy(productnumber = value)))
      override val makeflag = new Field[Flag, Row](prefix, "makeflag", None, Some("bool"))(x => extract(x).makeflag, (row, value) => merge(row, extract(row).copy(makeflag = value)))
      override val finishedgoodsflag = new Field[Flag, Row](prefix, "finishedgoodsflag", None, Some("bool"))(x => extract(x).finishedgoodsflag, (row, value) => merge(row, extract(row).copy(finishedgoodsflag = value)))
      override val color = new OptField[/* max 15 chars */ String, Row](prefix, "color", None, None)(x => extract(x).color, (row, value) => merge(row, extract(row).copy(color = value)))
      override val safetystocklevel = new Field[TypoShort, Row](prefix, "safetystocklevel", None, Some("int2"))(x => extract(x).safetystocklevel, (row, value) => merge(row, extract(row).copy(safetystocklevel = value)))
      override val reorderpoint = new Field[TypoShort, Row](prefix, "reorderpoint", None, Some("int2"))(x => extract(x).reorderpoint, (row, value) => merge(row, extract(row).copy(reorderpoint = value)))
      override val standardcost = new Field[BigDecimal, Row](prefix, "standardcost", None, Some("numeric"))(x => extract(x).standardcost, (row, value) => merge(row, extract(row).copy(standardcost = value)))
      override val listprice = new Field[BigDecimal, Row](prefix, "listprice", None, Some("numeric"))(x => extract(x).listprice, (row, value) => merge(row, extract(row).copy(listprice = value)))
      override val size = new OptField[/* max 5 chars */ String, Row](prefix, "size", None, None)(x => extract(x).size, (row, value) => merge(row, extract(row).copy(size = value)))
      override val sizeunitmeasurecode = new OptField[UnitmeasureId, Row](prefix, "sizeunitmeasurecode", None, Some("bpchar"))(x => extract(x).sizeunitmeasurecode, (row, value) => merge(row, extract(row).copy(sizeunitmeasurecode = value)))
      override val weightunitmeasurecode = new OptField[UnitmeasureId, Row](prefix, "weightunitmeasurecode", None, Some("bpchar"))(x => extract(x).weightunitmeasurecode, (row, value) => merge(row, extract(row).copy(weightunitmeasurecode = value)))
      override val weight = new OptField[BigDecimal, Row](prefix, "weight", None, Some("numeric"))(x => extract(x).weight, (row, value) => merge(row, extract(row).copy(weight = value)))
      override val daystomanufacture = new Field[Int, Row](prefix, "daystomanufacture", None, Some("int4"))(x => extract(x).daystomanufacture, (row, value) => merge(row, extract(row).copy(daystomanufacture = value)))
      override val productline = new OptField[/* bpchar, max 2 chars */ String, Row](prefix, "productline", None, Some("bpchar"))(x => extract(x).productline, (row, value) => merge(row, extract(row).copy(productline = value)))
      override val `class` = new OptField[/* bpchar, max 2 chars */ String, Row](prefix, "class", None, Some("bpchar"))(x => extract(x).`class`, (row, value) => merge(row, extract(row).copy(`class` = value)))
      override val style = new OptField[/* bpchar, max 2 chars */ String, Row](prefix, "style", None, Some("bpchar"))(x => extract(x).style, (row, value) => merge(row, extract(row).copy(style = value)))
      override val productsubcategoryid = new OptField[ProductsubcategoryId, Row](prefix, "productsubcategoryid", None, Some("int4"))(x => extract(x).productsubcategoryid, (row, value) => merge(row, extract(row).copy(productsubcategoryid = value)))
      override val productmodelid = new OptField[ProductmodelId, Row](prefix, "productmodelid", None, Some("int4"))(x => extract(x).productmodelid, (row, value) => merge(row, extract(row).copy(productmodelid = value)))
      override val sellstartdate = new Field[TypoLocalDateTime, Row](prefix, "sellstartdate", Some("text"), Some("timestamp"))(x => extract(x).sellstartdate, (row, value) => merge(row, extract(row).copy(sellstartdate = value)))
      override val sellenddate = new OptField[TypoLocalDateTime, Row](prefix, "sellenddate", Some("text"), Some("timestamp"))(x => extract(x).sellenddate, (row, value) => merge(row, extract(row).copy(sellenddate = value)))
      override val discontinueddate = new OptField[TypoLocalDateTime, Row](prefix, "discontinueddate", Some("text"), Some("timestamp"))(x => extract(x).discontinueddate, (row, value) => merge(row, extract(row).copy(discontinueddate = value)))
      override val rowguid = new Field[TypoUUID, Row](prefix, "rowguid", None, Some("uuid"))(x => extract(x).rowguid, (row, value) => merge(row, extract(row).copy(rowguid = value)))
      override val modifieddate = new Field[TypoLocalDateTime, Row](prefix, "modifieddate", Some("text"), Some("timestamp"))(x => extract(x).modifieddate, (row, value) => merge(row, extract(row).copy(modifieddate = value)))
    }
  
    override val columns: List[FieldLikeNoHkt[?, Row]] =
      List[FieldLikeNoHkt[?, Row]](fields.productid, fields.name, fields.productnumber, fields.makeflag, fields.finishedgoodsflag, fields.color, fields.safetystocklevel, fields.reorderpoint, fields.standardcost, fields.listprice, fields.size, fields.sizeunitmeasurecode, fields.weightunitmeasurecode, fields.weight, fields.daystomanufacture, fields.productline, fields.`class`, fields.style, fields.productsubcategoryid, fields.productmodelid, fields.sellstartdate, fields.sellenddate, fields.discontinueddate, fields.rowguid, fields.modifieddate)
  
    override def copy[NewRow](prefix: Option[String], extract: NewRow => ProductRow, merge: (NewRow, ProductRow) => NewRow): Impl[NewRow] =
      new Impl(prefix, extract, merge)
  }
  
}
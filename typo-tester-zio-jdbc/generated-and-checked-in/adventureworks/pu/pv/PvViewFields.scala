/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pu
package pv

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.person.businessentity.BusinessentityId
import adventureworks.production.product.ProductId
import adventureworks.production.unitmeasure.UnitmeasureId
import typo.dsl.SqlExpr.Field
import typo.dsl.SqlExpr.FieldLikeNoHkt
import typo.dsl.SqlExpr.OptField
import typo.dsl.Structure.Relation

trait PvViewFields[Row] {
  val id: Field[ProductId, Row]
  val productid: Field[ProductId, Row]
  val businessentityid: Field[BusinessentityId, Row]
  val averageleadtime: Field[Int, Row]
  val standardprice: Field[BigDecimal, Row]
  val lastreceiptcost: OptField[BigDecimal, Row]
  val lastreceiptdate: OptField[TypoLocalDateTime, Row]
  val minorderqty: Field[Int, Row]
  val maxorderqty: Field[Int, Row]
  val onorderqty: OptField[Int, Row]
  val unitmeasurecode: Field[UnitmeasureId, Row]
  val modifieddate: Field[TypoLocalDateTime, Row]
}

object PvViewFields {
  val structure: Relation[PvViewFields, PvViewRow, PvViewRow] = 
    new Impl(None, identity, (_, x) => x)
    
  private final class Impl[Row](val prefix: Option[String], val extract: Row => PvViewRow, val merge: (Row, PvViewRow) => Row)
    extends Relation[PvViewFields, PvViewRow, Row] { 
  
    override val fields: PvViewFields[Row] = new PvViewFields[Row] {
      override val id = new Field[ProductId, Row](prefix, "id", None, None)(x => extract(x).id, (row, value) => merge(row, extract(row).copy(id = value)))
      override val productid = new Field[ProductId, Row](prefix, "productid", None, None)(x => extract(x).productid, (row, value) => merge(row, extract(row).copy(productid = value)))
      override val businessentityid = new Field[BusinessentityId, Row](prefix, "businessentityid", None, None)(x => extract(x).businessentityid, (row, value) => merge(row, extract(row).copy(businessentityid = value)))
      override val averageleadtime = new Field[Int, Row](prefix, "averageleadtime", None, None)(x => extract(x).averageleadtime, (row, value) => merge(row, extract(row).copy(averageleadtime = value)))
      override val standardprice = new Field[BigDecimal, Row](prefix, "standardprice", None, None)(x => extract(x).standardprice, (row, value) => merge(row, extract(row).copy(standardprice = value)))
      override val lastreceiptcost = new OptField[BigDecimal, Row](prefix, "lastreceiptcost", None, None)(x => extract(x).lastreceiptcost, (row, value) => merge(row, extract(row).copy(lastreceiptcost = value)))
      override val lastreceiptdate = new OptField[TypoLocalDateTime, Row](prefix, "lastreceiptdate", Some("text"), None)(x => extract(x).lastreceiptdate, (row, value) => merge(row, extract(row).copy(lastreceiptdate = value)))
      override val minorderqty = new Field[Int, Row](prefix, "minorderqty", None, None)(x => extract(x).minorderqty, (row, value) => merge(row, extract(row).copy(minorderqty = value)))
      override val maxorderqty = new Field[Int, Row](prefix, "maxorderqty", None, None)(x => extract(x).maxorderqty, (row, value) => merge(row, extract(row).copy(maxorderqty = value)))
      override val onorderqty = new OptField[Int, Row](prefix, "onorderqty", None, None)(x => extract(x).onorderqty, (row, value) => merge(row, extract(row).copy(onorderqty = value)))
      override val unitmeasurecode = new Field[UnitmeasureId, Row](prefix, "unitmeasurecode", None, None)(x => extract(x).unitmeasurecode, (row, value) => merge(row, extract(row).copy(unitmeasurecode = value)))
      override val modifieddate = new Field[TypoLocalDateTime, Row](prefix, "modifieddate", Some("text"), None)(x => extract(x).modifieddate, (row, value) => merge(row, extract(row).copy(modifieddate = value)))
    }
  
    override val columns: List[FieldLikeNoHkt[?, Row]] =
      List[FieldLikeNoHkt[?, Row]](fields.id, fields.productid, fields.businessentityid, fields.averageleadtime, fields.standardprice, fields.lastreceiptcost, fields.lastreceiptdate, fields.minorderqty, fields.maxorderqty, fields.onorderqty, fields.unitmeasurecode, fields.modifieddate)
  
    override def copy[NewRow](prefix: Option[String], extract: NewRow => PvViewRow, merge: (NewRow, PvViewRow) => NewRow): Impl[NewRow] =
      new Impl(prefix, extract, merge)
  }
  
}
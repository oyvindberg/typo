/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package salesorderdetail

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoShort
import adventureworks.customtypes.TypoUUID
import adventureworks.production.product.ProductId
import adventureworks.sales.salesorderheader.SalesorderheaderId
import adventureworks.sales.specialoffer.SpecialofferId
import typo.dsl.SqlExpr.Field
import typo.dsl.SqlExpr.FieldLikeNoHkt
import typo.dsl.SqlExpr.IdField
import typo.dsl.SqlExpr.OptField
import typo.dsl.Structure.Relation

trait SalesorderdetailFields[Row] {
  val salesorderid: IdField[SalesorderheaderId, Row]
  val salesorderdetailid: IdField[Int, Row]
  val carriertrackingnumber: OptField[/* max 25 chars */ String, Row]
  val orderqty: Field[TypoShort, Row]
  val productid: Field[ProductId, Row]
  val specialofferid: Field[SpecialofferId, Row]
  val unitprice: Field[BigDecimal, Row]
  val unitpricediscount: Field[BigDecimal, Row]
  val rowguid: Field[TypoUUID, Row]
  val modifieddate: Field[TypoLocalDateTime, Row]
}

object SalesorderdetailFields {
  val structure: Relation[SalesorderdetailFields, SalesorderdetailRow, SalesorderdetailRow] = 
    new Impl(None, identity, (_, x) => x)
    
  private final class Impl[Row](val prefix: Option[String], val extract: Row => SalesorderdetailRow, val merge: (Row, SalesorderdetailRow) => Row)
    extends Relation[SalesorderdetailFields, SalesorderdetailRow, Row] { 
  
    override val fields: SalesorderdetailFields[Row] = new SalesorderdetailFields[Row] {
      override val salesorderid = new IdField[SalesorderheaderId, Row](prefix, "salesorderid", None, Some("int4"))(x => extract(x).salesorderid, (row, value) => merge(row, extract(row).copy(salesorderid = value)))
      override val salesorderdetailid = new IdField[Int, Row](prefix, "salesorderdetailid", None, Some("int4"))(x => extract(x).salesorderdetailid, (row, value) => merge(row, extract(row).copy(salesorderdetailid = value)))
      override val carriertrackingnumber = new OptField[/* max 25 chars */ String, Row](prefix, "carriertrackingnumber", None, None)(x => extract(x).carriertrackingnumber, (row, value) => merge(row, extract(row).copy(carriertrackingnumber = value)))
      override val orderqty = new Field[TypoShort, Row](prefix, "orderqty", None, Some("int2"))(x => extract(x).orderqty, (row, value) => merge(row, extract(row).copy(orderqty = value)))
      override val productid = new Field[ProductId, Row](prefix, "productid", None, Some("int4"))(x => extract(x).productid, (row, value) => merge(row, extract(row).copy(productid = value)))
      override val specialofferid = new Field[SpecialofferId, Row](prefix, "specialofferid", None, Some("int4"))(x => extract(x).specialofferid, (row, value) => merge(row, extract(row).copy(specialofferid = value)))
      override val unitprice = new Field[BigDecimal, Row](prefix, "unitprice", None, Some("numeric"))(x => extract(x).unitprice, (row, value) => merge(row, extract(row).copy(unitprice = value)))
      override val unitpricediscount = new Field[BigDecimal, Row](prefix, "unitpricediscount", None, Some("numeric"))(x => extract(x).unitpricediscount, (row, value) => merge(row, extract(row).copy(unitpricediscount = value)))
      override val rowguid = new Field[TypoUUID, Row](prefix, "rowguid", None, Some("uuid"))(x => extract(x).rowguid, (row, value) => merge(row, extract(row).copy(rowguid = value)))
      override val modifieddate = new Field[TypoLocalDateTime, Row](prefix, "modifieddate", Some("text"), Some("timestamp"))(x => extract(x).modifieddate, (row, value) => merge(row, extract(row).copy(modifieddate = value)))
    }
  
    override val columns: List[FieldLikeNoHkt[?, Row]] =
      List[FieldLikeNoHkt[?, Row]](fields.salesorderid, fields.salesorderdetailid, fields.carriertrackingnumber, fields.orderqty, fields.productid, fields.specialofferid, fields.unitprice, fields.unitpricediscount, fields.rowguid, fields.modifieddate)
  
    override def copy[NewRow](prefix: Option[String], extract: NewRow => SalesorderdetailRow, merge: (NewRow, SalesorderdetailRow) => NewRow): Impl[NewRow] =
      new Impl(prefix, extract, merge)
  }
  
}
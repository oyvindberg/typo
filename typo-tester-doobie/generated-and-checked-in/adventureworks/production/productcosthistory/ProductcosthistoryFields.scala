/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package productcosthistory

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.production.product.ProductId
import typo.dsl.SqlExpr.Field
import typo.dsl.SqlExpr.FieldLikeNoHkt
import typo.dsl.SqlExpr.IdField
import typo.dsl.SqlExpr.OptField
import typo.dsl.Structure.Relation

trait ProductcosthistoryFields[Row] {
  val productid: IdField[ProductId, Row]
  val startdate: IdField[TypoLocalDateTime, Row]
  val enddate: OptField[TypoLocalDateTime, Row]
  val standardcost: Field[BigDecimal, Row]
  val modifieddate: Field[TypoLocalDateTime, Row]
}

object ProductcosthistoryFields {
  val structure: Relation[ProductcosthistoryFields, ProductcosthistoryRow, ProductcosthistoryRow] = 
    new Impl(None, identity, (_, x) => x)
    
  private final class Impl[Row](val prefix: Option[String], val extract: Row => ProductcosthistoryRow, val merge: (Row, ProductcosthistoryRow) => Row)
    extends Relation[ProductcosthistoryFields, ProductcosthistoryRow, Row] { 
  
    override val fields: ProductcosthistoryFields[Row] = new ProductcosthistoryFields[Row] {
      override val productid = new IdField[ProductId, Row](prefix, "productid", None, Some("int4"))(x => extract(x).productid, (row, value) => merge(row, extract(row).copy(productid = value)))
      override val startdate = new IdField[TypoLocalDateTime, Row](prefix, "startdate", Some("text"), Some("timestamp"))(x => extract(x).startdate, (row, value) => merge(row, extract(row).copy(startdate = value)))
      override val enddate = new OptField[TypoLocalDateTime, Row](prefix, "enddate", Some("text"), Some("timestamp"))(x => extract(x).enddate, (row, value) => merge(row, extract(row).copy(enddate = value)))
      override val standardcost = new Field[BigDecimal, Row](prefix, "standardcost", None, Some("numeric"))(x => extract(x).standardcost, (row, value) => merge(row, extract(row).copy(standardcost = value)))
      override val modifieddate = new Field[TypoLocalDateTime, Row](prefix, "modifieddate", Some("text"), Some("timestamp"))(x => extract(x).modifieddate, (row, value) => merge(row, extract(row).copy(modifieddate = value)))
    }
  
    override val columns: List[FieldLikeNoHkt[?, Row]] =
      List[FieldLikeNoHkt[?, Row]](fields.productid, fields.startdate, fields.enddate, fields.standardcost, fields.modifieddate)
  
    override def copy[NewRow](prefix: Option[String], extract: NewRow => ProductcosthistoryRow, merge: (NewRow, ProductcosthistoryRow) => NewRow): Impl[NewRow] =
      new Impl(prefix, extract, merge)
  }
  
}
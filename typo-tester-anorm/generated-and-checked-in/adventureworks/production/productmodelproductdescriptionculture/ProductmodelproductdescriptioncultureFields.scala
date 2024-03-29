/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package productmodelproductdescriptionculture

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.production.culture.CultureId
import adventureworks.production.productdescription.ProductdescriptionId
import adventureworks.production.productmodel.ProductmodelId
import typo.dsl.SqlExpr.Field
import typo.dsl.SqlExpr.FieldLikeNoHkt
import typo.dsl.SqlExpr.IdField
import typo.dsl.Structure.Relation

trait ProductmodelproductdescriptioncultureFields[Row] {
  val productmodelid: IdField[ProductmodelId, Row]
  val productdescriptionid: IdField[ProductdescriptionId, Row]
  val cultureid: IdField[CultureId, Row]
  val modifieddate: Field[TypoLocalDateTime, Row]
}

object ProductmodelproductdescriptioncultureFields {
  val structure: Relation[ProductmodelproductdescriptioncultureFields, ProductmodelproductdescriptioncultureRow, ProductmodelproductdescriptioncultureRow] = 
    new Impl(None, identity, (_, x) => x)
    
  private final class Impl[Row](val prefix: Option[String], val extract: Row => ProductmodelproductdescriptioncultureRow, val merge: (Row, ProductmodelproductdescriptioncultureRow) => Row)
    extends Relation[ProductmodelproductdescriptioncultureFields, ProductmodelproductdescriptioncultureRow, Row] { 
  
    override val fields: ProductmodelproductdescriptioncultureFields[Row] = new ProductmodelproductdescriptioncultureFields[Row] {
      override val productmodelid = new IdField[ProductmodelId, Row](prefix, "productmodelid", None, Some("int4"))(x => extract(x).productmodelid, (row, value) => merge(row, extract(row).copy(productmodelid = value)))
      override val productdescriptionid = new IdField[ProductdescriptionId, Row](prefix, "productdescriptionid", None, Some("int4"))(x => extract(x).productdescriptionid, (row, value) => merge(row, extract(row).copy(productdescriptionid = value)))
      override val cultureid = new IdField[CultureId, Row](prefix, "cultureid", None, Some("bpchar"))(x => extract(x).cultureid, (row, value) => merge(row, extract(row).copy(cultureid = value)))
      override val modifieddate = new Field[TypoLocalDateTime, Row](prefix, "modifieddate", Some("text"), Some("timestamp"))(x => extract(x).modifieddate, (row, value) => merge(row, extract(row).copy(modifieddate = value)))
    }
  
    override val columns: List[FieldLikeNoHkt[?, Row]] =
      List[FieldLikeNoHkt[?, Row]](fields.productmodelid, fields.productdescriptionid, fields.cultureid, fields.modifieddate)
  
    override def copy[NewRow](prefix: Option[String], extract: NewRow => ProductmodelproductdescriptioncultureRow, merge: (NewRow, ProductmodelproductdescriptioncultureRow) => NewRow): Impl[NewRow] =
      new Impl(prefix, extract, merge)
  }
  
}

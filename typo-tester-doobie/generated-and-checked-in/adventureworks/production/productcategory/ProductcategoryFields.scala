/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package productcategory

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoUUID
import adventureworks.public.Name
import typo.dsl.SqlExpr.Field
import typo.dsl.SqlExpr.FieldLikeNoHkt
import typo.dsl.SqlExpr.IdField
import typo.dsl.Structure.Relation

trait ProductcategoryFields[Row] {
  val productcategoryid: IdField[ProductcategoryId, Row]
  val name: Field[Name, Row]
  val rowguid: Field[TypoUUID, Row]
  val modifieddate: Field[TypoLocalDateTime, Row]
}

object ProductcategoryFields {
  val structure: Relation[ProductcategoryFields, ProductcategoryRow, ProductcategoryRow] = 
    new Impl(None, identity, (_, x) => x)
    
  private final class Impl[Row](val prefix: Option[String], val extract: Row => ProductcategoryRow, val merge: (Row, ProductcategoryRow) => Row)
    extends Relation[ProductcategoryFields, ProductcategoryRow, Row] { 
  
    override val fields: ProductcategoryFields[Row] = new ProductcategoryFields[Row] {
      override val productcategoryid = new IdField[ProductcategoryId, Row](prefix, "productcategoryid", None, Some("int4"))(x => extract(x).productcategoryid, (row, value) => merge(row, extract(row).copy(productcategoryid = value)))
      override val name = new Field[Name, Row](prefix, "name", None, Some("varchar"))(x => extract(x).name, (row, value) => merge(row, extract(row).copy(name = value)))
      override val rowguid = new Field[TypoUUID, Row](prefix, "rowguid", None, Some("uuid"))(x => extract(x).rowguid, (row, value) => merge(row, extract(row).copy(rowguid = value)))
      override val modifieddate = new Field[TypoLocalDateTime, Row](prefix, "modifieddate", Some("text"), Some("timestamp"))(x => extract(x).modifieddate, (row, value) => merge(row, extract(row).copy(modifieddate = value)))
    }
  
    override val columns: List[FieldLikeNoHkt[?, Row]] =
      List[FieldLikeNoHkt[?, Row]](fields.productcategoryid, fields.name, fields.rowguid, fields.modifieddate)
  
    override def copy[NewRow](prefix: Option[String], extract: NewRow => ProductcategoryRow, merge: (NewRow, ProductcategoryRow) => NewRow): Impl[NewRow] =
      new Impl(prefix, extract, merge)
  }
  
}
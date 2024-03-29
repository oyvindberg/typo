/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package productmodel

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoUUID
import adventureworks.customtypes.TypoXml
import adventureworks.public.Name
import typo.dsl.SqlExpr.Field
import typo.dsl.SqlExpr.FieldLikeNoHkt
import typo.dsl.SqlExpr.IdField
import typo.dsl.SqlExpr.OptField
import typo.dsl.Structure.Relation

trait ProductmodelFields[Row] {
  val productmodelid: IdField[ProductmodelId, Row]
  val name: Field[Name, Row]
  val catalogdescription: OptField[TypoXml, Row]
  val instructions: OptField[TypoXml, Row]
  val rowguid: Field[TypoUUID, Row]
  val modifieddate: Field[TypoLocalDateTime, Row]
}

object ProductmodelFields {
  val structure: Relation[ProductmodelFields, ProductmodelRow, ProductmodelRow] = 
    new Impl(None, identity, (_, x) => x)
    
  private final class Impl[Row](val prefix: Option[String], val extract: Row => ProductmodelRow, val merge: (Row, ProductmodelRow) => Row)
    extends Relation[ProductmodelFields, ProductmodelRow, Row] { 
  
    override val fields: ProductmodelFields[Row] = new ProductmodelFields[Row] {
      override val productmodelid = new IdField[ProductmodelId, Row](prefix, "productmodelid", None, Some("int4"))(x => extract(x).productmodelid, (row, value) => merge(row, extract(row).copy(productmodelid = value)))
      override val name = new Field[Name, Row](prefix, "name", None, Some("varchar"))(x => extract(x).name, (row, value) => merge(row, extract(row).copy(name = value)))
      override val catalogdescription = new OptField[TypoXml, Row](prefix, "catalogdescription", None, Some("xml"))(x => extract(x).catalogdescription, (row, value) => merge(row, extract(row).copy(catalogdescription = value)))
      override val instructions = new OptField[TypoXml, Row](prefix, "instructions", None, Some("xml"))(x => extract(x).instructions, (row, value) => merge(row, extract(row).copy(instructions = value)))
      override val rowguid = new Field[TypoUUID, Row](prefix, "rowguid", None, Some("uuid"))(x => extract(x).rowguid, (row, value) => merge(row, extract(row).copy(rowguid = value)))
      override val modifieddate = new Field[TypoLocalDateTime, Row](prefix, "modifieddate", Some("text"), Some("timestamp"))(x => extract(x).modifieddate, (row, value) => merge(row, extract(row).copy(modifieddate = value)))
    }
  
    override val columns: List[FieldLikeNoHkt[?, Row]] =
      List[FieldLikeNoHkt[?, Row]](fields.productmodelid, fields.name, fields.catalogdescription, fields.instructions, fields.rowguid, fields.modifieddate)
  
    override def copy[NewRow](prefix: Option[String], extract: NewRow => ProductmodelRow, merge: (NewRow, ProductmodelRow) => NewRow): Impl[NewRow] =
      new Impl(prefix, extract, merge)
  }
  
}

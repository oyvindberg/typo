/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package sth

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoUUID
import adventureworks.person.businessentity.BusinessentityId
import adventureworks.sales.salesterritory.SalesterritoryId
import typo.dsl.SqlExpr.Field
import typo.dsl.SqlExpr.FieldLikeNoHkt
import typo.dsl.SqlExpr.OptField
import typo.dsl.Structure.Relation

trait SthViewFields[Row] {
  val id: Field[SalesterritoryId, Row]
  val businessentityid: Field[BusinessentityId, Row]
  val territoryid: Field[SalesterritoryId, Row]
  val startdate: Field[TypoLocalDateTime, Row]
  val enddate: OptField[TypoLocalDateTime, Row]
  val rowguid: Field[TypoUUID, Row]
  val modifieddate: Field[TypoLocalDateTime, Row]
}

object SthViewFields {
  val structure: Relation[SthViewFields, SthViewRow, SthViewRow] = 
    new Impl(None, identity, (_, x) => x)
    
  private final class Impl[Row](val prefix: Option[String], val extract: Row => SthViewRow, val merge: (Row, SthViewRow) => Row)
    extends Relation[SthViewFields, SthViewRow, Row] { 
  
    override val fields: SthViewFields[Row] = new SthViewFields[Row] {
      override val id = new Field[SalesterritoryId, Row](prefix, "id", None, None)(x => extract(x).id, (row, value) => merge(row, extract(row).copy(id = value)))
      override val businessentityid = new Field[BusinessentityId, Row](prefix, "businessentityid", None, None)(x => extract(x).businessentityid, (row, value) => merge(row, extract(row).copy(businessentityid = value)))
      override val territoryid = new Field[SalesterritoryId, Row](prefix, "territoryid", None, None)(x => extract(x).territoryid, (row, value) => merge(row, extract(row).copy(territoryid = value)))
      override val startdate = new Field[TypoLocalDateTime, Row](prefix, "startdate", Some("text"), None)(x => extract(x).startdate, (row, value) => merge(row, extract(row).copy(startdate = value)))
      override val enddate = new OptField[TypoLocalDateTime, Row](prefix, "enddate", Some("text"), None)(x => extract(x).enddate, (row, value) => merge(row, extract(row).copy(enddate = value)))
      override val rowguid = new Field[TypoUUID, Row](prefix, "rowguid", None, None)(x => extract(x).rowguid, (row, value) => merge(row, extract(row).copy(rowguid = value)))
      override val modifieddate = new Field[TypoLocalDateTime, Row](prefix, "modifieddate", Some("text"), None)(x => extract(x).modifieddate, (row, value) => merge(row, extract(row).copy(modifieddate = value)))
    }
  
    override val columns: List[FieldLikeNoHkt[?, Row]] =
      List[FieldLikeNoHkt[?, Row]](fields.id, fields.businessentityid, fields.territoryid, fields.startdate, fields.enddate, fields.rowguid, fields.modifieddate)
  
    override def copy[NewRow](prefix: Option[String], extract: NewRow => SthViewRow, merge: (NewRow, SthViewRow) => NewRow): Impl[NewRow] =
      new Impl(prefix, extract, merge)
  }
  
}
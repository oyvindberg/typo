/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package humanresources
package employeepayhistory

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoShort
import adventureworks.person.businessentity.BusinessentityId
import typo.dsl.SqlExpr.Field
import typo.dsl.SqlExpr.FieldLikeNoHkt
import typo.dsl.SqlExpr.IdField
import typo.dsl.Structure.Relation

trait EmployeepayhistoryFields[Row] {
  val businessentityid: IdField[BusinessentityId, Row]
  val ratechangedate: IdField[TypoLocalDateTime, Row]
  val rate: Field[BigDecimal, Row]
  val payfrequency: Field[TypoShort, Row]
  val modifieddate: Field[TypoLocalDateTime, Row]
}

object EmployeepayhistoryFields {
  val structure: Relation[EmployeepayhistoryFields, EmployeepayhistoryRow, EmployeepayhistoryRow] = 
    new Impl(None, identity, (_, x) => x)
    
  private final class Impl[Row](val prefix: Option[String], val extract: Row => EmployeepayhistoryRow, val merge: (Row, EmployeepayhistoryRow) => Row)
    extends Relation[EmployeepayhistoryFields, EmployeepayhistoryRow, Row] { 
  
    override val fields: EmployeepayhistoryFields[Row] = new EmployeepayhistoryFields[Row] {
      override val businessentityid = new IdField[BusinessentityId, Row](prefix, "businessentityid", None, Some("int4"))(x => extract(x).businessentityid, (row, value) => merge(row, extract(row).copy(businessentityid = value)))
      override val ratechangedate = new IdField[TypoLocalDateTime, Row](prefix, "ratechangedate", Some("text"), Some("timestamp"))(x => extract(x).ratechangedate, (row, value) => merge(row, extract(row).copy(ratechangedate = value)))
      override val rate = new Field[BigDecimal, Row](prefix, "rate", None, Some("numeric"))(x => extract(x).rate, (row, value) => merge(row, extract(row).copy(rate = value)))
      override val payfrequency = new Field[TypoShort, Row](prefix, "payfrequency", None, Some("int2"))(x => extract(x).payfrequency, (row, value) => merge(row, extract(row).copy(payfrequency = value)))
      override val modifieddate = new Field[TypoLocalDateTime, Row](prefix, "modifieddate", Some("text"), Some("timestamp"))(x => extract(x).modifieddate, (row, value) => merge(row, extract(row).copy(modifieddate = value)))
    }
  
    override val columns: List[FieldLikeNoHkt[?, Row]] =
      List[FieldLikeNoHkt[?, Row]](fields.businessentityid, fields.ratechangedate, fields.rate, fields.payfrequency, fields.modifieddate)
  
    override def copy[NewRow](prefix: Option[String], extract: NewRow => EmployeepayhistoryRow, merge: (NewRow, EmployeepayhistoryRow) => NewRow): Impl[NewRow] =
      new Impl(prefix, extract, merge)
  }
  
}
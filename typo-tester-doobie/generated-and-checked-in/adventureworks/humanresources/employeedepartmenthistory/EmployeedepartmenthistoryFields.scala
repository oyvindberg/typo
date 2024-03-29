/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package humanresources
package employeedepartmenthistory

import adventureworks.customtypes.TypoLocalDate
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.humanresources.department.DepartmentId
import adventureworks.humanresources.shift.ShiftId
import adventureworks.person.businessentity.BusinessentityId
import typo.dsl.SqlExpr.Field
import typo.dsl.SqlExpr.FieldLikeNoHkt
import typo.dsl.SqlExpr.IdField
import typo.dsl.SqlExpr.OptField
import typo.dsl.Structure.Relation

trait EmployeedepartmenthistoryFields[Row] {
  val businessentityid: IdField[BusinessentityId, Row]
  val departmentid: IdField[DepartmentId, Row]
  val shiftid: IdField[ShiftId, Row]
  val startdate: IdField[TypoLocalDate, Row]
  val enddate: OptField[TypoLocalDate, Row]
  val modifieddate: Field[TypoLocalDateTime, Row]
}

object EmployeedepartmenthistoryFields {
  val structure: Relation[EmployeedepartmenthistoryFields, EmployeedepartmenthistoryRow, EmployeedepartmenthistoryRow] = 
    new Impl(None, identity, (_, x) => x)
    
  private final class Impl[Row](val prefix: Option[String], val extract: Row => EmployeedepartmenthistoryRow, val merge: (Row, EmployeedepartmenthistoryRow) => Row)
    extends Relation[EmployeedepartmenthistoryFields, EmployeedepartmenthistoryRow, Row] { 
  
    override val fields: EmployeedepartmenthistoryFields[Row] = new EmployeedepartmenthistoryFields[Row] {
      override val businessentityid = new IdField[BusinessentityId, Row](prefix, "businessentityid", None, Some("int4"))(x => extract(x).businessentityid, (row, value) => merge(row, extract(row).copy(businessentityid = value)))
      override val departmentid = new IdField[DepartmentId, Row](prefix, "departmentid", None, Some("int2"))(x => extract(x).departmentid, (row, value) => merge(row, extract(row).copy(departmentid = value)))
      override val shiftid = new IdField[ShiftId, Row](prefix, "shiftid", None, Some("int2"))(x => extract(x).shiftid, (row, value) => merge(row, extract(row).copy(shiftid = value)))
      override val startdate = new IdField[TypoLocalDate, Row](prefix, "startdate", Some("text"), Some("date"))(x => extract(x).startdate, (row, value) => merge(row, extract(row).copy(startdate = value)))
      override val enddate = new OptField[TypoLocalDate, Row](prefix, "enddate", Some("text"), Some("date"))(x => extract(x).enddate, (row, value) => merge(row, extract(row).copy(enddate = value)))
      override val modifieddate = new Field[TypoLocalDateTime, Row](prefix, "modifieddate", Some("text"), Some("timestamp"))(x => extract(x).modifieddate, (row, value) => merge(row, extract(row).copy(modifieddate = value)))
    }
  
    override val columns: List[FieldLikeNoHkt[?, Row]] =
      List[FieldLikeNoHkt[?, Row]](fields.businessentityid, fields.departmentid, fields.shiftid, fields.startdate, fields.enddate, fields.modifieddate)
  
    override def copy[NewRow](prefix: Option[String], extract: NewRow => EmployeedepartmenthistoryRow, merge: (NewRow, EmployeedepartmenthistoryRow) => NewRow): Impl[NewRow] =
      new Impl(prefix, extract, merge)
  }
  
}

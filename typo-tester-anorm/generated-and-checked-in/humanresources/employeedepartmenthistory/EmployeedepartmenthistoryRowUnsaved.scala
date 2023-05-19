/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package humanresources
package employeedepartmenthistory

import adventureworks.Defaulted
import adventureworks.humanresources.department.DepartmentId
import adventureworks.humanresources.shift.ShiftId
import adventureworks.person.businessentity.BusinessentityId
import java.time.LocalDate
import java.time.LocalDateTime
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

/** This class corresponds to a row in table `humanresources.employeedepartmenthistory` which has not been persisted yet */
case class EmployeedepartmenthistoryRowUnsaved(
  /** Employee identification number. Foreign key to Employee.BusinessEntityID.
      Points to [[employee.EmployeeRow.businessentityid]] */
  businessentityid: BusinessentityId,
  /** Department in which the employee worked including currently. Foreign key to Department.DepartmentID.
      Points to [[department.DepartmentRow.departmentid]] */
  departmentid: DepartmentId,
  /** Identifies which 8-hour shift the employee works. Foreign key to Shift.Shift.ID.
      Points to [[shift.ShiftRow.shiftid]] */
  shiftid: ShiftId,
  /** Date the employee started work in the department. */
  startdate: LocalDate,
  /** Date the employee left the department. NULL = Current department. */
  enddate: Option[LocalDate],
  /** Default: now() */
  modifieddate: Defaulted[LocalDateTime] = Defaulted.UseDefault
) {
  def toRow(modifieddateDefault: => LocalDateTime): EmployeedepartmenthistoryRow =
    EmployeedepartmenthistoryRow(
      businessentityid = businessentityid,
      departmentid = departmentid,
      shiftid = shiftid,
      startdate = startdate,
      enddate = enddate,
      modifieddate = modifieddate match {
                       case Defaulted.UseDefault => modifieddateDefault
                       case Defaulted.Provided(value) => value
                     }
    )
}
object EmployeedepartmenthistoryRowUnsaved {
  implicit val oFormat: OFormat[EmployeedepartmenthistoryRowUnsaved] = new OFormat[EmployeedepartmenthistoryRowUnsaved]{
    override def writes(o: EmployeedepartmenthistoryRowUnsaved): JsObject =
      Json.obj(
        "businessentityid" -> o.businessentityid,
        "departmentid" -> o.departmentid,
        "shiftid" -> o.shiftid,
        "startdate" -> o.startdate,
        "enddate" -> o.enddate,
        "modifieddate" -> o.modifieddate
      )
  
    override def reads(json: JsValue): JsResult[EmployeedepartmenthistoryRowUnsaved] = {
      JsResult.fromTry(
        Try(
          EmployeedepartmenthistoryRowUnsaved(
            businessentityid = json.\("businessentityid").as[BusinessentityId],
            departmentid = json.\("departmentid").as[DepartmentId],
            shiftid = json.\("shiftid").as[ShiftId],
            startdate = json.\("startdate").as[LocalDate],
            enddate = json.\("enddate").toOption.map(_.as[LocalDate]),
            modifieddate = json.\("modifieddate").as[Defaulted[LocalDateTime]]
          )
        )
      )
    }
  }
}
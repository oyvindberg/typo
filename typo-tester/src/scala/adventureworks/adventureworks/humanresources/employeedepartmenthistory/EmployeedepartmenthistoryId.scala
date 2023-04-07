/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package humanresources
package employeedepartmenthistory

import adventureworks.humanresources.department.DepartmentId
import adventureworks.humanresources.shift.ShiftId
import adventureworks.person.businessentity.BusinessentityId
import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class EmployeedepartmenthistoryId(businessentityid: BusinessentityId, startdate: String, departmentid: DepartmentId, shiftid: ShiftId)
object EmployeedepartmenthistoryId {
  implicit def ordering: Ordering[EmployeedepartmenthistoryId] = Ordering.by(x => (x.businessentityid, x.startdate, x.departmentid, x.shiftid))
  implicit val oFormat: OFormat[EmployeedepartmenthistoryId] = new OFormat[EmployeedepartmenthistoryId]{
    override def writes(o: EmployeedepartmenthistoryId): JsObject =
      Json.obj(
        "businessentityid" -> o.businessentityid,
        "startdate" -> o.startdate,
        "departmentid" -> o.departmentid,
        "shiftid" -> o.shiftid
      )
  
    override def reads(json: JsValue): JsResult[EmployeedepartmenthistoryId] = {
      JsResult.fromTry(
        Try(
          EmployeedepartmenthistoryId(
            businessentityid = json.\("businessentityid").as[BusinessentityId],
            startdate = json.\("startdate").as[String],
            departmentid = json.\("departmentid").as[DepartmentId],
            shiftid = json.\("shiftid").as[ShiftId]
          )
        )
      )
    }
  }
  def rowParser(prefix: String): RowParser[EmployeedepartmenthistoryId] = { row =>
    Success(
      EmployeedepartmenthistoryId(
        businessentityid = row[BusinessentityId](prefix + "businessentityid"),
        startdate = row[String](prefix + "startdate"),
        departmentid = row[DepartmentId](prefix + "departmentid"),
        shiftid = row[ShiftId](prefix + "shiftid")
      )
    )
  }

}
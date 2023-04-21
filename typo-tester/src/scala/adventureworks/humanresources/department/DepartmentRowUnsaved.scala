/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package humanresources
package department

import adventureworks.Defaulted
import adventureworks.public.Name
import java.time.LocalDateTime
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

/** This class corresponds to a row in table `humanresources.department` which has not been persisted yet */
case class DepartmentRowUnsaved(
  /** Name of the department. */
  name: Name,
  /** Name of the group to which the department belongs. */
  groupname: Name,
  /** Default: now() */
  modifieddate: Defaulted[LocalDateTime]
) {
  def unsafeToRow(departmentid: DepartmentId): DepartmentRow =
    DepartmentRow(
      departmentid = departmentid,
      name = name,
      groupname = groupname,
      modifieddate = modifieddate match {
                       case Defaulted.UseDefault => sys.error("cannot produce row when you depend on a value which is defaulted in database")
                       case Defaulted.Provided(value) => value
                     }
    )
}
object DepartmentRowUnsaved {
  implicit val oFormat: OFormat[DepartmentRowUnsaved] = new OFormat[DepartmentRowUnsaved]{
    override def writes(o: DepartmentRowUnsaved): JsObject =
      Json.obj(
        "name" -> o.name,
        "groupname" -> o.groupname,
        "modifieddate" -> o.modifieddate
      )
  
    override def reads(json: JsValue): JsResult[DepartmentRowUnsaved] = {
      JsResult.fromTry(
        Try(
          DepartmentRowUnsaved(
            name = json.\("name").as[Name],
            groupname = json.\("groupname").as[Name],
            modifieddate = json.\("modifieddate").as[Defaulted[LocalDateTime]]
          )
        )
      )
    }
  }
}
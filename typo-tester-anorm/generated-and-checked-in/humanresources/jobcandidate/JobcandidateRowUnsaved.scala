/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package humanresources
package jobcandidate

import adventureworks.Defaulted
import adventureworks.person.businessentity.BusinessentityId
import java.time.LocalDateTime
import org.postgresql.jdbc.PgSQLXML
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

/** This class corresponds to a row in table `humanresources.jobcandidate` which has not been persisted yet */
case class JobcandidateRowUnsaved(
  /** Employee identification number if applicant was hired. Foreign key to Employee.BusinessEntityID.
      Points to [[employee.EmployeeRow.businessentityid]] */
  businessentityid: Option[BusinessentityId],
  /** RÃ©sumÃ© in XML format. */
  resume: Option[PgSQLXML],
  /** Default: nextval('humanresources.jobcandidate_jobcandidateid_seq'::regclass)
      Primary key for JobCandidate records. */
  jobcandidateid: Defaulted[JobcandidateId] = Defaulted.UseDefault,
  /** Default: now() */
  modifieddate: Defaulted[LocalDateTime] = Defaulted.UseDefault
) {
  def toRow(jobcandidateidDefault: => JobcandidateId, modifieddateDefault: => LocalDateTime): JobcandidateRow =
    JobcandidateRow(
      businessentityid = businessentityid,
      resume = resume,
      jobcandidateid = jobcandidateid match {
                         case Defaulted.UseDefault => jobcandidateidDefault
                         case Defaulted.Provided(value) => value
                       },
      modifieddate = modifieddate match {
                       case Defaulted.UseDefault => modifieddateDefault
                       case Defaulted.Provided(value) => value
                     }
    )
}
object JobcandidateRowUnsaved {
  implicit val oFormat: OFormat[JobcandidateRowUnsaved] = new OFormat[JobcandidateRowUnsaved]{
    override def writes(o: JobcandidateRowUnsaved): JsObject =
      Json.obj(
        "businessentityid" -> o.businessentityid,
        "resume" -> o.resume,
        "jobcandidateid" -> o.jobcandidateid,
        "modifieddate" -> o.modifieddate
      )
  
    override def reads(json: JsValue): JsResult[JobcandidateRowUnsaved] = {
      JsResult.fromTry(
        Try(
          JobcandidateRowUnsaved(
            businessentityid = json.\("businessentityid").toOption.map(_.as[BusinessentityId]),
            resume = json.\("resume").toOption.map(_.as[PgSQLXML]),
            jobcandidateid = json.\("jobcandidateid").as[Defaulted[JobcandidateId]],
            modifieddate = json.\("modifieddate").as[Defaulted[LocalDateTime]]
          )
        )
      )
    }
  }
}
/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package humanresources
package vjobcandidateeducation

import adventureworks.customtypes.TypoLocalDate
import adventureworks.humanresources.jobcandidate.JobcandidateId
import anorm.Column
import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.OWrites
import play.api.libs.json.Reads
import play.api.libs.json.Writes
import scala.collection.immutable.ListMap
import scala.util.Try

case class VjobcandidateeducationViewRow(
  /** Points to [[jobcandidate.JobcandidateRow.jobcandidateid]] */
  jobcandidateid: JobcandidateId,
  EduLevel: /* nullability unknown */ Option[/* max 50 chars */ String],
  EduStartDate: /* nullability unknown */ Option[TypoLocalDate],
  EduEndDate: /* nullability unknown */ Option[TypoLocalDate],
  EduDegree: /* nullability unknown */ Option[/* max 50 chars */ String],
  EduMajor: /* nullability unknown */ Option[/* max 50 chars */ String],
  EduMinor: /* nullability unknown */ Option[/* max 50 chars */ String],
  EduGPA: /* nullability unknown */ Option[/* max 5 chars */ String],
  EduGPAScale: /* nullability unknown */ Option[/* max 5 chars */ String],
  EduSchool: /* nullability unknown */ Option[/* max 100 chars */ String],
  EduLocCountryRegion: /* nullability unknown */ Option[/* max 100 chars */ String],
  EduLocState: /* nullability unknown */ Option[/* max 100 chars */ String],
  EduLocCity: /* nullability unknown */ Option[/* max 100 chars */ String]
)

object VjobcandidateeducationViewRow {
  implicit lazy val reads: Reads[VjobcandidateeducationViewRow] = Reads[VjobcandidateeducationViewRow](json => JsResult.fromTry(
      Try(
        VjobcandidateeducationViewRow(
          jobcandidateid = json.\("jobcandidateid").as(JobcandidateId.reads),
          EduLevel = json.\("Edu.Level").toOption.map(_.as(Reads.StringReads)),
          EduStartDate = json.\("Edu.StartDate").toOption.map(_.as(TypoLocalDate.reads)),
          EduEndDate = json.\("Edu.EndDate").toOption.map(_.as(TypoLocalDate.reads)),
          EduDegree = json.\("Edu.Degree").toOption.map(_.as(Reads.StringReads)),
          EduMajor = json.\("Edu.Major").toOption.map(_.as(Reads.StringReads)),
          EduMinor = json.\("Edu.Minor").toOption.map(_.as(Reads.StringReads)),
          EduGPA = json.\("Edu.GPA").toOption.map(_.as(Reads.StringReads)),
          EduGPAScale = json.\("Edu.GPAScale").toOption.map(_.as(Reads.StringReads)),
          EduSchool = json.\("Edu.School").toOption.map(_.as(Reads.StringReads)),
          EduLocCountryRegion = json.\("Edu.Loc.CountryRegion").toOption.map(_.as(Reads.StringReads)),
          EduLocState = json.\("Edu.Loc.State").toOption.map(_.as(Reads.StringReads)),
          EduLocCity = json.\("Edu.Loc.City").toOption.map(_.as(Reads.StringReads))
        )
      )
    ),
  )
  def rowParser(idx: Int): RowParser[VjobcandidateeducationViewRow] = RowParser[VjobcandidateeducationViewRow] { row =>
    Success(
      VjobcandidateeducationViewRow(
        jobcandidateid = row(idx + 0)(JobcandidateId.column),
        EduLevel = row(idx + 1)(Column.columnToOption(Column.columnToString)),
        EduStartDate = row(idx + 2)(Column.columnToOption(TypoLocalDate.column)),
        EduEndDate = row(idx + 3)(Column.columnToOption(TypoLocalDate.column)),
        EduDegree = row(idx + 4)(Column.columnToOption(Column.columnToString)),
        EduMajor = row(idx + 5)(Column.columnToOption(Column.columnToString)),
        EduMinor = row(idx + 6)(Column.columnToOption(Column.columnToString)),
        EduGPA = row(idx + 7)(Column.columnToOption(Column.columnToString)),
        EduGPAScale = row(idx + 8)(Column.columnToOption(Column.columnToString)),
        EduSchool = row(idx + 9)(Column.columnToOption(Column.columnToString)),
        EduLocCountryRegion = row(idx + 10)(Column.columnToOption(Column.columnToString)),
        EduLocState = row(idx + 11)(Column.columnToOption(Column.columnToString)),
        EduLocCity = row(idx + 12)(Column.columnToOption(Column.columnToString))
      )
    )
  }
  implicit lazy val writes: OWrites[VjobcandidateeducationViewRow] = OWrites[VjobcandidateeducationViewRow](o =>
    new JsObject(ListMap[String, JsValue](
      "jobcandidateid" -> JobcandidateId.writes.writes(o.jobcandidateid),
      "Edu.Level" -> Writes.OptionWrites(Writes.StringWrites).writes(o.EduLevel),
      "Edu.StartDate" -> Writes.OptionWrites(TypoLocalDate.writes).writes(o.EduStartDate),
      "Edu.EndDate" -> Writes.OptionWrites(TypoLocalDate.writes).writes(o.EduEndDate),
      "Edu.Degree" -> Writes.OptionWrites(Writes.StringWrites).writes(o.EduDegree),
      "Edu.Major" -> Writes.OptionWrites(Writes.StringWrites).writes(o.EduMajor),
      "Edu.Minor" -> Writes.OptionWrites(Writes.StringWrites).writes(o.EduMinor),
      "Edu.GPA" -> Writes.OptionWrites(Writes.StringWrites).writes(o.EduGPA),
      "Edu.GPAScale" -> Writes.OptionWrites(Writes.StringWrites).writes(o.EduGPAScale),
      "Edu.School" -> Writes.OptionWrites(Writes.StringWrites).writes(o.EduSchool),
      "Edu.Loc.CountryRegion" -> Writes.OptionWrites(Writes.StringWrites).writes(o.EduLocCountryRegion),
      "Edu.Loc.State" -> Writes.OptionWrites(Writes.StringWrites).writes(o.EduLocState),
      "Edu.Loc.City" -> Writes.OptionWrites(Writes.StringWrites).writes(o.EduLocCity)
    ))
  )
}

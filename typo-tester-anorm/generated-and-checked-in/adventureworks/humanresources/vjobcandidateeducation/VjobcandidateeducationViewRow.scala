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

/** View: humanresources.vjobcandidateeducation */
case class VjobcandidateeducationViewRow(
  /** Points to [[jobcandidate.JobcandidateRow.jobcandidateid]] */
  jobcandidateid: JobcandidateId,
  eduLevel: /* nullability unknown */ Option[/* max 50 chars */ String],
  eduStartDate: /* nullability unknown */ Option[TypoLocalDate],
  eduEndDate: /* nullability unknown */ Option[TypoLocalDate],
  eduDegree: /* nullability unknown */ Option[/* max 50 chars */ String],
  eduMajor: /* nullability unknown */ Option[/* max 50 chars */ String],
  eduMinor: /* nullability unknown */ Option[/* max 50 chars */ String],
  eduGPA: /* nullability unknown */ Option[/* max 5 chars */ String],
  eduGPAScale: /* nullability unknown */ Option[/* max 5 chars */ String],
  eduSchool: /* nullability unknown */ Option[/* max 100 chars */ String],
  eduLocCountryRegion: /* nullability unknown */ Option[/* max 100 chars */ String],
  eduLocState: /* nullability unknown */ Option[/* max 100 chars */ String],
  eduLocCity: /* nullability unknown */ Option[/* max 100 chars */ String]
)

object VjobcandidateeducationViewRow {
  implicit lazy val reads: Reads[VjobcandidateeducationViewRow] = Reads[VjobcandidateeducationViewRow](json => JsResult.fromTry(
      Try(
        VjobcandidateeducationViewRow(
          jobcandidateid = json.\("jobcandidateid").as(JobcandidateId.reads),
          eduLevel = json.\("Edu.Level").toOption.map(_.as(Reads.StringReads)),
          eduStartDate = json.\("Edu.StartDate").toOption.map(_.as(TypoLocalDate.reads)),
          eduEndDate = json.\("Edu.EndDate").toOption.map(_.as(TypoLocalDate.reads)),
          eduDegree = json.\("Edu.Degree").toOption.map(_.as(Reads.StringReads)),
          eduMajor = json.\("Edu.Major").toOption.map(_.as(Reads.StringReads)),
          eduMinor = json.\("Edu.Minor").toOption.map(_.as(Reads.StringReads)),
          eduGPA = json.\("Edu.GPA").toOption.map(_.as(Reads.StringReads)),
          eduGPAScale = json.\("Edu.GPAScale").toOption.map(_.as(Reads.StringReads)),
          eduSchool = json.\("Edu.School").toOption.map(_.as(Reads.StringReads)),
          eduLocCountryRegion = json.\("Edu.Loc.CountryRegion").toOption.map(_.as(Reads.StringReads)),
          eduLocState = json.\("Edu.Loc.State").toOption.map(_.as(Reads.StringReads)),
          eduLocCity = json.\("Edu.Loc.City").toOption.map(_.as(Reads.StringReads))
        )
      )
    ),
  )
  def rowParser(idx: Int): RowParser[VjobcandidateeducationViewRow] = RowParser[VjobcandidateeducationViewRow] { row =>
    Success(
      VjobcandidateeducationViewRow(
        jobcandidateid = row(idx + 0)(JobcandidateId.column),
        eduLevel = row(idx + 1)(Column.columnToOption(Column.columnToString)),
        eduStartDate = row(idx + 2)(Column.columnToOption(TypoLocalDate.column)),
        eduEndDate = row(idx + 3)(Column.columnToOption(TypoLocalDate.column)),
        eduDegree = row(idx + 4)(Column.columnToOption(Column.columnToString)),
        eduMajor = row(idx + 5)(Column.columnToOption(Column.columnToString)),
        eduMinor = row(idx + 6)(Column.columnToOption(Column.columnToString)),
        eduGPA = row(idx + 7)(Column.columnToOption(Column.columnToString)),
        eduGPAScale = row(idx + 8)(Column.columnToOption(Column.columnToString)),
        eduSchool = row(idx + 9)(Column.columnToOption(Column.columnToString)),
        eduLocCountryRegion = row(idx + 10)(Column.columnToOption(Column.columnToString)),
        eduLocState = row(idx + 11)(Column.columnToOption(Column.columnToString)),
        eduLocCity = row(idx + 12)(Column.columnToOption(Column.columnToString))
      )
    )
  }
  implicit lazy val writes: OWrites[VjobcandidateeducationViewRow] = OWrites[VjobcandidateeducationViewRow](o =>
    new JsObject(ListMap[String, JsValue](
      "jobcandidateid" -> JobcandidateId.writes.writes(o.jobcandidateid),
      "Edu.Level" -> Writes.OptionWrites(Writes.StringWrites).writes(o.eduLevel),
      "Edu.StartDate" -> Writes.OptionWrites(TypoLocalDate.writes).writes(o.eduStartDate),
      "Edu.EndDate" -> Writes.OptionWrites(TypoLocalDate.writes).writes(o.eduEndDate),
      "Edu.Degree" -> Writes.OptionWrites(Writes.StringWrites).writes(o.eduDegree),
      "Edu.Major" -> Writes.OptionWrites(Writes.StringWrites).writes(o.eduMajor),
      "Edu.Minor" -> Writes.OptionWrites(Writes.StringWrites).writes(o.eduMinor),
      "Edu.GPA" -> Writes.OptionWrites(Writes.StringWrites).writes(o.eduGPA),
      "Edu.GPAScale" -> Writes.OptionWrites(Writes.StringWrites).writes(o.eduGPAScale),
      "Edu.School" -> Writes.OptionWrites(Writes.StringWrites).writes(o.eduSchool),
      "Edu.Loc.CountryRegion" -> Writes.OptionWrites(Writes.StringWrites).writes(o.eduLocCountryRegion),
      "Edu.Loc.State" -> Writes.OptionWrites(Writes.StringWrites).writes(o.eduLocState),
      "Edu.Loc.City" -> Writes.OptionWrites(Writes.StringWrites).writes(o.eduLocCity)
    ))
  )
}

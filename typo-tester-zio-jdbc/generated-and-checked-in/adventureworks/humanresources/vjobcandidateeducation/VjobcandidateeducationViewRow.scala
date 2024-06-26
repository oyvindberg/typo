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
import java.sql.ResultSet
import zio.jdbc.JdbcDecoder
import zio.json.JsonDecoder
import zio.json.JsonEncoder
import zio.json.ast.Json
import zio.json.internal.Write

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
  implicit lazy val jdbcDecoder: JdbcDecoder[VjobcandidateeducationViewRow] = new JdbcDecoder[VjobcandidateeducationViewRow] {
    override def unsafeDecode(columIndex: Int, rs: ResultSet): (Int, VjobcandidateeducationViewRow) =
      columIndex + 12 ->
        VjobcandidateeducationViewRow(
          jobcandidateid = JobcandidateId.jdbcDecoder.unsafeDecode(columIndex + 0, rs)._2,
          eduLevel = JdbcDecoder.optionDecoder(JdbcDecoder.stringDecoder).unsafeDecode(columIndex + 1, rs)._2,
          eduStartDate = JdbcDecoder.optionDecoder(TypoLocalDate.jdbcDecoder).unsafeDecode(columIndex + 2, rs)._2,
          eduEndDate = JdbcDecoder.optionDecoder(TypoLocalDate.jdbcDecoder).unsafeDecode(columIndex + 3, rs)._2,
          eduDegree = JdbcDecoder.optionDecoder(JdbcDecoder.stringDecoder).unsafeDecode(columIndex + 4, rs)._2,
          eduMajor = JdbcDecoder.optionDecoder(JdbcDecoder.stringDecoder).unsafeDecode(columIndex + 5, rs)._2,
          eduMinor = JdbcDecoder.optionDecoder(JdbcDecoder.stringDecoder).unsafeDecode(columIndex + 6, rs)._2,
          eduGPA = JdbcDecoder.optionDecoder(JdbcDecoder.stringDecoder).unsafeDecode(columIndex + 7, rs)._2,
          eduGPAScale = JdbcDecoder.optionDecoder(JdbcDecoder.stringDecoder).unsafeDecode(columIndex + 8, rs)._2,
          eduSchool = JdbcDecoder.optionDecoder(JdbcDecoder.stringDecoder).unsafeDecode(columIndex + 9, rs)._2,
          eduLocCountryRegion = JdbcDecoder.optionDecoder(JdbcDecoder.stringDecoder).unsafeDecode(columIndex + 10, rs)._2,
          eduLocState = JdbcDecoder.optionDecoder(JdbcDecoder.stringDecoder).unsafeDecode(columIndex + 11, rs)._2,
          eduLocCity = JdbcDecoder.optionDecoder(JdbcDecoder.stringDecoder).unsafeDecode(columIndex + 12, rs)._2
        )
  }
  implicit lazy val jsonDecoder: JsonDecoder[VjobcandidateeducationViewRow] = JsonDecoder[Json.Obj].mapOrFail { jsonObj =>
    val jobcandidateid = jsonObj.get("jobcandidateid").toRight("Missing field 'jobcandidateid'").flatMap(_.as(JobcandidateId.jsonDecoder))
    val eduLevel = jsonObj.get("Edu.Level").fold[Either[String, Option[String]]](Right(None))(_.as(JsonDecoder.option(using JsonDecoder.string)))
    val eduStartDate = jsonObj.get("Edu.StartDate").fold[Either[String, Option[TypoLocalDate]]](Right(None))(_.as(JsonDecoder.option(using TypoLocalDate.jsonDecoder)))
    val eduEndDate = jsonObj.get("Edu.EndDate").fold[Either[String, Option[TypoLocalDate]]](Right(None))(_.as(JsonDecoder.option(using TypoLocalDate.jsonDecoder)))
    val eduDegree = jsonObj.get("Edu.Degree").fold[Either[String, Option[String]]](Right(None))(_.as(JsonDecoder.option(using JsonDecoder.string)))
    val eduMajor = jsonObj.get("Edu.Major").fold[Either[String, Option[String]]](Right(None))(_.as(JsonDecoder.option(using JsonDecoder.string)))
    val eduMinor = jsonObj.get("Edu.Minor").fold[Either[String, Option[String]]](Right(None))(_.as(JsonDecoder.option(using JsonDecoder.string)))
    val eduGPA = jsonObj.get("Edu.GPA").fold[Either[String, Option[String]]](Right(None))(_.as(JsonDecoder.option(using JsonDecoder.string)))
    val eduGPAScale = jsonObj.get("Edu.GPAScale").fold[Either[String, Option[String]]](Right(None))(_.as(JsonDecoder.option(using JsonDecoder.string)))
    val eduSchool = jsonObj.get("Edu.School").fold[Either[String, Option[String]]](Right(None))(_.as(JsonDecoder.option(using JsonDecoder.string)))
    val eduLocCountryRegion = jsonObj.get("Edu.Loc.CountryRegion").fold[Either[String, Option[String]]](Right(None))(_.as(JsonDecoder.option(using JsonDecoder.string)))
    val eduLocState = jsonObj.get("Edu.Loc.State").fold[Either[String, Option[String]]](Right(None))(_.as(JsonDecoder.option(using JsonDecoder.string)))
    val eduLocCity = jsonObj.get("Edu.Loc.City").fold[Either[String, Option[String]]](Right(None))(_.as(JsonDecoder.option(using JsonDecoder.string)))
    if (jobcandidateid.isRight && eduLevel.isRight && eduStartDate.isRight && eduEndDate.isRight && eduDegree.isRight && eduMajor.isRight && eduMinor.isRight && eduGPA.isRight && eduGPAScale.isRight && eduSchool.isRight && eduLocCountryRegion.isRight && eduLocState.isRight && eduLocCity.isRight)
      Right(VjobcandidateeducationViewRow(jobcandidateid = jobcandidateid.toOption.get, eduLevel = eduLevel.toOption.get, eduStartDate = eduStartDate.toOption.get, eduEndDate = eduEndDate.toOption.get, eduDegree = eduDegree.toOption.get, eduMajor = eduMajor.toOption.get, eduMinor = eduMinor.toOption.get, eduGPA = eduGPA.toOption.get, eduGPAScale = eduGPAScale.toOption.get, eduSchool = eduSchool.toOption.get, eduLocCountryRegion = eduLocCountryRegion.toOption.get, eduLocState = eduLocState.toOption.get, eduLocCity = eduLocCity.toOption.get))
    else Left(List[Either[String, Any]](jobcandidateid, eduLevel, eduStartDate, eduEndDate, eduDegree, eduMajor, eduMinor, eduGPA, eduGPAScale, eduSchool, eduLocCountryRegion, eduLocState, eduLocCity).flatMap(_.left.toOption).mkString(", "))
  }
  implicit lazy val jsonEncoder: JsonEncoder[VjobcandidateeducationViewRow] = new JsonEncoder[VjobcandidateeducationViewRow] {
    override def unsafeEncode(a: VjobcandidateeducationViewRow, indent: Option[Int], out: Write): Unit = {
      out.write("{")
      out.write(""""jobcandidateid":""")
      JobcandidateId.jsonEncoder.unsafeEncode(a.jobcandidateid, indent, out)
      out.write(",")
      out.write(""""Edu.Level":""")
      JsonEncoder.option(using JsonEncoder.string).unsafeEncode(a.eduLevel, indent, out)
      out.write(",")
      out.write(""""Edu.StartDate":""")
      JsonEncoder.option(using TypoLocalDate.jsonEncoder).unsafeEncode(a.eduStartDate, indent, out)
      out.write(",")
      out.write(""""Edu.EndDate":""")
      JsonEncoder.option(using TypoLocalDate.jsonEncoder).unsafeEncode(a.eduEndDate, indent, out)
      out.write(",")
      out.write(""""Edu.Degree":""")
      JsonEncoder.option(using JsonEncoder.string).unsafeEncode(a.eduDegree, indent, out)
      out.write(",")
      out.write(""""Edu.Major":""")
      JsonEncoder.option(using JsonEncoder.string).unsafeEncode(a.eduMajor, indent, out)
      out.write(",")
      out.write(""""Edu.Minor":""")
      JsonEncoder.option(using JsonEncoder.string).unsafeEncode(a.eduMinor, indent, out)
      out.write(",")
      out.write(""""Edu.GPA":""")
      JsonEncoder.option(using JsonEncoder.string).unsafeEncode(a.eduGPA, indent, out)
      out.write(",")
      out.write(""""Edu.GPAScale":""")
      JsonEncoder.option(using JsonEncoder.string).unsafeEncode(a.eduGPAScale, indent, out)
      out.write(",")
      out.write(""""Edu.School":""")
      JsonEncoder.option(using JsonEncoder.string).unsafeEncode(a.eduSchool, indent, out)
      out.write(",")
      out.write(""""Edu.Loc.CountryRegion":""")
      JsonEncoder.option(using JsonEncoder.string).unsafeEncode(a.eduLocCountryRegion, indent, out)
      out.write(",")
      out.write(""""Edu.Loc.State":""")
      JsonEncoder.option(using JsonEncoder.string).unsafeEncode(a.eduLocState, indent, out)
      out.write(",")
      out.write(""""Edu.Loc.City":""")
      JsonEncoder.option(using JsonEncoder.string).unsafeEncode(a.eduLocCity, indent, out)
      out.write("}")
    }
  }
}

/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package humanresources
package vjobcandidateemployment

import adventureworks.customtypes.TypoLocalDate
import adventureworks.humanresources.jobcandidate.JobcandidateId
import doobie.enumerated.Nullability
import doobie.util.Read
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.Encoder
import java.sql.ResultSet

case class VjobcandidateemploymentViewRow(
  /** Points to [[jobcandidate.JobcandidateRow.jobcandidateid]] */
  jobcandidateid: JobcandidateId,
  EmpStartDate: /* nullability unknown */ Option[TypoLocalDate],
  EmpEndDate: /* nullability unknown */ Option[TypoLocalDate],
  EmpOrgName: /* nullability unknown */ Option[/* max 100 chars */ String],
  EmpJobTitle: /* nullability unknown */ Option[/* max 100 chars */ String],
  EmpResponsibility: /* nullability unknown */ Option[String],
  EmpFunctionCategory: /* nullability unknown */ Option[String],
  EmpIndustryCategory: /* nullability unknown */ Option[String],
  EmpLocCountryRegion: /* nullability unknown */ Option[String],
  EmpLocState: /* nullability unknown */ Option[String],
  EmpLocCity: /* nullability unknown */ Option[String]
)

object VjobcandidateemploymentViewRow {
  implicit lazy val decoder: Decoder[VjobcandidateemploymentViewRow] = Decoder.forProduct11[VjobcandidateemploymentViewRow, JobcandidateId, /* nullability unknown */ Option[TypoLocalDate], /* nullability unknown */ Option[TypoLocalDate], /* nullability unknown */ Option[/* max 100 chars */ String], /* nullability unknown */ Option[/* max 100 chars */ String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String]]("jobcandidateid", "Emp.StartDate", "Emp.EndDate", "Emp.OrgName", "Emp.JobTitle", "Emp.Responsibility", "Emp.FunctionCategory", "Emp.IndustryCategory", "Emp.Loc.CountryRegion", "Emp.Loc.State", "Emp.Loc.City")(VjobcandidateemploymentViewRow.apply)(JobcandidateId.decoder, Decoder.decodeOption(TypoLocalDate.decoder), Decoder.decodeOption(TypoLocalDate.decoder), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString))
  implicit lazy val encoder: Encoder[VjobcandidateemploymentViewRow] = Encoder.forProduct11[VjobcandidateemploymentViewRow, JobcandidateId, /* nullability unknown */ Option[TypoLocalDate], /* nullability unknown */ Option[TypoLocalDate], /* nullability unknown */ Option[/* max 100 chars */ String], /* nullability unknown */ Option[/* max 100 chars */ String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String]]("jobcandidateid", "Emp.StartDate", "Emp.EndDate", "Emp.OrgName", "Emp.JobTitle", "Emp.Responsibility", "Emp.FunctionCategory", "Emp.IndustryCategory", "Emp.Loc.CountryRegion", "Emp.Loc.State", "Emp.Loc.City")(x => (x.jobcandidateid, x.EmpStartDate, x.EmpEndDate, x.EmpOrgName, x.EmpJobTitle, x.EmpResponsibility, x.EmpFunctionCategory, x.EmpIndustryCategory, x.EmpLocCountryRegion, x.EmpLocState, x.EmpLocCity))(JobcandidateId.encoder, Encoder.encodeOption(TypoLocalDate.encoder), Encoder.encodeOption(TypoLocalDate.encoder), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString))
  implicit lazy val read: Read[VjobcandidateemploymentViewRow] = new Read[VjobcandidateemploymentViewRow](
    gets = List(
      (JobcandidateId.get, Nullability.NoNulls),
      (TypoLocalDate.get, Nullability.Nullable),
      (TypoLocalDate.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable)
    ),
    unsafeGet = (rs: ResultSet, i: Int) => VjobcandidateemploymentViewRow(
      jobcandidateid = JobcandidateId.get.unsafeGetNonNullable(rs, i + 0),
      EmpStartDate = TypoLocalDate.get.unsafeGetNullable(rs, i + 1),
      EmpEndDate = TypoLocalDate.get.unsafeGetNullable(rs, i + 2),
      EmpOrgName = Meta.StringMeta.get.unsafeGetNullable(rs, i + 3),
      EmpJobTitle = Meta.StringMeta.get.unsafeGetNullable(rs, i + 4),
      EmpResponsibility = Meta.StringMeta.get.unsafeGetNullable(rs, i + 5),
      EmpFunctionCategory = Meta.StringMeta.get.unsafeGetNullable(rs, i + 6),
      EmpIndustryCategory = Meta.StringMeta.get.unsafeGetNullable(rs, i + 7),
      EmpLocCountryRegion = Meta.StringMeta.get.unsafeGetNullable(rs, i + 8),
      EmpLocState = Meta.StringMeta.get.unsafeGetNullable(rs, i + 9),
      EmpLocCity = Meta.StringMeta.get.unsafeGetNullable(rs, i + 10)
    )
  )
}
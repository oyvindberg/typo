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
import doobie.util.Read
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.Encoder

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
  implicit lazy val decoder: Decoder[VjobcandidateeducationViewRow] = Decoder.forProduct13[VjobcandidateeducationViewRow, JobcandidateId, /* nullability unknown */ Option[/* max 50 chars */ String], /* nullability unknown */ Option[TypoLocalDate], /* nullability unknown */ Option[TypoLocalDate], /* nullability unknown */ Option[/* max 50 chars */ String], /* nullability unknown */ Option[/* max 50 chars */ String], /* nullability unknown */ Option[/* max 50 chars */ String], /* nullability unknown */ Option[/* max 5 chars */ String], /* nullability unknown */ Option[/* max 5 chars */ String], /* nullability unknown */ Option[/* max 100 chars */ String], /* nullability unknown */ Option[/* max 100 chars */ String], /* nullability unknown */ Option[/* max 100 chars */ String], /* nullability unknown */ Option[/* max 100 chars */ String]]("jobcandidateid", "Edu.Level", "Edu.StartDate", "Edu.EndDate", "Edu.Degree", "Edu.Major", "Edu.Minor", "Edu.GPA", "Edu.GPAScale", "Edu.School", "Edu.Loc.CountryRegion", "Edu.Loc.State", "Edu.Loc.City")(VjobcandidateeducationViewRow.apply)(JobcandidateId.decoder, Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(TypoLocalDate.decoder), Decoder.decodeOption(TypoLocalDate.decoder), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString))
  implicit lazy val encoder: Encoder[VjobcandidateeducationViewRow] = Encoder.forProduct13[VjobcandidateeducationViewRow, JobcandidateId, /* nullability unknown */ Option[/* max 50 chars */ String], /* nullability unknown */ Option[TypoLocalDate], /* nullability unknown */ Option[TypoLocalDate], /* nullability unknown */ Option[/* max 50 chars */ String], /* nullability unknown */ Option[/* max 50 chars */ String], /* nullability unknown */ Option[/* max 50 chars */ String], /* nullability unknown */ Option[/* max 5 chars */ String], /* nullability unknown */ Option[/* max 5 chars */ String], /* nullability unknown */ Option[/* max 100 chars */ String], /* nullability unknown */ Option[/* max 100 chars */ String], /* nullability unknown */ Option[/* max 100 chars */ String], /* nullability unknown */ Option[/* max 100 chars */ String]]("jobcandidateid", "Edu.Level", "Edu.StartDate", "Edu.EndDate", "Edu.Degree", "Edu.Major", "Edu.Minor", "Edu.GPA", "Edu.GPAScale", "Edu.School", "Edu.Loc.CountryRegion", "Edu.Loc.State", "Edu.Loc.City")(x => (x.jobcandidateid, x.eduLevel, x.eduStartDate, x.eduEndDate, x.eduDegree, x.eduMajor, x.eduMinor, x.eduGPA, x.eduGPAScale, x.eduSchool, x.eduLocCountryRegion, x.eduLocState, x.eduLocCity))(JobcandidateId.encoder, Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(TypoLocalDate.encoder), Encoder.encodeOption(TypoLocalDate.encoder), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString))
  implicit lazy val read: Read[VjobcandidateeducationViewRow] = new Read.CompositeOfInstances(Array(
    new Read.Single(JobcandidateId.get).asInstanceOf[Read[Any]],
      new Read.SingleOpt(Meta.StringMeta.get).asInstanceOf[Read[Any]],
      new Read.SingleOpt(TypoLocalDate.get).asInstanceOf[Read[Any]],
      new Read.SingleOpt(TypoLocalDate.get).asInstanceOf[Read[Any]],
      new Read.SingleOpt(Meta.StringMeta.get).asInstanceOf[Read[Any]],
      new Read.SingleOpt(Meta.StringMeta.get).asInstanceOf[Read[Any]],
      new Read.SingleOpt(Meta.StringMeta.get).asInstanceOf[Read[Any]],
      new Read.SingleOpt(Meta.StringMeta.get).asInstanceOf[Read[Any]],
      new Read.SingleOpt(Meta.StringMeta.get).asInstanceOf[Read[Any]],
      new Read.SingleOpt(Meta.StringMeta.get).asInstanceOf[Read[Any]],
      new Read.SingleOpt(Meta.StringMeta.get).asInstanceOf[Read[Any]],
      new Read.SingleOpt(Meta.StringMeta.get).asInstanceOf[Read[Any]],
      new Read.SingleOpt(Meta.StringMeta.get).asInstanceOf[Read[Any]]
  ))(using scala.reflect.ClassTag.Any).map { arr =>
    VjobcandidateeducationViewRow(
      jobcandidateid = arr(0).asInstanceOf[JobcandidateId],
          eduLevel = arr(1).asInstanceOf[/* nullability unknown */ Option[/* max 50 chars */ String]],
          eduStartDate = arr(2).asInstanceOf[/* nullability unknown */ Option[TypoLocalDate]],
          eduEndDate = arr(3).asInstanceOf[/* nullability unknown */ Option[TypoLocalDate]],
          eduDegree = arr(4).asInstanceOf[/* nullability unknown */ Option[/* max 50 chars */ String]],
          eduMajor = arr(5).asInstanceOf[/* nullability unknown */ Option[/* max 50 chars */ String]],
          eduMinor = arr(6).asInstanceOf[/* nullability unknown */ Option[/* max 50 chars */ String]],
          eduGPA = arr(7).asInstanceOf[/* nullability unknown */ Option[/* max 5 chars */ String]],
          eduGPAScale = arr(8).asInstanceOf[/* nullability unknown */ Option[/* max 5 chars */ String]],
          eduSchool = arr(9).asInstanceOf[/* nullability unknown */ Option[/* max 100 chars */ String]],
          eduLocCountryRegion = arr(10).asInstanceOf[/* nullability unknown */ Option[/* max 100 chars */ String]],
          eduLocState = arr(11).asInstanceOf[/* nullability unknown */ Option[/* max 100 chars */ String]],
          eduLocCity = arr(12).asInstanceOf[/* nullability unknown */ Option[/* max 100 chars */ String]]
    )
  }
}

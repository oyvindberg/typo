/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package vpersondemographics

import adventureworks.customtypes.TypoLocalDate
import adventureworks.customtypes.TypoMoney
import adventureworks.person.businessentity.BusinessentityId
import doobie.enumerated.Nullability
import doobie.util.Read
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.Encoder
import java.sql.ResultSet

/** View: sales.vpersondemographics */
case class VpersondemographicsViewRow(
  /** Points to [[person.person.PersonRow.businessentityid]] */
  businessentityid: BusinessentityId,
  totalpurchaseytd: /* nullability unknown */ Option[TypoMoney],
  datefirstpurchase: /* nullability unknown */ Option[TypoLocalDate],
  birthdate: /* nullability unknown */ Option[TypoLocalDate],
  maritalstatus: /* nullability unknown */ Option[/* max 1 chars */ String],
  yearlyincome: /* nullability unknown */ Option[/* max 30 chars */ String],
  gender: /* nullability unknown */ Option[/* max 1 chars */ String],
  totalchildren: /* nullability unknown */ Option[Int],
  numberchildrenathome: /* nullability unknown */ Option[Int],
  education: /* nullability unknown */ Option[/* max 30 chars */ String],
  occupation: /* nullability unknown */ Option[/* max 30 chars */ String],
  homeownerflag: /* nullability unknown */ Option[Boolean],
  numbercarsowned: /* nullability unknown */ Option[Int]
)

object VpersondemographicsViewRow {
  implicit lazy val decoder: Decoder[VpersondemographicsViewRow] = Decoder.forProduct13[VpersondemographicsViewRow, BusinessentityId, /* nullability unknown */ Option[TypoMoney], /* nullability unknown */ Option[TypoLocalDate], /* nullability unknown */ Option[TypoLocalDate], /* nullability unknown */ Option[/* max 1 chars */ String], /* nullability unknown */ Option[/* max 30 chars */ String], /* nullability unknown */ Option[/* max 1 chars */ String], /* nullability unknown */ Option[Int], /* nullability unknown */ Option[Int], /* nullability unknown */ Option[/* max 30 chars */ String], /* nullability unknown */ Option[/* max 30 chars */ String], /* nullability unknown */ Option[Boolean], /* nullability unknown */ Option[Int]]("businessentityid", "totalpurchaseytd", "datefirstpurchase", "birthdate", "maritalstatus", "yearlyincome", "gender", "totalchildren", "numberchildrenathome", "education", "occupation", "homeownerflag", "numbercarsowned")(VpersondemographicsViewRow.apply)(BusinessentityId.decoder, Decoder.decodeOption(TypoMoney.decoder), Decoder.decodeOption(TypoLocalDate.decoder), Decoder.decodeOption(TypoLocalDate.decoder), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeInt), Decoder.decodeOption(Decoder.decodeInt), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeBoolean), Decoder.decodeOption(Decoder.decodeInt))
  implicit lazy val encoder: Encoder[VpersondemographicsViewRow] = Encoder.forProduct13[VpersondemographicsViewRow, BusinessentityId, /* nullability unknown */ Option[TypoMoney], /* nullability unknown */ Option[TypoLocalDate], /* nullability unknown */ Option[TypoLocalDate], /* nullability unknown */ Option[/* max 1 chars */ String], /* nullability unknown */ Option[/* max 30 chars */ String], /* nullability unknown */ Option[/* max 1 chars */ String], /* nullability unknown */ Option[Int], /* nullability unknown */ Option[Int], /* nullability unknown */ Option[/* max 30 chars */ String], /* nullability unknown */ Option[/* max 30 chars */ String], /* nullability unknown */ Option[Boolean], /* nullability unknown */ Option[Int]]("businessentityid", "totalpurchaseytd", "datefirstpurchase", "birthdate", "maritalstatus", "yearlyincome", "gender", "totalchildren", "numberchildrenathome", "education", "occupation", "homeownerflag", "numbercarsowned")(x => (x.businessentityid, x.totalpurchaseytd, x.datefirstpurchase, x.birthdate, x.maritalstatus, x.yearlyincome, x.gender, x.totalchildren, x.numberchildrenathome, x.education, x.occupation, x.homeownerflag, x.numbercarsowned))(BusinessentityId.encoder, Encoder.encodeOption(TypoMoney.encoder), Encoder.encodeOption(TypoLocalDate.encoder), Encoder.encodeOption(TypoLocalDate.encoder), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeInt), Encoder.encodeOption(Encoder.encodeInt), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeBoolean), Encoder.encodeOption(Encoder.encodeInt))
  implicit lazy val read: Read[VpersondemographicsViewRow] = new Read[VpersondemographicsViewRow](
    gets = List(
      (BusinessentityId.get, Nullability.NoNulls),
      (TypoMoney.get, Nullability.Nullable),
      (TypoLocalDate.get, Nullability.Nullable),
      (TypoLocalDate.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.IntMeta.get, Nullability.Nullable),
      (Meta.IntMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.BooleanMeta.get, Nullability.Nullable),
      (Meta.IntMeta.get, Nullability.Nullable)
    ),
    unsafeGet = (rs: ResultSet, i: Int) => VpersondemographicsViewRow(
      businessentityid = BusinessentityId.get.unsafeGetNonNullable(rs, i + 0),
      totalpurchaseytd = TypoMoney.get.unsafeGetNullable(rs, i + 1),
      datefirstpurchase = TypoLocalDate.get.unsafeGetNullable(rs, i + 2),
      birthdate = TypoLocalDate.get.unsafeGetNullable(rs, i + 3),
      maritalstatus = Meta.StringMeta.get.unsafeGetNullable(rs, i + 4),
      yearlyincome = Meta.StringMeta.get.unsafeGetNullable(rs, i + 5),
      gender = Meta.StringMeta.get.unsafeGetNullable(rs, i + 6),
      totalchildren = Meta.IntMeta.get.unsafeGetNullable(rs, i + 7),
      numberchildrenathome = Meta.IntMeta.get.unsafeGetNullable(rs, i + 8),
      education = Meta.StringMeta.get.unsafeGetNullable(rs, i + 9),
      occupation = Meta.StringMeta.get.unsafeGetNullable(rs, i + 10),
      homeownerflag = Meta.BooleanMeta.get.unsafeGetNullable(rs, i + 11),
      numbercarsowned = Meta.IntMeta.get.unsafeGetNullable(rs, i + 12)
    )
  )
}

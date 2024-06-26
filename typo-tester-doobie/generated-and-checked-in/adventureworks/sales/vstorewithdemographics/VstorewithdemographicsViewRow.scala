/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package vstorewithdemographics

import adventureworks.customtypes.TypoMoney
import adventureworks.person.businessentity.BusinessentityId
import adventureworks.public.Name
import doobie.enumerated.Nullability
import doobie.util.Read
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.Encoder
import java.sql.ResultSet

/** View: sales.vstorewithdemographics */
case class VstorewithdemographicsViewRow(
  /** Points to [[store.StoreRow.businessentityid]] */
  businessentityid: BusinessentityId,
  /** Points to [[store.StoreRow.name]] */
  name: Name,
  annualSales: /* nullability unknown */ Option[TypoMoney],
  annualRevenue: /* nullability unknown */ Option[TypoMoney],
  bankName: /* nullability unknown */ Option[/* max 50 chars */ String],
  businessType: /* nullability unknown */ Option[/* max 5 chars */ String],
  yearOpened: /* nullability unknown */ Option[Int],
  specialty: /* nullability unknown */ Option[/* max 50 chars */ String],
  squareFeet: /* nullability unknown */ Option[Int],
  brands: /* nullability unknown */ Option[/* max 30 chars */ String],
  internet: /* nullability unknown */ Option[/* max 30 chars */ String],
  numberEmployees: /* nullability unknown */ Option[Int]
)

object VstorewithdemographicsViewRow {
  implicit lazy val decoder: Decoder[VstorewithdemographicsViewRow] = Decoder.forProduct12[VstorewithdemographicsViewRow, BusinessentityId, Name, /* nullability unknown */ Option[TypoMoney], /* nullability unknown */ Option[TypoMoney], /* nullability unknown */ Option[/* max 50 chars */ String], /* nullability unknown */ Option[/* max 5 chars */ String], /* nullability unknown */ Option[Int], /* nullability unknown */ Option[/* max 50 chars */ String], /* nullability unknown */ Option[Int], /* nullability unknown */ Option[/* max 30 chars */ String], /* nullability unknown */ Option[/* max 30 chars */ String], /* nullability unknown */ Option[Int]]("businessentityid", "name", "AnnualSales", "AnnualRevenue", "BankName", "BusinessType", "YearOpened", "Specialty", "SquareFeet", "Brands", "Internet", "NumberEmployees")(VstorewithdemographicsViewRow.apply)(BusinessentityId.decoder, Name.decoder, Decoder.decodeOption(TypoMoney.decoder), Decoder.decodeOption(TypoMoney.decoder), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeInt), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeInt), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeInt))
  implicit lazy val encoder: Encoder[VstorewithdemographicsViewRow] = Encoder.forProduct12[VstorewithdemographicsViewRow, BusinessentityId, Name, /* nullability unknown */ Option[TypoMoney], /* nullability unknown */ Option[TypoMoney], /* nullability unknown */ Option[/* max 50 chars */ String], /* nullability unknown */ Option[/* max 5 chars */ String], /* nullability unknown */ Option[Int], /* nullability unknown */ Option[/* max 50 chars */ String], /* nullability unknown */ Option[Int], /* nullability unknown */ Option[/* max 30 chars */ String], /* nullability unknown */ Option[/* max 30 chars */ String], /* nullability unknown */ Option[Int]]("businessentityid", "name", "AnnualSales", "AnnualRevenue", "BankName", "BusinessType", "YearOpened", "Specialty", "SquareFeet", "Brands", "Internet", "NumberEmployees")(x => (x.businessentityid, x.name, x.annualSales, x.annualRevenue, x.bankName, x.businessType, x.yearOpened, x.specialty, x.squareFeet, x.brands, x.internet, x.numberEmployees))(BusinessentityId.encoder, Name.encoder, Encoder.encodeOption(TypoMoney.encoder), Encoder.encodeOption(TypoMoney.encoder), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeInt), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeInt), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeInt))
  implicit lazy val read: Read[VstorewithdemographicsViewRow] = new Read[VstorewithdemographicsViewRow](
    gets = List(
      (BusinessentityId.get, Nullability.NoNulls),
      (Name.get, Nullability.NoNulls),
      (TypoMoney.get, Nullability.Nullable),
      (TypoMoney.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.IntMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.IntMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.IntMeta.get, Nullability.Nullable)
    ),
    unsafeGet = (rs: ResultSet, i: Int) => VstorewithdemographicsViewRow(
      businessentityid = BusinessentityId.get.unsafeGetNonNullable(rs, i + 0),
      name = Name.get.unsafeGetNonNullable(rs, i + 1),
      annualSales = TypoMoney.get.unsafeGetNullable(rs, i + 2),
      annualRevenue = TypoMoney.get.unsafeGetNullable(rs, i + 3),
      bankName = Meta.StringMeta.get.unsafeGetNullable(rs, i + 4),
      businessType = Meta.StringMeta.get.unsafeGetNullable(rs, i + 5),
      yearOpened = Meta.IntMeta.get.unsafeGetNullable(rs, i + 6),
      specialty = Meta.StringMeta.get.unsafeGetNullable(rs, i + 7),
      squareFeet = Meta.IntMeta.get.unsafeGetNullable(rs, i + 8),
      brands = Meta.StringMeta.get.unsafeGetNullable(rs, i + 9),
      internet = Meta.StringMeta.get.unsafeGetNullable(rs, i + 10),
      numberEmployees = Meta.IntMeta.get.unsafeGetNullable(rs, i + 11)
    )
  )
}

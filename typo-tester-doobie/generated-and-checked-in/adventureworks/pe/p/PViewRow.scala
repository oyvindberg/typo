/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pe
package p

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoUUID
import adventureworks.customtypes.TypoXml
import adventureworks.person.businessentity.BusinessentityId
import adventureworks.public.Name
import adventureworks.public.NameStyle
import adventureworks.userdefined.FirstName
import doobie.enumerated.Nullability
import doobie.util.Read
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.Encoder
import java.sql.ResultSet

case class PViewRow(
  /** Points to [[person.person.PersonRow.businessentityid]] */
  id: BusinessentityId,
  /** Points to [[person.person.PersonRow.businessentityid]] */
  businessentityid: BusinessentityId,
  /** Points to [[person.person.PersonRow.persontype]] */
  persontype: /* bpchar, max 2 chars */ String,
  /** Points to [[person.person.PersonRow.namestyle]] */
  namestyle: NameStyle,
  /** Points to [[person.person.PersonRow.title]] */
  title: Option[/* max 8 chars */ String],
  /** Points to [[person.person.PersonRow.firstname]] */
  firstname: /* user-picked */ FirstName,
  /** Points to [[person.person.PersonRow.middlename]] */
  middlename: Option[Name],
  /** Points to [[person.person.PersonRow.lastname]] */
  lastname: Name,
  /** Points to [[person.person.PersonRow.suffix]] */
  suffix: Option[/* max 10 chars */ String],
  /** Points to [[person.person.PersonRow.emailpromotion]] */
  emailpromotion: Int,
  /** Points to [[person.person.PersonRow.additionalcontactinfo]] */
  additionalcontactinfo: Option[TypoXml],
  /** Points to [[person.person.PersonRow.demographics]] */
  demographics: Option[TypoXml],
  /** Points to [[person.person.PersonRow.rowguid]] */
  rowguid: TypoUUID,
  /** Points to [[person.person.PersonRow.modifieddate]] */
  modifieddate: TypoLocalDateTime
)

object PViewRow {
  implicit lazy val decoder: Decoder[PViewRow] = Decoder.forProduct14[PViewRow, BusinessentityId, BusinessentityId, /* bpchar, max 2 chars */ String, NameStyle, Option[/* max 8 chars */ String], /* user-picked */ FirstName, Option[Name], Name, Option[/* max 10 chars */ String], Int, Option[TypoXml], Option[TypoXml], TypoUUID, TypoLocalDateTime]("id", "businessentityid", "persontype", "namestyle", "title", "firstname", "middlename", "lastname", "suffix", "emailpromotion", "additionalcontactinfo", "demographics", "rowguid", "modifieddate")(PViewRow.apply)(BusinessentityId.decoder, BusinessentityId.decoder, Decoder.decodeString, NameStyle.decoder, Decoder.decodeOption(Decoder.decodeString), FirstName.decoder, Decoder.decodeOption(Name.decoder), Name.decoder, Decoder.decodeOption(Decoder.decodeString), Decoder.decodeInt, Decoder.decodeOption(TypoXml.decoder), Decoder.decodeOption(TypoXml.decoder), TypoUUID.decoder, TypoLocalDateTime.decoder)
  implicit lazy val encoder: Encoder[PViewRow] = Encoder.forProduct14[PViewRow, BusinessentityId, BusinessentityId, /* bpchar, max 2 chars */ String, NameStyle, Option[/* max 8 chars */ String], /* user-picked */ FirstName, Option[Name], Name, Option[/* max 10 chars */ String], Int, Option[TypoXml], Option[TypoXml], TypoUUID, TypoLocalDateTime]("id", "businessentityid", "persontype", "namestyle", "title", "firstname", "middlename", "lastname", "suffix", "emailpromotion", "additionalcontactinfo", "demographics", "rowguid", "modifieddate")(x => (x.id, x.businessentityid, x.persontype, x.namestyle, x.title, x.firstname, x.middlename, x.lastname, x.suffix, x.emailpromotion, x.additionalcontactinfo, x.demographics, x.rowguid, x.modifieddate))(BusinessentityId.encoder, BusinessentityId.encoder, Encoder.encodeString, NameStyle.encoder, Encoder.encodeOption(Encoder.encodeString), FirstName.encoder, Encoder.encodeOption(Name.encoder), Name.encoder, Encoder.encodeOption(Encoder.encodeString), Encoder.encodeInt, Encoder.encodeOption(TypoXml.encoder), Encoder.encodeOption(TypoXml.encoder), TypoUUID.encoder, TypoLocalDateTime.encoder)
  implicit lazy val read: Read[PViewRow] = new Read[PViewRow](
    gets = List(
      (BusinessentityId.get, Nullability.NoNulls),
      (BusinessentityId.get, Nullability.NoNulls),
      (Meta.StringMeta.get, Nullability.NoNulls),
      (NameStyle.get, Nullability.NoNulls),
      (Meta.StringMeta.get, Nullability.Nullable),
      (/* user-picked */ FirstName.get, Nullability.NoNulls),
      (Name.get, Nullability.Nullable),
      (Name.get, Nullability.NoNulls),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.IntMeta.get, Nullability.NoNulls),
      (TypoXml.get, Nullability.Nullable),
      (TypoXml.get, Nullability.Nullable),
      (TypoUUID.get, Nullability.NoNulls),
      (TypoLocalDateTime.get, Nullability.NoNulls)
    ),
    unsafeGet = (rs: ResultSet, i: Int) => PViewRow(
      id = BusinessentityId.get.unsafeGetNonNullable(rs, i + 0),
      businessentityid = BusinessentityId.get.unsafeGetNonNullable(rs, i + 1),
      persontype = Meta.StringMeta.get.unsafeGetNonNullable(rs, i + 2),
      namestyle = NameStyle.get.unsafeGetNonNullable(rs, i + 3),
      title = Meta.StringMeta.get.unsafeGetNullable(rs, i + 4),
      firstname = /* user-picked */ FirstName.get.unsafeGetNonNullable(rs, i + 5),
      middlename = Name.get.unsafeGetNullable(rs, i + 6),
      lastname = Name.get.unsafeGetNonNullable(rs, i + 7),
      suffix = Meta.StringMeta.get.unsafeGetNullable(rs, i + 8),
      emailpromotion = Meta.IntMeta.get.unsafeGetNonNullable(rs, i + 9),
      additionalcontactinfo = TypoXml.get.unsafeGetNullable(rs, i + 10),
      demographics = TypoXml.get.unsafeGetNullable(rs, i + 11),
      rowguid = TypoUUID.get.unsafeGetNonNullable(rs, i + 12),
      modifieddate = TypoLocalDateTime.get.unsafeGetNonNullable(rs, i + 13)
    )
  )
}
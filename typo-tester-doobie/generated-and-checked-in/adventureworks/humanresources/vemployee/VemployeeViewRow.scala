/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package humanresources
package vemployee

import adventureworks.customtypes.TypoXml
import adventureworks.person.businessentity.BusinessentityId
import adventureworks.public.Name
import adventureworks.public.Phone
import adventureworks.userdefined.FirstName
import doobie.enumerated.Nullability
import doobie.util.Read
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.Encoder
import java.sql.ResultSet

case class VemployeeViewRow(
  /** Points to [[employee.EmployeeRow.businessentityid]] */
  businessentityid: BusinessentityId,
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
  /** Points to [[employee.EmployeeRow.jobtitle]] */
  jobtitle: /* max 50 chars */ String,
  /** Points to [[person.personphone.PersonphoneRow.phonenumber]] */
  phonenumber: Option[Phone],
  /** Points to [[person.phonenumbertype.PhonenumbertypeRow.name]] */
  phonenumbertype: Option[Name],
  /** Points to [[person.emailaddress.EmailaddressRow.emailaddress]] */
  emailaddress: Option[/* max 50 chars */ String],
  /** Points to [[person.person.PersonRow.emailpromotion]] */
  emailpromotion: Int,
  /** Points to [[person.address.AddressRow.addressline1]] */
  addressline1: /* max 60 chars */ String,
  /** Points to [[person.address.AddressRow.addressline2]] */
  addressline2: Option[/* max 60 chars */ String],
  /** Points to [[person.address.AddressRow.city]] */
  city: /* max 30 chars */ String,
  /** Points to [[person.stateprovince.StateprovinceRow.name]] */
  stateprovincename: Name,
  /** Points to [[person.address.AddressRow.postalcode]] */
  postalcode: /* max 15 chars */ String,
  /** Points to [[person.countryregion.CountryregionRow.name]] */
  countryregionname: Name,
  /** Points to [[person.person.PersonRow.additionalcontactinfo]] */
  additionalcontactinfo: Option[TypoXml]
)

object VemployeeViewRow {
  implicit lazy val decoder: Decoder[VemployeeViewRow] = Decoder.forProduct18[VemployeeViewRow, BusinessentityId, Option[/* max 8 chars */ String], /* user-picked */ FirstName, Option[Name], Name, Option[/* max 10 chars */ String], /* max 50 chars */ String, Option[Phone], Option[Name], Option[/* max 50 chars */ String], Int, /* max 60 chars */ String, Option[/* max 60 chars */ String], /* max 30 chars */ String, Name, /* max 15 chars */ String, Name, Option[TypoXml]]("businessentityid", "title", "firstname", "middlename", "lastname", "suffix", "jobtitle", "phonenumber", "phonenumbertype", "emailaddress", "emailpromotion", "addressline1", "addressline2", "city", "stateprovincename", "postalcode", "countryregionname", "additionalcontactinfo")(VemployeeViewRow.apply)(BusinessentityId.decoder, Decoder.decodeOption(Decoder.decodeString), FirstName.decoder, Decoder.decodeOption(Name.decoder), Name.decoder, Decoder.decodeOption(Decoder.decodeString), Decoder.decodeString, Decoder.decodeOption(Phone.decoder), Decoder.decodeOption(Name.decoder), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeInt, Decoder.decodeString, Decoder.decodeOption(Decoder.decodeString), Decoder.decodeString, Name.decoder, Decoder.decodeString, Name.decoder, Decoder.decodeOption(TypoXml.decoder))
  implicit lazy val encoder: Encoder[VemployeeViewRow] = Encoder.forProduct18[VemployeeViewRow, BusinessentityId, Option[/* max 8 chars */ String], /* user-picked */ FirstName, Option[Name], Name, Option[/* max 10 chars */ String], /* max 50 chars */ String, Option[Phone], Option[Name], Option[/* max 50 chars */ String], Int, /* max 60 chars */ String, Option[/* max 60 chars */ String], /* max 30 chars */ String, Name, /* max 15 chars */ String, Name, Option[TypoXml]]("businessentityid", "title", "firstname", "middlename", "lastname", "suffix", "jobtitle", "phonenumber", "phonenumbertype", "emailaddress", "emailpromotion", "addressline1", "addressline2", "city", "stateprovincename", "postalcode", "countryregionname", "additionalcontactinfo")(x => (x.businessentityid, x.title, x.firstname, x.middlename, x.lastname, x.suffix, x.jobtitle, x.phonenumber, x.phonenumbertype, x.emailaddress, x.emailpromotion, x.addressline1, x.addressline2, x.city, x.stateprovincename, x.postalcode, x.countryregionname, x.additionalcontactinfo))(BusinessentityId.encoder, Encoder.encodeOption(Encoder.encodeString), FirstName.encoder, Encoder.encodeOption(Name.encoder), Name.encoder, Encoder.encodeOption(Encoder.encodeString), Encoder.encodeString, Encoder.encodeOption(Phone.encoder), Encoder.encodeOption(Name.encoder), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeInt, Encoder.encodeString, Encoder.encodeOption(Encoder.encodeString), Encoder.encodeString, Name.encoder, Encoder.encodeString, Name.encoder, Encoder.encodeOption(TypoXml.encoder))
  implicit lazy val read: Read[VemployeeViewRow] = new Read[VemployeeViewRow](
    gets = List(
      (BusinessentityId.get, Nullability.NoNulls),
      (Meta.StringMeta.get, Nullability.Nullable),
      (/* user-picked */ FirstName.get, Nullability.NoNulls),
      (Name.get, Nullability.Nullable),
      (Name.get, Nullability.NoNulls),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.NoNulls),
      (Phone.get, Nullability.Nullable),
      (Name.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.IntMeta.get, Nullability.NoNulls),
      (Meta.StringMeta.get, Nullability.NoNulls),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.NoNulls),
      (Name.get, Nullability.NoNulls),
      (Meta.StringMeta.get, Nullability.NoNulls),
      (Name.get, Nullability.NoNulls),
      (TypoXml.get, Nullability.Nullable)
    ),
    unsafeGet = (rs: ResultSet, i: Int) => VemployeeViewRow(
      businessentityid = BusinessentityId.get.unsafeGetNonNullable(rs, i + 0),
      title = Meta.StringMeta.get.unsafeGetNullable(rs, i + 1),
      firstname = /* user-picked */ FirstName.get.unsafeGetNonNullable(rs, i + 2),
      middlename = Name.get.unsafeGetNullable(rs, i + 3),
      lastname = Name.get.unsafeGetNonNullable(rs, i + 4),
      suffix = Meta.StringMeta.get.unsafeGetNullable(rs, i + 5),
      jobtitle = Meta.StringMeta.get.unsafeGetNonNullable(rs, i + 6),
      phonenumber = Phone.get.unsafeGetNullable(rs, i + 7),
      phonenumbertype = Name.get.unsafeGetNullable(rs, i + 8),
      emailaddress = Meta.StringMeta.get.unsafeGetNullable(rs, i + 9),
      emailpromotion = Meta.IntMeta.get.unsafeGetNonNullable(rs, i + 10),
      addressline1 = Meta.StringMeta.get.unsafeGetNonNullable(rs, i + 11),
      addressline2 = Meta.StringMeta.get.unsafeGetNullable(rs, i + 12),
      city = Meta.StringMeta.get.unsafeGetNonNullable(rs, i + 13),
      stateprovincename = Name.get.unsafeGetNonNullable(rs, i + 14),
      postalcode = Meta.StringMeta.get.unsafeGetNonNullable(rs, i + 15),
      countryregionname = Name.get.unsafeGetNonNullable(rs, i + 16),
      additionalcontactinfo = TypoXml.get.unsafeGetNullable(rs, i + 17)
    )
  )
}
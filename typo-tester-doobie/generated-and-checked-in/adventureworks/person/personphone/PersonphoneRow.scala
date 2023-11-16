/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package personphone

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.person.businessentity.BusinessentityId
import adventureworks.person.phonenumbertype.PhonenumbertypeId
import adventureworks.public.Phone
import doobie.enumerated.Nullability
import doobie.postgres.Text
import doobie.util.Read
import io.circe.Decoder
import io.circe.Encoder
import java.sql.ResultSet

case class PersonphoneRow(
  /** Business entity identification number. Foreign key to Person.BusinessEntityID.
      Points to [[person.PersonRow.businessentityid]] */
  businessentityid: BusinessentityId,
  /** Telephone number identification number. */
  phonenumber: Phone,
  /** Kind of phone number. Foreign key to PhoneNumberType.PhoneNumberTypeID.
      Points to [[phonenumbertype.PhonenumbertypeRow.phonenumbertypeid]] */
  phonenumbertypeid: PhonenumbertypeId,
  modifieddate: TypoLocalDateTime
){
   val compositeId: PersonphoneId = PersonphoneId(businessentityid, phonenumber, phonenumbertypeid)
 }

object PersonphoneRow {
  implicit lazy val decoder: Decoder[PersonphoneRow] = Decoder.forProduct4[PersonphoneRow, BusinessentityId, Phone, PhonenumbertypeId, TypoLocalDateTime]("businessentityid", "phonenumber", "phonenumbertypeid", "modifieddate")(PersonphoneRow.apply)(BusinessentityId.decoder, Phone.decoder, PhonenumbertypeId.decoder, TypoLocalDateTime.decoder)
  implicit lazy val encoder: Encoder[PersonphoneRow] = Encoder.forProduct4[PersonphoneRow, BusinessentityId, Phone, PhonenumbertypeId, TypoLocalDateTime]("businessentityid", "phonenumber", "phonenumbertypeid", "modifieddate")(x => (x.businessentityid, x.phonenumber, x.phonenumbertypeid, x.modifieddate))(BusinessentityId.encoder, Phone.encoder, PhonenumbertypeId.encoder, TypoLocalDateTime.encoder)
  implicit lazy val read: Read[PersonphoneRow] = new Read[PersonphoneRow](
    gets = List(
      (BusinessentityId.get, Nullability.NoNulls),
      (Phone.get, Nullability.NoNulls),
      (PhonenumbertypeId.get, Nullability.NoNulls),
      (TypoLocalDateTime.get, Nullability.NoNulls)
    ),
    unsafeGet = (rs: ResultSet, i: Int) => PersonphoneRow(
      businessentityid = BusinessentityId.get.unsafeGetNonNullable(rs, i + 0),
      phonenumber = Phone.get.unsafeGetNonNullable(rs, i + 1),
      phonenumbertypeid = PhonenumbertypeId.get.unsafeGetNonNullable(rs, i + 2),
      modifieddate = TypoLocalDateTime.get.unsafeGetNonNullable(rs, i + 3)
    )
  )
  implicit lazy val text: Text[PersonphoneRow] = Text.instance[PersonphoneRow]{ (row, sb) =>
    BusinessentityId.text.unsafeEncode(row.businessentityid, sb)
    sb.append(Text.DELIMETER)
    Phone.text.unsafeEncode(row.phonenumber, sb)
    sb.append(Text.DELIMETER)
    PhonenumbertypeId.text.unsafeEncode(row.phonenumbertypeid, sb)
    sb.append(Text.DELIMETER)
    TypoLocalDateTime.text.unsafeEncode(row.modifieddate, sb)
  }
}
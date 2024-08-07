/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package phonenumbertype

import adventureworks.customtypes.Defaulted
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.public.Name
import doobie.enumerated.Nullability
import doobie.postgres.Text
import doobie.util.Read
import doobie.util.Write
import io.circe.Decoder
import io.circe.Encoder
import java.sql.ResultSet

/** Table: person.phonenumbertype
    Type of phone number of a person.
    Primary key: phonenumbertypeid */
case class PhonenumbertypeRow(
  /** Primary key for telephone number type records.
      Default: nextval('person.phonenumbertype_phonenumbertypeid_seq'::regclass) */
  phonenumbertypeid: PhonenumbertypeId,
  /** Name of the telephone number type */
  name: Name,
  /** Default: now() */
  modifieddate: TypoLocalDateTime
){
   val id = phonenumbertypeid
   def toUnsavedRow(phonenumbertypeid: Defaulted[PhonenumbertypeId], modifieddate: Defaulted[TypoLocalDateTime] = Defaulted.Provided(this.modifieddate)): PhonenumbertypeRowUnsaved =
     PhonenumbertypeRowUnsaved(name, phonenumbertypeid, modifieddate)
 }

object PhonenumbertypeRow {
  implicit lazy val decoder: Decoder[PhonenumbertypeRow] = Decoder.forProduct3[PhonenumbertypeRow, PhonenumbertypeId, Name, TypoLocalDateTime]("phonenumbertypeid", "name", "modifieddate")(PhonenumbertypeRow.apply)(PhonenumbertypeId.decoder, Name.decoder, TypoLocalDateTime.decoder)
  implicit lazy val encoder: Encoder[PhonenumbertypeRow] = Encoder.forProduct3[PhonenumbertypeRow, PhonenumbertypeId, Name, TypoLocalDateTime]("phonenumbertypeid", "name", "modifieddate")(x => (x.phonenumbertypeid, x.name, x.modifieddate))(PhonenumbertypeId.encoder, Name.encoder, TypoLocalDateTime.encoder)
  implicit lazy val read: Read[PhonenumbertypeRow] = new Read[PhonenumbertypeRow](
    gets = List(
      (PhonenumbertypeId.get, Nullability.NoNulls),
      (Name.get, Nullability.NoNulls),
      (TypoLocalDateTime.get, Nullability.NoNulls)
    ),
    unsafeGet = (rs: ResultSet, i: Int) => PhonenumbertypeRow(
      phonenumbertypeid = PhonenumbertypeId.get.unsafeGetNonNullable(rs, i + 0),
      name = Name.get.unsafeGetNonNullable(rs, i + 1),
      modifieddate = TypoLocalDateTime.get.unsafeGetNonNullable(rs, i + 2)
    )
  )
  implicit lazy val text: Text[PhonenumbertypeRow] = Text.instance[PhonenumbertypeRow]{ (row, sb) =>
    PhonenumbertypeId.text.unsafeEncode(row.phonenumbertypeid, sb)
    sb.append(Text.DELIMETER)
    Name.text.unsafeEncode(row.name, sb)
    sb.append(Text.DELIMETER)
    TypoLocalDateTime.text.unsafeEncode(row.modifieddate, sb)
  }
  implicit lazy val write: Write[PhonenumbertypeRow] = new Write[PhonenumbertypeRow](
    puts = List((PhonenumbertypeId.put, Nullability.NoNulls),
                (Name.put, Nullability.NoNulls),
                (TypoLocalDateTime.put, Nullability.NoNulls)),
    toList = x => List(x.phonenumbertypeid, x.name, x.modifieddate),
    unsafeSet = (rs, i, a) => {
                  PhonenumbertypeId.put.unsafeSetNonNullable(rs, i + 0, a.phonenumbertypeid)
                  Name.put.unsafeSetNonNullable(rs, i + 1, a.name)
                  TypoLocalDateTime.put.unsafeSetNonNullable(rs, i + 2, a.modifieddate)
                },
    unsafeUpdate = (ps, i, a) => {
                     PhonenumbertypeId.put.unsafeUpdateNonNullable(ps, i + 0, a.phonenumbertypeid)
                     Name.put.unsafeUpdateNonNullable(ps, i + 1, a.name)
                     TypoLocalDateTime.put.unsafeUpdateNonNullable(ps, i + 2, a.modifieddate)
                   }
  )
}

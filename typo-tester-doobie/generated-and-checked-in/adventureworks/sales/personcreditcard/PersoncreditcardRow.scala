/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package personcreditcard

import adventureworks.customtypes.Defaulted
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.person.businessentity.BusinessentityId
import adventureworks.userdefined.CustomCreditcardId
import doobie.enumerated.Nullability
import doobie.postgres.Text
import doobie.util.Read
import doobie.util.Write
import io.circe.Decoder
import io.circe.Encoder
import java.sql.ResultSet

/** Table: sales.personcreditcard
    Cross-reference table mapping people to their credit card information in the CreditCard table.
    Composite primary key: businessentityid, creditcardid */
case class PersoncreditcardRow(
  /** Business entity identification number. Foreign key to Person.BusinessEntityID.
      Points to [[person.person.PersonRow.businessentityid]] */
  businessentityid: BusinessentityId,
  /** Credit card identification number. Foreign key to CreditCard.CreditCardID.
      Points to [[creditcard.CreditcardRow.creditcardid]] */
  creditcardid: /* user-picked */ CustomCreditcardId,
  /** Default: now() */
  modifieddate: TypoLocalDateTime
){
   val compositeId: PersoncreditcardId = PersoncreditcardId(businessentityid, creditcardid)
   val id = compositeId
   def toUnsavedRow(modifieddate: Defaulted[TypoLocalDateTime] = Defaulted.Provided(this.modifieddate)): PersoncreditcardRowUnsaved =
     PersoncreditcardRowUnsaved(businessentityid, creditcardid, modifieddate)
 }

object PersoncreditcardRow {
  def apply(compositeId: PersoncreditcardId, modifieddate: TypoLocalDateTime) =
    new PersoncreditcardRow(compositeId.businessentityid, compositeId.creditcardid, modifieddate)
  implicit lazy val decoder: Decoder[PersoncreditcardRow] = Decoder.forProduct3[PersoncreditcardRow, BusinessentityId, /* user-picked */ CustomCreditcardId, TypoLocalDateTime]("businessentityid", "creditcardid", "modifieddate")(PersoncreditcardRow.apply)(BusinessentityId.decoder, CustomCreditcardId.decoder, TypoLocalDateTime.decoder)
  implicit lazy val encoder: Encoder[PersoncreditcardRow] = Encoder.forProduct3[PersoncreditcardRow, BusinessentityId, /* user-picked */ CustomCreditcardId, TypoLocalDateTime]("businessentityid", "creditcardid", "modifieddate")(x => (x.businessentityid, x.creditcardid, x.modifieddate))(BusinessentityId.encoder, CustomCreditcardId.encoder, TypoLocalDateTime.encoder)
  implicit lazy val read: Read[PersoncreditcardRow] = new Read[PersoncreditcardRow](
    gets = List(
      (BusinessentityId.get, Nullability.NoNulls),
      (/* user-picked */ CustomCreditcardId.get, Nullability.NoNulls),
      (TypoLocalDateTime.get, Nullability.NoNulls)
    ),
    unsafeGet = (rs: ResultSet, i: Int) => PersoncreditcardRow(
      businessentityid = BusinessentityId.get.unsafeGetNonNullable(rs, i + 0),
      creditcardid = /* user-picked */ CustomCreditcardId.get.unsafeGetNonNullable(rs, i + 1),
      modifieddate = TypoLocalDateTime.get.unsafeGetNonNullable(rs, i + 2)
    )
  )
  implicit lazy val text: Text[PersoncreditcardRow] = Text.instance[PersoncreditcardRow]{ (row, sb) =>
    BusinessentityId.text.unsafeEncode(row.businessentityid, sb)
    sb.append(Text.DELIMETER)
    /* user-picked */ CustomCreditcardId.text.unsafeEncode(row.creditcardid, sb)
    sb.append(Text.DELIMETER)
    TypoLocalDateTime.text.unsafeEncode(row.modifieddate, sb)
  }
  implicit lazy val write: Write[PersoncreditcardRow] = new Write[PersoncreditcardRow](
    puts = List((BusinessentityId.put, Nullability.NoNulls),
                (/* user-picked */ CustomCreditcardId.put, Nullability.NoNulls),
                (TypoLocalDateTime.put, Nullability.NoNulls)),
    toList = x => List(x.businessentityid, x.creditcardid, x.modifieddate),
    unsafeSet = (rs, i, a) => {
                  BusinessentityId.put.unsafeSetNonNullable(rs, i + 0, a.businessentityid)
                  /* user-picked */ CustomCreditcardId.put.unsafeSetNonNullable(rs, i + 1, a.creditcardid)
                  TypoLocalDateTime.put.unsafeSetNonNullable(rs, i + 2, a.modifieddate)
                },
    unsafeUpdate = (ps, i, a) => {
                     BusinessentityId.put.unsafeUpdateNonNullable(ps, i + 0, a.businessentityid)
                     /* user-picked */ CustomCreditcardId.put.unsafeUpdateNonNullable(ps, i + 1, a.creditcardid)
                     TypoLocalDateTime.put.unsafeUpdateNonNullable(ps, i + 2, a.modifieddate)
                   }
  )
}

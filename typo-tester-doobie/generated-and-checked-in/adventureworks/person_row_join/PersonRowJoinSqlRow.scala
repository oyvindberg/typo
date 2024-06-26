/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person_row_join

import adventureworks.customtypes.TypoRecord
import adventureworks.person.businessentity.BusinessentityId
import doobie.enumerated.Nullability
import doobie.util.Read
import io.circe.Decoder
import io.circe.Encoder
import java.sql.ResultSet

/** SQL file: person_row_join.sql */
case class PersonRowJoinSqlRow(
  /** Points to [[sales.salesperson.SalespersonRow.businessentityid]] */
  businessentityid: BusinessentityId,
  email: /* nullability unknown */ Option[Array[TypoRecord]],
  emails: /* nullability unknown */ Option[Array[TypoRecord]]
)

object PersonRowJoinSqlRow {
  implicit lazy val decoder: Decoder[PersonRowJoinSqlRow] = Decoder.forProduct3[PersonRowJoinSqlRow, BusinessentityId, /* nullability unknown */ Option[Array[TypoRecord]], /* nullability unknown */ Option[Array[TypoRecord]]]("businessentityid", "email", "emails")(PersonRowJoinSqlRow.apply)(BusinessentityId.decoder, Decoder.decodeOption(Decoder.decodeArray[TypoRecord](TypoRecord.decoder, implicitly)), Decoder.decodeOption(Decoder.decodeArray[TypoRecord](TypoRecord.decoder, implicitly)))
  implicit lazy val encoder: Encoder[PersonRowJoinSqlRow] = Encoder.forProduct3[PersonRowJoinSqlRow, BusinessentityId, /* nullability unknown */ Option[Array[TypoRecord]], /* nullability unknown */ Option[Array[TypoRecord]]]("businessentityid", "email", "emails")(x => (x.businessentityid, x.email, x.emails))(BusinessentityId.encoder, Encoder.encodeOption(Encoder.encodeIterable[TypoRecord, Array](TypoRecord.encoder, implicitly)), Encoder.encodeOption(Encoder.encodeIterable[TypoRecord, Array](TypoRecord.encoder, implicitly)))
  implicit lazy val read: Read[PersonRowJoinSqlRow] = new Read[PersonRowJoinSqlRow](
    gets = List(
      (BusinessentityId.get, Nullability.NoNulls),
      (TypoRecord.arrayGet, Nullability.Nullable),
      (TypoRecord.arrayGet, Nullability.Nullable)
    ),
    unsafeGet = (rs: ResultSet, i: Int) => PersonRowJoinSqlRow(
      businessentityid = BusinessentityId.get.unsafeGetNonNullable(rs, i + 0),
      email = TypoRecord.arrayGet.unsafeGetNullable(rs, i + 1),
      emails = TypoRecord.arrayGet.unsafeGetNullable(rs, i + 2)
    )
  )
}

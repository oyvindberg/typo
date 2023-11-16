/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package businessentity

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoUUID
import doobie.enumerated.Nullability
import doobie.postgres.Text
import doobie.util.Read
import io.circe.Decoder
import io.circe.Encoder
import java.sql.ResultSet

case class BusinessentityRow(
  /** Primary key for all customers, vendors, and employees. */
  businessentityid: BusinessentityId,
  rowguid: TypoUUID,
  modifieddate: TypoLocalDateTime
)

object BusinessentityRow {
  implicit lazy val decoder: Decoder[BusinessentityRow] = Decoder.forProduct3[BusinessentityRow, BusinessentityId, TypoUUID, TypoLocalDateTime]("businessentityid", "rowguid", "modifieddate")(BusinessentityRow.apply)(BusinessentityId.decoder, TypoUUID.decoder, TypoLocalDateTime.decoder)
  implicit lazy val encoder: Encoder[BusinessentityRow] = Encoder.forProduct3[BusinessentityRow, BusinessentityId, TypoUUID, TypoLocalDateTime]("businessentityid", "rowguid", "modifieddate")(x => (x.businessentityid, x.rowguid, x.modifieddate))(BusinessentityId.encoder, TypoUUID.encoder, TypoLocalDateTime.encoder)
  implicit lazy val read: Read[BusinessentityRow] = new Read[BusinessentityRow](
    gets = List(
      (BusinessentityId.get, Nullability.NoNulls),
      (TypoUUID.get, Nullability.NoNulls),
      (TypoLocalDateTime.get, Nullability.NoNulls)
    ),
    unsafeGet = (rs: ResultSet, i: Int) => BusinessentityRow(
      businessentityid = BusinessentityId.get.unsafeGetNonNullable(rs, i + 0),
      rowguid = TypoUUID.get.unsafeGetNonNullable(rs, i + 1),
      modifieddate = TypoLocalDateTime.get.unsafeGetNonNullable(rs, i + 2)
    )
  )
  implicit lazy val text: Text[BusinessentityRow] = Text.instance[BusinessentityRow]{ (row, sb) =>
    BusinessentityId.text.unsafeEncode(row.businessentityid, sb)
    sb.append(Text.DELIMETER)
    TypoUUID.text.unsafeEncode(row.rowguid, sb)
    sb.append(Text.DELIMETER)
    TypoLocalDateTime.text.unsafeEncode(row.modifieddate, sb)
  }
}
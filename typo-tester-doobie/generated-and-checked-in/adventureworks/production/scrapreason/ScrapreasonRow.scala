/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package scrapreason

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

/** Table: production.scrapreason
    Manufacturing failure reasons lookup table.
    Primary key: scrapreasonid */
case class ScrapreasonRow(
  /** Primary key for ScrapReason records.
      Default: nextval('production.scrapreason_scrapreasonid_seq'::regclass) */
  scrapreasonid: ScrapreasonId,
  /** Failure description. */
  name: Name,
  /** Default: now() */
  modifieddate: TypoLocalDateTime
){
   val id = scrapreasonid
   def toUnsavedRow(scrapreasonid: Defaulted[ScrapreasonId], modifieddate: Defaulted[TypoLocalDateTime] = Defaulted.Provided(this.modifieddate)): ScrapreasonRowUnsaved =
     ScrapreasonRowUnsaved(name, scrapreasonid, modifieddate)
 }

object ScrapreasonRow {
  implicit lazy val decoder: Decoder[ScrapreasonRow] = Decoder.forProduct3[ScrapreasonRow, ScrapreasonId, Name, TypoLocalDateTime]("scrapreasonid", "name", "modifieddate")(ScrapreasonRow.apply)(ScrapreasonId.decoder, Name.decoder, TypoLocalDateTime.decoder)
  implicit lazy val encoder: Encoder[ScrapreasonRow] = Encoder.forProduct3[ScrapreasonRow, ScrapreasonId, Name, TypoLocalDateTime]("scrapreasonid", "name", "modifieddate")(x => (x.scrapreasonid, x.name, x.modifieddate))(ScrapreasonId.encoder, Name.encoder, TypoLocalDateTime.encoder)
  implicit lazy val read: Read[ScrapreasonRow] = new Read[ScrapreasonRow](
    gets = List(
      (ScrapreasonId.get, Nullability.NoNulls),
      (Name.get, Nullability.NoNulls),
      (TypoLocalDateTime.get, Nullability.NoNulls)
    ),
    unsafeGet = (rs: ResultSet, i: Int) => ScrapreasonRow(
      scrapreasonid = ScrapreasonId.get.unsafeGetNonNullable(rs, i + 0),
      name = Name.get.unsafeGetNonNullable(rs, i + 1),
      modifieddate = TypoLocalDateTime.get.unsafeGetNonNullable(rs, i + 2)
    )
  )
  implicit lazy val text: Text[ScrapreasonRow] = Text.instance[ScrapreasonRow]{ (row, sb) =>
    ScrapreasonId.text.unsafeEncode(row.scrapreasonid, sb)
    sb.append(Text.DELIMETER)
    Name.text.unsafeEncode(row.name, sb)
    sb.append(Text.DELIMETER)
    TypoLocalDateTime.text.unsafeEncode(row.modifieddate, sb)
  }
  implicit lazy val write: Write[ScrapreasonRow] = new Write[ScrapreasonRow](
    puts = List((ScrapreasonId.put, Nullability.NoNulls),
                (Name.put, Nullability.NoNulls),
                (TypoLocalDateTime.put, Nullability.NoNulls)),
    toList = x => List(x.scrapreasonid, x.name, x.modifieddate),
    unsafeSet = (rs, i, a) => {
                  ScrapreasonId.put.unsafeSetNonNullable(rs, i + 0, a.scrapreasonid)
                  Name.put.unsafeSetNonNullable(rs, i + 1, a.name)
                  TypoLocalDateTime.put.unsafeSetNonNullable(rs, i + 2, a.modifieddate)
                },
    unsafeUpdate = (ps, i, a) => {
                     ScrapreasonId.put.unsafeUpdateNonNullable(ps, i + 0, a.scrapreasonid)
                     Name.put.unsafeUpdateNonNullable(ps, i + 1, a.name)
                     TypoLocalDateTime.put.unsafeUpdateNonNullable(ps, i + 2, a.modifieddate)
                   }
  )
}

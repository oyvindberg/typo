/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package public
package issue142

import doobie.enumerated.Nullability
import doobie.postgres.Text
import doobie.util.Read
import doobie.util.Write
import io.circe.Decoder
import io.circe.Encoder
import java.sql.ResultSet

/** Table: public.issue142
    Primary key: tabellkode */
case class Issue142Row(
  tabellkode: Issue142Id
){
   val id = tabellkode
 }

object Issue142Row {
  implicit lazy val decoder: Decoder[Issue142Row] = Decoder.forProduct1[Issue142Row, Issue142Id]("tabellkode")(Issue142Row.apply)(Issue142Id.decoder)
  implicit lazy val encoder: Encoder[Issue142Row] = Encoder.forProduct1[Issue142Row, Issue142Id]("tabellkode")(x => (x.tabellkode))(Issue142Id.encoder)
  implicit lazy val read: Read[Issue142Row] = new Read[Issue142Row](
    gets = List(
      (Issue142Id.get, Nullability.NoNulls)
    ),
    unsafeGet = (rs: ResultSet, i: Int) => Issue142Row(
      tabellkode = Issue142Id.get.unsafeGetNonNullable(rs, i + 0)
    )
  )
  implicit lazy val text: Text[Issue142Row] = Text.instance[Issue142Row]{ (row, sb) =>
    Issue142Id.text.unsafeEncode(row.tabellkode, sb)
  }
  implicit lazy val write: Write[Issue142Row] = new Write[Issue142Row](
    puts = List((Issue142Id.put, Nullability.NoNulls)),
    toList = x => List(x.tabellkode),
    unsafeSet = (rs, i, a) => {
                  Issue142Id.put.unsafeSetNonNullable(rs, i + 0, a.tabellkode)
                },
    unsafeUpdate = (ps, i, a) => {
                     Issue142Id.put.unsafeUpdateNonNullable(ps, i + 0, a.tabellkode)
                   }
  )
}

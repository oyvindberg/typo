/**
 * File automatically generated by `typo` for its own test suite.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN
 */
package testdb
package hardcoded
package compositepk
package person

import doobie.enumerated.Nullability
import doobie.postgres.Text
import doobie.util.Read
import doobie.util.Write
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.Encoder
import java.sql.ResultSet
import testdb.hardcoded.customtypes.Defaulted

/** Table: compositepk.person
    Composite primary key: one, two */
case class PersonRow(
  /** Default: auto-increment */
  one: Long,
  /** Default: auto-increment */
  two: Option[String],
  name: Option[String]
){
   val compositeId: PersonId = PersonId(one, two)
   val id = compositeId
   def toUnsavedRow(one: Defaulted[Long], two: Defaulted[Option[String]]): PersonRowUnsaved =
     PersonRowUnsaved(name, one, two)
 }

object PersonRow {
  def apply(compositeId: PersonId, name: Option[String]) =
    new PersonRow(compositeId.one, compositeId.two, name)
  implicit lazy val decoder: Decoder[PersonRow] = Decoder.forProduct3[PersonRow, Long, Option[String], Option[String]]("one", "two", "name")(PersonRow.apply)(Decoder.decodeLong, Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString))
  implicit lazy val encoder: Encoder[PersonRow] = Encoder.forProduct3[PersonRow, Long, Option[String], Option[String]]("one", "two", "name")(x => (x.one, x.two, x.name))(Encoder.encodeLong, Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString))
  implicit lazy val read: Read[PersonRow] = new Read[PersonRow](
    gets = List(
      (Meta.LongMeta.get, Nullability.NoNulls),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable)
    ),
    unsafeGet = (rs: ResultSet, i: Int) => PersonRow(
      one = Meta.LongMeta.get.unsafeGetNonNullable(rs, i + 0),
      two = Meta.StringMeta.get.unsafeGetNullable(rs, i + 1),
      name = Meta.StringMeta.get.unsafeGetNullable(rs, i + 2)
    )
  )
  implicit lazy val text: Text[PersonRow] = Text.instance[PersonRow]{ (row, sb) =>
    Text.longInstance.unsafeEncode(row.one, sb)
    sb.append(Text.DELIMETER)
    Text.option(Text.stringInstance).unsafeEncode(row.two, sb)
    sb.append(Text.DELIMETER)
    Text.option(Text.stringInstance).unsafeEncode(row.name, sb)
  }
  implicit lazy val write: Write[PersonRow] = new Write[PersonRow](
    puts = List((Meta.LongMeta.put, Nullability.NoNulls),
                (Meta.StringMeta.put, Nullability.Nullable),
                (Meta.StringMeta.put, Nullability.Nullable)),
    toList = x => List(x.one, x.two, x.name),
    unsafeSet = (rs, i, a) => {
                  Meta.LongMeta.put.unsafeSetNonNullable(rs, i + 0, a.one)
                  Meta.StringMeta.put.unsafeSetNullable(rs, i + 1, a.two)
                  Meta.StringMeta.put.unsafeSetNullable(rs, i + 2, a.name)
                },
    unsafeUpdate = (ps, i, a) => {
                     Meta.LongMeta.put.unsafeUpdateNonNullable(ps, i + 0, a.one)
                     Meta.StringMeta.put.unsafeUpdateNullable(ps, i + 1, a.two)
                     Meta.StringMeta.put.unsafeUpdateNullable(ps, i + 2, a.name)
                   }
  )
}

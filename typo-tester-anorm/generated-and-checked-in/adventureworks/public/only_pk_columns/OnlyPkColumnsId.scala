/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package public
package only_pk_columns

import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.OWrites
import play.api.libs.json.Reads
import play.api.libs.json.Writes
import scala.collection.immutable.ListMap
import scala.util.Try

/** Type for the composite primary key of table `public.only_pk_columns` */
case class OnlyPkColumnsId(
  keyColumn1: String,
  keyColumn2: Int
)
object OnlyPkColumnsId {
  implicit lazy val ordering: Ordering[OnlyPkColumnsId] = Ordering.by(x => (x.keyColumn1, x.keyColumn2))
  implicit lazy val reads: Reads[OnlyPkColumnsId] = Reads[OnlyPkColumnsId](json => JsResult.fromTry(
      Try(
        OnlyPkColumnsId(
          keyColumn1 = json.\("key_column_1").as(Reads.StringReads),
          keyColumn2 = json.\("key_column_2").as(Reads.IntReads)
        )
      )
    ),
  )
  implicit lazy val writes: OWrites[OnlyPkColumnsId] = OWrites[OnlyPkColumnsId](o =>
    new JsObject(ListMap[String, JsValue](
      "key_column_1" -> Writes.StringWrites.writes(o.keyColumn1),
      "key_column_2" -> Writes.IntWrites.writes(o.keyColumn2)
    ))
  )
}

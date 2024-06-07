/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package scrapreason

import doobie.postgres.Text
import doobie.util.Get
import doobie.util.Put
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.Encoder
import typo.dsl.Bijection

/** Type for the primary key of table `production.scrapreason` */
case class ScrapreasonId(value: Int) extends AnyVal
object ScrapreasonId {
  implicit lazy val arrayGet: Get[Array[ScrapreasonId]] = adventureworks.IntegerArrayMeta.get.map(_.map(ScrapreasonId.apply))
  implicit lazy val arrayPut: Put[Array[ScrapreasonId]] = adventureworks.IntegerArrayMeta.put.contramap(_.map(_.value))
  implicit lazy val bijection: Bijection[ScrapreasonId, Int] = Bijection[ScrapreasonId, Int](_.value)(ScrapreasonId.apply)
  implicit lazy val decoder: Decoder[ScrapreasonId] = Decoder.decodeInt.map(ScrapreasonId.apply)
  implicit lazy val encoder: Encoder[ScrapreasonId] = Encoder.encodeInt.contramap(_.value)
  implicit lazy val get: Get[ScrapreasonId] = Meta.IntMeta.get.map(ScrapreasonId.apply)
  implicit lazy val ordering: Ordering[ScrapreasonId] = Ordering.by(_.value)
  implicit lazy val put: Put[ScrapreasonId] = Meta.IntMeta.put.contramap(_.value)
  implicit lazy val text: Text[ScrapreasonId] = new Text[ScrapreasonId] {
    override def unsafeEncode(v: ScrapreasonId, sb: StringBuilder) = Text.intInstance.unsafeEncode(v.value, sb)
    override def unsafeArrayEncode(v: ScrapreasonId, sb: StringBuilder) = Text.intInstance.unsafeArrayEncode(v.value, sb)
  }
}
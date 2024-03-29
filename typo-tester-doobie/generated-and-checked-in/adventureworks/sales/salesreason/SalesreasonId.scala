/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package salesreason

import doobie.postgres.Text
import doobie.util.Get
import doobie.util.Put
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.Encoder
import typo.dsl.Bijection

/** Type for the primary key of table `sales.salesreason` */
case class SalesreasonId(value: Int) extends AnyVal
object SalesreasonId {
  implicit lazy val arrayGet: Get[Array[SalesreasonId]] = adventureworks.IntegerArrayMeta.get.map(_.map(SalesreasonId.apply))
  implicit lazy val arrayPut: Put[Array[SalesreasonId]] = adventureworks.IntegerArrayMeta.put.contramap(_.map(_.value))
  implicit lazy val bijection: Bijection[SalesreasonId, Int] = Bijection[SalesreasonId, Int](_.value)(SalesreasonId.apply)
  implicit lazy val decoder: Decoder[SalesreasonId] = Decoder.decodeInt.map(SalesreasonId.apply)
  implicit lazy val encoder: Encoder[SalesreasonId] = Encoder.encodeInt.contramap(_.value)
  implicit lazy val get: Get[SalesreasonId] = Meta.IntMeta.get.map(SalesreasonId.apply)
  implicit lazy val ordering: Ordering[SalesreasonId] = Ordering.by(_.value)
  implicit lazy val put: Put[SalesreasonId] = Meta.IntMeta.put.contramap(_.value)
  implicit lazy val text: Text[SalesreasonId] = new Text[SalesreasonId] {
    override def unsafeEncode(v: SalesreasonId, sb: StringBuilder) = Text.intInstance.unsafeEncode(v.value, sb)
    override def unsafeArrayEncode(v: SalesreasonId, sb: StringBuilder) = Text.intInstance.unsafeArrayEncode(v.value, sb)
  }
}

/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package productmodel

import doobie.util.Get
import doobie.util.Put
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.Encoder
import typo.dsl.Bijection

/** Type for the primary key of table `production.productmodel` */
case class ProductmodelId(value: Int) extends AnyVal
object ProductmodelId {
  implicit lazy val arrayGet: Get[Array[ProductmodelId]] = adventureworks.IntegerArrayMeta.get.map(_.map(ProductmodelId.apply))
  implicit lazy val arrayPut: Put[Array[ProductmodelId]] = adventureworks.IntegerArrayMeta.put.contramap(_.map(_.value))
  implicit lazy val bijection: Bijection[ProductmodelId, Int] = Bijection[ProductmodelId, Int](_.value)(ProductmodelId.apply)
  implicit lazy val decoder: Decoder[ProductmodelId] = Decoder.decodeInt.map(ProductmodelId.apply)
  implicit lazy val encoder: Encoder[ProductmodelId] = Encoder.encodeInt.contramap(_.value)
  implicit lazy val get: Get[ProductmodelId] = Meta.IntMeta.get.map(ProductmodelId.apply)
  implicit lazy val ordering: Ordering[ProductmodelId] = Ordering.by(_.value)
  implicit lazy val put: Put[ProductmodelId] = Meta.IntMeta.put.contramap(_.value)
}
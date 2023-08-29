/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_foreign_server

import doobie.util.Get
import doobie.util.Put
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.Encoder
import typo.dsl.Bijection

/** Type for the primary key of table `pg_catalog.pg_foreign_server` */
case class PgForeignServerId(value: /* oid */ Long) extends AnyVal
object PgForeignServerId {
  implicit lazy val arrayGet: Get[Array[PgForeignServerId]] = adventureworks.LongArrayMeta.get.map(_.map(PgForeignServerId.apply))
  implicit lazy val arrayPut: Put[Array[PgForeignServerId]] = adventureworks.LongArrayMeta.put.contramap(_.map(_.value))
  implicit lazy val bijection: Bijection[PgForeignServerId, /* oid */ Long] = Bijection[PgForeignServerId, /* oid */ Long](_.value)(PgForeignServerId.apply)
  implicit lazy val decoder: Decoder[PgForeignServerId] = Decoder.decodeLong.map(PgForeignServerId.apply)
  implicit lazy val encoder: Encoder[PgForeignServerId] = Encoder.encodeLong.contramap(_.value)
  implicit lazy val get: Get[PgForeignServerId] = Meta.LongMeta.get.map(PgForeignServerId.apply)
  implicit lazy val ordering: Ordering[PgForeignServerId] = Ordering.by(_.value)
  implicit lazy val put: Put[PgForeignServerId] = Meta.LongMeta.put.contramap(_.value)
}
/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_foreign_table

import doobie.util.Get
import doobie.util.Put
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.Encoder
import typo.dsl.Bijection

/** Type for the primary key of table `pg_catalog.pg_foreign_table` */
case class PgForeignTableId(value: /* oid */ Long) extends AnyVal
object PgForeignTableId {
  implicit lazy val arrayGet: Get[Array[PgForeignTableId]] = adventureworks.LongArrayMeta.get.map(_.map(PgForeignTableId.apply))
  implicit lazy val arrayPut: Put[Array[PgForeignTableId]] = adventureworks.LongArrayMeta.put.contramap(_.map(_.value))
  implicit lazy val bijection: Bijection[PgForeignTableId, /* oid */ Long] = Bijection[PgForeignTableId, /* oid */ Long](_.value)(PgForeignTableId.apply)
  implicit lazy val decoder: Decoder[PgForeignTableId] = Decoder.decodeLong.map(PgForeignTableId.apply)
  implicit lazy val encoder: Encoder[PgForeignTableId] = Encoder.encodeLong.contramap(_.value)
  implicit lazy val get: Get[PgForeignTableId] = Meta.LongMeta.get.map(PgForeignTableId.apply)
  implicit lazy val ordering: Ordering[PgForeignTableId] = Ordering.by(_.value)
  implicit lazy val put: Put[PgForeignTableId] = Meta.LongMeta.put.contramap(_.value)
}
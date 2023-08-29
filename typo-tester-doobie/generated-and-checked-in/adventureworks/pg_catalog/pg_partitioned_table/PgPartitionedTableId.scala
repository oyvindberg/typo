/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_partitioned_table

import doobie.util.Get
import doobie.util.Put
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.Encoder
import typo.dsl.Bijection

/** Type for the primary key of table `pg_catalog.pg_partitioned_table` */
case class PgPartitionedTableId(value: /* oid */ Long) extends AnyVal
object PgPartitionedTableId {
  implicit lazy val arrayGet: Get[Array[PgPartitionedTableId]] = adventureworks.LongArrayMeta.get.map(_.map(PgPartitionedTableId.apply))
  implicit lazy val arrayPut: Put[Array[PgPartitionedTableId]] = adventureworks.LongArrayMeta.put.contramap(_.map(_.value))
  implicit lazy val bijection: Bijection[PgPartitionedTableId, /* oid */ Long] = Bijection[PgPartitionedTableId, /* oid */ Long](_.value)(PgPartitionedTableId.apply)
  implicit lazy val decoder: Decoder[PgPartitionedTableId] = Decoder.decodeLong.map(PgPartitionedTableId.apply)
  implicit lazy val encoder: Encoder[PgPartitionedTableId] = Encoder.encodeLong.contramap(_.value)
  implicit lazy val get: Get[PgPartitionedTableId] = Meta.LongMeta.get.map(PgPartitionedTableId.apply)
  implicit lazy val ordering: Ordering[PgPartitionedTableId] = Ordering.by(_.value)
  implicit lazy val put: Put[PgPartitionedTableId] = Meta.LongMeta.put.contramap(_.value)
}
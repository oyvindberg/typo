/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_index

import doobie.Meta
import io.circe.Decoder
import io.circe.Encoder

/** Type for the primary key of table `pg_catalog.pg_index` */
case class PgIndexId(value: /* oid */ Long) extends AnyVal
object PgIndexId {
  implicit val ordering: Ordering[PgIndexId] = Ordering.by(_.value)
  implicit val encoder: Encoder[PgIndexId] =
    Encoder[/* oid */ Long].contramap(_.value)
  implicit val decoder: Decoder[PgIndexId] =
    Decoder[/* oid */ Long].map(PgIndexId(_))
  implicit val meta: Meta[PgIndexId] = Meta[/* oid */ Long].imap(PgIndexId.apply)(_.value)
  implicit val metaArray: Meta[Array[PgIndexId]] = Meta[Array[/* oid */ Long]].imap(_.map(PgIndexId.apply))(_.map(_.value))
}
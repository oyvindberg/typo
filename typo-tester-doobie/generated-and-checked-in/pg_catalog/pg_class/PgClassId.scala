/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_class

import doobie.Meta
import io.circe.Decoder
import io.circe.Encoder

/** Type for the primary key of table `pg_catalog.pg_class` */
case class PgClassId(value: /* oid */ Long) extends AnyVal
object PgClassId {
  implicit val ordering: Ordering[PgClassId] = Ordering.by(_.value)
  implicit val encoder: Encoder[PgClassId] =
    Encoder[/* oid */ Long].contramap(_.value)
  implicit val decoder: Decoder[PgClassId] =
    Decoder[/* oid */ Long].map(PgClassId(_))
  implicit val meta: Meta[PgClassId] = Meta[/* oid */ Long].imap(PgClassId.apply)(_.value)
  implicit val metaArray: Meta[Array[PgClassId]] = Meta[Array[/* oid */ Long]].imap(_.map(PgClassId.apply))(_.map(_.value))
}
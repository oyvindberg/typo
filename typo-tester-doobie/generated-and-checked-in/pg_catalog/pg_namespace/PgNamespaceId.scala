/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_namespace

import doobie.Meta
import io.circe.Decoder
import io.circe.Encoder

/** Type for the primary key of table `pg_catalog.pg_namespace` */
case class PgNamespaceId(value: /* oid */ Long) extends AnyVal
object PgNamespaceId {
  implicit val ordering: Ordering[PgNamespaceId] = Ordering.by(_.value)
  implicit val encoder: Encoder[PgNamespaceId] =
    Encoder[/* oid */ Long].contramap(_.value)
  implicit val decoder: Decoder[PgNamespaceId] =
    Decoder[/* oid */ Long].map(PgNamespaceId(_))
  implicit val meta: Meta[PgNamespaceId] = Meta[/* oid */ Long].imap(PgNamespaceId.apply)(_.value)
  implicit val metaArray: Meta[Array[PgNamespaceId]] = Meta[Array[/* oid */ Long]].imap(_.map(PgNamespaceId.apply))(_.map(_.value))
}
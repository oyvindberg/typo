/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_policy

import doobie.Meta
import io.circe.Decoder
import io.circe.Encoder

/** Type for the primary key of table `pg_catalog.pg_policy` */
case class PgPolicyId(value: /* oid */ Long) extends AnyVal
object PgPolicyId {
  implicit val ordering: Ordering[PgPolicyId] = Ordering.by(_.value)
  implicit val encoder: Encoder[PgPolicyId] =
    Encoder[/* oid */ Long].contramap(_.value)
  implicit val decoder: Decoder[PgPolicyId] =
    Decoder[/* oid */ Long].map(PgPolicyId(_))
  implicit val meta: Meta[PgPolicyId] = Meta[/* oid */ Long].imap(PgPolicyId.apply)(_.value)
  implicit val metaArray: Meta[Array[PgPolicyId]] = Meta[Array[/* oid */ Long]].imap(_.map(PgPolicyId.apply))(_.map(_.value))
}
/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_statistic_ext

import doobie.Meta
import io.circe.Decoder
import io.circe.Encoder

/** Type for the primary key of table `pg_catalog.pg_statistic_ext` */
case class PgStatisticExtId(value: /* oid */ Long) extends AnyVal
object PgStatisticExtId {
  implicit val ordering: Ordering[PgStatisticExtId] = Ordering.by(_.value)
  implicit val encoder: Encoder[PgStatisticExtId] =
    Encoder[/* oid */ Long].contramap(_.value)
  implicit val decoder: Decoder[PgStatisticExtId] =
    Decoder[/* oid */ Long].map(PgStatisticExtId(_))
  implicit val meta: Meta[PgStatisticExtId] = Meta[/* oid */ Long].imap(PgStatisticExtId.apply)(_.value)
  implicit val metaArray: Meta[Array[PgStatisticExtId]] = Meta[Array[/* oid */ Long]].imap(_.map(PgStatisticExtId.apply))(_.map(_.value))
}
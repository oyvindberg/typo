/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package information_schema
package domain_udt_usage

import adventureworks.information_schema.SqlIdentifier
import doobie.enumerated.Nullability
import doobie.util.Read
import io.circe.Decoder
import io.circe.Encoder
import java.sql.ResultSet

case class DomainUdtUsageViewRow(
  udtCatalog: Option[SqlIdentifier],
  udtSchema: Option[SqlIdentifier],
  udtName: Option[SqlIdentifier],
  domainCatalog: Option[SqlIdentifier],
  domainSchema: Option[SqlIdentifier],
  domainName: Option[SqlIdentifier]
)

object DomainUdtUsageViewRow {
  implicit lazy val decoder: Decoder[DomainUdtUsageViewRow] = Decoder.forProduct6[DomainUdtUsageViewRow, Option[SqlIdentifier], Option[SqlIdentifier], Option[SqlIdentifier], Option[SqlIdentifier], Option[SqlIdentifier], Option[SqlIdentifier]]("udt_catalog", "udt_schema", "udt_name", "domain_catalog", "domain_schema", "domain_name")(DomainUdtUsageViewRow.apply)(Decoder.decodeOption(SqlIdentifier.decoder), Decoder.decodeOption(SqlIdentifier.decoder), Decoder.decodeOption(SqlIdentifier.decoder), Decoder.decodeOption(SqlIdentifier.decoder), Decoder.decodeOption(SqlIdentifier.decoder), Decoder.decodeOption(SqlIdentifier.decoder))
  implicit lazy val encoder: Encoder[DomainUdtUsageViewRow] = Encoder.forProduct6[DomainUdtUsageViewRow, Option[SqlIdentifier], Option[SqlIdentifier], Option[SqlIdentifier], Option[SqlIdentifier], Option[SqlIdentifier], Option[SqlIdentifier]]("udt_catalog", "udt_schema", "udt_name", "domain_catalog", "domain_schema", "domain_name")(x => (x.udtCatalog, x.udtSchema, x.udtName, x.domainCatalog, x.domainSchema, x.domainName))(Encoder.encodeOption(SqlIdentifier.encoder), Encoder.encodeOption(SqlIdentifier.encoder), Encoder.encodeOption(SqlIdentifier.encoder), Encoder.encodeOption(SqlIdentifier.encoder), Encoder.encodeOption(SqlIdentifier.encoder), Encoder.encodeOption(SqlIdentifier.encoder))
  implicit lazy val read: Read[DomainUdtUsageViewRow] = new Read[DomainUdtUsageViewRow](
    gets = List(
      (SqlIdentifier.get, Nullability.Nullable),
      (SqlIdentifier.get, Nullability.Nullable),
      (SqlIdentifier.get, Nullability.Nullable),
      (SqlIdentifier.get, Nullability.Nullable),
      (SqlIdentifier.get, Nullability.Nullable),
      (SqlIdentifier.get, Nullability.Nullable)
    ),
    unsafeGet = (rs: ResultSet, i: Int) => DomainUdtUsageViewRow(
      udtCatalog = SqlIdentifier.get.unsafeGetNullable(rs, i + 0),
      udtSchema = SqlIdentifier.get.unsafeGetNullable(rs, i + 1),
      udtName = SqlIdentifier.get.unsafeGetNullable(rs, i + 2),
      domainCatalog = SqlIdentifier.get.unsafeGetNullable(rs, i + 3),
      domainSchema = SqlIdentifier.get.unsafeGetNullable(rs, i + 4),
      domainName = SqlIdentifier.get.unsafeGetNullable(rs, i + 5)
    )
  )
}
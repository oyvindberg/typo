/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_rules

import doobie.enumerated.Nullability
import doobie.util.Read
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.Encoder
import java.sql.ResultSet

case class PgRulesViewRow(
  /** Points to [[pg_namespace.PgNamespaceRow.nspname]] */
  schemaname: Option[String],
  /** Points to [[pg_class.PgClassRow.relname]] */
  tablename: String,
  /** Points to [[pg_rewrite.PgRewriteRow.rulename]] */
  rulename: String,
  definition: /* nullability unknown */ Option[String]
)

object PgRulesViewRow {
  implicit lazy val decoder: Decoder[PgRulesViewRow] = Decoder.forProduct4[PgRulesViewRow, Option[String], String, String, /* nullability unknown */ Option[String]]("schemaname", "tablename", "rulename", "definition")(PgRulesViewRow.apply)(Decoder.decodeOption(Decoder.decodeString), Decoder.decodeString, Decoder.decodeString, Decoder.decodeOption(Decoder.decodeString))
  implicit lazy val encoder: Encoder[PgRulesViewRow] = Encoder.forProduct4[PgRulesViewRow, Option[String], String, String, /* nullability unknown */ Option[String]]("schemaname", "tablename", "rulename", "definition")(x => (x.schemaname, x.tablename, x.rulename, x.definition))(Encoder.encodeOption(Encoder.encodeString), Encoder.encodeString, Encoder.encodeString, Encoder.encodeOption(Encoder.encodeString))
  implicit lazy val read: Read[PgRulesViewRow] = new Read[PgRulesViewRow](
    gets = List(
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.NoNulls),
      (Meta.StringMeta.get, Nullability.NoNulls),
      (Meta.StringMeta.get, Nullability.Nullable)
    ),
    unsafeGet = (rs: ResultSet, i: Int) => PgRulesViewRow(
      schemaname = Meta.StringMeta.get.unsafeGetNullable(rs, i + 0),
      tablename = Meta.StringMeta.get.unsafeGetNonNullable(rs, i + 1),
      rulename = Meta.StringMeta.get.unsafeGetNonNullable(rs, i + 2),
      definition = Meta.StringMeta.get.unsafeGetNullable(rs, i + 3)
    )
  )
}
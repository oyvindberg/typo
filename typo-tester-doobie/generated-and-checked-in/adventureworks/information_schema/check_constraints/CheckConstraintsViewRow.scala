/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package information_schema
package check_constraints

import doobie.enumerated.Nullability
import doobie.util.Read
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.Encoder
import java.sql.ResultSet

case class CheckConstraintsViewRow(
  constraintCatalog: /* nullability unknown */ Option[String],
  constraintSchema: /* nullability unknown */ Option[String],
  constraintName: /* nullability unknown */ Option[String],
  checkClause: /* nullability unknown */ Option[String]
)

object CheckConstraintsViewRow {
  implicit lazy val decoder: Decoder[CheckConstraintsViewRow] = Decoder.forProduct4[CheckConstraintsViewRow, /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String]]("constraint_catalog", "constraint_schema", "constraint_name", "check_clause")(CheckConstraintsViewRow.apply)(Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString))
  implicit lazy val encoder: Encoder[CheckConstraintsViewRow] = Encoder.forProduct4[CheckConstraintsViewRow, /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String]]("constraint_catalog", "constraint_schema", "constraint_name", "check_clause")(x => (x.constraintCatalog, x.constraintSchema, x.constraintName, x.checkClause))(Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString))
  implicit lazy val read: Read[CheckConstraintsViewRow] = new Read[CheckConstraintsViewRow](
    gets = List(
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable)
    ),
    unsafeGet = (rs: ResultSet, i: Int) => CheckConstraintsViewRow(
      constraintCatalog = Meta.StringMeta.get.unsafeGetNullable(rs, i + 0),
      constraintSchema = Meta.StringMeta.get.unsafeGetNullable(rs, i + 1),
      constraintName = Meta.StringMeta.get.unsafeGetNullable(rs, i + 2),
      checkClause = Meta.StringMeta.get.unsafeGetNullable(rs, i + 3)
    )
  )
}
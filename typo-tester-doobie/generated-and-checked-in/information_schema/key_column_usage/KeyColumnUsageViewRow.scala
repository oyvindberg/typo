/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package information_schema
package key_column_usage

import adventureworks.information_schema.CardinalNumber
import adventureworks.information_schema.SqlIdentifier
import doobie.enumerated.Nullability
import doobie.util.Read
import io.circe.Decoder
import io.circe.Encoder
import java.sql.ResultSet

case class KeyColumnUsageViewRow(
  constraintCatalog: Option[SqlIdentifier],
  constraintSchema: Option[SqlIdentifier],
  constraintName: Option[SqlIdentifier],
  tableCatalog: Option[SqlIdentifier],
  tableSchema: Option[SqlIdentifier],
  tableName: Option[SqlIdentifier],
  columnName: Option[SqlIdentifier],
  ordinalPosition: Option[CardinalNumber],
  positionInUniqueConstraint: Option[CardinalNumber]
)

object KeyColumnUsageViewRow {
  implicit lazy val decoder: Decoder[KeyColumnUsageViewRow] = Decoder.forProduct9[KeyColumnUsageViewRow, Option[SqlIdentifier], Option[SqlIdentifier], Option[SqlIdentifier], Option[SqlIdentifier], Option[SqlIdentifier], Option[SqlIdentifier], Option[SqlIdentifier], Option[CardinalNumber], Option[CardinalNumber]]("constraint_catalog", "constraint_schema", "constraint_name", "table_catalog", "table_schema", "table_name", "column_name", "ordinal_position", "position_in_unique_constraint")(KeyColumnUsageViewRow.apply)(Decoder.decodeOption(SqlIdentifier.decoder), Decoder.decodeOption(SqlIdentifier.decoder), Decoder.decodeOption(SqlIdentifier.decoder), Decoder.decodeOption(SqlIdentifier.decoder), Decoder.decodeOption(SqlIdentifier.decoder), Decoder.decodeOption(SqlIdentifier.decoder), Decoder.decodeOption(SqlIdentifier.decoder), Decoder.decodeOption(CardinalNumber.decoder), Decoder.decodeOption(CardinalNumber.decoder))
  implicit lazy val encoder: Encoder[KeyColumnUsageViewRow] = Encoder.forProduct9[KeyColumnUsageViewRow, Option[SqlIdentifier], Option[SqlIdentifier], Option[SqlIdentifier], Option[SqlIdentifier], Option[SqlIdentifier], Option[SqlIdentifier], Option[SqlIdentifier], Option[CardinalNumber], Option[CardinalNumber]]("constraint_catalog", "constraint_schema", "constraint_name", "table_catalog", "table_schema", "table_name", "column_name", "ordinal_position", "position_in_unique_constraint")(x => (x.constraintCatalog, x.constraintSchema, x.constraintName, x.tableCatalog, x.tableSchema, x.tableName, x.columnName, x.ordinalPosition, x.positionInUniqueConstraint))(Encoder.encodeOption(SqlIdentifier.encoder), Encoder.encodeOption(SqlIdentifier.encoder), Encoder.encodeOption(SqlIdentifier.encoder), Encoder.encodeOption(SqlIdentifier.encoder), Encoder.encodeOption(SqlIdentifier.encoder), Encoder.encodeOption(SqlIdentifier.encoder), Encoder.encodeOption(SqlIdentifier.encoder), Encoder.encodeOption(CardinalNumber.encoder), Encoder.encodeOption(CardinalNumber.encoder))
  implicit lazy val read: Read[KeyColumnUsageViewRow] = new Read[KeyColumnUsageViewRow](
    gets = List(
      (SqlIdentifier.get, Nullability.Nullable),
      (SqlIdentifier.get, Nullability.Nullable),
      (SqlIdentifier.get, Nullability.Nullable),
      (SqlIdentifier.get, Nullability.Nullable),
      (SqlIdentifier.get, Nullability.Nullable),
      (SqlIdentifier.get, Nullability.Nullable),
      (SqlIdentifier.get, Nullability.Nullable),
      (CardinalNumber.get, Nullability.Nullable),
      (CardinalNumber.get, Nullability.Nullable)
    ),
    unsafeGet = (rs: ResultSet, i: Int) => KeyColumnUsageViewRow(
      constraintCatalog = SqlIdentifier.get.unsafeGetNullable(rs, i + 0),
      constraintSchema = SqlIdentifier.get.unsafeGetNullable(rs, i + 1),
      constraintName = SqlIdentifier.get.unsafeGetNullable(rs, i + 2),
      tableCatalog = SqlIdentifier.get.unsafeGetNullable(rs, i + 3),
      tableSchema = SqlIdentifier.get.unsafeGetNullable(rs, i + 4),
      tableName = SqlIdentifier.get.unsafeGetNullable(rs, i + 5),
      columnName = SqlIdentifier.get.unsafeGetNullable(rs, i + 6),
      ordinalPosition = CardinalNumber.get.unsafeGetNullable(rs, i + 7),
      positionInUniqueConstraint = CardinalNumber.get.unsafeGetNullable(rs, i + 8)
    )
  )
}
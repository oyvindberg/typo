/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package information_schema
package tables

import doobie.enumerated.Nullability
import doobie.util.Read
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.Encoder
import java.sql.ResultSet

case class TablesViewRow(
  tableCatalog: /* nullability unknown */ Option[String],
  tableSchema: /* nullability unknown */ Option[String],
  tableName: /* nullability unknown */ Option[String],
  tableType: /* nullability unknown */ Option[String],
  selfReferencingColumnName: /* nullability unknown */ Option[String],
  referenceGeneration: /* nullability unknown */ Option[String],
  userDefinedTypeCatalog: /* nullability unknown */ Option[String],
  userDefinedTypeSchema: /* nullability unknown */ Option[String],
  userDefinedTypeName: /* nullability unknown */ Option[String],
  isInsertableInto: /* nullability unknown */ Option[/* max 3 chars */ String],
  isTyped: /* nullability unknown */ Option[/* max 3 chars */ String],
  commitAction: /* nullability unknown */ Option[String]
)

object TablesViewRow {
  implicit lazy val decoder: Decoder[TablesViewRow] = Decoder.forProduct12[TablesViewRow, /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[/* max 3 chars */ String], /* nullability unknown */ Option[/* max 3 chars */ String], /* nullability unknown */ Option[String]]("table_catalog", "table_schema", "table_name", "table_type", "self_referencing_column_name", "reference_generation", "user_defined_type_catalog", "user_defined_type_schema", "user_defined_type_name", "is_insertable_into", "is_typed", "commit_action")(TablesViewRow.apply)(Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString))
  implicit lazy val encoder: Encoder[TablesViewRow] = Encoder.forProduct12[TablesViewRow, /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[/* max 3 chars */ String], /* nullability unknown */ Option[/* max 3 chars */ String], /* nullability unknown */ Option[String]]("table_catalog", "table_schema", "table_name", "table_type", "self_referencing_column_name", "reference_generation", "user_defined_type_catalog", "user_defined_type_schema", "user_defined_type_name", "is_insertable_into", "is_typed", "commit_action")(x => (x.tableCatalog, x.tableSchema, x.tableName, x.tableType, x.selfReferencingColumnName, x.referenceGeneration, x.userDefinedTypeCatalog, x.userDefinedTypeSchema, x.userDefinedTypeName, x.isInsertableInto, x.isTyped, x.commitAction))(Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString))
  implicit lazy val read: Read[TablesViewRow] = new Read[TablesViewRow](
    gets = List(
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable)
    ),
    unsafeGet = (rs: ResultSet, i: Int) => TablesViewRow(
      tableCatalog = Meta.StringMeta.get.unsafeGetNullable(rs, i + 0),
      tableSchema = Meta.StringMeta.get.unsafeGetNullable(rs, i + 1),
      tableName = Meta.StringMeta.get.unsafeGetNullable(rs, i + 2),
      tableType = Meta.StringMeta.get.unsafeGetNullable(rs, i + 3),
      selfReferencingColumnName = Meta.StringMeta.get.unsafeGetNullable(rs, i + 4),
      referenceGeneration = Meta.StringMeta.get.unsafeGetNullable(rs, i + 5),
      userDefinedTypeCatalog = Meta.StringMeta.get.unsafeGetNullable(rs, i + 6),
      userDefinedTypeSchema = Meta.StringMeta.get.unsafeGetNullable(rs, i + 7),
      userDefinedTypeName = Meta.StringMeta.get.unsafeGetNullable(rs, i + 8),
      isInsertableInto = Meta.StringMeta.get.unsafeGetNullable(rs, i + 9),
      isTyped = Meta.StringMeta.get.unsafeGetNullable(rs, i + 10),
      commitAction = Meta.StringMeta.get.unsafeGetNullable(rs, i + 11)
    )
  )
}
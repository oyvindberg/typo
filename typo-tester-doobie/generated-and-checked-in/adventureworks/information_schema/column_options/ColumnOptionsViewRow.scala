/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package information_schema
package column_options

import doobie.enumerated.Nullability
import doobie.util.Read
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.Encoder
import java.sql.ResultSet

case class ColumnOptionsViewRow(
  tableCatalog: /* nullability unknown */ Option[String],
  tableSchema: /* nullability unknown */ Option[String],
  tableName: /* nullability unknown */ Option[String],
  columnName: /* nullability unknown */ Option[String],
  optionName: /* nullability unknown */ Option[String],
  optionValue: /* nullability unknown */ Option[String]
)

object ColumnOptionsViewRow {
  implicit lazy val decoder: Decoder[ColumnOptionsViewRow] = Decoder.forProduct6[ColumnOptionsViewRow, /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String]]("table_catalog", "table_schema", "table_name", "column_name", "option_name", "option_value")(ColumnOptionsViewRow.apply)(Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString))
  implicit lazy val encoder: Encoder[ColumnOptionsViewRow] = Encoder.forProduct6[ColumnOptionsViewRow, /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String]]("table_catalog", "table_schema", "table_name", "column_name", "option_name", "option_value")(x => (x.tableCatalog, x.tableSchema, x.tableName, x.columnName, x.optionName, x.optionValue))(Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString))
  implicit lazy val read: Read[ColumnOptionsViewRow] = new Read[ColumnOptionsViewRow](
    gets = List(
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable)
    ),
    unsafeGet = (rs: ResultSet, i: Int) => ColumnOptionsViewRow(
      tableCatalog = Meta.StringMeta.get.unsafeGetNullable(rs, i + 0),
      tableSchema = Meta.StringMeta.get.unsafeGetNullable(rs, i + 1),
      tableName = Meta.StringMeta.get.unsafeGetNullable(rs, i + 2),
      columnName = Meta.StringMeta.get.unsafeGetNullable(rs, i + 3),
      optionName = Meta.StringMeta.get.unsafeGetNullable(rs, i + 4),
      optionValue = Meta.StringMeta.get.unsafeGetNullable(rs, i + 5)
    )
  )
}
/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package information_schema
package routine_table_usage

import doobie.enumerated.Nullability
import doobie.util.Read
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.Encoder
import java.sql.ResultSet

case class RoutineTableUsageViewRow(
  specificCatalog: /* nullability unknown */ Option[String],
  specificSchema: /* nullability unknown */ Option[String],
  specificName: /* nullability unknown */ Option[String],
  routineCatalog: /* nullability unknown */ Option[String],
  routineSchema: /* nullability unknown */ Option[String],
  routineName: /* nullability unknown */ Option[String],
  tableCatalog: /* nullability unknown */ Option[String],
  tableSchema: /* nullability unknown */ Option[String],
  tableName: /* nullability unknown */ Option[String]
)

object RoutineTableUsageViewRow {
  implicit lazy val decoder: Decoder[RoutineTableUsageViewRow] = Decoder.forProduct9[RoutineTableUsageViewRow, /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String]]("specific_catalog", "specific_schema", "specific_name", "routine_catalog", "routine_schema", "routine_name", "table_catalog", "table_schema", "table_name")(RoutineTableUsageViewRow.apply)(Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString))
  implicit lazy val encoder: Encoder[RoutineTableUsageViewRow] = Encoder.forProduct9[RoutineTableUsageViewRow, /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String]]("specific_catalog", "specific_schema", "specific_name", "routine_catalog", "routine_schema", "routine_name", "table_catalog", "table_schema", "table_name")(x => (x.specificCatalog, x.specificSchema, x.specificName, x.routineCatalog, x.routineSchema, x.routineName, x.tableCatalog, x.tableSchema, x.tableName))(Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString))
  implicit lazy val read: Read[RoutineTableUsageViewRow] = new Read[RoutineTableUsageViewRow](
    gets = List(
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
    unsafeGet = (rs: ResultSet, i: Int) => RoutineTableUsageViewRow(
      specificCatalog = Meta.StringMeta.get.unsafeGetNullable(rs, i + 0),
      specificSchema = Meta.StringMeta.get.unsafeGetNullable(rs, i + 1),
      specificName = Meta.StringMeta.get.unsafeGetNullable(rs, i + 2),
      routineCatalog = Meta.StringMeta.get.unsafeGetNullable(rs, i + 3),
      routineSchema = Meta.StringMeta.get.unsafeGetNullable(rs, i + 4),
      routineName = Meta.StringMeta.get.unsafeGetNullable(rs, i + 5),
      tableCatalog = Meta.StringMeta.get.unsafeGetNullable(rs, i + 6),
      tableSchema = Meta.StringMeta.get.unsafeGetNullable(rs, i + 7),
      tableName = Meta.StringMeta.get.unsafeGetNullable(rs, i + 8)
    )
  )
}
/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_indexes

import doobie.enumerated.Nullability
import doobie.util.Read
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.Encoder
import java.sql.ResultSet

case class PgIndexesViewRow(
  schemaname: Option[String],
  tablename: Option[String],
  indexname: Option[String],
  tablespace: Option[String],
  indexdef: Option[String]
)

object PgIndexesViewRow {
  implicit lazy val decoder: Decoder[PgIndexesViewRow] = Decoder.forProduct5[PgIndexesViewRow, Option[String], Option[String], Option[String], Option[String], Option[String]]("schemaname", "tablename", "indexname", "tablespace", "indexdef")(PgIndexesViewRow.apply)(Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString))
  implicit lazy val encoder: Encoder[PgIndexesViewRow] = Encoder.forProduct5[PgIndexesViewRow, Option[String], Option[String], Option[String], Option[String], Option[String]]("schemaname", "tablename", "indexname", "tablespace", "indexdef")(x => (x.schemaname, x.tablename, x.indexname, x.tablespace, x.indexdef))(Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString))
  implicit lazy val read: Read[PgIndexesViewRow] = new Read[PgIndexesViewRow](
    gets = List(
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable)
    ),
    unsafeGet = (rs: ResultSet, i: Int) => PgIndexesViewRow(
      schemaname = Meta.StringMeta.get.unsafeGetNullable(rs, i + 0),
      tablename = Meta.StringMeta.get.unsafeGetNullable(rs, i + 1),
      indexname = Meta.StringMeta.get.unsafeGetNullable(rs, i + 2),
      tablespace = Meta.StringMeta.get.unsafeGetNullable(rs, i + 3),
      indexdef = Meta.StringMeta.get.unsafeGetNullable(rs, i + 4)
    )
  )
}
/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_subscription

import doobie.enumerated.Nullability
import doobie.util.Read
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.Encoder
import java.sql.ResultSet

case class PgSubscriptionRow(
  oid: PgSubscriptionId,
  subdbid: /* oid */ Long,
  subname: String,
  subowner: /* oid */ Long,
  subenabled: Boolean,
  subbinary: Boolean,
  substream: Boolean,
  subconninfo: String,
  subslotname: Option[String],
  subsynccommit: String,
  subpublications: Array[String]
)

object PgSubscriptionRow {
  implicit lazy val decoder: Decoder[PgSubscriptionRow] = Decoder.forProduct11[PgSubscriptionRow, PgSubscriptionId, /* oid */ Long, String, /* oid */ Long, Boolean, Boolean, Boolean, String, Option[String], String, Array[String]]("oid", "subdbid", "subname", "subowner", "subenabled", "subbinary", "substream", "subconninfo", "subslotname", "subsynccommit", "subpublications")(PgSubscriptionRow.apply)(PgSubscriptionId.decoder, Decoder.decodeLong, Decoder.decodeString, Decoder.decodeLong, Decoder.decodeBoolean, Decoder.decodeBoolean, Decoder.decodeBoolean, Decoder.decodeString, Decoder.decodeOption(Decoder.decodeString), Decoder.decodeString, Decoder.decodeArray[String](Decoder.decodeString, implicitly))
  implicit lazy val encoder: Encoder[PgSubscriptionRow] = Encoder.forProduct11[PgSubscriptionRow, PgSubscriptionId, /* oid */ Long, String, /* oid */ Long, Boolean, Boolean, Boolean, String, Option[String], String, Array[String]]("oid", "subdbid", "subname", "subowner", "subenabled", "subbinary", "substream", "subconninfo", "subslotname", "subsynccommit", "subpublications")(x => (x.oid, x.subdbid, x.subname, x.subowner, x.subenabled, x.subbinary, x.substream, x.subconninfo, x.subslotname, x.subsynccommit, x.subpublications))(PgSubscriptionId.encoder, Encoder.encodeLong, Encoder.encodeString, Encoder.encodeLong, Encoder.encodeBoolean, Encoder.encodeBoolean, Encoder.encodeBoolean, Encoder.encodeString, Encoder.encodeOption(Encoder.encodeString), Encoder.encodeString, Encoder.encodeIterable[String, Array](Encoder.encodeString, implicitly))
  implicit lazy val read: Read[PgSubscriptionRow] = new Read[PgSubscriptionRow](
    gets = List(
      (PgSubscriptionId.get, Nullability.NoNulls),
      (Meta.LongMeta.get, Nullability.NoNulls),
      (Meta.StringMeta.get, Nullability.NoNulls),
      (Meta.LongMeta.get, Nullability.NoNulls),
      (Meta.BooleanMeta.get, Nullability.NoNulls),
      (Meta.BooleanMeta.get, Nullability.NoNulls),
      (Meta.BooleanMeta.get, Nullability.NoNulls),
      (Meta.StringMeta.get, Nullability.NoNulls),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.NoNulls),
      (adventureworks.StringArrayMeta.get, Nullability.NoNulls)
    ),
    unsafeGet = (rs: ResultSet, i: Int) => PgSubscriptionRow(
      oid = PgSubscriptionId.get.unsafeGetNonNullable(rs, i + 0),
      subdbid = Meta.LongMeta.get.unsafeGetNonNullable(rs, i + 1),
      subname = Meta.StringMeta.get.unsafeGetNonNullable(rs, i + 2),
      subowner = Meta.LongMeta.get.unsafeGetNonNullable(rs, i + 3),
      subenabled = Meta.BooleanMeta.get.unsafeGetNonNullable(rs, i + 4),
      subbinary = Meta.BooleanMeta.get.unsafeGetNonNullable(rs, i + 5),
      substream = Meta.BooleanMeta.get.unsafeGetNonNullable(rs, i + 6),
      subconninfo = Meta.StringMeta.get.unsafeGetNonNullable(rs, i + 7),
      subslotname = Meta.StringMeta.get.unsafeGetNullable(rs, i + 8),
      subsynccommit = Meta.StringMeta.get.unsafeGetNonNullable(rs, i + 9),
      subpublications = adventureworks.StringArrayMeta.get.unsafeGetNonNullable(rs, i + 10)
    )
  )
}
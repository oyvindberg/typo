/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_stat_wal_receiver

import adventureworks.customtypes.TypoInstant
import doobie.enumerated.Nullability
import doobie.util.Read
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.Encoder
import java.sql.ResultSet

case class PgStatWalReceiverViewRow(
  pid: /* nullability unknown */ Option[Int],
  status: /* nullability unknown */ Option[String],
  receiveStartLsn: /* nullability unknown */ Option[/* pg_lsn */ Long],
  receiveStartTli: /* nullability unknown */ Option[Int],
  writtenLsn: /* nullability unknown */ Option[/* pg_lsn */ Long],
  flushedLsn: /* nullability unknown */ Option[/* pg_lsn */ Long],
  receivedTli: /* nullability unknown */ Option[Int],
  lastMsgSendTime: /* nullability unknown */ Option[TypoInstant],
  lastMsgReceiptTime: /* nullability unknown */ Option[TypoInstant],
  latestEndLsn: /* nullability unknown */ Option[/* pg_lsn */ Long],
  latestEndTime: /* nullability unknown */ Option[TypoInstant],
  slotName: /* nullability unknown */ Option[String],
  senderHost: /* nullability unknown */ Option[String],
  senderPort: /* nullability unknown */ Option[Int],
  conninfo: /* nullability unknown */ Option[String]
)

object PgStatWalReceiverViewRow {
  implicit lazy val decoder: Decoder[PgStatWalReceiverViewRow] = Decoder.forProduct15[PgStatWalReceiverViewRow, /* nullability unknown */ Option[Int], /* nullability unknown */ Option[String], /* nullability unknown */ Option[/* pg_lsn */ Long], /* nullability unknown */ Option[Int], /* nullability unknown */ Option[/* pg_lsn */ Long], /* nullability unknown */ Option[/* pg_lsn */ Long], /* nullability unknown */ Option[Int], /* nullability unknown */ Option[TypoInstant], /* nullability unknown */ Option[TypoInstant], /* nullability unknown */ Option[/* pg_lsn */ Long], /* nullability unknown */ Option[TypoInstant], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[Int], /* nullability unknown */ Option[String]]("pid", "status", "receive_start_lsn", "receive_start_tli", "written_lsn", "flushed_lsn", "received_tli", "last_msg_send_time", "last_msg_receipt_time", "latest_end_lsn", "latest_end_time", "slot_name", "sender_host", "sender_port", "conninfo")(PgStatWalReceiverViewRow.apply)(Decoder.decodeOption(Decoder.decodeInt), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeLong), Decoder.decodeOption(Decoder.decodeInt), Decoder.decodeOption(Decoder.decodeLong), Decoder.decodeOption(Decoder.decodeLong), Decoder.decodeOption(Decoder.decodeInt), Decoder.decodeOption(TypoInstant.decoder), Decoder.decodeOption(TypoInstant.decoder), Decoder.decodeOption(Decoder.decodeLong), Decoder.decodeOption(TypoInstant.decoder), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeInt), Decoder.decodeOption(Decoder.decodeString))
  implicit lazy val encoder: Encoder[PgStatWalReceiverViewRow] = Encoder.forProduct15[PgStatWalReceiverViewRow, /* nullability unknown */ Option[Int], /* nullability unknown */ Option[String], /* nullability unknown */ Option[/* pg_lsn */ Long], /* nullability unknown */ Option[Int], /* nullability unknown */ Option[/* pg_lsn */ Long], /* nullability unknown */ Option[/* pg_lsn */ Long], /* nullability unknown */ Option[Int], /* nullability unknown */ Option[TypoInstant], /* nullability unknown */ Option[TypoInstant], /* nullability unknown */ Option[/* pg_lsn */ Long], /* nullability unknown */ Option[TypoInstant], /* nullability unknown */ Option[String], /* nullability unknown */ Option[String], /* nullability unknown */ Option[Int], /* nullability unknown */ Option[String]]("pid", "status", "receive_start_lsn", "receive_start_tli", "written_lsn", "flushed_lsn", "received_tli", "last_msg_send_time", "last_msg_receipt_time", "latest_end_lsn", "latest_end_time", "slot_name", "sender_host", "sender_port", "conninfo")(x => (x.pid, x.status, x.receiveStartLsn, x.receiveStartTli, x.writtenLsn, x.flushedLsn, x.receivedTli, x.lastMsgSendTime, x.lastMsgReceiptTime, x.latestEndLsn, x.latestEndTime, x.slotName, x.senderHost, x.senderPort, x.conninfo))(Encoder.encodeOption(Encoder.encodeInt), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeLong), Encoder.encodeOption(Encoder.encodeInt), Encoder.encodeOption(Encoder.encodeLong), Encoder.encodeOption(Encoder.encodeLong), Encoder.encodeOption(Encoder.encodeInt), Encoder.encodeOption(TypoInstant.encoder), Encoder.encodeOption(TypoInstant.encoder), Encoder.encodeOption(Encoder.encodeLong), Encoder.encodeOption(TypoInstant.encoder), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeInt), Encoder.encodeOption(Encoder.encodeString))
  implicit lazy val read: Read[PgStatWalReceiverViewRow] = new Read[PgStatWalReceiverViewRow](
    gets = List(
      (Meta.IntMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.LongMeta.get, Nullability.Nullable),
      (Meta.IntMeta.get, Nullability.Nullable),
      (Meta.LongMeta.get, Nullability.Nullable),
      (Meta.LongMeta.get, Nullability.Nullable),
      (Meta.IntMeta.get, Nullability.Nullable),
      (TypoInstant.get, Nullability.Nullable),
      (TypoInstant.get, Nullability.Nullable),
      (Meta.LongMeta.get, Nullability.Nullable),
      (TypoInstant.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.IntMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable)
    ),
    unsafeGet = (rs: ResultSet, i: Int) => PgStatWalReceiverViewRow(
      pid = Meta.IntMeta.get.unsafeGetNullable(rs, i + 0),
      status = Meta.StringMeta.get.unsafeGetNullable(rs, i + 1),
      receiveStartLsn = Meta.LongMeta.get.unsafeGetNullable(rs, i + 2),
      receiveStartTli = Meta.IntMeta.get.unsafeGetNullable(rs, i + 3),
      writtenLsn = Meta.LongMeta.get.unsafeGetNullable(rs, i + 4),
      flushedLsn = Meta.LongMeta.get.unsafeGetNullable(rs, i + 5),
      receivedTli = Meta.IntMeta.get.unsafeGetNullable(rs, i + 6),
      lastMsgSendTime = TypoInstant.get.unsafeGetNullable(rs, i + 7),
      lastMsgReceiptTime = TypoInstant.get.unsafeGetNullable(rs, i + 8),
      latestEndLsn = Meta.LongMeta.get.unsafeGetNullable(rs, i + 9),
      latestEndTime = TypoInstant.get.unsafeGetNullable(rs, i + 10),
      slotName = Meta.StringMeta.get.unsafeGetNullable(rs, i + 11),
      senderHost = Meta.StringMeta.get.unsafeGetNullable(rs, i + 12),
      senderPort = Meta.IntMeta.get.unsafeGetNullable(rs, i + 13),
      conninfo = Meta.StringMeta.get.unsafeGetNullable(rs, i + 14)
    )
  )
}
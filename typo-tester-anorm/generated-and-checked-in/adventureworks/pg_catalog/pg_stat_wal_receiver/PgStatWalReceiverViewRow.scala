/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_stat_wal_receiver

import adventureworks.customtypes.TypoOffsetDateTime
import anorm.Column
import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.OWrites
import play.api.libs.json.Reads
import play.api.libs.json.Writes
import scala.collection.immutable.ListMap
import scala.util.Try

case class PgStatWalReceiverViewRow(
  pid: /* nullability unknown */ Option[Int],
  status: /* nullability unknown */ Option[String],
  receiveStartLsn: /* nullability unknown */ Option[/* pg_lsn */ Long],
  receiveStartTli: /* nullability unknown */ Option[Int],
  writtenLsn: /* nullability unknown */ Option[/* pg_lsn */ Long],
  flushedLsn: /* nullability unknown */ Option[/* pg_lsn */ Long],
  receivedTli: /* nullability unknown */ Option[Int],
  lastMsgSendTime: /* nullability unknown */ Option[TypoOffsetDateTime],
  lastMsgReceiptTime: /* nullability unknown */ Option[TypoOffsetDateTime],
  latestEndLsn: /* nullability unknown */ Option[/* pg_lsn */ Long],
  latestEndTime: /* nullability unknown */ Option[TypoOffsetDateTime],
  slotName: /* nullability unknown */ Option[String],
  senderHost: /* nullability unknown */ Option[String],
  senderPort: /* nullability unknown */ Option[Int],
  conninfo: /* nullability unknown */ Option[String]
)

object PgStatWalReceiverViewRow {
  implicit lazy val reads: Reads[PgStatWalReceiverViewRow] = Reads[PgStatWalReceiverViewRow](json => JsResult.fromTry(
      Try(
        PgStatWalReceiverViewRow(
          pid = json.\("pid").toOption.map(_.as(Reads.IntReads)),
          status = json.\("status").toOption.map(_.as(Reads.StringReads)),
          receiveStartLsn = json.\("receive_start_lsn").toOption.map(_.as(Reads.LongReads)),
          receiveStartTli = json.\("receive_start_tli").toOption.map(_.as(Reads.IntReads)),
          writtenLsn = json.\("written_lsn").toOption.map(_.as(Reads.LongReads)),
          flushedLsn = json.\("flushed_lsn").toOption.map(_.as(Reads.LongReads)),
          receivedTli = json.\("received_tli").toOption.map(_.as(Reads.IntReads)),
          lastMsgSendTime = json.\("last_msg_send_time").toOption.map(_.as(TypoOffsetDateTime.reads)),
          lastMsgReceiptTime = json.\("last_msg_receipt_time").toOption.map(_.as(TypoOffsetDateTime.reads)),
          latestEndLsn = json.\("latest_end_lsn").toOption.map(_.as(Reads.LongReads)),
          latestEndTime = json.\("latest_end_time").toOption.map(_.as(TypoOffsetDateTime.reads)),
          slotName = json.\("slot_name").toOption.map(_.as(Reads.StringReads)),
          senderHost = json.\("sender_host").toOption.map(_.as(Reads.StringReads)),
          senderPort = json.\("sender_port").toOption.map(_.as(Reads.IntReads)),
          conninfo = json.\("conninfo").toOption.map(_.as(Reads.StringReads))
        )
      )
    ),
  )
  def rowParser(idx: Int): RowParser[PgStatWalReceiverViewRow] = RowParser[PgStatWalReceiverViewRow] { row =>
    Success(
      PgStatWalReceiverViewRow(
        pid = row(idx + 0)(Column.columnToOption(Column.columnToInt)),
        status = row(idx + 1)(Column.columnToOption(Column.columnToString)),
        receiveStartLsn = row(idx + 2)(Column.columnToOption(Column.columnToLong)),
        receiveStartTli = row(idx + 3)(Column.columnToOption(Column.columnToInt)),
        writtenLsn = row(idx + 4)(Column.columnToOption(Column.columnToLong)),
        flushedLsn = row(idx + 5)(Column.columnToOption(Column.columnToLong)),
        receivedTli = row(idx + 6)(Column.columnToOption(Column.columnToInt)),
        lastMsgSendTime = row(idx + 7)(Column.columnToOption(TypoOffsetDateTime.column)),
        lastMsgReceiptTime = row(idx + 8)(Column.columnToOption(TypoOffsetDateTime.column)),
        latestEndLsn = row(idx + 9)(Column.columnToOption(Column.columnToLong)),
        latestEndTime = row(idx + 10)(Column.columnToOption(TypoOffsetDateTime.column)),
        slotName = row(idx + 11)(Column.columnToOption(Column.columnToString)),
        senderHost = row(idx + 12)(Column.columnToOption(Column.columnToString)),
        senderPort = row(idx + 13)(Column.columnToOption(Column.columnToInt)),
        conninfo = row(idx + 14)(Column.columnToOption(Column.columnToString))
      )
    )
  }
  implicit lazy val writes: OWrites[PgStatWalReceiverViewRow] = OWrites[PgStatWalReceiverViewRow](o =>
    new JsObject(ListMap[String, JsValue](
      "pid" -> Writes.OptionWrites(Writes.IntWrites).writes(o.pid),
      "status" -> Writes.OptionWrites(Writes.StringWrites).writes(o.status),
      "receive_start_lsn" -> Writes.OptionWrites(Writes.LongWrites).writes(o.receiveStartLsn),
      "receive_start_tli" -> Writes.OptionWrites(Writes.IntWrites).writes(o.receiveStartTli),
      "written_lsn" -> Writes.OptionWrites(Writes.LongWrites).writes(o.writtenLsn),
      "flushed_lsn" -> Writes.OptionWrites(Writes.LongWrites).writes(o.flushedLsn),
      "received_tli" -> Writes.OptionWrites(Writes.IntWrites).writes(o.receivedTli),
      "last_msg_send_time" -> Writes.OptionWrites(TypoOffsetDateTime.writes).writes(o.lastMsgSendTime),
      "last_msg_receipt_time" -> Writes.OptionWrites(TypoOffsetDateTime.writes).writes(o.lastMsgReceiptTime),
      "latest_end_lsn" -> Writes.OptionWrites(Writes.LongWrites).writes(o.latestEndLsn),
      "latest_end_time" -> Writes.OptionWrites(TypoOffsetDateTime.writes).writes(o.latestEndTime),
      "slot_name" -> Writes.OptionWrites(Writes.StringWrites).writes(o.slotName),
      "sender_host" -> Writes.OptionWrites(Writes.StringWrites).writes(o.senderHost),
      "sender_port" -> Writes.OptionWrites(Writes.IntWrites).writes(o.senderPort),
      "conninfo" -> Writes.OptionWrites(Writes.StringWrites).writes(o.conninfo)
    ))
  )
}
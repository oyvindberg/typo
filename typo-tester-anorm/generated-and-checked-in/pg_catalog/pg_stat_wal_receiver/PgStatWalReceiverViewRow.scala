/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_stat_wal_receiver

import anorm.RowParser
import anorm.Success
import java.time.OffsetDateTime
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgStatWalReceiverViewRow(
  pid: Option[Int],
  status: Option[String],
  receiveStartLsn: Option[/* pg_lsn */ Long],
  receiveStartTli: Option[Int],
  writtenLsn: Option[/* pg_lsn */ Long],
  flushedLsn: Option[/* pg_lsn */ Long],
  receivedTli: Option[Int],
  lastMsgSendTime: Option[OffsetDateTime],
  lastMsgReceiptTime: Option[OffsetDateTime],
  latestEndLsn: Option[/* pg_lsn */ Long],
  latestEndTime: Option[OffsetDateTime],
  slotName: Option[String],
  senderHost: Option[String],
  senderPort: Option[Int],
  conninfo: Option[String]
)

object PgStatWalReceiverViewRow {
  def rowParser(idx: Int): RowParser[PgStatWalReceiverViewRow] =
    RowParser[PgStatWalReceiverViewRow] { row =>
      Success(
        PgStatWalReceiverViewRow(
          pid = row[Option[Int]](idx + 0),
          status = row[Option[String]](idx + 1),
          receiveStartLsn = row[Option[/* pg_lsn */ Long]](idx + 2),
          receiveStartTli = row[Option[Int]](idx + 3),
          writtenLsn = row[Option[/* pg_lsn */ Long]](idx + 4),
          flushedLsn = row[Option[/* pg_lsn */ Long]](idx + 5),
          receivedTli = row[Option[Int]](idx + 6),
          lastMsgSendTime = row[Option[OffsetDateTime]](idx + 7),
          lastMsgReceiptTime = row[Option[OffsetDateTime]](idx + 8),
          latestEndLsn = row[Option[/* pg_lsn */ Long]](idx + 9),
          latestEndTime = row[Option[OffsetDateTime]](idx + 10),
          slotName = row[Option[String]](idx + 11),
          senderHost = row[Option[String]](idx + 12),
          senderPort = row[Option[Int]](idx + 13),
          conninfo = row[Option[String]](idx + 14)
        )
      )
    }
  implicit val oFormat: OFormat[PgStatWalReceiverViewRow] = new OFormat[PgStatWalReceiverViewRow]{
    override def writes(o: PgStatWalReceiverViewRow): JsObject =
      Json.obj(
        "pid" -> o.pid,
        "status" -> o.status,
        "receive_start_lsn" -> o.receiveStartLsn,
        "receive_start_tli" -> o.receiveStartTli,
        "written_lsn" -> o.writtenLsn,
        "flushed_lsn" -> o.flushedLsn,
        "received_tli" -> o.receivedTli,
        "last_msg_send_time" -> o.lastMsgSendTime,
        "last_msg_receipt_time" -> o.lastMsgReceiptTime,
        "latest_end_lsn" -> o.latestEndLsn,
        "latest_end_time" -> o.latestEndTime,
        "slot_name" -> o.slotName,
        "sender_host" -> o.senderHost,
        "sender_port" -> o.senderPort,
        "conninfo" -> o.conninfo
      )
  
    override def reads(json: JsValue): JsResult[PgStatWalReceiverViewRow] = {
      JsResult.fromTry(
        Try(
          PgStatWalReceiverViewRow(
            pid = json.\("pid").toOption.map(_.as[Int]),
            status = json.\("status").toOption.map(_.as[String]),
            receiveStartLsn = json.\("receive_start_lsn").toOption.map(_.as[/* pg_lsn */ Long]),
            receiveStartTli = json.\("receive_start_tli").toOption.map(_.as[Int]),
            writtenLsn = json.\("written_lsn").toOption.map(_.as[/* pg_lsn */ Long]),
            flushedLsn = json.\("flushed_lsn").toOption.map(_.as[/* pg_lsn */ Long]),
            receivedTli = json.\("received_tli").toOption.map(_.as[Int]),
            lastMsgSendTime = json.\("last_msg_send_time").toOption.map(_.as[OffsetDateTime]),
            lastMsgReceiptTime = json.\("last_msg_receipt_time").toOption.map(_.as[OffsetDateTime]),
            latestEndLsn = json.\("latest_end_lsn").toOption.map(_.as[/* pg_lsn */ Long]),
            latestEndTime = json.\("latest_end_time").toOption.map(_.as[OffsetDateTime]),
            slotName = json.\("slot_name").toOption.map(_.as[String]),
            senderHost = json.\("sender_host").toOption.map(_.as[String]),
            senderPort = json.\("sender_port").toOption.map(_.as[Int]),
            conninfo = json.\("conninfo").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}
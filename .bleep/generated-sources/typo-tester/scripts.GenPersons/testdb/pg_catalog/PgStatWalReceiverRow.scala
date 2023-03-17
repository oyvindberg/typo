package testdb
package pg_catalog

import anorm.RowParser
import anorm.Success
import java.time.LocalDateTime
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgStatWalReceiverRow(
  pid: /* unknown nullability */ Option[Int],
  status: /* unknown nullability */ Option[String],
  receiveStartLsn: /* unknown nullability */ Option[/* pg_lsn */ String],
  receiveStartTli: /* unknown nullability */ Option[Int],
  writtenLsn: /* unknown nullability */ Option[/* pg_lsn */ String],
  flushedLsn: /* unknown nullability */ Option[/* pg_lsn */ String],
  receivedTli: /* unknown nullability */ Option[Int],
  lastMsgSendTime: /* unknown nullability */ Option[LocalDateTime],
  lastMsgReceiptTime: /* unknown nullability */ Option[LocalDateTime],
  latestEndLsn: /* unknown nullability */ Option[/* pg_lsn */ String],
  latestEndTime: /* unknown nullability */ Option[LocalDateTime],
  slotName: /* unknown nullability */ Option[String],
  senderHost: /* unknown nullability */ Option[String],
  senderPort: /* unknown nullability */ Option[Int],
  conninfo: /* unknown nullability */ Option[String]
)

object PgStatWalReceiverRow {
  implicit val rowParser: RowParser[PgStatWalReceiverRow] = { row =>
    Success(
      PgStatWalReceiverRow(
        pid = row[/* unknown nullability */ Option[Int]]("pid"),
        status = row[/* unknown nullability */ Option[String]]("status"),
        receiveStartLsn = row[/* unknown nullability */ Option[/* pg_lsn */ String]]("receive_start_lsn"),
        receiveStartTli = row[/* unknown nullability */ Option[Int]]("receive_start_tli"),
        writtenLsn = row[/* unknown nullability */ Option[/* pg_lsn */ String]]("written_lsn"),
        flushedLsn = row[/* unknown nullability */ Option[/* pg_lsn */ String]]("flushed_lsn"),
        receivedTli = row[/* unknown nullability */ Option[Int]]("received_tli"),
        lastMsgSendTime = row[/* unknown nullability */ Option[LocalDateTime]]("last_msg_send_time"),
        lastMsgReceiptTime = row[/* unknown nullability */ Option[LocalDateTime]]("last_msg_receipt_time"),
        latestEndLsn = row[/* unknown nullability */ Option[/* pg_lsn */ String]]("latest_end_lsn"),
        latestEndTime = row[/* unknown nullability */ Option[LocalDateTime]]("latest_end_time"),
        slotName = row[/* unknown nullability */ Option[String]]("slot_name"),
        senderHost = row[/* unknown nullability */ Option[String]]("sender_host"),
        senderPort = row[/* unknown nullability */ Option[Int]]("sender_port"),
        conninfo = row[/* unknown nullability */ Option[String]]("conninfo")
      )
    )
  }

  implicit val oFormat: OFormat[PgStatWalReceiverRow] = new OFormat[PgStatWalReceiverRow]{
    override def writes(o: PgStatWalReceiverRow): JsObject =
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

    override def reads(json: JsValue): JsResult[PgStatWalReceiverRow] = {
      JsResult.fromTry(
        Try(
          PgStatWalReceiverRow(
            pid = json.\("pid").toOption.map(_.as[Int]),
            status = json.\("status").toOption.map(_.as[String]),
            receiveStartLsn = json.\("receive_start_lsn").toOption.map(_.as[/* pg_lsn */ String]),
            receiveStartTli = json.\("receive_start_tli").toOption.map(_.as[Int]),
            writtenLsn = json.\("written_lsn").toOption.map(_.as[/* pg_lsn */ String]),
            flushedLsn = json.\("flushed_lsn").toOption.map(_.as[/* pg_lsn */ String]),
            receivedTli = json.\("received_tli").toOption.map(_.as[Int]),
            lastMsgSendTime = json.\("last_msg_send_time").toOption.map(_.as[LocalDateTime]),
            lastMsgReceiptTime = json.\("last_msg_receipt_time").toOption.map(_.as[LocalDateTime]),
            latestEndLsn = json.\("latest_end_lsn").toOption.map(_.as[/* pg_lsn */ String]),
            latestEndTime = json.\("latest_end_time").toOption.map(_.as[LocalDateTime]),
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

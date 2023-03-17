package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import java.time.LocalDateTime
import play.api.libs.json.Json
import play.api.libs.json.OFormat

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

  implicit val oFormat: OFormat[PgStatWalReceiverRow] = Json.format
}

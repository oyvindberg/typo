package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import java.time.LocalDateTime
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgStatSubscriptionRow(
  subid: Long,
  subname: String,
  pid: /* unknown nullability */ Option[Int],
  relid: /* unknown nullability */ Option[Long],
  receivedLsn: /* unknown nullability */ Option[/* pg_lsn */ String],
  lastMsgSendTime: /* unknown nullability */ Option[LocalDateTime],
  lastMsgReceiptTime: /* unknown nullability */ Option[LocalDateTime],
  latestEndLsn: /* unknown nullability */ Option[/* pg_lsn */ String],
  latestEndTime: /* unknown nullability */ Option[LocalDateTime]
)

object PgStatSubscriptionRow {
  implicit val rowParser: RowParser[PgStatSubscriptionRow] = { row =>
    Success(
      PgStatSubscriptionRow(
        subid = row[Long]("subid"),
        subname = row[String]("subname"),
        pid = row[/* unknown nullability */ Option[Int]]("pid"),
        relid = row[/* unknown nullability */ Option[Long]]("relid"),
        receivedLsn = row[/* unknown nullability */ Option[/* pg_lsn */ String]]("received_lsn"),
        lastMsgSendTime = row[/* unknown nullability */ Option[LocalDateTime]]("last_msg_send_time"),
        lastMsgReceiptTime = row[/* unknown nullability */ Option[LocalDateTime]]("last_msg_receipt_time"),
        latestEndLsn = row[/* unknown nullability */ Option[/* pg_lsn */ String]]("latest_end_lsn"),
        latestEndTime = row[/* unknown nullability */ Option[LocalDateTime]]("latest_end_time")
      )
    )
  }

  implicit val oFormat: OFormat[PgStatSubscriptionRow] = Json.format
}

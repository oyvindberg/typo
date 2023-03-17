package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import java.time.LocalDateTime
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgStatReplicationSlotsRow(
  slotName: /* unknown nullability */ Option[String],
  spillTxns: /* unknown nullability */ Option[Long],
  spillCount: /* unknown nullability */ Option[Long],
  spillBytes: /* unknown nullability */ Option[Long],
  streamTxns: /* unknown nullability */ Option[Long],
  streamCount: /* unknown nullability */ Option[Long],
  streamBytes: /* unknown nullability */ Option[Long],
  totalTxns: /* unknown nullability */ Option[Long],
  totalBytes: /* unknown nullability */ Option[Long],
  statsReset: /* unknown nullability */ Option[LocalDateTime]
)

object PgStatReplicationSlotsRow {
  implicit val rowParser: RowParser[PgStatReplicationSlotsRow] = { row =>
    Success(
      PgStatReplicationSlotsRow(
        slotName = row[/* unknown nullability */ Option[String]]("slot_name"),
        spillTxns = row[/* unknown nullability */ Option[Long]]("spill_txns"),
        spillCount = row[/* unknown nullability */ Option[Long]]("spill_count"),
        spillBytes = row[/* unknown nullability */ Option[Long]]("spill_bytes"),
        streamTxns = row[/* unknown nullability */ Option[Long]]("stream_txns"),
        streamCount = row[/* unknown nullability */ Option[Long]]("stream_count"),
        streamBytes = row[/* unknown nullability */ Option[Long]]("stream_bytes"),
        totalTxns = row[/* unknown nullability */ Option[Long]]("total_txns"),
        totalBytes = row[/* unknown nullability */ Option[Long]]("total_bytes"),
        statsReset = row[/* unknown nullability */ Option[LocalDateTime]]("stats_reset")
      )
    )
  }

  implicit val oFormat: OFormat[PgStatReplicationSlotsRow] = new OFormat[PgStatReplicationSlotsRow]{
    override def writes(o: PgStatReplicationSlotsRow): JsObject =
      Json.obj(
        "slot_name" -> o.slotName,
      "spill_txns" -> o.spillTxns,
      "spill_count" -> o.spillCount,
      "spill_bytes" -> o.spillBytes,
      "stream_txns" -> o.streamTxns,
      "stream_count" -> o.streamCount,
      "stream_bytes" -> o.streamBytes,
      "total_txns" -> o.totalTxns,
      "total_bytes" -> o.totalBytes,
      "stats_reset" -> o.statsReset
      )

    override def reads(json: JsValue): JsResult[PgStatReplicationSlotsRow] = {
      JsResult.fromTry(
        Try(
          PgStatReplicationSlotsRow(
            slotName = json.\("slot_name").toOption.map(_.as[String]),
            spillTxns = json.\("spill_txns").toOption.map(_.as[Long]),
            spillCount = json.\("spill_count").toOption.map(_.as[Long]),
            spillBytes = json.\("spill_bytes").toOption.map(_.as[Long]),
            streamTxns = json.\("stream_txns").toOption.map(_.as[Long]),
            streamCount = json.\("stream_count").toOption.map(_.as[Long]),
            streamBytes = json.\("stream_bytes").toOption.map(_.as[Long]),
            totalTxns = json.\("total_txns").toOption.map(_.as[Long]),
            totalBytes = json.\("total_bytes").toOption.map(_.as[Long]),
            statsReset = json.\("stats_reset").toOption.map(_.as[LocalDateTime])
          )
        )
      )
    }
  }
}

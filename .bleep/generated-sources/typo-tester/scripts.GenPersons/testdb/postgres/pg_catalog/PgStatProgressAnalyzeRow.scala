package testdb
package postgres
package pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgStatProgressAnalyzeRow(
  pid: /* unknown nullability */ Option[Int],
  datid: /* unknown nullability */ Option[Long],
  /** Points to [[PgDatabaseRow.datname]] */
  datname: String,
  relid: /* unknown nullability */ Option[Long],
  phase: /* unknown nullability */ Option[String],
  sampleBlksTotal: /* unknown nullability */ Option[Long],
  sampleBlksScanned: /* unknown nullability */ Option[Long],
  extStatsTotal: /* unknown nullability */ Option[Long],
  extStatsComputed: /* unknown nullability */ Option[Long],
  childTablesTotal: /* unknown nullability */ Option[Long],
  childTablesDone: /* unknown nullability */ Option[Long],
  currentChildTableRelid: /* unknown nullability */ Option[Long]
)

object PgStatProgressAnalyzeRow {
  implicit val rowParser: RowParser[PgStatProgressAnalyzeRow] = { row =>
    Success(
      PgStatProgressAnalyzeRow(
        pid = row[/* unknown nullability */ Option[Int]]("pid"),
        datid = row[/* unknown nullability */ Option[Long]]("datid"),
        datname = row[String]("datname"),
        relid = row[/* unknown nullability */ Option[Long]]("relid"),
        phase = row[/* unknown nullability */ Option[String]]("phase"),
        sampleBlksTotal = row[/* unknown nullability */ Option[Long]]("sample_blks_total"),
        sampleBlksScanned = row[/* unknown nullability */ Option[Long]]("sample_blks_scanned"),
        extStatsTotal = row[/* unknown nullability */ Option[Long]]("ext_stats_total"),
        extStatsComputed = row[/* unknown nullability */ Option[Long]]("ext_stats_computed"),
        childTablesTotal = row[/* unknown nullability */ Option[Long]]("child_tables_total"),
        childTablesDone = row[/* unknown nullability */ Option[Long]]("child_tables_done"),
        currentChildTableRelid = row[/* unknown nullability */ Option[Long]]("current_child_table_relid")
      )
    )
  }

  implicit val oFormat: OFormat[PgStatProgressAnalyzeRow] = new OFormat[PgStatProgressAnalyzeRow]{
    override def writes(o: PgStatProgressAnalyzeRow): JsObject =
      Json.obj(
        "pid" -> o.pid,
      "datid" -> o.datid,
      "datname" -> o.datname,
      "relid" -> o.relid,
      "phase" -> o.phase,
      "sample_blks_total" -> o.sampleBlksTotal,
      "sample_blks_scanned" -> o.sampleBlksScanned,
      "ext_stats_total" -> o.extStatsTotal,
      "ext_stats_computed" -> o.extStatsComputed,
      "child_tables_total" -> o.childTablesTotal,
      "child_tables_done" -> o.childTablesDone,
      "current_child_table_relid" -> o.currentChildTableRelid
      )

    override def reads(json: JsValue): JsResult[PgStatProgressAnalyzeRow] = {
      JsResult.fromTry(
        Try(
          PgStatProgressAnalyzeRow(
            pid = json.\("pid").toOption.map(_.as[Int]),
            datid = json.\("datid").toOption.map(_.as[Long]),
            datname = json.\("datname").as[String],
            relid = json.\("relid").toOption.map(_.as[Long]),
            phase = json.\("phase").toOption.map(_.as[String]),
            sampleBlksTotal = json.\("sample_blks_total").toOption.map(_.as[Long]),
            sampleBlksScanned = json.\("sample_blks_scanned").toOption.map(_.as[Long]),
            extStatsTotal = json.\("ext_stats_total").toOption.map(_.as[Long]),
            extStatsComputed = json.\("ext_stats_computed").toOption.map(_.as[Long]),
            childTablesTotal = json.\("child_tables_total").toOption.map(_.as[Long]),
            childTablesDone = json.\("child_tables_done").toOption.map(_.as[Long]),
            currentChildTableRelid = json.\("current_child_table_relid").toOption.map(_.as[Long])
          )
        )
      )
    }
  }
}

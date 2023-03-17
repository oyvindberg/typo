package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgStatProgressAnalyzeRow(
  pid: /* unknown nullability */ Option[Int],
  datid: /* unknown nullability */ Option[Long],
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

  implicit val oFormat: OFormat[PgStatProgressAnalyzeRow] = Json.format
}

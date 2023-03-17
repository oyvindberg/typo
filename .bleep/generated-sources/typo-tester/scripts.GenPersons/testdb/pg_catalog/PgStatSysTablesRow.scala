package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import java.time.LocalDateTime
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgStatSysTablesRow(
  relid: Option[Long],
  schemaname: Option[String],
  relname: Option[String],
  seqScan: Option[Long],
  seqTupRead: Option[Long],
  idxScan: Option[Long],
  idxTupFetch: Option[Long],
  nTupIns: Option[Long],
  nTupUpd: Option[Long],
  nTupDel: Option[Long],
  nTupHotUpd: Option[Long],
  nLiveTup: Option[Long],
  nDeadTup: Option[Long],
  nModSinceAnalyze: Option[Long],
  nInsSinceVacuum: Option[Long],
  lastVacuum: Option[LocalDateTime],
  lastAutovacuum: Option[LocalDateTime],
  lastAnalyze: Option[LocalDateTime],
  lastAutoanalyze: Option[LocalDateTime],
  vacuumCount: Option[Long],
  autovacuumCount: Option[Long],
  analyzeCount: Option[Long],
  autoanalyzeCount: Option[Long]
)

object PgStatSysTablesRow {
  implicit val rowParser: RowParser[PgStatSysTablesRow] = { row =>
    Success(
      PgStatSysTablesRow(
        relid = row[Option[Long]]("relid"),
        schemaname = row[Option[String]]("schemaname"),
        relname = row[Option[String]]("relname"),
        seqScan = row[Option[Long]]("seq_scan"),
        seqTupRead = row[Option[Long]]("seq_tup_read"),
        idxScan = row[Option[Long]]("idx_scan"),
        idxTupFetch = row[Option[Long]]("idx_tup_fetch"),
        nTupIns = row[Option[Long]]("n_tup_ins"),
        nTupUpd = row[Option[Long]]("n_tup_upd"),
        nTupDel = row[Option[Long]]("n_tup_del"),
        nTupHotUpd = row[Option[Long]]("n_tup_hot_upd"),
        nLiveTup = row[Option[Long]]("n_live_tup"),
        nDeadTup = row[Option[Long]]("n_dead_tup"),
        nModSinceAnalyze = row[Option[Long]]("n_mod_since_analyze"),
        nInsSinceVacuum = row[Option[Long]]("n_ins_since_vacuum"),
        lastVacuum = row[Option[LocalDateTime]]("last_vacuum"),
        lastAutovacuum = row[Option[LocalDateTime]]("last_autovacuum"),
        lastAnalyze = row[Option[LocalDateTime]]("last_analyze"),
        lastAutoanalyze = row[Option[LocalDateTime]]("last_autoanalyze"),
        vacuumCount = row[Option[Long]]("vacuum_count"),
        autovacuumCount = row[Option[Long]]("autovacuum_count"),
        analyzeCount = row[Option[Long]]("analyze_count"),
        autoanalyzeCount = row[Option[Long]]("autoanalyze_count")
      )
    )
  }

  implicit val oFormat: OFormat[PgStatSysTablesRow] = Json.format
}

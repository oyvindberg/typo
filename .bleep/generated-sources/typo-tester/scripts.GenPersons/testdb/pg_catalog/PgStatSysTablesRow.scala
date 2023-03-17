package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import java.time.LocalDateTime
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgStatSysTablesRow(
  /** Points to [[testdb.pg_catalog.PgStatAllTablesRow.relid]] */
  relid: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatAllTablesRow.schemaname]] */
  schemaname: Option[String],
  /** Points to [[testdb.pg_catalog.PgStatAllTablesRow.relname]] */
  relname: Option[String],
  /** Points to [[testdb.pg_catalog.PgStatAllTablesRow.seqScan]] */
  seqScan: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatAllTablesRow.seqTupRead]] */
  seqTupRead: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatAllTablesRow.idxScan]] */
  idxScan: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatAllTablesRow.idxTupFetch]] */
  idxTupFetch: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatAllTablesRow.nTupIns]] */
  nTupIns: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatAllTablesRow.nTupUpd]] */
  nTupUpd: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatAllTablesRow.nTupDel]] */
  nTupDel: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatAllTablesRow.nTupHotUpd]] */
  nTupHotUpd: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatAllTablesRow.nLiveTup]] */
  nLiveTup: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatAllTablesRow.nDeadTup]] */
  nDeadTup: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatAllTablesRow.nModSinceAnalyze]] */
  nModSinceAnalyze: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatAllTablesRow.nInsSinceVacuum]] */
  nInsSinceVacuum: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatAllTablesRow.lastVacuum]] */
  lastVacuum: Option[LocalDateTime],
  /** Points to [[testdb.pg_catalog.PgStatAllTablesRow.lastAutovacuum]] */
  lastAutovacuum: Option[LocalDateTime],
  /** Points to [[testdb.pg_catalog.PgStatAllTablesRow.lastAnalyze]] */
  lastAnalyze: Option[LocalDateTime],
  /** Points to [[testdb.pg_catalog.PgStatAllTablesRow.lastAutoanalyze]] */
  lastAutoanalyze: Option[LocalDateTime],
  /** Points to [[testdb.pg_catalog.PgStatAllTablesRow.vacuumCount]] */
  vacuumCount: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatAllTablesRow.autovacuumCount]] */
  autovacuumCount: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatAllTablesRow.analyzeCount]] */
  analyzeCount: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatAllTablesRow.autoanalyzeCount]] */
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

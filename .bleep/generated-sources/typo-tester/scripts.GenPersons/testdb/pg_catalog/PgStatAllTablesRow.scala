package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import java.time.LocalDateTime
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgStatAllTablesRow(
  /** Points to [[testdb.pg_catalog.PgClassRow.oid]] */
  relid: Long,
  /** Points to [[testdb.pg_catalog.PgNamespaceRow.nspname]] */
  schemaname: String,
  /** Points to [[testdb.pg_catalog.PgClassRow.relname]] */
  relname: String,
  seqScan: /* unknown nullability */ Option[Long],
  seqTupRead: /* unknown nullability */ Option[Long],
  idxScan: /* unknown nullability */ Option[Long],
  idxTupFetch: /* unknown nullability */ Option[Long],
  nTupIns: /* unknown nullability */ Option[Long],
  nTupUpd: /* unknown nullability */ Option[Long],
  nTupDel: /* unknown nullability */ Option[Long],
  nTupHotUpd: /* unknown nullability */ Option[Long],
  nLiveTup: /* unknown nullability */ Option[Long],
  nDeadTup: /* unknown nullability */ Option[Long],
  nModSinceAnalyze: /* unknown nullability */ Option[Long],
  nInsSinceVacuum: /* unknown nullability */ Option[Long],
  lastVacuum: /* unknown nullability */ Option[LocalDateTime],
  lastAutovacuum: /* unknown nullability */ Option[LocalDateTime],
  lastAnalyze: /* unknown nullability */ Option[LocalDateTime],
  lastAutoanalyze: /* unknown nullability */ Option[LocalDateTime],
  vacuumCount: /* unknown nullability */ Option[Long],
  autovacuumCount: /* unknown nullability */ Option[Long],
  analyzeCount: /* unknown nullability */ Option[Long],
  autoanalyzeCount: /* unknown nullability */ Option[Long]
)

object PgStatAllTablesRow {
  implicit val rowParser: RowParser[PgStatAllTablesRow] = { row =>
    Success(
      PgStatAllTablesRow(
        relid = row[Long]("relid"),
        schemaname = row[String]("schemaname"),
        relname = row[String]("relname"),
        seqScan = row[/* unknown nullability */ Option[Long]]("seq_scan"),
        seqTupRead = row[/* unknown nullability */ Option[Long]]("seq_tup_read"),
        idxScan = row[/* unknown nullability */ Option[Long]]("idx_scan"),
        idxTupFetch = row[/* unknown nullability */ Option[Long]]("idx_tup_fetch"),
        nTupIns = row[/* unknown nullability */ Option[Long]]("n_tup_ins"),
        nTupUpd = row[/* unknown nullability */ Option[Long]]("n_tup_upd"),
        nTupDel = row[/* unknown nullability */ Option[Long]]("n_tup_del"),
        nTupHotUpd = row[/* unknown nullability */ Option[Long]]("n_tup_hot_upd"),
        nLiveTup = row[/* unknown nullability */ Option[Long]]("n_live_tup"),
        nDeadTup = row[/* unknown nullability */ Option[Long]]("n_dead_tup"),
        nModSinceAnalyze = row[/* unknown nullability */ Option[Long]]("n_mod_since_analyze"),
        nInsSinceVacuum = row[/* unknown nullability */ Option[Long]]("n_ins_since_vacuum"),
        lastVacuum = row[/* unknown nullability */ Option[LocalDateTime]]("last_vacuum"),
        lastAutovacuum = row[/* unknown nullability */ Option[LocalDateTime]]("last_autovacuum"),
        lastAnalyze = row[/* unknown nullability */ Option[LocalDateTime]]("last_analyze"),
        lastAutoanalyze = row[/* unknown nullability */ Option[LocalDateTime]]("last_autoanalyze"),
        vacuumCount = row[/* unknown nullability */ Option[Long]]("vacuum_count"),
        autovacuumCount = row[/* unknown nullability */ Option[Long]]("autovacuum_count"),
        analyzeCount = row[/* unknown nullability */ Option[Long]]("analyze_count"),
        autoanalyzeCount = row[/* unknown nullability */ Option[Long]]("autoanalyze_count")
      )
    )
  }

  implicit val oFormat: OFormat[PgStatAllTablesRow] = Json.format
}

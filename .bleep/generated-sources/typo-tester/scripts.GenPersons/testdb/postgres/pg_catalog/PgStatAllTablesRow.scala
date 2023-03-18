package testdb
package postgres
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

case class PgStatAllTablesRow(
  /** Points to [[PgClassRow.oid]] */
  relid: Long,
  /** Points to [[PgNamespaceRow.nspname]] */
  schemaname: String,
  /** Points to [[PgClassRow.relname]] */
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

  implicit val oFormat: OFormat[PgStatAllTablesRow] = new OFormat[PgStatAllTablesRow]{
    override def writes(o: PgStatAllTablesRow): JsObject =
      Json.obj(
        "relid" -> o.relid,
      "schemaname" -> o.schemaname,
      "relname" -> o.relname,
      "seq_scan" -> o.seqScan,
      "seq_tup_read" -> o.seqTupRead,
      "idx_scan" -> o.idxScan,
      "idx_tup_fetch" -> o.idxTupFetch,
      "n_tup_ins" -> o.nTupIns,
      "n_tup_upd" -> o.nTupUpd,
      "n_tup_del" -> o.nTupDel,
      "n_tup_hot_upd" -> o.nTupHotUpd,
      "n_live_tup" -> o.nLiveTup,
      "n_dead_tup" -> o.nDeadTup,
      "n_mod_since_analyze" -> o.nModSinceAnalyze,
      "n_ins_since_vacuum" -> o.nInsSinceVacuum,
      "last_vacuum" -> o.lastVacuum,
      "last_autovacuum" -> o.lastAutovacuum,
      "last_analyze" -> o.lastAnalyze,
      "last_autoanalyze" -> o.lastAutoanalyze,
      "vacuum_count" -> o.vacuumCount,
      "autovacuum_count" -> o.autovacuumCount,
      "analyze_count" -> o.analyzeCount,
      "autoanalyze_count" -> o.autoanalyzeCount
      )

    override def reads(json: JsValue): JsResult[PgStatAllTablesRow] = {
      JsResult.fromTry(
        Try(
          PgStatAllTablesRow(
            relid = json.\("relid").as[Long],
            schemaname = json.\("schemaname").as[String],
            relname = json.\("relname").as[String],
            seqScan = json.\("seq_scan").toOption.map(_.as[Long]),
            seqTupRead = json.\("seq_tup_read").toOption.map(_.as[Long]),
            idxScan = json.\("idx_scan").toOption.map(_.as[Long]),
            idxTupFetch = json.\("idx_tup_fetch").toOption.map(_.as[Long]),
            nTupIns = json.\("n_tup_ins").toOption.map(_.as[Long]),
            nTupUpd = json.\("n_tup_upd").toOption.map(_.as[Long]),
            nTupDel = json.\("n_tup_del").toOption.map(_.as[Long]),
            nTupHotUpd = json.\("n_tup_hot_upd").toOption.map(_.as[Long]),
            nLiveTup = json.\("n_live_tup").toOption.map(_.as[Long]),
            nDeadTup = json.\("n_dead_tup").toOption.map(_.as[Long]),
            nModSinceAnalyze = json.\("n_mod_since_analyze").toOption.map(_.as[Long]),
            nInsSinceVacuum = json.\("n_ins_since_vacuum").toOption.map(_.as[Long]),
            lastVacuum = json.\("last_vacuum").toOption.map(_.as[LocalDateTime]),
            lastAutovacuum = json.\("last_autovacuum").toOption.map(_.as[LocalDateTime]),
            lastAnalyze = json.\("last_analyze").toOption.map(_.as[LocalDateTime]),
            lastAutoanalyze = json.\("last_autoanalyze").toOption.map(_.as[LocalDateTime]),
            vacuumCount = json.\("vacuum_count").toOption.map(_.as[Long]),
            autovacuumCount = json.\("autovacuum_count").toOption.map(_.as[Long]),
            analyzeCount = json.\("analyze_count").toOption.map(_.as[Long]),
            autoanalyzeCount = json.\("autoanalyze_count").toOption.map(_.as[Long])
          )
        )
      )
    }
  }
}

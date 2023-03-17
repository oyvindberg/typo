package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgStatXactUserTablesRow(
  /** Points to [[testdb.pg_catalog.PgStatXactAllTablesRow.relid]] */
  relid: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatXactAllTablesRow.schemaname]] */
  schemaname: Option[String],
  /** Points to [[testdb.pg_catalog.PgStatXactAllTablesRow.relname]] */
  relname: Option[String],
  /** Points to [[testdb.pg_catalog.PgStatXactAllTablesRow.seqScan]] */
  seqScan: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatXactAllTablesRow.seqTupRead]] */
  seqTupRead: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatXactAllTablesRow.idxScan]] */
  idxScan: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatXactAllTablesRow.idxTupFetch]] */
  idxTupFetch: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatXactAllTablesRow.nTupIns]] */
  nTupIns: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatXactAllTablesRow.nTupUpd]] */
  nTupUpd: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatXactAllTablesRow.nTupDel]] */
  nTupDel: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatXactAllTablesRow.nTupHotUpd]] */
  nTupHotUpd: Option[Long]
)

object PgStatXactUserTablesRow {
  implicit val rowParser: RowParser[PgStatXactUserTablesRow] = { row =>
    Success(
      PgStatXactUserTablesRow(
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
        nTupHotUpd = row[Option[Long]]("n_tup_hot_upd")
      )
    )
  }

  implicit val oFormat: OFormat[PgStatXactUserTablesRow] = Json.format
}

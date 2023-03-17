package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgStatXactSysTablesRow(
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
  nTupHotUpd: Option[Long]
)

object PgStatXactSysTablesRow {
  implicit val rowParser: RowParser[PgStatXactSysTablesRow] = { row =>
    Success(
      PgStatXactSysTablesRow(
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

  implicit val oFormat: OFormat[PgStatXactSysTablesRow] = Json.format
}

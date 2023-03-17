package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgStatXactAllTablesRow(
  relid: Long,
  schemaname: String,
  relname: String,
  seqScan: /* unknown nullability */ Option[Long],
  seqTupRead: /* unknown nullability */ Option[Long],
  idxScan: /* unknown nullability */ Option[Long],
  idxTupFetch: /* unknown nullability */ Option[Long],
  nTupIns: /* unknown nullability */ Option[Long],
  nTupUpd: /* unknown nullability */ Option[Long],
  nTupDel: /* unknown nullability */ Option[Long],
  nTupHotUpd: /* unknown nullability */ Option[Long]
)

object PgStatXactAllTablesRow {
  implicit val rowParser: RowParser[PgStatXactAllTablesRow] = { row =>
    Success(
      PgStatXactAllTablesRow(
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
        nTupHotUpd = row[/* unknown nullability */ Option[Long]]("n_tup_hot_upd")
      )
    )
  }

  implicit val oFormat: OFormat[PgStatXactAllTablesRow] = Json.format
}

package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

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

  implicit val oFormat: OFormat[PgStatXactUserTablesRow] = new OFormat[PgStatXactUserTablesRow]{
    override def writes(o: PgStatXactUserTablesRow): JsObject =
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
      "n_tup_hot_upd" -> o.nTupHotUpd
      )

    override def reads(json: JsValue): JsResult[PgStatXactUserTablesRow] = {
      JsResult.fromTry(
        Try(
          PgStatXactUserTablesRow(
            relid = json.\("relid").toOption.map(_.as[Long]),
            schemaname = json.\("schemaname").toOption.map(_.as[String]),
            relname = json.\("relname").toOption.map(_.as[String]),
            seqScan = json.\("seq_scan").toOption.map(_.as[Long]),
            seqTupRead = json.\("seq_tup_read").toOption.map(_.as[Long]),
            idxScan = json.\("idx_scan").toOption.map(_.as[Long]),
            idxTupFetch = json.\("idx_tup_fetch").toOption.map(_.as[Long]),
            nTupIns = json.\("n_tup_ins").toOption.map(_.as[Long]),
            nTupUpd = json.\("n_tup_upd").toOption.map(_.as[Long]),
            nTupDel = json.\("n_tup_del").toOption.map(_.as[Long]),
            nTupHotUpd = json.\("n_tup_hot_upd").toOption.map(_.as[Long])
          )
        )
      )
    }
  }
}

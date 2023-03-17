package testdb
package pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgStatXactAllTablesRow(
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

  implicit val oFormat: OFormat[PgStatXactAllTablesRow] = new OFormat[PgStatXactAllTablesRow]{
    override def writes(o: PgStatXactAllTablesRow): JsObject =
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

    override def reads(json: JsValue): JsResult[PgStatXactAllTablesRow] = {
      JsResult.fromTry(
        Try(
          PgStatXactAllTablesRow(
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
            nTupHotUpd = json.\("n_tup_hot_upd").toOption.map(_.as[Long])
          )
        )
      )
    }
  }
}

/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_stat_all_indexes

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgStatAllIndexesViewRow(
  relid: Option[/* oid */ Long],
  indexrelid: Option[/* oid */ Long],
  schemaname: Option[String],
  relname: Option[String],
  indexrelname: Option[String],
  idxScan: Option[Long],
  idxTupRead: Option[Long],
  idxTupFetch: Option[Long]
)

object PgStatAllIndexesViewRow {
  def rowParser(idx: Int): RowParser[PgStatAllIndexesViewRow] =
    RowParser[PgStatAllIndexesViewRow] { row =>
      Success(
        PgStatAllIndexesViewRow(
          relid = row[Option[/* oid */ Long]](idx + 0),
          indexrelid = row[Option[/* oid */ Long]](idx + 1),
          schemaname = row[Option[String]](idx + 2),
          relname = row[Option[String]](idx + 3),
          indexrelname = row[Option[String]](idx + 4),
          idxScan = row[Option[Long]](idx + 5),
          idxTupRead = row[Option[Long]](idx + 6),
          idxTupFetch = row[Option[Long]](idx + 7)
        )
      )
    }
  implicit val oFormat: OFormat[PgStatAllIndexesViewRow] = new OFormat[PgStatAllIndexesViewRow]{
    override def writes(o: PgStatAllIndexesViewRow): JsObject =
      Json.obj(
        "relid" -> o.relid,
        "indexrelid" -> o.indexrelid,
        "schemaname" -> o.schemaname,
        "relname" -> o.relname,
        "indexrelname" -> o.indexrelname,
        "idx_scan" -> o.idxScan,
        "idx_tup_read" -> o.idxTupRead,
        "idx_tup_fetch" -> o.idxTupFetch
      )
  
    override def reads(json: JsValue): JsResult[PgStatAllIndexesViewRow] = {
      JsResult.fromTry(
        Try(
          PgStatAllIndexesViewRow(
            relid = json.\("relid").toOption.map(_.as[/* oid */ Long]),
            indexrelid = json.\("indexrelid").toOption.map(_.as[/* oid */ Long]),
            schemaname = json.\("schemaname").toOption.map(_.as[String]),
            relname = json.\("relname").toOption.map(_.as[String]),
            indexrelname = json.\("indexrelname").toOption.map(_.as[String]),
            idxScan = json.\("idx_scan").toOption.map(_.as[Long]),
            idxTupRead = json.\("idx_tup_read").toOption.map(_.as[Long]),
            idxTupFetch = json.\("idx_tup_fetch").toOption.map(_.as[Long])
          )
        )
      )
    }
  }
}
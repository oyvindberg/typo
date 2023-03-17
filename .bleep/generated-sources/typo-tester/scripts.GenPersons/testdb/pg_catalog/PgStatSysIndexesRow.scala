package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgStatSysIndexesRow(
  /** Points to [[testdb.pg_catalog.PgStatAllIndexesRow.relid]] */
  relid: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatAllIndexesRow.indexrelid]] */
  indexrelid: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatAllIndexesRow.schemaname]] */
  schemaname: Option[String],
  /** Points to [[testdb.pg_catalog.PgStatAllIndexesRow.relname]] */
  relname: Option[String],
  /** Points to [[testdb.pg_catalog.PgStatAllIndexesRow.indexrelname]] */
  indexrelname: Option[String],
  /** Points to [[testdb.pg_catalog.PgStatAllIndexesRow.idxScan]] */
  idxScan: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatAllIndexesRow.idxTupRead]] */
  idxTupRead: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatAllIndexesRow.idxTupFetch]] */
  idxTupFetch: Option[Long]
)

object PgStatSysIndexesRow {
  implicit val rowParser: RowParser[PgStatSysIndexesRow] = { row =>
    Success(
      PgStatSysIndexesRow(
        relid = row[Option[Long]]("relid"),
        indexrelid = row[Option[Long]]("indexrelid"),
        schemaname = row[Option[String]]("schemaname"),
        relname = row[Option[String]]("relname"),
        indexrelname = row[Option[String]]("indexrelname"),
        idxScan = row[Option[Long]]("idx_scan"),
        idxTupRead = row[Option[Long]]("idx_tup_read"),
        idxTupFetch = row[Option[Long]]("idx_tup_fetch")
      )
    )
  }

  implicit val oFormat: OFormat[PgStatSysIndexesRow] = new OFormat[PgStatSysIndexesRow]{
    override def writes(o: PgStatSysIndexesRow): JsObject =
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

    override def reads(json: JsValue): JsResult[PgStatSysIndexesRow] = {
      JsResult.fromTry(
        Try(
          PgStatSysIndexesRow(
            relid = json.\("relid").toOption.map(_.as[Long]),
            indexrelid = json.\("indexrelid").toOption.map(_.as[Long]),
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

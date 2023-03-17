package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgStatAllIndexesRow(
  /** Points to [[testdb.pg_catalog.PgClassRow.oid]] */
  relid: Long,
  /** Points to [[testdb.pg_catalog.PgClassRow.oid]] */
  indexrelid: Long,
  /** Points to [[testdb.pg_catalog.PgNamespaceRow.nspname]] */
  schemaname: String,
  /** Points to [[testdb.pg_catalog.PgClassRow.relname]] */
  relname: String,
  /** Points to [[testdb.pg_catalog.PgClassRow.relname]] */
  indexrelname: String,
  idxScan: /* unknown nullability */ Option[Long],
  idxTupRead: /* unknown nullability */ Option[Long],
  idxTupFetch: /* unknown nullability */ Option[Long]
)

object PgStatAllIndexesRow {
  implicit val rowParser: RowParser[PgStatAllIndexesRow] = { row =>
    Success(
      PgStatAllIndexesRow(
        relid = row[Long]("relid"),
        indexrelid = row[Long]("indexrelid"),
        schemaname = row[String]("schemaname"),
        relname = row[String]("relname"),
        indexrelname = row[String]("indexrelname"),
        idxScan = row[/* unknown nullability */ Option[Long]]("idx_scan"),
        idxTupRead = row[/* unknown nullability */ Option[Long]]("idx_tup_read"),
        idxTupFetch = row[/* unknown nullability */ Option[Long]]("idx_tup_fetch")
      )
    )
  }

  implicit val oFormat: OFormat[PgStatAllIndexesRow] = new OFormat[PgStatAllIndexesRow]{
    override def writes(o: PgStatAllIndexesRow): JsObject =
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

    override def reads(json: JsValue): JsResult[PgStatAllIndexesRow] = {
      JsResult.fromTry(
        Try(
          PgStatAllIndexesRow(
            relid = json.\("relid").as[Long],
            indexrelid = json.\("indexrelid").as[Long],
            schemaname = json.\("schemaname").as[String],
            relname = json.\("relname").as[String],
            indexrelname = json.\("indexrelname").as[String],
            idxScan = json.\("idx_scan").toOption.map(_.as[Long]),
            idxTupRead = json.\("idx_tup_read").toOption.map(_.as[Long]),
            idxTupFetch = json.\("idx_tup_fetch").toOption.map(_.as[Long])
          )
        )
      )
    }
  }
}

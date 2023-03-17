package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgStatUserIndexesRow(
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

object PgStatUserIndexesRow {
  implicit val rowParser: RowParser[PgStatUserIndexesRow] = { row =>
    Success(
      PgStatUserIndexesRow(
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

  implicit val oFormat: OFormat[PgStatUserIndexesRow] = Json.format
}

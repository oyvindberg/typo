package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgStatSysIndexesRow(
  relid: Option[Long],
  indexrelid: Option[Long],
  schemaname: Option[String],
  relname: Option[String],
  indexrelname: Option[String],
  idxScan: Option[Long],
  idxTupRead: Option[Long],
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

  implicit val oFormat: OFormat[PgStatSysIndexesRow] = Json.format
}

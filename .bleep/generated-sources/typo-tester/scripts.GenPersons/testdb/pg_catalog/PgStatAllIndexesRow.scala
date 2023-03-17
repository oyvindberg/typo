package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgStatAllIndexesRow(
  relid: Long,
  indexrelid: Long,
  schemaname: String,
  relname: String,
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

  implicit val oFormat: OFormat[PgStatAllIndexesRow] = Json.format
}

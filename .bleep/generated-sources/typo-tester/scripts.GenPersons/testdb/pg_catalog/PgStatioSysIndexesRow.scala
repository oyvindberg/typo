package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgStatioSysIndexesRow(
  /** Points to [[testdb.pg_catalog.PgStatioAllIndexesRow.relid]] */
  relid: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatioAllIndexesRow.indexrelid]] */
  indexrelid: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatioAllIndexesRow.schemaname]] */
  schemaname: Option[String],
  /** Points to [[testdb.pg_catalog.PgStatioAllIndexesRow.relname]] */
  relname: Option[String],
  /** Points to [[testdb.pg_catalog.PgStatioAllIndexesRow.indexrelname]] */
  indexrelname: Option[String],
  /** Points to [[testdb.pg_catalog.PgStatioAllIndexesRow.idxBlksRead]] */
  idxBlksRead: Option[Long],
  /** Points to [[testdb.pg_catalog.PgStatioAllIndexesRow.idxBlksHit]] */
  idxBlksHit: Option[Long]
)

object PgStatioSysIndexesRow {
  implicit val rowParser: RowParser[PgStatioSysIndexesRow] = { row =>
    Success(
      PgStatioSysIndexesRow(
        relid = row[Option[Long]]("relid"),
        indexrelid = row[Option[Long]]("indexrelid"),
        schemaname = row[Option[String]]("schemaname"),
        relname = row[Option[String]]("relname"),
        indexrelname = row[Option[String]]("indexrelname"),
        idxBlksRead = row[Option[Long]]("idx_blks_read"),
        idxBlksHit = row[Option[Long]]("idx_blks_hit")
      )
    )
  }

  implicit val oFormat: OFormat[PgStatioSysIndexesRow] = Json.format
}

package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgStatioAllIndexesRow(
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
  idxBlksRead: /* unknown nullability */ Option[Long],
  idxBlksHit: /* unknown nullability */ Option[Long]
)

object PgStatioAllIndexesRow {
  implicit val rowParser: RowParser[PgStatioAllIndexesRow] = { row =>
    Success(
      PgStatioAllIndexesRow(
        relid = row[Long]("relid"),
        indexrelid = row[Long]("indexrelid"),
        schemaname = row[String]("schemaname"),
        relname = row[String]("relname"),
        indexrelname = row[String]("indexrelname"),
        idxBlksRead = row[/* unknown nullability */ Option[Long]]("idx_blks_read"),
        idxBlksHit = row[/* unknown nullability */ Option[Long]]("idx_blks_hit")
      )
    )
  }

  implicit val oFormat: OFormat[PgStatioAllIndexesRow] = Json.format
}

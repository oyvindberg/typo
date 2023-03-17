package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgStatioAllIndexesRow(
  relid: Long,
  indexrelid: Long,
  schemaname: String,
  relname: String,
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

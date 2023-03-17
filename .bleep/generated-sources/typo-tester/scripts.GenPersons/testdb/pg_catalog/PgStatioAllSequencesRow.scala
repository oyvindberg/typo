package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgStatioAllSequencesRow(
  relid: Long,
  schemaname: String,
  relname: String,
  blksRead: /* unknown nullability */ Option[Long],
  blksHit: /* unknown nullability */ Option[Long]
)

object PgStatioAllSequencesRow {
  implicit val rowParser: RowParser[PgStatioAllSequencesRow] = { row =>
    Success(
      PgStatioAllSequencesRow(
        relid = row[Long]("relid"),
        schemaname = row[String]("schemaname"),
        relname = row[String]("relname"),
        blksRead = row[/* unknown nullability */ Option[Long]]("blks_read"),
        blksHit = row[/* unknown nullability */ Option[Long]]("blks_hit")
      )
    )
  }

  implicit val oFormat: OFormat[PgStatioAllSequencesRow] = Json.format
}

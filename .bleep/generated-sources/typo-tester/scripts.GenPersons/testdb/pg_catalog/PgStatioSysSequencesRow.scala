package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgStatioSysSequencesRow(
  relid: Option[Long],
  schemaname: Option[String],
  relname: Option[String],
  blksRead: Option[Long],
  blksHit: Option[Long]
)

object PgStatioSysSequencesRow {
  implicit val rowParser: RowParser[PgStatioSysSequencesRow] = { row =>
    Success(
      PgStatioSysSequencesRow(
        relid = row[Option[Long]]("relid"),
        schemaname = row[Option[String]]("schemaname"),
        relname = row[Option[String]]("relname"),
        blksRead = row[Option[Long]]("blks_read"),
        blksHit = row[Option[Long]]("blks_hit")
      )
    )
  }

  implicit val oFormat: OFormat[PgStatioSysSequencesRow] = Json.format
}

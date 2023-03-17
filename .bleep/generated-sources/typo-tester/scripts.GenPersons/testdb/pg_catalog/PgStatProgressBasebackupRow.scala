package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgStatProgressBasebackupRow(
  pid: /* unknown nullability */ Option[Int],
  phase: /* unknown nullability */ Option[String],
  backupTotal: /* unknown nullability */ Option[Long],
  backupStreamed: /* unknown nullability */ Option[Long],
  tablespacesTotal: /* unknown nullability */ Option[Long],
  tablespacesStreamed: /* unknown nullability */ Option[Long]
)

object PgStatProgressBasebackupRow {
  implicit val rowParser: RowParser[PgStatProgressBasebackupRow] = { row =>
    Success(
      PgStatProgressBasebackupRow(
        pid = row[/* unknown nullability */ Option[Int]]("pid"),
        phase = row[/* unknown nullability */ Option[String]]("phase"),
        backupTotal = row[/* unknown nullability */ Option[Long]]("backup_total"),
        backupStreamed = row[/* unknown nullability */ Option[Long]]("backup_streamed"),
        tablespacesTotal = row[/* unknown nullability */ Option[Long]]("tablespaces_total"),
        tablespacesStreamed = row[/* unknown nullability */ Option[Long]]("tablespaces_streamed")
      )
    )
  }

  implicit val oFormat: OFormat[PgStatProgressBasebackupRow] = Json.format
}

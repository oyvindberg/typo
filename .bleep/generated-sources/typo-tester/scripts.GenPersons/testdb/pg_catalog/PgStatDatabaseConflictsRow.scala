package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgStatDatabaseConflictsRow(
  datid: Long,
  datname: String,
  conflTablespace: /* unknown nullability */ Option[Long],
  conflLock: /* unknown nullability */ Option[Long],
  conflSnapshot: /* unknown nullability */ Option[Long],
  conflBufferpin: /* unknown nullability */ Option[Long],
  conflDeadlock: /* unknown nullability */ Option[Long]
)

object PgStatDatabaseConflictsRow {
  implicit val rowParser: RowParser[PgStatDatabaseConflictsRow] = { row =>
    Success(
      PgStatDatabaseConflictsRow(
        datid = row[Long]("datid"),
        datname = row[String]("datname"),
        conflTablespace = row[/* unknown nullability */ Option[Long]]("confl_tablespace"),
        conflLock = row[/* unknown nullability */ Option[Long]]("confl_lock"),
        conflSnapshot = row[/* unknown nullability */ Option[Long]]("confl_snapshot"),
        conflBufferpin = row[/* unknown nullability */ Option[Long]]("confl_bufferpin"),
        conflDeadlock = row[/* unknown nullability */ Option[Long]]("confl_deadlock")
      )
    )
  }

  implicit val oFormat: OFormat[PgStatDatabaseConflictsRow] = Json.format
}

package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgStatProgressCopyRow(
  pid: /* unknown nullability */ Option[Int],
  datid: /* unknown nullability */ Option[Long],
  datname: String,
  relid: /* unknown nullability */ Option[Long],
  command: /* unknown nullability */ Option[String],
  `type`: /* unknown nullability */ Option[String],
  bytesProcessed: /* unknown nullability */ Option[Long],
  bytesTotal: /* unknown nullability */ Option[Long],
  tuplesProcessed: /* unknown nullability */ Option[Long],
  tuplesExcluded: /* unknown nullability */ Option[Long]
)

object PgStatProgressCopyRow {
  implicit val rowParser: RowParser[PgStatProgressCopyRow] = { row =>
    Success(
      PgStatProgressCopyRow(
        pid = row[/* unknown nullability */ Option[Int]]("pid"),
        datid = row[/* unknown nullability */ Option[Long]]("datid"),
        datname = row[String]("datname"),
        relid = row[/* unknown nullability */ Option[Long]]("relid"),
        command = row[/* unknown nullability */ Option[String]]("command"),
        `type` = row[/* unknown nullability */ Option[String]]("type"),
        bytesProcessed = row[/* unknown nullability */ Option[Long]]("bytes_processed"),
        bytesTotal = row[/* unknown nullability */ Option[Long]]("bytes_total"),
        tuplesProcessed = row[/* unknown nullability */ Option[Long]]("tuples_processed"),
        tuplesExcluded = row[/* unknown nullability */ Option[Long]]("tuples_excluded")
      )
    )
  }

  implicit val oFormat: OFormat[PgStatProgressCopyRow] = Json.format
}

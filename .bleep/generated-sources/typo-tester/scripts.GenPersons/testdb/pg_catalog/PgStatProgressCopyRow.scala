package testdb
package pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgStatProgressCopyRow(
  pid: /* unknown nullability */ Option[Int],
  datid: /* unknown nullability */ Option[Long],
  /** Points to [[testdb.pg_catalog.PgDatabaseRow.datname]] */
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

  implicit val oFormat: OFormat[PgStatProgressCopyRow] = new OFormat[PgStatProgressCopyRow]{
    override def writes(o: PgStatProgressCopyRow): JsObject =
      Json.obj(
        "pid" -> o.pid,
      "datid" -> o.datid,
      "datname" -> o.datname,
      "relid" -> o.relid,
      "command" -> o.command,
      "type" -> o.`type`,
      "bytes_processed" -> o.bytesProcessed,
      "bytes_total" -> o.bytesTotal,
      "tuples_processed" -> o.tuplesProcessed,
      "tuples_excluded" -> o.tuplesExcluded
      )

    override def reads(json: JsValue): JsResult[PgStatProgressCopyRow] = {
      JsResult.fromTry(
        Try(
          PgStatProgressCopyRow(
            pid = json.\("pid").toOption.map(_.as[Int]),
            datid = json.\("datid").toOption.map(_.as[Long]),
            datname = json.\("datname").as[String],
            relid = json.\("relid").toOption.map(_.as[Long]),
            command = json.\("command").toOption.map(_.as[String]),
            `type` = json.\("type").toOption.map(_.as[String]),
            bytesProcessed = json.\("bytes_processed").toOption.map(_.as[Long]),
            bytesTotal = json.\("bytes_total").toOption.map(_.as[Long]),
            tuplesProcessed = json.\("tuples_processed").toOption.map(_.as[Long]),
            tuplesExcluded = json.\("tuples_excluded").toOption.map(_.as[Long])
          )
        )
      )
    }
  }
}

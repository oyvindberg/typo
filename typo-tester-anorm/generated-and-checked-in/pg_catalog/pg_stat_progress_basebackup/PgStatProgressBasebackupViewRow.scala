/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_stat_progress_basebackup

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgStatProgressBasebackupViewRow(
  pid: Option[Int],
  phase: Option[String],
  backupTotal: Option[Long],
  backupStreamed: Option[Long],
  tablespacesTotal: Option[Long],
  tablespacesStreamed: Option[Long]
)

object PgStatProgressBasebackupViewRow {
  def rowParser(idx: Int): RowParser[PgStatProgressBasebackupViewRow] =
    RowParser[PgStatProgressBasebackupViewRow] { row =>
      Success(
        PgStatProgressBasebackupViewRow(
          pid = row[Option[Int]](idx + 0),
          phase = row[Option[String]](idx + 1),
          backupTotal = row[Option[Long]](idx + 2),
          backupStreamed = row[Option[Long]](idx + 3),
          tablespacesTotal = row[Option[Long]](idx + 4),
          tablespacesStreamed = row[Option[Long]](idx + 5)
        )
      )
    }
  implicit val oFormat: OFormat[PgStatProgressBasebackupViewRow] = new OFormat[PgStatProgressBasebackupViewRow]{
    override def writes(o: PgStatProgressBasebackupViewRow): JsObject =
      Json.obj(
        "pid" -> o.pid,
        "phase" -> o.phase,
        "backup_total" -> o.backupTotal,
        "backup_streamed" -> o.backupStreamed,
        "tablespaces_total" -> o.tablespacesTotal,
        "tablespaces_streamed" -> o.tablespacesStreamed
      )
  
    override def reads(json: JsValue): JsResult[PgStatProgressBasebackupViewRow] = {
      JsResult.fromTry(
        Try(
          PgStatProgressBasebackupViewRow(
            pid = json.\("pid").toOption.map(_.as[Int]),
            phase = json.\("phase").toOption.map(_.as[String]),
            backupTotal = json.\("backup_total").toOption.map(_.as[Long]),
            backupStreamed = json.\("backup_streamed").toOption.map(_.as[Long]),
            tablespacesTotal = json.\("tablespaces_total").toOption.map(_.as[Long]),
            tablespacesStreamed = json.\("tablespaces_streamed").toOption.map(_.as[Long])
          )
        )
      )
    }
  }
}
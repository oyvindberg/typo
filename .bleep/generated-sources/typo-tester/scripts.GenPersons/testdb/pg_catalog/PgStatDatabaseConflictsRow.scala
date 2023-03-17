package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgStatDatabaseConflictsRow(
  /** Points to [[testdb.pg_catalog.PgDatabaseRow.oid]] */
  datid: Long,
  /** Points to [[testdb.pg_catalog.PgDatabaseRow.datname]] */
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

  implicit val oFormat: OFormat[PgStatDatabaseConflictsRow] = new OFormat[PgStatDatabaseConflictsRow]{
    override def writes(o: PgStatDatabaseConflictsRow): JsObject =
      Json.obj(
        "datid" -> o.datid,
      "datname" -> o.datname,
      "confl_tablespace" -> o.conflTablespace,
      "confl_lock" -> o.conflLock,
      "confl_snapshot" -> o.conflSnapshot,
      "confl_bufferpin" -> o.conflBufferpin,
      "confl_deadlock" -> o.conflDeadlock
      )

    override def reads(json: JsValue): JsResult[PgStatDatabaseConflictsRow] = {
      JsResult.fromTry(
        Try(
          PgStatDatabaseConflictsRow(
            datid = json.\("datid").as[Long],
            datname = json.\("datname").as[String],
            conflTablespace = json.\("confl_tablespace").toOption.map(_.as[Long]),
            conflLock = json.\("confl_lock").toOption.map(_.as[Long]),
            conflSnapshot = json.\("confl_snapshot").toOption.map(_.as[Long]),
            conflBufferpin = json.\("confl_bufferpin").toOption.map(_.as[Long]),
            conflDeadlock = json.\("confl_deadlock").toOption.map(_.as[Long])
          )
        )
      )
    }
  }
}

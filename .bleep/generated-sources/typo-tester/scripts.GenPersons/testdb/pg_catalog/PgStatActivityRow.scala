package testdb
package pg_catalog

import anorm.RowParser
import anorm.Success
import java.time.LocalDateTime
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgStatActivityRow(
  datid: /* unknown nullability */ Option[Long],
  /** Points to [[testdb.pg_catalog.PgDatabaseRow.datname]] */
  datname: String,
  pid: /* unknown nullability */ Option[Int],
  leaderPid: /* unknown nullability */ Option[Int],
  usesysid: /* unknown nullability */ Option[Long],
  /** Points to [[testdb.pg_catalog.PgAuthidRow.rolname]] */
  usename: String,
  applicationName: /* unknown nullability */ Option[String],
  clientAddr: /* unknown nullability */ Option[/* inet */ String],
  clientHostname: /* unknown nullability */ Option[String],
  clientPort: /* unknown nullability */ Option[Int],
  backendStart: /* unknown nullability */ Option[LocalDateTime],
  xactStart: /* unknown nullability */ Option[LocalDateTime],
  queryStart: /* unknown nullability */ Option[LocalDateTime],
  stateChange: /* unknown nullability */ Option[LocalDateTime],
  waitEventType: /* unknown nullability */ Option[String],
  waitEvent: /* unknown nullability */ Option[String],
  state: /* unknown nullability */ Option[String],
  backendXid: /* unknown nullability */ Option[/* xid */ String],
  backendXmin: /* unknown nullability */ Option[/* xid */ String],
  queryId: /* unknown nullability */ Option[Long],
  query: /* unknown nullability */ Option[String],
  backendType: /* unknown nullability */ Option[String]
)

object PgStatActivityRow {
  implicit val rowParser: RowParser[PgStatActivityRow] = { row =>
    Success(
      PgStatActivityRow(
        datid = row[/* unknown nullability */ Option[Long]]("datid"),
        datname = row[String]("datname"),
        pid = row[/* unknown nullability */ Option[Int]]("pid"),
        leaderPid = row[/* unknown nullability */ Option[Int]]("leader_pid"),
        usesysid = row[/* unknown nullability */ Option[Long]]("usesysid"),
        usename = row[String]("usename"),
        applicationName = row[/* unknown nullability */ Option[String]]("application_name"),
        clientAddr = row[/* unknown nullability */ Option[/* inet */ String]]("client_addr"),
        clientHostname = row[/* unknown nullability */ Option[String]]("client_hostname"),
        clientPort = row[/* unknown nullability */ Option[Int]]("client_port"),
        backendStart = row[/* unknown nullability */ Option[LocalDateTime]]("backend_start"),
        xactStart = row[/* unknown nullability */ Option[LocalDateTime]]("xact_start"),
        queryStart = row[/* unknown nullability */ Option[LocalDateTime]]("query_start"),
        stateChange = row[/* unknown nullability */ Option[LocalDateTime]]("state_change"),
        waitEventType = row[/* unknown nullability */ Option[String]]("wait_event_type"),
        waitEvent = row[/* unknown nullability */ Option[String]]("wait_event"),
        state = row[/* unknown nullability */ Option[String]]("state"),
        backendXid = row[/* unknown nullability */ Option[/* xid */ String]]("backend_xid"),
        backendXmin = row[/* unknown nullability */ Option[/* xid */ String]]("backend_xmin"),
        queryId = row[/* unknown nullability */ Option[Long]]("query_id"),
        query = row[/* unknown nullability */ Option[String]]("query"),
        backendType = row[/* unknown nullability */ Option[String]]("backend_type")
      )
    )
  }

  implicit val oFormat: OFormat[PgStatActivityRow] = new OFormat[PgStatActivityRow]{
    override def writes(o: PgStatActivityRow): JsObject =
      Json.obj(
        "datid" -> o.datid,
      "datname" -> o.datname,
      "pid" -> o.pid,
      "leader_pid" -> o.leaderPid,
      "usesysid" -> o.usesysid,
      "usename" -> o.usename,
      "application_name" -> o.applicationName,
      "client_addr" -> o.clientAddr,
      "client_hostname" -> o.clientHostname,
      "client_port" -> o.clientPort,
      "backend_start" -> o.backendStart,
      "xact_start" -> o.xactStart,
      "query_start" -> o.queryStart,
      "state_change" -> o.stateChange,
      "wait_event_type" -> o.waitEventType,
      "wait_event" -> o.waitEvent,
      "state" -> o.state,
      "backend_xid" -> o.backendXid,
      "backend_xmin" -> o.backendXmin,
      "query_id" -> o.queryId,
      "query" -> o.query,
      "backend_type" -> o.backendType
      )

    override def reads(json: JsValue): JsResult[PgStatActivityRow] = {
      JsResult.fromTry(
        Try(
          PgStatActivityRow(
            datid = json.\("datid").toOption.map(_.as[Long]),
            datname = json.\("datname").as[String],
            pid = json.\("pid").toOption.map(_.as[Int]),
            leaderPid = json.\("leader_pid").toOption.map(_.as[Int]),
            usesysid = json.\("usesysid").toOption.map(_.as[Long]),
            usename = json.\("usename").as[String],
            applicationName = json.\("application_name").toOption.map(_.as[String]),
            clientAddr = json.\("client_addr").toOption.map(_.as[/* inet */ String]),
            clientHostname = json.\("client_hostname").toOption.map(_.as[String]),
            clientPort = json.\("client_port").toOption.map(_.as[Int]),
            backendStart = json.\("backend_start").toOption.map(_.as[LocalDateTime]),
            xactStart = json.\("xact_start").toOption.map(_.as[LocalDateTime]),
            queryStart = json.\("query_start").toOption.map(_.as[LocalDateTime]),
            stateChange = json.\("state_change").toOption.map(_.as[LocalDateTime]),
            waitEventType = json.\("wait_event_type").toOption.map(_.as[String]),
            waitEvent = json.\("wait_event").toOption.map(_.as[String]),
            state = json.\("state").toOption.map(_.as[String]),
            backendXid = json.\("backend_xid").toOption.map(_.as[/* xid */ String]),
            backendXmin = json.\("backend_xmin").toOption.map(_.as[/* xid */ String]),
            queryId = json.\("query_id").toOption.map(_.as[Long]),
            query = json.\("query").toOption.map(_.as[String]),
            backendType = json.\("backend_type").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}

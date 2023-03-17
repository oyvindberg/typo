package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import java.time.LocalDateTime
import org.postgresql.util.PGInterval
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgStatReplicationRow(
  pid: /* unknown nullability */ Option[Int],
  usesysid: /* unknown nullability */ Option[Long],
  usename: String,
  applicationName: /* unknown nullability */ Option[String],
  clientAddr: /* unknown nullability */ Option[/* inet */ String],
  clientHostname: /* unknown nullability */ Option[String],
  clientPort: /* unknown nullability */ Option[Int],
  backendStart: /* unknown nullability */ Option[LocalDateTime],
  backendXmin: /* unknown nullability */ Option[/* xid */ String],
  state: /* unknown nullability */ Option[String],
  sentLsn: /* unknown nullability */ Option[/* pg_lsn */ String],
  writeLsn: /* unknown nullability */ Option[/* pg_lsn */ String],
  flushLsn: /* unknown nullability */ Option[/* pg_lsn */ String],
  replayLsn: /* unknown nullability */ Option[/* pg_lsn */ String],
  writeLag: /* unknown nullability */ Option[/* interval */ PGInterval],
  flushLag: /* unknown nullability */ Option[/* interval */ PGInterval],
  replayLag: /* unknown nullability */ Option[/* interval */ PGInterval],
  syncPriority: /* unknown nullability */ Option[Int],
  syncState: /* unknown nullability */ Option[String],
  replyTime: /* unknown nullability */ Option[LocalDateTime]
)

object PgStatReplicationRow {
  implicit val rowParser: RowParser[PgStatReplicationRow] = { row =>
    Success(
      PgStatReplicationRow(
        pid = row[/* unknown nullability */ Option[Int]]("pid"),
        usesysid = row[/* unknown nullability */ Option[Long]]("usesysid"),
        usename = row[String]("usename"),
        applicationName = row[/* unknown nullability */ Option[String]]("application_name"),
        clientAddr = row[/* unknown nullability */ Option[/* inet */ String]]("client_addr"),
        clientHostname = row[/* unknown nullability */ Option[String]]("client_hostname"),
        clientPort = row[/* unknown nullability */ Option[Int]]("client_port"),
        backendStart = row[/* unknown nullability */ Option[LocalDateTime]]("backend_start"),
        backendXmin = row[/* unknown nullability */ Option[/* xid */ String]]("backend_xmin"),
        state = row[/* unknown nullability */ Option[String]]("state"),
        sentLsn = row[/* unknown nullability */ Option[/* pg_lsn */ String]]("sent_lsn"),
        writeLsn = row[/* unknown nullability */ Option[/* pg_lsn */ String]]("write_lsn"),
        flushLsn = row[/* unknown nullability */ Option[/* pg_lsn */ String]]("flush_lsn"),
        replayLsn = row[/* unknown nullability */ Option[/* pg_lsn */ String]]("replay_lsn"),
        writeLag = row[/* unknown nullability */ Option[/* interval */ PGInterval]]("write_lag"),
        flushLag = row[/* unknown nullability */ Option[/* interval */ PGInterval]]("flush_lag"),
        replayLag = row[/* unknown nullability */ Option[/* interval */ PGInterval]]("replay_lag"),
        syncPriority = row[/* unknown nullability */ Option[Int]]("sync_priority"),
        syncState = row[/* unknown nullability */ Option[String]]("sync_state"),
        replyTime = row[/* unknown nullability */ Option[LocalDateTime]]("reply_time")
      )
    )
  }

  implicit val oFormat: OFormat[PgStatReplicationRow] = Json.format
}

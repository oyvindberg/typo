package testdb.pg_catalog

import java.time.LocalDateTime
import org.postgresql.util.PGInterval

sealed abstract class PgStatReplicationFieldValue[T](val name: String, val value: T)

object PgStatReplicationFieldValue {
  case class pid(override val value: /* unknown nullability */ Option[Int]) extends PgStatReplicationFieldValue("pid", value)
  case class usesysid(override val value: /* unknown nullability */ Option[Long]) extends PgStatReplicationFieldValue("usesysid", value)
  case class usename(override val value: String) extends PgStatReplicationFieldValue("usename", value)
  case class applicationName(override val value: /* unknown nullability */ Option[String]) extends PgStatReplicationFieldValue("application_name", value)
  case class clientAddr(override val value: /* unknown nullability */ Option[/* inet */ String]) extends PgStatReplicationFieldValue("client_addr", value)
  case class clientHostname(override val value: /* unknown nullability */ Option[String]) extends PgStatReplicationFieldValue("client_hostname", value)
  case class clientPort(override val value: /* unknown nullability */ Option[Int]) extends PgStatReplicationFieldValue("client_port", value)
  case class backendStart(override val value: /* unknown nullability */ Option[LocalDateTime]) extends PgStatReplicationFieldValue("backend_start", value)
  case class backendXmin(override val value: /* unknown nullability */ Option[/* xid */ String]) extends PgStatReplicationFieldValue("backend_xmin", value)
  case class state(override val value: /* unknown nullability */ Option[String]) extends PgStatReplicationFieldValue("state", value)
  case class sentLsn(override val value: /* unknown nullability */ Option[/* pg_lsn */ String]) extends PgStatReplicationFieldValue("sent_lsn", value)
  case class writeLsn(override val value: /* unknown nullability */ Option[/* pg_lsn */ String]) extends PgStatReplicationFieldValue("write_lsn", value)
  case class flushLsn(override val value: /* unknown nullability */ Option[/* pg_lsn */ String]) extends PgStatReplicationFieldValue("flush_lsn", value)
  case class replayLsn(override val value: /* unknown nullability */ Option[/* pg_lsn */ String]) extends PgStatReplicationFieldValue("replay_lsn", value)
  case class writeLag(override val value: /* unknown nullability */ Option[/* interval */ PGInterval]) extends PgStatReplicationFieldValue("write_lag", value)
  case class flushLag(override val value: /* unknown nullability */ Option[/* interval */ PGInterval]) extends PgStatReplicationFieldValue("flush_lag", value)
  case class replayLag(override val value: /* unknown nullability */ Option[/* interval */ PGInterval]) extends PgStatReplicationFieldValue("replay_lag", value)
  case class syncPriority(override val value: /* unknown nullability */ Option[Int]) extends PgStatReplicationFieldValue("sync_priority", value)
  case class syncState(override val value: /* unknown nullability */ Option[String]) extends PgStatReplicationFieldValue("sync_state", value)
  case class replyTime(override val value: /* unknown nullability */ Option[LocalDateTime]) extends PgStatReplicationFieldValue("reply_time", value)
}

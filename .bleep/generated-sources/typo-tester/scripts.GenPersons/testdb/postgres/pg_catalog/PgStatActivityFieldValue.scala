package testdb
package postgres
package pg_catalog

import java.time.LocalDateTime

sealed abstract class PgStatActivityFieldValue[T](val name: String, val value: T)

object PgStatActivityFieldValue {
  case class datid(override val value: /* unknown nullability */ Option[Long]) extends PgStatActivityFieldValue("datid", value)
  case class datname(override val value: String) extends PgStatActivityFieldValue("datname", value)
  case class pid(override val value: /* unknown nullability */ Option[Int]) extends PgStatActivityFieldValue("pid", value)
  case class leaderPid(override val value: /* unknown nullability */ Option[Int]) extends PgStatActivityFieldValue("leader_pid", value)
  case class usesysid(override val value: /* unknown nullability */ Option[Long]) extends PgStatActivityFieldValue("usesysid", value)
  case class usename(override val value: String) extends PgStatActivityFieldValue("usename", value)
  case class applicationName(override val value: /* unknown nullability */ Option[String]) extends PgStatActivityFieldValue("application_name", value)
  case class clientAddr(override val value: /* unknown nullability */ Option[/* inet */ String]) extends PgStatActivityFieldValue("client_addr", value)
  case class clientHostname(override val value: /* unknown nullability */ Option[String]) extends PgStatActivityFieldValue("client_hostname", value)
  case class clientPort(override val value: /* unknown nullability */ Option[Int]) extends PgStatActivityFieldValue("client_port", value)
  case class backendStart(override val value: /* unknown nullability */ Option[LocalDateTime]) extends PgStatActivityFieldValue("backend_start", value)
  case class xactStart(override val value: /* unknown nullability */ Option[LocalDateTime]) extends PgStatActivityFieldValue("xact_start", value)
  case class queryStart(override val value: /* unknown nullability */ Option[LocalDateTime]) extends PgStatActivityFieldValue("query_start", value)
  case class stateChange(override val value: /* unknown nullability */ Option[LocalDateTime]) extends PgStatActivityFieldValue("state_change", value)
  case class waitEventType(override val value: /* unknown nullability */ Option[String]) extends PgStatActivityFieldValue("wait_event_type", value)
  case class waitEvent(override val value: /* unknown nullability */ Option[String]) extends PgStatActivityFieldValue("wait_event", value)
  case class state(override val value: /* unknown nullability */ Option[String]) extends PgStatActivityFieldValue("state", value)
  case class backendXid(override val value: /* unknown nullability */ Option[/* xid */ String]) extends PgStatActivityFieldValue("backend_xid", value)
  case class backendXmin(override val value: /* unknown nullability */ Option[/* xid */ String]) extends PgStatActivityFieldValue("backend_xmin", value)
  case class queryId(override val value: /* unknown nullability */ Option[Long]) extends PgStatActivityFieldValue("query_id", value)
  case class query(override val value: /* unknown nullability */ Option[String]) extends PgStatActivityFieldValue("query", value)
  case class backendType(override val value: /* unknown nullability */ Option[String]) extends PgStatActivityFieldValue("backend_type", value)
}

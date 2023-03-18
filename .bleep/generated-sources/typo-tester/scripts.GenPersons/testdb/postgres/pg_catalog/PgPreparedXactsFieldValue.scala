package testdb
package postgres
package pg_catalog

import java.time.LocalDateTime

sealed abstract class PgPreparedXactsFieldValue[T](val name: String, val value: T)

object PgPreparedXactsFieldValue {
  case class transaction(override val value: /* unknown nullability */ Option[/* xid */ String]) extends PgPreparedXactsFieldValue("transaction", value)
  case class gid(override val value: /* unknown nullability */ Option[String]) extends PgPreparedXactsFieldValue("gid", value)
  case class prepared(override val value: /* unknown nullability */ Option[LocalDateTime]) extends PgPreparedXactsFieldValue("prepared", value)
  case class owner(override val value: String) extends PgPreparedXactsFieldValue("owner", value)
  case class database(override val value: String) extends PgPreparedXactsFieldValue("database", value)
}

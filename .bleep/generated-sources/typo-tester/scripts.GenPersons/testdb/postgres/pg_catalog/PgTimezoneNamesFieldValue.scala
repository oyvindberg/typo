package testdb
package postgres
package pg_catalog

import org.postgresql.util.PGInterval

sealed abstract class PgTimezoneNamesFieldValue[T](val name: String, val value: T)

object PgTimezoneNamesFieldValue {
  case class name(override val value: /* unknown nullability */ Option[String]) extends PgTimezoneNamesFieldValue("name", value)
  case class abbrev(override val value: /* unknown nullability */ Option[String]) extends PgTimezoneNamesFieldValue("abbrev", value)
  case class utcOffset(override val value: /* unknown nullability */ Option[/* interval */ PGInterval]) extends PgTimezoneNamesFieldValue("utc_offset", value)
  case class isDst(override val value: /* unknown nullability */ Option[Boolean]) extends PgTimezoneNamesFieldValue("is_dst", value)
}

package testdb
package pg_catalog

import org.postgresql.util.PGInterval

sealed abstract class PgTimezoneAbbrevsFieldValue[T](val name: String, val value: T)

object PgTimezoneAbbrevsFieldValue {
  case class abbrev(override val value: /* unknown nullability */ Option[String]) extends PgTimezoneAbbrevsFieldValue("abbrev", value)
  case class utcOffset(override val value: /* unknown nullability */ Option[/* interval */ PGInterval]) extends PgTimezoneAbbrevsFieldValue("utc_offset", value)
  case class isDst(override val value: /* unknown nullability */ Option[Boolean]) extends PgTimezoneAbbrevsFieldValue("is_dst", value)
}

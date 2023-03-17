package testdb
package pg_catalog

import java.time.LocalDateTime

sealed abstract class PgRolesFieldValue[T](val name: String, val value: T)

object PgRolesFieldValue {
  case class rolname(override val value: String) extends PgRolesFieldValue("rolname", value)
  case class rolsuper(override val value: Boolean) extends PgRolesFieldValue("rolsuper", value)
  case class rolinherit(override val value: Boolean) extends PgRolesFieldValue("rolinherit", value)
  case class rolcreaterole(override val value: Boolean) extends PgRolesFieldValue("rolcreaterole", value)
  case class rolcreatedb(override val value: Boolean) extends PgRolesFieldValue("rolcreatedb", value)
  case class rolcanlogin(override val value: Boolean) extends PgRolesFieldValue("rolcanlogin", value)
  case class rolreplication(override val value: Boolean) extends PgRolesFieldValue("rolreplication", value)
  case class rolconnlimit(override val value: Int) extends PgRolesFieldValue("rolconnlimit", value)
  case class rolpassword(override val value: /* unknown nullability */ Option[String]) extends PgRolesFieldValue("rolpassword", value)
  case class rolvaliduntil(override val value: Option[LocalDateTime]) extends PgRolesFieldValue("rolvaliduntil", value)
  case class rolbypassrls(override val value: Boolean) extends PgRolesFieldValue("rolbypassrls", value)
  case class rolconfig(override val value: Option[Array[String]]) extends PgRolesFieldValue("rolconfig", value)
  case class oid(override val value: Long) extends PgRolesFieldValue("oid", value)
}

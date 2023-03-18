package testdb
package postgres
package pg_catalog

import java.time.ZonedDateTime

sealed abstract class PgAuthidFieldValue[T](val name: String, val value: T)

object PgAuthidFieldValue {
  case class oid(override val value: PgAuthidId) extends PgAuthidFieldValue("oid", value)
  case class rolname(override val value: String) extends PgAuthidFieldValue("rolname", value)
  case class rolsuper(override val value: Boolean) extends PgAuthidFieldValue("rolsuper", value)
  case class rolinherit(override val value: Boolean) extends PgAuthidFieldValue("rolinherit", value)
  case class rolcreaterole(override val value: Boolean) extends PgAuthidFieldValue("rolcreaterole", value)
  case class rolcreatedb(override val value: Boolean) extends PgAuthidFieldValue("rolcreatedb", value)
  case class rolcanlogin(override val value: Boolean) extends PgAuthidFieldValue("rolcanlogin", value)
  case class rolreplication(override val value: Boolean) extends PgAuthidFieldValue("rolreplication", value)
  case class rolbypassrls(override val value: Boolean) extends PgAuthidFieldValue("rolbypassrls", value)
  case class rolconnlimit(override val value: Int) extends PgAuthidFieldValue("rolconnlimit", value)
  case class rolpassword(override val value: Option[String]) extends PgAuthidFieldValue("rolpassword", value)
  case class rolvaliduntil(override val value: Option[ZonedDateTime]) extends PgAuthidFieldValue("rolvaliduntil", value)
}

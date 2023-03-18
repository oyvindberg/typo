package testdb
package postgres
package pg_catalog



sealed abstract class PgNamespaceFieldValue[T](val name: String, val value: T)

object PgNamespaceFieldValue {
  case class oid(override val value: PgNamespaceId) extends PgNamespaceFieldValue("oid", value)
  case class nspname(override val value: String) extends PgNamespaceFieldValue("nspname", value)
  case class nspowner(override val value: Long) extends PgNamespaceFieldValue("nspowner", value)
  case class nspacl(override val value: Option[Array[String]]) extends PgNamespaceFieldValue("nspacl", value)
}

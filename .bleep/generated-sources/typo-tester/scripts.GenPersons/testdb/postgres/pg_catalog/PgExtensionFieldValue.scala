package testdb
package postgres
package pg_catalog



sealed abstract class PgExtensionFieldValue[T](val name: String, val value: T)

object PgExtensionFieldValue {
  case class oid(override val value: Long) extends PgExtensionFieldValue("oid", value)
  case class extname(override val value: String) extends PgExtensionFieldValue("extname", value)
  case class extowner(override val value: Long) extends PgExtensionFieldValue("extowner", value)
  case class extnamespace(override val value: Long) extends PgExtensionFieldValue("extnamespace", value)
  case class extrelocatable(override val value: Boolean) extends PgExtensionFieldValue("extrelocatable", value)
  case class extversion(override val value: String) extends PgExtensionFieldValue("extversion", value)
  case class extconfig(override val value: Option[Array[Long]]) extends PgExtensionFieldValue("extconfig", value)
  case class extcondition(override val value: Option[Array[String]]) extends PgExtensionFieldValue("extcondition", value)
}

package testdb
package postgres
package pg_catalog



sealed abstract class PgTsConfigFieldValue[T](val name: String, val value: T)

object PgTsConfigFieldValue {
  case class oid(override val value: PgTsConfigId) extends PgTsConfigFieldValue("oid", value)
  case class cfgname(override val value: String) extends PgTsConfigFieldValue("cfgname", value)
  case class cfgnamespace(override val value: Long) extends PgTsConfigFieldValue("cfgnamespace", value)
  case class cfgowner(override val value: Long) extends PgTsConfigFieldValue("cfgowner", value)
  case class cfgparser(override val value: Long) extends PgTsConfigFieldValue("cfgparser", value)
}

package testdb
package postgres
package pg_catalog



sealed abstract class PgDbRoleSettingFieldValue[T](val name: String, val value: T)

object PgDbRoleSettingFieldValue {
  case class setdatabase(override val value: Long) extends PgDbRoleSettingFieldValue("setdatabase", value)
  case class setrole(override val value: Long) extends PgDbRoleSettingFieldValue("setrole", value)
  case class setconfig(override val value: Option[Array[String]]) extends PgDbRoleSettingFieldValue("setconfig", value)
}

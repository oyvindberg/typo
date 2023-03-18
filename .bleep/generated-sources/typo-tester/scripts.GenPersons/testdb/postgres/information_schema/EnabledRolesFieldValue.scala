package testdb
package postgres
package information_schema



sealed abstract class EnabledRolesFieldValue[T](val name: String, val value: T)

object EnabledRolesFieldValue {
  case class roleName(override val value: /* unknown nullability */ Option[String]) extends EnabledRolesFieldValue("role_name", value)
}

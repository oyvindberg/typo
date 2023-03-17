package testdb
package information_schema



sealed abstract class AdministrableRoleAuthorizationsFieldValue[T](val name: String, val value: T)

object AdministrableRoleAuthorizationsFieldValue {
  case class grantee(override val value: Option[String]) extends AdministrableRoleAuthorizationsFieldValue("grantee", value)
  case class roleName(override val value: Option[String]) extends AdministrableRoleAuthorizationsFieldValue("role_name", value)
  case class isGrantable(override val value: Option[String]) extends AdministrableRoleAuthorizationsFieldValue("is_grantable", value)
}

package testdb.information_schema



sealed abstract class ApplicableRolesFieldValue[T](val name: String, val value: T)

object ApplicableRolesFieldValue {
  case class grantee(override val value: /* unknown nullability */ Option[String]) extends ApplicableRolesFieldValue("grantee", value)
  case class roleName(override val value: /* unknown nullability */ Option[String]) extends ApplicableRolesFieldValue("role_name", value)
  case class isGrantable(override val value: /* unknown nullability */ Option[String]) extends ApplicableRolesFieldValue("is_grantable", value)
}

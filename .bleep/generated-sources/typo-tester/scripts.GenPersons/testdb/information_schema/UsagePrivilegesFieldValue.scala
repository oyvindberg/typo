package testdb
package information_schema



sealed abstract class UsagePrivilegesFieldValue[T](val name: String, val value: T)

object UsagePrivilegesFieldValue {
  case class grantor(override val value: /* unknown nullability */ Option[String]) extends UsagePrivilegesFieldValue("grantor", value)
  case class grantee(override val value: /* unknown nullability */ Option[String]) extends UsagePrivilegesFieldValue("grantee", value)
  case class objectCatalog(override val value: /* unknown nullability */ Option[String]) extends UsagePrivilegesFieldValue("object_catalog", value)
  case class objectSchema(override val value: /* unknown nullability */ Option[String]) extends UsagePrivilegesFieldValue("object_schema", value)
  case class objectName(override val value: /* unknown nullability */ Option[String]) extends UsagePrivilegesFieldValue("object_name", value)
  case class objectType(override val value: /* unknown nullability */ Option[String]) extends UsagePrivilegesFieldValue("object_type", value)
  case class privilegeType(override val value: /* unknown nullability */ Option[String]) extends UsagePrivilegesFieldValue("privilege_type", value)
  case class isGrantable(override val value: /* unknown nullability */ Option[String]) extends UsagePrivilegesFieldValue("is_grantable", value)
}

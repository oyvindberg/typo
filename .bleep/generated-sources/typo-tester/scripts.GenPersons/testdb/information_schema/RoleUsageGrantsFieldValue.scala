package testdb
package information_schema



sealed abstract class RoleUsageGrantsFieldValue[T](val name: String, val value: T)

object RoleUsageGrantsFieldValue {
  case class grantor(override val value: Option[String]) extends RoleUsageGrantsFieldValue("grantor", value)
  case class grantee(override val value: Option[String]) extends RoleUsageGrantsFieldValue("grantee", value)
  case class objectCatalog(override val value: Option[String]) extends RoleUsageGrantsFieldValue("object_catalog", value)
  case class objectSchema(override val value: Option[String]) extends RoleUsageGrantsFieldValue("object_schema", value)
  case class objectName(override val value: Option[String]) extends RoleUsageGrantsFieldValue("object_name", value)
  case class objectType(override val value: Option[String]) extends RoleUsageGrantsFieldValue("object_type", value)
  case class privilegeType(override val value: Option[String]) extends RoleUsageGrantsFieldValue("privilege_type", value)
  case class isGrantable(override val value: Option[String]) extends RoleUsageGrantsFieldValue("is_grantable", value)
}

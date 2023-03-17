package testdb
package information_schema



sealed abstract class RoleUdtGrantsFieldValue[T](val name: String, val value: T)

object RoleUdtGrantsFieldValue {
  case class grantor(override val value: Option[String]) extends RoleUdtGrantsFieldValue("grantor", value)
  case class grantee(override val value: Option[String]) extends RoleUdtGrantsFieldValue("grantee", value)
  case class udtCatalog(override val value: Option[String]) extends RoleUdtGrantsFieldValue("udt_catalog", value)
  case class udtSchema(override val value: Option[String]) extends RoleUdtGrantsFieldValue("udt_schema", value)
  case class udtName(override val value: Option[String]) extends RoleUdtGrantsFieldValue("udt_name", value)
  case class privilegeType(override val value: Option[String]) extends RoleUdtGrantsFieldValue("privilege_type", value)
  case class isGrantable(override val value: Option[String]) extends RoleUdtGrantsFieldValue("is_grantable", value)
}

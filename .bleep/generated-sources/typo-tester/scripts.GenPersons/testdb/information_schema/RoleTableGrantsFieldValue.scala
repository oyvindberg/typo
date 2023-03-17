package testdb
package information_schema



sealed abstract class RoleTableGrantsFieldValue[T](val name: String, val value: T)

object RoleTableGrantsFieldValue {
  case class grantor(override val value: Option[String]) extends RoleTableGrantsFieldValue("grantor", value)
  case class grantee(override val value: Option[String]) extends RoleTableGrantsFieldValue("grantee", value)
  case class tableCatalog(override val value: Option[String]) extends RoleTableGrantsFieldValue("table_catalog", value)
  case class tableSchema(override val value: Option[String]) extends RoleTableGrantsFieldValue("table_schema", value)
  case class tableName(override val value: Option[String]) extends RoleTableGrantsFieldValue("table_name", value)
  case class privilegeType(override val value: Option[String]) extends RoleTableGrantsFieldValue("privilege_type", value)
  case class isGrantable(override val value: Option[String]) extends RoleTableGrantsFieldValue("is_grantable", value)
  case class withHierarchy(override val value: Option[String]) extends RoleTableGrantsFieldValue("with_hierarchy", value)
}

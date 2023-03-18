package testdb
package postgres
package information_schema



sealed abstract class ColumnPrivilegesFieldValue[T](val name: String, val value: T)

object ColumnPrivilegesFieldValue {
  case class grantor(override val value: /* unknown nullability */ Option[String]) extends ColumnPrivilegesFieldValue("grantor", value)
  case class grantee(override val value: /* unknown nullability */ Option[String]) extends ColumnPrivilegesFieldValue("grantee", value)
  case class tableCatalog(override val value: /* unknown nullability */ Option[String]) extends ColumnPrivilegesFieldValue("table_catalog", value)
  case class tableSchema(override val value: /* unknown nullability */ Option[String]) extends ColumnPrivilegesFieldValue("table_schema", value)
  case class tableName(override val value: /* unknown nullability */ Option[String]) extends ColumnPrivilegesFieldValue("table_name", value)
  case class columnName(override val value: /* unknown nullability */ Option[String]) extends ColumnPrivilegesFieldValue("column_name", value)
  case class privilegeType(override val value: /* unknown nullability */ Option[String]) extends ColumnPrivilegesFieldValue("privilege_type", value)
  case class isGrantable(override val value: /* unknown nullability */ Option[String]) extends ColumnPrivilegesFieldValue("is_grantable", value)
}

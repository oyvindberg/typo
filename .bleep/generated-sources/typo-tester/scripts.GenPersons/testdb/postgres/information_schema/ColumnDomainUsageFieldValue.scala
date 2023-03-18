package testdb
package postgres
package information_schema



sealed abstract class ColumnDomainUsageFieldValue[T](val name: String, val value: T)

object ColumnDomainUsageFieldValue {
  case class domainCatalog(override val value: /* unknown nullability */ Option[String]) extends ColumnDomainUsageFieldValue("domain_catalog", value)
  case class domainSchema(override val value: /* unknown nullability */ Option[String]) extends ColumnDomainUsageFieldValue("domain_schema", value)
  case class domainName(override val value: /* unknown nullability */ Option[String]) extends ColumnDomainUsageFieldValue("domain_name", value)
  case class tableCatalog(override val value: /* unknown nullability */ Option[String]) extends ColumnDomainUsageFieldValue("table_catalog", value)
  case class tableSchema(override val value: /* unknown nullability */ Option[String]) extends ColumnDomainUsageFieldValue("table_schema", value)
  case class tableName(override val value: /* unknown nullability */ Option[String]) extends ColumnDomainUsageFieldValue("table_name", value)
  case class columnName(override val value: /* unknown nullability */ Option[String]) extends ColumnDomainUsageFieldValue("column_name", value)
}

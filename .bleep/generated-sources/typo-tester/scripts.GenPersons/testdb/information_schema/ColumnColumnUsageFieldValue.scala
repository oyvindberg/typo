package testdb
package information_schema



sealed abstract class ColumnColumnUsageFieldValue[T](val name: String, val value: T)

object ColumnColumnUsageFieldValue {
  case class tableCatalog(override val value: /* unknown nullability */ Option[String]) extends ColumnColumnUsageFieldValue("table_catalog", value)
  case class tableSchema(override val value: /* unknown nullability */ Option[String]) extends ColumnColumnUsageFieldValue("table_schema", value)
  case class tableName(override val value: /* unknown nullability */ Option[String]) extends ColumnColumnUsageFieldValue("table_name", value)
  case class columnName(override val value: /* unknown nullability */ Option[String]) extends ColumnColumnUsageFieldValue("column_name", value)
  case class dependentColumn(override val value: /* unknown nullability */ Option[String]) extends ColumnColumnUsageFieldValue("dependent_column", value)
}

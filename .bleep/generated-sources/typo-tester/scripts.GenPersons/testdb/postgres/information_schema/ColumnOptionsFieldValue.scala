package testdb
package postgres
package information_schema



sealed abstract class ColumnOptionsFieldValue[T](val name: String, val value: T)

object ColumnOptionsFieldValue {
  case class tableCatalog(override val value: /* unknown nullability */ Option[String]) extends ColumnOptionsFieldValue("table_catalog", value)
  case class tableSchema(override val value: /* unknown nullability */ Option[String]) extends ColumnOptionsFieldValue("table_schema", value)
  case class tableName(override val value: /* unknown nullability */ Option[String]) extends ColumnOptionsFieldValue("table_name", value)
  case class columnName(override val value: /* unknown nullability */ Option[String]) extends ColumnOptionsFieldValue("column_name", value)
  case class optionName(override val value: /* unknown nullability */ Option[String]) extends ColumnOptionsFieldValue("option_name", value)
  case class optionValue(override val value: /* unknown nullability */ Option[String]) extends ColumnOptionsFieldValue("option_value", value)
}

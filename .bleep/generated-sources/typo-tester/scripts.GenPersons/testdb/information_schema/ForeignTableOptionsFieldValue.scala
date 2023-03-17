package testdb.information_schema



sealed abstract class ForeignTableOptionsFieldValue[T](val name: String, val value: T)

object ForeignTableOptionsFieldValue {
  case class foreignTableCatalog(override val value: Option[String]) extends ForeignTableOptionsFieldValue("foreign_table_catalog", value)
  case class foreignTableSchema(override val value: Option[String]) extends ForeignTableOptionsFieldValue("foreign_table_schema", value)
  case class foreignTableName(override val value: Option[String]) extends ForeignTableOptionsFieldValue("foreign_table_name", value)
  case class optionName(override val value: /* unknown nullability */ Option[String]) extends ForeignTableOptionsFieldValue("option_name", value)
  case class optionValue(override val value: /* unknown nullability */ Option[String]) extends ForeignTableOptionsFieldValue("option_value", value)
}

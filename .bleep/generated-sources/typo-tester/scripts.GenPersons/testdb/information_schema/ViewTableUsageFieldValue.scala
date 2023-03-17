package testdb.information_schema



sealed abstract class ViewTableUsageFieldValue[T](val name: String, val value: T)

object ViewTableUsageFieldValue {
  case class viewCatalog(override val value: /* unknown nullability */ Option[String]) extends ViewTableUsageFieldValue("view_catalog", value)
  case class viewSchema(override val value: /* unknown nullability */ Option[String]) extends ViewTableUsageFieldValue("view_schema", value)
  case class viewName(override val value: /* unknown nullability */ Option[String]) extends ViewTableUsageFieldValue("view_name", value)
  case class tableCatalog(override val value: /* unknown nullability */ Option[String]) extends ViewTableUsageFieldValue("table_catalog", value)
  case class tableSchema(override val value: /* unknown nullability */ Option[String]) extends ViewTableUsageFieldValue("table_schema", value)
  case class tableName(override val value: /* unknown nullability */ Option[String]) extends ViewTableUsageFieldValue("table_name", value)
}

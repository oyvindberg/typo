package testdb.information_schema



sealed abstract class ViewColumnUsageFieldValue[T](val name: String, val value: T)

object ViewColumnUsageFieldValue {
  case class viewCatalog(override val value: /* unknown nullability */ Option[String]) extends ViewColumnUsageFieldValue("view_catalog", value)
  case class viewSchema(override val value: /* unknown nullability */ Option[String]) extends ViewColumnUsageFieldValue("view_schema", value)
  case class viewName(override val value: /* unknown nullability */ Option[String]) extends ViewColumnUsageFieldValue("view_name", value)
  case class tableCatalog(override val value: /* unknown nullability */ Option[String]) extends ViewColumnUsageFieldValue("table_catalog", value)
  case class tableSchema(override val value: /* unknown nullability */ Option[String]) extends ViewColumnUsageFieldValue("table_schema", value)
  case class tableName(override val value: /* unknown nullability */ Option[String]) extends ViewColumnUsageFieldValue("table_name", value)
  case class columnName(override val value: /* unknown nullability */ Option[String]) extends ViewColumnUsageFieldValue("column_name", value)
}

package testdb.information_schema



sealed abstract class ColumnUdtUsageFieldValue[T](val name: String, val value: T)

object ColumnUdtUsageFieldValue {
  case class udtCatalog(override val value: /* unknown nullability */ Option[String]) extends ColumnUdtUsageFieldValue("udt_catalog", value)
  case class udtSchema(override val value: /* unknown nullability */ Option[String]) extends ColumnUdtUsageFieldValue("udt_schema", value)
  case class udtName(override val value: /* unknown nullability */ Option[String]) extends ColumnUdtUsageFieldValue("udt_name", value)
  case class tableCatalog(override val value: /* unknown nullability */ Option[String]) extends ColumnUdtUsageFieldValue("table_catalog", value)
  case class tableSchema(override val value: /* unknown nullability */ Option[String]) extends ColumnUdtUsageFieldValue("table_schema", value)
  case class tableName(override val value: /* unknown nullability */ Option[String]) extends ColumnUdtUsageFieldValue("table_name", value)
  case class columnName(override val value: /* unknown nullability */ Option[String]) extends ColumnUdtUsageFieldValue("column_name", value)
}

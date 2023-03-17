package testdb
package information_schema



sealed abstract class ConstraintColumnUsageFieldValue[T](val name: String, val value: T)

object ConstraintColumnUsageFieldValue {
  case class tableCatalog(override val value: /* unknown nullability */ Option[String]) extends ConstraintColumnUsageFieldValue("table_catalog", value)
  case class tableSchema(override val value: /* unknown nullability */ Option[String]) extends ConstraintColumnUsageFieldValue("table_schema", value)
  case class tableName(override val value: /* unknown nullability */ Option[String]) extends ConstraintColumnUsageFieldValue("table_name", value)
  case class columnName(override val value: /* unknown nullability */ Option[String]) extends ConstraintColumnUsageFieldValue("column_name", value)
  case class constraintCatalog(override val value: /* unknown nullability */ Option[String]) extends ConstraintColumnUsageFieldValue("constraint_catalog", value)
  case class constraintSchema(override val value: /* unknown nullability */ Option[String]) extends ConstraintColumnUsageFieldValue("constraint_schema", value)
  case class constraintName(override val value: /* unknown nullability */ Option[String]) extends ConstraintColumnUsageFieldValue("constraint_name", value)
}

package testdb
package information_schema



sealed abstract class ConstraintTableUsageFieldValue[T](val name: String, val value: T)

object ConstraintTableUsageFieldValue {
  case class tableCatalog(override val value: /* unknown nullability */ Option[String]) extends ConstraintTableUsageFieldValue("table_catalog", value)
  case class tableSchema(override val value: /* unknown nullability */ Option[String]) extends ConstraintTableUsageFieldValue("table_schema", value)
  case class tableName(override val value: /* unknown nullability */ Option[String]) extends ConstraintTableUsageFieldValue("table_name", value)
  case class constraintCatalog(override val value: /* unknown nullability */ Option[String]) extends ConstraintTableUsageFieldValue("constraint_catalog", value)
  case class constraintSchema(override val value: /* unknown nullability */ Option[String]) extends ConstraintTableUsageFieldValue("constraint_schema", value)
  case class constraintName(override val value: /* unknown nullability */ Option[String]) extends ConstraintTableUsageFieldValue("constraint_name", value)
}

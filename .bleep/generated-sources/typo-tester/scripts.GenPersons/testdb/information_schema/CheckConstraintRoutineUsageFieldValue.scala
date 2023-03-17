package testdb
package information_schema



sealed abstract class CheckConstraintRoutineUsageFieldValue[T](val name: String, val value: T)

object CheckConstraintRoutineUsageFieldValue {
  case class constraintCatalog(override val value: /* unknown nullability */ Option[String]) extends CheckConstraintRoutineUsageFieldValue("constraint_catalog", value)
  case class constraintSchema(override val value: /* unknown nullability */ Option[String]) extends CheckConstraintRoutineUsageFieldValue("constraint_schema", value)
  case class constraintName(override val value: /* unknown nullability */ Option[String]) extends CheckConstraintRoutineUsageFieldValue("constraint_name", value)
  case class specificCatalog(override val value: /* unknown nullability */ Option[String]) extends CheckConstraintRoutineUsageFieldValue("specific_catalog", value)
  case class specificSchema(override val value: /* unknown nullability */ Option[String]) extends CheckConstraintRoutineUsageFieldValue("specific_schema", value)
  case class specificName(override val value: /* unknown nullability */ Option[String]) extends CheckConstraintRoutineUsageFieldValue("specific_name", value)
}

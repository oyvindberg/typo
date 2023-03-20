/**
 * File has been automatically generated by `typo` for internal use.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 *
 * (If you're developing `typo` and want to change it: run `bleep generate-sources)
 */
package typo
package generated
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
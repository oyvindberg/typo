package testdb.information_schema



sealed abstract class RoutineSequenceUsageFieldValue[T](val name: String, val value: T)

object RoutineSequenceUsageFieldValue {
  case class specificCatalog(override val value: /* unknown nullability */ Option[String]) extends RoutineSequenceUsageFieldValue("specific_catalog", value)
  case class specificSchema(override val value: /* unknown nullability */ Option[String]) extends RoutineSequenceUsageFieldValue("specific_schema", value)
  case class specificName(override val value: /* unknown nullability */ Option[String]) extends RoutineSequenceUsageFieldValue("specific_name", value)
  case class routineCatalog(override val value: /* unknown nullability */ Option[String]) extends RoutineSequenceUsageFieldValue("routine_catalog", value)
  case class routineSchema(override val value: /* unknown nullability */ Option[String]) extends RoutineSequenceUsageFieldValue("routine_schema", value)
  case class routineName(override val value: /* unknown nullability */ Option[String]) extends RoutineSequenceUsageFieldValue("routine_name", value)
  case class sequenceCatalog(override val value: /* unknown nullability */ Option[String]) extends RoutineSequenceUsageFieldValue("sequence_catalog", value)
  case class sequenceSchema(override val value: /* unknown nullability */ Option[String]) extends RoutineSequenceUsageFieldValue("sequence_schema", value)
  case class sequenceName(override val value: /* unknown nullability */ Option[String]) extends RoutineSequenceUsageFieldValue("sequence_name", value)
}

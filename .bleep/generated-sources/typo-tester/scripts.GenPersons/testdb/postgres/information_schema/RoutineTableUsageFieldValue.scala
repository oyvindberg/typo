package testdb
package postgres
package information_schema



sealed abstract class RoutineTableUsageFieldValue[T](val name: String, val value: T)

object RoutineTableUsageFieldValue {
  case class specificCatalog(override val value: /* unknown nullability */ Option[String]) extends RoutineTableUsageFieldValue("specific_catalog", value)
  case class specificSchema(override val value: /* unknown nullability */ Option[String]) extends RoutineTableUsageFieldValue("specific_schema", value)
  case class specificName(override val value: /* unknown nullability */ Option[String]) extends RoutineTableUsageFieldValue("specific_name", value)
  case class routineCatalog(override val value: /* unknown nullability */ Option[String]) extends RoutineTableUsageFieldValue("routine_catalog", value)
  case class routineSchema(override val value: /* unknown nullability */ Option[String]) extends RoutineTableUsageFieldValue("routine_schema", value)
  case class routineName(override val value: /* unknown nullability */ Option[String]) extends RoutineTableUsageFieldValue("routine_name", value)
  case class tableCatalog(override val value: /* unknown nullability */ Option[String]) extends RoutineTableUsageFieldValue("table_catalog", value)
  case class tableSchema(override val value: /* unknown nullability */ Option[String]) extends RoutineTableUsageFieldValue("table_schema", value)
  case class tableName(override val value: /* unknown nullability */ Option[String]) extends RoutineTableUsageFieldValue("table_name", value)
}

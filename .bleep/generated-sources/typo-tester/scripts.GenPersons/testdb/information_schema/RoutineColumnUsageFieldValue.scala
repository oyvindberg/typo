package testdb
package information_schema



sealed abstract class RoutineColumnUsageFieldValue[T](val name: String, val value: T)

object RoutineColumnUsageFieldValue {
  case class specificCatalog(override val value: /* unknown nullability */ Option[String]) extends RoutineColumnUsageFieldValue("specific_catalog", value)
  case class specificSchema(override val value: /* unknown nullability */ Option[String]) extends RoutineColumnUsageFieldValue("specific_schema", value)
  case class specificName(override val value: /* unknown nullability */ Option[String]) extends RoutineColumnUsageFieldValue("specific_name", value)
  case class routineCatalog(override val value: /* unknown nullability */ Option[String]) extends RoutineColumnUsageFieldValue("routine_catalog", value)
  case class routineSchema(override val value: /* unknown nullability */ Option[String]) extends RoutineColumnUsageFieldValue("routine_schema", value)
  case class routineName(override val value: /* unknown nullability */ Option[String]) extends RoutineColumnUsageFieldValue("routine_name", value)
  case class tableCatalog(override val value: /* unknown nullability */ Option[String]) extends RoutineColumnUsageFieldValue("table_catalog", value)
  case class tableSchema(override val value: /* unknown nullability */ Option[String]) extends RoutineColumnUsageFieldValue("table_schema", value)
  case class tableName(override val value: /* unknown nullability */ Option[String]) extends RoutineColumnUsageFieldValue("table_name", value)
  case class columnName(override val value: /* unknown nullability */ Option[String]) extends RoutineColumnUsageFieldValue("column_name", value)
}

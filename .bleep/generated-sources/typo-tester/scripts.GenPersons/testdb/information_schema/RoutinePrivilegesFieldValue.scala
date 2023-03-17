package testdb
package information_schema



sealed abstract class RoutinePrivilegesFieldValue[T](val name: String, val value: T)

object RoutinePrivilegesFieldValue {
  case class grantor(override val value: /* unknown nullability */ Option[String]) extends RoutinePrivilegesFieldValue("grantor", value)
  case class grantee(override val value: /* unknown nullability */ Option[String]) extends RoutinePrivilegesFieldValue("grantee", value)
  case class specificCatalog(override val value: /* unknown nullability */ Option[String]) extends RoutinePrivilegesFieldValue("specific_catalog", value)
  case class specificSchema(override val value: /* unknown nullability */ Option[String]) extends RoutinePrivilegesFieldValue("specific_schema", value)
  case class specificName(override val value: /* unknown nullability */ Option[String]) extends RoutinePrivilegesFieldValue("specific_name", value)
  case class routineCatalog(override val value: /* unknown nullability */ Option[String]) extends RoutinePrivilegesFieldValue("routine_catalog", value)
  case class routineSchema(override val value: /* unknown nullability */ Option[String]) extends RoutinePrivilegesFieldValue("routine_schema", value)
  case class routineName(override val value: /* unknown nullability */ Option[String]) extends RoutinePrivilegesFieldValue("routine_name", value)
  case class privilegeType(override val value: /* unknown nullability */ Option[String]) extends RoutinePrivilegesFieldValue("privilege_type", value)
  case class isGrantable(override val value: /* unknown nullability */ Option[String]) extends RoutinePrivilegesFieldValue("is_grantable", value)
}

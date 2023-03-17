package testdb
package information_schema



sealed abstract class RoleRoutineGrantsFieldValue[T](val name: String, val value: T)

object RoleRoutineGrantsFieldValue {
  case class grantor(override val value: Option[String]) extends RoleRoutineGrantsFieldValue("grantor", value)
  case class grantee(override val value: Option[String]) extends RoleRoutineGrantsFieldValue("grantee", value)
  case class specificCatalog(override val value: Option[String]) extends RoleRoutineGrantsFieldValue("specific_catalog", value)
  case class specificSchema(override val value: Option[String]) extends RoleRoutineGrantsFieldValue("specific_schema", value)
  case class specificName(override val value: Option[String]) extends RoleRoutineGrantsFieldValue("specific_name", value)
  case class routineCatalog(override val value: Option[String]) extends RoleRoutineGrantsFieldValue("routine_catalog", value)
  case class routineSchema(override val value: Option[String]) extends RoleRoutineGrantsFieldValue("routine_schema", value)
  case class routineName(override val value: Option[String]) extends RoleRoutineGrantsFieldValue("routine_name", value)
  case class privilegeType(override val value: Option[String]) extends RoleRoutineGrantsFieldValue("privilege_type", value)
  case class isGrantable(override val value: Option[String]) extends RoleRoutineGrantsFieldValue("is_grantable", value)
}

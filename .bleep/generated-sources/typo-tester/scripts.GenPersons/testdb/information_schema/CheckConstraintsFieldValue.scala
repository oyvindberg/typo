package testdb
package information_schema



sealed abstract class CheckConstraintsFieldValue[T](val name: String, val value: T)

object CheckConstraintsFieldValue {
  case class constraintCatalog(override val value: /* unknown nullability */ Option[String]) extends CheckConstraintsFieldValue("constraint_catalog", value)
  case class constraintSchema(override val value: /* unknown nullability */ Option[String]) extends CheckConstraintsFieldValue("constraint_schema", value)
  case class constraintName(override val value: /* unknown nullability */ Option[String]) extends CheckConstraintsFieldValue("constraint_name", value)
  case class checkClause(override val value: /* unknown nullability */ Option[String]) extends CheckConstraintsFieldValue("check_clause", value)
}

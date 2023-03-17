package testdb
package information_schema



sealed abstract class ReferentialConstraintsFieldValue[T](val name: String, val value: T)

object ReferentialConstraintsFieldValue {
  case class constraintCatalog(override val value: /* unknown nullability */ Option[String]) extends ReferentialConstraintsFieldValue("constraint_catalog", value)
  case class constraintSchema(override val value: /* unknown nullability */ Option[String]) extends ReferentialConstraintsFieldValue("constraint_schema", value)
  case class constraintName(override val value: /* unknown nullability */ Option[String]) extends ReferentialConstraintsFieldValue("constraint_name", value)
  case class uniqueConstraintCatalog(override val value: /* unknown nullability */ Option[String]) extends ReferentialConstraintsFieldValue("unique_constraint_catalog", value)
  case class uniqueConstraintSchema(override val value: /* unknown nullability */ Option[String]) extends ReferentialConstraintsFieldValue("unique_constraint_schema", value)
  case class uniqueConstraintName(override val value: /* unknown nullability */ Option[String]) extends ReferentialConstraintsFieldValue("unique_constraint_name", value)
  case class matchOption(override val value: /* unknown nullability */ Option[String]) extends ReferentialConstraintsFieldValue("match_option", value)
  case class updateRule(override val value: /* unknown nullability */ Option[String]) extends ReferentialConstraintsFieldValue("update_rule", value)
  case class deleteRule(override val value: /* unknown nullability */ Option[String]) extends ReferentialConstraintsFieldValue("delete_rule", value)
}

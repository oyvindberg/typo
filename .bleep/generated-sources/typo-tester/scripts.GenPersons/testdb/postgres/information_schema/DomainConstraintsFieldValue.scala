package testdb
package postgres
package information_schema



sealed abstract class DomainConstraintsFieldValue[T](val name: String, val value: T)

object DomainConstraintsFieldValue {
  case class constraintCatalog(override val value: /* unknown nullability */ Option[String]) extends DomainConstraintsFieldValue("constraint_catalog", value)
  case class constraintSchema(override val value: /* unknown nullability */ Option[String]) extends DomainConstraintsFieldValue("constraint_schema", value)
  case class constraintName(override val value: /* unknown nullability */ Option[String]) extends DomainConstraintsFieldValue("constraint_name", value)
  case class domainCatalog(override val value: /* unknown nullability */ Option[String]) extends DomainConstraintsFieldValue("domain_catalog", value)
  case class domainSchema(override val value: /* unknown nullability */ Option[String]) extends DomainConstraintsFieldValue("domain_schema", value)
  case class domainName(override val value: /* unknown nullability */ Option[String]) extends DomainConstraintsFieldValue("domain_name", value)
  case class isDeferrable(override val value: /* unknown nullability */ Option[String]) extends DomainConstraintsFieldValue("is_deferrable", value)
  case class initiallyDeferred(override val value: /* unknown nullability */ Option[String]) extends DomainConstraintsFieldValue("initially_deferred", value)
}

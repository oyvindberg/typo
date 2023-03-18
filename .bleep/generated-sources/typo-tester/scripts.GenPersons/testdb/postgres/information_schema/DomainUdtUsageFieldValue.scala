package testdb
package postgres
package information_schema



sealed abstract class DomainUdtUsageFieldValue[T](val name: String, val value: T)

object DomainUdtUsageFieldValue {
  case class udtCatalog(override val value: /* unknown nullability */ Option[String]) extends DomainUdtUsageFieldValue("udt_catalog", value)
  case class udtSchema(override val value: /* unknown nullability */ Option[String]) extends DomainUdtUsageFieldValue("udt_schema", value)
  case class udtName(override val value: /* unknown nullability */ Option[String]) extends DomainUdtUsageFieldValue("udt_name", value)
  case class domainCatalog(override val value: /* unknown nullability */ Option[String]) extends DomainUdtUsageFieldValue("domain_catalog", value)
  case class domainSchema(override val value: /* unknown nullability */ Option[String]) extends DomainUdtUsageFieldValue("domain_schema", value)
  case class domainName(override val value: /* unknown nullability */ Option[String]) extends DomainUdtUsageFieldValue("domain_name", value)
}

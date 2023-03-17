package testdb.information_schema



sealed abstract class DomainsFieldValue[T](val name: String, val value: T)

object DomainsFieldValue {
  case class domainCatalog(override val value: /* unknown nullability */ Option[String]) extends DomainsFieldValue("domain_catalog", value)
  case class domainSchema(override val value: /* unknown nullability */ Option[String]) extends DomainsFieldValue("domain_schema", value)
  case class domainName(override val value: /* unknown nullability */ Option[String]) extends DomainsFieldValue("domain_name", value)
  case class dataType(override val value: /* unknown nullability */ Option[String]) extends DomainsFieldValue("data_type", value)
  case class characterMaximumLength(override val value: /* unknown nullability */ Option[Int]) extends DomainsFieldValue("character_maximum_length", value)
  case class characterOctetLength(override val value: /* unknown nullability */ Option[Int]) extends DomainsFieldValue("character_octet_length", value)
  case class characterSetCatalog(override val value: /* unknown nullability */ Option[String]) extends DomainsFieldValue("character_set_catalog", value)
  case class characterSetSchema(override val value: /* unknown nullability */ Option[String]) extends DomainsFieldValue("character_set_schema", value)
  case class characterSetName(override val value: /* unknown nullability */ Option[String]) extends DomainsFieldValue("character_set_name", value)
  case class collationCatalog(override val value: /* unknown nullability */ Option[String]) extends DomainsFieldValue("collation_catalog", value)
  case class collationSchema(override val value: /* unknown nullability */ Option[String]) extends DomainsFieldValue("collation_schema", value)
  case class collationName(override val value: /* unknown nullability */ Option[String]) extends DomainsFieldValue("collation_name", value)
  case class numericPrecision(override val value: /* unknown nullability */ Option[Int]) extends DomainsFieldValue("numeric_precision", value)
  case class numericPrecisionRadix(override val value: /* unknown nullability */ Option[Int]) extends DomainsFieldValue("numeric_precision_radix", value)
  case class numericScale(override val value: /* unknown nullability */ Option[Int]) extends DomainsFieldValue("numeric_scale", value)
  case class datetimePrecision(override val value: /* unknown nullability */ Option[Int]) extends DomainsFieldValue("datetime_precision", value)
  case class intervalType(override val value: /* unknown nullability */ Option[String]) extends DomainsFieldValue("interval_type", value)
  case class intervalPrecision(override val value: /* unknown nullability */ Option[Int]) extends DomainsFieldValue("interval_precision", value)
  case class domainDefault(override val value: /* unknown nullability */ Option[String]) extends DomainsFieldValue("domain_default", value)
  case class udtCatalog(override val value: /* unknown nullability */ Option[String]) extends DomainsFieldValue("udt_catalog", value)
  case class udtSchema(override val value: /* unknown nullability */ Option[String]) extends DomainsFieldValue("udt_schema", value)
  case class udtName(override val value: /* unknown nullability */ Option[String]) extends DomainsFieldValue("udt_name", value)
  case class scopeCatalog(override val value: /* unknown nullability */ Option[String]) extends DomainsFieldValue("scope_catalog", value)
  case class scopeSchema(override val value: /* unknown nullability */ Option[String]) extends DomainsFieldValue("scope_schema", value)
  case class scopeName(override val value: /* unknown nullability */ Option[String]) extends DomainsFieldValue("scope_name", value)
  case class maximumCardinality(override val value: /* unknown nullability */ Option[Int]) extends DomainsFieldValue("maximum_cardinality", value)
  case class dtdIdentifier(override val value: /* unknown nullability */ Option[String]) extends DomainsFieldValue("dtd_identifier", value)
}

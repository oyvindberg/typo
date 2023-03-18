package testdb
package postgres
package information_schema



sealed abstract class ElementTypesFieldValue[T](val name: String, val value: T)

object ElementTypesFieldValue {
  case class objectCatalog(override val value: /* unknown nullability */ Option[String]) extends ElementTypesFieldValue("object_catalog", value)
  case class objectSchema(override val value: /* unknown nullability */ Option[String]) extends ElementTypesFieldValue("object_schema", value)
  case class objectName(override val value: /* unknown nullability */ Option[String]) extends ElementTypesFieldValue("object_name", value)
  case class objectType(override val value: /* unknown nullability */ Option[String]) extends ElementTypesFieldValue("object_type", value)
  case class collectionTypeIdentifier(override val value: /* unknown nullability */ Option[String]) extends ElementTypesFieldValue("collection_type_identifier", value)
  case class dataType(override val value: /* unknown nullability */ Option[String]) extends ElementTypesFieldValue("data_type", value)
  case class characterMaximumLength(override val value: /* unknown nullability */ Option[Int]) extends ElementTypesFieldValue("character_maximum_length", value)
  case class characterOctetLength(override val value: /* unknown nullability */ Option[Int]) extends ElementTypesFieldValue("character_octet_length", value)
  case class characterSetCatalog(override val value: /* unknown nullability */ Option[String]) extends ElementTypesFieldValue("character_set_catalog", value)
  case class characterSetSchema(override val value: /* unknown nullability */ Option[String]) extends ElementTypesFieldValue("character_set_schema", value)
  case class characterSetName(override val value: /* unknown nullability */ Option[String]) extends ElementTypesFieldValue("character_set_name", value)
  case class collationCatalog(override val value: /* unknown nullability */ Option[String]) extends ElementTypesFieldValue("collation_catalog", value)
  case class collationSchema(override val value: /* unknown nullability */ Option[String]) extends ElementTypesFieldValue("collation_schema", value)
  case class collationName(override val value: /* unknown nullability */ Option[String]) extends ElementTypesFieldValue("collation_name", value)
  case class numericPrecision(override val value: /* unknown nullability */ Option[Int]) extends ElementTypesFieldValue("numeric_precision", value)
  case class numericPrecisionRadix(override val value: /* unknown nullability */ Option[Int]) extends ElementTypesFieldValue("numeric_precision_radix", value)
  case class numericScale(override val value: /* unknown nullability */ Option[Int]) extends ElementTypesFieldValue("numeric_scale", value)
  case class datetimePrecision(override val value: /* unknown nullability */ Option[Int]) extends ElementTypesFieldValue("datetime_precision", value)
  case class intervalType(override val value: /* unknown nullability */ Option[String]) extends ElementTypesFieldValue("interval_type", value)
  case class intervalPrecision(override val value: /* unknown nullability */ Option[Int]) extends ElementTypesFieldValue("interval_precision", value)
  case class domainDefault(override val value: /* unknown nullability */ Option[String]) extends ElementTypesFieldValue("domain_default", value)
  case class udtCatalog(override val value: /* unknown nullability */ Option[String]) extends ElementTypesFieldValue("udt_catalog", value)
  case class udtSchema(override val value: /* unknown nullability */ Option[String]) extends ElementTypesFieldValue("udt_schema", value)
  case class udtName(override val value: /* unknown nullability */ Option[String]) extends ElementTypesFieldValue("udt_name", value)
  case class scopeCatalog(override val value: /* unknown nullability */ Option[String]) extends ElementTypesFieldValue("scope_catalog", value)
  case class scopeSchema(override val value: /* unknown nullability */ Option[String]) extends ElementTypesFieldValue("scope_schema", value)
  case class scopeName(override val value: /* unknown nullability */ Option[String]) extends ElementTypesFieldValue("scope_name", value)
  case class maximumCardinality(override val value: /* unknown nullability */ Option[Int]) extends ElementTypesFieldValue("maximum_cardinality", value)
  case class dtdIdentifier(override val value: /* unknown nullability */ Option[String]) extends ElementTypesFieldValue("dtd_identifier", value)
}

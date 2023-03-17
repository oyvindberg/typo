package testdb.information_schema



sealed abstract class AttributesFieldValue[T](val name: String, val value: T)

object AttributesFieldValue {
  case class udtCatalog(override val value: /* unknown nullability */ Option[String]) extends AttributesFieldValue("udt_catalog", value)
  case class udtSchema(override val value: /* unknown nullability */ Option[String]) extends AttributesFieldValue("udt_schema", value)
  case class udtName(override val value: /* unknown nullability */ Option[String]) extends AttributesFieldValue("udt_name", value)
  case class attributeName(override val value: /* unknown nullability */ Option[String]) extends AttributesFieldValue("attribute_name", value)
  case class ordinalPosition(override val value: /* unknown nullability */ Option[Int]) extends AttributesFieldValue("ordinal_position", value)
  case class attributeDefault(override val value: /* unknown nullability */ Option[String]) extends AttributesFieldValue("attribute_default", value)
  case class isNullable(override val value: /* unknown nullability */ Option[String]) extends AttributesFieldValue("is_nullable", value)
  case class dataType(override val value: /* unknown nullability */ Option[String]) extends AttributesFieldValue("data_type", value)
  case class characterMaximumLength(override val value: /* unknown nullability */ Option[Int]) extends AttributesFieldValue("character_maximum_length", value)
  case class characterOctetLength(override val value: /* unknown nullability */ Option[Int]) extends AttributesFieldValue("character_octet_length", value)
  case class characterSetCatalog(override val value: /* unknown nullability */ Option[String]) extends AttributesFieldValue("character_set_catalog", value)
  case class characterSetSchema(override val value: /* unknown nullability */ Option[String]) extends AttributesFieldValue("character_set_schema", value)
  case class characterSetName(override val value: /* unknown nullability */ Option[String]) extends AttributesFieldValue("character_set_name", value)
  case class collationCatalog(override val value: /* unknown nullability */ Option[String]) extends AttributesFieldValue("collation_catalog", value)
  case class collationSchema(override val value: /* unknown nullability */ Option[String]) extends AttributesFieldValue("collation_schema", value)
  case class collationName(override val value: /* unknown nullability */ Option[String]) extends AttributesFieldValue("collation_name", value)
  case class numericPrecision(override val value: /* unknown nullability */ Option[Int]) extends AttributesFieldValue("numeric_precision", value)
  case class numericPrecisionRadix(override val value: /* unknown nullability */ Option[Int]) extends AttributesFieldValue("numeric_precision_radix", value)
  case class numericScale(override val value: /* unknown nullability */ Option[Int]) extends AttributesFieldValue("numeric_scale", value)
  case class datetimePrecision(override val value: /* unknown nullability */ Option[Int]) extends AttributesFieldValue("datetime_precision", value)
  case class intervalType(override val value: /* unknown nullability */ Option[String]) extends AttributesFieldValue("interval_type", value)
  case class intervalPrecision(override val value: /* unknown nullability */ Option[Int]) extends AttributesFieldValue("interval_precision", value)
  case class attributeUdtCatalog(override val value: /* unknown nullability */ Option[String]) extends AttributesFieldValue("attribute_udt_catalog", value)
  case class attributeUdtSchema(override val value: /* unknown nullability */ Option[String]) extends AttributesFieldValue("attribute_udt_schema", value)
  case class attributeUdtName(override val value: /* unknown nullability */ Option[String]) extends AttributesFieldValue("attribute_udt_name", value)
  case class scopeCatalog(override val value: /* unknown nullability */ Option[String]) extends AttributesFieldValue("scope_catalog", value)
  case class scopeSchema(override val value: /* unknown nullability */ Option[String]) extends AttributesFieldValue("scope_schema", value)
  case class scopeName(override val value: /* unknown nullability */ Option[String]) extends AttributesFieldValue("scope_name", value)
  case class maximumCardinality(override val value: /* unknown nullability */ Option[Int]) extends AttributesFieldValue("maximum_cardinality", value)
  case class dtdIdentifier(override val value: /* unknown nullability */ Option[String]) extends AttributesFieldValue("dtd_identifier", value)
  case class isDerivedReferenceAttribute(override val value: /* unknown nullability */ Option[String]) extends AttributesFieldValue("is_derived_reference_attribute", value)
}

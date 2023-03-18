package testdb
package postgres
package information_schema



sealed abstract class CollationCharacterSetApplicabilityFieldValue[T](val name: String, val value: T)

object CollationCharacterSetApplicabilityFieldValue {
  case class collationCatalog(override val value: /* unknown nullability */ Option[String]) extends CollationCharacterSetApplicabilityFieldValue("collation_catalog", value)
  case class collationSchema(override val value: /* unknown nullability */ Option[String]) extends CollationCharacterSetApplicabilityFieldValue("collation_schema", value)
  case class collationName(override val value: /* unknown nullability */ Option[String]) extends CollationCharacterSetApplicabilityFieldValue("collation_name", value)
  case class characterSetCatalog(override val value: /* unknown nullability */ Option[String]) extends CollationCharacterSetApplicabilityFieldValue("character_set_catalog", value)
  case class characterSetSchema(override val value: /* unknown nullability */ Option[String]) extends CollationCharacterSetApplicabilityFieldValue("character_set_schema", value)
  case class characterSetName(override val value: /* unknown nullability */ Option[String]) extends CollationCharacterSetApplicabilityFieldValue("character_set_name", value)
}

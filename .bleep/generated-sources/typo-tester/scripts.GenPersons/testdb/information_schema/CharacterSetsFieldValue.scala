package testdb
package information_schema



sealed abstract class CharacterSetsFieldValue[T](val name: String, val value: T)

object CharacterSetsFieldValue {
  case class characterSetCatalog(override val value: /* unknown nullability */ Option[String]) extends CharacterSetsFieldValue("character_set_catalog", value)
  case class characterSetSchema(override val value: /* unknown nullability */ Option[String]) extends CharacterSetsFieldValue("character_set_schema", value)
  case class characterSetName(override val value: /* unknown nullability */ Option[String]) extends CharacterSetsFieldValue("character_set_name", value)
  case class characterRepertoire(override val value: /* unknown nullability */ Option[String]) extends CharacterSetsFieldValue("character_repertoire", value)
  case class formOfUse(override val value: /* unknown nullability */ Option[String]) extends CharacterSetsFieldValue("form_of_use", value)
  case class defaultCollateCatalog(override val value: /* unknown nullability */ Option[String]) extends CharacterSetsFieldValue("default_collate_catalog", value)
  case class defaultCollateSchema(override val value: /* unknown nullability */ Option[String]) extends CharacterSetsFieldValue("default_collate_schema", value)
  case class defaultCollateName(override val value: /* unknown nullability */ Option[String]) extends CharacterSetsFieldValue("default_collate_name", value)
}

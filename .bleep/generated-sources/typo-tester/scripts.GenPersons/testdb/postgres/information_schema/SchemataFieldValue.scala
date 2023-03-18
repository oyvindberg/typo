package testdb
package postgres
package information_schema



sealed abstract class SchemataFieldValue[T](val name: String, val value: T)

object SchemataFieldValue {
  case class catalogName(override val value: /* unknown nullability */ Option[String]) extends SchemataFieldValue("catalog_name", value)
  case class schemaName(override val value: /* unknown nullability */ Option[String]) extends SchemataFieldValue("schema_name", value)
  case class schemaOwner(override val value: /* unknown nullability */ Option[String]) extends SchemataFieldValue("schema_owner", value)
  case class defaultCharacterSetCatalog(override val value: /* unknown nullability */ Option[String]) extends SchemataFieldValue("default_character_set_catalog", value)
  case class defaultCharacterSetSchema(override val value: /* unknown nullability */ Option[String]) extends SchemataFieldValue("default_character_set_schema", value)
  case class defaultCharacterSetName(override val value: /* unknown nullability */ Option[String]) extends SchemataFieldValue("default_character_set_name", value)
  case class sqlPath(override val value: /* unknown nullability */ Option[String]) extends SchemataFieldValue("sql_path", value)
}

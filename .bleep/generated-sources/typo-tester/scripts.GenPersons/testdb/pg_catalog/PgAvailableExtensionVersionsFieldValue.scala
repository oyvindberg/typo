package testdb
package pg_catalog



sealed abstract class PgAvailableExtensionVersionsFieldValue[T](val name: String, val value: T)

object PgAvailableExtensionVersionsFieldValue {
  case class name(override val value: /* unknown nullability */ Option[String]) extends PgAvailableExtensionVersionsFieldValue("name", value)
  case class version(override val value: /* unknown nullability */ Option[String]) extends PgAvailableExtensionVersionsFieldValue("version", value)
  case class installed(override val value: /* unknown nullability */ Option[Boolean]) extends PgAvailableExtensionVersionsFieldValue("installed", value)
  case class superuser(override val value: /* unknown nullability */ Option[Boolean]) extends PgAvailableExtensionVersionsFieldValue("superuser", value)
  case class trusted(override val value: /* unknown nullability */ Option[Boolean]) extends PgAvailableExtensionVersionsFieldValue("trusted", value)
  case class relocatable(override val value: /* unknown nullability */ Option[Boolean]) extends PgAvailableExtensionVersionsFieldValue("relocatable", value)
  case class schema(override val value: /* unknown nullability */ Option[String]) extends PgAvailableExtensionVersionsFieldValue("schema", value)
  case class requires(override val value: /* typo doesn't know how to translate: columnType: Array, columnTypeName: _name, columnClassName: java.sql.Array */ Any) extends PgAvailableExtensionVersionsFieldValue("requires", value)
  case class comment(override val value: /* unknown nullability */ Option[String]) extends PgAvailableExtensionVersionsFieldValue("comment", value)
}

package testdb
package postgres
package information_schema



sealed abstract class UserMappingOptionsFieldValue[T](val name: String, val value: T)

object UserMappingOptionsFieldValue {
  case class authorizationIdentifier(override val value: Option[String]) extends UserMappingOptionsFieldValue("authorization_identifier", value)
  case class foreignServerCatalog(override val value: Option[String]) extends UserMappingOptionsFieldValue("foreign_server_catalog", value)
  case class foreignServerName(override val value: Option[String]) extends UserMappingOptionsFieldValue("foreign_server_name", value)
  case class optionName(override val value: /* unknown nullability */ Option[String]) extends UserMappingOptionsFieldValue("option_name", value)
  case class optionValue(override val value: /* unknown nullability */ Option[String]) extends UserMappingOptionsFieldValue("option_value", value)
}

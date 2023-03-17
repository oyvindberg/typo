package testdb.information_schema



sealed abstract class UserMappingsFieldValue[T](val name: String, val value: T)

object UserMappingsFieldValue {
  case class authorizationIdentifier(override val value: Option[String]) extends UserMappingsFieldValue("authorization_identifier", value)
  case class foreignServerCatalog(override val value: Option[String]) extends UserMappingsFieldValue("foreign_server_catalog", value)
  case class foreignServerName(override val value: Option[String]) extends UserMappingsFieldValue("foreign_server_name", value)
}

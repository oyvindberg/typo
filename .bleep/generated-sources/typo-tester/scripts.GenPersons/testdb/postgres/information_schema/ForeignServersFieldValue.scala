package testdb
package postgres
package information_schema



sealed abstract class ForeignServersFieldValue[T](val name: String, val value: T)

object ForeignServersFieldValue {
  case class foreignServerCatalog(override val value: Option[String]) extends ForeignServersFieldValue("foreign_server_catalog", value)
  case class foreignServerName(override val value: Option[String]) extends ForeignServersFieldValue("foreign_server_name", value)
  case class foreignDataWrapperCatalog(override val value: Option[String]) extends ForeignServersFieldValue("foreign_data_wrapper_catalog", value)
  case class foreignDataWrapperName(override val value: Option[String]) extends ForeignServersFieldValue("foreign_data_wrapper_name", value)
  case class foreignServerType(override val value: Option[String]) extends ForeignServersFieldValue("foreign_server_type", value)
  case class foreignServerVersion(override val value: Option[String]) extends ForeignServersFieldValue("foreign_server_version", value)
  case class authorizationIdentifier(override val value: Option[String]) extends ForeignServersFieldValue("authorization_identifier", value)
}

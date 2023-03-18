package testdb
package postgres
package information_schema



sealed abstract class PgForeignServersFieldValue[T](val name: String, val value: T)

object PgForeignServersFieldValue {
  case class oid(override val value: Long) extends PgForeignServersFieldValue("oid", value)
  case class srvoptions(override val value: Option[Array[String]]) extends PgForeignServersFieldValue("srvoptions", value)
  case class foreignServerCatalog(override val value: /* unknown nullability */ Option[String]) extends PgForeignServersFieldValue("foreign_server_catalog", value)
  case class foreignServerName(override val value: /* unknown nullability */ Option[String]) extends PgForeignServersFieldValue("foreign_server_name", value)
  case class foreignDataWrapperCatalog(override val value: /* unknown nullability */ Option[String]) extends PgForeignServersFieldValue("foreign_data_wrapper_catalog", value)
  case class foreignDataWrapperName(override val value: /* unknown nullability */ Option[String]) extends PgForeignServersFieldValue("foreign_data_wrapper_name", value)
  case class foreignServerType(override val value: /* unknown nullability */ Option[String]) extends PgForeignServersFieldValue("foreign_server_type", value)
  case class foreignServerVersion(override val value: /* unknown nullability */ Option[String]) extends PgForeignServersFieldValue("foreign_server_version", value)
  case class authorizationIdentifier(override val value: /* unknown nullability */ Option[String]) extends PgForeignServersFieldValue("authorization_identifier", value)
}

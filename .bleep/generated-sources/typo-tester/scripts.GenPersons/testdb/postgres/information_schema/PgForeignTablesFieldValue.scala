package testdb
package postgres
package information_schema



sealed abstract class PgForeignTablesFieldValue[T](val name: String, val value: T)

object PgForeignTablesFieldValue {
  case class foreignTableCatalog(override val value: /* unknown nullability */ Option[String]) extends PgForeignTablesFieldValue("foreign_table_catalog", value)
  case class foreignTableSchema(override val value: /* unknown nullability */ Option[String]) extends PgForeignTablesFieldValue("foreign_table_schema", value)
  case class foreignTableName(override val value: /* unknown nullability */ Option[String]) extends PgForeignTablesFieldValue("foreign_table_name", value)
  case class ftoptions(override val value: Option[Array[String]]) extends PgForeignTablesFieldValue("ftoptions", value)
  case class foreignServerCatalog(override val value: /* unknown nullability */ Option[String]) extends PgForeignTablesFieldValue("foreign_server_catalog", value)
  case class foreignServerName(override val value: /* unknown nullability */ Option[String]) extends PgForeignTablesFieldValue("foreign_server_name", value)
  case class authorizationIdentifier(override val value: /* unknown nullability */ Option[String]) extends PgForeignTablesFieldValue("authorization_identifier", value)
}

package testdb
package information_schema



sealed abstract class ForeignTablesFieldValue[T](val name: String, val value: T)

object ForeignTablesFieldValue {
  case class foreignTableCatalog(override val value: Option[String]) extends ForeignTablesFieldValue("foreign_table_catalog", value)
  case class foreignTableSchema(override val value: Option[String]) extends ForeignTablesFieldValue("foreign_table_schema", value)
  case class foreignTableName(override val value: Option[String]) extends ForeignTablesFieldValue("foreign_table_name", value)
  case class foreignServerCatalog(override val value: Option[String]) extends ForeignTablesFieldValue("foreign_server_catalog", value)
  case class foreignServerName(override val value: Option[String]) extends ForeignTablesFieldValue("foreign_server_name", value)
}

package testdb
package postgres
package pg_catalog



sealed abstract class PgPublicationTablesFieldValue[T](val name: String, val value: T)

object PgPublicationTablesFieldValue {
  case class pubname(override val value: String) extends PgPublicationTablesFieldValue("pubname", value)
  case class schemaname(override val value: String) extends PgPublicationTablesFieldValue("schemaname", value)
  case class tablename(override val value: String) extends PgPublicationTablesFieldValue("tablename", value)
}

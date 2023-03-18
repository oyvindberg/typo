package testdb
package postgres
package information_schema



sealed abstract class PgForeignTableColumnsFieldValue[T](val name: String, val value: T)

object PgForeignTableColumnsFieldValue {
  case class nspname(override val value: String) extends PgForeignTableColumnsFieldValue("nspname", value)
  case class relname(override val value: String) extends PgForeignTableColumnsFieldValue("relname", value)
  case class attname(override val value: String) extends PgForeignTableColumnsFieldValue("attname", value)
  case class attfdwoptions(override val value: Option[Array[String]]) extends PgForeignTableColumnsFieldValue("attfdwoptions", value)
}

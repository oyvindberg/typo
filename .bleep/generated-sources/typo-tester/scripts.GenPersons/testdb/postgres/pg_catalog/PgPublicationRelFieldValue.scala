package testdb
package postgres
package pg_catalog



sealed abstract class PgPublicationRelFieldValue[T](val name: String, val value: T)

object PgPublicationRelFieldValue {
  case class oid(override val value: Long) extends PgPublicationRelFieldValue("oid", value)
  case class prpubid(override val value: Long) extends PgPublicationRelFieldValue("prpubid", value)
  case class prrelid(override val value: Long) extends PgPublicationRelFieldValue("prrelid", value)
}

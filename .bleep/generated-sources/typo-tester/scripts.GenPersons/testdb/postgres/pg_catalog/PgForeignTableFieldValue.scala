package testdb
package postgres
package pg_catalog



sealed abstract class PgForeignTableFieldValue[T](val name: String, val value: T)

object PgForeignTableFieldValue {
  case class ftrelid(override val value: PgForeignTableId) extends PgForeignTableFieldValue("ftrelid", value)
  case class ftserver(override val value: Long) extends PgForeignTableFieldValue("ftserver", value)
  case class ftoptions(override val value: Option[Array[String]]) extends PgForeignTableFieldValue("ftoptions", value)
}

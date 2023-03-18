package testdb
package postgres
package pg_catalog



sealed abstract class PgAmFieldValue[T](val name: String, val value: T)

object PgAmFieldValue {
  case class oid(override val value: PgAmId) extends PgAmFieldValue("oid", value)
  case class amname(override val value: String) extends PgAmFieldValue("amname", value)
  case class amhandler(override val value: String) extends PgAmFieldValue("amhandler", value)
  case class amtype(override val value: String) extends PgAmFieldValue("amtype", value)
}

package testdb
package postgres
package pg_catalog



sealed abstract class PgAttrdefFieldValue[T](val name: String, val value: T)

object PgAttrdefFieldValue {
  case class oid(override val value: PgAttrdefId) extends PgAttrdefFieldValue("oid", value)
  case class adrelid(override val value: Long) extends PgAttrdefFieldValue("adrelid", value)
  case class adnum(override val value: Short) extends PgAttrdefFieldValue("adnum", value)
  case class adbin(override val value: String) extends PgAttrdefFieldValue("adbin", value)
}

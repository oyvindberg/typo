package testdb
package postgres
package pg_catalog



sealed abstract class PgInheritsFieldValue[T](val name: String, val value: T)

object PgInheritsFieldValue {
  case class inhrelid(override val value: Long) extends PgInheritsFieldValue("inhrelid", value)
  case class inhparent(override val value: Long) extends PgInheritsFieldValue("inhparent", value)
  case class inhseqno(override val value: Int) extends PgInheritsFieldValue("inhseqno", value)
  case class inhdetachpending(override val value: Boolean) extends PgInheritsFieldValue("inhdetachpending", value)
}

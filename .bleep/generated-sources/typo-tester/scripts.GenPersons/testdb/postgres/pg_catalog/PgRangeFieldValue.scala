package testdb
package postgres
package pg_catalog



sealed abstract class PgRangeFieldValue[T](val name: String, val value: T)

object PgRangeFieldValue {
  case class rngtypid(override val value: Long) extends PgRangeFieldValue("rngtypid", value)
  case class rngsubtype(override val value: Long) extends PgRangeFieldValue("rngsubtype", value)
  case class rngmultitypid(override val value: Long) extends PgRangeFieldValue("rngmultitypid", value)
  case class rngcollation(override val value: Long) extends PgRangeFieldValue("rngcollation", value)
  case class rngsubopc(override val value: Long) extends PgRangeFieldValue("rngsubopc", value)
  case class rngcanonical(override val value: String) extends PgRangeFieldValue("rngcanonical", value)
  case class rngsubdiff(override val value: String) extends PgRangeFieldValue("rngsubdiff", value)
}

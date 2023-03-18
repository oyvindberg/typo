package testdb
package postgres
package pg_catalog



sealed abstract class PgAmprocFieldValue[T](val name: String, val value: T)

object PgAmprocFieldValue {
  case class oid(override val value: Long) extends PgAmprocFieldValue("oid", value)
  case class amprocfamily(override val value: Long) extends PgAmprocFieldValue("amprocfamily", value)
  case class amproclefttype(override val value: Long) extends PgAmprocFieldValue("amproclefttype", value)
  case class amprocrighttype(override val value: Long) extends PgAmprocFieldValue("amprocrighttype", value)
  case class amprocnum(override val value: Short) extends PgAmprocFieldValue("amprocnum", value)
  case class amproc(override val value: String) extends PgAmprocFieldValue("amproc", value)
}

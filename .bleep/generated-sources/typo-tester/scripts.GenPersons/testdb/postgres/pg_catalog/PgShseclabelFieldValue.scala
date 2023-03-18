package testdb
package postgres
package pg_catalog



sealed abstract class PgShseclabelFieldValue[T](val name: String, val value: T)

object PgShseclabelFieldValue {
  case class objoid(override val value: Long) extends PgShseclabelFieldValue("objoid", value)
  case class classoid(override val value: Long) extends PgShseclabelFieldValue("classoid", value)
  case class provider(override val value: String) extends PgShseclabelFieldValue("provider", value)
  case class label(override val value: String) extends PgShseclabelFieldValue("label", value)
}

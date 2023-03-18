package testdb
package postgres
package pg_catalog



sealed abstract class PgSubscriptionRelFieldValue[T](val name: String, val value: T)

object PgSubscriptionRelFieldValue {
  case class srsubid(override val value: Long) extends PgSubscriptionRelFieldValue("srsubid", value)
  case class srrelid(override val value: Long) extends PgSubscriptionRelFieldValue("srrelid", value)
  case class srsubstate(override val value: String) extends PgSubscriptionRelFieldValue("srsubstate", value)
  case class srsublsn(override val value: Option[String]) extends PgSubscriptionRelFieldValue("srsublsn", value)
}

package testdb
package postgres
package pg_catalog



sealed abstract class PgEventTriggerFieldValue[T](val name: String, val value: T)

object PgEventTriggerFieldValue {
  case class oid(override val value: Long) extends PgEventTriggerFieldValue("oid", value)
  case class evtname(override val value: String) extends PgEventTriggerFieldValue("evtname", value)
  case class evtevent(override val value: String) extends PgEventTriggerFieldValue("evtevent", value)
  case class evtowner(override val value: Long) extends PgEventTriggerFieldValue("evtowner", value)
  case class evtfoid(override val value: Long) extends PgEventTriggerFieldValue("evtfoid", value)
  case class evtenabled(override val value: String) extends PgEventTriggerFieldValue("evtenabled", value)
  case class evttags(override val value: Option[Array[String]]) extends PgEventTriggerFieldValue("evttags", value)
}

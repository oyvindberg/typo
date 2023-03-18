package testdb
package postgres
package pg_catalog



sealed abstract class PgTriggerFieldValue[T](val name: String, val value: T)

object PgTriggerFieldValue {
  case class oid(override val value: PgTriggerId) extends PgTriggerFieldValue("oid", value)
  case class tgrelid(override val value: Long) extends PgTriggerFieldValue("tgrelid", value)
  case class tgparentid(override val value: Long) extends PgTriggerFieldValue("tgparentid", value)
  case class tgname(override val value: String) extends PgTriggerFieldValue("tgname", value)
  case class tgfoid(override val value: Long) extends PgTriggerFieldValue("tgfoid", value)
  case class tgtype(override val value: Short) extends PgTriggerFieldValue("tgtype", value)
  case class tgenabled(override val value: String) extends PgTriggerFieldValue("tgenabled", value)
  case class tgisinternal(override val value: Boolean) extends PgTriggerFieldValue("tgisinternal", value)
  case class tgconstrrelid(override val value: Long) extends PgTriggerFieldValue("tgconstrrelid", value)
  case class tgconstrindid(override val value: Long) extends PgTriggerFieldValue("tgconstrindid", value)
  case class tgconstraint(override val value: Long) extends PgTriggerFieldValue("tgconstraint", value)
  case class tgdeferrable(override val value: Boolean) extends PgTriggerFieldValue("tgdeferrable", value)
  case class tginitdeferred(override val value: Boolean) extends PgTriggerFieldValue("tginitdeferred", value)
  case class tgnargs(override val value: Short) extends PgTriggerFieldValue("tgnargs", value)
  case class tgattr(override val value: String) extends PgTriggerFieldValue("tgattr", value)
  case class tgargs(override val value: String) extends PgTriggerFieldValue("tgargs", value)
  case class tgqual(override val value: Option[String]) extends PgTriggerFieldValue("tgqual", value)
  case class tgoldtable(override val value: Option[String]) extends PgTriggerFieldValue("tgoldtable", value)
  case class tgnewtable(override val value: Option[String]) extends PgTriggerFieldValue("tgnewtable", value)
}

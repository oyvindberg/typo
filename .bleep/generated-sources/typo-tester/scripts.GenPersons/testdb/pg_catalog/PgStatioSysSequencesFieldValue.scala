package testdb
package pg_catalog



sealed abstract class PgStatioSysSequencesFieldValue[T](val name: String, val value: T)

object PgStatioSysSequencesFieldValue {
  case class relid(override val value: Option[Long]) extends PgStatioSysSequencesFieldValue("relid", value)
  case class schemaname(override val value: Option[String]) extends PgStatioSysSequencesFieldValue("schemaname", value)
  case class relname(override val value: Option[String]) extends PgStatioSysSequencesFieldValue("relname", value)
  case class blksRead(override val value: Option[Long]) extends PgStatioSysSequencesFieldValue("blks_read", value)
  case class blksHit(override val value: Option[Long]) extends PgStatioSysSequencesFieldValue("blks_hit", value)
}

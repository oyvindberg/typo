package testdb
package postgres
package pg_catalog



sealed abstract class PgStatioAllSequencesFieldValue[T](val name: String, val value: T)

object PgStatioAllSequencesFieldValue {
  case class relid(override val value: Long) extends PgStatioAllSequencesFieldValue("relid", value)
  case class schemaname(override val value: String) extends PgStatioAllSequencesFieldValue("schemaname", value)
  case class relname(override val value: String) extends PgStatioAllSequencesFieldValue("relname", value)
  case class blksRead(override val value: /* unknown nullability */ Option[Long]) extends PgStatioAllSequencesFieldValue("blks_read", value)
  case class blksHit(override val value: /* unknown nullability */ Option[Long]) extends PgStatioAllSequencesFieldValue("blks_hit", value)
}

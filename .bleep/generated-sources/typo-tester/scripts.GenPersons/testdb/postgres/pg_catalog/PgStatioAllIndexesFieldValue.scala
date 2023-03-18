package testdb
package postgres
package pg_catalog



sealed abstract class PgStatioAllIndexesFieldValue[T](val name: String, val value: T)

object PgStatioAllIndexesFieldValue {
  case class relid(override val value: Long) extends PgStatioAllIndexesFieldValue("relid", value)
  case class indexrelid(override val value: Long) extends PgStatioAllIndexesFieldValue("indexrelid", value)
  case class schemaname(override val value: String) extends PgStatioAllIndexesFieldValue("schemaname", value)
  case class relname(override val value: String) extends PgStatioAllIndexesFieldValue("relname", value)
  case class indexrelname(override val value: String) extends PgStatioAllIndexesFieldValue("indexrelname", value)
  case class idxBlksRead(override val value: /* unknown nullability */ Option[Long]) extends PgStatioAllIndexesFieldValue("idx_blks_read", value)
  case class idxBlksHit(override val value: /* unknown nullability */ Option[Long]) extends PgStatioAllIndexesFieldValue("idx_blks_hit", value)
}

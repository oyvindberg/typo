package testdb.pg_catalog



sealed abstract class PgStatioAllTablesFieldValue[T](val name: String, val value: T)

object PgStatioAllTablesFieldValue {
  case class relid(override val value: Long) extends PgStatioAllTablesFieldValue("relid", value)
  case class schemaname(override val value: String) extends PgStatioAllTablesFieldValue("schemaname", value)
  case class relname(override val value: String) extends PgStatioAllTablesFieldValue("relname", value)
  case class heapBlksRead(override val value: /* unknown nullability */ Option[Long]) extends PgStatioAllTablesFieldValue("heap_blks_read", value)
  case class heapBlksHit(override val value: /* unknown nullability */ Option[Long]) extends PgStatioAllTablesFieldValue("heap_blks_hit", value)
  case class idxBlksRead(override val value: /* unknown nullability */ Option[Long]) extends PgStatioAllTablesFieldValue("idx_blks_read", value)
  case class idxBlksHit(override val value: /* unknown nullability */ Option[Long]) extends PgStatioAllTablesFieldValue("idx_blks_hit", value)
  case class toastBlksRead(override val value: /* unknown nullability */ Option[Long]) extends PgStatioAllTablesFieldValue("toast_blks_read", value)
  case class toastBlksHit(override val value: /* unknown nullability */ Option[Long]) extends PgStatioAllTablesFieldValue("toast_blks_hit", value)
  case class tidxBlksRead(override val value: /* unknown nullability */ Option[Long]) extends PgStatioAllTablesFieldValue("tidx_blks_read", value)
  case class tidxBlksHit(override val value: /* unknown nullability */ Option[Long]) extends PgStatioAllTablesFieldValue("tidx_blks_hit", value)
}

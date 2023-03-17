package testdb.pg_catalog



sealed abstract class PgStatXactUserTablesFieldValue[T](val name: String, val value: T)

object PgStatXactUserTablesFieldValue {
  case class relid(override val value: Option[Long]) extends PgStatXactUserTablesFieldValue("relid", value)
  case class schemaname(override val value: Option[String]) extends PgStatXactUserTablesFieldValue("schemaname", value)
  case class relname(override val value: Option[String]) extends PgStatXactUserTablesFieldValue("relname", value)
  case class seqScan(override val value: Option[Long]) extends PgStatXactUserTablesFieldValue("seq_scan", value)
  case class seqTupRead(override val value: Option[Long]) extends PgStatXactUserTablesFieldValue("seq_tup_read", value)
  case class idxScan(override val value: Option[Long]) extends PgStatXactUserTablesFieldValue("idx_scan", value)
  case class idxTupFetch(override val value: Option[Long]) extends PgStatXactUserTablesFieldValue("idx_tup_fetch", value)
  case class nTupIns(override val value: Option[Long]) extends PgStatXactUserTablesFieldValue("n_tup_ins", value)
  case class nTupUpd(override val value: Option[Long]) extends PgStatXactUserTablesFieldValue("n_tup_upd", value)
  case class nTupDel(override val value: Option[Long]) extends PgStatXactUserTablesFieldValue("n_tup_del", value)
  case class nTupHotUpd(override val value: Option[Long]) extends PgStatXactUserTablesFieldValue("n_tup_hot_upd", value)
}

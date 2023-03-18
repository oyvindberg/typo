package testdb
package postgres
package pg_catalog



sealed abstract class PgStatProgressAnalyzeFieldValue[T](val name: String, val value: T)

object PgStatProgressAnalyzeFieldValue {
  case class pid(override val value: /* unknown nullability */ Option[Int]) extends PgStatProgressAnalyzeFieldValue("pid", value)
  case class datid(override val value: /* unknown nullability */ Option[Long]) extends PgStatProgressAnalyzeFieldValue("datid", value)
  case class datname(override val value: String) extends PgStatProgressAnalyzeFieldValue("datname", value)
  case class relid(override val value: /* unknown nullability */ Option[Long]) extends PgStatProgressAnalyzeFieldValue("relid", value)
  case class phase(override val value: /* unknown nullability */ Option[String]) extends PgStatProgressAnalyzeFieldValue("phase", value)
  case class sampleBlksTotal(override val value: /* unknown nullability */ Option[Long]) extends PgStatProgressAnalyzeFieldValue("sample_blks_total", value)
  case class sampleBlksScanned(override val value: /* unknown nullability */ Option[Long]) extends PgStatProgressAnalyzeFieldValue("sample_blks_scanned", value)
  case class extStatsTotal(override val value: /* unknown nullability */ Option[Long]) extends PgStatProgressAnalyzeFieldValue("ext_stats_total", value)
  case class extStatsComputed(override val value: /* unknown nullability */ Option[Long]) extends PgStatProgressAnalyzeFieldValue("ext_stats_computed", value)
  case class childTablesTotal(override val value: /* unknown nullability */ Option[Long]) extends PgStatProgressAnalyzeFieldValue("child_tables_total", value)
  case class childTablesDone(override val value: /* unknown nullability */ Option[Long]) extends PgStatProgressAnalyzeFieldValue("child_tables_done", value)
  case class currentChildTableRelid(override val value: /* unknown nullability */ Option[Long]) extends PgStatProgressAnalyzeFieldValue("current_child_table_relid", value)
}

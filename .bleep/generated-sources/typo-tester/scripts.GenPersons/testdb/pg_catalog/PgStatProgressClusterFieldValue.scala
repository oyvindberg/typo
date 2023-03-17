package testdb
package pg_catalog



sealed abstract class PgStatProgressClusterFieldValue[T](val name: String, val value: T)

object PgStatProgressClusterFieldValue {
  case class pid(override val value: /* unknown nullability */ Option[Int]) extends PgStatProgressClusterFieldValue("pid", value)
  case class datid(override val value: /* unknown nullability */ Option[Long]) extends PgStatProgressClusterFieldValue("datid", value)
  case class datname(override val value: String) extends PgStatProgressClusterFieldValue("datname", value)
  case class relid(override val value: /* unknown nullability */ Option[Long]) extends PgStatProgressClusterFieldValue("relid", value)
  case class command(override val value: /* unknown nullability */ Option[String]) extends PgStatProgressClusterFieldValue("command", value)
  case class phase(override val value: /* unknown nullability */ Option[String]) extends PgStatProgressClusterFieldValue("phase", value)
  case class clusterIndexRelid(override val value: /* unknown nullability */ Option[Long]) extends PgStatProgressClusterFieldValue("cluster_index_relid", value)
  case class heapTuplesScanned(override val value: /* unknown nullability */ Option[Long]) extends PgStatProgressClusterFieldValue("heap_tuples_scanned", value)
  case class heapTuplesWritten(override val value: /* unknown nullability */ Option[Long]) extends PgStatProgressClusterFieldValue("heap_tuples_written", value)
  case class heapBlksTotal(override val value: /* unknown nullability */ Option[Long]) extends PgStatProgressClusterFieldValue("heap_blks_total", value)
  case class heapBlksScanned(override val value: /* unknown nullability */ Option[Long]) extends PgStatProgressClusterFieldValue("heap_blks_scanned", value)
  case class indexRebuildCount(override val value: /* unknown nullability */ Option[Long]) extends PgStatProgressClusterFieldValue("index_rebuild_count", value)
}

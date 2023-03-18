package testdb
package postgres
package pg_catalog



sealed abstract class PgStatProgressCopyFieldValue[T](val name: String, val value: T)

object PgStatProgressCopyFieldValue {
  case class pid(override val value: /* unknown nullability */ Option[Int]) extends PgStatProgressCopyFieldValue("pid", value)
  case class datid(override val value: /* unknown nullability */ Option[Long]) extends PgStatProgressCopyFieldValue("datid", value)
  case class datname(override val value: String) extends PgStatProgressCopyFieldValue("datname", value)
  case class relid(override val value: /* unknown nullability */ Option[Long]) extends PgStatProgressCopyFieldValue("relid", value)
  case class command(override val value: /* unknown nullability */ Option[String]) extends PgStatProgressCopyFieldValue("command", value)
  case class `type`(override val value: /* unknown nullability */ Option[String]) extends PgStatProgressCopyFieldValue("type", value)
  case class bytesProcessed(override val value: /* unknown nullability */ Option[Long]) extends PgStatProgressCopyFieldValue("bytes_processed", value)
  case class bytesTotal(override val value: /* unknown nullability */ Option[Long]) extends PgStatProgressCopyFieldValue("bytes_total", value)
  case class tuplesProcessed(override val value: /* unknown nullability */ Option[Long]) extends PgStatProgressCopyFieldValue("tuples_processed", value)
  case class tuplesExcluded(override val value: /* unknown nullability */ Option[Long]) extends PgStatProgressCopyFieldValue("tuples_excluded", value)
}

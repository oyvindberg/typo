package testdb
package pg_catalog



sealed abstract class PgStatXactUserFunctionsFieldValue[T](val name: String, val value: T)

object PgStatXactUserFunctionsFieldValue {
  case class funcid(override val value: Long) extends PgStatXactUserFunctionsFieldValue("funcid", value)
  case class schemaname(override val value: String) extends PgStatXactUserFunctionsFieldValue("schemaname", value)
  case class funcname(override val value: String) extends PgStatXactUserFunctionsFieldValue("funcname", value)
  case class calls(override val value: /* unknown nullability */ Option[Long]) extends PgStatXactUserFunctionsFieldValue("calls", value)
  case class totalTime(override val value: /* unknown nullability */ Option[Double]) extends PgStatXactUserFunctionsFieldValue("total_time", value)
  case class selfTime(override val value: /* unknown nullability */ Option[Double]) extends PgStatXactUserFunctionsFieldValue("self_time", value)
}

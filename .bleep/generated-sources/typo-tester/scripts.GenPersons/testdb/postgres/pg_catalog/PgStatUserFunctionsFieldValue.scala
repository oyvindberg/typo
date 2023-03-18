package testdb
package postgres
package pg_catalog



sealed abstract class PgStatUserFunctionsFieldValue[T](val name: String, val value: T)

object PgStatUserFunctionsFieldValue {
  case class funcid(override val value: Long) extends PgStatUserFunctionsFieldValue("funcid", value)
  case class schemaname(override val value: String) extends PgStatUserFunctionsFieldValue("schemaname", value)
  case class funcname(override val value: String) extends PgStatUserFunctionsFieldValue("funcname", value)
  case class calls(override val value: /* unknown nullability */ Option[Long]) extends PgStatUserFunctionsFieldValue("calls", value)
  case class totalTime(override val value: /* unknown nullability */ Option[Double]) extends PgStatUserFunctionsFieldValue("total_time", value)
  case class selfTime(override val value: /* unknown nullability */ Option[Double]) extends PgStatUserFunctionsFieldValue("self_time", value)
}

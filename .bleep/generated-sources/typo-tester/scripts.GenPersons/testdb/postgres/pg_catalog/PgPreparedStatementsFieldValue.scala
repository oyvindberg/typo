package testdb
package postgres
package pg_catalog

import java.time.LocalDateTime

sealed abstract class PgPreparedStatementsFieldValue[T](val name: String, val value: T)

object PgPreparedStatementsFieldValue {
  case class name(override val value: /* unknown nullability */ Option[String]) extends PgPreparedStatementsFieldValue("name", value)
  case class statement(override val value: /* unknown nullability */ Option[String]) extends PgPreparedStatementsFieldValue("statement", value)
  case class prepareTime(override val value: /* unknown nullability */ Option[LocalDateTime]) extends PgPreparedStatementsFieldValue("prepare_time", value)
  case class parameterTypes(override val value: /* unknown nullability */ Option[Array[String]]) extends PgPreparedStatementsFieldValue("parameter_types", value)
  case class fromSql(override val value: /* unknown nullability */ Option[Boolean]) extends PgPreparedStatementsFieldValue("from_sql", value)
  case class genericPlans(override val value: /* unknown nullability */ Option[Long]) extends PgPreparedStatementsFieldValue("generic_plans", value)
  case class customPlans(override val value: /* unknown nullability */ Option[Long]) extends PgPreparedStatementsFieldValue("custom_plans", value)
}

package testdb
package postgres
package pg_catalog



sealed abstract class PgStatisticExtDataFieldValue[T](val name: String, val value: T)

object PgStatisticExtDataFieldValue {
  case class stxoid(override val value: PgStatisticExtDataId) extends PgStatisticExtDataFieldValue("stxoid", value)
  case class stxdndistinct(override val value: Option[String]) extends PgStatisticExtDataFieldValue("stxdndistinct", value)
  case class stxddependencies(override val value: Option[String]) extends PgStatisticExtDataFieldValue("stxddependencies", value)
  case class stxdmcv(override val value: Option[String]) extends PgStatisticExtDataFieldValue("stxdmcv", value)
  case class stxdexpr(override val value: Option[Array[String]]) extends PgStatisticExtDataFieldValue("stxdexpr", value)
}

package testdb
package postgres
package pg_catalog



sealed abstract class PgTsConfigMapFieldValue[T](val name: String, val value: T)

object PgTsConfigMapFieldValue {
  case class mapcfg(override val value: Long) extends PgTsConfigMapFieldValue("mapcfg", value)
  case class maptokentype(override val value: Int) extends PgTsConfigMapFieldValue("maptokentype", value)
  case class mapseqno(override val value: Int) extends PgTsConfigMapFieldValue("mapseqno", value)
  case class mapdict(override val value: Long) extends PgTsConfigMapFieldValue("mapdict", value)
}

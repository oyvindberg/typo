package testdb.pg_catalog



sealed abstract class PgViewsFieldValue[T](val name: String, val value: T)

object PgViewsFieldValue {
  case class schemaname(override val value: String) extends PgViewsFieldValue("schemaname", value)
  case class viewname(override val value: String) extends PgViewsFieldValue("viewname", value)
  case class viewowner(override val value: /* unknown nullability */ Option[String]) extends PgViewsFieldValue("viewowner", value)
  case class definition(override val value: /* unknown nullability */ Option[String]) extends PgViewsFieldValue("definition", value)
}

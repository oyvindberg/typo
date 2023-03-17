package testdb.pg_catalog



sealed abstract class PgConfigFieldValue[T](val name: String, val value: T)

object PgConfigFieldValue {
  case class name(override val value: /* unknown nullability */ Option[String]) extends PgConfigFieldValue("name", value)
  case class setting(override val value: /* unknown nullability */ Option[String]) extends PgConfigFieldValue("setting", value)
}

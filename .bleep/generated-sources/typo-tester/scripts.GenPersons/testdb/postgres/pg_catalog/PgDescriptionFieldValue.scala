package testdb
package postgres
package pg_catalog



sealed abstract class PgDescriptionFieldValue[T](val name: String, val value: T)

object PgDescriptionFieldValue {
  case class objoid(override val value: Long) extends PgDescriptionFieldValue("objoid", value)
  case class classoid(override val value: Long) extends PgDescriptionFieldValue("classoid", value)
  case class objsubid(override val value: Int) extends PgDescriptionFieldValue("objsubid", value)
  case class description(override val value: String) extends PgDescriptionFieldValue("description", value)
}

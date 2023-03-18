package testdb
package hardcoded
package compositepk



sealed abstract class PersonFieldValue[T](val name: String, val value: T)

object PersonFieldValue {
  case class one(override val value: Long) extends PersonFieldValue("one", value)
  case class two(override val value: Option[String]) extends PersonFieldValue("two", value)
  case class name(override val value: Option[String]) extends PersonFieldValue("name", value)
}

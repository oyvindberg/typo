package testdb
package hardcoded
package myschema



sealed abstract class MaritalStatusFieldValue[T](val name: String, val value: T)

object MaritalStatusFieldValue {
  case class id(override val value: MaritalStatusId) extends MaritalStatusFieldValue("id", value)
}

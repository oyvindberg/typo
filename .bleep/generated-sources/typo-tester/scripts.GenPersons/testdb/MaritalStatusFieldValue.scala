package testdb

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.ToStatement

sealed abstract class MaritalStatusFieldValue[T: ToStatement](val name: String, val value: T) {
  def toNamedParameter: NamedParameter = NamedParameter(name, ParameterValue.toParameterValue(value))
}

object MaritalStatusFieldValue {
  case class id(override val value: MaritalStatusId) extends MaritalStatusFieldValue("id", value)
}

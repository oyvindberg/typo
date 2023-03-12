package testdb

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.ToStatement

sealed abstract class FootballClubFieldValue[T: ToStatement](val name: String, val value: T) {
  def toNamedParameter: NamedParameter = NamedParameter(name, ParameterValue.toParameterValue(value))
}

object FootballClubFieldValue {
  case class id(override val value: FootballClubId) extends FootballClubFieldValue("id", value)
  case class name(override val value: String) extends FootballClubFieldValue("name", value)
}

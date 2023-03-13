package testdb



sealed abstract class FootballClubFieldValue[T](val name: String, val value: T)

object FootballClubFieldValue {
  case class id(override val value: FootballClubId) extends FootballClubFieldValue("id", value)
  case class name(override val value: String) extends FootballClubFieldValue("name", value)
}

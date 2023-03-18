package testdb
package hardcoded
package myschema



sealed abstract class PersonFieldValue[T](val name: String, val value: T)

object PersonFieldValue {
  case class id(override val value: PersonId) extends PersonFieldValue("id", value)
  case class favouriteFootballClubId(override val value: String) extends PersonFieldValue("favourite_football_club_id", value)
  case class name(override val value: String) extends PersonFieldValue("name", value)
  case class nickName(override val value: Option[String]) extends PersonFieldValue("nick_name", value)
  case class blogUrl(override val value: Option[String]) extends PersonFieldValue("blog_url", value)
  case class email(override val value: String) extends PersonFieldValue("email", value)
  case class phone(override val value: String) extends PersonFieldValue("phone", value)
  case class likesPizza(override val value: Boolean) extends PersonFieldValue("likes_pizza", value)
  case class maritalStatusId(override val value: String) extends PersonFieldValue("marital_status_id", value)
  case class workEmail(override val value: Option[String]) extends PersonFieldValue("work_email", value)
  case class sector(override val value: SectorEnum) extends PersonFieldValue("sector", value)
}

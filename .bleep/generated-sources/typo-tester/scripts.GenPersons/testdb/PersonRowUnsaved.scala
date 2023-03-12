package testdb

import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PersonRowUnsaved(
  favouriteFootballClubId: String,
  name: String,
  nickName: Option[String],
  blogUrl: Option[String],
  email: String,
  phone: String,
  likesPizza: Boolean,
  maritalStatusId: String,
  workEmail: Option[String],
  sector: SectorEnum
)
object PersonRowUnsaved {
  implicit val oFormat: OFormat[PersonRowUnsaved] = Json.format
}

package testdb

import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class FootballClubRowUnsaved(
  name: String
)
object FootballClubRowUnsaved {
  implicit val oFormat: OFormat[FootballClubRowUnsaved] = Json.format
}

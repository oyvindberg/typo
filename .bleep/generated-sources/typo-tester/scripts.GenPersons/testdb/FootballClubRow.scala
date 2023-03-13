package testdb

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class FootballClubRow(
  id: FootballClubId,
  name: String
)
object FootballClubRow {
  implicit val rowParser: RowParser[FootballClubRow] = { row =>
    Success(
      FootballClubRow(
        id = row[FootballClubId]("id"),
        name = row[String]("name")
      )
    )
  }

  implicit val oFormat: OFormat[FootballClubRow] = Json.format
}

package testdb

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PersonRow(
  id: PersonId,
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
object PersonRow {
  implicit val rowParser: RowParser[PersonRow] = { row =>
    Success(
      PersonRow(
        id = row[PersonId]("id"),
        favouriteFootballClubId = row[String]("favourite_football_club_id"),
        name = row[String]("name"),
        nickName = row[Option[String]]("nick_name"),
        blogUrl = row[Option[String]]("blog_url"),
        email = row[String]("email"),
        phone = row[String]("phone"),
        likesPizza = row[Boolean]("likes_pizza"),
        maritalStatusId = row[String]("marital_status_id"),
        workEmail = row[Option[String]]("work_email"),
        sector = row[SectorEnum]("sector")
      )
    )
  }
  implicit val oFormat: OFormat[PersonRow] = Json.format
}

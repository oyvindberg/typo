package testdb.myschema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

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

  implicit val oFormat: OFormat[PersonRow] = new OFormat[PersonRow]{
    override def writes(o: PersonRow): JsObject =
      Json.obj(
        "id" -> o.id,
      "favourite_football_club_id" -> o.favouriteFootballClubId,
      "name" -> o.name,
      "nick_name" -> o.nickName,
      "blog_url" -> o.blogUrl,
      "email" -> o.email,
      "phone" -> o.phone,
      "likes_pizza" -> o.likesPizza,
      "marital_status_id" -> o.maritalStatusId,
      "work_email" -> o.workEmail,
      "sector" -> o.sector
      )

    override def reads(json: JsValue): JsResult[PersonRow] = {
      JsResult.fromTry(
        Try(
          PersonRow(
            id = json.\("id").as[PersonId],
            favouriteFootballClubId = json.\("favourite_football_club_id").as[String],
            name = json.\("name").as[String],
            nickName = json.\("nick_name").toOption.map(_.as[String]),
            blogUrl = json.\("blog_url").toOption.map(_.as[String]),
            email = json.\("email").as[String],
            phone = json.\("phone").as[String],
            likesPizza = json.\("likes_pizza").as[Boolean],
            maritalStatusId = json.\("marital_status_id").as[String],
            workEmail = json.\("work_email").toOption.map(_.as[String]),
            sector = json.\("sector").as[SectorEnum]
          )
        )
      )
    }
  }
}

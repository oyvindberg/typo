package testdb
package hardcoded
package myschema

import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try
import testdb.hardcoded.Defaulted

case class PersonRowUnsaved(
  favouriteFootballClubId: String,
  name: String,
  nickName: Option[String],
  blogUrl: Option[String],
  email: String,
  phone: String,
  likesPizza: Boolean,
  maritalStatusId: Defaulted[String],
  workEmail: Option[String],
  sector: Defaulted[SectorEnum]
)
object PersonRowUnsaved {
  implicit val oFormat: OFormat[PersonRowUnsaved] = new OFormat[PersonRowUnsaved]{
    override def writes(o: PersonRowUnsaved): JsObject =
      Json.obj(
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

    override def reads(json: JsValue): JsResult[PersonRowUnsaved] = {
      JsResult.fromTry(
        Try(
          PersonRowUnsaved(
            favouriteFootballClubId = json.\("favourite_football_club_id").as[String],
            name = json.\("name").as[String],
            nickName = json.\("nick_name").toOption.map(_.as[String]),
            blogUrl = json.\("blog_url").toOption.map(_.as[String]),
            email = json.\("email").as[String],
            phone = json.\("phone").as[String],
            likesPizza = json.\("likes_pizza").as[Boolean],
            maritalStatusId = json.\("marital_status_id").as[Defaulted[String]],
            workEmail = json.\("work_email").toOption.map(_.as[String]),
            sector = json.\("sector").as[Defaulted[SectorEnum]]
          )
        )
      )
    }
  }
}

/**
 * File automatically generated by `typo` for its own test suite.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN
 */
package testdb
package hardcoded
package myschema
package person

import io.circe.Decoder
import io.circe.Encoder
import io.circe.HCursor
import io.circe.Json
import testdb.hardcoded.myschema.Sector
import testdb.hardcoded.myschema.football_club.FootballClubId
import testdb.hardcoded.myschema.marital_status.MaritalStatusId

case class PersonRow(
  id: PersonId,
  /** Points to [[football_club.FootballClubRow.id]] */
  favouriteFootballClubId: FootballClubId,
  name: String,
  nickName: Option[String],
  blogUrl: Option[String],
  email: String,
  phone: String,
  likesPizza: Boolean,
  /** Points to [[marital_status.MaritalStatusRow.id]] */
  maritalStatusId: MaritalStatusId,
  workEmail: Option[String],
  sector: Sector
)

object PersonRow {
  implicit val decoder: Decoder[PersonRow] =
    (c: HCursor) =>
      for {
        id <- c.downField("id").as[PersonId]
        favouriteFootballClubId <- c.downField("favourite_football_club_id").as[FootballClubId]
        name <- c.downField("name").as[String]
        nickName <- c.downField("nick_name").as[Option[String]]
        blogUrl <- c.downField("blog_url").as[Option[String]]
        email <- c.downField("email").as[String]
        phone <- c.downField("phone").as[String]
        likesPizza <- c.downField("likes_pizza").as[Boolean]
        maritalStatusId <- c.downField("marital_status_id").as[MaritalStatusId]
        workEmail <- c.downField("work_email").as[Option[String]]
        sector <- c.downField("sector").as[Sector]
      } yield PersonRow(id, favouriteFootballClubId, name, nickName, blogUrl, email, phone, likesPizza, maritalStatusId, workEmail, sector)
  implicit val encoder: Encoder[PersonRow] = {
    import io.circe.syntax._
    row =>
      Json.obj(
        "id" := row.id,
        "favourite_football_club_id" := row.favouriteFootballClubId,
        "name" := row.name,
        "nick_name" := row.nickName,
        "blog_url" := row.blogUrl,
        "email" := row.email,
        "phone" := row.phone,
        "likes_pizza" := row.likesPizza,
        "marital_status_id" := row.maritalStatusId,
        "work_email" := row.workEmail,
        "sector" := row.sector
      )}
}
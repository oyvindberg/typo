/**
 * File automatically generated by `typo` for its own test suite.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN
 */
package testdb
package hardcoded
package myschema
package person

import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.OWrites
import play.api.libs.json.Reads
import play.api.libs.json.Writes
import scala.collection.immutable.ListMap
import scala.util.Try
import testdb.hardcoded.Defaulted
import testdb.hardcoded.myschema.Number
import testdb.hardcoded.myschema.Sector
import testdb.hardcoded.myschema.football_club.FootballClubId
import testdb.hardcoded.myschema.marital_status.MaritalStatusId

/** This class corresponds to a row in table `myschema.person` which has not been persisted yet */
case class PersonRowUnsaved(
  /** Points to [[football_club.FootballClubRow.id]] */
  favouriteFootballClubId: FootballClubId,
  name: /* max 100 chars */ String,
  nickName: Option[/* max 30 chars */ String],
  blogUrl: Option[/* max 100 chars */ String],
  email: /* max 254 chars */ String,
  phone: /* max 8 chars */ String,
  likesPizza: Boolean,
  workEmail: Option[/* max 254 chars */ String],
  /** Default: auto-increment */
  id: Defaulted[PersonId] = Defaulted.UseDefault,
  /** Default: some-value
      Points to [[marital_status.MaritalStatusRow.id]] */
  maritalStatusId: Defaulted[MaritalStatusId] = Defaulted.UseDefault,
  /** Default: PUBLIC */
  sector: Defaulted[Sector] = Defaulted.UseDefault,
  /** Default: one */
  favoriteNumber: Defaulted[Number] = Defaulted.UseDefault
) {
  def toRow(idDefault: => PersonId, maritalStatusIdDefault: => MaritalStatusId, sectorDefault: => Sector, favoriteNumberDefault: => Number): PersonRow =
    PersonRow(
      favouriteFootballClubId = favouriteFootballClubId,
      name = name,
      nickName = nickName,
      blogUrl = blogUrl,
      email = email,
      phone = phone,
      likesPizza = likesPizza,
      workEmail = workEmail,
      id = id match {
             case Defaulted.UseDefault => idDefault
             case Defaulted.Provided(value) => value
           },
      maritalStatusId = maritalStatusId match {
                          case Defaulted.UseDefault => maritalStatusIdDefault
                          case Defaulted.Provided(value) => value
                        },
      sector = sector match {
                 case Defaulted.UseDefault => sectorDefault
                 case Defaulted.Provided(value) => value
               },
      favoriteNumber = favoriteNumber match {
                         case Defaulted.UseDefault => favoriteNumberDefault
                         case Defaulted.Provided(value) => value
                       }
    )
}
object PersonRowUnsaved {
  implicit lazy val reads: Reads[PersonRowUnsaved] = Reads[PersonRowUnsaved](json => JsResult.fromTry(
      Try(
        PersonRowUnsaved(
          favouriteFootballClubId = json.\("favourite_football_club_id").as(FootballClubId.reads),
          name = json.\("name").as(Reads.StringReads),
          nickName = json.\("nick_name").toOption.map(_.as(Reads.StringReads)),
          blogUrl = json.\("blog_url").toOption.map(_.as(Reads.StringReads)),
          email = json.\("email").as(Reads.StringReads),
          phone = json.\("phone").as(Reads.StringReads),
          likesPizza = json.\("likes_pizza").as(Reads.BooleanReads),
          workEmail = json.\("work_email").toOption.map(_.as(Reads.StringReads)),
          id = json.\("id").as(Defaulted.reads(PersonId.reads)),
          maritalStatusId = json.\("marital_status_id").as(Defaulted.reads(MaritalStatusId.reads)),
          sector = json.\("sector").as(Defaulted.reads(Sector.reads)),
          favoriteNumber = json.\("favorite_number").as(Defaulted.reads(Number.reads))
        )
      )
    ),
  )
  implicit lazy val writes: OWrites[PersonRowUnsaved] = OWrites[PersonRowUnsaved](o =>
    new JsObject(ListMap[String, JsValue](
      "favourite_football_club_id" -> FootballClubId.writes.writes(o.favouriteFootballClubId),
      "name" -> Writes.StringWrites.writes(o.name),
      "nick_name" -> Writes.OptionWrites(Writes.StringWrites).writes(o.nickName),
      "blog_url" -> Writes.OptionWrites(Writes.StringWrites).writes(o.blogUrl),
      "email" -> Writes.StringWrites.writes(o.email),
      "phone" -> Writes.StringWrites.writes(o.phone),
      "likes_pizza" -> Writes.BooleanWrites.writes(o.likesPizza),
      "work_email" -> Writes.OptionWrites(Writes.StringWrites).writes(o.workEmail),
      "id" -> Defaulted.writes(PersonId.writes).writes(o.id),
      "marital_status_id" -> Defaulted.writes(MaritalStatusId.writes).writes(o.maritalStatusId),
      "sector" -> Defaulted.writes(Sector.writes).writes(o.sector),
      "favorite_number" -> Defaulted.writes(Number.writes).writes(o.favoriteNumber)
    ))
  )
}
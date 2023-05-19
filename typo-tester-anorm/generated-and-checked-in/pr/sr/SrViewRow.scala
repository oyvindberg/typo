/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package sr

import adventureworks.production.scrapreason.ScrapreasonId
import adventureworks.public.Name
import java.time.LocalDateTime
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class SrViewRow(
  id: Option[Int],
  /** Points to [[production.scrapreason.ScrapreasonRow.scrapreasonid]] */
  scrapreasonid: Option[ScrapreasonId],
  /** Points to [[production.scrapreason.ScrapreasonRow.name]] */
  name: Option[Name],
  /** Points to [[production.scrapreason.ScrapreasonRow.modifieddate]] */
  modifieddate: Option[LocalDateTime]
)

object SrViewRow {
  implicit val oFormat: OFormat[SrViewRow] = new OFormat[SrViewRow]{
    override def writes(o: SrViewRow): JsObject =
      Json.obj(
        "id" -> o.id,
        "scrapreasonid" -> o.scrapreasonid,
        "name" -> o.name,
        "modifieddate" -> o.modifieddate
      )
  
    override def reads(json: JsValue): JsResult[SrViewRow] = {
      JsResult.fromTry(
        Try(
          SrViewRow(
            id = json.\("id").toOption.map(_.as[Int]),
            scrapreasonid = json.\("scrapreasonid").toOption.map(_.as[ScrapreasonId]),
            name = json.\("name").toOption.map(_.as[Name]),
            modifieddate = json.\("modifieddate").toOption.map(_.as[LocalDateTime])
          )
        )
      )
    }
  }
}
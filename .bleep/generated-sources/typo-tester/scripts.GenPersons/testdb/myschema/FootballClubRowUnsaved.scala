package testdb.myschema

import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class FootballClubRowUnsaved(
  name: String
)
object FootballClubRowUnsaved {
  implicit val oFormat: OFormat[FootballClubRowUnsaved] = new OFormat[FootballClubRowUnsaved]{
    override def writes(o: FootballClubRowUnsaved): JsObject =
      Json.obj(
        "name" -> o.name
      )

    override def reads(json: JsValue): JsResult[FootballClubRowUnsaved] = {
      JsResult.fromTry(
        Try(
          FootballClubRowUnsaved(
            name = json.\("name").as[String]
          )
        )
      )
    }
  }
}

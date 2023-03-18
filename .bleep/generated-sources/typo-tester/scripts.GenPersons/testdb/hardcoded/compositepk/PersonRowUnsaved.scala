package testdb
package hardcoded
package compositepk

import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PersonRowUnsaved(
  name: Option[String]
)
object PersonRowUnsaved {
  implicit val oFormat: OFormat[PersonRowUnsaved] = new OFormat[PersonRowUnsaved]{
    override def writes(o: PersonRowUnsaved): JsObject =
      Json.obj(
        "name" -> o.name
      )

    override def reads(json: JsValue): JsResult[PersonRowUnsaved] = {
      JsResult.fromTry(
        Try(
          PersonRowUnsaved(
            name = json.\("name").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}

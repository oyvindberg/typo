package testdb.compositepk

import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PersonRowUnsaved(
  name: Option[String]
)
object PersonRowUnsaved {
  implicit val oFormat: OFormat[PersonRowUnsaved] = Json.format
}

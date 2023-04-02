package typo
package internal

import play.api.libs.json.*

object minimalJson {
  def apply[T: Writes](t: T): JsValue =
    removeNullsFromJson(Json.toJson(t))

  def removeNullsFromJson(json: JsValue): JsValue = json match {
    case x: JsObject => JsObject(x.fields.collect { case (k, v) if v != JsNull => k -> removeNullsFromJson(v) })
    case other       => other
  }
}

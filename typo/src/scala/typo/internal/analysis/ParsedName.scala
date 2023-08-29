package typo
package internal
package analysis

import play.api.libs.json.{Json, Writes}

case class ParsedName(name: db.ColName, nullability: Option[Nullability], overriddenType: Option[sc.Type])

object ParsedName {
  implicit val oformat: Writes[ParsedName] = (x: ParsedName) =>
    Json.obj(
      "name" -> x.name.value,
      "nullability" -> x.nullability.map(_.toString),
      "overriddenType" -> x.overriddenType.map(sc.renderTree)
    )
}

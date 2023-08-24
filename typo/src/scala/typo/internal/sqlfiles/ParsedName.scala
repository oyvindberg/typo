package typo.internal.sqlfiles

import play.api.libs.json.{Json, Writes}
import typo.{Nullability, db, sc}

case class ParsedName(name: db.ColName, nullability: Option[Nullability], overriddenType: Option[sc.Type])
object ParsedName {
  implicit val oformat: Writes[ParsedName] = (x: ParsedName) =>
    Json.obj(
      "name" -> x.name.value,
      "nullability" -> x.nullability.map(_.toString),
      "overriddenType" -> x.overriddenType.map(sc.renderTree)
    )
}

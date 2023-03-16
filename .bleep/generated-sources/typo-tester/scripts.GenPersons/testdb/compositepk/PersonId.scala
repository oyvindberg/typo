package testdb.compositepk

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PersonId(one: Long, two: Option[String])
object PersonId {
  implicit val ordering: Ordering[PersonId] = Ordering.by(x => (x.one, x.two))
  implicit val oFormat: OFormat[PersonId] = Json.format
  implicit val rowParser: RowParser[PersonId] = { row =>
    Success(
      PersonId(
        one = row[Long]("one"),
        two = row[Option[String]]("two")
      )
    )
  }

}

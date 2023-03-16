package testdb.compositepk

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PersonRow(
  one: Long,
  two: Option[String],
  name: Option[String]
){
  val oneAndTwo: PersonId = PersonId(one, two)
}

object PersonRow {
  implicit val rowParser: RowParser[PersonRow] = { row =>
    Success(
      PersonRow(
        one = row[Long]("one"),
        two = row[Option[String]]("two"),
        name = row[Option[String]]("name")
      )
    )
  }

  implicit val oFormat: OFormat[PersonRow] = Json.format
}

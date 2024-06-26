/**
 * File automatically generated by `typo` for its own test suite.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN
 */
package testdb
package hardcoded
package compositepk
package person

import anorm.Column
import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.OWrites
import play.api.libs.json.Reads
import play.api.libs.json.Writes
import scala.collection.immutable.ListMap
import scala.util.Try
import testdb.hardcoded.customtypes.Defaulted

/** Table: compositepk.person
    Composite primary key: one, two */
case class PersonRow(
  /** Default: auto-increment */
  one: Long,
  /** Default: auto-increment */
  two: Option[String],
  name: Option[String]
){
   val compositeId: PersonId = PersonId(one, two)
   val id = compositeId
   def toUnsavedRow(one: Defaulted[Long], two: Defaulted[Option[String]]): PersonRowUnsaved =
     PersonRowUnsaved(name, one, two)
 }

object PersonRow {
  def apply(compositeId: PersonId, name: Option[String]) =
    new PersonRow(compositeId.one, compositeId.two, name)
  implicit lazy val reads: Reads[PersonRow] = Reads[PersonRow](json => JsResult.fromTry(
      Try(
        PersonRow(
          one = json.\("one").as(Reads.LongReads),
          two = json.\("two").toOption.map(_.as(Reads.StringReads)),
          name = json.\("name").toOption.map(_.as(Reads.StringReads))
        )
      )
    ),
  )
  def rowParser(idx: Int): RowParser[PersonRow] = RowParser[PersonRow] { row =>
    Success(
      PersonRow(
        one = row(idx + 0)(Column.columnToLong),
        two = row(idx + 1)(Column.columnToOption(Column.columnToString)),
        name = row(idx + 2)(Column.columnToOption(Column.columnToString))
      )
    )
  }
  implicit lazy val text: Text[PersonRow] = Text.instance[PersonRow]{ (row, sb) =>
    Text.longInstance.unsafeEncode(row.one, sb)
    sb.append(Text.DELIMETER)
    Text.option(Text.stringInstance).unsafeEncode(row.two, sb)
    sb.append(Text.DELIMETER)
    Text.option(Text.stringInstance).unsafeEncode(row.name, sb)
  }
  implicit lazy val writes: OWrites[PersonRow] = OWrites[PersonRow](o =>
    new JsObject(ListMap[String, JsValue](
      "one" -> Writes.LongWrites.writes(o.one),
      "two" -> Writes.OptionWrites(Writes.StringWrites).writes(o.two),
      "name" -> Writes.OptionWrites(Writes.StringWrites).writes(o.name)
    ))
  )
}

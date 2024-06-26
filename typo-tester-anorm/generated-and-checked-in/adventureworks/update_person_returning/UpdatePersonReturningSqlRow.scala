/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package update_person_returning

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.userdefined.FirstName
import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.OWrites
import play.api.libs.json.Reads
import scala.collection.immutable.ListMap
import scala.util.Try

/** SQL file: update_person_returning.sql */
case class UpdatePersonReturningSqlRow(
  /** Points to [[person.person.PersonRow.firstname]] */
  firstname: /* user-picked */ FirstName,
  /** Points to [[person.person.PersonRow.modifieddate]] */
  modifieddate: TypoLocalDateTime
)

object UpdatePersonReturningSqlRow {
  implicit lazy val reads: Reads[UpdatePersonReturningSqlRow] = Reads[UpdatePersonReturningSqlRow](json => JsResult.fromTry(
      Try(
        UpdatePersonReturningSqlRow(
          firstname = json.\("firstname").as(FirstName.reads),
          modifieddate = json.\("modifieddate").as(TypoLocalDateTime.reads)
        )
      )
    ),
  )
  def rowParser(idx: Int): RowParser[UpdatePersonReturningSqlRow] = RowParser[UpdatePersonReturningSqlRow] { row =>
    Success(
      UpdatePersonReturningSqlRow(
        firstname = row(idx + 0)(/* user-picked */ FirstName.column),
        modifieddate = row(idx + 1)(TypoLocalDateTime.column)
      )
    )
  }
  implicit lazy val writes: OWrites[UpdatePersonReturningSqlRow] = OWrites[UpdatePersonReturningSqlRow](o =>
    new JsObject(ListMap[String, JsValue](
      "firstname" -> FirstName.writes.writes(o.firstname),
      "modifieddate" -> TypoLocalDateTime.writes.writes(o.modifieddate)
    ))
  )
}

/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pe
package at

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoUUID
import adventureworks.person.addresstype.AddresstypeId
import adventureworks.public.Name
import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.OWrites
import play.api.libs.json.Reads
import scala.collection.immutable.ListMap
import scala.util.Try

case class AtViewRow(
  /** Points to [[person.addresstype.AddresstypeRow.addresstypeid]] */
  id: AddresstypeId,
  /** Points to [[person.addresstype.AddresstypeRow.addresstypeid]] */
  addresstypeid: AddresstypeId,
  /** Points to [[person.addresstype.AddresstypeRow.name]] */
  name: Name,
  /** Points to [[person.addresstype.AddresstypeRow.rowguid]] */
  rowguid: TypoUUID,
  /** Points to [[person.addresstype.AddresstypeRow.modifieddate]] */
  modifieddate: TypoLocalDateTime
)

object AtViewRow {
  implicit lazy val reads: Reads[AtViewRow] = Reads[AtViewRow](json => JsResult.fromTry(
      Try(
        AtViewRow(
          id = json.\("id").as(AddresstypeId.reads),
          addresstypeid = json.\("addresstypeid").as(AddresstypeId.reads),
          name = json.\("name").as(Name.reads),
          rowguid = json.\("rowguid").as(TypoUUID.reads),
          modifieddate = json.\("modifieddate").as(TypoLocalDateTime.reads)
        )
      )
    ),
  )
  def rowParser(idx: Int): RowParser[AtViewRow] = RowParser[AtViewRow] { row =>
    Success(
      AtViewRow(
        id = row(idx + 0)(AddresstypeId.column),
        addresstypeid = row(idx + 1)(AddresstypeId.column),
        name = row(idx + 2)(Name.column),
        rowguid = row(idx + 3)(TypoUUID.column),
        modifieddate = row(idx + 4)(TypoLocalDateTime.column)
      )
    )
  }
  implicit lazy val writes: OWrites[AtViewRow] = OWrites[AtViewRow](o =>
    new JsObject(ListMap[String, JsValue](
      "id" -> AddresstypeId.writes.writes(o.id),
      "addresstypeid" -> AddresstypeId.writes.writes(o.addresstypeid),
      "name" -> Name.writes.writes(o.name),
      "rowguid" -> TypoUUID.writes.writes(o.rowguid),
      "modifieddate" -> TypoLocalDateTime.writes.writes(o.modifieddate)
    ))
  )
}
/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package humanresources
package shift

import adventureworks.customtypes.Defaulted
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoLocalTime
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

/** Table: humanresources.shift
    Work shift lookup table.
    Primary key: shiftid */
case class ShiftRow(
  /** Primary key for Shift records.
      Default: nextval('humanresources.shift_shiftid_seq'::regclass) */
  shiftid: ShiftId,
  /** Shift description. */
  name: Name,
  /** Shift start time. */
  starttime: TypoLocalTime,
  /** Shift end time. */
  endtime: TypoLocalTime,
  /** Default: now() */
  modifieddate: TypoLocalDateTime
){
   val id = shiftid
   def toUnsavedRow(shiftid: Defaulted[ShiftId], modifieddate: Defaulted[TypoLocalDateTime] = Defaulted.Provided(this.modifieddate)): ShiftRowUnsaved =
     ShiftRowUnsaved(name, starttime, endtime, shiftid, modifieddate)
 }

object ShiftRow {
  implicit lazy val reads: Reads[ShiftRow] = Reads[ShiftRow](json => JsResult.fromTry(
      Try(
        ShiftRow(
          shiftid = json.\("shiftid").as(ShiftId.reads),
          name = json.\("name").as(Name.reads),
          starttime = json.\("starttime").as(TypoLocalTime.reads),
          endtime = json.\("endtime").as(TypoLocalTime.reads),
          modifieddate = json.\("modifieddate").as(TypoLocalDateTime.reads)
        )
      )
    ),
  )
  def rowParser(idx: Int): RowParser[ShiftRow] = RowParser[ShiftRow] { row =>
    Success(
      ShiftRow(
        shiftid = row(idx + 0)(ShiftId.column),
        name = row(idx + 1)(Name.column),
        starttime = row(idx + 2)(TypoLocalTime.column),
        endtime = row(idx + 3)(TypoLocalTime.column),
        modifieddate = row(idx + 4)(TypoLocalDateTime.column)
      )
    )
  }
  implicit lazy val text: Text[ShiftRow] = Text.instance[ShiftRow]{ (row, sb) =>
    ShiftId.text.unsafeEncode(row.shiftid, sb)
    sb.append(Text.DELIMETER)
    Name.text.unsafeEncode(row.name, sb)
    sb.append(Text.DELIMETER)
    TypoLocalTime.text.unsafeEncode(row.starttime, sb)
    sb.append(Text.DELIMETER)
    TypoLocalTime.text.unsafeEncode(row.endtime, sb)
    sb.append(Text.DELIMETER)
    TypoLocalDateTime.text.unsafeEncode(row.modifieddate, sb)
  }
  implicit lazy val writes: OWrites[ShiftRow] = OWrites[ShiftRow](o =>
    new JsObject(ListMap[String, JsValue](
      "shiftid" -> ShiftId.writes.writes(o.shiftid),
      "name" -> Name.writes.writes(o.name),
      "starttime" -> TypoLocalTime.writes.writes(o.starttime),
      "endtime" -> TypoLocalTime.writes.writes(o.endtime),
      "modifieddate" -> TypoLocalDateTime.writes.writes(o.modifieddate)
    ))
  )
}

/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package so

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoUUID
import adventureworks.sales.specialoffer.SpecialofferId
import typo.dsl.SqlExpr.Field
import typo.dsl.SqlExpr.FieldLikeNoHkt
import typo.dsl.SqlExpr.OptField
import typo.dsl.Structure.Relation

class SoViewStructure[Row](val prefix: Option[String], val extract: Row => SoViewRow, val merge: (Row, SoViewRow) => Row)
  extends Relation[SoViewFields, SoViewRow, Row]
    with SoViewFields[Row] { outer =>

  override val id = new Field[SpecialofferId, Row](prefix, "id", None, None)(x => extract(x).id, (row, value) => merge(row, extract(row).copy(id = value)))
  override val specialofferid = new Field[SpecialofferId, Row](prefix, "specialofferid", None, None)(x => extract(x).specialofferid, (row, value) => merge(row, extract(row).copy(specialofferid = value)))
  override val description = new Field[/* max 255 chars */ String, Row](prefix, "description", None, None)(x => extract(x).description, (row, value) => merge(row, extract(row).copy(description = value)))
  override val discountpct = new Field[BigDecimal, Row](prefix, "discountpct", None, None)(x => extract(x).discountpct, (row, value) => merge(row, extract(row).copy(discountpct = value)))
  override val `type` = new Field[/* max 50 chars */ String, Row](prefix, "type", None, None)(x => extract(x).`type`, (row, value) => merge(row, extract(row).copy(`type` = value)))
  override val category = new Field[/* max 50 chars */ String, Row](prefix, "category", None, None)(x => extract(x).category, (row, value) => merge(row, extract(row).copy(category = value)))
  override val startdate = new Field[TypoLocalDateTime, Row](prefix, "startdate", Some("text"), None)(x => extract(x).startdate, (row, value) => merge(row, extract(row).copy(startdate = value)))
  override val enddate = new Field[TypoLocalDateTime, Row](prefix, "enddate", Some("text"), None)(x => extract(x).enddate, (row, value) => merge(row, extract(row).copy(enddate = value)))
  override val minqty = new Field[Int, Row](prefix, "minqty", None, None)(x => extract(x).minqty, (row, value) => merge(row, extract(row).copy(minqty = value)))
  override val maxqty = new OptField[Int, Row](prefix, "maxqty", None, None)(x => extract(x).maxqty, (row, value) => merge(row, extract(row).copy(maxqty = value)))
  override val rowguid = new Field[TypoUUID, Row](prefix, "rowguid", None, None)(x => extract(x).rowguid, (row, value) => merge(row, extract(row).copy(rowguid = value)))
  override val modifieddate = new Field[TypoLocalDateTime, Row](prefix, "modifieddate", Some("text"), None)(x => extract(x).modifieddate, (row, value) => merge(row, extract(row).copy(modifieddate = value)))

  override val columns: List[FieldLikeNoHkt[?, Row]] =
    List[FieldLikeNoHkt[?, Row]](id, specialofferid, description, discountpct, `type`, category, startdate, enddate, minqty, maxqty, rowguid, modifieddate)

  override def copy[NewRow](prefix: Option[String], extract: NewRow => SoViewRow, merge: (NewRow, SoViewRow) => NewRow): SoViewStructure[NewRow] =
    new SoViewStructure(prefix, extract, merge)
}
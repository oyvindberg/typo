/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package sp

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoUUID
import adventureworks.person.businessentity.BusinessentityId
import adventureworks.sales.salesterritory.SalesterritoryId
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

/** View: sa.sp */
case class SpViewRow(
  /** Points to [[sales.salesperson.SalespersonRow.businessentityid]] */
  id: BusinessentityId,
  /** Points to [[sales.salesperson.SalespersonRow.businessentityid]] */
  businessentityid: BusinessentityId,
  /** Points to [[sales.salesperson.SalespersonRow.territoryid]] */
  territoryid: Option[SalesterritoryId],
  /** Points to [[sales.salesperson.SalespersonRow.salesquota]] */
  salesquota: Option[BigDecimal],
  /** Points to [[sales.salesperson.SalespersonRow.bonus]] */
  bonus: BigDecimal,
  /** Points to [[sales.salesperson.SalespersonRow.commissionpct]] */
  commissionpct: BigDecimal,
  /** Points to [[sales.salesperson.SalespersonRow.salesytd]] */
  salesytd: BigDecimal,
  /** Points to [[sales.salesperson.SalespersonRow.saleslastyear]] */
  saleslastyear: BigDecimal,
  /** Points to [[sales.salesperson.SalespersonRow.rowguid]] */
  rowguid: TypoUUID,
  /** Points to [[sales.salesperson.SalespersonRow.modifieddate]] */
  modifieddate: TypoLocalDateTime
)

object SpViewRow {
  implicit lazy val reads: Reads[SpViewRow] = Reads[SpViewRow](json => JsResult.fromTry(
      Try(
        SpViewRow(
          id = json.\("id").as(BusinessentityId.reads),
          businessentityid = json.\("businessentityid").as(BusinessentityId.reads),
          territoryid = json.\("territoryid").toOption.map(_.as(SalesterritoryId.reads)),
          salesquota = json.\("salesquota").toOption.map(_.as(Reads.bigDecReads)),
          bonus = json.\("bonus").as(Reads.bigDecReads),
          commissionpct = json.\("commissionpct").as(Reads.bigDecReads),
          salesytd = json.\("salesytd").as(Reads.bigDecReads),
          saleslastyear = json.\("saleslastyear").as(Reads.bigDecReads),
          rowguid = json.\("rowguid").as(TypoUUID.reads),
          modifieddate = json.\("modifieddate").as(TypoLocalDateTime.reads)
        )
      )
    ),
  )
  def rowParser(idx: Int): RowParser[SpViewRow] = RowParser[SpViewRow] { row =>
    Success(
      SpViewRow(
        id = row(idx + 0)(BusinessentityId.column),
        businessentityid = row(idx + 1)(BusinessentityId.column),
        territoryid = row(idx + 2)(Column.columnToOption(SalesterritoryId.column)),
        salesquota = row(idx + 3)(Column.columnToOption(Column.columnToScalaBigDecimal)),
        bonus = row(idx + 4)(Column.columnToScalaBigDecimal),
        commissionpct = row(idx + 5)(Column.columnToScalaBigDecimal),
        salesytd = row(idx + 6)(Column.columnToScalaBigDecimal),
        saleslastyear = row(idx + 7)(Column.columnToScalaBigDecimal),
        rowguid = row(idx + 8)(TypoUUID.column),
        modifieddate = row(idx + 9)(TypoLocalDateTime.column)
      )
    )
  }
  implicit lazy val writes: OWrites[SpViewRow] = OWrites[SpViewRow](o =>
    new JsObject(ListMap[String, JsValue](
      "id" -> BusinessentityId.writes.writes(o.id),
      "businessentityid" -> BusinessentityId.writes.writes(o.businessentityid),
      "territoryid" -> Writes.OptionWrites(SalesterritoryId.writes).writes(o.territoryid),
      "salesquota" -> Writes.OptionWrites(Writes.BigDecimalWrites).writes(o.salesquota),
      "bonus" -> Writes.BigDecimalWrites.writes(o.bonus),
      "commissionpct" -> Writes.BigDecimalWrites.writes(o.commissionpct),
      "salesytd" -> Writes.BigDecimalWrites.writes(o.salesytd),
      "saleslastyear" -> Writes.BigDecimalWrites.writes(o.saleslastyear),
      "rowguid" -> TypoUUID.writes.writes(o.rowguid),
      "modifieddate" -> TypoLocalDateTime.writes.writes(o.modifieddate)
    ))
  )
}

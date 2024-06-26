/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package st

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoUUID
import adventureworks.person.countryregion.CountryregionId
import adventureworks.public.Name
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

/** View: sa.st */
case class StViewRow(
  /** Points to [[sales.salesterritory.SalesterritoryRow.territoryid]] */
  id: SalesterritoryId,
  /** Points to [[sales.salesterritory.SalesterritoryRow.territoryid]] */
  territoryid: SalesterritoryId,
  /** Points to [[sales.salesterritory.SalesterritoryRow.name]] */
  name: Name,
  /** Points to [[sales.salesterritory.SalesterritoryRow.countryregioncode]] */
  countryregioncode: CountryregionId,
  /** Points to [[sales.salesterritory.SalesterritoryRow.group]] */
  group: /* max 50 chars */ String,
  /** Points to [[sales.salesterritory.SalesterritoryRow.salesytd]] */
  salesytd: BigDecimal,
  /** Points to [[sales.salesterritory.SalesterritoryRow.saleslastyear]] */
  saleslastyear: BigDecimal,
  /** Points to [[sales.salesterritory.SalesterritoryRow.costytd]] */
  costytd: BigDecimal,
  /** Points to [[sales.salesterritory.SalesterritoryRow.costlastyear]] */
  costlastyear: BigDecimal,
  /** Points to [[sales.salesterritory.SalesterritoryRow.rowguid]] */
  rowguid: TypoUUID,
  /** Points to [[sales.salesterritory.SalesterritoryRow.modifieddate]] */
  modifieddate: TypoLocalDateTime
)

object StViewRow {
  implicit lazy val reads: Reads[StViewRow] = Reads[StViewRow](json => JsResult.fromTry(
      Try(
        StViewRow(
          id = json.\("id").as(SalesterritoryId.reads),
          territoryid = json.\("territoryid").as(SalesterritoryId.reads),
          name = json.\("name").as(Name.reads),
          countryregioncode = json.\("countryregioncode").as(CountryregionId.reads),
          group = json.\("group").as(Reads.StringReads),
          salesytd = json.\("salesytd").as(Reads.bigDecReads),
          saleslastyear = json.\("saleslastyear").as(Reads.bigDecReads),
          costytd = json.\("costytd").as(Reads.bigDecReads),
          costlastyear = json.\("costlastyear").as(Reads.bigDecReads),
          rowguid = json.\("rowguid").as(TypoUUID.reads),
          modifieddate = json.\("modifieddate").as(TypoLocalDateTime.reads)
        )
      )
    ),
  )
  def rowParser(idx: Int): RowParser[StViewRow] = RowParser[StViewRow] { row =>
    Success(
      StViewRow(
        id = row(idx + 0)(SalesterritoryId.column),
        territoryid = row(idx + 1)(SalesterritoryId.column),
        name = row(idx + 2)(Name.column),
        countryregioncode = row(idx + 3)(CountryregionId.column),
        group = row(idx + 4)(Column.columnToString),
        salesytd = row(idx + 5)(Column.columnToScalaBigDecimal),
        saleslastyear = row(idx + 6)(Column.columnToScalaBigDecimal),
        costytd = row(idx + 7)(Column.columnToScalaBigDecimal),
        costlastyear = row(idx + 8)(Column.columnToScalaBigDecimal),
        rowguid = row(idx + 9)(TypoUUID.column),
        modifieddate = row(idx + 10)(TypoLocalDateTime.column)
      )
    )
  }
  implicit lazy val writes: OWrites[StViewRow] = OWrites[StViewRow](o =>
    new JsObject(ListMap[String, JsValue](
      "id" -> SalesterritoryId.writes.writes(o.id),
      "territoryid" -> SalesterritoryId.writes.writes(o.territoryid),
      "name" -> Name.writes.writes(o.name),
      "countryregioncode" -> CountryregionId.writes.writes(o.countryregioncode),
      "group" -> Writes.StringWrites.writes(o.group),
      "salesytd" -> Writes.BigDecimalWrites.writes(o.salesytd),
      "saleslastyear" -> Writes.BigDecimalWrites.writes(o.saleslastyear),
      "costytd" -> Writes.BigDecimalWrites.writes(o.costytd),
      "costlastyear" -> Writes.BigDecimalWrites.writes(o.costlastyear),
      "rowguid" -> TypoUUID.writes.writes(o.rowguid),
      "modifieddate" -> TypoLocalDateTime.writes.writes(o.modifieddate)
    ))
  )
}

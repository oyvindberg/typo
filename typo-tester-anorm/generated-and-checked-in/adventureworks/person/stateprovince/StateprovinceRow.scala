/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package stateprovince

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoUUID
import adventureworks.person.countryregion.CountryregionId
import adventureworks.public.Flag
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

case class StateprovinceRow(
  /** Primary key for StateProvince records. */
  stateprovinceid: StateprovinceId,
  /** ISO standard state or province code. */
  stateprovincecode: /* bpchar, max 3 chars */ String,
  /** ISO standard country or region code. Foreign key to CountryRegion.CountryRegionCode.
      Points to [[countryregion.CountryregionRow.countryregioncode]] */
  countryregioncode: CountryregionId,
  /** 0 = StateProvinceCode exists. 1 = StateProvinceCode unavailable, using CountryRegionCode. */
  isonlystateprovinceflag: Flag,
  /** State or province description. */
  name: Name,
  /** ID of the territory in which the state or province is located. Foreign key to SalesTerritory.SalesTerritoryID.
      Points to [[sales.salesterritory.SalesterritoryRow.territoryid]] */
  territoryid: SalesterritoryId,
  rowguid: TypoUUID,
  modifieddate: TypoLocalDateTime
)

object StateprovinceRow {
  implicit lazy val reads: Reads[StateprovinceRow] = Reads[StateprovinceRow](json => JsResult.fromTry(
      Try(
        StateprovinceRow(
          stateprovinceid = json.\("stateprovinceid").as(StateprovinceId.reads),
          stateprovincecode = json.\("stateprovincecode").as(Reads.StringReads),
          countryregioncode = json.\("countryregioncode").as(CountryregionId.reads),
          isonlystateprovinceflag = json.\("isonlystateprovinceflag").as(Flag.reads),
          name = json.\("name").as(Name.reads),
          territoryid = json.\("territoryid").as(SalesterritoryId.reads),
          rowguid = json.\("rowguid").as(TypoUUID.reads),
          modifieddate = json.\("modifieddate").as(TypoLocalDateTime.reads)
        )
      )
    ),
  )
  def rowParser(idx: Int): RowParser[StateprovinceRow] = RowParser[StateprovinceRow] { row =>
    Success(
      StateprovinceRow(
        stateprovinceid = row(idx + 0)(StateprovinceId.column),
        stateprovincecode = row(idx + 1)(Column.columnToString),
        countryregioncode = row(idx + 2)(CountryregionId.column),
        isonlystateprovinceflag = row(idx + 3)(Flag.column),
        name = row(idx + 4)(Name.column),
        territoryid = row(idx + 5)(SalesterritoryId.column),
        rowguid = row(idx + 6)(TypoUUID.column),
        modifieddate = row(idx + 7)(TypoLocalDateTime.column)
      )
    )
  }
  implicit lazy val writes: OWrites[StateprovinceRow] = OWrites[StateprovinceRow](o =>
    new JsObject(ListMap[String, JsValue](
      "stateprovinceid" -> StateprovinceId.writes.writes(o.stateprovinceid),
      "stateprovincecode" -> Writes.StringWrites.writes(o.stateprovincecode),
      "countryregioncode" -> CountryregionId.writes.writes(o.countryregioncode),
      "isonlystateprovinceflag" -> Flag.writes.writes(o.isonlystateprovinceflag),
      "name" -> Name.writes.writes(o.name),
      "territoryid" -> SalesterritoryId.writes.writes(o.territoryid),
      "rowguid" -> TypoUUID.writes.writes(o.rowguid),
      "modifieddate" -> TypoLocalDateTime.writes.writes(o.modifieddate)
    ))
  )
}
/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package st

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.person.countryregion.CountryregionId
import adventureworks.public.Name
import adventureworks.sales.salesterritory.SalesterritoryId
import java.util.UUID
import typo.dsl.SqlExpr.Field

trait StViewFields[Row] {
  val id: Field[SalesterritoryId, Row]
  val territoryid: Field[SalesterritoryId, Row]
  val name: Field[Name, Row]
  val countryregioncode: Field[CountryregionId, Row]
  val group: Field[/* max 50 chars */ String, Row]
  val salesytd: Field[BigDecimal, Row]
  val saleslastyear: Field[BigDecimal, Row]
  val costytd: Field[BigDecimal, Row]
  val costlastyear: Field[BigDecimal, Row]
  val rowguid: Field[UUID, Row]
  val modifieddate: Field[TypoLocalDateTime, Row]
}
object StViewFields extends StViewStructure[StViewRow](None, identity, (_, x) => x)

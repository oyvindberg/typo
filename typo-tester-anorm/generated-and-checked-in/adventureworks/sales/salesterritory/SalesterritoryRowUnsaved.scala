/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package salesterritory

import adventureworks.Text
import adventureworks.customtypes.Defaulted
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoUUID
import adventureworks.person.countryregion.CountryregionId
import adventureworks.public.Name
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.OWrites
import play.api.libs.json.Reads
import play.api.libs.json.Writes
import scala.collection.immutable.ListMap
import scala.util.Try

/** This class corresponds to a row in table `sales.salesterritory` which has not been persisted yet */
case class SalesterritoryRowUnsaved(
  /** Sales territory description */
  name: Name,
  /** ISO standard country or region code. Foreign key to CountryRegion.CountryRegionCode.
      Points to [[person.countryregion.CountryregionRow.countryregioncode]] */
  countryregioncode: CountryregionId,
  /** Geographic area to which the sales territory belong. */
  group: /* max 50 chars */ String,
  /** Default: nextval('sales.salesterritory_territoryid_seq'::regclass)
      Primary key for SalesTerritory records. */
  territoryid: Defaulted[SalesterritoryId] = Defaulted.UseDefault,
  /** Default: 0.00
      Sales in the territory year to date.
      Constraint CK_SalesTerritory_SalesYTD affecting columns s, a, l, e, s, y, t, d:  ((salesytd >= 0.00)) */
  salesytd: Defaulted[BigDecimal] = Defaulted.UseDefault,
  /** Default: 0.00
      Sales in the territory the previous year.
      Constraint CK_SalesTerritory_SalesLastYear affecting columns s, a, l, e, s, l, a, s, t, y, e, a, r:  ((saleslastyear >= 0.00)) */
  saleslastyear: Defaulted[BigDecimal] = Defaulted.UseDefault,
  /** Default: 0.00
      Business costs in the territory year to date.
      Constraint CK_SalesTerritory_CostYTD affecting columns c, o, s, t, y, t, d:  ((costytd >= 0.00)) */
  costytd: Defaulted[BigDecimal] = Defaulted.UseDefault,
  /** Default: 0.00
      Business costs in the territory the previous year.
      Constraint CK_SalesTerritory_CostLastYear affecting columns c, o, s, t, l, a, s, t, y, e, a, r:  ((costlastyear >= 0.00)) */
  costlastyear: Defaulted[BigDecimal] = Defaulted.UseDefault,
  /** Default: uuid_generate_v1() */
  rowguid: Defaulted[TypoUUID] = Defaulted.UseDefault,
  /** Default: now() */
  modifieddate: Defaulted[TypoLocalDateTime] = Defaulted.UseDefault
) {
  def toRow(territoryidDefault: => SalesterritoryId, salesytdDefault: => BigDecimal, saleslastyearDefault: => BigDecimal, costytdDefault: => BigDecimal, costlastyearDefault: => BigDecimal, rowguidDefault: => TypoUUID, modifieddateDefault: => TypoLocalDateTime): SalesterritoryRow =
    SalesterritoryRow(
      name = name,
      countryregioncode = countryregioncode,
      group = group,
      territoryid = territoryid match {
                      case Defaulted.UseDefault => territoryidDefault
                      case Defaulted.Provided(value) => value
                    },
      salesytd = salesytd match {
                   case Defaulted.UseDefault => salesytdDefault
                   case Defaulted.Provided(value) => value
                 },
      saleslastyear = saleslastyear match {
                        case Defaulted.UseDefault => saleslastyearDefault
                        case Defaulted.Provided(value) => value
                      },
      costytd = costytd match {
                  case Defaulted.UseDefault => costytdDefault
                  case Defaulted.Provided(value) => value
                },
      costlastyear = costlastyear match {
                       case Defaulted.UseDefault => costlastyearDefault
                       case Defaulted.Provided(value) => value
                     },
      rowguid = rowguid match {
                  case Defaulted.UseDefault => rowguidDefault
                  case Defaulted.Provided(value) => value
                },
      modifieddate = modifieddate match {
                       case Defaulted.UseDefault => modifieddateDefault
                       case Defaulted.Provided(value) => value
                     }
    )
}
object SalesterritoryRowUnsaved {
  implicit lazy val reads: Reads[SalesterritoryRowUnsaved] = Reads[SalesterritoryRowUnsaved](json => JsResult.fromTry(
      Try(
        SalesterritoryRowUnsaved(
          name = json.\("name").as(Name.reads),
          countryregioncode = json.\("countryregioncode").as(CountryregionId.reads),
          group = json.\("group").as(Reads.StringReads),
          territoryid = json.\("territoryid").as(Defaulted.reads(SalesterritoryId.reads)),
          salesytd = json.\("salesytd").as(Defaulted.reads(Reads.bigDecReads)),
          saleslastyear = json.\("saleslastyear").as(Defaulted.reads(Reads.bigDecReads)),
          costytd = json.\("costytd").as(Defaulted.reads(Reads.bigDecReads)),
          costlastyear = json.\("costlastyear").as(Defaulted.reads(Reads.bigDecReads)),
          rowguid = json.\("rowguid").as(Defaulted.reads(TypoUUID.reads)),
          modifieddate = json.\("modifieddate").as(Defaulted.reads(TypoLocalDateTime.reads))
        )
      )
    ),
  )
  implicit lazy val text: Text[SalesterritoryRowUnsaved] = Text.instance[SalesterritoryRowUnsaved]{ (row, sb) =>
    Name.text.unsafeEncode(row.name, sb)
    sb.append(Text.DELIMETER)
    CountryregionId.text.unsafeEncode(row.countryregioncode, sb)
    sb.append(Text.DELIMETER)
    Text.stringInstance.unsafeEncode(row.group, sb)
    sb.append(Text.DELIMETER)
    Defaulted.text(SalesterritoryId.text).unsafeEncode(row.territoryid, sb)
    sb.append(Text.DELIMETER)
    Defaulted.text(Text.bigDecimalInstance).unsafeEncode(row.salesytd, sb)
    sb.append(Text.DELIMETER)
    Defaulted.text(Text.bigDecimalInstance).unsafeEncode(row.saleslastyear, sb)
    sb.append(Text.DELIMETER)
    Defaulted.text(Text.bigDecimalInstance).unsafeEncode(row.costytd, sb)
    sb.append(Text.DELIMETER)
    Defaulted.text(Text.bigDecimalInstance).unsafeEncode(row.costlastyear, sb)
    sb.append(Text.DELIMETER)
    Defaulted.text(TypoUUID.text).unsafeEncode(row.rowguid, sb)
    sb.append(Text.DELIMETER)
    Defaulted.text(TypoLocalDateTime.text).unsafeEncode(row.modifieddate, sb)
  }
  implicit lazy val writes: OWrites[SalesterritoryRowUnsaved] = OWrites[SalesterritoryRowUnsaved](o =>
    new JsObject(ListMap[String, JsValue](
      "name" -> Name.writes.writes(o.name),
      "countryregioncode" -> CountryregionId.writes.writes(o.countryregioncode),
      "group" -> Writes.StringWrites.writes(o.group),
      "territoryid" -> Defaulted.writes(SalesterritoryId.writes).writes(o.territoryid),
      "salesytd" -> Defaulted.writes(Writes.BigDecimalWrites).writes(o.salesytd),
      "saleslastyear" -> Defaulted.writes(Writes.BigDecimalWrites).writes(o.saleslastyear),
      "costytd" -> Defaulted.writes(Writes.BigDecimalWrites).writes(o.costytd),
      "costlastyear" -> Defaulted.writes(Writes.BigDecimalWrites).writes(o.costlastyear),
      "rowguid" -> Defaulted.writes(TypoUUID.writes).writes(o.rowguid),
      "modifieddate" -> Defaulted.writes(TypoLocalDateTime.writes).writes(o.modifieddate)
    ))
  )
}
/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package countryregioncurrency

import adventureworks.Text
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.person.countryregion.CountryregionId
import adventureworks.sales.currency.CurrencyId
import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.OWrites
import play.api.libs.json.Reads
import scala.collection.immutable.ListMap
import scala.util.Try

case class CountryregioncurrencyRow(
  /** ISO code for countries and regions. Foreign key to CountryRegion.CountryRegionCode.
      Points to [[person.countryregion.CountryregionRow.countryregioncode]] */
  countryregioncode: CountryregionId,
  /** ISO standard currency code. Foreign key to Currency.CurrencyCode.
      Points to [[currency.CurrencyRow.currencycode]] */
  currencycode: CurrencyId,
  /** Default: now() */
  modifieddate: TypoLocalDateTime
){
   val compositeId: CountryregioncurrencyId = CountryregioncurrencyId(countryregioncode, currencycode)
 }

object CountryregioncurrencyRow {
  implicit lazy val reads: Reads[CountryregioncurrencyRow] = Reads[CountryregioncurrencyRow](json => JsResult.fromTry(
      Try(
        CountryregioncurrencyRow(
          countryregioncode = json.\("countryregioncode").as(CountryregionId.reads),
          currencycode = json.\("currencycode").as(CurrencyId.reads),
          modifieddate = json.\("modifieddate").as(TypoLocalDateTime.reads)
        )
      )
    ),
  )
  def rowParser(idx: Int): RowParser[CountryregioncurrencyRow] = RowParser[CountryregioncurrencyRow] { row =>
    Success(
      CountryregioncurrencyRow(
        countryregioncode = row(idx + 0)(CountryregionId.column),
        currencycode = row(idx + 1)(CurrencyId.column),
        modifieddate = row(idx + 2)(TypoLocalDateTime.column)
      )
    )
  }
  implicit lazy val text: Text[CountryregioncurrencyRow] = Text.instance[CountryregioncurrencyRow]{ (row, sb) =>
    CountryregionId.text.unsafeEncode(row.countryregioncode, sb)
    sb.append(Text.DELIMETER)
    CurrencyId.text.unsafeEncode(row.currencycode, sb)
    sb.append(Text.DELIMETER)
    TypoLocalDateTime.text.unsafeEncode(row.modifieddate, sb)
  }
  implicit lazy val writes: OWrites[CountryregioncurrencyRow] = OWrites[CountryregioncurrencyRow](o =>
    new JsObject(ListMap[String, JsValue](
      "countryregioncode" -> CountryregionId.writes.writes(o.countryregioncode),
      "currencycode" -> CurrencyId.writes.writes(o.currencycode),
      "modifieddate" -> TypoLocalDateTime.writes.writes(o.modifieddate)
    ))
  )
}
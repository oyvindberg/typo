/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package cu

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.public.Name
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

/** View: sa.cu */
case class CuViewRow(
  /** Points to [[sales.currency.CurrencyRow.currencycode]] */
  id: CurrencyId,
  /** Points to [[sales.currency.CurrencyRow.currencycode]] */
  currencycode: CurrencyId,
  /** Points to [[sales.currency.CurrencyRow.name]] */
  name: Name,
  /** Points to [[sales.currency.CurrencyRow.modifieddate]] */
  modifieddate: TypoLocalDateTime
)

object CuViewRow {
  implicit lazy val reads: Reads[CuViewRow] = Reads[CuViewRow](json => JsResult.fromTry(
      Try(
        CuViewRow(
          id = json.\("id").as(CurrencyId.reads),
          currencycode = json.\("currencycode").as(CurrencyId.reads),
          name = json.\("name").as(Name.reads),
          modifieddate = json.\("modifieddate").as(TypoLocalDateTime.reads)
        )
      )
    ),
  )
  def rowParser(idx: Int): RowParser[CuViewRow] = RowParser[CuViewRow] { row =>
    Success(
      CuViewRow(
        id = row(idx + 0)(CurrencyId.column),
        currencycode = row(idx + 1)(CurrencyId.column),
        name = row(idx + 2)(Name.column),
        modifieddate = row(idx + 3)(TypoLocalDateTime.column)
      )
    )
  }
  implicit lazy val writes: OWrites[CuViewRow] = OWrites[CuViewRow](o =>
    new JsObject(ListMap[String, JsValue](
      "id" -> CurrencyId.writes.writes(o.id),
      "currencycode" -> CurrencyId.writes.writes(o.currencycode),
      "name" -> Name.writes.writes(o.name),
      "modifieddate" -> TypoLocalDateTime.writes.writes(o.modifieddate)
    ))
  )
}

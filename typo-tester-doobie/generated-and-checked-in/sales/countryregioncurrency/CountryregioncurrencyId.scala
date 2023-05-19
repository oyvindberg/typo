/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package countryregioncurrency

import adventureworks.person.countryregion.CountryregionId
import adventureworks.sales.currency.CurrencyId
import io.circe.Decoder
import io.circe.Encoder
import io.circe.HCursor
import io.circe.Json

/** Type for the composite primary key of table `sales.countryregioncurrency` */
case class CountryregioncurrencyId(countryregioncode: CountryregionId, currencycode: CurrencyId)
object CountryregioncurrencyId {
  implicit def ordering: Ordering[CountryregioncurrencyId] = Ordering.by(x => (x.countryregioncode, x.currencycode))
  implicit val decoder: Decoder[CountryregioncurrencyId] =
    (c: HCursor) =>
      for {
        countryregioncode <- c.downField("countryregioncode").as[CountryregionId]
        currencycode <- c.downField("currencycode").as[CurrencyId]
      } yield CountryregioncurrencyId(countryregioncode, currencycode)
  implicit val encoder: Encoder[CountryregioncurrencyId] = {
    import io.circe.syntax._
    row =>
      Json.obj(
        "countryregioncode" := row.countryregioncode,
        "currencycode" := row.currencycode
      )}
}
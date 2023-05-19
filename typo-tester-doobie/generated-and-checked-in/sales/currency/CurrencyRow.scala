/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package currency

import adventureworks.public.Name
import io.circe.Decoder
import io.circe.Encoder
import io.circe.HCursor
import io.circe.Json
import java.time.LocalDateTime

case class CurrencyRow(
  /** The ISO code for the Currency. */
  currencycode: CurrencyId,
  /** Currency name. */
  name: Name,
  modifieddate: LocalDateTime
)

object CurrencyRow {
  implicit val decoder: Decoder[CurrencyRow] =
    (c: HCursor) =>
      for {
        currencycode <- c.downField("currencycode").as[CurrencyId]
        name <- c.downField("name").as[Name]
        modifieddate <- c.downField("modifieddate").as[LocalDateTime]
      } yield CurrencyRow(currencycode, name, modifieddate)
  implicit val encoder: Encoder[CurrencyRow] = {
    import io.circe.syntax._
    row =>
      Json.obj(
        "currencycode" := row.currencycode,
        "name" := row.name,
        "modifieddate" := row.modifieddate
      )}
}
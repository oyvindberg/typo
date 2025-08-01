/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package crc

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.person.countryregion.CountryregionId
import adventureworks.sales.currency.CurrencyId
import doobie.util.Read
import io.circe.Decoder
import io.circe.Encoder

/** View: sa.crc */
case class CrcViewRow(
  /** Points to [[sales.countryregioncurrency.CountryregioncurrencyRow.countryregioncode]] */
  countryregioncode: CountryregionId,
  /** Points to [[sales.countryregioncurrency.CountryregioncurrencyRow.currencycode]] */
  currencycode: CurrencyId,
  /** Points to [[sales.countryregioncurrency.CountryregioncurrencyRow.modifieddate]] */
  modifieddate: TypoLocalDateTime
)

object CrcViewRow {
  implicit lazy val decoder: Decoder[CrcViewRow] = Decoder.forProduct3[CrcViewRow, CountryregionId, CurrencyId, TypoLocalDateTime]("countryregioncode", "currencycode", "modifieddate")(CrcViewRow.apply)(CountryregionId.decoder, CurrencyId.decoder, TypoLocalDateTime.decoder)
  implicit lazy val encoder: Encoder[CrcViewRow] = Encoder.forProduct3[CrcViewRow, CountryregionId, CurrencyId, TypoLocalDateTime]("countryregioncode", "currencycode", "modifieddate")(x => (x.countryregioncode, x.currencycode, x.modifieddate))(CountryregionId.encoder, CurrencyId.encoder, TypoLocalDateTime.encoder)
  implicit lazy val read: Read[CrcViewRow] = new Read.CompositeOfInstances(Array(
    new Read.Single(CountryregionId.get).asInstanceOf[Read[Any]],
      new Read.Single(CurrencyId.get).asInstanceOf[Read[Any]],
      new Read.Single(TypoLocalDateTime.get).asInstanceOf[Read[Any]]
  ))(using scala.reflect.ClassTag.Any).map { arr =>
    CrcViewRow(
      countryregioncode = arr(0).asInstanceOf[CountryregionId],
          currencycode = arr(1).asInstanceOf[CurrencyId],
          modifieddate = arr(2).asInstanceOf[TypoLocalDateTime]
    )
  }
}

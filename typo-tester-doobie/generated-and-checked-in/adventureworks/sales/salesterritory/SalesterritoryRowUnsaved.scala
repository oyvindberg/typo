/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package salesterritory

import adventureworks.customtypes.Defaulted
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoUUID
import adventureworks.person.countryregion.CountryregionId
import adventureworks.public.Name
import doobie.postgres.Text
import io.circe.Decoder
import io.circe.Encoder

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
      Constraint CK_SalesTerritory_SalesYTD affecting columns "salesytd":  ((salesytd >= 0.00)) */
  salesytd: Defaulted[BigDecimal] = Defaulted.UseDefault,
  /** Default: 0.00
      Sales in the territory the previous year.
      Constraint CK_SalesTerritory_SalesLastYear affecting columns "saleslastyear":  ((saleslastyear >= 0.00)) */
  saleslastyear: Defaulted[BigDecimal] = Defaulted.UseDefault,
  /** Default: 0.00
      Business costs in the territory year to date.
      Constraint CK_SalesTerritory_CostYTD affecting columns "costytd":  ((costytd >= 0.00)) */
  costytd: Defaulted[BigDecimal] = Defaulted.UseDefault,
  /** Default: 0.00
      Business costs in the territory the previous year.
      Constraint CK_SalesTerritory_CostLastYear affecting columns "costlastyear":  ((costlastyear >= 0.00)) */
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
  implicit lazy val decoder: Decoder[SalesterritoryRowUnsaved] = Decoder.forProduct10[SalesterritoryRowUnsaved, Name, CountryregionId, /* max 50 chars */ String, Defaulted[SalesterritoryId], Defaulted[BigDecimal], Defaulted[BigDecimal], Defaulted[BigDecimal], Defaulted[BigDecimal], Defaulted[TypoUUID], Defaulted[TypoLocalDateTime]]("name", "countryregioncode", "group", "territoryid", "salesytd", "saleslastyear", "costytd", "costlastyear", "rowguid", "modifieddate")(SalesterritoryRowUnsaved.apply)(Name.decoder, CountryregionId.decoder, Decoder.decodeString, Defaulted.decoder(SalesterritoryId.decoder), Defaulted.decoder(Decoder.decodeBigDecimal), Defaulted.decoder(Decoder.decodeBigDecimal), Defaulted.decoder(Decoder.decodeBigDecimal), Defaulted.decoder(Decoder.decodeBigDecimal), Defaulted.decoder(TypoUUID.decoder), Defaulted.decoder(TypoLocalDateTime.decoder))
  implicit lazy val encoder: Encoder[SalesterritoryRowUnsaved] = Encoder.forProduct10[SalesterritoryRowUnsaved, Name, CountryregionId, /* max 50 chars */ String, Defaulted[SalesterritoryId], Defaulted[BigDecimal], Defaulted[BigDecimal], Defaulted[BigDecimal], Defaulted[BigDecimal], Defaulted[TypoUUID], Defaulted[TypoLocalDateTime]]("name", "countryregioncode", "group", "territoryid", "salesytd", "saleslastyear", "costytd", "costlastyear", "rowguid", "modifieddate")(x => (x.name, x.countryregioncode, x.group, x.territoryid, x.salesytd, x.saleslastyear, x.costytd, x.costlastyear, x.rowguid, x.modifieddate))(Name.encoder, CountryregionId.encoder, Encoder.encodeString, Defaulted.encoder(SalesterritoryId.encoder), Defaulted.encoder(Encoder.encodeBigDecimal), Defaulted.encoder(Encoder.encodeBigDecimal), Defaulted.encoder(Encoder.encodeBigDecimal), Defaulted.encoder(Encoder.encodeBigDecimal), Defaulted.encoder(TypoUUID.encoder), Defaulted.encoder(TypoLocalDateTime.encoder))
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
}
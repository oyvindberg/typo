/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package salestaxrate

import adventureworks.customtypes.Defaulted
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoShort
import adventureworks.customtypes.TypoUUID
import adventureworks.person.stateprovince.StateprovinceId
import adventureworks.public.Name
import doobie.postgres.Text
import io.circe.Decoder
import io.circe.Encoder

/** This class corresponds to a row in table `sales.salestaxrate` which has not been persisted yet */
case class SalestaxrateRowUnsaved(
  /** State, province, or country/region the sales tax applies to.
      Points to [[person.stateprovince.StateprovinceRow.stateprovinceid]] */
  stateprovinceid: StateprovinceId,
  /** 1 = Tax applied to retail transactions, 2 = Tax applied to wholesale transactions, 3 = Tax applied to all sales (retail and wholesale) transactions.
      Constraint CK_SalesTaxRate_TaxType affecting columns "taxtype":  (((taxtype >= 1) AND (taxtype <= 3))) */
  taxtype: TypoShort,
  /** Tax rate description. */
  name: Name,
  /** Default: nextval('sales.salestaxrate_salestaxrateid_seq'::regclass)
      Primary key for SalesTaxRate records. */
  salestaxrateid: Defaulted[SalestaxrateId] = Defaulted.UseDefault,
  /** Default: 0.00
      Tax rate amount. */
  taxrate: Defaulted[BigDecimal] = Defaulted.UseDefault,
  /** Default: uuid_generate_v1() */
  rowguid: Defaulted[TypoUUID] = Defaulted.UseDefault,
  /** Default: now() */
  modifieddate: Defaulted[TypoLocalDateTime] = Defaulted.UseDefault
) {
  def toRow(salestaxrateidDefault: => SalestaxrateId, taxrateDefault: => BigDecimal, rowguidDefault: => TypoUUID, modifieddateDefault: => TypoLocalDateTime): SalestaxrateRow =
    SalestaxrateRow(
      stateprovinceid = stateprovinceid,
      taxtype = taxtype,
      name = name,
      salestaxrateid = salestaxrateid match {
                         case Defaulted.UseDefault => salestaxrateidDefault
                         case Defaulted.Provided(value) => value
                       },
      taxrate = taxrate match {
                  case Defaulted.UseDefault => taxrateDefault
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
object SalestaxrateRowUnsaved {
  implicit lazy val decoder: Decoder[SalestaxrateRowUnsaved] = Decoder.forProduct7[SalestaxrateRowUnsaved, StateprovinceId, TypoShort, Name, Defaulted[SalestaxrateId], Defaulted[BigDecimal], Defaulted[TypoUUID], Defaulted[TypoLocalDateTime]]("stateprovinceid", "taxtype", "name", "salestaxrateid", "taxrate", "rowguid", "modifieddate")(SalestaxrateRowUnsaved.apply)(StateprovinceId.decoder, TypoShort.decoder, Name.decoder, Defaulted.decoder(SalestaxrateId.decoder), Defaulted.decoder(Decoder.decodeBigDecimal), Defaulted.decoder(TypoUUID.decoder), Defaulted.decoder(TypoLocalDateTime.decoder))
  implicit lazy val encoder: Encoder[SalestaxrateRowUnsaved] = Encoder.forProduct7[SalestaxrateRowUnsaved, StateprovinceId, TypoShort, Name, Defaulted[SalestaxrateId], Defaulted[BigDecimal], Defaulted[TypoUUID], Defaulted[TypoLocalDateTime]]("stateprovinceid", "taxtype", "name", "salestaxrateid", "taxrate", "rowguid", "modifieddate")(x => (x.stateprovinceid, x.taxtype, x.name, x.salestaxrateid, x.taxrate, x.rowguid, x.modifieddate))(StateprovinceId.encoder, TypoShort.encoder, Name.encoder, Defaulted.encoder(SalestaxrateId.encoder), Defaulted.encoder(Encoder.encodeBigDecimal), Defaulted.encoder(TypoUUID.encoder), Defaulted.encoder(TypoLocalDateTime.encoder))
  implicit lazy val text: Text[SalestaxrateRowUnsaved] = Text.instance[SalestaxrateRowUnsaved]{ (row, sb) =>
    StateprovinceId.text.unsafeEncode(row.stateprovinceid, sb)
    sb.append(Text.DELIMETER)
    TypoShort.text.unsafeEncode(row.taxtype, sb)
    sb.append(Text.DELIMETER)
    Name.text.unsafeEncode(row.name, sb)
    sb.append(Text.DELIMETER)
    Defaulted.text(SalestaxrateId.text).unsafeEncode(row.salestaxrateid, sb)
    sb.append(Text.DELIMETER)
    Defaulted.text(Text.bigDecimalInstance).unsafeEncode(row.taxrate, sb)
    sb.append(Text.DELIMETER)
    Defaulted.text(TypoUUID.text).unsafeEncode(row.rowguid, sb)
    sb.append(Text.DELIMETER)
    Defaulted.text(TypoLocalDateTime.text).unsafeEncode(row.modifieddate, sb)
  }
}
/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package tr

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoShort
import adventureworks.customtypes.TypoUUID
import adventureworks.person.stateprovince.StateprovinceId
import adventureworks.public.Name
import adventureworks.sales.salestaxrate.SalestaxrateId
import doobie.enumerated.Nullability
import doobie.util.Read
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.Encoder
import java.sql.ResultSet

/** View: sa.tr */
case class TrViewRow(
  /** Points to [[sales.salestaxrate.SalestaxrateRow.salestaxrateid]] */
  id: SalestaxrateId,
  /** Points to [[sales.salestaxrate.SalestaxrateRow.salestaxrateid]] */
  salestaxrateid: SalestaxrateId,
  /** Points to [[sales.salestaxrate.SalestaxrateRow.stateprovinceid]] */
  stateprovinceid: StateprovinceId,
  /** Points to [[sales.salestaxrate.SalestaxrateRow.taxtype]] */
  taxtype: TypoShort,
  /** Points to [[sales.salestaxrate.SalestaxrateRow.taxrate]] */
  taxrate: BigDecimal,
  /** Points to [[sales.salestaxrate.SalestaxrateRow.name]] */
  name: Name,
  /** Points to [[sales.salestaxrate.SalestaxrateRow.rowguid]] */
  rowguid: TypoUUID,
  /** Points to [[sales.salestaxrate.SalestaxrateRow.modifieddate]] */
  modifieddate: TypoLocalDateTime
)

object TrViewRow {
  implicit lazy val decoder: Decoder[TrViewRow] = Decoder.forProduct8[TrViewRow, SalestaxrateId, SalestaxrateId, StateprovinceId, TypoShort, BigDecimal, Name, TypoUUID, TypoLocalDateTime]("id", "salestaxrateid", "stateprovinceid", "taxtype", "taxrate", "name", "rowguid", "modifieddate")(TrViewRow.apply)(SalestaxrateId.decoder, SalestaxrateId.decoder, StateprovinceId.decoder, TypoShort.decoder, Decoder.decodeBigDecimal, Name.decoder, TypoUUID.decoder, TypoLocalDateTime.decoder)
  implicit lazy val encoder: Encoder[TrViewRow] = Encoder.forProduct8[TrViewRow, SalestaxrateId, SalestaxrateId, StateprovinceId, TypoShort, BigDecimal, Name, TypoUUID, TypoLocalDateTime]("id", "salestaxrateid", "stateprovinceid", "taxtype", "taxrate", "name", "rowguid", "modifieddate")(x => (x.id, x.salestaxrateid, x.stateprovinceid, x.taxtype, x.taxrate, x.name, x.rowguid, x.modifieddate))(SalestaxrateId.encoder, SalestaxrateId.encoder, StateprovinceId.encoder, TypoShort.encoder, Encoder.encodeBigDecimal, Name.encoder, TypoUUID.encoder, TypoLocalDateTime.encoder)
  implicit lazy val read: Read[TrViewRow] = new Read[TrViewRow](
    gets = List(
      (SalestaxrateId.get, Nullability.NoNulls),
      (SalestaxrateId.get, Nullability.NoNulls),
      (StateprovinceId.get, Nullability.NoNulls),
      (TypoShort.get, Nullability.NoNulls),
      (Meta.ScalaBigDecimalMeta.get, Nullability.NoNulls),
      (Name.get, Nullability.NoNulls),
      (TypoUUID.get, Nullability.NoNulls),
      (TypoLocalDateTime.get, Nullability.NoNulls)
    ),
    unsafeGet = (rs: ResultSet, i: Int) => TrViewRow(
      id = SalestaxrateId.get.unsafeGetNonNullable(rs, i + 0),
      salestaxrateid = SalestaxrateId.get.unsafeGetNonNullable(rs, i + 1),
      stateprovinceid = StateprovinceId.get.unsafeGetNonNullable(rs, i + 2),
      taxtype = TypoShort.get.unsafeGetNonNullable(rs, i + 3),
      taxrate = Meta.ScalaBigDecimalMeta.get.unsafeGetNonNullable(rs, i + 4),
      name = Name.get.unsafeGetNonNullable(rs, i + 5),
      rowguid = TypoUUID.get.unsafeGetNonNullable(rs, i + 6),
      modifieddate = TypoLocalDateTime.get.unsafeGetNonNullable(rs, i + 7)
    )
  )
}

/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package stateprovince

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoUUID
import adventureworks.person.countryregion.CountryregionId
import adventureworks.public.Flag
import adventureworks.public.Name
import adventureworks.sales.salesterritory.SalesterritoryId
import doobie.enumerated.Nullability
import doobie.util.Read
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.Encoder
import java.sql.ResultSet

case class StateprovinceRow(
  /** Primary key for StateProvince records. */
  stateprovinceid: StateprovinceId,
  /** ISO standard state or province code. */
  stateprovincecode: /* bpchar, max 3 chars */ String,
  /** ISO standard country or region code. Foreign key to CountryRegion.CountryRegionCode.
      Points to [[countryregion.CountryregionRow.countryregioncode]] */
  countryregioncode: CountryregionId,
  /** 0 = StateProvinceCode exists. 1 = StateProvinceCode unavailable, using CountryRegionCode. */
  isonlystateprovinceflag: Flag,
  /** State or province description. */
  name: Name,
  /** ID of the territory in which the state or province is located. Foreign key to SalesTerritory.SalesTerritoryID.
      Points to [[sales.salesterritory.SalesterritoryRow.territoryid]] */
  territoryid: SalesterritoryId,
  rowguid: TypoUUID,
  modifieddate: TypoLocalDateTime
)

object StateprovinceRow {
  implicit lazy val decoder: Decoder[StateprovinceRow] = Decoder.forProduct8[StateprovinceRow, StateprovinceId, /* bpchar, max 3 chars */ String, CountryregionId, Flag, Name, SalesterritoryId, TypoUUID, TypoLocalDateTime]("stateprovinceid", "stateprovincecode", "countryregioncode", "isonlystateprovinceflag", "name", "territoryid", "rowguid", "modifieddate")(StateprovinceRow.apply)(StateprovinceId.decoder, Decoder.decodeString, CountryregionId.decoder, Flag.decoder, Name.decoder, SalesterritoryId.decoder, TypoUUID.decoder, TypoLocalDateTime.decoder)
  implicit lazy val encoder: Encoder[StateprovinceRow] = Encoder.forProduct8[StateprovinceRow, StateprovinceId, /* bpchar, max 3 chars */ String, CountryregionId, Flag, Name, SalesterritoryId, TypoUUID, TypoLocalDateTime]("stateprovinceid", "stateprovincecode", "countryregioncode", "isonlystateprovinceflag", "name", "territoryid", "rowguid", "modifieddate")(x => (x.stateprovinceid, x.stateprovincecode, x.countryregioncode, x.isonlystateprovinceflag, x.name, x.territoryid, x.rowguid, x.modifieddate))(StateprovinceId.encoder, Encoder.encodeString, CountryregionId.encoder, Flag.encoder, Name.encoder, SalesterritoryId.encoder, TypoUUID.encoder, TypoLocalDateTime.encoder)
  implicit lazy val read: Read[StateprovinceRow] = new Read[StateprovinceRow](
    gets = List(
      (StateprovinceId.get, Nullability.NoNulls),
      (Meta.StringMeta.get, Nullability.NoNulls),
      (CountryregionId.get, Nullability.NoNulls),
      (Flag.get, Nullability.NoNulls),
      (Name.get, Nullability.NoNulls),
      (SalesterritoryId.get, Nullability.NoNulls),
      (TypoUUID.get, Nullability.NoNulls),
      (TypoLocalDateTime.get, Nullability.NoNulls)
    ),
    unsafeGet = (rs: ResultSet, i: Int) => StateprovinceRow(
      stateprovinceid = StateprovinceId.get.unsafeGetNonNullable(rs, i + 0),
      stateprovincecode = Meta.StringMeta.get.unsafeGetNonNullable(rs, i + 1),
      countryregioncode = CountryregionId.get.unsafeGetNonNullable(rs, i + 2),
      isonlystateprovinceflag = Flag.get.unsafeGetNonNullable(rs, i + 3),
      name = Name.get.unsafeGetNonNullable(rs, i + 4),
      territoryid = SalesterritoryId.get.unsafeGetNonNullable(rs, i + 5),
      rowguid = TypoUUID.get.unsafeGetNonNullable(rs, i + 6),
      modifieddate = TypoLocalDateTime.get.unsafeGetNonNullable(rs, i + 7)
    )
  )
}
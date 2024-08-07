/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package stateprovince

import adventureworks.customtypes.Defaulted
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoUUID
import adventureworks.person.countryregion.CountryregionId
import adventureworks.public.Flag
import adventureworks.public.Name
import adventureworks.sales.salesterritory.SalesterritoryId
import doobie.enumerated.Nullability
import doobie.postgres.Text
import doobie.util.Read
import doobie.util.Write
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.Encoder
import java.sql.ResultSet

/** Table: person.stateprovince
    State and province lookup table.
    Primary key: stateprovinceid */
case class StateprovinceRow(
  /** Primary key for StateProvince records.
      Default: nextval('person.stateprovince_stateprovinceid_seq'::regclass) */
  stateprovinceid: StateprovinceId,
  /** ISO standard state or province code. */
  stateprovincecode: /* bpchar, max 3 chars */ String,
  /** ISO standard country or region code. Foreign key to CountryRegion.CountryRegionCode.
      Points to [[countryregion.CountryregionRow.countryregioncode]] */
  countryregioncode: CountryregionId,
  /** 0 = StateProvinceCode exists. 1 = StateProvinceCode unavailable, using CountryRegionCode.
      Default: true */
  isonlystateprovinceflag: Flag,
  /** State or province description. */
  name: Name,
  /** ID of the territory in which the state or province is located. Foreign key to SalesTerritory.SalesTerritoryID.
      Points to [[sales.salesterritory.SalesterritoryRow.territoryid]] */
  territoryid: SalesterritoryId,
  /** Default: uuid_generate_v1() */
  rowguid: TypoUUID,
  /** Default: now() */
  modifieddate: TypoLocalDateTime
){
   val id = stateprovinceid
   def toUnsavedRow(stateprovinceid: Defaulted[StateprovinceId], isonlystateprovinceflag: Defaulted[Flag] = Defaulted.Provided(this.isonlystateprovinceflag), rowguid: Defaulted[TypoUUID] = Defaulted.Provided(this.rowguid), modifieddate: Defaulted[TypoLocalDateTime] = Defaulted.Provided(this.modifieddate)): StateprovinceRowUnsaved =
     StateprovinceRowUnsaved(stateprovincecode, countryregioncode, name, territoryid, stateprovinceid, isonlystateprovinceflag, rowguid, modifieddate)
 }

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
  implicit lazy val text: Text[StateprovinceRow] = Text.instance[StateprovinceRow]{ (row, sb) =>
    StateprovinceId.text.unsafeEncode(row.stateprovinceid, sb)
    sb.append(Text.DELIMETER)
    Text.stringInstance.unsafeEncode(row.stateprovincecode, sb)
    sb.append(Text.DELIMETER)
    CountryregionId.text.unsafeEncode(row.countryregioncode, sb)
    sb.append(Text.DELIMETER)
    Flag.text.unsafeEncode(row.isonlystateprovinceflag, sb)
    sb.append(Text.DELIMETER)
    Name.text.unsafeEncode(row.name, sb)
    sb.append(Text.DELIMETER)
    SalesterritoryId.text.unsafeEncode(row.territoryid, sb)
    sb.append(Text.DELIMETER)
    TypoUUID.text.unsafeEncode(row.rowguid, sb)
    sb.append(Text.DELIMETER)
    TypoLocalDateTime.text.unsafeEncode(row.modifieddate, sb)
  }
  implicit lazy val write: Write[StateprovinceRow] = new Write[StateprovinceRow](
    puts = List((StateprovinceId.put, Nullability.NoNulls),
                (Meta.StringMeta.put, Nullability.NoNulls),
                (CountryregionId.put, Nullability.NoNulls),
                (Flag.put, Nullability.NoNulls),
                (Name.put, Nullability.NoNulls),
                (SalesterritoryId.put, Nullability.NoNulls),
                (TypoUUID.put, Nullability.NoNulls),
                (TypoLocalDateTime.put, Nullability.NoNulls)),
    toList = x => List(x.stateprovinceid, x.stateprovincecode, x.countryregioncode, x.isonlystateprovinceflag, x.name, x.territoryid, x.rowguid, x.modifieddate),
    unsafeSet = (rs, i, a) => {
                  StateprovinceId.put.unsafeSetNonNullable(rs, i + 0, a.stateprovinceid)
                  Meta.StringMeta.put.unsafeSetNonNullable(rs, i + 1, a.stateprovincecode)
                  CountryregionId.put.unsafeSetNonNullable(rs, i + 2, a.countryregioncode)
                  Flag.put.unsafeSetNonNullable(rs, i + 3, a.isonlystateprovinceflag)
                  Name.put.unsafeSetNonNullable(rs, i + 4, a.name)
                  SalesterritoryId.put.unsafeSetNonNullable(rs, i + 5, a.territoryid)
                  TypoUUID.put.unsafeSetNonNullable(rs, i + 6, a.rowguid)
                  TypoLocalDateTime.put.unsafeSetNonNullable(rs, i + 7, a.modifieddate)
                },
    unsafeUpdate = (ps, i, a) => {
                     StateprovinceId.put.unsafeUpdateNonNullable(ps, i + 0, a.stateprovinceid)
                     Meta.StringMeta.put.unsafeUpdateNonNullable(ps, i + 1, a.stateprovincecode)
                     CountryregionId.put.unsafeUpdateNonNullable(ps, i + 2, a.countryregioncode)
                     Flag.put.unsafeUpdateNonNullable(ps, i + 3, a.isonlystateprovinceflag)
                     Name.put.unsafeUpdateNonNullable(ps, i + 4, a.name)
                     SalesterritoryId.put.unsafeUpdateNonNullable(ps, i + 5, a.territoryid)
                     TypoUUID.put.unsafeUpdateNonNullable(ps, i + 6, a.rowguid)
                     TypoLocalDateTime.put.unsafeUpdateNonNullable(ps, i + 7, a.modifieddate)
                   }
  )
}

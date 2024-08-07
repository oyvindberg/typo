/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package location

import adventureworks.customtypes.Defaulted
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.public.Name
import doobie.enumerated.Nullability
import doobie.postgres.Text
import doobie.util.Read
import doobie.util.Write
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.Encoder
import java.sql.ResultSet

/** Table: production.location
    Product inventory and manufacturing locations.
    Primary key: locationid */
case class LocationRow(
  /** Primary key for Location records.
      Default: nextval('production.location_locationid_seq'::regclass) */
  locationid: LocationId,
  /** Location description. */
  name: Name,
  /** Standard hourly cost of the manufacturing location.
      Default: 0.00
      Constraint CK_Location_CostRate affecting columns costrate: ((costrate >= 0.00)) */
  costrate: BigDecimal,
  /** Work capacity (in hours) of the manufacturing location.
      Default: 0.00
      Constraint CK_Location_Availability affecting columns availability: ((availability >= 0.00)) */
  availability: BigDecimal,
  /** Default: now() */
  modifieddate: TypoLocalDateTime
){
   val id = locationid
   def toUnsavedRow(locationid: Defaulted[LocationId], costrate: Defaulted[BigDecimal] = Defaulted.Provided(this.costrate), availability: Defaulted[BigDecimal] = Defaulted.Provided(this.availability), modifieddate: Defaulted[TypoLocalDateTime] = Defaulted.Provided(this.modifieddate)): LocationRowUnsaved =
     LocationRowUnsaved(name, locationid, costrate, availability, modifieddate)
 }

object LocationRow {
  implicit lazy val decoder: Decoder[LocationRow] = Decoder.forProduct5[LocationRow, LocationId, Name, BigDecimal, BigDecimal, TypoLocalDateTime]("locationid", "name", "costrate", "availability", "modifieddate")(LocationRow.apply)(LocationId.decoder, Name.decoder, Decoder.decodeBigDecimal, Decoder.decodeBigDecimal, TypoLocalDateTime.decoder)
  implicit lazy val encoder: Encoder[LocationRow] = Encoder.forProduct5[LocationRow, LocationId, Name, BigDecimal, BigDecimal, TypoLocalDateTime]("locationid", "name", "costrate", "availability", "modifieddate")(x => (x.locationid, x.name, x.costrate, x.availability, x.modifieddate))(LocationId.encoder, Name.encoder, Encoder.encodeBigDecimal, Encoder.encodeBigDecimal, TypoLocalDateTime.encoder)
  implicit lazy val read: Read[LocationRow] = new Read[LocationRow](
    gets = List(
      (LocationId.get, Nullability.NoNulls),
      (Name.get, Nullability.NoNulls),
      (Meta.ScalaBigDecimalMeta.get, Nullability.NoNulls),
      (Meta.ScalaBigDecimalMeta.get, Nullability.NoNulls),
      (TypoLocalDateTime.get, Nullability.NoNulls)
    ),
    unsafeGet = (rs: ResultSet, i: Int) => LocationRow(
      locationid = LocationId.get.unsafeGetNonNullable(rs, i + 0),
      name = Name.get.unsafeGetNonNullable(rs, i + 1),
      costrate = Meta.ScalaBigDecimalMeta.get.unsafeGetNonNullable(rs, i + 2),
      availability = Meta.ScalaBigDecimalMeta.get.unsafeGetNonNullable(rs, i + 3),
      modifieddate = TypoLocalDateTime.get.unsafeGetNonNullable(rs, i + 4)
    )
  )
  implicit lazy val text: Text[LocationRow] = Text.instance[LocationRow]{ (row, sb) =>
    LocationId.text.unsafeEncode(row.locationid, sb)
    sb.append(Text.DELIMETER)
    Name.text.unsafeEncode(row.name, sb)
    sb.append(Text.DELIMETER)
    Text.bigDecimalInstance.unsafeEncode(row.costrate, sb)
    sb.append(Text.DELIMETER)
    Text.bigDecimalInstance.unsafeEncode(row.availability, sb)
    sb.append(Text.DELIMETER)
    TypoLocalDateTime.text.unsafeEncode(row.modifieddate, sb)
  }
  implicit lazy val write: Write[LocationRow] = new Write[LocationRow](
    puts = List((LocationId.put, Nullability.NoNulls),
                (Name.put, Nullability.NoNulls),
                (Meta.ScalaBigDecimalMeta.put, Nullability.NoNulls),
                (Meta.ScalaBigDecimalMeta.put, Nullability.NoNulls),
                (TypoLocalDateTime.put, Nullability.NoNulls)),
    toList = x => List(x.locationid, x.name, x.costrate, x.availability, x.modifieddate),
    unsafeSet = (rs, i, a) => {
                  LocationId.put.unsafeSetNonNullable(rs, i + 0, a.locationid)
                  Name.put.unsafeSetNonNullable(rs, i + 1, a.name)
                  Meta.ScalaBigDecimalMeta.put.unsafeSetNonNullable(rs, i + 2, a.costrate)
                  Meta.ScalaBigDecimalMeta.put.unsafeSetNonNullable(rs, i + 3, a.availability)
                  TypoLocalDateTime.put.unsafeSetNonNullable(rs, i + 4, a.modifieddate)
                },
    unsafeUpdate = (ps, i, a) => {
                     LocationId.put.unsafeUpdateNonNullable(ps, i + 0, a.locationid)
                     Name.put.unsafeUpdateNonNullable(ps, i + 1, a.name)
                     Meta.ScalaBigDecimalMeta.put.unsafeUpdateNonNullable(ps, i + 2, a.costrate)
                     Meta.ScalaBigDecimalMeta.put.unsafeUpdateNonNullable(ps, i + 3, a.availability)
                     TypoLocalDateTime.put.unsafeUpdateNonNullable(ps, i + 4, a.modifieddate)
                   }
  )
}

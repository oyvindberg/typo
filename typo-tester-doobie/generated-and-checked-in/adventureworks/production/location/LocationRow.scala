/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package location

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.public.Name
import doobie.enumerated.Nullability
import doobie.util.Read
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.Encoder
import java.sql.ResultSet

case class LocationRow(
  /** Primary key for Location records. */
  locationid: LocationId,
  /** Location description. */
  name: Name,
  /** Standard hourly cost of the manufacturing location.
      Constraint CK_Location_CostRate affecting columns "costrate":  ((costrate >= 0.00)) */
  costrate: BigDecimal,
  /** Work capacity (in hours) of the manufacturing location.
      Constraint CK_Location_Availability affecting columns "availability":  ((availability >= 0.00)) */
  availability: BigDecimal,
  modifieddate: TypoLocalDateTime
)

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
}
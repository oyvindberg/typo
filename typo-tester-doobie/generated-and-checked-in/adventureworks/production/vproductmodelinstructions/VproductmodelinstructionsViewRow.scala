/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package vproductmodelinstructions

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoUUID
import adventureworks.production.productmodel.ProductmodelId
import adventureworks.public.Name
import doobie.enumerated.Nullability
import doobie.util.Read
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.Encoder
import java.sql.ResultSet

case class VproductmodelinstructionsViewRow(
  /** Points to [[productmodel.ProductmodelRow.productmodelid]] */
  productmodelid: ProductmodelId,
  /** Points to [[productmodel.ProductmodelRow.name]] */
  name: Name,
  instructions: /* nullability unknown */ Option[String],
  LocationID: /* nullability unknown */ Option[Int],
  SetupHours: /* nullability unknown */ Option[BigDecimal],
  MachineHours: /* nullability unknown */ Option[BigDecimal],
  LaborHours: /* nullability unknown */ Option[BigDecimal],
  LotSize: /* nullability unknown */ Option[Int],
  Step: /* nullability unknown */ Option[/* max 1024 chars */ String],
  /** Points to [[productmodel.ProductmodelRow.rowguid]] */
  rowguid: TypoUUID,
  /** Points to [[productmodel.ProductmodelRow.modifieddate]] */
  modifieddate: TypoLocalDateTime
)

object VproductmodelinstructionsViewRow {
  implicit lazy val decoder: Decoder[VproductmodelinstructionsViewRow] = Decoder.forProduct11[VproductmodelinstructionsViewRow, ProductmodelId, Name, /* nullability unknown */ Option[String], /* nullability unknown */ Option[Int], /* nullability unknown */ Option[BigDecimal], /* nullability unknown */ Option[BigDecimal], /* nullability unknown */ Option[BigDecimal], /* nullability unknown */ Option[Int], /* nullability unknown */ Option[/* max 1024 chars */ String], TypoUUID, TypoLocalDateTime]("productmodelid", "name", "instructions", "LocationID", "SetupHours", "MachineHours", "LaborHours", "LotSize", "Step", "rowguid", "modifieddate")(VproductmodelinstructionsViewRow.apply)(ProductmodelId.decoder, Name.decoder, Decoder.decodeOption(Decoder.decodeString), Decoder.decodeOption(Decoder.decodeInt), Decoder.decodeOption(Decoder.decodeBigDecimal), Decoder.decodeOption(Decoder.decodeBigDecimal), Decoder.decodeOption(Decoder.decodeBigDecimal), Decoder.decodeOption(Decoder.decodeInt), Decoder.decodeOption(Decoder.decodeString), TypoUUID.decoder, TypoLocalDateTime.decoder)
  implicit lazy val encoder: Encoder[VproductmodelinstructionsViewRow] = Encoder.forProduct11[VproductmodelinstructionsViewRow, ProductmodelId, Name, /* nullability unknown */ Option[String], /* nullability unknown */ Option[Int], /* nullability unknown */ Option[BigDecimal], /* nullability unknown */ Option[BigDecimal], /* nullability unknown */ Option[BigDecimal], /* nullability unknown */ Option[Int], /* nullability unknown */ Option[/* max 1024 chars */ String], TypoUUID, TypoLocalDateTime]("productmodelid", "name", "instructions", "LocationID", "SetupHours", "MachineHours", "LaborHours", "LotSize", "Step", "rowguid", "modifieddate")(x => (x.productmodelid, x.name, x.instructions, x.LocationID, x.SetupHours, x.MachineHours, x.LaborHours, x.LotSize, x.Step, x.rowguid, x.modifieddate))(ProductmodelId.encoder, Name.encoder, Encoder.encodeOption(Encoder.encodeString), Encoder.encodeOption(Encoder.encodeInt), Encoder.encodeOption(Encoder.encodeBigDecimal), Encoder.encodeOption(Encoder.encodeBigDecimal), Encoder.encodeOption(Encoder.encodeBigDecimal), Encoder.encodeOption(Encoder.encodeInt), Encoder.encodeOption(Encoder.encodeString), TypoUUID.encoder, TypoLocalDateTime.encoder)
  implicit lazy val read: Read[VproductmodelinstructionsViewRow] = new Read[VproductmodelinstructionsViewRow](
    gets = List(
      (ProductmodelId.get, Nullability.NoNulls),
      (Name.get, Nullability.NoNulls),
      (Meta.StringMeta.get, Nullability.Nullable),
      (Meta.IntMeta.get, Nullability.Nullable),
      (Meta.ScalaBigDecimalMeta.get, Nullability.Nullable),
      (Meta.ScalaBigDecimalMeta.get, Nullability.Nullable),
      (Meta.ScalaBigDecimalMeta.get, Nullability.Nullable),
      (Meta.IntMeta.get, Nullability.Nullable),
      (Meta.StringMeta.get, Nullability.Nullable),
      (TypoUUID.get, Nullability.NoNulls),
      (TypoLocalDateTime.get, Nullability.NoNulls)
    ),
    unsafeGet = (rs: ResultSet, i: Int) => VproductmodelinstructionsViewRow(
      productmodelid = ProductmodelId.get.unsafeGetNonNullable(rs, i + 0),
      name = Name.get.unsafeGetNonNullable(rs, i + 1),
      instructions = Meta.StringMeta.get.unsafeGetNullable(rs, i + 2),
      LocationID = Meta.IntMeta.get.unsafeGetNullable(rs, i + 3),
      SetupHours = Meta.ScalaBigDecimalMeta.get.unsafeGetNullable(rs, i + 4),
      MachineHours = Meta.ScalaBigDecimalMeta.get.unsafeGetNullable(rs, i + 5),
      LaborHours = Meta.ScalaBigDecimalMeta.get.unsafeGetNullable(rs, i + 6),
      LotSize = Meta.IntMeta.get.unsafeGetNullable(rs, i + 7),
      Step = Meta.StringMeta.get.unsafeGetNullable(rs, i + 8),
      rowguid = TypoUUID.get.unsafeGetNonNullable(rs, i + 9),
      modifieddate = TypoLocalDateTime.get.unsafeGetNonNullable(rs, i + 10)
    )
  )
}
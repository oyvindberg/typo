/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package so

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoUUID
import adventureworks.sales.specialoffer.SpecialofferId
import doobie.util.Read
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.Encoder

/** View: sa.so */
case class SoViewRow(
  /** Points to [[sales.specialoffer.SpecialofferRow.specialofferid]] */
  id: SpecialofferId,
  /** Points to [[sales.specialoffer.SpecialofferRow.specialofferid]] */
  specialofferid: SpecialofferId,
  /** Points to [[sales.specialoffer.SpecialofferRow.description]] */
  description: /* max 255 chars */ String,
  /** Points to [[sales.specialoffer.SpecialofferRow.discountpct]] */
  discountpct: BigDecimal,
  /** Points to [[sales.specialoffer.SpecialofferRow.type]] */
  `type`: /* max 50 chars */ String,
  /** Points to [[sales.specialoffer.SpecialofferRow.category]] */
  category: /* max 50 chars */ String,
  /** Points to [[sales.specialoffer.SpecialofferRow.startdate]] */
  startdate: TypoLocalDateTime,
  /** Points to [[sales.specialoffer.SpecialofferRow.enddate]] */
  enddate: TypoLocalDateTime,
  /** Points to [[sales.specialoffer.SpecialofferRow.minqty]] */
  minqty: Int,
  /** Points to [[sales.specialoffer.SpecialofferRow.maxqty]] */
  maxqty: Option[Int],
  /** Points to [[sales.specialoffer.SpecialofferRow.rowguid]] */
  rowguid: TypoUUID,
  /** Points to [[sales.specialoffer.SpecialofferRow.modifieddate]] */
  modifieddate: TypoLocalDateTime
)

object SoViewRow {
  implicit lazy val decoder: Decoder[SoViewRow] = Decoder.forProduct12[SoViewRow, SpecialofferId, SpecialofferId, /* max 255 chars */ String, BigDecimal, /* max 50 chars */ String, /* max 50 chars */ String, TypoLocalDateTime, TypoLocalDateTime, Int, Option[Int], TypoUUID, TypoLocalDateTime]("id", "specialofferid", "description", "discountpct", "type", "category", "startdate", "enddate", "minqty", "maxqty", "rowguid", "modifieddate")(SoViewRow.apply)(SpecialofferId.decoder, SpecialofferId.decoder, Decoder.decodeString, Decoder.decodeBigDecimal, Decoder.decodeString, Decoder.decodeString, TypoLocalDateTime.decoder, TypoLocalDateTime.decoder, Decoder.decodeInt, Decoder.decodeOption(Decoder.decodeInt), TypoUUID.decoder, TypoLocalDateTime.decoder)
  implicit lazy val encoder: Encoder[SoViewRow] = Encoder.forProduct12[SoViewRow, SpecialofferId, SpecialofferId, /* max 255 chars */ String, BigDecimal, /* max 50 chars */ String, /* max 50 chars */ String, TypoLocalDateTime, TypoLocalDateTime, Int, Option[Int], TypoUUID, TypoLocalDateTime]("id", "specialofferid", "description", "discountpct", "type", "category", "startdate", "enddate", "minqty", "maxqty", "rowguid", "modifieddate")(x => (x.id, x.specialofferid, x.description, x.discountpct, x.`type`, x.category, x.startdate, x.enddate, x.minqty, x.maxqty, x.rowguid, x.modifieddate))(SpecialofferId.encoder, SpecialofferId.encoder, Encoder.encodeString, Encoder.encodeBigDecimal, Encoder.encodeString, Encoder.encodeString, TypoLocalDateTime.encoder, TypoLocalDateTime.encoder, Encoder.encodeInt, Encoder.encodeOption(Encoder.encodeInt), TypoUUID.encoder, TypoLocalDateTime.encoder)
  implicit lazy val read: Read[SoViewRow] = new Read.CompositeOfInstances(Array(
    new Read.Single(SpecialofferId.get).asInstanceOf[Read[Any]],
      new Read.Single(SpecialofferId.get).asInstanceOf[Read[Any]],
      new Read.Single(Meta.StringMeta.get).asInstanceOf[Read[Any]],
      new Read.Single(Meta.ScalaBigDecimalMeta.get).asInstanceOf[Read[Any]],
      new Read.Single(Meta.StringMeta.get).asInstanceOf[Read[Any]],
      new Read.Single(Meta.StringMeta.get).asInstanceOf[Read[Any]],
      new Read.Single(TypoLocalDateTime.get).asInstanceOf[Read[Any]],
      new Read.Single(TypoLocalDateTime.get).asInstanceOf[Read[Any]],
      new Read.Single(Meta.IntMeta.get).asInstanceOf[Read[Any]],
      new Read.SingleOpt(Meta.IntMeta.get).asInstanceOf[Read[Any]],
      new Read.Single(TypoUUID.get).asInstanceOf[Read[Any]],
      new Read.Single(TypoLocalDateTime.get).asInstanceOf[Read[Any]]
  ))(using scala.reflect.ClassTag.Any).map { arr =>
    SoViewRow(
      id = arr(0).asInstanceOf[SpecialofferId],
          specialofferid = arr(1).asInstanceOf[SpecialofferId],
          description = arr(2).asInstanceOf[/* max 255 chars */ String],
          discountpct = arr(3).asInstanceOf[BigDecimal],
          `type` = arr(4).asInstanceOf[/* max 50 chars */ String],
          category = arr(5).asInstanceOf[/* max 50 chars */ String],
          startdate = arr(6).asInstanceOf[TypoLocalDateTime],
          enddate = arr(7).asInstanceOf[TypoLocalDateTime],
          minqty = arr(8).asInstanceOf[Int],
          maxqty = arr(9).asInstanceOf[Option[Int]],
          rowguid = arr(10).asInstanceOf[TypoUUID],
          modifieddate = arr(11).asInstanceOf[TypoLocalDateTime]
    )
  }
}

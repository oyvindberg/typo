/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package specialoffer

import adventureworks.customtypes.Defaulted
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoUUID
import doobie.postgres.Text
import doobie.util.Read
import doobie.util.Write
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.Encoder

/** Table: sales.specialoffer
    Sale discounts lookup table.
    Primary key: specialofferid */
case class SpecialofferRow(
  /** Primary key for SpecialOffer records.
      Default: nextval('sales.specialoffer_specialofferid_seq'::regclass) */
  specialofferid: SpecialofferId,
  /** Discount description. */
  description: /* max 255 chars */ String,
  /** Discount precentage.
      Default: 0.00
      Constraint CK_SpecialOffer_DiscountPct affecting columns discountpct: ((discountpct >= 0.00)) */
  discountpct: BigDecimal,
  /** Discount type category. */
  `type`: /* max 50 chars */ String,
  /** Group the discount applies to such as Reseller or Customer. */
  category: /* max 50 chars */ String,
  /** Discount start date.
      Constraint CK_SpecialOffer_EndDate affecting columns enddate, startdate: ((enddate >= startdate)) */
  startdate: TypoLocalDateTime,
  /** Discount end date.
      Constraint CK_SpecialOffer_EndDate affecting columns enddate, startdate: ((enddate >= startdate)) */
  enddate: TypoLocalDateTime,
  /** Minimum discount percent allowed.
      Default: 0
      Constraint CK_SpecialOffer_MinQty affecting columns minqty: ((minqty >= 0)) */
  minqty: Int,
  /** Maximum discount percent allowed.
      Constraint CK_SpecialOffer_MaxQty affecting columns maxqty: ((maxqty >= 0)) */
  maxqty: Option[Int],
  /** Default: uuid_generate_v1() */
  rowguid: TypoUUID,
  /** Default: now() */
  modifieddate: TypoLocalDateTime
){
   val id = specialofferid
   def toUnsavedRow(specialofferid: Defaulted[SpecialofferId], discountpct: Defaulted[BigDecimal] = Defaulted.Provided(this.discountpct), minqty: Defaulted[Int] = Defaulted.Provided(this.minqty), rowguid: Defaulted[TypoUUID] = Defaulted.Provided(this.rowguid), modifieddate: Defaulted[TypoLocalDateTime] = Defaulted.Provided(this.modifieddate)): SpecialofferRowUnsaved =
     SpecialofferRowUnsaved(description, `type`, category, startdate, enddate, maxqty, specialofferid, discountpct, minqty, rowguid, modifieddate)
 }

object SpecialofferRow {
  implicit lazy val decoder: Decoder[SpecialofferRow] = Decoder.forProduct11[SpecialofferRow, SpecialofferId, /* max 255 chars */ String, BigDecimal, /* max 50 chars */ String, /* max 50 chars */ String, TypoLocalDateTime, TypoLocalDateTime, Int, Option[Int], TypoUUID, TypoLocalDateTime]("specialofferid", "description", "discountpct", "type", "category", "startdate", "enddate", "minqty", "maxqty", "rowguid", "modifieddate")(SpecialofferRow.apply)(SpecialofferId.decoder, Decoder.decodeString, Decoder.decodeBigDecimal, Decoder.decodeString, Decoder.decodeString, TypoLocalDateTime.decoder, TypoLocalDateTime.decoder, Decoder.decodeInt, Decoder.decodeOption(Decoder.decodeInt), TypoUUID.decoder, TypoLocalDateTime.decoder)
  implicit lazy val encoder: Encoder[SpecialofferRow] = Encoder.forProduct11[SpecialofferRow, SpecialofferId, /* max 255 chars */ String, BigDecimal, /* max 50 chars */ String, /* max 50 chars */ String, TypoLocalDateTime, TypoLocalDateTime, Int, Option[Int], TypoUUID, TypoLocalDateTime]("specialofferid", "description", "discountpct", "type", "category", "startdate", "enddate", "minqty", "maxqty", "rowguid", "modifieddate")(x => (x.specialofferid, x.description, x.discountpct, x.`type`, x.category, x.startdate, x.enddate, x.minqty, x.maxqty, x.rowguid, x.modifieddate))(SpecialofferId.encoder, Encoder.encodeString, Encoder.encodeBigDecimal, Encoder.encodeString, Encoder.encodeString, TypoLocalDateTime.encoder, TypoLocalDateTime.encoder, Encoder.encodeInt, Encoder.encodeOption(Encoder.encodeInt), TypoUUID.encoder, TypoLocalDateTime.encoder)
  implicit lazy val read: Read[SpecialofferRow] = new Read.CompositeOfInstances(Array(
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
    SpecialofferRow(
      specialofferid = arr(0).asInstanceOf[SpecialofferId],
          description = arr(1).asInstanceOf[/* max 255 chars */ String],
          discountpct = arr(2).asInstanceOf[BigDecimal],
          `type` = arr(3).asInstanceOf[/* max 50 chars */ String],
          category = arr(4).asInstanceOf[/* max 50 chars */ String],
          startdate = arr(5).asInstanceOf[TypoLocalDateTime],
          enddate = arr(6).asInstanceOf[TypoLocalDateTime],
          minqty = arr(7).asInstanceOf[Int],
          maxqty = arr(8).asInstanceOf[Option[Int]],
          rowguid = arr(9).asInstanceOf[TypoUUID],
          modifieddate = arr(10).asInstanceOf[TypoLocalDateTime]
    )
  }
  implicit lazy val text: Text[SpecialofferRow] = Text.instance[SpecialofferRow]{ (row, sb) =>
    SpecialofferId.text.unsafeEncode(row.specialofferid, sb)
    sb.append(Text.DELIMETER)
    Text.stringInstance.unsafeEncode(row.description, sb)
    sb.append(Text.DELIMETER)
    Text.bigDecimalInstance.unsafeEncode(row.discountpct, sb)
    sb.append(Text.DELIMETER)
    Text.stringInstance.unsafeEncode(row.`type`, sb)
    sb.append(Text.DELIMETER)
    Text.stringInstance.unsafeEncode(row.category, sb)
    sb.append(Text.DELIMETER)
    TypoLocalDateTime.text.unsafeEncode(row.startdate, sb)
    sb.append(Text.DELIMETER)
    TypoLocalDateTime.text.unsafeEncode(row.enddate, sb)
    sb.append(Text.DELIMETER)
    Text.intInstance.unsafeEncode(row.minqty, sb)
    sb.append(Text.DELIMETER)
    Text.option(Text.intInstance).unsafeEncode(row.maxqty, sb)
    sb.append(Text.DELIMETER)
    TypoUUID.text.unsafeEncode(row.rowguid, sb)
    sb.append(Text.DELIMETER)
    TypoLocalDateTime.text.unsafeEncode(row.modifieddate, sb)
  }
  implicit lazy val write: Write[SpecialofferRow] = new Write.Composite[SpecialofferRow](
    List(new Write.Single(SpecialofferId.put),
         new Write.Single(Meta.StringMeta.put),
         new Write.Single(Meta.ScalaBigDecimalMeta.put),
         new Write.Single(Meta.StringMeta.put),
         new Write.Single(Meta.StringMeta.put),
         new Write.Single(TypoLocalDateTime.put),
         new Write.Single(TypoLocalDateTime.put),
         new Write.Single(Meta.IntMeta.put),
         new Write.Single(Meta.IntMeta.put).toOpt,
         new Write.Single(TypoUUID.put),
         new Write.Single(TypoLocalDateTime.put)),
    a => List(a.specialofferid, a.description, a.discountpct, a.`type`, a.category, a.startdate, a.enddate, a.minqty, a.maxqty, a.rowguid, a.modifieddate)
  )
}

/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package sr

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.public.Name
import adventureworks.sales.salesreason.SalesreasonId
import doobie.util.Read
import io.circe.Decoder
import io.circe.Encoder

/** View: sa.sr */
case class SrViewRow(
  /** Points to [[sales.salesreason.SalesreasonRow.salesreasonid]] */
  id: SalesreasonId,
  /** Points to [[sales.salesreason.SalesreasonRow.salesreasonid]] */
  salesreasonid: SalesreasonId,
  /** Points to [[sales.salesreason.SalesreasonRow.name]] */
  name: Name,
  /** Points to [[sales.salesreason.SalesreasonRow.reasontype]] */
  reasontype: Name,
  /** Points to [[sales.salesreason.SalesreasonRow.modifieddate]] */
  modifieddate: TypoLocalDateTime
)

object SrViewRow {
  implicit lazy val decoder: Decoder[SrViewRow] = Decoder.forProduct5[SrViewRow, SalesreasonId, SalesreasonId, Name, Name, TypoLocalDateTime]("id", "salesreasonid", "name", "reasontype", "modifieddate")(SrViewRow.apply)(SalesreasonId.decoder, SalesreasonId.decoder, Name.decoder, Name.decoder, TypoLocalDateTime.decoder)
  implicit lazy val encoder: Encoder[SrViewRow] = Encoder.forProduct5[SrViewRow, SalesreasonId, SalesreasonId, Name, Name, TypoLocalDateTime]("id", "salesreasonid", "name", "reasontype", "modifieddate")(x => (x.id, x.salesreasonid, x.name, x.reasontype, x.modifieddate))(SalesreasonId.encoder, SalesreasonId.encoder, Name.encoder, Name.encoder, TypoLocalDateTime.encoder)
  implicit lazy val read: Read[SrViewRow] = new Read.CompositeOfInstances(Array(
    new Read.Single(SalesreasonId.get).asInstanceOf[Read[Any]],
      new Read.Single(SalesreasonId.get).asInstanceOf[Read[Any]],
      new Read.Single(Name.get).asInstanceOf[Read[Any]],
      new Read.Single(Name.get).asInstanceOf[Read[Any]],
      new Read.Single(TypoLocalDateTime.get).asInstanceOf[Read[Any]]
  ))(using scala.reflect.ClassTag.Any).map { arr =>
    SrViewRow(
      id = arr(0).asInstanceOf[SalesreasonId],
          salesreasonid = arr(1).asInstanceOf[SalesreasonId],
          name = arr(2).asInstanceOf[Name],
          reasontype = arr(3).asInstanceOf[Name],
          modifieddate = arr(4).asInstanceOf[TypoLocalDateTime]
    )
  }
}

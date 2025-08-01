/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package pm

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoUUID
import adventureworks.customtypes.TypoXml
import adventureworks.production.productmodel.ProductmodelId
import adventureworks.public.Name
import doobie.util.Read
import io.circe.Decoder
import io.circe.Encoder

/** View: pr.pm */
case class PmViewRow(
  /** Points to [[production.productmodel.ProductmodelRow.productmodelid]] */
  id: ProductmodelId,
  /** Points to [[production.productmodel.ProductmodelRow.productmodelid]] */
  productmodelid: ProductmodelId,
  /** Points to [[production.productmodel.ProductmodelRow.name]] */
  name: Name,
  /** Points to [[production.productmodel.ProductmodelRow.catalogdescription]] */
  catalogdescription: Option[TypoXml],
  /** Points to [[production.productmodel.ProductmodelRow.instructions]] */
  instructions: Option[TypoXml],
  /** Points to [[production.productmodel.ProductmodelRow.rowguid]] */
  rowguid: TypoUUID,
  /** Points to [[production.productmodel.ProductmodelRow.modifieddate]] */
  modifieddate: TypoLocalDateTime
)

object PmViewRow {
  implicit lazy val decoder: Decoder[PmViewRow] = Decoder.forProduct7[PmViewRow, ProductmodelId, ProductmodelId, Name, Option[TypoXml], Option[TypoXml], TypoUUID, TypoLocalDateTime]("id", "productmodelid", "name", "catalogdescription", "instructions", "rowguid", "modifieddate")(PmViewRow.apply)(ProductmodelId.decoder, ProductmodelId.decoder, Name.decoder, Decoder.decodeOption(TypoXml.decoder), Decoder.decodeOption(TypoXml.decoder), TypoUUID.decoder, TypoLocalDateTime.decoder)
  implicit lazy val encoder: Encoder[PmViewRow] = Encoder.forProduct7[PmViewRow, ProductmodelId, ProductmodelId, Name, Option[TypoXml], Option[TypoXml], TypoUUID, TypoLocalDateTime]("id", "productmodelid", "name", "catalogdescription", "instructions", "rowguid", "modifieddate")(x => (x.id, x.productmodelid, x.name, x.catalogdescription, x.instructions, x.rowguid, x.modifieddate))(ProductmodelId.encoder, ProductmodelId.encoder, Name.encoder, Encoder.encodeOption(TypoXml.encoder), Encoder.encodeOption(TypoXml.encoder), TypoUUID.encoder, TypoLocalDateTime.encoder)
  implicit lazy val read: Read[PmViewRow] = new Read.CompositeOfInstances(Array(
    new Read.Single(ProductmodelId.get).asInstanceOf[Read[Any]],
      new Read.Single(ProductmodelId.get).asInstanceOf[Read[Any]],
      new Read.Single(Name.get).asInstanceOf[Read[Any]],
      new Read.SingleOpt(TypoXml.get).asInstanceOf[Read[Any]],
      new Read.SingleOpt(TypoXml.get).asInstanceOf[Read[Any]],
      new Read.Single(TypoUUID.get).asInstanceOf[Read[Any]],
      new Read.Single(TypoLocalDateTime.get).asInstanceOf[Read[Any]]
  ))(using scala.reflect.ClassTag.Any).map { arr =>
    PmViewRow(
      id = arr(0).asInstanceOf[ProductmodelId],
          productmodelid = arr(1).asInstanceOf[ProductmodelId],
          name = arr(2).asInstanceOf[Name],
          catalogdescription = arr(3).asInstanceOf[Option[TypoXml]],
          instructions = arr(4).asInstanceOf[Option[TypoXml]],
          rowguid = arr(5).asInstanceOf[TypoUUID],
          modifieddate = arr(6).asInstanceOf[TypoLocalDateTime]
    )
  }
}

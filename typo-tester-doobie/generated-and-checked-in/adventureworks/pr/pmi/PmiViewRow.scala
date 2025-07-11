/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package pmi

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.production.illustration.IllustrationId
import adventureworks.production.productmodel.ProductmodelId
import doobie.util.Read
import io.circe.Decoder
import io.circe.Encoder

/** View: pr.pmi */
case class PmiViewRow(
  /** Points to [[production.productmodelillustration.ProductmodelillustrationRow.productmodelid]] */
  productmodelid: ProductmodelId,
  /** Points to [[production.productmodelillustration.ProductmodelillustrationRow.illustrationid]] */
  illustrationid: IllustrationId,
  /** Points to [[production.productmodelillustration.ProductmodelillustrationRow.modifieddate]] */
  modifieddate: TypoLocalDateTime
)

object PmiViewRow {
  implicit lazy val decoder: Decoder[PmiViewRow] = Decoder.forProduct3[PmiViewRow, ProductmodelId, IllustrationId, TypoLocalDateTime]("productmodelid", "illustrationid", "modifieddate")(PmiViewRow.apply)(ProductmodelId.decoder, IllustrationId.decoder, TypoLocalDateTime.decoder)
  implicit lazy val encoder: Encoder[PmiViewRow] = Encoder.forProduct3[PmiViewRow, ProductmodelId, IllustrationId, TypoLocalDateTime]("productmodelid", "illustrationid", "modifieddate")(x => (x.productmodelid, x.illustrationid, x.modifieddate))(ProductmodelId.encoder, IllustrationId.encoder, TypoLocalDateTime.encoder)
  implicit lazy val read: Read[PmiViewRow] = new Read.CompositeOfInstances(Array(
    new Read.Single(ProductmodelId.get).asInstanceOf[Read[Any]],
      new Read.Single(IllustrationId.get).asInstanceOf[Read[Any]],
      new Read.Single(TypoLocalDateTime.get).asInstanceOf[Read[Any]]
  ))(using scala.reflect.ClassTag.Any).map { arr =>
    PmiViewRow(
      productmodelid = arr(0).asInstanceOf[ProductmodelId],
          illustrationid = arr(1).asInstanceOf[IllustrationId],
          modifieddate = arr(2).asInstanceOf[TypoLocalDateTime]
    )
  }
}

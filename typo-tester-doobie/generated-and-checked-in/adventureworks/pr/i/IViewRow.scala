/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package i

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoXml
import adventureworks.production.illustration.IllustrationId
import doobie.enumerated.Nullability
import doobie.util.Read
import io.circe.Decoder
import io.circe.Encoder
import java.sql.ResultSet

case class IViewRow(
  /** Points to [[production.illustration.IllustrationRow.illustrationid]] */
  id: IllustrationId,
  /** Points to [[production.illustration.IllustrationRow.illustrationid]] */
  illustrationid: IllustrationId,
  /** Points to [[production.illustration.IllustrationRow.diagram]] */
  diagram: Option[TypoXml],
  /** Points to [[production.illustration.IllustrationRow.modifieddate]] */
  modifieddate: TypoLocalDateTime
)

object IViewRow {
  implicit lazy val decoder: Decoder[IViewRow] = Decoder.forProduct4[IViewRow, IllustrationId, IllustrationId, Option[TypoXml], TypoLocalDateTime]("id", "illustrationid", "diagram", "modifieddate")(IViewRow.apply)(IllustrationId.decoder, IllustrationId.decoder, Decoder.decodeOption(TypoXml.decoder), TypoLocalDateTime.decoder)
  implicit lazy val encoder: Encoder[IViewRow] = Encoder.forProduct4[IViewRow, IllustrationId, IllustrationId, Option[TypoXml], TypoLocalDateTime]("id", "illustrationid", "diagram", "modifieddate")(x => (x.id, x.illustrationid, x.diagram, x.modifieddate))(IllustrationId.encoder, IllustrationId.encoder, Encoder.encodeOption(TypoXml.encoder), TypoLocalDateTime.encoder)
  implicit lazy val read: Read[IViewRow] = new Read[IViewRow](
    gets = List(
      (IllustrationId.get, Nullability.NoNulls),
      (IllustrationId.get, Nullability.NoNulls),
      (TypoXml.get, Nullability.Nullable),
      (TypoLocalDateTime.get, Nullability.NoNulls)
    ),
    unsafeGet = (rs: ResultSet, i: Int) => IViewRow(
      id = IllustrationId.get.unsafeGetNonNullable(rs, i + 0),
      illustrationid = IllustrationId.get.unsafeGetNonNullable(rs, i + 1),
      diagram = TypoXml.get.unsafeGetNullable(rs, i + 2),
      modifieddate = TypoLocalDateTime.get.unsafeGetNonNullable(rs, i + 3)
    )
  )
}
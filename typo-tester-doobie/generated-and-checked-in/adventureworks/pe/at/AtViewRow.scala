/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pe
package at

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoUUID
import adventureworks.person.addresstype.AddresstypeId
import adventureworks.public.Name
import doobie.util.Read
import io.circe.Decoder
import io.circe.Encoder

/** View: pe.at */
case class AtViewRow(
  /** Points to [[person.addresstype.AddresstypeRow.addresstypeid]] */
  id: AddresstypeId,
  /** Points to [[person.addresstype.AddresstypeRow.addresstypeid]] */
  addresstypeid: AddresstypeId,
  /** Points to [[person.addresstype.AddresstypeRow.name]] */
  name: Name,
  /** Points to [[person.addresstype.AddresstypeRow.rowguid]] */
  rowguid: TypoUUID,
  /** Points to [[person.addresstype.AddresstypeRow.modifieddate]] */
  modifieddate: TypoLocalDateTime
)

object AtViewRow {
  implicit lazy val decoder: Decoder[AtViewRow] = Decoder.forProduct5[AtViewRow, AddresstypeId, AddresstypeId, Name, TypoUUID, TypoLocalDateTime]("id", "addresstypeid", "name", "rowguid", "modifieddate")(AtViewRow.apply)(AddresstypeId.decoder, AddresstypeId.decoder, Name.decoder, TypoUUID.decoder, TypoLocalDateTime.decoder)
  implicit lazy val encoder: Encoder[AtViewRow] = Encoder.forProduct5[AtViewRow, AddresstypeId, AddresstypeId, Name, TypoUUID, TypoLocalDateTime]("id", "addresstypeid", "name", "rowguid", "modifieddate")(x => (x.id, x.addresstypeid, x.name, x.rowguid, x.modifieddate))(AddresstypeId.encoder, AddresstypeId.encoder, Name.encoder, TypoUUID.encoder, TypoLocalDateTime.encoder)
  implicit lazy val read: Read[AtViewRow] = new Read.CompositeOfInstances(Array(
    new Read.Single(AddresstypeId.get).asInstanceOf[Read[Any]],
      new Read.Single(AddresstypeId.get).asInstanceOf[Read[Any]],
      new Read.Single(Name.get).asInstanceOf[Read[Any]],
      new Read.Single(TypoUUID.get).asInstanceOf[Read[Any]],
      new Read.Single(TypoLocalDateTime.get).asInstanceOf[Read[Any]]
  ))(using scala.reflect.ClassTag.Any).map { arr =>
    AtViewRow(
      id = arr(0).asInstanceOf[AddresstypeId],
          addresstypeid = arr(1).asInstanceOf[AddresstypeId],
          name = arr(2).asInstanceOf[Name],
          rowguid = arr(3).asInstanceOf[TypoUUID],
          modifieddate = arr(4).asInstanceOf[TypoLocalDateTime]
    )
  }
}

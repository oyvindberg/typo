/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package vstorewithaddresses

import adventureworks.person.businessentity.BusinessentityId
import adventureworks.public.Name
import doobie.util.Read
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.Encoder

/** View: sales.vstorewithaddresses */
case class VstorewithaddressesViewRow(
  /** Points to [[store.StoreRow.businessentityid]] */
  businessentityid: BusinessentityId,
  /** Points to [[store.StoreRow.name]] */
  name: Name,
  /** Points to [[person.addresstype.AddresstypeRow.name]] */
  addresstype: Name,
  /** Points to [[person.address.AddressRow.addressline1]] */
  addressline1: /* max 60 chars */ String,
  /** Points to [[person.address.AddressRow.addressline2]] */
  addressline2: Option[/* max 60 chars */ String],
  /** Points to [[person.address.AddressRow.city]] */
  city: /* max 30 chars */ String,
  /** Points to [[person.stateprovince.StateprovinceRow.name]] */
  stateprovincename: Name,
  /** Points to [[person.address.AddressRow.postalcode]] */
  postalcode: /* max 15 chars */ String,
  /** Points to [[person.countryregion.CountryregionRow.name]] */
  countryregionname: Name
)

object VstorewithaddressesViewRow {
  implicit lazy val decoder: Decoder[VstorewithaddressesViewRow] = Decoder.forProduct9[VstorewithaddressesViewRow, BusinessentityId, Name, Name, /* max 60 chars */ String, Option[/* max 60 chars */ String], /* max 30 chars */ String, Name, /* max 15 chars */ String, Name]("businessentityid", "name", "addresstype", "addressline1", "addressline2", "city", "stateprovincename", "postalcode", "countryregionname")(VstorewithaddressesViewRow.apply)(BusinessentityId.decoder, Name.decoder, Name.decoder, Decoder.decodeString, Decoder.decodeOption(Decoder.decodeString), Decoder.decodeString, Name.decoder, Decoder.decodeString, Name.decoder)
  implicit lazy val encoder: Encoder[VstorewithaddressesViewRow] = Encoder.forProduct9[VstorewithaddressesViewRow, BusinessentityId, Name, Name, /* max 60 chars */ String, Option[/* max 60 chars */ String], /* max 30 chars */ String, Name, /* max 15 chars */ String, Name]("businessentityid", "name", "addresstype", "addressline1", "addressline2", "city", "stateprovincename", "postalcode", "countryregionname")(x => (x.businessentityid, x.name, x.addresstype, x.addressline1, x.addressline2, x.city, x.stateprovincename, x.postalcode, x.countryregionname))(BusinessentityId.encoder, Name.encoder, Name.encoder, Encoder.encodeString, Encoder.encodeOption(Encoder.encodeString), Encoder.encodeString, Name.encoder, Encoder.encodeString, Name.encoder)
  implicit lazy val read: Read[VstorewithaddressesViewRow] = new Read.CompositeOfInstances(Array(
    new Read.Single(BusinessentityId.get).asInstanceOf[Read[Any]],
      new Read.Single(Name.get).asInstanceOf[Read[Any]],
      new Read.Single(Name.get).asInstanceOf[Read[Any]],
      new Read.Single(Meta.StringMeta.get).asInstanceOf[Read[Any]],
      new Read.SingleOpt(Meta.StringMeta.get).asInstanceOf[Read[Any]],
      new Read.Single(Meta.StringMeta.get).asInstanceOf[Read[Any]],
      new Read.Single(Name.get).asInstanceOf[Read[Any]],
      new Read.Single(Meta.StringMeta.get).asInstanceOf[Read[Any]],
      new Read.Single(Name.get).asInstanceOf[Read[Any]]
  ))(using scala.reflect.ClassTag.Any).map { arr =>
    VstorewithaddressesViewRow(
      businessentityid = arr(0).asInstanceOf[BusinessentityId],
          name = arr(1).asInstanceOf[Name],
          addresstype = arr(2).asInstanceOf[Name],
          addressline1 = arr(3).asInstanceOf[/* max 60 chars */ String],
          addressline2 = arr(4).asInstanceOf[Option[/* max 60 chars */ String]],
          city = arr(5).asInstanceOf[/* max 30 chars */ String],
          stateprovincename = arr(6).asInstanceOf[Name],
          postalcode = arr(7).asInstanceOf[/* max 15 chars */ String],
          countryregionname = arr(8).asInstanceOf[Name]
    )
  }
}

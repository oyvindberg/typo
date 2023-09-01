package adventureworks.person

import adventureworks.person.address.*
import adventureworks.person.addresstype.*
import adventureworks.person.businessentityaddress.*
import adventureworks.person.person.*
import adventureworks.public.Name
import cats.syntax.applicative.*
import cats.syntax.traverse.*
import doobie.ConnectionIO
import doobie.free.connection.WeakAsyncConnectionIO // necessary for 2.12

case class PersonWithAddresses(person: PersonRow, addresses: Map[Name, AddressRow])

case class PersonWithAddressesRepo(
    personRepo: PersonRepo,
    businessentityAddressRepo: BusinessentityaddressRepo,
    addresstypeRepo: AddresstypeRepo,
    addressRepo: AddressRepo
) {

  /** A person can have a bunch of addresses registered, and they each have their own type.
    *
    * This method syncs [[PersonWithAddresses#addresses]] to postgres, so that old attached addresses are removed,
    *
    * and the given addresses are attached with the chosen type
    */
  def upsert(pa: PersonWithAddresses): ConnectionIO[List[BusinessentityaddressRow]] = {
    for {
      // update person
      _ <- personRepo.upsert(pa.person)
      // update stored addresses
      _ <- pa.addresses.values.toList.traverse { address => addressRepo.update(address) }
      // addresses are stored in `PersonWithAddress` by a `Name` which means what type of address it is.
      // this address type is stored in addresstypeRepo.
      // In order for foreign keys to align, we need to translate from names to ids, and create rows as necessary
      oldStoredAddressTypes <- addresstypeRepo.select.where(r => r.name in pa.addresses.keys.toArray).toList
      currentAddressesWithAddresstype <- pa.addresses.toList.traverse { case (addressTypeName, wanted) =>
        oldStoredAddressTypes.find(_.name == addressTypeName) match {
          case Some(found) => (found.addresstypeid, wanted).pure[ConnectionIO]
          case None        => addresstypeRepo.insert(AddresstypeRowUnsaved(name = addressTypeName)).map(row => (row.addresstypeid, wanted))
        }
      }
      currentAddressesByAddresstype = currentAddressesWithAddresstype.toMap
      // discover existing addresses attached to person
      oldAttachedAddresses <- businessentityAddressRepo.select.where(x => x.businessentityid === pa.person.businessentityid).toList
      // unattach old attached rows
      _ <- oldAttachedAddresses.traverse { ba =>
        currentAddressesByAddresstype.get(ba.addresstypeid) match {
          case Some(address) if address.addressid == ba.addressid => false.pure[ConnectionIO]
          case _                                                  => businessentityAddressRepo.delete(ba.compositeId)
        }
      }
      currentAttachedAddresses <- currentAddressesWithAddresstype.traverse { case (addresstypeId, address) =>
        oldAttachedAddresses.find(x => x.addressid == address.addressid && x.addresstypeid == addresstypeId) match {
          case Some(bea) => bea.pure[ConnectionIO]
          case None =>
            businessentityAddressRepo.insert(
              BusinessentityaddressRowUnsaved(pa.person.businessentityid, address.addressid, addresstypeId)
            )
        }
      }
    } yield currentAttachedAddresses
  }
}

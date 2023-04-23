package adventureworks.person.businessentityaddress

import adventureworks.person.address.{AddressRepoImpl, AddressRowUnsaved}
import adventureworks.person.addresstype.{AddresstypeRepoImpl, AddresstypeRowUnsaved}
import adventureworks.person.businessentity.{BusinessentityRepoImpl, BusinessentityRow, BusinessentityRowUnsaved}
import adventureworks.person.countryregion.{CountryregionId, CountryregionRepoImpl, CountryregionRowUnsaved}
import adventureworks.person.stateprovince.{StateprovinceRepoImpl, StateprovinceRowUnsaved}
import adventureworks.public.Name
import adventureworks.sales.salesterritory.{SalesterritoryRepoImpl, SalesterritoryRowUnsaved}
import adventureworks.{Defaulted, withConnection}
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.funsuite.AnyFunSuite

import java.sql.{Connection, DriverManager}
import java.util.UUID

class BusinessentityaddressTest extends AnyFunSuite with TypeCheckedTripleEquals {
  val repo = BusinessentityaddressRepoImpl

  test("works") {
    withConnection { implicit c =>
      // setup
      val businessentityRow: BusinessentityRow =
        BusinessentityRepoImpl.insert(
          BusinessentityRowUnsaved()
        )

      val countryregion =
        CountryregionRepoImpl.insert(
          CountryregionRowUnsaved(
            countryregioncode = CountryregionId("max"),
            name = Name("max")
          )
        )
      val salesTerritory = SalesterritoryRepoImpl.insert(
        SalesterritoryRowUnsaved(
          name = Name("name"),
          countryregioncode = countryregion.countryregioncode,
          group = "flaff",
          salesytd = Defaulted.Provided(1)
        )
      )
      val stateProvidence = StateprovinceRepoImpl.insert(
        StateprovinceRowUnsaved(
          stateprovincecode = "cde",
          countryregioncode = countryregion.countryregioncode,
          name = Name("name"),
          territoryid = salesTerritory.territoryid
        )
      )
      val address = AddressRepoImpl.insert(
        AddressRowUnsaved(
          addressline1 = "addressline1",
          addressline2 = Some("addressline2"),
          city = "city",
          stateprovinceid = stateProvidence.stateprovinceid,
          postalcode = "postalcode",
          spatiallocation = None
        )
      )
      val addressType = AddresstypeRepoImpl.insert(
        new AddresstypeRowUnsaved(
          name = Name("name")
        )
      )
      val unsaved1 = BusinessentityaddressRowUnsaved(
        businessentityid = businessentityRow.businessentityid,
        addressid = address.addressid,
        addresstypeid = addressType.addresstypeid,
        rowguid = Defaulted.Provided(UUID.randomUUID()),
        modifieddate = Defaulted.Provided(java.time.LocalDateTime.now.withNano(0))
      )

      // insert and round trip check
      val saved1 = repo.insert(unsaved1)
      val saved2 = unsaved1.toRow(???, ???)
      assert(saved1 === saved2)

      // check field values
      val newModifiedDate = saved1.modifieddate.minusDays(1)
      repo.updateFieldValues(saved1.compositeId, List(BusinessentityaddressFieldValue.modifieddate(newModifiedDate)))
      val List(saved3) = repo.selectAll: @unchecked
      assert(saved3.modifieddate == newModifiedDate)

      // delete
      repo.delete(saved1.compositeId)

      val List() = repo.selectAll: @unchecked

      succeed
    }
  }
}

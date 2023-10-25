package adventureworks.person.businessentityaddress

import adventureworks.customtypes.{Defaulted, TypoLocalDateTime, TypoUUID}
import adventureworks.person.address.{AddressRepoImpl, AddressRowUnsaved}
import adventureworks.person.addresstype.{AddresstypeRepoImpl, AddresstypeRowUnsaved}
import adventureworks.person.businessentity.{BusinessentityRepoImpl, BusinessentityRowUnsaved}
import adventureworks.person.countryregion.{CountryregionId, CountryregionRepoImpl, CountryregionRowUnsaved}
import adventureworks.person.stateprovince.{StateprovinceId, StateprovinceRepoImpl, StateprovinceRowUnsaved}
import adventureworks.public.Name
import adventureworks.sales.salesterritory.{SalesterritoryRepoImpl, SalesterritoryRowUnsaved}
import adventureworks.withConnection
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.funsuite.AnyFunSuite
import zio.ZIO
import zio.json.{DecoderOps, EncoderOps}

class BusinessentityaddressTest extends AnyFunSuite with TypeCheckedTripleEquals {
  private val repo = BusinessentityaddressRepoImpl

  test("json") {
    val initial = AddressRowUnsaved(
      addressline1 = "addressline1",
      addressline2 = Some("addressline2"),
      city = "city",
      stateprovinceid = StateprovinceId(1),
      postalcode = "postalcode",
      spatiallocation = None
    )
    initial.toJson.fromJson[AddressRowUnsaved] match {
      case Right(roundtripped) => assert(roundtripped === initial)
      case Left(error)         => fail(error)
    }

  }
  test("works") {
    withConnection {
      for {
        // setup
        businessentityInserted <- BusinessentityRepoImpl.insert(BusinessentityRowUnsaved())
        _ <- ZIO.succeed(assert(businessentityInserted.rowsUpdated == 1L))
        businessentityRow = businessentityInserted.updatedKeys.head
        countryregionInserted <- CountryregionRepoImpl.insert(
          CountryregionRowUnsaved(
            countryregioncode = CountryregionId("max"),
            name = Name("max")
          )
        )
        _ <- ZIO.succeed(assert(countryregionInserted.rowsUpdated == 1L))
        countryregion = countryregionInserted.updatedKeys.head
        salesTerritoryInserted <- SalesterritoryRepoImpl.insert(
          SalesterritoryRowUnsaved(
            name = Name("name"),
            countryregioncode = countryregion.countryregioncode,
            group = "flaff",
            salesytd = Defaulted.Provided(1)
          )
        )
        _ <- ZIO.succeed(assert(salesTerritoryInserted.rowsUpdated == 1L))
        salesTerritory = salesTerritoryInserted.updatedKeys.head
        stateProvidenceInserted <- StateprovinceRepoImpl.insert(
          StateprovinceRowUnsaved(
            stateprovincecode = "cde",
            countryregioncode = countryregion.countryregioncode,
            name = Name("name"),
            territoryid = salesTerritory.territoryid
          )
        )
        _ <- ZIO.succeed(assert(stateProvidenceInserted.rowsUpdated == 1L))
        stateProvidence = stateProvidenceInserted.updatedKeys.head
        addressInserted <- AddressRepoImpl.insert(
          AddressRowUnsaved(
            addressline1 = "addressline1",
            addressline2 = Some("addressline2"),
            city = "city",
            stateprovinceid = stateProvidence.stateprovinceid,
            postalcode = "postalcode",
            spatiallocation = None
          )
        )
        _ <- ZIO.succeed(assert(addressInserted.rowsUpdated == 1L))
        address = addressInserted.updatedKeys.head
        addressTypeInserted <- AddresstypeRepoImpl.insert(
          AddresstypeRowUnsaved(
            name = Name("name")
          )
        )
        _ <- ZIO.succeed(assert(addressTypeInserted.rowsUpdated == 1L))
        addressType = addressTypeInserted.updatedKeys.head
        unsaved1 = BusinessentityaddressRowUnsaved(
          businessentityid = businessentityRow.businessentityid,
          addressid = address.addressid,
          addresstypeid = addressType.addresstypeid,
          rowguid = Defaulted.Provided(TypoUUID.randomUUID),
          modifieddate = Defaulted.Provided(TypoLocalDateTime.now)
        )
        // insert and round trip check
        inserted <- repo.insert(unsaved1)
        _ <- ZIO.succeed(assert(inserted.rowsUpdated == 1L))
        saved1 = inserted.updatedKeys.head
        saved2 = unsaved1.toRow(???, ???)
        _ <- ZIO.succeed(assert(saved1 === saved2))
        // check field values
        newModifiedDate = TypoLocalDateTime(saved1.modifieddate.value.minusDays(1))
        _ <- repo.update(saved1.copy(modifieddate = newModifiedDate))
        saved3 <- repo.selectAll.runCollect.map(_.toList).map {
          case List(x) => x
          case other   => throw new MatchError(other)
        }
        _ <- ZIO.succeed(assert(saved3.modifieddate == newModifiedDate))
        // delete
        _ <- repo.delete(saved1.compositeId)
        _ <- repo.selectAll.runCollect.map(_.toList).map {
          case Nil   => ()
          case other => throw new MatchError(other)
        }
      } yield succeed
    }
  }
}

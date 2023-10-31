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
        businessentityRow <- BusinessentityRepoImpl.insert(BusinessentityRowUnsaved())
        countryregion <- CountryregionRepoImpl.insert(
          CountryregionRowUnsaved(
            countryregioncode = CountryregionId("max"),
            name = Name("max")
          )
        )
        salesTerritory <- SalesterritoryRepoImpl.insert(
          SalesterritoryRowUnsaved(
            name = Name("name"),
            countryregioncode = countryregion.countryregioncode,
            group = "flaff",
            salesytd = Defaulted.Provided(1)
          )
        )
        stateProvidence <- StateprovinceRepoImpl.insert(
          StateprovinceRowUnsaved(
            stateprovincecode = "cde",
            countryregioncode = countryregion.countryregioncode,
            name = Name("name"),
            territoryid = salesTerritory.territoryid
          )
        )
        address <- AddressRepoImpl.insert(
          AddressRowUnsaved(
            addressline1 = "addressline1",
            addressline2 = Some("addressline2"),
            city = "city",
            stateprovinceid = stateProvidence.stateprovinceid,
            postalcode = "postalcode",
            spatiallocation = None
          )
        )
        addressType <- AddresstypeRepoImpl.insert(
          AddresstypeRowUnsaved(
            name = Name("name")
          )
        )
        unsaved1 = BusinessentityaddressRowUnsaved(
          businessentityid = businessentityRow.businessentityid,
          addressid = address.addressid,
          addresstypeid = addressType.addresstypeid,
          rowguid = Defaulted.Provided(TypoUUID.randomUUID),
          modifieddate = Defaulted.Provided(TypoLocalDateTime.now)
        )
        // insert and round trip check
        saved1 <- repo.insert(unsaved1)
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

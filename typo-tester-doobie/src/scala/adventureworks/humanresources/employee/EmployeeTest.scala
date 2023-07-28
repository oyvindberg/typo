package adventureworks.humanresources.employee

import adventureworks.person.businessentity.{BusinessentityId, BusinessentityRepoImpl, BusinessentityRowUnsaved}
import adventureworks.person.person.{PersonRepoImpl, PersonRowUnsaved}
import adventureworks.public.{Flag, Name}
import adventureworks.{Defaulted, TypoXml, withConnection}
import doobie.free.connection.delay
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.funsuite.AnyFunSuite

import java.time.LocalDate
import java.util.UUID

class EmployeeTest extends AnyFunSuite with TypeCheckedTripleEquals {
  val repo = EmployeeRepoImpl

  test("json") {
    val initial = PersonRowUnsaved(
      businessentityid = BusinessentityId(1),
      persontype = "SC",
      title = None,
      firstname = Name("firstname"),
      middlename = Some(Name("middlename")),
      lastname = Name("lastname"),
      suffix = Some("suffix"),
      additionalcontactinfo = Some(TypoXml("<additionalcontactinfo/>")),
      demographics = None,
      namestyle = Defaulted.UseDefault,
      emailpromotion = Defaulted.UseDefault,
      rowguid = Defaulted.UseDefault,
      modifieddate = Defaulted.UseDefault
    )

    // the xml structure doesn't have a stable equals method, so we need to use the json representation
    import io.circe.syntax.*
    val initialJson = initial.asJson
    initialJson.as[PersonRowUnsaved] match {
      case Right(roundtripped) =>
        val roundtrippedAsJson = roundtripped.asJson
        assert(roundtrippedAsJson === initialJson)
      case Left(error) => fail(error)
    }
  }

  test("works") {
    withConnection {
      for {
        businessentityRow <- BusinessentityRepoImpl.insert(
          BusinessentityRowUnsaved(
            businessentityid = Defaulted.UseDefault,
            rowguid = Defaulted.UseDefault,
            modifieddate = Defaulted.UseDefault
          )
        )
        personRowUnsaved = PersonRowUnsaved(
          businessentityid = businessentityRow.businessentityid,
          persontype = "SC",
          title = None,
          firstname = Name("firstname"),
          middlename = Some(Name("middlename")),
          lastname = Name("lastname"),
          suffix = Some("suffix"),
          additionalcontactinfo = Some(TypoXml("<additionalcontactinfo/>")),
          demographics = None,
          namestyle = Defaulted.UseDefault,
          emailpromotion = Defaulted.UseDefault,
          rowguid = Defaulted.UseDefault,
          modifieddate = Defaulted.UseDefault
        )
        personRow <- PersonRepoImpl.insert(personRowUnsaved)
        // setup
        unsaved = EmployeeRowUnsaved(
          businessentityid = personRow.businessentityid,
          nationalidnumber = "9912312312",
          loginid = "loginid",
          jobtitle = "jobtitle",
          birthdate = LocalDate.of(1950, 1, 1),
          maritalstatus = "M",
          gender = "F",
          hiredate = LocalDate.now().minusYears(1),
          salariedflag = Defaulted.Provided(Flag(true)),
          vacationhours = Defaulted.Provided(1),
          sickleavehours = Defaulted.Provided(2),
          currentflag = Defaulted.Provided(Flag(true)),
          rowguid = Defaulted.Provided(UUID.randomUUID()),
          modifieddate = Defaulted.Provided(java.time.LocalDateTime.now.withNano(0)),
          organizationnode = Defaulted.Provided(Some("/"))
        )
        // insert and round trip check
        saved1 <- repo.insert(unsaved)
        saved2 = unsaved.toRow(???, ???, ???, ???, ???, ???, ???)
        _ <- delay(assert(saved1 === saved2))
        // check field values
        _ <- repo.updateFieldValues(saved1.businessentityid, List(EmployeeFieldValue.gender("M")))
        saved3_1 <- repo.selectAll.compile.lastOrError
        saved3_2 <- repo.selectByIds(Array(saved1.businessentityid, BusinessentityId(22))).compile.lastOrError
        _ <- delay(assert(saved3_1 === saved3_2))
        _ <- delay(assert(saved3_2.gender == "M"))
        // delete
        _ <- repo.delete(saved1.businessentityid)
        _ <- repo.selectAll.compile.toList.map {
          case Nil      => ()
          case nonEmpty => throw new MatchError(nonEmpty)
        }
        employeeRowUnsaved = EmployeeRowUnsaved(
          businessentityid = personRow.businessentityid,
          nationalidnumber = "9912312312",
          loginid = "loginid",
          jobtitle = "jobtitle",
          birthdate = LocalDate.of(1950, 1, 1),
          maritalstatus = "M",
          gender = "F",
          hiredate = LocalDate.now().minusYears(1),
          salariedflag = Defaulted.UseDefault,
          vacationhours = Defaulted.UseDefault,
          sickleavehours = Defaulted.UseDefault,
          currentflag = Defaulted.UseDefault,
          rowguid = Defaulted.UseDefault,
          modifieddate = Defaulted.UseDefault,
          organizationnode = Defaulted.UseDefault
        )
        _ <- repo.insert(employeeRowUnsaved).map {
          case EmployeeRow(
                personRow.businessentityid,
                employeeRowUnsaved.nationalidnumber,
                employeeRowUnsaved.loginid,
                employeeRowUnsaved.jobtitle,
                employeeRowUnsaved.birthdate,
                employeeRowUnsaved.maritalstatus,
                employeeRowUnsaved.gender,
                employeeRowUnsaved.hiredate,
                // below: these are assertions for the static default values
                Flag(true),
                0,
                0,
                Flag(true),
                _,
                _,
                Some("/")
              ) =>
            ()
          case other => throw new MatchError(other)
        }
      } yield succeed
    }
  }
}

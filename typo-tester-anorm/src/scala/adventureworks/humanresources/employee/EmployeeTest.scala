package adventureworks.humanresources.employee

import adventureworks.person.businessentity.{BusinessentityId, BusinessentityRepoImpl, BusinessentityRow, BusinessentityRowUnsaved}
import adventureworks.person.person.{PersonRepoImpl, PersonRowUnsaved}
import adventureworks.public.{Flag, Name}
import adventureworks.{Defaulted, withConnection}
import org.postgresql.jdbc.PgSQLXML
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.funsuite.AnyFunSuite

import java.sql.{Connection, DriverManager}
import java.time.LocalDate
import java.util.UUID

class EmployeeTest extends AnyFunSuite with TypeCheckedTripleEquals {
  val repo = EmployeeRepoImpl

  test("works") {
    withConnection { implicit c =>
      val businessentityRow: BusinessentityRow =
        BusinessentityRepoImpl.insert(
          BusinessentityRowUnsaved(
            businessentityid = Defaulted.UseDefault,
            rowguid = Defaulted.UseDefault,
            modifieddate = Defaulted.UseDefault
          )
        )

      val personRow = PersonRepoImpl.insert(
        PersonRowUnsaved(
          businessentityid = businessentityRow.businessentityid,
          persontype = "SC",
          title = None,
          firstname = Name("firstname"),
          middlename = Some(Name("middlename")),
          lastname = Name("lastname"),
          suffix = Some("suffix"),
          additionalcontactinfo = Some(new PgSQLXML(null, "<additionalcontactinfo/>")),
          demographics = None,
          namestyle = Defaulted.UseDefault,
          emailpromotion = Defaulted.UseDefault,
          rowguid = Defaulted.UseDefault,
          modifieddate = Defaulted.UseDefault
        )
      )
      // setup
      val unsaved = EmployeeRowUnsaved(
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
      val saved1 = repo.insert(unsaved)
      val saved2 = unsaved.toRow(???, ???, ???, ???, ???, ???, ???)
      assert(saved1 === saved2)

      // check field values
      repo.updateFieldValues(saved1.businessentityid, List(EmployeeFieldValue.gender("M")))
      val List(saved3) = repo.selectAll: @unchecked
      val List(`saved3`) = repo.selectByIds(Array(saved1.businessentityid, BusinessentityId(22))): @unchecked
      assert(saved3.gender == "M")

      // delete
      repo.delete(saved1.businessentityid)

      val List() = repo.selectAll: @unchecked

      {
        val unsaved = EmployeeRowUnsaved(
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

        // insert and round trip check
        val EmployeeRow(
          personRow.businessentityid,
          unsaved.nationalidnumber,
          unsaved.loginid,
          unsaved.jobtitle,
          unsaved.birthdate,
          unsaved.maritalstatus,
          unsaved.gender,
          unsaved.hiredate,
          // below: these are assertions for the static default values
          Flag(true),
          0,
          0,
          Flag(true),
          _,
          _,
          Some("/")
        ) = repo.insert(unsaved): @unchecked
      }
      succeed
    }
  }
}

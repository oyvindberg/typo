package adventureworks

import adventureworks.customtypes.*
import adventureworks.person_row_join.PersonRowJoinSqlRepoImpl
import adventureworks.userdefined.FirstName
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.funsuite.AnyFunSuite

import scala.util.Random

class RecordTest extends AnyFunSuite with TypeCheckedTripleEquals {
  test("works") {
    withConnection { implicit c =>
      val testInsert = new testInsert(new Random(0))
      val businessentityRow = testInsert.personBusinessentity()
      val personRow = testInsert.personPerson(businessentityRow.businessentityid, FirstName("a"), persontype = "EM")
      testInsert.personEmailaddress(personRow.businessentityid, Some("a@b.c"))
      val employeeRow = testInsert.humanresourcesEmployee(personRow.businessentityid, gender = "M", maritalstatus = "M", hiredate = TypoLocalDate.apply("1997-01-01"))
      testInsert.salesSalesperson(employeeRow.businessentityid)
      PersonRowJoinSqlRepoImpl() foreach println
    }
  }
}

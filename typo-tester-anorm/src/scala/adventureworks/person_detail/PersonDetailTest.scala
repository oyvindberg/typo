package adventureworks.person_detail

import adventureworks.person.businessentity.BusinessentityId
import adventureworks.{TypoLocalDateTime, withConnection}
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.funsuite.AnyFunSuite

class PersonDetailTest extends AnyFunSuite with TypeCheckedTripleEquals {
  test("timestamp works") {
    withConnection { implicit c =>
      PersonDetailSqlRepoImpl(Some(BusinessentityId(1)), Some(TypoLocalDateTime.now))
    }
  }
}

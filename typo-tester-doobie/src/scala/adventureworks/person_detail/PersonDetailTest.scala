package adventureworks.person_detail

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.person.businessentity.BusinessentityId
import adventureworks.withConnection
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.funsuite.AnyFunSuite

class PersonDetailTest extends AnyFunSuite with TypeCheckedTripleEquals {
  test("timestamp works") {
    withConnection {
      PersonDetailSqlRepoImpl(BusinessentityId(1), TypoLocalDateTime.now).compile.toList
    }
  }
}

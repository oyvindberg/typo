package adventureworks.update_person_returning

import adventureworks.{TypoLocalDateTime, withConnection}
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.funsuite.AnyFunSuite

class UpdatePersonReturningSqlRepoTest extends AnyFunSuite with TypeCheckedTripleEquals {
  test("timestamp works") {
    withConnection { implicit c =>
      UpdatePersonReturningSqlRepoImpl("1", TypoLocalDateTime.now)
    }
  }
}

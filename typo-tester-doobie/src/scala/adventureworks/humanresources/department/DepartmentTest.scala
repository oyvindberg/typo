package adventureworks.humanresources.department

import adventureworks.public.Name
import adventureworks.{Defaulted, TypoLocalDateTime, withConnection}
import doobie.free.connection.delay
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.funsuite.AnyFunSuite

class DepartmentTest extends AnyFunSuite with TypeCheckedTripleEquals {
  val repo = DepartmentRepoImpl

  test("works") {
    withConnection {
      // setup
      val unsaved = DepartmentRowUnsaved(
        name = Name("foo"),
        groupname = Name("bar"),
        modifieddate = Defaulted.Provided(TypoLocalDateTime.now)
      )
      for {
        // insert and round trip check
        saved1 <- repo.insert(unsaved)
        saved2 = unsaved.toRow(departmentidDefault = saved1.departmentid, modifieddateDefault = saved1.modifieddate)
        _ <- delay(assert(saved1 === saved2))
        // check field values
        _ <- repo.update(saved1.copy(name = Name("baz")))
        saved3 <- repo.selectAll.compile.lastOrError
        _ <- delay(assert(saved3.name == Name("baz")))
        // delete
        _ <- repo.delete(saved1.departmentid)
        _ <- repo.selectAll.compile.toList.map(x => assert(x === List()))
      } yield succeed
    }
  }

  test("upserts works") {
    withConnection {
      // setup
      val unsaved = DepartmentRowUnsaved(
        name = Name("foo"),
        groupname = Name("bar"),
        modifieddate = Defaulted.Provided(TypoLocalDateTime.now)
      )
      for {
        // insert and round trip check
        saved1 <- repo.insert(unsaved)
        newName = Name("baz")
        saved2 <- repo.upsert(saved1.copy(name = newName))
        _ <- delay(assert(saved2.name === newName))
      } yield succeed
    }
  }
}

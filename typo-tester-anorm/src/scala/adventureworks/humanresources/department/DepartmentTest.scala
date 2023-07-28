package adventureworks.humanresources.department

import adventureworks.public.Name
import adventureworks.{Defaulted, TypoLocalDateTime, withConnection}
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.funsuite.AnyFunSuite

class DepartmentTest extends AnyFunSuite with TypeCheckedTripleEquals {
  val repo = DepartmentRepoImpl

  test("works") {
    withConnection { implicit c =>
      // setup
      val unsaved = DepartmentRowUnsaved(
        name = Name("foo"),
        groupname = Name("bar"),
        modifieddate = Defaulted.Provided(TypoLocalDateTime.now)
      )

      // insert and round trip check
      val saved1 = repo.insert(unsaved)
      val saved2 = unsaved.toRow(departmentidDefault = saved1.departmentid, modifieddateDefault = saved1.modifieddate)
      assert(saved1 === saved2)

      // check field values
      repo.update(saved1.copy(name = Name("baz")))
      val List(saved3) = repo.selectAll: @unchecked
      assert(saved3.name == Name("baz"))

      // delete
      repo.delete(saved1.departmentid)
      val List() = repo.selectAll: @unchecked

      // done
      succeed
    }
  }

  test("upserts works") {
    withConnection { implicit c =>
      // setup
      val unsaved = DepartmentRowUnsaved(
        name = Name("foo"),
        groupname = Name("bar"),
        modifieddate = Defaulted.Provided(TypoLocalDateTime.now)
      )

      // insert and round trip check
      val saved1 = repo.insert(unsaved)
      val newName = Name("baz")
      val saved2 = repo.upsert(saved1.copy(name = newName))
      assert(saved2.name === newName)

      // done
      succeed
    }
  }
}

package adventureworks

import adventureworks.customtypes.*
import adventureworks.public.identity_test.{IdentityTestId, IdentityTestRepoImpl, IdentityTestRow, IdentityTestRowUnsaved}
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.funsuite.AnyFunSuite

import scala.annotation.nowarn

class IdentityTest extends AnyFunSuite with TypeCheckedTripleEquals {
  val repo = new IdentityTestRepoImpl()

  def row(str: String) =
    IdentityTestRowUnsaved(IdentityTestId(str), Defaulted.UseDefault)

  test("works") {
    withConnection { implicit c =>
      val unsaved = row("a")
      val inserted = repo.insert(unsaved)
      val upserted = repo.upsert(inserted)
      assert(inserted === upserted): @nowarn
      assert(repo.insertUnsavedStreaming(Iterator(row("b"), row("c"))) === 2L): @nowarn
      repo.select.orderBy(_.name.asc).toList === List(
        IdentityTestRow(1, 1, IdentityTestId("a")),
        IdentityTestRow(2, 2, IdentityTestId("b")),
        IdentityTestRow(3, 3, IdentityTestId("c"))
      )
    }
  }
}

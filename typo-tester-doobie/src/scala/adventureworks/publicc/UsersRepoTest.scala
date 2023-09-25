package adventureworks.publicc

import adventureworks.customtypes.*
import adventureworks.public.users.*
import adventureworks.withConnection
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.Assertion
import org.scalatest.funsuite.AnyFunSuite

class UsersRepoTest extends AnyFunSuite with TypeCheckedTripleEquals {
  def runTest(usersRepo: UsersRepo): Assertion = {
    withConnection {
      val unsaved = UsersRowUnsaved(
        userId = UsersId(TypoUUID.randomUUID),
        name = "name",
        lastName = Some("last_name"),
        email = TypoUnknownCitext("email@asd.no"),
        password = "password",
        verifiedOn = Some(TypoOffsetDateTime.now),
        createdAt = Defaulted.Provided(TypoOffsetDateTime.now)
      )
      for {
        _ <- usersRepo.insert(unsaved)
        all <- usersRepo.select.where(p => p.userId === unsaved.userId).toList
      } yield {
        assert(unsaved.toRow(???) === all.head)
      }
    }
  }

  test("in-memory") {
    runTest(usersRepo = new UsersRepoMock(_.toRow(TypoOffsetDateTime.now)))
  }

  test("pg") {
    runTest(usersRepo = UsersRepoImpl)
  }
}

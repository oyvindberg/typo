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
        verifiedOn = Some(TypoInstant.now),
        createdAt = Defaulted.Provided(TypoInstant.now)
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
    runTest(usersRepo = new UsersRepoMock(_.toRow(TypoInstant.now)))
  }

  test("pg") {
    runTest(usersRepo = UsersRepoImpl)
  }

  val unsavedManyDefaulted = List.tabulate(100)(idx =>
    UsersRowUnsaved(
      userId = UsersId(TypoUUID.randomUUID),
      name = "name",
      lastName = Some("last_name"),
      email = TypoUnknownCitext(s"email-$idx@asd.no"),
      password = "password",
      verifiedOn = Some(TypoInstant.now),
      createdAt = Defaulted.UseDefault
    )
  )

  test("bulkInsert") {
    val usersRepo = UsersRepoImpl
    val unsaved = unsavedManyDefaulted.map(_.toRow(TypoInstant.now))
    withConnection {
      for {
        _ <- usersRepo.bulkInsert(unsaved)
        retrieved <- usersRepo.selectByIds(unsaved.map(_.userId).toArray).compile.toList
      } yield {
        assert(unsaved === retrieved)
      }
    }
  }
}

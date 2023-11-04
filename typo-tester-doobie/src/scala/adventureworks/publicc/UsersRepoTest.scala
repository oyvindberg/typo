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

  val unsavedManyDefaulted = List.tabulate(20)(idx =>
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

  test("insertMany") {
    val usersRepo = UsersRepoImpl
    val unsaved = unsavedManyDefaulted.map(_.toRow(TypoInstant.now))
    withConnection {
      for {
        inserted <- usersRepo.insertMany(unsaved).compile.toList
        retrieved <- usersRepo.selectByIds(unsaved.map(_.userId).toArray).compile.toList
      } yield {
        assert(inserted === retrieved)
        assert(unsaved === retrieved)
      }
    }
  }

  def testInsertManyUnsaved(inputs: Seq[UsersRowUnsaved]) = {
    val usersRepo = UsersRepoImpl
    withConnection {
      for {
        inserted <- usersRepo.insertManyUnsaved(inputs).compile.toList
        retrieved <- usersRepo.selectByIds(inputs.map(_.userId).toArray).compile.toList
      } yield {
        assert(inserted === retrieved)
        assert(inputs.map(_.toRow(???)) === retrieved)
      }
    }
  }

  test("insertManyUnsavedProvided") {
    val unsavedManyProvided = unsavedManyDefaulted.map(_.copy(createdAt = Defaulted.Provided(TypoInstant.now)))
    testInsertManyUnsaved(unsavedManyProvided)
  }

  test("insertManyUnsavedDefaulted") {
    testInsertManyUnsaved(unsavedManyDefaulted)
  }
}

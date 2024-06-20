package adventureworks

import adventureworks.customtypes.{TypoLocalDateTime, TypoUUID}
import adventureworks.person.businessentity.*
import doobie.free.connection.delay
import io.circe.Json
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.Assertion
import org.scalatest.funsuite.AnyFunSuite
import typo.dsl.pagination.*

import java.time.LocalDateTime

class PaginationTest extends AnyFunSuite with TypeCheckedTripleEquals {
  implicit class ListOps[A](list: List[A]) {
    def dropUntil(f: A => Boolean): List[A] = {
      val iterator = list.iterator
      var continue = true
      var i = 0
      while (continue && iterator.hasNext) {
        val a = iterator.next()
        if (f(a)) continue = false
        i += 1
      }
      list.drop(i)
    }
  }
  def runTest(businessentityRepo: BusinessentityRepo): Assertion = {

    val limit = 10
    val now = LocalDateTime.of(2021, 1, 1, 0, 0)
    val rows = List.range(0, 25).map { i =>
      // ensure some duplicate values
      val time = TypoLocalDateTime(now.minusDays(i % limit.toLong))
      BusinessentityRow(BusinessentityId.apply(i), TypoUUID.randomUUID, time)
    }

    /** mock repositories do not compute prefixed fields. I chose to patch the test rather than fix the implementation because the complexity doen't make sense
      */
    def patch(c: ClientCursor[Json]): ClientCursor[Json] =
      businessentityRepo match {
        case _: BusinessentityRepoMock =>
          c.copy(parts = c.parts.map { case (k, v) => (SortOrderRepr(k.expr.replace("(businessentity0).", "")), v) })
        case _ => c
      }

    // same as sql below
    val rowsDropped = rows.dropRight(1)
    val List(group1, group2, group3) = rowsDropped
      .sortBy(x => (Reverse(x.modifieddate), x.businessentityid.value - 2))
      .grouped(limit)
      .toList: @unchecked

    // paginated query
    import PaginationQueryCirce.*

    val q: PaginationQueryCirce[BusinessentityFields, BusinessentityRow] =
      businessentityRepo.select
        .where(_.businessentityid < rows.last.businessentityid)
        // first ordering
        .seekPaginationOn(_.modifieddate.desc)
        // add a second ordering. supports any sql expression that can be sorted
        .andOn(x => (x.businessentityid.underlying - 2).asc)

    withConnection(
      for {
        // batch insert some rows
        _ <- businessentityRepo.insertStreaming(fs2.Stream.emits(rows))

        // first batch. we receive a cursor to continue from
        t1 <- q.toList(limit = limit, continueFrom = None)
        (rows1, clientCursor1) = t1
        _ <- delay(assert(rows1 == group1))
        _ <- delay(
          assertResult(clientCursor1)(
            Some(
              patch(
                ClientCursor(
                  Map(
                    SortOrderRepr("(businessentity0).modifieddate") -> Json.fromString("2020-12-29T00:00:00"),
                    SortOrderRepr("((businessentity0).businessentityid - ? ) :2") -> Json.fromInt(1)
                  )
                )
              )
            )
          )
        )

        // second batch. we receive a cursor to continue from
        t2 <- q.toList(limit = limit, continueFrom = clientCursor1)
        (rows2, clientCursor2) = t2
        _ <- delay(assert(rows2 == group2))
        _ <- delay(
          assertResult(clientCursor2)(
            Some(
              patch(
                ClientCursor(
                  Map(
                    SortOrderRepr("(businessentity0).modifieddate") -> Json.fromString("2020-12-25T00:00:00"),
                    SortOrderRepr("((businessentity0).businessentityid - ? ) :2") -> Json.fromInt(15)
                  )
                )
              )
            )
          )
        )
        // third and last batch
        t3 <- q.toList(limit = limit, continueFrom = clientCursor2)
        (rows3, clientCursor3) = t3
        _ <- delay(assert(rows3 == group3))
        _ <- delay(assertResult(clientCursor3)(None))

        // reuse `clientCursor1` for a similar but not equal query. both `order by`s are ascending, which enables more efficient query
        q2: PaginationQueryCirce[BusinessentityFields, BusinessentityRow] =
          businessentityRepo.select
            .where(_.businessentityid < rows.last.businessentityid)
            // first ordering
            .seekPaginationOn(_.modifieddate.asc)
            // add a second ordering. supports any sql expression that can be sorted
            .andOn(x => (x.businessentityid.underlying - 2).asc)
        t4 <- q2.toList(limit = limit, continueFrom = clientCursor1)
        (rows4, _) = t4
        expectedRows4 = rowsDropped.sortBy(x => (x.modifieddate, x.businessentityid.value - 2)).dropUntil(_ == rows1.last).take(limit)
        _ <- delay(assert(rows4 == expectedRows4))

      } yield succeed
    )
  }

  test("in-memory") {
    runTest(new BusinessentityRepoMock(_.toRow(???, ???, ???)))
  }

  test("pg") {
    runTest(new BusinessentityRepoImpl)
  }
}

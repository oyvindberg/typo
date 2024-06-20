package adventureworks

import adventureworks.customtypes.{TypoLocalDateTime, TypoUUID}
import adventureworks.person.businessentity.*
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.Assertion
import org.scalatest.funsuite.AnyFunSuite
import typo.dsl.pagination.*
import zio.json.ast.Json
import zio.stream.ZStream
import zio.{Chunk, ZIO}

import java.time.LocalDateTime

class PaginationTest extends AnyFunSuite with TypeCheckedTripleEquals {
  def runTest(businessentityRepo: BusinessentityRepo): Assertion = {

    val limit = 10
    val now = LocalDateTime.of(2021, 1, 1, 0, 0)
    val rows = Chunk.range(0, 25).map { i =>
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
    import PaginationQueryZioJson.*

    val q: PaginationQueryZioJson[BusinessentityFields, BusinessentityRow] =
      businessentityRepo.select
        .where(_.businessentityid < rows.last.businessentityid)
        // first ordering
        .seekPaginationOn(_.modifieddate.desc)
        // add a second ordering. supports any sql expression that can be sorted
        .andOn(x => (x.businessentityid.underlying - 2).asc)

    withConnection(
      for {
        // batch insert some rows
        _ <- businessentityRepo.insertStreaming(ZStream.fromChunk(rows))

        // first batch. we receive a cursor to continue from
        t1 <- q.toChunk(limit = limit, continueFrom = None)
        (rows1, clientCursor1) = t1
        _ <- ZIO.attempt(assert(rows1 == group1))
        _ <- ZIO.attempt(
          assertResult(clientCursor1)(
            Some(
              patch(
                ClientCursor(
                  Map(
                    SortOrderRepr("(businessentity0).modifieddate") -> new Json.Str("2020-12-29T00:00:00"),
                    SortOrderRepr("((businessentity0).businessentityid - 2::int4)") -> new Json.Num(java.math.BigDecimal.valueOf(1))
                  )
                )
              )
            )
          )
        )
        // second batch. we receive a cursor to continue from
        t2 <- q.toChunk(limit = limit, continueFrom = clientCursor1)
        (rows2, clientCursor2) = t2
        _ <- ZIO.attempt(assert(rows2 == group2))
        _ <- ZIO.attempt(
          assertResult(clientCursor2)(
            Some(
              patch(
                ClientCursor(
                  Map(
                    SortOrderRepr("(businessentity0).modifieddate") -> new Json.Str("2020-12-25T00:00:00"),
                    SortOrderRepr("((businessentity0).businessentityid - 2::int4)") -> new Json.Num(java.math.BigDecimal.valueOf(15))
                  )
                )
              )
            )
          )
        )
        // third and last batch
        t3 <- q.toChunk(limit = limit, continueFrom = clientCursor2)
        (rows3, clientCursor3) = t3
        _ <- ZIO.attempt(assert(rows3 == group3))
        _ <- ZIO.attempt(assertResult(clientCursor3)(None))

        // reuse `clientCursor1` for a similar but not equal query. both `order by`s are ascending, which enables more efficient query
        q2: PaginationQueryZioJson[BusinessentityFields, BusinessentityRow] =
          businessentityRepo.select
            .where(_.businessentityid < rows.last.businessentityid)
            // first ordering
            .seekPaginationOn(_.modifieddate.asc)
            // add a second ordering. supports any sql expression that can be sorted
            .andOn(x => (x.businessentityid.underlying - 2).asc)
        t4 <- q2.toChunk(limit = limit, continueFrom = clientCursor1)
        (rows4, _) = t4
        expectedRows4 = rowsDropped.sortBy(x => (x.modifieddate, x.businessentityid.value - 2)).dropUntil(_ == rows1.last).take(limit)
        _ <- ZIO.attempt(assert(rows4 == expectedRows4))

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

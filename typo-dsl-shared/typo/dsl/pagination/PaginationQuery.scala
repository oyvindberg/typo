package typo.dsl.pagination

import typo.dsl.*

import java.util.concurrent.atomic.AtomicInteger

/** You'll typically use this behind a facade which accounts for your json library of choice.
  *
  * If you don't, you'll just have to fill in [[AbstractJsonCodec]] yourself.
  */
class PaginationQuery[Fields[_], Row, E](
    query: SelectBuilder[Fields, Row],
    part1s: List[ServerCursor.Part1NoHkt[Fields, Row, ?, E]]
) {
  def andOn[T, N[_]](v: Fields[Row] => SortOrder[T, N, Row])(codec: AbstractJsonCodec[N[T], E])(implicit asConst: SqlExpr.Const.As[T, N, Row]): PaginationQuery[Fields, Row, E] =
    new PaginationQuery(query, part1s :+ new ServerCursor.Part1(v, codec, asConst))

  def done(limit: Int, continueFrom: Option[ClientCursor[E]]): Either[String, (SelectBuilder[Fields, Row], ServerCursor[Fields, Row, E])] = {
    val initialCursor = {
      // hack: improve DSL to avoid  instantiating structure twice
      val structure: Structure[Fields, Row] = query match {
        case mock: SelectBuilderMock[Fields, Row] => mock.structure
        case sql: SelectBuilderSql[Fields, Row]   => sql.instantiate(new AtomicInteger(0)).structure
        case _                                    => sys.error("unsupported query type")
      }
      new ServerCursor.Initial(part1s.map(_.asPart2(structure.fields)), limit)
    }

    continueFrom match {
      case None =>
        val newQuery = initialCursor.part2s
          .foldLeft(query) { case (q, part2: ServerCursor.Part2[Fields, Row, t, n, E] @unchecked /* for 2.13 */ ) =>
            q.orderBy(part2.part1.v.asInstanceOf[Fields[Hidden] => SortOrder[t, n, Hidden]])
          }
          .limit(limit)
        Right((newQuery, initialCursor))

      case Some(clientCursor) =>
        initialCursor.withTupleFrom(clientCursor).map { cursor =>
          val newQuery = cursor.part3s
            .foldLeft(query) { case (q, part3: ServerCursor.Part3[Fields, Row, _, _, E] @unchecked /* for 2.13 */ ) =>
              q.seek(part3.part2.part1.v)(part3.value)
            }
            .limit(limit)
          (newQuery, cursor)
        }
    }
  }
}

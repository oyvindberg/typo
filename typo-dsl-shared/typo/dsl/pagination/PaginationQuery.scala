package typo.dsl.pagination

import typo.dsl.*

/** You'll typically use this behind a facade which accounts for your json library of choice.
  *
  * If you don't, you'll just have to fill in [[AbstractJsonCodec]] yourself.
  */
class PaginationQuery[Fields, Row, E](
    query: SelectBuilder[Fields, Row],
    parts: List[ServerCursor.PartNoHkt[Fields, Row, ?, E]]
) {

  def andOn[T, N[_]](v: Fields => SortOrder[T, N])(codec: AbstractJsonCodec[N[T], E])(implicit asConst: SqlExpr.Const.As[T, N]): PaginationQuery[Fields, Row, E] = {
    val sortOrder = v(query.structure.fields)
    val sortOrderRepr = SortOrderRepr.from(sortOrder, query.renderCtx)
    val newPart = new ServerCursor.Part[Fields, Row, T, N, E](v, sortOrder, sortOrderRepr, codec, asConst)
    new PaginationQuery(query, parts :+ newPart)
  }

  def done(limit: Int, continueFrom: Option[ClientCursor[E]]): Either[String, (SelectBuilder[Fields, Row], ServerCursor[Fields, Row, E])] = {
    val initialCursor = new ServerCursor.Initial(query.structure, parts, limit)

    continueFrom match {
      case None =>
        val newQuery = initialCursor.parts
          .foldLeft(query) { case (q, part2: ServerCursor.Part[Fields, Row, t, n, E] @unchecked /* for 2.13 */ ) =>
            q.orderBy(part2.v)
          }
          .limit(limit)
        Right((newQuery, initialCursor))

      case Some(clientCursor) =>
        initialCursor.withTupleFrom(clientCursor).map { cursor =>
          val newQuery = cursor.part3s
            .foldLeft(query) { case (q, partData: ServerCursor.PartWithData[Fields, Row, _, _, E] @unchecked /* for 2.13 */ ) =>
              q.seek(partData.part.v)(partData.value)
            }
            .limit(limit)
          (newQuery, cursor)
        }
    }
  }
}

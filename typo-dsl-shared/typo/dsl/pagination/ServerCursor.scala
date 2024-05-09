package typo.dsl.pagination

import typo.dsl.{SortOrder, SqlExpr}
import typo.dsl.pagination.internal.ListOps

sealed trait ServerCursor[Fields[_], Row, E] {
  def part2s: List[ServerCursor.Part2NoHkt[Fields, Row, ?, E]]
  val limit: Int

  final def withNewResults(rows: Seq[Row]): Option[ServerCursor.AtTuple[Fields, Row, E]] =
    if (rows.length < limit) None // no new cursor if we know we reached end
    else rows.lastOption.map(lastRow => new ServerCursor.AtTuple(part2s.map(_.asPart3(lastRow)), limit))

  final def withTupleFrom(clientCursor: ClientCursor[E]): Either[String, ServerCursor.AtTuple[Fields, Row, E]] =
    part2s.traverse(_.decodeDataFrom(clientCursor)).map(parts => new ServerCursor.AtTuple(parts, limit))
}

object ServerCursor {
  class Initial[Fields[_], Row, E](val part2s: List[Part2NoHkt[Fields, Row, ?, E]], val limit: Int) extends ServerCursor[Fields, Row, E]

  class AtTuple[Fields[_], Row, E](val part3s: List[Part3NoHkt[Fields, Row, ?, E]], val limit: Int) extends ServerCursor[Fields, Row, E] {
    def part2s: List[Part2NoHkt[Fields, Row, ?, E]] = part3s.map(_.part2)
    def clientCursor: ClientCursor[E] = ClientCursor(part3s.map(_.encoded).toMap)
  }

  /** A constituent in a server-side cursor.
    *
    * At this point we have a function from `Fields` to a [[SortOrder]], making it dependant on the *shape* of the query, but not an *instance* of a query.
    *
    * You very likely don't have to worry about this, as it is used internally.
    */
  class Part1[Fields[_], Row, T, N[_], E](
      val v: Fields[Row] => SortOrder[T, N, Row],
      val codec: AbstractJsonCodec[N[T], E],
      val asConst: SqlExpr.Const.As[T, N, Row]
  ) extends Part1NoHkt[Fields, Row, N[T], E] {
    def asPart2(fields: Fields[Row]): ServerCursor.Part2[Fields, Row, T, N, E] =
      new ServerCursor.Part2(this, v(fields))
  }
  sealed trait Part1NoHkt[Fields[_], Row, NT, E] { // for scala 2.13
    def asPart2(fields: Fields[Row]): ServerCursor.Part2NoHkt[Fields, Row, NT, E]
  }

  /** A constituent in a server-side cursor.
    *
    * At this point we have extracted the [[SortOrder]] from a query, making the value somewhat depending on it. That dependency affects the rendering of columns only
    *
    * The benefit is that we can now render it to a [[SortOrderRepr]], so we can encode it in a [[ClientCursor]]
    */
  class Part2[Fields[_], Row, T, N[_], E](val part1: Part1[Fields, Row, T, N, E], val sortOrder: SortOrder[T, N, Row]) extends Part2NoHkt[Fields, Row, N[T], E] {
    val sortOrderRepr: SortOrderRepr = SortOrderRepr.from(sortOrder)

    override def asPart3(row: Row): Part3[Fields, Row, T, N, E] =
      new ServerCursor.Part3[Fields, Row, T, N, E](this, part1.asConst(sortOrder.expr.eval(row)))

    override def decodeDataFrom(clientCursor: ClientCursor[E]): Either[String, Part3[Fields, Row, T, N, E]] =
      clientCursor.parts.get(sortOrderRepr) match {
        case None => Left(s"Cursor is missing value for column '${sortOrderRepr.expr}'")
        case Some(value) =>
          part1.codec.decode(value) match {
            case Left(msg) =>
              Left(s"Cursor had invalid value '$value' for column '${sortOrderRepr.expr}': $msg")
            case Right(nt) =>
              Right(new ServerCursor.Part3[Fields, Row, T, N, E](this, part1.asConst(nt)))
          }
      }
  }
  sealed trait Part2NoHkt[Fields[_], Row, NT, E] { // for scala 2.13
    def asPart3(row: Row): Part3NoHkt[Fields, Row, NT, E]
    def decodeDataFrom(clientCursor: ClientCursor[E]): Either[String, Part3NoHkt[Fields, Row, NT, E]]
  }

  /** A constituent in a server-side cursor.
    *
    * At this point there is also data, essentially a column reference plus a value. This makes it an actual cursor.
    *
    * The data is wrapped in [[SqlExpr.Const]] so it's ready to embed into a query without threading through the type class instances
    */
  class Part3[Fields[_], Row, T, N[_], E](val part2: Part2[Fields, Row, T, N, E], val value: SqlExpr.Const[T, N, Row]) extends Part3NoHkt[Fields, Row, N[T], E] {
    override def encoded: (SortOrderRepr, E) =
      (part2.sortOrderRepr, part2.part1.codec.encode(value.value))
  }

  sealed trait Part3NoHkt[Fields[_], Row, NT, E] { // for scala 2.13
    val part2: Part2NoHkt[Fields, Row, ?, E]
    def encoded: (SortOrderRepr, E)
  }
}

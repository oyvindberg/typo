package typo.dsl.pagination

import typo.dsl.pagination.internal.ListOps
import typo.dsl.{SortOrder, SqlExpr, Structure}

sealed trait ServerCursor[Fields, Row, E] {
  def parts: List[ServerCursor.PartNoHkt[Fields, Row, ?, E]]
  val structure: Structure[Fields, Row]
  val limit: Int

  final def withNewResults(rows: Seq[Row]): Option[ServerCursor.AtTuple[Fields, Row, E]] =
    if (rows.length < limit) None // no new cursor if we know we reached end
    else rows.lastOption.map(lastRow => new ServerCursor.AtTuple(structure, parts.map(_.withData(lastRow, structure)), limit))

  final def withTupleFrom(clientCursor: ClientCursor[E]): Either[String, ServerCursor.AtTuple[Fields, Row, E]] =
    parts.traverse(_.decodeDataFrom(clientCursor)).map(parts => new ServerCursor.AtTuple(structure, parts, limit))
}

object ServerCursor {
  class Initial[Fields, Row, E](val structure: Structure[Fields, Row], val parts: List[PartNoHkt[Fields, Row, ?, E]], val limit: Int) extends ServerCursor[Fields, Row, E]

  class AtTuple[Fields, Row, E](val structure: Structure[Fields, Row], val part3s: List[PartWithDataNoHkt[Fields, Row, ?, E]], val limit: Int) extends ServerCursor[Fields, Row, E] {
    def parts: List[PartNoHkt[Fields, Row, ?, E]] = part3s.map(_.part)
    def clientCursor: ClientCursor[E] = ClientCursor(part3s.map(_.encoded).toMap)
  }

  /** A constituent in a server-side cursor.
    */
  class Part[Fields, Row, T, N[_], E](
      val v: Fields => SortOrder[T, N],
      val sortOrder: SortOrder[T, N],
      val sortOrderRepr: SortOrderRepr,
      val codec: AbstractJsonCodec[N[T], E],
      val asConst: SqlExpr.Const.As[T, N]
  ) extends PartNoHkt[Fields, Row, N[T], E] {
    override def withData(row: Row, structure: Structure[Fields, Row]): PartWithData[Fields, Row, T, N, E] =
      new ServerCursor.PartWithData[Fields, Row, T, N, E](this, asConst(structure.untypedEval(sortOrder.expr, row)))

    override def decodeDataFrom(clientCursor: ClientCursor[E]): Either[String, PartWithData[Fields, Row, T, N, E]] =
      clientCursor.parts.get(sortOrderRepr) match {
        case None => Left(s"Cursor is missing value for column '${sortOrderRepr.expr}'")
        case Some(value) =>
          codec.decode(value) match {
            case Left(msg) =>
              Left(s"Cursor had invalid value '$value' for column '${sortOrderRepr.expr}': $msg")
            case Right(nt) =>
              Right(new ServerCursor.PartWithData[Fields, Row, T, N, E](this, asConst(nt)))
          }
      }
  }

  sealed trait PartNoHkt[Fields, Row, NT, E] { // for scala 2.13
    def withData(row: Row, structure: Structure[Fields, Row]): PartWithDataNoHkt[Fields, Row, NT, E]
    def decodeDataFrom(clientCursor: ClientCursor[E]): Either[String, PartWithDataNoHkt[Fields, Row, NT, E]]
  }

  /** A constituent in a server-side cursor.
    *
    * At this point there is also data, essentially a column reference plus a value. This makes it an actual cursor.
    *
    * The data is wrapped in [[SqlExpr.Const]] so it's ready to embed into a query without threading through the type class instances
    */
  class PartWithData[Fields, Row, T, N[_], E](val part: Part[Fields, Row, T, N, E], val value: SqlExpr.Const[T, N]) extends PartWithDataNoHkt[Fields, Row, N[T], E] {
    override def encoded: (SortOrderRepr, E) =
      (part.sortOrderRepr, part.codec.encode(value.value))
  }

  sealed trait PartWithDataNoHkt[Fields, Row, NT, E] { // for scala 2.13
    val part: PartNoHkt[Fields, Row, ?, E]
    def encoded: (SortOrderRepr, E)
  }
}

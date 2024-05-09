package adventureworks

import play.api.libs.json.*
import typo.dsl.pagination.*
import typo.dsl.{SelectBuilder, SortOrder, SqlExpr}

import java.sql.Connection

class PaginationQueryCirce[Fields[_], Row](underlying: PaginationQuery[Fields, Row, JsValue]) {
  def andOn[T, N[_]](v: Fields[Row] => SortOrder[T, N, Row])(implicit
      e: Writes[N[T]],
      d: Reads[N[T]],
      asConst: SqlExpr.Const.As[T, N, Row]
  ): PaginationQueryCirce[Fields, Row] =
    new PaginationQueryCirce(underlying.andOn(v)(PaginationQueryCirce.abstractCodec)(asConst))

  def done(limit: Int, continueFrom: Option[ClientCursor[JsValue]]): Either[String, (SelectBuilder[Fields, Row], ServerCursor[Fields, Row, JsValue])] =
    underlying.done(limit, continueFrom)

  def toList(limit: Int, continueFrom: Option[ClientCursor[JsValue]])(implicit c: Connection): (List[Row], Option[ClientCursor[JsValue]]) =
    underlying.done(limit, continueFrom) match {
      case Left(msg) =>
        throw new IllegalArgumentException(msg)
      case Right((newQuery, cursor)) =>
        val rows = newQuery.toList
        (rows, cursor.withNewResults(rows).map(_.clientCursor))
    }
}

object PaginationQueryCirce {
  implicit val clientCursorEncoder: Writes[ClientCursor[JsValue]] =
    implicitly[Writes[Map[String, JsValue]]].contramap(_.parts.map { case (k, v) => (k.expr, v) }.toMap)
  implicit val clientCursorDecoder: Reads[ClientCursor[JsValue]] =
    implicitly[Reads[Map[String, JsValue]]].map(parts => ClientCursor(parts.map { case (k, v) => (SortOrderRepr(k), v) }))

  implicit class PaginationQuerySyntax[Fields[_], Row](private val query: SelectBuilder[Fields, Row]) extends AnyVal {
    def seekPaginationOn[T, N[_]](v: Fields[Row] => SortOrder[T, N, Row])(implicit
        e: Writes[N[T]],
        d: Reads[N[T]],
        asConst: SqlExpr.Const.As[T, N, Row]
    ): PaginationQueryCirce[Fields, Row] =
      new PaginationQueryCirce(new PaginationQuery(query, Nil).andOn(v)(PaginationQueryCirce.abstractCodec))
  }

  def abstractCodec[N[_], T](implicit e: Writes[N[T]], d: Reads[N[T]]): AbstractJsonCodec[N[T], JsValue] =
    new AbstractJsonCodec[N[T], JsValue](e.writes, json => d.reads(json).asEither.left.map(_.toString())) // todo: improve error message
}

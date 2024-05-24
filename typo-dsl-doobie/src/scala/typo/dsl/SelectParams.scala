package typo.dsl

import cats.data.NonEmptyList
import doobie.implicits.toSqlInterpolator
import doobie.util.fragment.Fragment
import doobie.util.fragments
import typo.dsl.internal.seeks

final case class SelectParams[Fields, Row](
    where: List[Fields => SqlExpr[Boolean, Option]],
    orderBy: List[Fields => SortOrderNoHkt[?]],
    seeks: List[SelectParams.SeekNoHkt[Fields, ?]],
    offset: Option[Int],
    limit: Option[Int]
) {
  def where(v: Fields => SqlExpr[Boolean, Option]): SelectParams[Fields, Row] = copy(where = where :+ v)
  def orderBy(v: Fields => SortOrderNoHkt[?]): SelectParams[Fields, Row] = copy(orderBy = orderBy :+ v)
  def seek(v: SelectParams.SeekNoHkt[Fields, ?]): SelectParams[Fields, Row] = copy(seeks = seeks :+ v)
  def offset(v: Int): SelectParams[Fields, Row] = copy(offset = Some(v))
  def limit(v: Int): SelectParams[Fields, Row] = copy(limit = Some(v))
}

object SelectParams {
  def empty[Fields, R]: SelectParams[Fields, R] =
    SelectParams[Fields, R](List.empty, List.empty, List.empty, None, None)

  sealed trait SeekNoHkt[Fields, NT] {
    val f: Fields => SortOrderNoHkt[NT]
  }

  case class Seek[Fields, T, N[_]](f: Fields => SortOrder[T, N], value: SqlExpr.Const[T, N]) extends SeekNoHkt[Fields, N[T]]

  def render[Fields, R](fields: Fields, baseSql: Fragment, ctx: RenderCtx, params: SelectParams[Fields, R]): Fragment = {
    val (filters, orderBys) = seeks.expand(fields, params)

    List[Option[Fragment]](
      Some(baseSql),
      NonEmptyList.fromFoldable(filters.map(f => f.render(ctx))).map(fragments.whereAnd(_)),
      NonEmptyList.fromFoldable(orderBys.map(f => f.render(ctx))).map(fragments.orderBy(_)),
      params.offset.map(value => fr"offset $value"),
      params.limit.map(value => fr"limit $value")
    ).flatten.reduce(_ ++ _)
  }
}

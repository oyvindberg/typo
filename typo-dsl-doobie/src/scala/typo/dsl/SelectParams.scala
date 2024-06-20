package typo.dsl

import cats.data.NonEmptyList
import doobie.implicits.toSqlInterpolator
import doobie.util.fragment.Fragment
import doobie.util.fragments

final case class SelectParams[Fields, Row](
    where: List[Fields => SqlExpr[Boolean, Option]],
    orderBy: List[OrderByOrSeek[Fields, ?]],
    offset: Option[Int],
    limit: Option[Int]
) {
  def where(v: Fields => SqlExpr[Boolean, Option]): SelectParams[Fields, Row] = copy(where = where :+ v)
  def orderBy[T, N[_]](f: Fields => SortOrder[T, N]): SelectParams[Fields, Row] = copy(orderBy = orderBy :+ OrderByOrSeek.OrderBy(f))
  def seek[T, N[_]](f: Fields => SortOrder[T, N], value: SqlExpr.Const[T, N]): SelectParams[Fields, Row] = copy(orderBy = orderBy :+ OrderByOrSeek.Seek(f, value))
  def offset(v: Int): SelectParams[Fields, Row] = copy(offset = Some(v))
  def limit(v: Int): SelectParams[Fields, Row] = copy(limit = Some(v))
}

object SelectParams {
  def empty[Fields, R]: SelectParams[Fields, R] =
    SelectParams[Fields, R](List.empty, List.empty, None, None)

  def render[Fields, R](fields: Fields, ctx: RenderCtx, params: SelectParams[Fields, R]): Option[Fragment] = {
    val (filters, orderBys) = OrderByOrSeek.expand(fields, params)

    List[Option[Fragment]](
      NonEmptyList.fromFoldable(filters.map(f => f.render(ctx))).map(fragments.whereAnd(_)),
      NonEmptyList.fromFoldable(orderBys.map(f => f.render(ctx))).map(fragments.orderBy(_)),
      params.offset.map(value => fr"offset $value"),
      params.limit.map(value => fr"limit $value")
    ).flatten.reduceOption(_ ++ _)
  }
}

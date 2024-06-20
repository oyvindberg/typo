package typo.dsl

import zio.NonEmptyChunk
import zio.jdbc.*

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

  def render[Fields, R](fields: Fields, baseSql: SqlFragment, ctx: RenderCtx, params: SelectParams[Fields, R]): SqlFragment = {
    val (filters, orderBys) = OrderByOrSeek.expand(fields, params)
    List[Option[SqlFragment]](
      Some(baseSql),
      NonEmptyChunk.fromIterableOption(filters.map(f => f.render(ctx))).map(fs => fs.mkFragment(" WHERE ", " AND ", "")),
      NonEmptyChunk.fromIterableOption(orderBys.map(f => f.render(ctx))).map(fs => fs.mkFragment(" ORDER BY ", ", ", "")),
      params.offset.map(value => sql" offset $value"),
      params.limit.map(value => sql" limit $value")
    ).flatten.reduce(_ ++ _)
  }
}

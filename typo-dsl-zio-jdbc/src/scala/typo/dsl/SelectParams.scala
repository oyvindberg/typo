package typo.dsl

import typo.dsl.internal.seeks
import zio.NonEmptyChunk
import zio.jdbc.*

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

  def render[Fields, R](fields: Fields, baseSql: SqlFragment, ctx: RenderCtx, params: SelectParams[Fields, R]): SqlFragment = {
    val (filters, orderBys) = seeks.expand(fields, params)
    List[Option[SqlFragment]](
      Some(baseSql),
      NonEmptyChunk.fromIterableOption(filters.map(f => f.render(ctx))).map(fs => fs.mkFragment(" WHERE ", " AND ", "")),
      NonEmptyChunk.fromIterableOption(orderBys.map(f => f.render(ctx))).map(fs => fs.mkFragment(" ORDER BY ", ", ", "")),
      params.offset.map(value => sql" offset $value"),
      params.limit.map(value => sql" limit $value")
    ).flatten.reduce(_ ++ _)
  }
}

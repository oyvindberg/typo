package typo.dsl

import cats.data.NonEmptyList
import doobie.implicits.toSqlInterpolator
import doobie.util.fragment.Fragment
import doobie.util.fragments
import typo.dsl.internal.seeks

import java.util.concurrent.atomic.AtomicInteger

final case class SelectParams[Fields[_], Row](
    where: List[Fields[Row] => SqlExpr[Boolean, Option, Row]],
    orderBy: List[Fields[Row] => SortOrderNoHkt[?, Row]],
    seeks: List[SelectParams.SeekNoHkt[Fields, Row, ?]],
    offset: Option[Int],
    limit: Option[Int]
) {
  def where(v: Fields[Row] => SqlExpr[Boolean, Option, Row]): SelectParams[Fields, Row] = copy(where = where :+ v)
  def orderBy(v: Fields[Row] => SortOrderNoHkt[?, Row]): SelectParams[Fields, Row] = copy(orderBy = orderBy :+ v)
  def seek(v: SelectParams.SeekNoHkt[Fields, Row, ?]): SelectParams[Fields, Row] = copy(seeks = seeks :+ v)
  def offset(v: Int): SelectParams[Fields, Row] = copy(offset = Some(v))
  def limit(v: Int): SelectParams[Fields, Row] = copy(limit = Some(v))
}

object SelectParams {
  def empty[Fields[_], Row]: SelectParams[Fields, Row] =
    SelectParams[Fields, Row](List.empty, List.empty, List.empty, None, None)

  sealed trait SeekNoHkt[Fields[_], Row, NT] {
    val f: Fields[Row] => SortOrderNoHkt[NT, Row]
  }

  case class Seek[Fields[_], Row, T, N[_]](
      f: Fields[Row] => SortOrder[T, N, Row],
      value: SqlExpr.Const[T, N, Row]
  ) extends SeekNoHkt[Fields, Row, N[T]]

  def render[Row, Fields[_]](fields: Fields[Row], baseSql: Fragment, counter: AtomicInteger, params: SelectParams[Fields, Row]): Fragment = {
    val (filters, orderBys) = seeks.expand(fields, params)

    List[Option[Fragment]](
      Some(baseSql),
      NonEmptyList.fromFoldable(filters.map(f => f.render(counter))).map(fragments.whereAnd(_)),
      NonEmptyList.fromFoldable(orderBys.map(f => f.render(counter))).map(fragments.orderBy(_)),
      params.offset.map(value => fr"offset $value"),
      params.limit.map(value => fr"limit $value")
    ).flatten.reduce(_ ++ _)
  }
}

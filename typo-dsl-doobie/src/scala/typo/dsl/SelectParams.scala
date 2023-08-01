package typo.dsl

import cats.data.NonEmptyList
import doobie.implicits.toSqlInterpolator
import doobie.util.fragment.Fragment
import doobie.util.fragments

import java.util.concurrent.atomic.AtomicInteger

case class SelectParams[Fields[_], Row](
    where: List[Fields[Row] => SqlExpr[Boolean, Option, Row]],
    orderBy: List[Fields[Row] => SortOrder[?, Row]],
    offset: Option[Int],
    limit: Option[Int]
) {
  def where(v: Fields[Row] => SqlExpr[Boolean, Option, Row]): SelectParams[Fields, Row] = copy(where = where :+ v)
  def orderBy(v: Fields[Row] => SortOrder[?, Row]): SelectParams[Fields, Row] = copy(orderBy = orderBy :+ v)
  def offset(v: Int): SelectParams[Fields, Row] = copy(offset = Some(v))
  def limit(v: Int): SelectParams[Fields, Row] = copy(limit = Some(v))
}

object SelectParams {
  def empty[Fields[_], Row]: SelectParams[Fields, Row] =
    SelectParams[Fields, Row](List.empty, List.empty, None, None)

  def render[Row, Fields[_]](fields: Fields[Row], baseSql: Fragment, counter: AtomicInteger, params: SelectParams[Fields, Row]): Fragment = {
    List[Option[Fragment]](
      Some(baseSql),
      NonEmptyList.fromFoldable(params.where.map(f => f(fields).render(counter))).map(fragments.whereAnd(_)),
      NonEmptyList.fromFoldable(params.orderBy.map(f => f(fields).render(counter))).map(fragments.orderBy(_)),
      params.offset.map(value => fr"offset $value"),
      params.limit.map(value => fr"limit $value")
    ).flatten.reduce(_ ++ _)
  }

  def applyParams[Fields[_], Row](fields: Fields[Row], rows: List[Row], params: SelectParams[Fields, Row]): List[Row] = {
    // precompute filters and order bys for this row structure
    val filters: List[SqlExpr[Boolean, Option, Row]] =
      params.where.map(f => f(fields))
    val orderBys: List[SortOrder[?, Row]] =
      params.orderBy.map(f => f(fields))

    rows
      .filter(row => filters.forall(_.eval(row).getOrElse(false)))
      .sorted((row1: Row, row2: Row) =>
        orderBys.foldLeft(0) {
          case (acc, so: SortOrder[t, Row]) if acc == 0 =>
            val t1: t = so.expr.eval(row1)
            val t2: t = so.expr.eval(row2)
            val ordering: Ordering[t] = if (so.ascending) so.ordering else so.ordering.reverse
            ordering.compare(t1, t2)
          case (acc, _) => acc
        }
      )
      .drop(params.offset.getOrElse(0))
      .take(params.limit.getOrElse(Int.MaxValue))
  }
}

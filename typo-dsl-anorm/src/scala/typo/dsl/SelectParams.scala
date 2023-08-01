package typo.dsl

import typo.dsl.Fragment.FragmentStringInterpolator

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

    val maybeEnd: Option[Fragment] =
      List[Option[Fragment]](
        params.where.map(w => w(fields)).reduceLeftOption(_ and _).map { where =>
          Fragment(" where ") ++ where.render(counter)
        },
        params.orderBy match {
          case Nil      => None
          case nonEmpty => Some(frag" order by ${nonEmpty.map(x => x(fields).render(counter)).mkFragment(", ")}")
        },
        params.offset.map(value => Fragment(" offset " + value)),
        params.limit.map { value => Fragment(" limit " + value) }
      ).flatten.reduceOption(_ ++ _)

    val completeSql = maybeEnd match {
      case Some(end) => frag"""|$baseSql
                               |$end
                               |""".stripMargin
      case None => baseSql
    }

    completeSql
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

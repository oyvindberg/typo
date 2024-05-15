package typo.dsl

import typo.dsl.Fragment.FragmentStringInterpolator
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
  sealed trait SeekNoHkt[Fields[_], Row, NT] {
    val f: Fields[Row] => SortOrderNoHkt[NT, Row]
  }

  case class Seek[Fields[_], Row, T, N[_]](
      f: Fields[Row] => SortOrder[T, N, Row],
      value: SqlExpr.Const[T, N, Row]
  ) extends SeekNoHkt[Fields, Row, N[T]]

  def empty[Fields[_], Row]: SelectParams[Fields, Row] =
    SelectParams[Fields, Row](List.empty, List.empty, List.empty, None, None)

  def render[Row, Fields[_]](fields: Fields[Row], baseSql: Fragment, counter: AtomicInteger, params: SelectParams[Fields, Row]): Fragment = {
    val (filters, orderBys) = seeks.expand(fields, params)

    val maybeEnd: Option[Fragment] =
      List[Option[Fragment]](
        filters.reduceLeftOption(_.and(_)).map { where =>
          Fragment(" where ") ++ where.render(counter)
        },
        orderBys match {
          case Nil      => None
          case nonEmpty => Some(frag" order by ${nonEmpty.map(x => x.render(counter)).mkFragment(", ")}")
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
}

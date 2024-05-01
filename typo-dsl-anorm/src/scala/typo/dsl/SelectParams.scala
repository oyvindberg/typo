package typo.dsl

import typo.dsl.Fragment.FragmentStringInterpolator
import typo.dsl.internal.seeks

import java.util.concurrent.atomic.AtomicInteger

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

  def render[Fields, R](fields: Fields, baseSql: Fragment, ctx: RenderCtx, counter: AtomicInteger, params: SelectParams[Fields, R]): Fragment = {
    val (filters, orderBys) = seeks.expand(fields, params)

    val maybeEnd: Option[Fragment] =
      List[Option[Fragment]](
        filters.reduceLeftOption(_.and(_)).map { where =>
          Fragment(" where ") ++ where.render(ctx, counter)
        },
        orderBys match {
          case Nil      => None
          case nonEmpty => Some(frag" order by ${nonEmpty.map(x => x.render(ctx, counter)).mkFragment(", ")}")
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

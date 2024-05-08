package typo.dsl

import cats.syntax.foldable.*
import doobie.implicits.toSqlInterpolator
import doobie.util.fragment.Fragment

import java.util.concurrent.atomic.AtomicInteger

sealed trait SortOrderNoHkt[NT, R] {
  val expr: SqlExpr.SqlExprNoHkt[NT, R]
  val ascending: Boolean
  val nullsFirst: Boolean

  def withNullsFirst: SortOrderNoHkt[NT, R]

  def render(counter: AtomicInteger): Fragment =
    List[Fragment](
      expr.render(counter),
      if (ascending) fr"ASC" else fr"DESC",
      if (nullsFirst) fr"NULLS FIRST" else Fragment.empty
    ).intercalate(fr" ")
}

// sort by a field
final case class SortOrder[T, N[_], R](expr: SqlExpr[T, N, R], ascending: Boolean, nullsFirst: Boolean)(implicit val ordering: Ordering[T], val nullability: Nullability[N])
    extends SortOrderNoHkt[N[T], R] {
  def withNullsFirst: SortOrder[T, N, R] = copy(nullsFirst = true)(ordering, nullability)
}

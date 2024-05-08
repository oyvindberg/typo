package typo.dsl

import typo.dsl.Fragment.FragmentStringInterpolator

import java.util.concurrent.atomic.AtomicInteger

sealed trait SortOrderNoHkt[NT, R] {
  val expr: SqlExpr.SqlExprNoHkt[NT, R]
  val ascending: Boolean
  val nullsFirst: Boolean

  def withNullsFirst: SortOrderNoHkt[NT, R]

  final def render(counter: AtomicInteger): Fragment = {
    List[Fragment](
      expr.render(counter),
      if (ascending) frag"ASC" else frag"DESC",
      if (nullsFirst) frag"NULLS FIRST" else Fragment.empty
    ).mkFragment(" ")
  }
}

// sort by a field
final case class SortOrder[T, N[_], R](expr: SqlExpr[T, N, R], ascending: Boolean, nullsFirst: Boolean)(implicit val ordering: Ordering[T], val nullability: Nullability[N])
    extends SortOrderNoHkt[N[T], R] {
  def withNullsFirst: SortOrder[T, N, R] = copy(nullsFirst = true)(ordering, nullability)
}

package typo.dsl

import typo.dsl.Fragment.FragmentStringInterpolator

import java.util.concurrent.atomic.AtomicInteger

// sort by a field
case class SortOrder[NT, R](expr: SqlExpr.SqlExprNoHkt[NT, R], ascending: Boolean, nullsFirst: Boolean)(implicit val ordering: Ordering[NT]) {
  def withNullsFirst: SortOrder[NT, R] = copy(nullsFirst = true)(ordering)
  def render(counter: AtomicInteger): Fragment =
    List[Fragment](
      expr.render(counter),
      if (ascending) frag"ASC" else frag"DESC",
      if (nullsFirst) frag"NULLS FIRST" else Fragment.empty
    ).mkFragment(" ")
}

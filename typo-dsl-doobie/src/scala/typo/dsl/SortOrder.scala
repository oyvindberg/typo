package typo.dsl

import cats.syntax.foldable.*
import doobie.implicits.toSqlInterpolator
import doobie.util.fragment.Fragment

import java.util.concurrent.atomic.AtomicInteger

// sort by a field
case class SortOrder[NT, R](expr: SqlExpr.SqlExprNoHkt[NT, R], ascending: Boolean, nullsFirst: Boolean)(implicit val ordering: Ordering[NT]) {
  def withNullsFirst: SortOrder[NT, R] = copy(nullsFirst = true)(ordering)
  def render(counter: AtomicInteger): Fragment =
    List[Fragment](
      expr.render(counter),
      if (ascending) fr"ASC" else fr"DESC",
      if (nullsFirst) fr"NULLS FIRST" else Fragment.empty
    ).intercalate(fr" ")
}

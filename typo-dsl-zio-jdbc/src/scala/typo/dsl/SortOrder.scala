package typo.dsl

import zio.Chunk
import zio.jdbc.*
import extensions.FragmentOps
import java.util.concurrent.atomic.AtomicInteger

// sort by a field
final case class SortOrder[NT, R](expr: SqlExpr.SqlExprNoHkt[NT, R], ascending: Boolean, nullsFirst: Boolean)(implicit val ordering: Ordering[NT]) {
  def withNullsFirst: SortOrder[NT, R] = copy(nullsFirst = true)(ordering)
  def render(counter: AtomicInteger): SqlFragment = {
    Chunk(
      expr.render(counter),
      if (ascending) sql"ASC" else sql"DESC",
      if (nullsFirst) sql"NULLS FIRST" else SqlFragment.empty
    ).mkFragment(" ")
  }
}

package typo.dsl.internal

import typo.dsl.{SortOrder, SortOrderNoHkt}

object mocks {
  class RowOrdering[Row](sortOrderings: List[SortOrderNoHkt[?, Row]]) extends Ordering[Row] {
    override def compare(leftRow: Row, rightRow: Row): Int =
      sortOrderings.iterator
        .map { case so: SortOrder[t, n, Row] @unchecked /* for 2.13*/ =>
          val left = so.expr.eval(leftRow)
          val right = so.expr.eval(rightRow)
          val ordering: Ordering[Option[t]] = Ordering.Option(if (so.ascending) so.ordering else so.ordering.reverse)
          ordering.compare(so.nullability.toOpt(left), so.nullability.toOpt(right))
        }
        .find(_ != 0)
        .getOrElse(0)
  }
}

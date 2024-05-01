package typo.dsl.pagination

import typo.dsl.{RenderCtx, SortOrderNoHkt}

import java.util.concurrent.atomic.AtomicInteger

/** A client cursor is inherently tied to a set of sort orderings.
  *
  * As such we encode them in the cursor itself, and verify them on the way in.
  */
case class SortOrderRepr(expr: String) extends AnyVal

object SortOrderRepr {
  def from[NT](x: SortOrderNoHkt[NT], ctx: RenderCtx): SortOrderRepr = {
    // note `x.expr`! the value is independent of ascending/descending and nulls first/last
    val fragment = x.expr.render(ctx, new AtomicInteger(0))
    // todo: deconstructing the sql string and replacing `?` with the value would yield a more readable result
    val sql = fragment.params match {
      case Nil      => fragment.sql
      case nonEmpty => fragment.sql + ":" + nonEmpty.map(_.value.show).mkString(",")
    }
    SortOrderRepr(sql.trim)
  }
}

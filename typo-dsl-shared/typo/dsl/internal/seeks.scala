package typo.dsl.internal

import typo.dsl.SelectParams.Seek
import typo.dsl.*

object seeks {
  def expand[Fields, Row](fields: Fields, params: SelectParams[Fields, Row]): (List[SqlExpr[Boolean, Option]], List[SortOrderNoHkt[?]]) =
    params.seeks match {
      case Nil => (params.where.map(_.apply(fields)), params.orderBy.map(_.apply(fields)))
      case nonEmpty =>
        require(params.orderBy.isEmpty, "Cannot have both seeks and orderBy")
        val seekOrderBys: List[SortOrderNoHkt[?]] =
          nonEmpty.map { case seek: Seek[Fields, _, _] @unchecked /* for 2.13*/ => seek.f(fields) }

        val seekWhere: SqlExpr[Boolean, Option] =
          seekOrderBys.map(_.ascending).distinct match {
            case List(uniformIsAscending) =>
              val dbTuple = SqlExpr.RowExpr(seekOrderBys.map(so => so.expr))
              val valueTuple = SqlExpr.RowExpr(nonEmpty.map { case seek: Seek[Fields, t, n] @unchecked /* for 2.13 */ => seek.value })
              // this is for mock repositories. it's hard to separate it out from here
              implicit val ordering: Ordering[List[?]] = (leftRow: List[?], rightRow: List[?]) =>
                leftRow.iterator
                  .zip(rightRow.iterator)
                  .zip(seekOrderBys.iterator)
                  .map { case ((left, right), so: SortOrder[t, n] @unchecked) /* for 2.13*/ =>
                    val ordering: Ordering[Option[t]] = Ordering.Option(if (so.ascending) so.ordering else so.ordering.reverse)
                    ordering.compare(so.nullability.toOpt(left.asInstanceOf[n[t]]), so.nullability.toOpt(right.asInstanceOf[n[t]]))
                  }
                  .find(_ != 0)
                  .getOrElse(0)

              if (uniformIsAscending) dbTuple > valueTuple else dbTuple < valueTuple
            case _ =>
              val orConditions: Seq[SqlExpr[Boolean, Option]] =
                nonEmpty.indices.map { i =>
                  val equals: List[SqlExpr[Boolean, Option]] =
                    nonEmpty.take(i).map { case seek: Seek[Fields, t, n] @unchecked /* for 2.13*/ =>
                      val so: SortOrder[t, n] = seek.f(fields)
                      implicit val n: Nullability2[n, n, Option] = Nullability2.opt(using so.nullability, so.nullability)
                      implicit val ordering: Ordering[t] = so.ordering
                      so.expr === seek.value
                    }

                  nonEmpty(i) match {
                    case seek: Seek[Fields, t, n] @unchecked /* for 2.13*/ =>
                      val so: SortOrder[t, n] = seek.f(fields)
                      implicit val n: Nullability2[n, n, Option] = Nullability2.opt(using so.nullability, so.nullability)
                      implicit val ordering: Ordering[t] = so.ordering
                      val predicate: SqlExpr[Boolean, Option] =
                        if (so.ascending) so.expr > seek.value else so.expr < seek.value
                      (equals :+ predicate).reduce(_ and _)
                  }
                }

              orConditions.reduce(_ or _)
          }

        (params.where.map(_.apply(fields)) :+ seekWhere, seekOrderBys)
    }
}

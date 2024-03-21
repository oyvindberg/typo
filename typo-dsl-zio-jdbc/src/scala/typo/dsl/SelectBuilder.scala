package typo.dsl

import zio.{Chunk, ZIO}
import zio.jdbc.*

trait SelectBuilder[Fields[_], Row] {

  /** Add a where clause to the query.
    *
    * Consecutive calls to where will be combined with `AND`
    *
    * {{{
    *   productRepo.select
    *    .where(_.`class` === "H")
    *    .where(x => x.daystomanufacture > 25 or x.daystomanufacture <= 0)
    *    .where(x => x.productline === "foo")
    * }}}
    */
  final def where[N[_]: Nullability](v: Fields[Hidden] => SqlExpr[Boolean, N, Hidden]): SelectBuilder[Fields, Row] =
    withParams(params.where(fields => v(fields.asInstanceOf[Fields[Hidden]]).asInstanceOf[SqlExpr[Boolean, N, Row]].?))

  final def maybeWhere[N[_]: Nullability, T](ot: Option[T])(v: (T, Fields[Hidden]) => SqlExpr[Boolean, N, Hidden]): SelectBuilder[Fields, Row] =
    ot match {
      case Some(t) => where(fields => v(t, fields))
      case None    => this
    }

  /** Same as [[where]], but requires the expression to not be nullable */
  final def whereStrict(v: Fields[Hidden] => SqlExpr[Boolean, Required, Hidden]): SelectBuilder[Fields, Row] =
    withParams(params.where(fields => v(fields.asInstanceOf[Fields[Hidden]]).asInstanceOf[SqlExpr[Boolean, Required, Row]].?))

  /** Add an order by clause to the query.
    *
    * Consecutive calls to orderBy will be combined and order kept
    *
    * {{{
    *      productRepo.select
    *        .join(unitmeasureRepo.select.where(_.name.like("name%")))
    *        .on { case (p, um) => p.sizeunitmeasurecode === um.unitmeasurecode }
    *        .join(projectModelRepo.select)
    *        .leftOn { case ((product, _), productModel) => product.productmodelid === productModel.productmodelid }
    *        .orderBy { case ((product, _), _) => product.productmodelid.asc }
    *        .orderBy { case ((_, _), productModel) => productModel(_.name).desc.withNullsFirst }
    * }}}
    */
  final def orderBy(v: Fields[Hidden] => SortOrder[?, Hidden]): SelectBuilder[Fields, Row] =
    withParams(params.orderBy(v.asInstanceOf[Fields[Row] => SortOrder[?, Row]]))
  final def offset(v: Int): SelectBuilder[Fields, Row] =
    withParams(params.offset(v))
  final def limit(v: Int): SelectBuilder[Fields, Row] =
    withParams(params.limit(v))

  /** Execute the query and return the results as a list */
  def toChunk: ZIO[ZConnection, Throwable, Chunk[Row]]

  /** Return sql for debugging. [[None]] if backed by a mock repository */
  def sql: Option[SqlFragment]

  /** start constructing a join */
  final def join[F2[_], Row2](other: SelectBuilder[F2, Row2]): PartialJoin[F2, Row2] =
    new PartialJoin[F2, Row2](other)

  final class PartialJoin[Fields2[_], Row2](other: SelectBuilder[Fields2, Row2]) {

    /** inner join with the given predicate
      * {{{
      *  val query = productRepo.select
      *   .join(unitmeasureRepo.select.where(_.name.like("name%")))
      *   .on { case (p, um) => p.sizeunitmeasurecode === um.unitmeasurecode }
      * }}}
      */
    def on[N[_]: Nullability](pred: Joined[Fields, Fields2, Hidden] => SqlExpr[Boolean, N, Hidden]): SelectBuilder[Joined[Fields, Fields2, *], (Row, Row2)] =
      joinOn(other)(pred.asInstanceOf[Joined[Fields, Fields2, (Row, Row2)] => SqlExpr[Boolean, N, (Row, Row2)]])

    /** Variant of `on` that requires the join predicate to not be nullable */
    def onStrict(pred: Joined[Fields, Fields2, Hidden] => SqlExpr[Boolean, Required, Hidden]): SelectBuilder[Joined[Fields, Fields2, *], (Row, Row2)] =
      joinOn(other)(pred.asInstanceOf[Joined[Fields, Fields2, (Row, Row2)] => SqlExpr[Boolean, Required, (Row, Row2)]])

    /** left join with the given predicate
      * {{{
      * val leftJoined = productRepo.select
      * .join(projectModelRepo.select)
      * .leftOn { case (p, pm) => p.productmodelid === pm.productmodelid }
      * }}}
      */
    def leftOn[N[_]: Nullability](pred: Joined[Fields, Fields2, Hidden] => SqlExpr[Boolean, N, Hidden]): SelectBuilder[LeftJoined[Fields, Fields2, *], (Row, Option[Row2])] =
      leftJoinOn(other)(pred.asInstanceOf[Joined[Fields, Fields2, (Row, Row2)] => SqlExpr[Boolean, N, (Row, Row2)]])

    /** Variant of `leftOn` that requires the join predicate to not be nullable */
    def leftOnStrict(pred: Joined[Fields, Fields2, Hidden] => SqlExpr[Boolean, Required, Hidden]): SelectBuilder[LeftJoined[Fields, Fields2, *], (Row, Option[Row2])] =
      leftJoinOn(other)(pred.asInstanceOf[Joined[Fields, Fields2, (Row, Row2)] => SqlExpr[Boolean, Required, (Row, Row2)]])
  }

  protected def params: SelectParams[Fields, Row]

  protected def withParams(sqlParams: SelectParams[Fields, Row]): SelectBuilder[Fields, Row]

  protected def joinOn[Fields2[_], N[_]: Nullability, Row2](other: SelectBuilder[Fields2, Row2])(
      pred: Joined[Fields, Fields2, (Row, Row2)] => SqlExpr[Boolean, N, (Row, Row2)]
  ): SelectBuilder[Joined[Fields, Fields2, *], (Row, Row2)]

  protected def leftJoinOn[Fields2[_], N[_]: Nullability, Row2](other: SelectBuilder[Fields2, Row2])(
      pred: Joined[Fields, Fields2, (Row, Row2)] => SqlExpr[Boolean, N, (Row, Row2)]
  ): SelectBuilder[LeftJoined[Fields, Fields2, *], (Row, Option[Row2])]
}

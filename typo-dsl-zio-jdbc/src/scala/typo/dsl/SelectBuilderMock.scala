package typo.dsl

import typo.dsl.internal.seeks
import typo.dsl.internal.mocks.RowOrdering
import zio.{Chunk, ZIO}
import zio.jdbc.*

final case class SelectBuilderMock[Fields[_], Row](
    structure: Structure[Fields, Row],
    all: ZIO[ZConnection, Throwable, Chunk[Row]],
    params: SelectParams[Fields, Row]
) extends SelectBuilder[Fields, Row] {
  override def withParams(sqlParams: SelectParams[Fields, Row]): SelectBuilder[Fields, Row] =
    copy(params = sqlParams)

  override def toChunk: ZIO[ZConnection, Throwable, Chunk[Row]] =
    all.map(all => SelectBuilderMock.applyParams(structure.fields, all, params))

  override def joinOn[Fields2[_], N[_]: Nullability, Row2](
      other: SelectBuilder[Fields2, Row2]
  )(pred: Joined[Fields, Fields2, (Row, Row2)] => SqlExpr[Boolean, N, (Row, Row2)]): SelectBuilder[Joined[Fields, Fields2, *], (Row, Row2)] = {
    def otherMock: SelectBuilderMock[Fields2, Row2] = other match {
      case x: SelectBuilderMock[Fields2, Row2] => x
      case _                                   => sys.error("you cannot mix mock and sql repos")
    }

    val newStructure = structure.join(otherMock.structure)

    val newRows: ZIO[ZConnection, Throwable, Chunk[(Row, Row2)]] =
      for {
        lefts <- this.toChunk
        rights <- otherMock.toChunk
      } yield for {
        left <- lefts
        right <- rights
        newRow = (left, right)
        if Nullability[N].toOpt(pred(newStructure.fields).eval(newRow)).getOrElse(false)
      } yield newRow

    SelectBuilderMock[Joined[Fields, Fields2, *], (Row, Row2)](newStructure, newRows, SelectParams.empty)
  }
  override def leftJoinOn[Fields2[_], N[_]: Nullability, Row2](other: SelectBuilder[Fields2, Row2])(
      pred: Joined[Fields, Fields2, (Row, Row2)] => SqlExpr[Boolean, N, (Row, Row2)]
  ): SelectBuilder[LeftJoined[Fields, Fields2, *], (Row, Option[Row2])] = {
    def otherMock: SelectBuilderMock[Fields2, Row2] = other match {
      case x: SelectBuilderMock[Fields2, Row2] => x
      case _                                   => sys.error("you cannot mix mock and sql repos")
    }

    val newRows: ZIO[ZConnection, Throwable, Chunk[(Row, Option[Row2])]] = {
      for {
        lefts <- this.toChunk
        rights <- otherMock.toChunk
      } yield {
        lefts.map { left =>
          val maybeRight = rights.find { right =>
            Nullability[N].toOpt(pred(structure.join(otherMock.structure).fields).eval((left, right))).getOrElse(false)
          }
          (left, maybeRight)
        }
      }
    }
    SelectBuilderMock[LeftJoined[Fields, Fields2, *], (Row, Option[Row2])](structure.leftJoin(otherMock.structure), newRows, SelectParams.empty)
  }

  override def sql: Option[SqlFragment] = None
}

object SelectBuilderMock {
  def applyParams[Fields[_], Row](fields: Fields[Row], rows: Chunk[Row], params: SelectParams[Fields, Row]): Chunk[Row] = {
    val (filters, orderBys) = seeks.expand(fields, params)
    implicit val rowOrdering: Ordering[Row] = new RowOrdering(orderBys)
    rows
      .filter(row => filters.forall(_.eval(row).getOrElse(false)))
      .sorted
      .drop(params.offset.getOrElse(0))
      .take(params.limit.getOrElse(Int.MaxValue))
  }
}

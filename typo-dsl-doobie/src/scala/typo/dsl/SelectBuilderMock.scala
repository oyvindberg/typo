package typo.dsl

import doobie.free.connection.ConnectionIO
import doobie.util.fragment.Fragment
import typo.dsl.internal.mocks.RowOrdering
import typo.dsl.internal.seeks

final case class SelectBuilderMock[Fields, Row](
    structure: Structure[Fields, Row],
    all: ConnectionIO[List[Row]],
    params: SelectParams[Fields, Row]
) extends SelectBuilder[Fields, Row] {
  def withPath(path: Path): SelectBuilderMock[Fields, Row] =
    copy(structure = structure.withPath(path))

  override val renderCtx: RenderCtx = RenderCtx.Empty

  override def withParams(sqlParams: SelectParams[Fields, Row]): SelectBuilder[Fields, Row] =
    copy(params = sqlParams)

  override def toList: ConnectionIO[List[Row]] =
    all.map(all => SelectBuilderMock.applyParams(structure, all, params))

  override def joinOn[Fields2, N[_]: Nullability, Row2](
      other: SelectBuilder[Fields2, Row2]
  )(pred: Joined[Fields, Fields2] => SqlExpr[Boolean, N]): SelectBuilderMock[Joined[Fields, Fields2], (Row, Row2)] = {
    val otherMock: SelectBuilderMock[Fields2, Row2] = other match {
      case x: SelectBuilderMock[Fields2, Row2] => x.withPath(Path.RightInJoin)
      case _                                   => sys.error("you cannot mix mock and sql repos")
    }

    val self = this.withPath(Path.LeftInJoin)
    val newStructure = self.structure.join(otherMock.structure)

    val newRows: ConnectionIO[List[(Row, Row2)]] =
      for {
        lefts <- this.toList
        rights <- otherMock.toList
      } yield for {
        left <- lefts
        right <- rights
        newRow = (left, right)
        if Nullability[N].toOpt(newStructure.untypedEval(pred(newStructure.fields), newRow)).getOrElse(false)
      } yield newRow

    SelectBuilderMock[Joined[Fields, Fields2], (Row, Row2)](newStructure, newRows, SelectParams.empty)
  }
  override def leftJoinOn[Fields2, N[_]: Nullability, Row2](
      other: SelectBuilder[Fields2, Row2]
  )(pred: Joined[Fields, Fields2] => SqlExpr[Boolean, N]): SelectBuilder[LeftJoined[Fields, Fields2], (Row, Option[Row2])] = {
    val otherMock: SelectBuilderMock[Fields2, Row2] = other match {
      case x: SelectBuilderMock[Fields2, Row2] => x.withPath(Path.RightInJoin)
      case _                                   => sys.error("you cannot mix mock and sql repos")
    }
    val self = this.withPath(Path.LeftInJoin)

    val newRows: ConnectionIO[List[(Row, Option[Row2])]] = {
      for {
        lefts <- self.toList
        rights <- otherMock.toList
      } yield {
        lefts.map { left =>
          val maybeRight = rights.find { right =>
            val newStructure = structure.join(otherMock.structure)
            Nullability[N].toOpt(newStructure.untypedEval(pred(newStructure.fields), (left, right))).getOrElse(false)
          }
          (left, maybeRight)
        }
      }
    }
    SelectBuilderMock[LeftJoined[Fields, Fields2], (Row, Option[Row2])](self.structure.leftJoin(otherMock.structure), newRows, SelectParams.empty)
  }

  override def sql: Option[Fragment] = None
}

object SelectBuilderMock {
  def applyParams[Fields, R](structure: Structure[Fields, R], rows: List[R], params: SelectParams[Fields, R]): List[R] = {
    val (filters, orderBys) = seeks.expand(structure.fields, params)
    implicit val rowOrdering: Ordering[R] = new RowOrdering(structure, orderBys)
    rows
      .filter(row => filters.forall(expr => structure.untypedEval(expr, row).getOrElse(false)))
      .sorted
      .drop(params.offset.getOrElse(0))
      .take(params.limit.getOrElse(Int.MaxValue))
  }
}

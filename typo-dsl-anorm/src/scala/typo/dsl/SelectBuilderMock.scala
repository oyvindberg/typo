package typo.dsl

import java.sql.Connection

case class SelectBuilderMock[Fields[_], Row](
    structure: Structure[Fields, Row],
    all: () => List[Row],
    params: SelectParams[Fields, Row]
) extends SelectBuilder[Fields, Row] {
  override def withParams(sqlParams: SelectParams[Fields, Row]): SelectBuilder[Fields, Row] =
    copy(params = sqlParams)

  override def toList(implicit c: Connection): List[Row] =
    SelectParams.applyParams(structure.fields, all(), params)

  override def joinOn[Fields2[_], N[_]: Nullability, Row2](
      other: SelectBuilder[Fields2, Row2]
  )(pred: Joined[Fields, Fields2, (Row, Row2)] => SqlExpr[Boolean, N, (Row, Row2)]): SelectBuilder[Joined[Fields, Fields2, *], (Row, Row2)] = {
    def otherMock: SelectBuilderMock[Fields2, Row2] = other match {
      case x: SelectBuilderMock[Fields2, Row2] => x
      case _                                   => sys.error("you cannot mix mock and sql repos")
    }

    val newStructure = structure.join(otherMock.structure)

    val newRows: () => List[(Row, Row2)] = () =>
      for {
        left <- this.toList(null)
        right <- otherMock.toList(null)
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

    val newRows: () => List[(Row, Option[Row2])] = () => {
      val rights = otherMock.toList(null)
      this.toList(null).map { left =>
        val maybeRight = rights.find { right =>
          Nullability[N].toOpt(pred(structure.join(otherMock.structure).fields).eval((left, right))).getOrElse(false)
        }
        (left, maybeRight)
      }
    }
    SelectBuilderMock[LeftJoined[Fields, Fields2, *], (Row, Option[Row2])](structure.leftJoin(otherMock.structure), newRows, SelectParams.empty)
  }

  override def sql: Option[Fragment] = None
}

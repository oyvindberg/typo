package typo
package internal

import typo.internal.rewriteDependentData.Eval

case class ViewComputed(view: db.View, naming: Naming, scalaTypeMapper: TypeMapperScala, eval: Eval[db.RelationName, Either[ViewComputed, TableComputed]]) {
  val cols: NonEmptyList[ColumnComputed] =
    view.cols.map { dbCol =>
      ColumnComputed(
        pointsTo = view.dependencies.get(dbCol.name),
        name = naming.field(dbCol.name),
        tpe = deriveType(dbCol),
        dbCol = dbCol
      )
    }

  def deriveType(dbCol: db.Col) = {
    // we let types flow through constraints down to this column, the point is to reuse id types downstream
    val typeFromFk: Option[sc.Type] =
      view.dependencies.get(dbCol.name) match {
        case Some((otherTableName, otherColName)) if otherTableName != view.name =>
          eval(otherTableName).get match {
            case Some(otherTable) =>
              val cols = otherTable match {
                case Left(view)   => view.cols
                case Right(table) => table.cols
              }
              cols.find(_.dbName == otherColName).map(_.tpe)
            case None =>
              System.err.println(s"Unexpected circular dependency involving ${view.name.value} => ${otherTableName.value}")
              None
          }

        case _ => None
      }

    val tpe = scalaTypeMapper.col(OverrideFrom.View(view.name), dbCol, typeFromFk)
    tpe
  }

  val relation = RelationComputed(naming, view.name, cols, maybeId = None)

  val repoMethods: NonEmptyList[RepoMethod] = {
    val RowType = relation.RowName
    val fieldValuesParam = sc.Param(
      sc.Ident("fieldValues"),
      sc.Type.List.of(relation.FieldOrIdValueName.of(sc.Type.Wildcard)),
      None
    )

    NonEmptyList(
      RepoMethod.SelectAll(RowType),
      RepoMethod.SelectByFieldValues(fieldValuesParam, RowType)
    )
  }
}

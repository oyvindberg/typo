package typo
package internal

import typo.internal.rewriteDependentData.Eval

case class ComputedView(view: db.View, naming: Naming, scalaTypeMapper: TypeMapperScala, eval: Eval[db.RelationName, HasSource], enableFieldValue: Boolean) extends HasSource {
  val source = Source.View(view.name, view.isMaterialized)
  val cols: NonEmptyList[ComputedColumn] =
    view.cols.map { dbCol =>
      ComputedColumn(
        pointsTo = view.dependencies.get(dbCol.name).map { case (relName, colName) => (eval(relName).forceGet.source, colName) },
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
            case Some(otherRelSource) =>
              otherRelSource.cols.find(_.dbName == otherColName).map(_.tpe)
            case None =>
              System.err.println(s"Unexpected circular dependency involving ${view.name.value} => ${otherTableName.value}")
              None
          }

        case _ => None
      }

    val tpe = scalaTypeMapper.col(source, dbCol, typeFromFk)
    tpe
  }

  val names = ComputedNames(naming, source, cols, maybeId = None, enableFieldValue)

  val repoMethods: NonEmptyList[RepoMethod] = {
    val maybeSelectByFieldValues = for {
      fieldOrIdValueName <- names.FieldOrIdValueName
      fieldValueName <- names.FieldValueName
    } yield {
      val fieldValuesParam = sc.Param(
        sc.Ident("fieldValues"),
        sc.Type.List.of(fieldOrIdValueName.of(sc.Type.Wildcard)),
        None
      )
      RepoMethod.SelectByFieldValues(view.name, cols, fieldValueName, fieldValuesParam, names.RowName)
    }

    NonEmptyList(
      RepoMethod.SelectAll(view.name, cols, names.RowName),
      maybeSelectByFieldValues.toList
    )
  }
}

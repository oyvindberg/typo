package typo
package internal

import typo.internal.rewriteDependentData.Eval

case class ComputedView(
    view: db.View,
    naming: Naming,
    typeMapperDb: TypeMapperDb,
    scalaTypeMapper: TypeMapperScala,
    eval: Eval[db.RelationName, HasSource],
    enableFieldValue: Boolean,
    enableDsl: Boolean
) extends HasSource {
  val source = Source.View(view.name, view.isMaterialized)

  val cols: NonEmptyList[ComputedColumn] =
    view.cols.map { case (col, parsedName) =>
      // we let types flow through constraints down to this column, the point is to reuse id types downstream
      val typeFromFk: Option[sc.Type] =
        view.deps.get(col.name) match {
          case Some((otherTableName, otherColName)) =>
            val existingTable = eval(otherTableName)
            for {
              nonCircular <- existingTable.get
              col <- nonCircular.cols.find(_.dbName == otherColName)
            } yield col.tpe
          case _ => None
        }

      val tpe = scalaTypeMapper.sqlFile(parsedName.overriddenType.orElse(typeFromFk), col.tpe, col.nullability)

      val pointsTo = view.deps.get(col.name).flatMap { case (relName, colName) => eval(relName).get.map(_.source -> colName) }
      ComputedColumn(pointsTo = pointsTo, name = naming.field(col.name), tpe = tpe, dbCol = col)
    }

  val names = ComputedNames(naming, source, maybeId = None, enableFieldValue, enableDsl = enableDsl)

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
    val maybeSelectBuilder = for {
      fieldsName <- names.FieldsName
    } yield {
      RepoMethod.SelectBuilder(view.name, fieldsName, names.RowName)
    }
    NonEmptyList[RepoMethod](
      RepoMethod.SelectAll(view.name, cols, names.RowName),
      maybeSelectBuilder.toList ++ maybeSelectByFieldValues
    ).sorted
  }
}

package typo
package internal

import typo.internal.analysis.MaybeReturnsRows
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

  val deps: Map[db.ColName, (db.RelationName, db.ColName)] =
    view.jdbcMetadata.columns match {
      case MaybeReturnsRows.Query(columns) =>
        columns.toList.flatMap(col => col.baseRelationName.zip(col.baseColumnName).map(col.name -> _)).toMap
      case MaybeReturnsRows.Update =>
        Map.empty
    }

  val cols: NonEmptyList[ComputedColumn] =
    view.jdbcMetadata.columns match {
      case MaybeReturnsRows.Query(metadataCols) =>
        metadataCols.zipWithIndex.map { case (col, idx) =>
          val nullability: Nullability =
            col.parsedColumnName.nullability.getOrElse {
              if (view.nullableColumnsFromJoins.exists(_.values(idx))) Nullability.Nullable
              else col.isNullable.toNullability
            }

          val dbType = typeMapperDb.dbTypeFrom(col.columnTypeName, Some(col.precision)).getOrElse {
            System.err.println(s"Couldn't translate type from view ${view.name.value} column ${col.name.value} with type ${col.columnTypeName}. Falling back to text")
            db.Type.Text
          }

          // we let types flow through constraints down to this column, the point is to reuse id types downstream
          val typeFromFk: Option[sc.Type] =
            deps.get(col.name) match {
              case Some((otherTableName, otherColName)) =>
                val existingTable = eval(otherTableName)
                for {
                  nonCircular <- existingTable.get
                  col <- nonCircular.cols.find(_.dbName == otherColName)
                } yield col.tpe
              case _ => None
            }

          val tpe = scalaTypeMapper.sqlFile(col.parsedColumnName.overriddenType.orElse(typeFromFk), dbType, nullability)

          ComputedColumn(
            pointsTo = deps.get(col.name).flatMap { case (relName, colName) => eval(relName).get.map(_.source -> colName) },
            name = naming.field(col.name),
            tpe = tpe,
            dbCol = db.Col(
              name = col.name,
              tpe = dbType,
              udtName = None,
              columnDefault = None,
              comment = None,
              jsonDescription = DebugJson(col),
              nullability = nullability
            )
          )
        }

      case MaybeReturnsRows.Update => ???
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

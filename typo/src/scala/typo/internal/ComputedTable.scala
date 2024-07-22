package typo
package internal

import typo.internal.rewriteDependentData.Eval

case class ComputedTable(
    options: InternalOptions,
    default: ComputedDefault,
    dbTable: db.Table,
    naming: Naming,
    scalaTypeMapper: TypeMapperJvm,
    eval: Eval[db.RelationName, HasSource]
) extends HasSource {
  override val source: Source.Table = Source.Table(dbTable.name)

  val deps: Map[db.ColName, List[(Source.Relation, db.ColName)]] = {
    val (fkSelf, fkOther) = dbTable.foreignKeys.partition { fk => fk.otherTable == dbTable.name }

    val fromSelf: List[(db.ColName, (Source.Relation, db.ColName))] =
      fkSelf.flatMap(fk => fk.cols.zip(fk.otherCols.map(cn => (source, cn))).toList)

    val fromOthers: List[(db.ColName, (Source.Relation, db.ColName))] =
      fkOther.flatMap { fk =>
        eval(fk.otherTable).get match {
          case None =>
            options.logger.warn(s"Circular: ${dbTable.name.value} => ${fk.otherTable.value}")
            Nil
          case Some(otherTable) =>
            fk.cols.zip(fk.otherCols.map(cn => (otherTable.source, cn))).toList
        }
      }

    (fromSelf ++ fromOthers).groupBy(_._1).map { case (colName, tuples) =>
      val sorted = tuples
        .map { case (_, other) => other }
        .sortBy { case (rel, colName) => (rel.name.value, colName.value) }
      colName -> sorted
    }
  }

  val dbColsByName: Map[db.ColName, db.Col] =
    dbTable.cols.map(col => (col.name, col)).toMap

  val maybeId: Option[IdComputed] =
    dbTable.primaryKey.flatMap { pk =>
      pk.colNames match {
        case NonEmptyList(colName, Nil) =>
          val dbCol = dbColsByName(colName)
          val pointsTo = deps.getOrElse(dbCol.name, Nil)

          findTypeFromFk(options.logger, source, dbCol.name, pointsTo, eval.asMaybe)(_ => None) match {
            case Some(tpe) =>
              val col = ComputedColumn(pointsTo = pointsTo, name = naming.field(dbCol.name), tpe = tpe, dbCol = dbCol)
              Some(IdComputed.UnaryInherited(col, tpe))
            case None =>
              val underlying = scalaTypeMapper.col(dbTable.name, dbCol, None)
              val col = ComputedColumn(pointsTo = pointsTo, name = naming.field(dbCol.name), tpe = underlying, dbCol = dbCol)
              if (sc.Type.containsUserDefined(underlying))
                Some(IdComputed.UnaryUserSpecified(col, underlying))
              else if (!options.enablePrimaryKeyType.include(dbTable.name))
                Some(IdComputed.UnaryNoIdType(col, underlying))
              else {
                val tpe = sc.Type.Qualified(naming.idName(source, List(col.dbCol)))
                Some(IdComputed.UnaryNormal(col, tpe))
              }
          }

        case colNames =>
          val cols: NonEmptyList[ComputedColumn] =
            colNames.map { colName =>
              ComputedColumn(
                pointsTo = Nil,
                name = naming.field(colName),
                tpe = deriveType(colName),
                dbCol = dbColsByName(colName)
              )
            }
          val tpe = sc.Type.Qualified(naming.idName(source, cols.toList.map(_.dbCol)))
          Some(IdComputed.Composite(cols, tpe, paramName = sc.Ident("compositeId")))
      }
    }

  val cols: NonEmptyList[ComputedColumn] =
    dbTable.cols.map { dbCol =>
      ComputedColumn(
        pointsTo = deps.getOrElse(dbCol.name, Nil),
        name = naming.field(dbCol.name),
        tpe = deriveType(dbCol.name),
        dbCol = dbCol
      )
    }

  def deriveType(colName: db.ColName): sc.Type = {
    val dbCol = dbColsByName(colName)
    // we let types flow through constraints down to this column, the point is to reuse id types downstream
    val typeFromFk: Option[sc.Type] =
      findTypeFromFk(options.logger, source, colName, deps.getOrElse(colName, Nil), eval.asMaybe)(otherColName => Some(deriveType(otherColName)))

    val typeFromId: Option[sc.Type] =
      maybeId match {
        case Some(id: IdComputed.Unary) if id.col.dbName == colName => Some(id.tpe)
        case _                                                      => None
      }

    scalaTypeMapper.col(dbTable.name, dbCol, typeFromFk.orElse(typeFromId))
  }

  val names = ComputedNames(naming, source, maybeId, options.enableFieldValue.include(dbTable.name), options.enableDsl)

  val colsNotId: Option[NonEmptyList[ComputedColumn]] =
    maybeId.flatMap { id =>
      val idNames = id.cols.map(_.dbName)
      NonEmptyList.fromList(
        cols.toList.filterNot(col => idNames.contains(col.dbName))
      )
    }

  val maybeUnsavedRow: Option[ComputedRowUnsaved] =
    ComputedRowUnsaved(source, cols, default, naming)

  val repoMethods: Option[NonEmptyList[RepoMethod]] = {
    val maybeMethods = List(
      List[Iterable[RepoMethod]](
        for {
          fieldsName <- names.FieldsName.toList
          builder <- List(
            RepoMethod.UpdateBuilder(dbTable.name, fieldsName, names.RowName),
            RepoMethod.SelectBuilder(dbTable.name, fieldsName, names.RowName),
            RepoMethod.DeleteBuilder(dbTable.name, fieldsName, names.RowName)
          )
        } yield builder,
        Some(RepoMethod.SelectAll(dbTable.name, cols, names.RowName)),
        maybeId.map(id => RepoMethod.SelectById(dbTable.name, cols, id, names.RowName)),
        maybeId.map { id =>
          val unsavedParam = sc.Param(sc.Ident("unsaved"), names.RowName)
          RepoMethod.Upsert(dbTable.name, cols, id, unsavedParam, names.RowName)
        },
        maybeId
          .collect {
            case unary: IdComputed.Unary =>
              RepoMethod.SelectByIds(dbTable.name, cols, unary, sc.Param(unary.paramName.appended("s"), sc.Type.ArrayOf(unary.tpe)), names.RowName)
            case x: IdComputed.Composite if x.cols.forall(col => col.dbCol.nullability == Nullability.NoNulls) =>
              RepoMethod.SelectByIds(dbTable.name, cols, x, sc.Param(x.paramName.appended("s"), sc.Type.ArrayOf(x.tpe)), names.RowName)
          }
          .toList
          .flatMap { x => List(x, RepoMethod.SelectByIdsTracked(x)) },
        for {
          fieldValueName <- names.FieldOrIdValueName
        } yield {
          val fieldValueOrIdsParam = sc.Param(sc.Ident("fieldValues"), TypesScala.List.of(fieldValueName.of(sc.Type.Wildcard)))
          RepoMethod.SelectByFieldValues(dbTable.name, cols, fieldValueName, fieldValueOrIdsParam, names.RowName)
        },
        for {
          id <- maybeId
          fieldValueName <- names.FieldOrIdValueName
        } yield RepoMethod.UpdateFieldValues(
          dbTable.name,
          id,
          sc.Param(sc.Ident("fieldValues"), TypesScala.List.of(fieldValueName.of(sc.Type.Wildcard))),
          fieldValueName,
          cols,
          names.RowName
        ),
        for {
          id <- maybeId
          colsNotId <- colsNotId
        } yield RepoMethod.Update(dbTable.name, cols, id, sc.Param(sc.Ident("row"), names.RowName), colsNotId),
        Some {
          val unsavedParam = sc.Param(sc.Ident("unsaved"), names.RowName)
          RepoMethod.Insert(dbTable.name, cols, unsavedParam, names.RowName)
        },
        if (options.enableStreamingInserts) Some(RepoMethod.InsertStreaming(dbTable.name, cols, names.RowName)) else None,
        maybeId.collect {
          case id if options.enableStreamingInserts => RepoMethod.UpsertStreaming(dbTable.name, cols, id, names.RowName)
        },
        maybeId.collect { case id =>
          RepoMethod.UpsertBatch(dbTable.name, cols, id, names.RowName)
        },
        maybeUnsavedRow.map { unsavedRow =>
          val unsavedParam = sc.Param(sc.Ident("unsaved"), unsavedRow.tpe)
          RepoMethod.InsertUnsaved(dbTable.name, cols, unsavedRow, unsavedParam, default, names.RowName)
        },
        maybeUnsavedRow.collect {
          case unsavedRow if options.enableStreamingInserts =>
            RepoMethod.InsertUnsavedStreaming(dbTable.name, unsavedRow)
        },
        maybeId.map(id => RepoMethod.Delete(dbTable.name, id)),
        maybeId.collect {
          case unary: IdComputed.Unary =>
            RepoMethod.DeleteByIds(dbTable.name, unary, sc.Param(unary.paramName.appended("s"), sc.Type.ArrayOf(unary.tpe)))
          case x: IdComputed.Composite if x.cols.forall(col => col.dbCol.nullability == Nullability.NoNulls) =>
            RepoMethod.DeleteByIds(dbTable.name, x, sc.Param(x.paramName.appended("s"), sc.Type.ArrayOf(x.tpe)))
        }
      ).flatten,
      dbTable.uniqueKeys
        .map { uk =>
          RepoMethod.SelectByUnique(
            dbTable.name,
            keyColumns = uk.cols.map(colName => cols.find(_.dbName == colName).get),
            allColumns = cols,
            rowType = names.RowName
          )
        }
    )
    val valid = maybeMethods.flatten.filter {
      case _: RepoMethod.Mutator => !options.readonlyRepo.include(dbTable.name)
      case _                     => true
    }.sorted

    NonEmptyList.fromList(valid)
  }
}

package typo
package internal

import typo.internal.compat.ListOps
import typo.internal.rewriteDependentData.Eval

case class ComputedTable(
    options: InternalOptions,
    default: ComputedDefault,
    dbTable: db.Table,
    naming: Naming,
    scalaTypeMapper: TypeMapperScala,
    eval: Eval[db.RelationName, HasSource]
) extends HasSource {
  override val source = Source.Table(dbTable.name)
  val pointsTo: Map[db.ColName, (Source.Relation, db.ColName)] =
    dbTable.foreignKeys.flatMap { fk =>
      val maybeOtherTable: Option[HasSource] =
        if (fk.otherTable == dbTable.name) Some(this)
        else eval(fk.otherTable).get

      maybeOtherTable match {
        case None =>
          options.logger.warn(s"Circular: ${dbTable.name.value} => ${fk.otherTable.value}")
          Nil
        case Some(otherTable) =>
          val value = fk.otherCols.map(cn => (otherTable.source, cn))
          fk.cols.zip(value).toList
      }
    }.toMap

  val dbColsByName: Map[db.ColName, db.Col] =
    dbTable.cols.map(col => (col.name, col)).toMap

  val maybeId: Option[IdComputed] =
    dbTable.primaryKey.flatMap { pk =>
      val tpe = sc.Type.Qualified(naming.idName(source))
      pk.colNames match {
        case NonEmptyList(colName, Nil) =>
          val dbCol = dbColsByName(colName)
          val underlying = scalaTypeMapper.col(dbTable.name, dbCol, None)
          val col = ComputedColumn(
            pointsTo = pointsTo.get(dbCol.name),
            name = naming.field(dbCol.name),
            tpe = underlying,
            dbCol = dbCol
          )
          col.pointsTo match {
            case Some((relationSource, colName)) =>
              val cols = eval(relationSource.name).forceGet.cols
              val tpe = cols.find(_.dbName == colName).get.tpe
              Some(IdComputed.UnaryInherited(col, tpe))
            case None =>
              if (sc.Type.containsUserDefined(underlying))
                Some(IdComputed.UnaryUserSpecified(col, underlying))
              else if (!options.enablePrimaryKeyType.include(dbTable.name))
                Some(IdComputed.UnaryNoIdType(col, underlying))
              else
                Some(IdComputed.UnaryNormal(col, tpe))

          }

        case colNames =>
          val cols: NonEmptyList[ComputedColumn] =
            colNames.map { colName =>
              val dbCol = dbColsByName(colName)
              ComputedColumn(
                pointsTo = None,
                name = naming.field(colName),
                tpe = deriveType(dbCol),
                dbCol = dbCol
              )
            }
          Some(IdComputed.Composite(cols, tpe, paramName = sc.Ident("compositeId")))
      }
    }

  val cols: NonEmptyList[ComputedColumn] = {
    dbTable.cols.map { dbCol =>
      val tpe = deriveType(dbCol)

      ComputedColumn(
        pointsTo = pointsTo.get(dbCol.name),
        name = naming.field(dbCol.name),
        tpe = tpe,
        dbCol = dbCol
      )
    }
  }

  def deriveType(dbCol: db.Col) = {
    // we let types flow through constraints down to this column, the point is to reuse id types downstream
    val typeFromFk: Option[sc.Type] =
      pointsTo.get(dbCol.name) match {
        case Some((otherTableSource, otherColName)) if otherTableSource.name != dbTable.name =>
          eval(otherTableSource.name).get match {
            case Some(otherTable) =>
              otherTable.cols.find(_.dbName == otherColName).map(_.tpe)
            case None =>
              options.logger.warn(s"Unexpected circular dependency involving ${dbTable.name.value} => ${otherTableSource.name.value}")
              None
          }

        case _ => None
      }

    val typeFromId: Option[sc.Type] =
      maybeId match {
        case Some(id: IdComputed.Unary) if id.col.dbName == dbCol.name => Some(id.tpe)
        case _                                                         => None
      }

    val tpe = scalaTypeMapper.col(dbTable.name, dbCol, typeFromFk.orElse(typeFromId))

    tpe
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
          val unsavedParam = sc.Param(sc.Ident("unsaved"), names.RowName, None)
          RepoMethod.Upsert(dbTable.name, cols, id, unsavedParam, names.RowName)
        },
        maybeId.collect {
          // todo: support composite ids
          case unary: IdComputed.Unary =>
            RepoMethod.SelectAllByIds(dbTable.name, cols, unary, sc.Param(unary.paramName.appended("s"), TypesScala.Array.of(unary.tpe), None), names.RowName)
        },
        for {
          name <- names.FieldOrIdValueName
          fieldValueName <- names.FieldValueName
        } yield {
          val fieldValueOrIdsParam = sc.Param(sc.Ident("fieldValues"), TypesScala.List.of(name.of(sc.Type.Wildcard)), None)
          RepoMethod.SelectByFieldValues(dbTable.name, cols, fieldValueName, fieldValueOrIdsParam, names.RowName)
        },
        for {
          id <- maybeId
          colsNotId <- colsNotId
          fieldValueName <- names.FieldValueName
        } yield RepoMethod.UpdateFieldValues(
          dbTable.name,
          id,
          sc.Param(
            sc.Ident("fieldValues"),
            TypesScala.List.of(fieldValueName.of(sc.Type.Wildcard)),
            None
          ),
          fieldValueName,
          colsNotId,
          names.RowName
        ),
        for {
          id <- maybeId
          colsNotId <- colsNotId
        } yield RepoMethod.Update(dbTable.name, cols, id, sc.Param(sc.Ident("row"), names.RowName, None), colsNotId),
        Some {
          val unsavedParam = sc.Param(sc.Ident("unsaved"), names.RowName, None)
          RepoMethod.Insert(dbTable.name, cols, unsavedParam, names.RowName)
        },
        if (options.enableStreamingInserts) Some(RepoMethod.InsertStreaming(dbTable.name, cols, names.RowName)) else None,
        maybeUnsavedRow.map { unsavedRow =>
          val unsavedParam = sc.Param(sc.Ident("unsaved"), unsavedRow.tpe, None)
          RepoMethod.InsertUnsaved(dbTable.name, cols, unsavedRow, unsavedParam, default, names.RowName)
        },
        maybeUnsavedRow.collect {
          case unsavedRow if options.enableStreamingInserts =>
            RepoMethod.InsertUnsavedStreaming(dbTable.name, unsavedRow)
        },
        maybeId.map(id => RepoMethod.Delete(dbTable.name, id))
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
        .distinctByCompat(x => x.keyColumns.map(_.tpe)) // avoid erasure clashes
    )
    val valid = maybeMethods.flatten.filter {
      case _: RepoMethod.Mutator => !options.readonlyRepo.include(dbTable.name)
      case _                     => true
    }.sorted

    NonEmptyList.fromList(valid)
  }
}

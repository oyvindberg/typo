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
  val source = Source.Table(dbTable.name)
  val pointsTo: Map[db.ColName, (Source.Relation, db.ColName)] =
    dbTable.foreignKeys.flatMap { fk =>
      val otherTable: HasSource =
        if (fk.otherTable == dbTable.name) this
        else eval(fk.otherTable).forceGet(s"${dbTable.name.value} => ${fk.otherTable.value}")

      val value = fk.otherCols.map(cn => (otherTable.source, cn))
      fk.cols.zip(value).toList
    }.toMap

  val dbColsByName: Map[db.ColName, db.Col] =
    dbTable.cols.map(col => (col.name, col)).toMap

  val maybeId: Option[IdComputed] =
    dbTable.primaryKey.flatMap { pk =>
      val tpe = sc.Type.Qualified(naming.idName(source))
      pk.colNames match {
        case NonEmptyList(colName, Nil) =>
          val dbCol = dbColsByName(colName)
          val underlying = scalaTypeMapper.col(Source.Table(dbTable.name), dbCol, None)
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
              System.err.println(s"Unexpected circular dependency involving ${dbTable.name.value} => ${otherTableSource.name.value}")
              None
          }

        case _ => None
      }

    val typeFromId: Option[sc.Type] =
      maybeId match {
        case Some(id: IdComputed.Unary) if id.col.dbName == dbCol.name => Some(id.tpe)
        case _                                                         => None
      }

    val tpe = scalaTypeMapper.col(source, dbCol, typeFromFk.orElse(typeFromId))

    tpe
  }

  val names = ComputedNames(naming, source, cols, maybeId)

  val colsNotId: Option[NonEmptyList[ComputedColumn]] =
    maybeId.flatMap { id =>
      val idNames = id.cols.map(_.dbName)
      NonEmptyList.fromList(
        cols.toList.filterNot(col => idNames.contains(col.dbName))
      )
    }

  val maybeUnsavedRow: Option[ComputedRowUnsaved] =
    ComputedRowUnsaved(source, cols, default, naming)

  val RowJoined: Option[ComputedRowJoined] =
    if (dbTable.foreignKeys.nonEmpty) {
      val name = naming.joinedRow(source)

      val maybeParams = dbTable.foreignKeys.flatMap {
        case fk if fk.otherTable == dbTable.name => None
        case fk =>
          eval(fk.otherTable).get match {
            case Some(nonCircularTable: ComputedTable) =>
              val tpe = nonCircularTable.names.RowName
              val fkContainsNullableRow = fk.cols.exists { colName =>
                dbColsByName(colName).nullability != Nullability.NoNulls
              }
              val tpeWithNullability = if (fkContainsNullableRow) sc.Type.Option.of(tpe) else tpe

              val newParam = ComputedRowJoined.Param(
                name = naming.field(fk.cols),
                tpe = tpeWithNullability,
                isOptional = fkContainsNullableRow,
                table = nonCircularTable
              )

              Some(newParam)
            case Some(_) =>
              System.err.println(s"Unexpected dependency on view ${dbTable.name.value} => ${fk.otherTable.value}")
              None
            case None =>
              System.err.println(s"Unexpected circular dependency ${dbTable.name.value} => ${fk.otherTable.value}")
              None
          }
      }
      NonEmptyList.fromList(maybeParams).map { params =>
        val thisParam = ComputedRowJoined.Param(sc.Ident("value"), names.RowName, isOptional = false, table = this)
        ComputedRowJoined(name, thisParam :: params)
      }

    } else None

  val repoMethods: Option[NonEmptyList[RepoMethod]] = {
    val fieldValueOrIdsParam = sc.Param(
      sc.Ident("fieldValues"),
      sc.Type.List.of(names.FieldOrIdValueName.of(sc.Type.Wildcard)),
      None
    )

    val insertMethod: RepoMethod = {
      val unsavedParam = sc.Param(sc.Ident("unsaved"), names.RowName, None)
      RepoMethod.Insert(dbTable.name, cols, unsavedParam, names.RowName)
    }

    val maybeInsertUnsavedMethod: Option[RepoMethod] =
      maybeUnsavedRow.map { unsavedRow =>
        val unsavedParam = sc.Param(sc.Ident("unsaved"), unsavedRow.tpe, None)
        RepoMethod.InsertUnsaved(dbTable.name, cols, unsavedRow, unsavedParam, default, names.RowName)
      }

    val maybeMethods = List(
      maybeId match {
        case Some(id) =>
          List[Iterable[RepoMethod]](
            Some(RepoMethod.SelectAll(dbTable.name, cols, names.RowName)),
            Some(RepoMethod.SelectById(dbTable.name, cols, id, names.RowName)), {
              val unsavedParam = sc.Param(sc.Ident("unsaved"), names.RowName, None)
              Some(RepoMethod.Upsert(dbTable.name, cols, id, unsavedParam, names.RowName))
            },
            id match {
              case unary: IdComputed.Unary =>
                Some(
                  RepoMethod.SelectAllByIds(dbTable.name, cols, unary, sc.Param(id.paramName.appended("s"), sc.Type.Array.of(id.tpe), None), names.RowName)
                )
              case IdComputed.Composite(_, _, _) =>
                // todo: support composite ids
                None
            },
            Some(RepoMethod.SelectByFieldValues(dbTable.name, cols, names.FieldValueName, fieldValueOrIdsParam, names.RowName)),
            colsNotId.map(colsNotId =>
              RepoMethod.UpdateFieldValues(
                dbTable.name,
                id,
                sc.Param(
                  sc.Ident("fieldValues"),
                  sc.Type.List.of(names.FieldValueName.of(sc.Type.Wildcard)),
                  None
                ),
                names.FieldValueName,
                colsNotId,
                names.RowName
              )
            ),
            colsNotId.map(colsNotId => RepoMethod.Update(dbTable.name, cols, id, sc.Param(sc.Ident("row"), names.RowName, None), colsNotId)),
            Some(insertMethod),
            maybeInsertUnsavedMethod,
            Some(RepoMethod.Delete(dbTable.name, id))
          ).flatten
        case None =>
          List(
            Some(insertMethod),
            maybeInsertUnsavedMethod,
            Some(RepoMethod.SelectAll(dbTable.name, cols, names.RowName)),
            Some(RepoMethod.SelectByFieldValues(dbTable.name, cols, names.FieldValueName, fieldValueOrIdsParam, names.RowName))
          ).flatten
      },
      dbTable.uniqueKeys
        .map { uk =>
          val params = uk.cols.map(colName => cols.find(_.dbName == colName).get)
          RepoMethod.SelectByUnique(params, names.FieldValueName, names.RowName)
        }
        .distinctByCompat(x => x.params.map(_.tpe)) // avoid erasure clashes
    )
    NonEmptyList.fromList(maybeMethods.flatten)
  }
}

package typo
package internal

import typo.internal.compat.ListOps
import typo.internal.rewriteDependentData.Eval

case class TableComputed(
    options: InternalOptions,
    default: DefaultComputed,
    dbTable: db.Table,
    naming: Naming,
    scalaTypeMapper: TypeMapperScala,
    eval: Eval[db.RelationName, Either[ViewComputed, TableComputed]]
) {
  val pointsTo: Map[db.ColName, (db.RelationName, db.ColName)] =
    dbTable.foreignKeys.flatMap(fk => fk.cols.zip(fk.otherCols.map(cn => (fk.otherTable, cn))).toList).toMap
  val dbColsByName: Map[db.ColName, db.Col] =
    dbTable.cols.map(col => (col.name, col)).toMap

  val maybeId: Option[IdComputed] =
    dbTable.primaryKey.flatMap { pk =>
      val tpe = sc.Type.Qualified(naming.idName(dbTable.name))
      pk.colNames match {
        case NonEmptyList(colName, Nil) =>
          val dbCol = dbColsByName(colName)
          val underlying = scalaTypeMapper.col(OverrideFrom.Table(dbTable.name), dbCol, None)
          val col = ColumnComputed(
            pointsTo = pointsTo.get(dbCol.name),
            name = naming.field(dbCol.name),
            tpe = underlying,
            dbCol = dbCol
          )
          col.pointsTo match {
            case Some((relationName, colName)) =>
              val cols = eval(relationName).forceGet match {
                case Left(view)   => view.cols
                case Right(table) => table.cols
              }
              val tpe = cols.find(_.dbName == colName).get.tpe
              Some(IdComputed.UnaryInherited(col, tpe))
            case None =>
              if (sc.Type.containsUserDefined(underlying))
                Some(IdComputed.UnaryUserSpecified(col, underlying))
              else
                Some(IdComputed.UnaryNormal(col, tpe))

          }

        case colNames =>
          val cols: NonEmptyList[ColumnComputed] =
            colNames.map { colName =>
              val dbCol = dbColsByName(colName)
              ColumnComputed(
                pointsTo = None,
                name = naming.field(colName),
                tpe = deriveType(dbCol),
                dbCol = dbCol
              )
            }
          Some(IdComputed.Composite(cols, tpe, paramName = sc.Ident("compositeId")))
      }
    }

  val cols: NonEmptyList[ColumnComputed] = {
    dbTable.cols.map { dbCol =>
      val tpe = deriveType(dbCol)

      ColumnComputed(
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
        case Some((otherTableName, otherColName)) if otherTableName != dbTable.name =>
          eval(otherTableName).get match {
            case Some(otherTable) =>
              val cols = otherTable match {
                case Left(view)   => view.cols
                case Right(table) => table.cols
              }
              cols.find(_.dbName == otherColName).map(_.tpe)
            case None =>
              System.err.println(s"Unexpected circular dependency involving ${dbTable.name.value} => ${otherTableName.value}")
              None
          }

        case _ => None
      }

    val typeFromId: Option[sc.Type] =
      maybeId match {
        case Some(id: IdComputed.Unary) if id.col.dbName == dbCol.name => Some(id.tpe)
        case _                                                         => None
      }

    val tpe = scalaTypeMapper.col(OverrideFrom.Table(dbTable.name), dbCol, typeFromFk.orElse(typeFromId))

    tpe
  }

  val relation = RelationComputed(naming, dbTable.name, cols, maybeId)

  val colsNotId: Option[NonEmptyList[ColumnComputed]] =
    maybeId.flatMap { id =>
      val idNames = id.cols.map(_.dbName)
      NonEmptyList.fromList(
        cols.toList.filterNot(col => idNames.contains(col.dbName))
      )
    }

  val maybeUnsavedRow: Option[RowUnsavedComputed] =
    RowUnsavedComputed(dbTable.name, cols, default, naming)

  val RowJoined: Option[RowJoinedComputed] =
    if (dbTable.foreignKeys.nonEmpty) {
      val name = naming.joinedRow(dbTable.name)

      val maybeParams = dbTable.foreignKeys.flatMap {
        case fk if fk.otherTable == dbTable.name => None
        case fk =>
          eval(fk.otherTable).get match {
            case Some(Right(nonCircularTable)) =>
              val tpe = nonCircularTable.relation.RowName
              val fkContainsNullableRow = fk.cols.exists { colName =>
                dbColsByName(colName).nullability != Nullability.NoNulls
              }
              val tpeWithNullability = if (fkContainsNullableRow) sc.Type.Option.of(tpe) else tpe

              val newParam = RowJoinedComputed.Param(
                name = naming.field(fk.cols),
                tpe = tpeWithNullability,
                isOptional = fkContainsNullableRow,
                table = nonCircularTable
              )

              Some(newParam)
            case Some(Left(_)) =>
              System.err.println(s"Unexpected dependency on view ${dbTable.name.value} => ${fk.otherTable.value}")
              None
            case None =>
              System.err.println(s"Unexpected circular dependency ${dbTable.name.value} => ${fk.otherTable.value}")
              None
          }
      }
      NonEmptyList.fromList(maybeParams).map { params =>
        val thisParam = RowJoinedComputed.Param(sc.Ident("value"), relation.RowName, isOptional = false, table = this)
        RowJoinedComputed(name, thisParam :: params)
      }

    } else None

  val repoMethods: Option[NonEmptyList[RepoMethod]] = {
    val fieldValueOrIdsParam = sc.Param(
      sc.Ident("fieldValues"),
      sc.Type.List.of(relation.FieldOrIdValueName.of(sc.Type.Wildcard)),
      None
    )

    val insertMethod: RepoMethod = {
      val unsavedParam = sc.Param(sc.Ident("unsaved"), relation.RowName, None)
      RepoMethod.Insert(cols, unsavedParam, relation.RowName)
    }

    val maybeInsertUnsavedMethod: Option[RepoMethod] =
      maybeUnsavedRow.map { unsavedRow =>
        val unsavedParam = sc.Param(sc.Ident("unsaved"), unsavedRow.tpe, None)
        RepoMethod.InsertUnsaved(unsavedRow, unsavedParam, default, relation.RowName)
      }

    val maybeMethods = List(
      maybeId match {
        case Some(id) =>
          List[Iterable[RepoMethod]](
            Some(RepoMethod.SelectAll(relation.RowName)),
            Some(RepoMethod.SelectById(id, relation.RowName)), {
              val unsavedParam = sc.Param(sc.Ident("unsaved"), relation.RowName, None)
              Some(RepoMethod.Upsert(id, unsavedParam, relation.RowName))
            },
            id match {
              case unary: IdComputed.Unary =>
                Some(RepoMethod.SelectAllByIds(unary, sc.Param(id.paramName.appended("s"), sc.Type.Array.of(id.tpe), None), relation.RowName))
              case IdComputed.Composite(_, _, _) =>
                // todo: support composite ids
                None
            },
            Some(RepoMethod.SelectByFieldValues(fieldValueOrIdsParam, relation.RowName)),
            colsNotId.map(colsNotId =>
              RepoMethod.UpdateFieldValues(
                id,
                sc.Param(
                  sc.Ident("fieldValues"),
                  sc.Type.List.of(relation.FieldValueName.of(sc.Type.Wildcard)),
                  None
                ),
                colsNotId,
                relation.RowName
              )
            ),
            colsNotId.map(colsNotId => RepoMethod.Update(id, sc.Param(sc.Ident("row"), relation.RowName, None), colsNotId)),
            Some(insertMethod),
            maybeInsertUnsavedMethod,
            Some(RepoMethod.Delete(id))
          ).flatten
        case None =>
          List(
            Some(insertMethod),
            maybeInsertUnsavedMethod,
            Some(RepoMethod.SelectAll(relation.RowName)),
            Some(RepoMethod.SelectByFieldValues(fieldValueOrIdsParam, relation.RowName))
          ).flatten
      },
      dbTable.uniqueKeys
        .map { uk =>
          val params = uk.cols.map(colName => cols.find(_.dbName == colName).get)
          RepoMethod.SelectByUnique(params, relation.RowName)
        }
        .distinctByCompat(x => x.params.map(_.tpe)) // avoid erasure clashes
    )
    NonEmptyList.fromList(maybeMethods.flatten)
  }
}

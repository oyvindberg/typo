package typo

import typo.internal.rewriteDependentData.Eval

case class TableComputed(
    options: Options,
    default: DefaultComputed,
    dbTable: db.Table,
    scalaTypeMapper: TypeMapperScala,
    eval: Eval[db.RelationName, Either[ViewComputed, TableComputed]]
) {
  val pointsTo: Map[db.ColName, (db.RelationName, db.ColName)] =
    dbTable.foreignKeys.flatMap(fk => fk.cols.zip(fk.otherCols.map(cn => (fk.otherTable, cn)))).toMap
  val dbColsByName: Map[db.ColName, db.Col] =
    dbTable.cols.map(col => (col.name, col)).toMap

  val maybeId: Option[IdComputed] =
    dbTable.primaryKey.flatMap { pk =>
      val qident = names.titleCase(options.pkg, dbTable.name, "Id")
      pk.colNames match {
        case Nil => None
        case colName :: Nil =>
          val dbCol = dbColsByName(colName)
          val underlying = scalaTypeMapper(Right(dbTable.name), dbCol, None)
          val col = ColumnComputed(
            pointsTo = pointsTo.get(dbCol.name),
            name = names.field(dbCol.name),
            tpe = underlying,
            dbName = dbCol.name,
            hasDefault = dbCol.hasDefault,
            jsonDescription = dbCol.jsonDescription
          )
          if (sc.Type.containsUserDefined(underlying))
            Some(IdComputed.UnaryUserSpecified(col, underlying))
          else
            Some(IdComputed.UnaryNormal(col, qident))

        case colNames =>
          val cols: List[ColumnComputed] =
            colNames.map { colName =>
              val dbCol = dbColsByName(colName)
              ColumnComputed(
                pointsTo = None,
                name = names.field(colName),
                tpe = deriveType(dbCol),
                dbName = dbCol.name,
                hasDefault = dbCol.hasDefault,
                jsonDescription = dbCol.jsonDescription
              )
            }
          Some(IdComputed.Composite(cols, qident, paramName = sc.Ident("compositeId")))
      }
    }

  val dbColsAndCols: List[(db.Col, ColumnComputed)] = {
    dbTable.cols.map { dbCol =>
      val tpe = deriveType(dbCol)

      val computed = ColumnComputed(
        pointsTo = pointsTo.get(dbCol.name),
        name = names.field(dbCol.name),
        tpe = tpe,
        dbName = dbCol.name,
        hasDefault = dbCol.hasDefault,
        jsonDescription = dbCol.jsonDescription
      )

      dbCol -> computed
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

    val tpe = scalaTypeMapper(Right(dbTable.name), dbCol, typeFromFk.orElse(typeFromId))

    tpe
  }

  val cols: List[ColumnComputed] = dbColsAndCols.map { case (_, col) => col }

  val relation = RelationComputed(options.pkg, dbTable.name, cols, maybeId)

  val colsUnsaved: Option[List[ColumnComputed]] = maybeId
    .map { _ =>
      dbColsAndCols
        .filterNot { case (_, col) => dbTable.primaryKey.exists(_.colNames.contains(col.dbName)) }
        .map { case (dbCol, col) =>
          val newType = if (dbCol.hasDefault) sc.Type.TApply(default.DefaultedType, List(col.tpe)) else col.tpe
          col.copy(tpe = newType)
        }
    }
    .filter(_.nonEmpty)

  val RowUnsavedName: Option[sc.QIdent] =
    if (colsUnsaved.nonEmpty) Some(names.titleCase(options.pkg, dbTable.name, "RowUnsaved")) else None

  val RowJoined: Option[RowJoinedComputed] =
    if (dbTable.foreignKeys.nonEmpty) {
      val name = names.titleCase(options.pkg, dbTable.name, "JoinedRow")

      val maybeParams = dbTable.foreignKeys.foldLeft(Right(Nil): Either[Unit, List[RowJoinedComputed.Param]]) {
        case (left @ Left(_), _) => left
        case (Right(acc), fk) =>
          val paramName = names.camelCase(fk.cols.map(names.camelCase).map(_.value).toArray)
          eval(fk.otherTable).get match {
            case Some(Right(nonCircularTable)) =>
              val tpe = sc.Type.Qualified(nonCircularTable.relation.RowName)
              val fkContainsNullableRow = fk.cols.exists { colName =>
                dbColsByName(colName).nullability != Nullability.NoNulls
              }
              val tpeWithNullability = if (fkContainsNullableRow) sc.Type.Option.of(tpe) else tpe
              Right(RowJoinedComputed.Param(paramName, tpeWithNullability, isOptional = fkContainsNullableRow, table = nonCircularTable) :: acc)
            case None => Left(())
          }
      }
      maybeParams match {
        case Left(()) =>
          None
        case Right(params) =>
          val thisParam = RowJoinedComputed.Param(sc.Ident("value"), sc.Type.Qualified(relation.RowName), isOptional = false, table = this)
          Some(RowJoinedComputed(name, thisParam :: params))
      }

    } else None

  val repoMethods: Option[List[RepoMethod]] = {
    val RowType = sc.Type.Qualified(relation.RowName)

    val fieldValuesParam = sc.Param(
      sc.Ident("fieldValues"),
      sc.Type.List.of(sc.Type.Qualified(relation.FieldValueName).of(sc.Type.Wildcard))
    )

    val maybeMethods = List(
      maybeId match {
        case Some(id) =>
          val updateMethod = RowUnsavedName.zip(colsUnsaved).map { case (unsaved, colsUnsaved) =>
            val unsavedParam = sc.Param(sc.Ident("unsaved"), sc.Type.Qualified(unsaved))

            if (id.cols.forall(_.hasDefault))
              RepoMethod.InsertDbGeneratedKey(id, colsUnsaved, unsavedParam, default)
            else
              RepoMethod.InsertProvidedKey(id, colsUnsaved, unsavedParam, default)
          }

          List(
            Some(RepoMethod.SelectAll(RowType)),
            Some(RepoMethod.SelectById(id, RowType)),
            id match {
              case unary: IdComputed.Unary =>
                Some(RepoMethod.SelectAllByIds(unary, sc.Param(id.paramName.appended("s"), sc.Type.List.of(id.tpe)), RowType))
              case IdComputed.Composite(_, _, _) =>
                None
            },
            Some(RepoMethod.SelectByFieldValues(fieldValuesParam, RowType)),
            Some(RepoMethod.UpdateFieldValues(id, fieldValuesParam)),
            updateMethod,
            Some(RepoMethod.Delete(id))
          ).flatten
        case None =>
          List(
            RepoMethod.SelectAll(RowType),
            RepoMethod.SelectByFieldValues(fieldValuesParam, RowType)
          )
      },
      dbTable.uniqueKeys.map { uk =>
        val params = uk.cols.map(colName => cols.find(_.dbName == colName).get)
        RepoMethod.SelectByUnique(params, RowType)
      }
    )
    Some(maybeMethods.flatten).filter(_.nonEmpty)
  }
}

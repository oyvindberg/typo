package typo
package internal

import typo.internal.codegen.*

class FkAnalysis(table: ComputedTable, candidateFks: List[FkAnalysis.CandidateFk]) {
  lazy val createWithFkIdsRow: Option[FkAnalysis.CreateWithFkIds] =
    FkAnalysis.CreateWithFkIds.compute(candidateFks, table.cols)
  lazy val createWithFkIdsUnsavedRow: Option[FkAnalysis.CreateWithFkIds] =
    table.maybeUnsavedRow.flatMap(unsaved => FkAnalysis.CreateWithFkIds.compute(candidateFks, unsaved.allCols))
  lazy val createWithFkIdsUnsavedRowOrRow: Option[FkAnalysis.CreateWithFkIds] =
    createWithFkIdsUnsavedRow.orElse(createWithFkIdsRow)

  lazy val createWithFkIdsId: Option[FkAnalysis.CreateWithFkIds] =
    table.maybeId match {
      case Some(id: IdComputed.Composite) => FkAnalysis.CreateWithFkIds.compute(candidateFks, id.cols)
      case _                              => None
    }

  lazy val extractFksIdsFromRow: List[FkAnalysis.ExtractFkId] =
    FkAnalysis.ExtractFkId.compute(candidateFks, table.cols)
  lazy val extractFksIdsFromId: List[FkAnalysis.ExtractFkId] =
    table.maybeId match {
      case Some(id: IdComputed.Composite) => FkAnalysis.ExtractFkId.compute(candidateFks, id.cols)
      case _                              => Nil
    }
  lazy val extractFksIdsFromRowNotId: List[FkAnalysis.ExtractFkId] =
    extractFksIdsFromRow.filterNot(x => extractFksIdsFromId.exists(_.name == x.name))
}

object FkAnalysis {
  def apply(tablesByName: Map[db.RelationName, ComputedTable], table: ComputedTable): FkAnalysis =
    new FkAnalysis(table, CandidateFk.of(tablesByName, table.dbTable))

  /** All columns in a table. some can be extracted from composite ID types from other tables, some need to specified
    * @param byFks
    *   the columns which can be extracted from a composite ID type from another table
    * @param remainingColumns
    *   those which can not be extracted from any of the composite fk types
    */
  case class CreateWithFkIds(byFks: NonEmptyList[ColsFromFk], remainingColumns: List[ComputedColumn], allColumns: NonEmptyList[ComputedColumn]) {

    /** a given column may appear in more than one foreign key value extraction expression */
    lazy val exprsForColumn: Map[sc.Ident, List[sc.Code]] =
      byFks.toList
        .flatMap(colsFromFk => colsFromFk.colPairs.map { case (_, col) => (col.name, colsFromFk.expr(col.name)) })
        .groupMap { case (colName, _) => colName } { case (_, expr) => expr }

    /** reduce the potentially multiple values in [[exprsForColumn]] down to one expression, with `require` to assert that the others contain the same value */
    lazy val exprForColumn: Map[sc.Ident, sc.Code] =
      exprsForColumn.map { case (colName, exprs) =>
        exprs match {
          case Nil        => sys.error("unexpected")
          case List(expr) => (colName, expr)
          case expr :: exprs =>
            val requires = exprs.map(e => code"""require($expr == $e, "${expr.render.lines.mkString("\n")} != ${e.render.lines.mkString("\n")}")""")
            val finalExpr = code"""|{
                                 |  ${requires.mkCode("\n")}
                                 |  $expr
                                 |}""".stripMargin
            (colName, finalExpr)
        }
      }

    /** parameters corresponding to all the foreign key types */
    lazy val params: List[sc.Param] =
      byFks.map(_.param).toList

    /** if you create a method with these parameters, you get all the data. */
    lazy val allParams: List[sc.Param] =
      params ++ remainingColumns.map(_.param)

    /** and this contains matching expressions for all the values in [[allParams]] */
    lazy val allExpr: List[(sc.Ident, sc.Code)] =
      allColumns.toList.map { col =>
        val expr = exprForColumn.getOrElse(col.name, col.name.code)
        (col.name, expr)
      }
  }

  object CreateWithFkIds {

    /** Compute a minimal set of composite FK IDs to cover maximum number of columns in table */
    def compute(candidateFks: List[CandidateFk], thisOriginalColumns: NonEmptyList[ComputedColumn]): Option[CreateWithFkIds] = {
      // state
      var remainingThisCols: List[ComputedColumn] = thisOriginalColumns.toList
      var byFk = List.empty[(db.ForeignKey, ColsFromFk)]

      candidateFks
        // consider longest first, plus disambiguate for consistency
        .sortBy(candidate => (-candidate.thisFk.cols.countWhere(colName => thisOriginalColumns.exists(_.dbName == colName)), candidate.thisFk.constraintName))
        .foreach { candidateFk =>
          // check that all cols in fk are not covered by other fks we already consumed
          remainingThisCols.partition(col => candidateFk.thisFk.cols.contains(col.dbName)) match {
            case (consumedNewCols, rest) if consumedNewCols.nonEmpty =>
              val affectedThisCols = reshuffle(candidateFk, thisOriginalColumns)
              byFk = (candidateFk.thisFk, ColsFromFk(candidateFk.otherId, affectedThisCols)) :: byFk
              remainingThisCols = rest
            case _ => ()
          }
        }

      NonEmptyList.fromList(ColsFromFk.renamed(byFk)).map(x => CreateWithFkIds(x, remainingThisCols, thisOriginalColumns))
    }
  }

  /** A composite ID type from another table, and the columns in this table which can be extracted¬ */
  case class ColsFromFk(otherCompositeId: IdComputed.Composite, thisColumns: List[ComputedColumn]) {
    def param: sc.Param =
      otherCompositeId.param
    def withParamName(name: sc.Ident): ColsFromFk =
      copy(otherCompositeId = otherCompositeId.copy(paramName = name))
    lazy val colPairs: List[(ComputedColumn, ComputedColumn)] =
      otherCompositeId.cols.toList.zip(thisColumns)
    lazy val expr: Map[sc.Ident, sc.Code] =
      colPairs.map { case (fromId, col) => (col.name, code"${param.name}.${fromId.name}") }.toMap
  }

  object ColsFromFk {
    // one table may appear in more than one fk, give ugly name when they do
    def renamed(byFk: List[(db.ForeignKey, ColsFromFk)]): List[ColsFromFk] =
      byFk
        .groupBy { case (_, colsFromFk) => colsFromFk.otherCompositeId.tpe.name }
        .valuesIterator
        .flatMap {
          case List((_, colsFromFk)) => List(colsFromFk.withParamName(colsFromFk.otherCompositeId.tpe.name))
          case more                  => more.map { case (fk, colsFromFk) => colsFromFk.withParamName(sc.Ident(fk.constraintName.name)) }
        }
        .toList
        .sortBy(_.otherCompositeId.paramName)
  }

  /** A composite ID type from another table, and the columns in this table which can be extracted¬ */
  case class ExtractFkId(name: sc.Ident, otherCompositeIdType: sc.Type.Qualified, colPairs: List[(ComputedColumn, ComputedColumn)])

  object ExtractFkId {

    /** compute a minimal set of composite foreign key ids to take as parameter in order to fill all values */
    def compute(candidateFks: List[CandidateFk], thisColumns: NonEmptyList[ComputedColumn]): List[ExtractFkId] =
      candidateFks
        // keep only those where all parts are among `thisOriginalColumns` as we need to produce the fk composite id types here
        .filter(candidate => candidate.thisFk.cols.forall(colName => thisColumns.exists(_.dbName == colName)))
        .groupBy(_.otherId.tpe.name)
        .iterator
        .flatMap { case (_, candidates) =>
          val useNiceName = candidates.length == 1
          candidates.flatMap { candidateFk =>
            val affectedThisCols = reshuffle(candidateFk, thisColumns)

            // we may have an optional value on our side, and a required value on the other side. in that case we cannot produce the row
            // this catches a little bit too much, but should be close enough.
            val typesExactMatch = affectedThisCols.zip(candidateFk.otherId.cols.toList).forall { case (one, two) => one.tpe == two.tpe }

            val colPairs: List[(ComputedColumn, ComputedColumn)] =
              candidateFk.otherId.cols.toList.zip(affectedThisCols)

            if (typesExactMatch) {
              val ident = if (useNiceName) candidateFk.otherId.tpe.name else sc.Ident(candidateFk.thisFk.constraintName.name)
              Some(ExtractFkId(ident, candidateFk.otherId.tpe, colPairs))
            } else None

          }
        }
        .toList
        // consider longest first, plus disambiguate for consistency
        .sortBy(x => (-x.colPairs.length, x.name))
  }

  case class CandidateFk(thisFk: db.ForeignKey, otherTable: ComputedTable, otherId: IdComputed.Composite)

  object CandidateFk {
    // fitting foreign keys
    def of(tablesByName: Map[db.RelationName, ComputedTable], dbTable: db.Table): List[CandidateFk] =
      for {
        fk <- dbTable.foreignKeys
        if fk.otherTable != dbTable.name
        if fk.cols.length > 1
        otherTable <- tablesByName.get(fk.otherTable)
        // we're only interested if pk matches, because that means we have a composite ID type to use
        _ <- otherTable.dbTable.primaryKey.filter(pk => pk.colNames == fk.otherCols)
        compositePk <- otherTable.maybeId.collect { case x: IdComputed.Composite => x.copy(paramName = sc.Ident(fk.constraintName.name)) }
      } yield CandidateFk(fk, otherTable, compositePk)
  }

  // pick out all columns included fk. fix ordering to account for different column names in the two relations
  def reshuffle(candidateFk: CandidateFk, thisOriginalColumns: NonEmptyList[ComputedColumn]): List[ComputedColumn] =
    thisOriginalColumns.toList
      .filter(col => candidateFk.thisFk.cols.contains(col.dbName))
      .sortBy(thisCol => candidateFk.thisFk.cols.toList.indexWhere(_ == thisCol.dbCol.name))
}

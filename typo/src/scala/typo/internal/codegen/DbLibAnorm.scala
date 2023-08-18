package typo
package internal
package codegen

import typo.internal.sqlfiles.MaybeReturnsRows

class DbLibAnorm(pkg: sc.QIdent, inlineImplicits: Boolean) extends DbLib {
  val Column = sc.Type.Qualified("anorm.Column")
  val ToStatement = sc.Type.Qualified("anorm.ToStatement")
  val ToSql = sc.Type.Qualified("anorm.ToSql")
  val NamedParameter = sc.Type.Qualified("anorm.NamedParameter")
  val ParameterValue = sc.Type.Qualified("anorm.ParameterValue")
  val RowParser = sc.Type.Qualified("anorm.RowParser")
  val Success = sc.Type.Qualified("anorm.Success")
  val SqlMappingError = sc.Type.Qualified("anorm.SqlMappingError")
  val SqlStringInterpolation = sc.Type.Qualified("anorm.SqlStringInterpolation")
  val ParameterMetaData = sc.Type.Qualified("anorm.ParameterMetaData")
  val SQL = sc.Type.Qualified("anorm.SQL")
  val TypeDoesNotMatch = sc.Type.Qualified("anorm.TypeDoesNotMatch")
  val SimpleSql = sc.Type.Qualified("anorm.SimpleSql")
  val Row = sc.Type.Qualified("anorm.Row")

  def rowParserFor(rowType: sc.Type) = code"$rowType.$rowParserName(1)"

  def SQL(content: sc.Code) =
    sc.StringInterpolate(SqlStringInterpolation, sc.Ident("SQL"), content)

  val arrayColumnName = sc.Ident("arrayColumn")
  val arrayToStatementName: sc.Ident = sc.Ident("arrayToStatement")
  val columnName: sc.Ident = sc.Ident("column")
  val parameterMetadataName: sc.Ident = sc.Ident("parameterMetadata")
  val rowParserName: sc.Ident = sc.Ident("rowParser")
  val toStatementName: sc.Ident = sc.Ident("toStatement")

  def dbNames(cols: NonEmptyList[ComputedColumn], isRead: Boolean): sc.Code =
    cols
      .map(c => maybeQuoted(c.dbName) ++ (if (isRead) sqlCast.fromPgCode(c) else sc.Code.Empty))
      .mkCode(", ")

  def matchId(id: IdComputed): sc.Code =
    id match {
      case id: IdComputed.Unary =>
        code"${maybeQuoted(id.col.dbName)} = $$${id.paramName}"
      case composite: IdComputed.Composite =>
        code"${composite.cols.map(cc => code"${maybeQuoted(cc.dbName)} = $${${composite.paramName}.${cc.name}}").mkCode(" AND ")}"
    }

  def matchAnyId(x: IdComputed.Unary, idsParam: sc.Param): sc.Code =
    code"${maybeQuoted(x.col.dbName)} = ANY($$${idsParam.name})"

  /** Resolve known implicits at generation-time instead of at compile-time */
  def lookupColumnFor(tpe: sc.Type): sc.Code =
    if (!inlineImplicits) Column.of(tpe).code
    else
      sc.Type.base(tpe) match {
        case sc.Type.BigDecimal     => code"$Column.columnToScalaBigDecimal"
        case sc.Type.Boolean        => code"$Column.columnToBoolean"
        case sc.Type.Byte           => code"$Column.columnToByte"
        case sc.Type.Double         => code"$Column.columnToDouble"
        case sc.Type.Float          => code"$Column.columnToFloat"
        case sc.Type.Int            => code"$Column.columnToInt"
        case sc.Type.Long           => code"$Column.columnToLong"
        case sc.Type.String         => code"$Column.columnToString"
        case sc.Type.UUID           => code"$Column.columnToUUID"
        case sc.Type.Optional(targ) => code"$Column.columnToOption(${lookupColumnFor(targ)})"
        // generated type
        case x: sc.Type.Qualified if x.value.idents.startsWith(pkg.idents) =>
          code"$tpe.$columnName"
        // customized type mapping
        case x if missingInstancesByType.contains(Column.of(x)) =>
          code"${missingInstancesByType(Column.of(x))}"
        // generated array type
        case sc.Type.TApply(sc.Type.Array, List(targ: sc.Type.Qualified)) if targ.value.idents.startsWith(pkg.idents) =>
          code"$targ.$arrayColumnName"
        case sc.Type.TApply(sc.Type.Array, List(sc.Type.Byte)) => code"$Column.columnToByteArray"
        // fallback array case. implementation looks loco, but I guess it works
        case sc.Type.TApply(sc.Type.Array, List(targ)) => code"$Column.columnToArray[$targ](${lookupColumnFor(targ)}, implicitly)"
        case other =>
          sc.Summon(Column.of(other)).code
      }

  override def repoSig(repoMethod: RepoMethod): sc.Code = repoMethod match {
    case RepoMethod.SelectBuilder(_, fieldsType, rowType) =>
      code"def select: ${sc.Type.dsl.SelectBuilder.of(fieldsType, rowType)}"
    case RepoMethod.SelectAll(_, _, rowType) =>
      code"def selectAll(implicit c: ${sc.Type.Connection}): ${sc.Type.List.of(rowType)}"
    case RepoMethod.SelectById(_, _, id, rowType) =>
      code"def selectById(${id.param})(implicit c: ${sc.Type.Connection}): ${sc.Type.Option.of(rowType)}"
    case RepoMethod.SelectAllByIds(_, _, unaryId, idsParam, rowType) =>
      unaryId match {
        case IdComputed.UnaryUserSpecified(_, tpe) =>
          code"def selectByIds($idsParam)(implicit c: ${sc.Type.Connection}, toStatement: ${ToStatement.of(sc.Type.Array.of(tpe))}): ${sc.Type.List.of(rowType)}"
        case _ =>
          code"def selectByIds($idsParam)(implicit c: ${sc.Type.Connection}): ${sc.Type.List.of(rowType)}"
      }
    case RepoMethod.SelectByUnique(params, _, rowType) =>
      val ident = Naming.camelCaseIdent(Array("selectByUnique"))
      code"def $ident(${params.map(_.param.code).mkCode(", ")})(implicit c: ${sc.Type.Connection}): ${sc.Type.Option.of(rowType)}"
    case RepoMethod.SelectByFieldValues(_, _, _, fieldValueOrIdsParam, rowType) =>
      code"def selectByFieldValues($fieldValueOrIdsParam)(implicit c: ${sc.Type.Connection}): ${sc.Type.List.of(rowType)}"
    case RepoMethod.UpdateBuilder(_, fieldsType, rowType) =>
      code"def update: ${sc.Type.dsl.UpdateBuilder.of(fieldsType, rowType)}"
    case RepoMethod.UpdateFieldValues(_, id, varargs, _, _, _) =>
      code"def updateFieldValues(${id.param}, $varargs)(implicit c: ${sc.Type.Connection}): ${sc.Type.Boolean}"
    case RepoMethod.Update(_, _, _, param, _) =>
      code"def update($param)(implicit c: ${sc.Type.Connection}): ${sc.Type.Boolean}"
    case RepoMethod.Insert(_, _, unsavedParam, rowType) =>
      code"def insert($unsavedParam)(implicit c: ${sc.Type.Connection}): $rowType"
    case RepoMethod.Upsert(_, _, _, unsavedParam, rowType) =>
      code"def upsert($unsavedParam)(implicit c: ${sc.Type.Connection}): $rowType"
    case RepoMethod.InsertUnsaved(_, _, _, unsavedParam, _, rowType) =>
      code"def insert($unsavedParam)(implicit c: ${sc.Type.Connection}): $rowType"
    case RepoMethod.DeleteBuilder(_, fieldsType, rowType) =>
      code"def delete: ${sc.Type.dsl.DeleteBuilder.of(fieldsType, rowType)}"
    case RepoMethod.Delete(_, id) =>
      code"def delete(${id.param})(implicit c: ${sc.Type.Connection}): ${sc.Type.Boolean}"
    case RepoMethod.SqlFile(sqlScript) =>
      val params = sc.Params(sqlScript.nullableParams.map(p => sc.Param(p.name, p.tpe, None)))
      val retType = sqlScript.maybeRowName match {
        case MaybeReturnsRows.Query(rowName) => sc.Type.List.of(rowName)
        case MaybeReturnsRows.Update         => sc.Type.Int
      }
      code"def opt$params(implicit c: ${sc.Type.Connection}): $retType"
    case RepoMethod.SqlFileRequiredParams(sqlScript) =>
      val params = sc.Params(sqlScript.params.map(param => sc.Param(param.name, param.tpe, None)))
      val retType = sqlScript.maybeRowName match {
        case MaybeReturnsRows.Query(rowName) => sc.Type.List.of(rowName)
        case MaybeReturnsRows.Update         => sc.Type.Int
      }
      code"def apply$params(implicit c: ${sc.Type.Connection}): $retType"
  }

  override def repoFinalImpl(repoMethod: RepoMethod.Final): sc.Code = repoMethod match {
    case RepoMethod.SqlFileRequiredParams(sqlScript) =>
      code"opt(${sqlScript.params.map(p => code"${sc.Type.Option}(${p.name})").mkCode(", ")})"
  }

  override def repoVirtualImpl(repoMethod: RepoMethod.Virtual): sc.Code =
    repoMethod match {
      case RepoMethod.SelectBuilder(relName, fieldsType, rowType) =>
        code"""${sc.Type.dsl.SelectBuilderSql}(${sc.StrLit(relName.value)}, $fieldsType, $rowType.rowParser)"""
      case RepoMethod.SelectAll(relName, cols, rowType) =>
        val sql = SQL {
          code"""|select ${dbNames(cols, isRead = true)}
                 |from $relName
                 |""".stripMargin
        }
        code"""$sql.as(${rowParserFor(rowType)}.*)"""

      case RepoMethod.SelectById(relName, cols, id, rowType) =>
        val sql = SQL {
          code"""|select ${dbNames(cols, isRead = true)}
                 |from $relName
                 |where ${matchId(id)}
                 |""".stripMargin
        }
        code"""$sql.as(${rowParserFor(rowType)}.singleOpt)"""

      case RepoMethod.SelectAllByIds(relName, cols, unaryId, idsParam, rowType) =>
        val sql = SQL {
          code"""|select ${dbNames(cols, isRead = true)}
                 |from $relName
                 |where ${matchAnyId(unaryId, idsParam)}
                 |""".stripMargin
        }

        code"""|$sql.as(${rowParserFor(rowType)}.*)
               |""".stripMargin

      case RepoMethod.UpdateBuilder(relName, fieldsType, rowType) =>
        code"${sc.Type.dsl.UpdateBuilder}(${sc.StrLit(relName.value)}, $fieldsType, $rowType.rowParser)"

      case RepoMethod.SelectByUnique(params, fieldValue, _) =>
        val args = params.map { param =>
          code"$fieldValue.${param.name}(${param.name})"
        }
        code"""selectByFieldValues(${sc.Type.List}(${args.mkCode(", ")})).headOption"""

      case RepoMethod.SelectByFieldValues(relName, cols, fieldValue, fieldValueOrIdsParam, rowType) =>
        val cases: NonEmptyList[sc.Code] =
          cols.map { col =>
            code"case $fieldValue.${col.name}(value) => $NamedParameter(${sc.StrLit(col.dbName.value)}, $ParameterValue.from(value))"
          }

        val sql = sc.s {
          code"""|select ${dbNames(cols, isRead = true)}
                 |from $relName
                 |where $${namedParams.map(x => s"$$quote$${x.name}$$quote = {$${x.name}}").mkString(" AND ")}
                 |""".stripMargin
        }
        // the weird block and wildcard import is to avoid warnings in scala 2 and 3, and to get the implicit `on` in scala 3
        code"""${fieldValueOrIdsParam.name} match {
              |  case Nil => selectAll
              |  case nonEmpty =>
              |    val namedParams = nonEmpty.map{
              |      ${cases.mkCode("\n")}
              |    }
              |    val quote = '"'.toString
              |    val q = $sql
              |    $SimpleSql($SQL(q), namedParameters.map { case (np, _) => np.tupled }.toMap, $RowParser($Success(_)))
              |      .as(${rowParserFor(rowType)}.*)
              |}
              |""".stripMargin

      case RepoMethod.UpdateFieldValues(relName, id, varargs, fieldValue, cases0, _) =>
        val cases: NonEmptyList[sc.Code] =
          cases0.map { col =>
            code"case $fieldValue.${col.name}(value) => $NamedParameter(${sc.StrLit(col.dbName.value)}, $ParameterValue.from(value))"
          }
        val where: sc.Code =
          id.cols.map { col => code"${maybeQuoted(col.dbName)} = {${col.name}}" }.mkCode(" AND ")

        val idCases: NonEmptyList[sc.Code] =
          id match {
            case unary: IdComputed.Unary =>
              NonEmptyList(code"(${sc.StrLit(unary.col.dbName.value)}, $ParameterValue.from(${id.paramName}))")
            case IdComputed.Composite(cols, _, paramName) =>
              cols.map { col =>
                code"(${sc.StrLit(col.dbName.value)}, $ParameterValue.from($paramName.${col.name}))"
              }
          }

        val sql = sc.s {
          code"""update $relName
                |set $${namedParams.map(x => s"$$quote$${x.name}$$quote = {$${x.name}}").mkString(", ")}
                |where $where
                |""".stripMargin
        }

        // the weird block and wildcard import is to avoid warnings in scala 2 and 3, and to get the implicit `on` in scala 3
        code"""${varargs.name} match {
              |  case Nil => false
              |  case nonEmpty =>
              |    val namedParams = nonEmpty.map{
              |      ${cases.mkCode("\n")}
              |    }
              |    val quote = '"'.toString
              |    val q = $sql
              |    $SimpleSql($SQL(q), namedParameters.map { case (np, _) => np.tupled }.toMap ++ ${sc.Type.List}(${idCases.mkCode(", ")}), $RowParser($Success(_)))
              |      .executeUpdate() > 0
              |}
              |""".stripMargin
      case RepoMethod.Update(relName, _, id, param, colsUnsaved) =>
        val sql = SQL {
          code"""|update $relName
                 |set ${colsUnsaved.map { col => code"${maybeQuoted(col.dbName)} = $${${param.name}.${col.name}}${sqlCast.toPgCode(col)}" }.mkCode(",\n")}
                 |where ${matchId(id)}
                 |""".stripMargin
        }
        code"""|val ${id.paramName} = ${param.name}.${id.paramName}
               |$sql.executeUpdate() > 0"""

      case RepoMethod.Insert(relName, cols, unsavedParam, rowType) =>
        val values = cols.map { c =>
          code"$${${unsavedParam.name}.${c.name}}${sqlCast.toPgCode(c)}"
        }
        val sql = SQL {
          code"""|insert into $relName(${dbNames(cols, isRead = false)})
                 |values (${values.mkCode(", ")})
                 |returning ${dbNames(cols, isRead = true)}
                 |""".stripMargin
        }

        code"""|$sql
               |  .executeInsert(${rowParserFor(rowType)}.single)
               |"""
      case RepoMethod.Upsert(relName, cols, id, unsavedParam, rowType) =>
        val values = cols.map { c =>
          code"$${${unsavedParam.name}.${c.name}}${sqlCast.toPgCode(c)}"
        }

        val pickExcludedCols = cols.toList
          .filterNot(c => id.cols.exists(_.name == c.name))
          .map { c => code"${maybeQuoted(c.dbName)} = EXCLUDED.${maybeQuoted(c.dbName)}" }

        val sql = SQL {
          code"""|insert into $relName(${dbNames(cols, isRead = false)})
                 |values (
                 |  ${values.mkCode(",\n")}
                 |)
                 |on conflict (${dbNames(id.cols, isRead = false)})
                 |do update set
                 |  ${pickExcludedCols.mkCode(",\n")}
                 |returning ${dbNames(cols, isRead = true)}
                 |""".stripMargin
        }

        code"""|$sql
               |  .executeInsert(${rowParserFor(rowType)}.single)
               |"""

      case RepoMethod.InsertUnsaved(relName, cols, unsaved, unsavedParam, default, rowType) =>
        val cases0 = unsaved.restCols.map { col =>
          val colCast = sc.StrLit(sqlCast.toPgCode(col).render.asString)
          code"""Some(($NamedParameter(${sc.StrLit(col.dbName.value)}, $ParameterValue.from(${unsavedParam.name}.${col.name})), $colCast))"""
        }
        val cases1 = unsaved.defaultCols.map { case (col @ ComputedColumn(_, ident, _, dbCol), origType) =>
          val dbName = sc.StrLit(dbCol.name.value)
          val colCast = sc.StrLit(sqlCast.toPgCode(col).render.asString)

          code"""|${unsavedParam.name}.$ident match {
                   |  case ${default.Defaulted}.${default.UseDefault} => None
                   |  case ${default.Defaulted}.${default.Provided}(value) => Some(($NamedParameter($dbName, $ParameterValue.from[$origType](value)), $colCast))
                   |}"""
        }

        val sql = sc.s {
          code"""|insert into $relName($${namedParameters.map{case (x, _) => quote + x.name + quote}.mkString(", ")})
                 |values ($${namedParameters.map{ case (np, cast) => s"{$${np.name}}$$cast"}.mkString(", ")})
                 |returning ${dbNames(cols, isRead = true)}
                 |""".stripMargin
        }
        val sqlEmpty = SQL {
          code"""|insert into $relName default values
                 |returning ${dbNames(cols, isRead = true)}
                 |""".stripMargin
        }

        // the weird block and wildcard import is to avoid warnings in scala 2 and 3, and to get the implicit `on` in scala 3
        code"""|val namedParameters = List(
               |  ${(cases0 ++ cases1.toList).mkCode(",\n")}
               |).flatten
               |val quote = '"'.toString
               |if (namedParameters.isEmpty) {
               |  $sqlEmpty
               |    .executeInsert(${rowParserFor(rowType)}.single)
               |} else {
               |  val q = $sql
               |  $SimpleSql($SQL(q), namedParameters.map { case (np, _) => np.tupled }.toMap, $RowParser($Success(_)))
               |    .executeInsert(${rowParserFor(rowType)}.single)
               |}
               |"""

      case RepoMethod.DeleteBuilder(relName, fieldsType, _) =>
        code"${sc.Type.dsl.DeleteBuilder}(${sc.StrLit(relName.value)}, $fieldsType)"
      case RepoMethod.Delete(relName, id) =>
        val sql = SQL {
          code"""delete from $relName where ${matchId(id)}"""
        }
        code"$sql.executeUpdate() > 0"

      case RepoMethod.SqlFile(sqlScript) =>
        val renderedScript: sc.Code = sqlScript.sqlFile.decomposedSql.render { (paramAtIndex: Int) =>
          val param = sqlScript.nullableParams.find(_.underlying.indices.contains(paramAtIndex)).get
          val cast = sqlCast.toPg(param.underlying).fold("")(udtType => s"::$udtType")
          s"$$${param.name.value}$cast"
        }
        val ret = for {
          cols <- sqlScript.maybeCols.toOption
          rowName <- sqlScript.maybeRowName.toOption
        } yield {
          // this is necessary to make custom types work with sql scripts, unfortunately.
          val renderedWithCasts: sc.Code =
            cols.toList.flatMap(c => sqlCast.fromPg(c.dbCol)) match {
              case Nil => renderedScript.code
              case _ =>
                val row = sc.Ident("row")

                code"""|with $row as (
                       |  $renderedScript
                       |)
                       |select ${cols.map(c => code"$row.${maybeQuoted(c.dbName)}${sqlCast.fromPgCode(c)}").mkCode(", ")}
                       |from $row""".stripMargin
            }

          code"""|val sql =
                 |  ${SQL(renderedWithCasts)}
                 |sql.as(${rowParserFor(rowName)}.*)""".stripMargin
        }

        ret.getOrElse {
          code"${SQL(renderedScript)}.executeUpdate()"
        }
    }

  override def mockVirtualRepoImpl(id: IdComputed, repoMethod: RepoMethod.Virtual, maybeToRow: Option[sc.Param]): sc.Code = {
    repoMethod match {
      case RepoMethod.SelectBuilder(_, fieldsType, _) =>
        code"${sc.Type.dsl.SelectBuilderMock}($fieldsType, () => map.values.toList, ${sc.Type.dsl.SelectParams}.empty)"
      case RepoMethod.SelectAll(_, _, _) =>
        code"map.values.toList"
      case RepoMethod.SelectById(_, _, id, _) =>
        code"map.get(${id.paramName})"
      case RepoMethod.SelectAllByIds(_, _, _, idsParam, _) =>
        code"${idsParam.name}.flatMap(map.get).toList"
      case RepoMethod.SelectByUnique(params, fieldValue, _) =>
        val args = params.map { param =>
          code"$fieldValue.${param.name}(${param.name})"
        }
        code"""selectByFieldValues(${sc.Type.List}(${args.mkCode(", ")})).headOption"""

      case RepoMethod.SelectByFieldValues(_, cols, fieldValue, fieldValueOrIdsParam, _) =>
        val cases = cols.map { col =>
          code"case (acc, $fieldValue.${col.name}(value)) => acc.filter(_.${col.name} == value)"
        }
        code"""${fieldValueOrIdsParam.name}.foldLeft(map.values) {
              |  ${cases.mkCode("\n")}
              |}.toList""".stripMargin
      case RepoMethod.UpdateFieldValues(_, _, varargs, fieldValue, cases0, _) =>
        val cases = cases0.map { col =>
          code"case (acc, $fieldValue.${col.name}(value)) => acc.copy(${col.name} = value)"
        }

        code"""|map.get(${id.paramName}) match {
               |  case ${sc.Type.Some}(oldRow) =>
               |    val updatedRow = ${varargs.name}.foldLeft(oldRow) {
               |      ${cases.mkCode("\n")}
               |    }
               |    if (updatedRow != oldRow) {
               |      map.put(${id.paramName}, updatedRow)
               |      true
               |    } else {
               |      false
               |    }
               |  case ${sc.Type.None} => false
               |}""".stripMargin
      case RepoMethod.UpdateBuilder(_, fieldsType, _) =>
        code"${sc.Type.dsl.UpdateBuilderMock}(${sc.Type.dsl.UpdateParams}.empty, $fieldsType, map)"
      case RepoMethod.Update(_, _, _, param, _) =>
        code"""map.get(${param.name}.${id.paramName}) match {
              |  case ${sc.Type.Some}(`${param.name}`) => false
              |  case ${sc.Type.Some}(_) =>
              |    map.put(${param.name}.${id.paramName}, ${param.name})
              |    true
              |  case ${sc.Type.None} => false
              |}""".stripMargin
      case RepoMethod.Insert(_, _, unsavedParam, _) =>
        code"""|if (map.contains(${unsavedParam.name}.${id.paramName}))
               |  sys.error(s"id $${${unsavedParam.name}.${id.paramName}} already exists")
               |else
               |  map.put(${unsavedParam.name}.${id.paramName}, ${unsavedParam.name})
               |${unsavedParam.name}"""
      case RepoMethod.Upsert(_, _, _, unsavedParam, _) =>
        code"""|map.put(${unsavedParam.name}.${id.paramName}, ${unsavedParam.name})
               |${unsavedParam.name}"""
      case RepoMethod.InsertUnsaved(_, _, _, unsavedParam, _, _) =>
        code"insert(${maybeToRow.get.name}(${unsavedParam.name}))"

      case RepoMethod.DeleteBuilder(_, fieldsType, _) =>
        code"${sc.Type.dsl.DeleteBuilderMock}(${sc.Type.dsl.DeleteParams}.empty, $fieldsType, map)"
      case RepoMethod.Delete(_, id) =>
        code"map.remove(${id.paramName}).isDefined"
      case RepoMethod.SqlFile(_) =>
        // should not happen (tm)
        code"???"
    }
  }
  override def testInsertMethod(x: ComputedTestInserts.InsertMethod): sc.Value =
    sc.Value(
      Nil,
      x.name,
      x.params,
      List(sc.Param(sc.Ident("c"), sc.Type.Connection, None)),
      x.table.names.RowName,
      code"${x.table.names.RepoImplName}.insert(new ${x.cls}(${x.params.map(p => code"${p.name} = ${p.name}").mkCode(", ")}))"
    )

  override def stringEnumInstances(wrapperType: sc.Type, underlying: sc.Type): List[sc.Given] =
    List(
      sc.Given(
        tparams = Nil,
        name = arrayColumnName,
        implicitParams = Nil,
        tpe = Column.of(sc.Type.Array.of(wrapperType)),
        body = code"""$Column.columnToArray($columnName, implicitly)"""
      ),
      sc.Given(
        tparams = Nil,
        name = columnName,
        implicitParams = Nil,
        tpe = Column.of(wrapperType),
        body = code"""${sc.Summon(Column.of(underlying))}.mapResult(str => $wrapperType(str).left.map($SqlMappingError.apply))"""
      ),
      sc.Given(
        tparams = Nil,
        name = toStatementName,
        implicitParams = Nil,
        tpe = ToStatement.of(wrapperType),
        body = code"${sc.Summon(ToStatement.of(underlying))}.contramap(_.value)"
      ),
      sc.Given(
        tparams = Nil,
        name = arrayToStatementName,
        implicitParams = Nil,
        tpe = ToStatement.of(sc.Type.Array.of(wrapperType)),
        body = code"${sc.Summon(ToStatement.of(sc.Type.Array.of(underlying)))}.contramap(_.map(_.value))"
      ),
      sc.Given(
        tparams = Nil,
        name = parameterMetadataName,
        implicitParams = Nil,
        tpe = ParameterMetaData.of(wrapperType),
        body = code"""|new $ParameterMetaData[$wrapperType] {
                 |  override def sqlType: ${sc.Type.String} = ${sc.Summon(ParameterMetaData.of(underlying))}.sqlType
                 |  override def jdbcType: ${sc.Type.Int} = ${sc.Summon(ParameterMetaData.of(underlying))}.jdbcType
                 |}""".stripMargin
      )
    )

  override def anyValInstances(wrapperType: sc.Type.Qualified, underlying: sc.Type): List[sc.Given] =
    List(
      sc.Given(
        tparams = Nil,
        name = toStatementName,
        implicitParams = Nil,
        tpe = ToStatement.of(wrapperType),
        body = code"${sc.Summon(ToStatement.of(underlying))}.contramap(_.value)"
      ),
      sc.Given(
        tparams = Nil,
        name = arrayToStatementName,
        implicitParams = Nil,
        tpe = ToStatement.of(sc.Type.Array.of(wrapperType)),
        body = code"${sc.Summon(ToStatement.of(sc.Type.Array.of(underlying)))}.contramap(_.map(_.value))"
      ),
      sc.Given(
        tparams = Nil,
        name = arrayColumnName,
        implicitParams = Nil,
        tpe = Column.of(sc.Type.Array.of(wrapperType)),
        body = code"$Column.columnToArray($columnName, implicitly)"
      ),
      sc.Given(
        tparams = Nil,
        name = columnName,
        implicitParams = Nil,
        tpe = Column.of(wrapperType),
        body = code"${sc.Summon(Column.of(underlying))}.map($wrapperType.apply)"
      ),
      sc.Given(
        tparams = Nil,
        name = parameterMetadataName,
        implicitParams = Nil,
        tpe = ParameterMetaData.of(wrapperType),
        body = code"""|new ${ParameterMetaData.of(wrapperType)} {
                 |  override def sqlType: String = ${sc.Summon(ParameterMetaData.of(underlying))}.sqlType
                 |  override def jdbcType: Int = ${sc.Summon(ParameterMetaData.of(underlying))}.jdbcType
                 |}""".stripMargin
      )
    )
  override val missingInstances: List[sc.ClassMember] = {
    val arrayInstances = List[(sc.Type.Qualified, sc.StrLit)](
      (sc.Type.Float, sc.StrLit("float4")),
      (sc.Type.Short, sc.StrLit("int2")),
      (sc.Type.Int, sc.StrLit("int4")),
      (sc.Type.Long, sc.StrLit("int8")),
      (sc.Type.Boolean, sc.StrLit("bool")),
      (sc.Type.Double, sc.StrLit("float8"))
    ).flatMap { case (tpe, elemType) =>
      val arrayType = sc.Type.Array.of(tpe)
      val boxedType = sc.Type.boxedType(tpe).getOrElse(sc.Type.AnyRef)
      List(
        sc.Given(
          Nil,
          tpe.value.name.appended("ArrayToStatement"),
          Nil,
          ToStatement.of(arrayType),
          code"${ToStatement.of(arrayType)}((ps, index, v) => ps.setArray(index, ps.getConnection.createArrayOf($elemType, v.map(v => v: $boxedType))))"
        )
      )
    }
    val bigDecimalArrayToStatement =
      sc.Given(
        Nil,
        sc.Ident("BigDecimalArrayToStatement"),
        Nil,
        ToStatement.of(sc.Type.Array.of(sc.Type.BigDecimal)),
        code"${ToStatement.of(sc.Type.Array.of(sc.Type.BigDecimal))}((ps, index, v) => ps.setArray(index, ps.getConnection.createArrayOf(${sc.StrLit("numeric")}, v.map(v => v.bigDecimal))))"
      )

    val arrayParameterMetaData = {
      val T = sc.Type.Abstract(sc.Ident("T"))
      sc.Given(
        List(T),
        sc.Ident("arrayParameterMetaData"),
        List(sc.Param(T.value, ParameterMetaData.of(T), None)),
        ParameterMetaData.of(sc.Type.Array.of(T)),
        code"""|new ${ParameterMetaData.of(sc.Type.Array.of(T))} {
               |  override def sqlType: ${sc.Type.String} = ${sc.StrLit("_")} + $T.sqlType
               |  override def jdbcType: ${sc.Type.Int} = ${sc.Type.Types}.ARRAY
               |}""".stripMargin
      )
    }

    arrayInstances ++ List(arrayParameterMetaData, bigDecimalArrayToStatement)
  }

  val missingInstancesByType: Map[sc.Type, sc.QIdent] =
    missingInstances.collect { case x: sc.Given => (x.tpe, pkg / x.name) }.toMap

  override def rowInstances(tpe: sc.Type, cols: NonEmptyList[ComputedColumn]): List[sc.ClassMember] = {
    val rowParser = {
      val mappedValues = cols.zipWithIndex.map { case (x, num) => code"${x.name} = row(idx + $num)(${lookupColumnFor(x.tpe)})" }
      sc.Value(
        Nil,
        rowParserName,
        params = List(sc.Param(sc.Ident("idx"), sc.Type.Int, None)),
        Nil,
        RowParser.of(tpe),
        code"""|${RowParser.of(tpe)} { row =>
               |  $Success(
               |    $tpe(
               |      ${mappedValues.mkCode(",\n")}
               |    )
               |  )
               |}""".stripMargin
      )
    }

    List(rowParser)
  }

  override def customTypeInstances(ct: CustomType): List[sc.Given] = {
    val tpe = ct.typoType
    val v = sc.Ident("v")
    val normal = List(
      sc.Given(
        Nil,
        toStatementName,
        Nil,
        ToStatement.of(tpe),
        code"${ToStatement.of(tpe)}((s, index, v) => s.setObject(index, ${ct.fromTypo0(v)}))"
      ),
      sc.Given(
        Nil,
        parameterMetadataName,
        Nil,
        ParameterMetaData.of(tpe),
        code"""|new ${ParameterMetaData.of(tpe)} {
               |  override def sqlType: ${sc.Type.String} = ${sc.StrLit(ct.sqlType)}
               |  override def jdbcType: ${sc.Type.Int} = ${sc.Type.Types}.OTHER
               |}""".stripMargin
      ),
      sc.Given(
        Nil,
        columnName,
        Nil,
        Column.of(tpe),
        code"""|$Column.nonNull[$tpe]((v1: ${sc.Type.Any}, _) =>
               |  v1 match {
               |    case $v: ${ct.toTypo.jdbcType} => ${sc.Type.Right}(${ct.toTypo0(v)})
               |    case other => ${sc.Type.Left}($TypeDoesNotMatch(s"Expected instance of ${ct.toTypo.jdbcType.render.asString}, got $${other.getClass.getName}"))
               |  }
               |)""".stripMargin
      )
    )

    val array = {
      val fromTypo = ct.fromTypoInArray.getOrElse(ct.fromTypo)
      val toTypo = ct.toTypoInArray.getOrElse(ct.toTypo)

      List(
        sc.Given(
          Nil,
          arrayToStatementName,
          Nil,
          ToStatement.of(sc.Type.Array.of(tpe)),
          code"${ToStatement.of(sc.Type.Array.of(tpe))}((s, index, v) => s.setArray(index, s.getConnection.createArrayOf(${sc.StrLit(ct.sqlType)}, $v.map(v => ${fromTypo.fromTypo0(v)}))))"
        ),
        sc.Given(
          Nil,
          arrayColumnName,
          Nil,
          Column.of(sc.Type.Array.of(tpe)),
          code"""|$Column.nonNull[${sc.Type.Array.of(tpe)}]((v1: ${sc.Type.Any}, _) =>
                 |  v1 match {
                 |      case $v: ${sc.Type.PgArray} =>
                 |       $v.getArray match {
                 |         case $v: ${sc.Type.Array.of(sc.Type.Wildcard)} =>
                 |           ${sc.Type.Right}($v.map($v => ${toTypo.toTypo(code"$v.asInstanceOf[${toTypo.jdbcType}]", ct.typoType)}))
                 |         case other => ${sc.Type.Left}($TypeDoesNotMatch(s"Expected one-dimensional array from JDBC to produce an array of ${ct.typoType}, got $${other.getClass.getName}"))
                 |       }
                 |    case other => ${sc.Type.Left}($TypeDoesNotMatch(s"Expected instance of ${sc.Type.PgArray.render.asString}, got $${other.getClass.getName}"))
                 |  }
                 |)""".stripMargin
        )
      )
    }
    normal ++ array
  }
}

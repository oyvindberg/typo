package typo
package internal
package codegen

object DbLibAnorm extends DbLib {
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

  def rowParserFor(rowType: sc.Type) = code"$rowType.$rowParserName(1)"

  def SQL(content: sc.Code) =
    sc.StringInterpolate(SqlStringInterpolation, sc.Ident("SQL"), content)

  val arrayColumnName = sc.Ident("arrayColumn")
  val arrayParameterMetaDataName = sc.Ident("arrayParameterMetaData")
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

  def columnFor(tpe: sc.Type): sc.Code =
    sc.Summon(Column.of(tpe)).code

  override def repoSig(repoMethod: RepoMethod): sc.Code = repoMethod match {
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
      val ident = Naming.camelCase(Array("selectByUnique"))
      code"def $ident(${params.map(_.param.code).mkCode(", ")})(implicit c: ${sc.Type.Connection}): ${sc.Type.Option.of(rowType)}"
    case RepoMethod.SelectByFieldValues(_, _, _, fieldValueOrIdsParam, rowType) =>
      code"def selectByFieldValues($fieldValueOrIdsParam)(implicit c: ${sc.Type.Connection}): ${sc.Type.List.of(rowType)}"
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
    case RepoMethod.Delete(_, id) =>
      code"def delete(${id.param})(implicit c: ${sc.Type.Connection}): ${sc.Type.Boolean}"
    case RepoMethod.SqlFile(sqlScript) =>
      val params = sqlScript.params match {
        case Nil      => sc.Code.Empty
        case nonEmpty => nonEmpty.map { param => sc.Param(param.name, param.tpe, None).code }.mkCode(",\n")
      }
      code"def apply($params)(implicit c: ${sc.Type.Connection}): ${sc.Type.List.of(sqlScript.RowName)}"
  }

  override def repoImpl(repoMethod: RepoMethod): sc.Code =
    repoMethod match {
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
        code"""${fieldValueOrIdsParam.name} match {
              |  case Nil => selectAll
              |  case nonEmpty =>
              |    val namedParams = nonEmpty.map{
              |      ${cases.mkCode("\n")}
              |    }
              |    val quote = '"'.toString
              |    val q = $sql
              |    // this line is here to include an extension method which is only needed for scala 3. no import is emitted for `SQL` to avoid warning for scala 2
              |    import anorm._
              |    SQL(q)
              |      .on(namedParams: _*)
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
              NonEmptyList(code"$NamedParameter(${sc.StrLit(unary.col.dbName.value)}, $ParameterValue.from(${id.paramName}))")
            case IdComputed.Composite(cols, _, paramName) =>
              cols.map { col =>
                code"$NamedParameter(${sc.StrLit(col.dbName.value)}, $ParameterValue.from($paramName.${col.name}))"
              }
          }

        val sql = sc.s {
          code"""update $relName
                |set $${namedParams.map(x => s"$$quote$${x.name}$$quote = {$${x.name}}").mkString(", ")}
                |where $where
                |""".stripMargin
        }
        code"""${varargs.name} match {
              |  case Nil => false
              |  case nonEmpty =>
              |    val namedParams = nonEmpty.map{
              |      ${cases.mkCode("\n")}
              |    }
              |    val quote = '"'.toString
              |    val q = $sql
              |    // this line is here to include an extension method which is only needed for scala 3. no import is emitted for `SQL` to avoid warning for scala 2
              |    import anorm._
              |    SQL(q)
              |      .on(namedParams: _*)
              |      .on(${idCases.mkCode(", ")})
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
          val colCast = sc.StrLit(sqlCast.toPgCode(col).render)
          code"""Some(($NamedParameter(${sc.StrLit(col.dbName.value)}, $ParameterValue.from(${unsavedParam.name}.${col.name})), $colCast))"""
        }
        val cases1 = unsaved.defaultCols.map { case (col @ ComputedColumn(_, ident, _, dbCol), origType) =>
          val dbName = sc.StrLit(dbCol.name.value)
          val colCast = sc.StrLit(sqlCast.toPgCode(col).render)

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

        code"""|val namedParameters = List(
               |  ${(cases0 ++ cases1.toList).mkCode(",\n")}
               |).flatten
               |val quote = '"'.toString
               |if (namedParameters.isEmpty) {
               |  $sqlEmpty
               |    .executeInsert(${rowParserFor(rowType)}.single)
               |} else {
               |  val q = $sql
               |  // this line is here to include an extension method which is only needed for scala 3. no import is emitted for `SQL` to avoid warning for scala 2
               |  import anorm._
               |  SQL(q)
               |    .on(namedParameters.map(_._1) :_*)
               |    .executeInsert(${rowParserFor(rowType)}.single)
               |}
               |"""

      case RepoMethod.Delete(relName, id) =>
        val sql = SQL {
          code"""delete from $relName where ${matchId(id)}"""
        }
        code"$sql.executeUpdate() > 0"
      case RepoMethod.SqlFile(sqlScript) =>
        val renderedScript = sqlScript.sqlFile.decomposedSql.render { (paramAtIndex: Int) =>
          val param = sqlScript.params.find(_.underlying.indices.contains(paramAtIndex)).get
          s"$$${param.name.value}"
        }
        code"""|val sql =
               |  ${SQL(renderedScript)}
               |sql.as(${rowParserFor(sqlScript.RowName)}.*)
               |""".stripMargin
    }

  override def mockRepoImpl(id: IdComputed, repoMethod: RepoMethod, maybeToRow: Option[sc.Param]): sc.Code = {
    repoMethod match {
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

      case RepoMethod.Delete(_, id) =>
        code"map.remove(${id.paramName}).isDefined"
      case RepoMethod.SqlFile(_) =>
        // should not happen (tm)
        code"???"
    }
  }

  override def stringEnumInstances(wrapperType: sc.Type, underlying: sc.Type, lookup: sc.Ident): List[sc.Given] =
    List(
      sc.Given(
        tparams = Nil,
        name = columnName,
        implicitParams = Nil,
        tpe = Column.of(wrapperType),
        body = code"""${sc.Summon(Column.of(underlying))}.mapResult { str => $lookup.get(str).toRight($SqlMappingError(s"$$str was not among $${$lookup.keys}")) }"""
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
      (sc.Type.String, sc.StrLit("varchar")),
      (sc.Type.Float, sc.StrLit("float4")),
      (sc.Type.Short, sc.StrLit("int2")),
      (sc.Type.Int, sc.StrLit("int4")),
      (sc.Type.Long, sc.StrLit("int8")),
      (sc.Type.Boolean, sc.StrLit("bool")),
      (sc.Type.Double, sc.StrLit("float8")),
      (sc.Type.UUID, sc.StrLit("uuid")),
      (sc.Type.BigDecimal, sc.StrLit("decimal"))
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
        ),
        sc.Given(
          Nil,
          tpe.value.name.appended("ArrayParameterMetaData"),
          Nil,
          ParameterMetaData.of(arrayType),
          code"""|new ${ParameterMetaData.of(arrayType)} {
                 |  override def sqlType: ${sc.Type.String} = ${elemType.prefixed("_")}
                 |  override def jdbcType: ${sc.Type.Int} = ${sc.Type.Types}.ARRAY
                 |}""".stripMargin
        )
      )
    }

    arrayInstances
  }

  override def rowInstances(tpe: sc.Type, cols: NonEmptyList[ComputedColumn]): List[sc.ClassMember] = {
    val rowParser = {
      val mappedValues = cols.zipWithIndex.map { case (x, num) => code"${x.name} = row[${x.tpe}](idx + $num)" }
      sc.Value(
        Nil,
        rowParserName,
        params = List(sc.Param(sc.Ident("idx"), sc.Type.Int, None)),
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
               |    case other => ${sc.Type.Left}($TypeDoesNotMatch(s"Expected instance of ${ct.toTypo.jdbcType.render}, got $${other.getClass.getName}"))
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
          arrayParameterMetaDataName,
          Nil,
          ParameterMetaData.of(sc.Type.Array.of(tpe)),
          code"""|new ${ParameterMetaData.of(sc.Type.Array.of(tpe))} {
                 |  override def sqlType: ${sc.Type.String} = ${sc.StrLit("_" + ct.sqlType)}
                 |  override def jdbcType: ${sc.Type.Int} = ${sc.Type.Types}.ARRAY
                 |}""".stripMargin
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
                 |    case other => ${sc.Type.Left}($TypeDoesNotMatch(s"Expected instance of ${sc.Type.PgArray.render}, got $${other.getClass.getName}"))
                 |  }
                 |)""".stripMargin
        )
      )
    }
    normal ++ array
  }
}

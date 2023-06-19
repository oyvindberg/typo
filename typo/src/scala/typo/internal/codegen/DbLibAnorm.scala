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
  val MetaDataItem = sc.Type.Qualified("anorm.MetaDataItem")
  val SqlRequestError = sc.Type.Qualified("anorm.SqlRequestError")
  val SQL = sc.Type.Qualified("anorm.SQL")
  val TypeDoesNotMatch = sc.Type.Qualified("anorm.TypeDoesNotMatch")

  val rowParserIdent = sc.Ident("rowParser")
  def rowParserFor(rowType: sc.Type) = code"$rowType.$rowParserIdent"

  def SQL(content: sc.Code) =
    sc.StringInterpolate(SqlStringInterpolation, sc.Ident("SQL"), content)

  def dbNames(cols: NonEmptyList[ComputedColumn], isRead: Boolean): sc.Code =
    cols
      .map { c =>
        val cast = (isRead, c.dbCol.tpe) match {
          case (true, db.Type.PGmoney)                => code"::numeric"
          case (true, db.Type.Array(db.Type.PGmoney)) => code"::numeric[]"
          case _                                      => sc.Code.Empty
        }
        maybeQuoted(c.dbName) ++ cast
      }
      .mkCode(", ")

  override def stringEnumInstances(wrapperType: sc.Type, underlying: sc.Type, lookup: sc.Ident): List[sc.Code] = {
    val column =
      code"""|implicit val column: ${Column.of(wrapperType)} =
             |  implicitly[${Column.of(underlying)}]
             |    .mapResult { str => $lookup.get(str).toRight($SqlMappingError(s"$$str was not among $${$lookup.keys}")) }""".stripMargin
    val toStatement =
      code"""|implicit val toStatement: ${ToStatement.of(wrapperType)} =
             |  implicitly[${ToStatement.of(underlying)}].contramap(_.value)""".stripMargin

    val parameterMetadata =
      code"""|implicit val parameterMetadata: $ParameterMetaData[$wrapperType] = new $ParameterMetaData[$wrapperType] {
             |  override def sqlType: ${sc.Type.String} = implicitly[${ParameterMetaData.of(underlying)}].sqlType
             |  override def jdbcType: ${sc.Type.Int} = implicitly[${ParameterMetaData.of(underlying)}].jdbcType
             |}"""
    List(column, toStatement, parameterMetadata)
  }

  override def anyValInstances(wrapperType: sc.Type.Qualified, underlying: sc.Type): List[sc.Code] = {
    val toStatement = code"""implicit val toStatement: ${ToStatement.of(wrapperType)} = implicitly[${ToStatement.of(underlying)}].contramap(_.value)"""
    val column = code"""implicit val column: ${Column.of(wrapperType)} = implicitly[${Column.of(underlying)}].map($wrapperType.apply)"""
    val parameterMetadata =
      code"""|implicit val parameterMetadata: ${ParameterMetaData.of(wrapperType)} = new ${ParameterMetaData.of(wrapperType)} {
             |  override def sqlType: String = implicitly[${ParameterMetaData.of(underlying)}].sqlType
             |  override def jdbcType: Int = implicitly[${ParameterMetaData.of(underlying)}].jdbcType
             |}
             |""".stripMargin

    List(toStatement, column, parameterMetadata)
  }

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

  def matchId(id: IdComputed): sc.Code =
    id match {
      case id: IdComputed.Unary =>
        code"${maybeQuoted(id.col.dbName)} = $$${id.paramName}"
      case composite: IdComputed.Composite =>
        code"${composite.cols.map(cc => code"${maybeQuoted(cc.dbName)} = $${${composite.paramName}.${cc.name}}").mkCode(" AND ")}"
    }

  def matchAnyId(x: IdComputed.Unary, idsParam: sc.Param): sc.Code =
    code"${maybeQuoted(x.col.dbName)} = ANY($$${idsParam.name})"

  def cast(c: ComputedColumn): sc.Code =
    c.dbCol.tpe match {
      case db.Type.EnumRef(name)                               => code"::$name"
      case db.Type.DomainRef(name)                             => code"::$name"
      case db.Type.PGmoney                                     => code"::numeric"
      case db.Type.Array(db.Type.PGmoney)                      => code"::numeric[]"
      case db.Type.Boolean | db.Type.Text | db.Type.VarChar(_) => sc.Code.Empty
      case _ =>
        c.dbCol.udtName match {
          case Some(value) => code"::$value"
          case None        => sc.Code.Empty
        }
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
        val maybeToStatement = unaryId match {
          case IdComputed.UnaryUserSpecified(_, _) =>
            sc.Code.Empty
          case id =>
            val boxedType = sc.Type.boxedType(id.col.tpe) match {
              case Some(boxed) => code": $boxed"
              case None        => sc.Code.Empty
            }
            code"""|implicit val toStatement: ${ToStatement.of(sc.Type.Array.of(id.tpe))} =
                   |  (s: ${sc.Type.PreparedStatement}, index: ${sc.Type.Int}, v: ${sc.Type.Array.of(id.tpe)}) =>
                   |    s.setArray(index, s.getConnection.createArrayOf(${sc.StrLit(id.col.dbCol.udtName.get)}, v.map(x => x.value$boxedType)))
                   |""".stripMargin
        }

        code"""|$maybeToStatement
               |$sql.as(${rowParserFor(rowType)}.*)
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
                 |set ${colsUnsaved.map { col => code"${maybeQuoted(col.dbName)} = $${${param.name}.${col.name}}${cast(col)}" }.mkCode(",\n")}
                 |where ${matchId(id)}
                 |""".stripMargin
        }
        code"""|val ${id.paramName} = ${param.name}.${id.paramName}
               |$sql.executeUpdate() > 0"""

      case RepoMethod.Insert(relName, cols, unsavedParam, rowType) =>
        val values = cols.map { c =>
          code"$${${unsavedParam.name}.${c.name}}${cast(c)}"
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
          code"$${${unsavedParam.name}.${c.name}}${cast(c)}"
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
          val colCast = sc.StrLit(cast(col).render)
          code"""Some(($NamedParameter(${sc.StrLit(col.dbName.value)}, $ParameterValue.from(${unsavedParam.name}.${col.name})), $colCast))"""
        }
        val cases1 = unsaved.defaultCols.map { case (col @ ComputedColumn(_, ident, _, dbCol), origType) =>
          val dbName = sc.StrLit(dbCol.name.value)
          val colCast = sc.StrLit(cast(col).render)

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

  override def missingInstances: List[sc.Code] = {
    def inout(tpe: sc.Type) = code"${ToStatement.of(tpe)} with ${ParameterMetaData.of(tpe)} with ${Column.of(tpe)}"
    def out(tpe: sc.Type) = code"${ToStatement.of(tpe)} with ${ParameterMetaData.of(tpe)}"

    val arrayTypes = List[(sc.Type.Qualified, sc.StrLit)](
      (sc.Type.String, sc.StrLit("varchar")),
      (sc.Type.Float, sc.StrLit("float4")),
      (sc.Type.Short, sc.StrLit("int2")),
      (sc.Type.Int, sc.StrLit("int4")),
      (sc.Type.Long, sc.StrLit("int8")),
      (sc.Type.Boolean, sc.StrLit("bool")),
      (sc.Type.Double, sc.StrLit("float8")),
      (sc.Type.UUID, sc.StrLit("uuid")),
      (sc.Type.BigDecimal, sc.StrLit("decimal"))
    )

    val arrayInstances = arrayTypes.map { case (tpe, elemType) =>
      val arrayType = sc.Type.Array.of(tpe)
      val boxedType = sc.Type.boxedType(tpe).getOrElse(sc.Type.AnyRef)
      code"""|implicit val ${tpe.value.name}Array: ${out(arrayType)} = new ${out(arrayType)} {
             |  override def sqlType: ${sc.Type.String} = ${elemType.prefixed("_")}
             |  override def jdbcType: ${sc.Type.Int} = ${sc.Type.Types}.ARRAY
             |  override def set(ps: ${sc.Type.PreparedStatement}, index: ${sc.Type.Int}, v: $arrayType): ${sc.Type.Unit} =
             |    ps.setArray(index, ps.getConnection.createArrayOf($elemType, v.map(v => v: $boxedType)))
             |}
             |""".stripMargin
    }

    val localTime = {
      val tpe = sc.Type.LocalTime
      val either = sc.Type.Either.of(SqlRequestError, tpe)
      code"""|implicit val localTimeDb: ${inout(tpe)} = new ${inout(tpe)} {
           |  override def sqlType: ${sc.Type.String} = "time"
           |  override def jdbcType: ${sc.Type.Int} = ${sc.Type.Types}.TIME
           |  override def set(s: ${sc.Type.PreparedStatement}, index: ${sc.Type.Int}, v: $tpe): ${sc.Type.Unit} =
           |    s.setObject(index, new java.sql.Time(v.toNanoOfDay / 1000000))
           |  override def apply(v1: ${sc.Type.Any}, v2: $MetaDataItem): $either =
           |    v1 match {
           |      case v: ${sc.Type.JavaTime} => ${sc.Type.Right}(v.toLocalTime)
           |      case other => ${sc.Type.Left}($TypeDoesNotMatch(s"Expected instance of java.sql.Time, got $${other.getClass.getName}"))
           |    }
           |}
           |""".stripMargin
    }

    arrayInstances ++ List(localTime)
  }

  def rowInstances(maybeId: Option[IdComputed], tpe: sc.Type, cols: NonEmptyList[ComputedColumn]): List[sc.Code] = {
    val rowParser = {
      val mappedValues = cols.map { x => code"${x.name} = row[${x.tpe}](${sc.StrLit(x.dbName.value)})" }
      code"""|val $rowParserIdent: ${RowParser.of(tpe)} =
             |  ${RowParser.of(tpe)} { row =>
             |    $Success(
             |      $tpe(
             |        ${mappedValues.mkCode(",\n")}
             |      )
             |    )
             |  }""".stripMargin
    }

    List(rowParser)
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

  override def customTypeInstances(ct: CustomType): List[sc.Code] = {
    def inout(tpe: sc.Type) = code"${ToStatement.of(tpe)} with ${ParameterMetaData.of(tpe)} with ${Column.of(tpe)}"
    val tpe = ct.typoType
    val v = sc.Ident("v")
    val normal =
      code"""|implicit val ${tpe.value.name}Db: ${inout(tpe)} = new ${inout(tpe)} {
             |  override def sqlType: ${sc.Type.String} = ${sc.StrLit(ct.sqlType)}
             |  override def jdbcType: ${sc.Type.Int} = ${sc.Type.Types}.OTHER
             |  override def set(s: ${sc.Type.PreparedStatement}, index: ${sc.Type.Int}, $v: $tpe): ${sc.Type.Unit} =
             |    s.setObject(index, ${ct.fromTypo0(v)})
             |  override def apply($v: ${sc.Type.Any}, v2: $MetaDataItem): ${sc.Type.Either.of(SqlRequestError, tpe)} =
             |    $v match {
             |      case $v: ${ct.toTypo.jdbcType} => ${sc.Type.Right}(${ct.toTypo0(v)})
             |      case other => ${sc.Type.Left}($TypeDoesNotMatch(s"Expected instance of ${ct.toTypo.jdbcType} from JDBC to produce a ${ct.typoType}, got $${other.getClass.getName}"))
             |    }
             |}
             |""".stripMargin
    val array = {
      val fromTypo = ct.fromTypoInArray.getOrElse(ct.fromTypo)
      val toTypo = ct.toTypoInArray.getOrElse(ct.toTypo)
      code"""|implicit val ${tpe.value.name}DbArray: ${inout(sc.Type.Array.of(tpe))} = new ${inout(sc.Type.Array.of(tpe))} {
             |  override def sqlType: ${sc.Type.String} = ${sc.StrLit("_" + ct.sqlType)}
             |  override def jdbcType: ${sc.Type.Int} = ${sc.Type.Types}.ARRAY
             |  override def set(s: ${sc.Type.PreparedStatement}, index: ${sc.Type.Int}, $v: ${sc.Type.Array.of(tpe)}): ${sc.Type.Unit} =
             |    s.setArray(index, s.getConnection.createArrayOf(${sc.StrLit(ct.sqlType)}, $v.map(v => ${fromTypo.fromTypo0(v)})))
             |  override def apply($v: ${sc.Type.Any}, v2: $MetaDataItem): ${sc.Type.Either.of(SqlRequestError, sc.Type.Array.of(tpe))} =
             |    $v match {
             |      case $v: ${sc.Type.PgArray} =>
             |       $v.getArray match {
             |         case $v: ${sc.Type.Array.of(sc.Type.Wildcard)} =>
             |           ${sc.Type.Right}($v.map($v => ${toTypo.toTypo(code"$v.asInstanceOf[${toTypo.jdbcType}]", ct.typoType)}))
             |         case other => ${sc.Type.Left}($TypeDoesNotMatch(s"Expected one-dimensional array from JDBC to produce an array of ${ct.typoType}, got $${other.getClass.getName}"))
             |       }
             |      case other => ${sc.Type.Left}($TypeDoesNotMatch(s"Expected ${sc.Type.PgArray} from JDBC to produce an array of ${ct.typoType}, got $${other.getClass.getName}"))
             |    }
             |}
             |""".stripMargin

    }
    List(normal, array)
  }
}

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
  val SqlParser = sc.Type.Qualified("anorm.SqlParser")
  val ParameterMetaData = sc.Type.Qualified("anorm.ParameterMetaData")
  val MetaDataItem = sc.Type.Qualified("anorm.MetaDataItem")
  val SqlRequestError = sc.Type.Qualified("anorm.SqlRequestError")
  val SQL = sc.Type.Qualified("anorm.SQL")
  val TypeDoesNotMatch = sc.Type.Qualified("anorm.TypeDoesNotMatch")

  val rowParserIdent = sc.Ident("rowParser")
  def SQL(content: sc.Code) =
    sc.StringInterpolate(SqlStringInterpolation, sc.Ident("SQL"), content)

  def dbNames(cols: NonEmptyList[ColumnComputed]): sc.Code =
    cols.map(c => maybeQuoted(c.dbName)).mkCode(", ")

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
    case RepoMethod.SelectAll(rowType) =>
      code"def selectAll(implicit c: ${sc.Type.Connection}): ${sc.Type.List.of(rowType)}"
    case RepoMethod.SelectById(id, rowType) =>
      code"def selectById(${id.param})(implicit c: ${sc.Type.Connection}): ${sc.Type.Option.of(rowType)}"
    case RepoMethod.SelectAllByIds(unaryId, idsParam, rowType) =>
      unaryId match {
        case IdComputed.UnaryUserSpecified(_, tpe) =>
          code"def selectByIds($idsParam)(implicit c: ${sc.Type.Connection}, toStatement: ${ToStatement.of(sc.Type.Array.of(tpe))}): ${sc.Type.List.of(rowType)}"
        case _ =>
          code"def selectByIds($idsParam)(implicit c: ${sc.Type.Connection}): ${sc.Type.List.of(rowType)}"
      }
    case RepoMethod.SelectByUnique(params, rowType) =>
      val ident = Naming.camelCase(Array("selectByUnique"))
      code"def $ident(${params.map(_.param.code).mkCode(", ")})(implicit c: ${sc.Type.Connection}): ${sc.Type.Option.of(rowType)}"
    case RepoMethod.SelectByFieldValues(param, rowType) =>
      code"def selectByFieldValues($param)(implicit c: ${sc.Type.Connection}): ${sc.Type.List.of(rowType)}"
    case RepoMethod.UpdateFieldValues(id, param, _, _) =>
      code"def updateFieldValues(${id.param}, $param)(implicit c: ${sc.Type.Connection}): ${sc.Type.Boolean}"
    case RepoMethod.Update(_, param, _) =>
      code"def update($param)(implicit c: ${sc.Type.Connection}): ${sc.Type.Boolean}"
    case RepoMethod.InsertDbGeneratedKey(_, _, unsavedParam, _, rowType) =>
      code"def insert($unsavedParam)(implicit c: ${sc.Type.Connection}): $rowType"
    case RepoMethod.InsertProvidedKey(id, _, unsavedParam, _, rowType) =>
      code"def insert(${id.param}, $unsavedParam)(implicit c: ${sc.Type.Connection}): $rowType"
    case RepoMethod.InsertOnlyKey(id) =>
      code"def insert(${id.param})(implicit c: ${sc.Type.Connection}): ${sc.Type.Unit}"
    case RepoMethod.Delete(id) =>
      code"def delete(${id.param})(implicit c: ${sc.Type.Connection}): ${sc.Type.Boolean}"
    case RepoMethod.SqlFile(sqlScript) =>
      val params = sqlScript.params match {
        case Nil      => sc.Code.Empty
        case nonEmpty => nonEmpty.map { param => sc.Param(param.name, param.tpe).code }.mkCode(",\n")
      }
      code"def apply($params)(implicit c: ${sc.Type.Connection}): ${sc.Type.List.of(sqlScript.relation.RowName)}"
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

  def cast(c: ColumnComputed): sc.Code =
    c.dbCol.tpe match {
      case db.Type.EnumRef(name)                               => code"::$name"
      case db.Type.DomainRef(name)                             => code"::$name"
      case db.Type.Boolean | db.Type.Text | db.Type.VarChar(_) => sc.Code.Empty
      case _ =>
        c.dbCol.udtName match {
          case Some(value) => code"::$value"
          case None        => sc.Code.Empty
        }
    }

  override def repoImpl(table: RelationComputed, repoMethod: RepoMethod): sc.Code =
    repoMethod match {
      case RepoMethod.SelectAll(_) =>
        val sql = SQL {
          code"""|select ${dbNames(table.cols)}
                 |from ${table.relationName}
                 |""".stripMargin
        }
        code"""$sql.as($rowParserIdent.*)"""

      case RepoMethod.SelectById(id, _) =>
        val sql = SQL {
          code"""|select ${dbNames(table.cols)}
                 |from ${table.relationName}
                 |where ${matchId(id)}
                 |""".stripMargin
        }
        code"""$sql.as($rowParserIdent.singleOpt)"""

      case RepoMethod.SelectAllByIds(unaryId, idsParam, _) =>
        val sql = SQL {
          code"""|select ${dbNames(table.cols)}
                 |from ${table.relationName}
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
               |$sql.as($rowParserIdent.*)
               |""".stripMargin

      case RepoMethod.SelectByUnique(params, _) =>
        val args = params.map { param =>
          code"${table.FieldValueName}.${param.name}(${param.name})"
        }
        code"""selectByFieldValues(${sc.Type.List}(${args.mkCode(", ")})).headOption"""

      case RepoMethod.SelectByFieldValues(param, _) =>
        val cases: NonEmptyList[sc.Code] =
          table.cols.map { col =>
            code"case ${table.FieldValueName}.${col.name}(value) => $NamedParameter(${sc.StrLit(col.dbName.value)}, $ParameterValue.from(value))"
          }

        val sql = sc.s {
          code"""|select ${dbNames(table.cols)}
                 |from ${table.relationName}
                 |where $${namedParams.map(x => s"$${x.name} = {$${x.name}}").mkString(" AND ")}
                 |""".stripMargin
        }
        code"""${param.name} match {
              |  case Nil => selectAll
              |  case nonEmpty =>
              |    val namedParams = nonEmpty.map{
              |      ${cases.mkCode("\n")}
              |    }
              |    val q = $sql
              |    // this line is here to include an extension method which is only needed for scala 3. no import is emitted for `SQL` to avoid warning for scala 2
              |    import anorm._
              |    SQL(q)
              |      .on(namedParams: _*)
              |      .as($rowParserIdent.*)
              |}
              |""".stripMargin

      case RepoMethod.UpdateFieldValues(id, param, cases0, _) =>
        val cases: NonEmptyList[sc.Code] =
          cases0.map { col =>
            code"case ${table.FieldValueName}.${col.name}(value) => $NamedParameter(${sc.StrLit(col.dbName.value)}, $ParameterValue.from(value))"
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
          code"""update ${table.relationName}
                |set $${namedParams.map(x => s"\\\"$${x.name}\\\" = {$${x.name}}").mkString(", ")}
                |where $where
                |""".stripMargin
        }
        code"""${param.name} match {
              |  case Nil => false
              |  case nonEmpty =>
              |    val namedParams = nonEmpty.map{
              |      ${cases.mkCode("\n")}
              |    }
              |    val q = $sql
              |    // this line is here to include an extension method which is only needed for scala 3. no import is emitted for `SQL` to avoid warning for scala 2
              |    import anorm._
              |    SQL(q)
              |      .on(namedParams: _*)
              |      .on(${idCases.mkCode(", ")})
              |      .executeUpdate() > 0
              |}
              |""".stripMargin

      case RepoMethod.Update(id, param, colsUnsaved) =>
        val sql = SQL {
          code"""|update ${table.relationName}
                 |set ${colsUnsaved.map { col => code"${maybeQuoted(col.dbName)} = $${${param.name}.${col.name}}${cast(col)}" }.mkCode(",\n")}
                 |where ${matchId(id)}
                 |""".stripMargin
        }
        code"""|val ${id.paramName} = ${param.name}.${id.paramName}
               |$sql.executeUpdate() > 0"""

      case RepoMethod.InsertDbGeneratedKey(_, colsUnsaved, unsavedParam, _, _) if colsUnsaved.forall(_.dbCol.columnDefault.isEmpty) =>
        val values = colsUnsaved.map { c =>
          code"$${${unsavedParam.name}.${c.name}}${cast(c)}"
        }
        val sql = SQL {
          code"""|insert into ${table.relationName}(${dbNames(colsUnsaved)})
                 |values (${values.mkCode(", ")})
                 |returning ${table.cols.map(_.dbName.value.code).mkCode(", ")}
                 |""".stripMargin
        }

        code"""|$sql
               |  .executeInsert($rowParserIdent.single)
               |"""

      case RepoMethod.InsertDbGeneratedKey(_, colsUnsaved, unsavedParam, default, _) =>
        val maybeNamedParameters = colsUnsaved.map {
          case col @ ColumnComputed(_, ident, sc.Type.TApply(default.Defaulted, List(tpe)), dbCol) =>
            val dbName = sc.StrLit(dbCol.name.value)
            val colCast = sc.StrLit(cast(col).render)

            code"""|${unsavedParam.name}.$ident match {
                   |  case ${default.Defaulted}.${default.UseDefault} => None
                   |  case ${default.Defaulted}.${default.Provided}(value) => Some(($NamedParameter($dbName, $ParameterValue.from[$tpe](value)), $colCast))
                   |}"""
          case col =>
            val colCast = sc.StrLit(cast(col).render)
            code"""Some(($NamedParameter(${sc.StrLit(col.dbName.value)}, $ParameterValue.from(${unsavedParam.name}.${col.name})), $colCast))"""
        }

        val sql = sc.s {
          code"""|insert into ${table.relationName}($${namedParameters.map{case (x, _) => "\\\"" + x.name + "\\\""}.mkString(", ")})
                 |values ($${namedParameters.map{ case (np, cast) => s"{$${np.name}}$$cast"}.mkString(", ")})
                 |returning ${dbNames(table.cols)}
                 |""".stripMargin
        }
        val sqlEmpty = SQL {
          code"""|insert into ${table.relationName} default values
                 |returning ${dbNames(table.cols)}
                 |""".stripMargin
        }

        code"""|val namedParameters = List(
               |  ${maybeNamedParameters.mkCode(",\n")}
               |).flatten
               |
               |if (namedParameters.isEmpty) {
               |  $sqlEmpty
               |    .executeInsert($rowParserIdent.single)
               |} else {
               |  val q = $sql
               |  // this line is here to include an extension method which is only needed for scala 3. no import is emitted for `SQL` to avoid warning for scala 2
               |  import anorm._
               |  SQL(q)
               |    .on(namedParameters.map(_._1) :_*)
               |    .executeInsert($rowParserIdent.single)
               |}
               |"""

      case RepoMethod.InsertProvidedKey(id, colsUnsaved, unsavedParam, _, _) if colsUnsaved.forall(_.dbCol.columnDefault.isEmpty) =>
        val idValues: NonEmptyList[sc.Code] =
          id match {
            case id: IdComputed.Unary     => NonEmptyList(code"$${${id.paramName}}${cast(id.col)}")
            case id: IdComputed.Composite => id.cols.map(c => code"$${${id.paramName}.${c.name}}${cast(c)}")
          }
        val unsavedValues = colsUnsaved.map(c => code"$${${unsavedParam.name}.${c.name}}${cast(c)}")

        val sql = SQL {
          code"""|insert into ${table.relationName}(${dbNames(id.cols)}, ${dbNames(colsUnsaved)})
                 |values (${idValues.mkCode(", ")}, ${unsavedValues.mkCode(", ")})
                 |returning ${dbNames(table.cols)}
                 |""".stripMargin
        }

        code"""|$sql
               |  .executeInsert($rowParserIdent.single)
               |"""

      case RepoMethod.InsertProvidedKey(id, colsUnsaved, unsavedParam, default, _) =>
        val maybeNamedParameters = colsUnsaved.map {
          case col @ ColumnComputed(_, ident, sc.Type.TApply(default.Defaulted, List(tpe)), dbCol) =>
            val dbName = sc.StrLit(dbCol.name.value)
            val colCast = sc.StrLit(cast(col).render)
            code"""|${unsavedParam.name}.$ident match {
                   |  case ${default.Defaulted}.${default.UseDefault} => None
                   |  case ${default.Defaulted}.${default.Provided}(value) => Some(($NamedParameter($dbName, $ParameterValue.from[$tpe](value)), $colCast))
                   |}"""
          case col =>
            val colCast = sc.StrLit(cast(col).render)
            code"""Some(($NamedParameter(${sc.StrLit(col.dbName.value)}, $ParameterValue.from(${unsavedParam.name}.${col.name})), $colCast))"""
        }

        val idColRefs: sc.Code =
          id.cols.map { col => code"{${col.name}}${cast(col)}" }.mkCode(", ")

        val idNamedParameters: NonEmptyList[sc.Code] =
          id match {
            case unary: IdComputed.Unary =>
              NonEmptyList(code"$NamedParameter(${sc.StrLit(unary.col.dbName.value)}, $ParameterValue.from(${id.paramName}))")
            case IdComputed.Composite(cols, _, paramName) =>
              cols.map { col =>
                code"$NamedParameter(${sc.StrLit(col.dbName.value)}, $ParameterValue.from($paramName.${col.name}))"
              }
          }

        val sql = sc.s {
          code"""|insert into ${table.relationName}(${dbNames(id.cols)}, $${namedParameters.map(x => "\\\"" + x._1.name + "\\\"").mkString(", ")})
                 |values ($idColRefs, $${namedParameters.map{case (np, cast) => s"{$${np.name}}$$cast"}.mkString(", ")})
                 |returning ${dbNames(table.cols)}
                 |""".stripMargin
        }

        code"""|val namedParameters = List(
               |  ${maybeNamedParameters.mkCode(",\n")}
               |).flatten
               |val q = $sql
               |// this line is here to include an extension method which is only needed for scala 3. no import is emitted for `SQL` to avoid warning for scala 2
               |import anorm._
               |SQL(q)
               |  .on(namedParameters.map(_._1) :_*)
               |  .on(${idNamedParameters.mkCode(", ")})
               |  .executeInsert($rowParserIdent.single)
               |"""

      case RepoMethod.InsertOnlyKey(id) =>
        val idValues: NonEmptyList[sc.Code] =
          id match {
            case id: IdComputed.Unary     => NonEmptyList(code"$${${id.paramName}}")
            case id: IdComputed.Composite => id.cols.map(cc => code"$${${id.paramName}.${cc.name}}")
          }

        val sql = SQL {
          code"""|insert into ${table.relationName}(${dbNames(id.cols)})
                 |values (${idValues.mkCode(", ")})
                 |""".stripMargin
        }

        code"""|$sql.execute()
               |()""".stripMargin

      case RepoMethod.Delete(id) =>
        val sql = SQL {
          code"""delete from ${table.relationName} where ${matchId(id)}"""
        }
        code"$sql.executeUpdate() > 0"
      case RepoMethod.SqlFile(sqlScript) =>
        val renderedScript = sqlScript.sqlFile.decomposedSql.render { (paramAtIndex: Int) =>
          val param = sqlScript.params.find(_.underlying.indices.contains(paramAtIndex)).get
          s"$$${param.name.value}"
        }
        code"""|val sql =
               |  ${SQL(renderedScript)}
               |sql.as($rowParserIdent.*)
               |""".stripMargin
    }

  override def missingInstances: List[sc.Code] = {
    def inout(tpe: sc.Type) = code"${ToStatement.of(tpe)} with ${ParameterMetaData.of(tpe)} with ${Column.of(tpe)}"
    def out(tpe: sc.Type) = code"${ToStatement.of(tpe)} with ${ParameterMetaData.of(tpe)}"

    val arrayTypes = List[(sc.Type.Qualified, sc.StrLit)](
      (sc.Type.String, sc.StrLit("_varchar")),
      (sc.Type.Float, sc.StrLit("_float4")),
      (sc.Type.Short, sc.StrLit("_int2")),
      (sc.Type.Int, sc.StrLit("_int4")),
      (sc.Type.Long, sc.StrLit("_int8")),
      (sc.Type.Boolean, sc.StrLit("_bool")),
      (sc.Type.Double, sc.StrLit("_float8")),
      (sc.Type.UUID, sc.StrLit("_uuid")),
      (sc.Type.BigDecimal, sc.StrLit("_decimal")),
      (sc.Type.PGobject, sc.StrLit("_aclitem"))
    )

    val arrayInstances = arrayTypes.map { case (tpe, elemType) =>
      val arrayType = sc.Type.Array.of(tpe)
      val boxedType = sc.Type.boxedType(tpe).getOrElse(sc.Type.AnyRef)
      code"""|implicit val ${tpe.value.name}Array: ${out(arrayType)} = new ${out(arrayType)} {
             |  override def sqlType: ${sc.Type.String} = $elemType
             |  override def jdbcType: ${sc.Type.Int} = ${sc.Type.Types}.ARRAY
             |  override def set(ps: ${sc.Type.PreparedStatement}, index: ${sc.Type.Int}, v: $arrayType): ${sc.Type.Unit} =
             |    ps.setArray(index, ps.getConnection.createArrayOf($elemType, v.map(v => v: $boxedType)))
             |}
             |""".stripMargin
    }

    val postgresTypes = PostgresTypes.all.map { case (typeName, tpe) =>
      val either = sc.Type.Either.of(SqlRequestError, tpe)
      code"""|implicit val ${tpe.value.name}Db: ${inout(tpe)} = new ${inout(tpe)} {
             |  override def sqlType: ${sc.Type.String} = $typeName
             |  override def jdbcType: ${sc.Type.Int} = ${sc.Type.Types}.OTHER
             |  override def set(s: ${sc.Type.PreparedStatement}, index: ${sc.Type.Int}, v: $tpe): ${sc.Type.Unit} = s.setObject(index, v)
             |  override def apply(v1: ${sc.Type.Any}, v2: $MetaDataItem): $either = ${sc.Type.Right}(v1.asInstanceOf[$tpe])
             |}
             |""".stripMargin
    }
    val PgSQLXML = {
      val tpe = sc.Type.PgSQLXML
      val either = sc.Type.Either.of(SqlRequestError, tpe)
      code"""|implicit val ${tpe.value.name}Db: ${inout(tpe)} = new ${inout(tpe)} {
             |  override def sqlType: ${sc.Type.String} = "xml"
             |  override def jdbcType: ${sc.Type.Int} = ${sc.Type.Types}.SQLXML
             |  override def set(s: ${sc.Type.PreparedStatement}, index: ${sc.Type.Int}, v: $tpe): ${sc.Type.Unit} = s.setObject(index, v)
             |  override def apply(v1: ${sc.Type.Any}, v2: $MetaDataItem): $either = ${sc.Type.Right}(v1.asInstanceOf[$tpe])
             |}
             |""".stripMargin

    }
    val hstore = {
      val tpe = sc.Type.JavaMap.of(sc.Type.String, sc.Type.String)
      val either = sc.Type.Either.of(SqlRequestError, tpe)
      code"""|implicit val hstoreDb: ${inout(tpe)} = new ${inout(tpe)} {
             |  override def sqlType: ${sc.Type.String} = "hstore"
             |  override def jdbcType: ${sc.Type.Int} = ${sc.Type.Types}.OTHER
             |  override def set(s: ${sc.Type.PreparedStatement}, index: ${sc.Type.Int}, v: $tpe): ${sc.Type.Unit} = s.setObject(index, v)
             |  override def apply(v1: ${sc.Type.Any}, v2: $MetaDataItem): $either = ${sc.Type.Right}(v1.asInstanceOf[$tpe])
             |}
             |""".stripMargin
    }

    val pgObject = {
      val tpe = sc.Type.PGobject
      val either = sc.Type.Either.of(SqlRequestError, tpe)
      code"""|implicit val pgObjectDb: ${inout(tpe)} = new ${inout(tpe)} {
           |  override def sqlType: ${sc.Type.String} = "hstore"
           |  override def jdbcType: ${sc.Type.Int} = ${sc.Type.Types}.OTHER
           |  override def set(s: ${sc.Type.PreparedStatement}, index: ${sc.Type.Int}, v: $tpe): ${sc.Type.Unit} = s.setObject(index, v)
           |  override def apply(v1: ${sc.Type.Any}, v2: $MetaDataItem): $either = ${sc.Type.Right}(v1.asInstanceOf[$tpe])
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

    arrayInstances ++ postgresTypes ++ List(PgSQLXML, hstore, pgObject, localTime)
  }

  def repoAdditionalMembers(maybeId: Option[IdComputed], tpe: sc.Type, cols: NonEmptyList[ColumnComputed]): List[sc.Code] = {
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
}

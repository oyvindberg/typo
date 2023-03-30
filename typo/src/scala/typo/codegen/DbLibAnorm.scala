package typo
package codegen

import typo.sc.syntax._

object DbLibAnorm extends DbLib {
  def Column(t: sc.Type) = sc.Type.TApply(sc.Type.Qualified("anorm.Column"), List(t))
  val ToStatementName = sc.Type.Qualified("anorm.ToStatement")
  def ToStatement(t: sc.Type) = sc.Type.TApply(ToStatementName, List(t))
  val NamedParameter = sc.Type.Qualified("anorm.NamedParameter")
  val ParameterValue = sc.Type.Qualified("anorm.ParameterValue")
  def RowParser(t: sc.Type) = sc.Type.TApply(sc.Type.Qualified("anorm.RowParser"), List(t))
  val Success = sc.Type.Qualified("anorm.Success")
  val SqlMappingError = sc.Type.Qualified("anorm.SqlMappingError")
  val SqlStringInterpolation = sc.Type.Qualified("anorm.SqlStringInterpolation")
  val SqlParser = sc.Type.Qualified("anorm.SqlParser")
  val ParameterMetaData = sc.Type.Qualified("anorm.ParameterMetaData")
  val MetaDataItem = sc.Type.Qualified("anorm.MetaDataItem")
  val SqlRequestError = sc.Type.Qualified("anorm.SqlRequestError")
  val SQL = sc.Type.Qualified("anorm.SQL")

  val rowParserIdent = sc.Ident("rowParser")
  def interpolate(content: sc.Code) =
    sc.StringInterpolate(SqlStringInterpolation, sc.Ident("SQL"), content)

  override def stringEnumInstances(wrapperType: sc.Type, underlying: sc.Type, lookup: sc.Ident): List[sc.Code] = {
    val column =
      code"""|implicit val column: ${Column(wrapperType)} =
               |    implicitly[${Column(underlying)}]
               |      .mapResult { str => $lookup.get(str).toRight($SqlMappingError(s"$$str was not among $${$lookup.keys}")) }""".stripMargin
    val toStatement =
      code"""|implicit val toStatement: ${ToStatement(wrapperType)} =
             |    implicitly[${ToStatement(underlying)}].contramap(_.value)""".stripMargin

    val parameterMetadata =
      code"""|implicit val parameterMetadata: $ParameterMetaData[$wrapperType] = new $ParameterMetaData[$wrapperType] {
             |    override def sqlType: ${sc.Type.String} = implicitly[${ParameterMetaData.of(underlying)}].sqlType
             |    override def jdbcType: ${sc.Type.Int} = implicitly[${ParameterMetaData.of(underlying)}].jdbcType
             |}"""
    List(column, toStatement, parameterMetadata)
  }

  override def instances(tpe: sc.Type, cols: Seq[ColumnComputed]): List[sc.Code] = {
    val mappedValues = cols.map { x => code"${x.name} = row[${x.tpe}](prefix + ${sc.StrLit(x.dbName.value)})" }
    val rowParser = code"""def $rowParserIdent(prefix: String): ${RowParser(tpe)} = { row =>
            |    $Success(
            |      $tpe(
            |        ${mappedValues.mkCode(",\n        ")}
            |      )
            |    )
            |  }
            |""".stripMargin

    List(rowParser)
  }
  override def anyValInstances(wrapperType: sc.Type.Qualified, underlying: sc.Type, colName: db.ColName): List[sc.Code] = {
    val toStatement = code"""implicit val toStatement: ${ToStatement(wrapperType)} = implicitly[${ToStatement(underlying)}].contramap(_.value)"""
    val column = code"""implicit val column: ${Column(wrapperType)} = implicitly[${Column(underlying)}].map($wrapperType.apply)"""
    val rowParser = code"def $rowParserIdent(prefix: String): ${RowParser(wrapperType)} = $SqlParser.get[$wrapperType](prefix + ${sc.StrLit(colName.value)})"
    val parameterMetadata =
      code"""|implicit val parameterMetadata: ${ParameterMetaData.of(wrapperType)} = new ${ParameterMetaData.of(wrapperType)} {
             |    override def sqlType: String = implicitly[${ParameterMetaData.of(underlying)}].sqlType
             |    override def jdbcType: Int = implicitly[${ParameterMetaData.of(underlying)}].jdbcType
             |  }
             |""".stripMargin
    List(toStatement, column, rowParser, parameterMetadata)
  }

  override def repoSig(repoMethod: RepoMethod): sc.Code = repoMethod match {
    case RepoMethod.SelectAll(rowType) =>
      code"def selectAll(implicit c: ${sc.Type.Connection}): ${sc.Type.List.of(rowType)}"
    case RepoMethod.SelectById(id, rowType) =>
      code"def selectById(${id.param})(implicit c: ${sc.Type.Connection}): ${sc.Type.Option.of(rowType)}"
    case RepoMethod.SelectAllByIds(_, idsParam, rowType) =>
      code"def selectByIds($idsParam)(implicit c: ${sc.Type.Connection}): ${sc.Type.List.of(rowType)}"
    case RepoMethod.SelectByUnique(params, rowType) =>
      val ident = Naming.camelCase(Array("selectByUnique") ++ params.map(_.name.value))
      code"def $ident(${params.map(_.param.code).mkCode(", ")})(implicit c: ${sc.Type.Connection}): ${sc.Type.Option.of(rowType)}"
    case RepoMethod.SelectByFieldValues(param, rowType) =>
      code"def selectByFieldValues($param)(implicit c: ${sc.Type.Connection}): ${sc.Type.List.of(rowType)}"
    case RepoMethod.UpdateFieldValues(id, param) =>
      code"def updateFieldValues(${id.param}, $param)(implicit c: ${sc.Type.Connection}): ${sc.Type.Int}"
    case RepoMethod.InsertDbGeneratedKey(id, _, unsavedParam, _) =>
      code"def insert($unsavedParam)(implicit c: ${sc.Type.Connection}): ${id.tpe}"
    case RepoMethod.InsertProvidedKey(id, _, unsavedParam, _) =>
      code"def insert(${id.param}, $unsavedParam)(implicit c: ${sc.Type.Connection}): ${sc.Type.Boolean}"
    case RepoMethod.InsertOnlyKey(id) =>
      code"def insert(${id.param})(implicit c: ${sc.Type.Connection}): ${sc.Type.Boolean}"
    case RepoMethod.Delete(id) =>
      code"def delete(${id.param})(implicit c: ${sc.Type.Connection}): ${sc.Type.Boolean}"
    case RepoMethod.SqlScript(sqlScript) =>
      val params = sqlScript.params match {
        case Nil      => sc.Code.Empty
        case nonEmpty => nonEmpty.map { param => sc.Param(param.name, param.tpe).code }.mkCode(",\n                     ")
      }
      code"def apply($params)(implicit c: ${sc.Type.Connection}): ${sc.Type.List.of(sc.Type.Qualified(sqlScript.RowName))}"
  }

  def matchId(id: IdComputed): sc.Code =
    id match {
      case id: IdComputed.Unary =>
        code"${id.col.dbName.value} = $$${id.paramName}"
      case compositive: IdComputed.Composite =>
        code"${compositive.cols.map(cc => code"${cc.dbName.value} = $${${compositive.paramName}.${cc.name}}").mkCode(", ")}"
    }

  def matchAnyId(x: IdComputed.Unary, idsParam: sc.Param): sc.Code =
    code"${x.col.dbName.value} in $$${idsParam.name}"

  override def repoImpl(table: RelationComputed, repoMethod: RepoMethod): sc.Code =
    repoMethod match {
      case RepoMethod.SelectAll(_) =>
        val joinedColNames = table.cols.map(_.dbName.value).mkString(", ")
        val sql = interpolate(code"""select $joinedColNames from ${table.relationName}""")
        code"""$sql.as(${table.RowName}.$rowParserIdent("").*)"""

      case RepoMethod.SelectById(id, _) =>
        val joinedColNames = table.cols.map(_.dbName.value).mkString(", ")
        val sql = interpolate(code"""select $joinedColNames from ${table.relationName} where ${matchId(id)}""")
        code"""$sql.as(${table.RowName}.$rowParserIdent("").singleOpt)"""

      case RepoMethod.SelectAllByIds(unaryId, idsParam, _) =>
        val joinedColNames = table.cols.map(_.dbName.value).mkString(", ")
        val sql = interpolate(code"""select $joinedColNames from ${table.relationName} where ${matchAnyId(unaryId, idsParam)}""")
        code"""$sql.as(${table.RowName}.$rowParserIdent("").*)"""

      case RepoMethod.SelectByUnique(params, _) =>
        val args = params.map { param => code"${table.FieldValueName}.${param.name}(${param.name})" }.mkCode(", ")
        code"""selectByFieldValues(${sc.Type.List}($args)).headOption"""

      case RepoMethod.SelectByFieldValues(param, _) =>
        val cases: Seq[sc.Code] =
          table.cols.map(col =>
            code"case ${table.FieldValueName}.${col.name}(value) => $NamedParameter(${sc.StrLit(col.dbName.value)}, $ParameterValue.from(value))"
          )

        val sql = sc.s(
          code"""select * from ${table.relationName} where $${namedParams.map(x => s"$${x.name} = {$${x.name}}").mkString(" AND ")}"""
        )
        code"""${param.name} match {
              |      case Nil => selectAll
              |      case nonEmpty =>
              |        val namedParams = nonEmpty.map{
              |          ${cases.mkCode("\n          ")}
              |        }
              |        val q = $sql
              |        $SQL(q)
              |          .on(namedParams: _*)
              |          .as(${table.RowName}.$rowParserIdent("").*)
              |    }
              |""".stripMargin

      case RepoMethod.UpdateFieldValues(id, param) =>
        val cases: Seq[sc.Code] =
          table.cols.map { col =>
            code"case ${table.FieldValueName}.${col.name}(value) => $NamedParameter(${sc.StrLit(col.dbName.value)}, $ParameterValue.from(value))"
          }

        val sql = sc.s(
          code"""update ${table.relationName}
                |          set $${namedParams.map(x => s"$${x.name} = {$${x.name}}").mkString(", ")}
                |          where ${matchId(id)}""".stripMargin
        )
        code"""${param.name} match {
              |      case Nil => 0
              |      case nonEmpty =>
              |        val namedParams = nonEmpty.map{
              |          ${cases.mkCode("\n          ")}
              |        }
              |        val q = $sql
              |        $SQL(q)
              |          .on(namedParams: _*)
              |          .executeUpdate()
              |    }
              |""".stripMargin

      case RepoMethod.InsertDbGeneratedKey(id, colsUnsaved, unsavedParam, default) =>
        val maybeNamedParameters = colsUnsaved.map {
          case ColumnComputed(_, ident, sc.Type.TApply(default.DefaultedType, List(tpe)), dbName, _, _) =>
            code"""|${unsavedParam.name}.$ident match {
                   |        case ${default.UseDefault} => None
                   |        case ${default.Provided}(value) => Some($NamedParameter(${sc.StrLit(dbName.value)}, $ParameterValue.from[$tpe](value)))
                   |      }"""
          case col =>
            code"""Some($NamedParameter(${sc.StrLit(col.dbName.value)}, $ParameterValue.from(${unsavedParam.name}.${col.name})))"""
        }

        val sql = interpolate(
          code"""|insert into ${table.relationName}($${namedParameters.map(_.name).mkString(", ")})
                 |      values ($${namedParameters.map(np => s"{$${np.name}}").mkString(", ")})
                 |      returning ${id.cols.map(_.name.value.code).mkCode(", ")}
                 |      """.stripMargin
        )

        // don't remand that user-specified id has a correct rowParser definition when we can trivially infer it
        val rowParser = id match {
          case IdComputed.UnaryUserSpecified(col, tpe) =>
            code"""$SqlParser.get[$tpe](${sc.StrLit(col.dbName.value)})"""
          case other =>
            code"""${other.tpe}.$rowParserIdent("")"""
        }

        code"""|val namedParameters = List(
               |      ${maybeNamedParameters.mkCode(",\n      ")}
               |    ).flatten
               |
               |    $sql
               |      .on(namedParameters :_*)
               |      .executeInsert($rowParser.single)
               |"""

      case RepoMethod.InsertProvidedKey(id, colsUnsaved, unsavedParam, default) =>
        val maybeNamedParameters = colsUnsaved.map {
          case ColumnComputed(_, ident, sc.Type.TApply(default.DefaultedType, List(tpe)), dbName, _, _) =>
            code"""|${unsavedParam.name}.$ident match {
                   |        case ${default.UseDefault} => None
                   |        case ${default.Provided}(value) => Some($NamedParameter(${sc.StrLit(dbName.value)}, $ParameterValue.from[$tpe](value)))
                   |      }"""
          case col =>
            code"""Some($NamedParameter(${sc.StrLit(col.dbName.value)}, $ParameterValue.from(${unsavedParam.name}.${col.name})))"""
        }

        val joinedIdColNames = id.cols.map(_.dbName.value).mkString(", ")
        val idValues: List[sc.Code] = id match {
          case id: IdComputed.Unary     => List(code"$${${id.paramName}}")
          case id: IdComputed.Composite => id.cols.map(cc => code"$${${id.paramName}.${cc.name}}")
        }

        val sql = interpolate(
          code"""|insert into ${table.relationName}($joinedIdColNames, $${namedParameters.map(_.name).mkString(", ")})
                 |      values (${idValues.mkCode(", ")}, $${namedParameters.map(np => s"{$${np.name}}").mkString(", ")})
                 |      """.stripMargin
        )

        code"""|val namedParameters = List(
               |      ${maybeNamedParameters.mkCode(",\n      ")}
               |    ).flatten
               |
               |    $sql
               |      .on(namedParameters :_*)
               |      .execute()
               |"""

      case RepoMethod.InsertOnlyKey(_) => code"???"
      case RepoMethod.Delete(id) =>
        val sql = interpolate(code"""delete from ${table.relationName} where ${matchId(id)}""")
        code"$sql.executeUpdate() > 0"
      case RepoMethod.SqlScript(sqlScript) =>
        val renderedScript = sqlScript.script.decomposedSql.render { (paramAtIndex: Int) =>
          val param = sqlScript.params.find(_.underlying.indices.contains(paramAtIndex)).get
          s"$$${param.name.value}"
        }
        code"""|val sql = ${interpolate(renderedScript)}
               |    sql.as(${sc.Type.Qualified(sqlScript.RowName)}.$rowParserIdent("").*)
               |""".stripMargin
    }

  override def missingInstances: List[sc.Code] = {
    def inout(tpe: sc.Type) = code"${ToStatement(tpe)} with ${ParameterMetaData.of(tpe)} with ${Column(tpe)}"
    def out(tpe: sc.Type) = code"${ToStatement(tpe)} with ${ParameterMetaData.of(tpe)}"

    val arrayTypes = List(
      sc.Type.String -> sc.StrLit("_varchar"),
      sc.Type.Float -> sc.StrLit("_float4"),
      sc.Type.Short -> sc.StrLit("_int2"),
      sc.Type.Int -> sc.StrLit("_int4"),
      sc.Type.Long -> sc.StrLit("_int8"),
      sc.Type.Boolean -> sc.StrLit("_bool"),
      sc.Type.Double -> sc.StrLit("_float8"),
      sc.Type.UUID -> sc.StrLit("_uuid"),
      sc.Type.BigDecimal -> sc.StrLit("_decimal"),
      sc.Type.PGobject -> sc.StrLit("_aclitem")
    )

    val arrayInstances = arrayTypes.map { case (tpe, elemType) =>
      val arrayType = sc.Type.Array.of(tpe)
      code"""|implicit val ${tpe.value.name}Array: ${out(arrayType)} = new ${out(arrayType)} {
             |    override def sqlType: ${sc.Type.String} = $elemType
             |    override def jdbcType: ${sc.Type.Int} = ${sc.Type.Types}.ARRAY
             |    override def set(ps: ${sc.Type.PreparedStatement}, index: ${sc.Type.Int}, v: $arrayType): ${sc.Type.Unit} = ps.setArray(index, ps.getConnection.createArrayOf($elemType, v.map(x => x)))
             |  }
             |""".stripMargin
    }

    val postgresTypes = PostgresTypes.all.map { case (typeName, tpe) =>
      val either = sc.Type.Either.of(SqlRequestError, tpe)
      code"""|implicit val ${tpe.value.name}Db: ${inout(tpe)} = new ${inout(tpe)} {
             |    override def sqlType: ${sc.Type.String} = $typeName
             |    override def jdbcType: ${sc.Type.Int} = ${sc.Type.Types}.OTHER
             |    override def set(s: ${sc.Type.PreparedStatement}, index: ${sc.Type.Int}, v: $tpe): ${sc.Type.Unit} = s.setObject(index, v)
             |    override def apply(v1: ${sc.Type.Any}, v2: $MetaDataItem): $either = ${sc.Type.Right}(v1.asInstanceOf[$tpe])
             |  }
             |""".stripMargin
    }
    val hstore = {
      val tpe = sc.Type.JavaMap.of(sc.Type.String, sc.Type.String)
      val either = sc.Type.Either.of(SqlRequestError, tpe)
      code"""|implicit val hstoreDb: ${inout(tpe)} = new ${inout(tpe)} {
             |    override def sqlType: ${sc.Type.String} = "hstore"
             |    override def jdbcType: ${sc.Type.Int} = ${sc.Type.Types}.OTHER
             |    override def set(s: ${sc.Type.PreparedStatement}, index: ${sc.Type.Int}, v: $tpe): ${sc.Type.Unit} = s.setObject(index, v)
             |    override def apply(v1: ${sc.Type.Any}, v2: $MetaDataItem): $either = ${sc.Type.Right}(v1.asInstanceOf[$tpe])
             |  }
             |""".stripMargin
    }

    val pgObject = {
      val tpe = sc.Type.PGobject
      val either = sc.Type.Either.of(SqlRequestError, tpe)
      code"""|implicit val pgObjectDb: ${inout(tpe)} = new ${inout(tpe)} {
           |    override def sqlType: ${sc.Type.String} = "hstore"
           |    override def jdbcType: ${sc.Type.Int} = ${sc.Type.Types}.OTHER
           |    override def set(s: ${sc.Type.PreparedStatement}, index: ${sc.Type.Int}, v: $tpe): ${sc.Type.Unit} = s.setObject(index, v)
           |    override def apply(v1: ${sc.Type.Any}, v2: $MetaDataItem): $either = ${sc.Type.Right}(v1.asInstanceOf[$tpe])
           |  }
           |""".stripMargin
    }

    arrayInstances ++ postgresTypes ++ List(hstore, pgObject)
  }
}

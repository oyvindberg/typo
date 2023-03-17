package typo

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
    List(column, toStatement)
  }

  override def instances(tpe: sc.Type, cols: Seq[ColumnComputed]): List[sc.Code] = {
    val mappedValues = cols.map { x => code"${x.name} = row[${x.tpe}](${sc.StrLit(x.dbName.value)})" }
    val rowParser = code"""implicit val $rowParserIdent: ${RowParser(tpe)} = { row =>
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
    val rowParser = code"implicit val $rowParserIdent: ${RowParser(wrapperType)} = $SqlParser.get[$wrapperType](${sc.StrLit(colName.value)})"
    List(toStatement, column, rowParser)
  }

  override def repoSig(repoMethod: RepoMethod): sc.Code = repoMethod match {
    case RepoMethod.SelectAll(rowType) =>
      code"def selectAll(implicit c: ${sc.Type.Connection}): ${sc.Type.List.of(rowType)}"
    case RepoMethod.SelectById(id, rowType) =>
      code"def selectById(${id.param})(implicit c: ${sc.Type.Connection}): ${sc.Type.Option.of(rowType)}"
    case RepoMethod.SelectAllByIds(_, idsParam, rowType) =>
      code"def selectByIds($idsParam)(implicit c: ${sc.Type.Connection}): ${sc.Type.List.of(rowType)}"
    case RepoMethod.SelectByUnique(params, rowType) =>
      code"def selectByUnique(${params.map(_.code).mkCode(", ")})(implicit c: ${sc.Type.Connection}): ${sc.Type.Option.of(rowType)}"
    case RepoMethod.SelectByFieldValues(param, rowType) =>
      code"def selectByFieldValues($param)(implicit c: ${sc.Type.Connection}): ${sc.Type.List.of(rowType)}"
    case RepoMethod.UpdateFieldValues(id, param) =>
      code"def updateFieldValues(${id.param}, $param)(implicit c: ${sc.Type.Connection}): ${sc.Type.Int}"
    case RepoMethod.InsertDbGeneratedKey(id, _, unsavedParam, _) =>
      code"def insert($unsavedParam)(implicit c: ${sc.Type.Connection}): ${id.tpe}"
    case RepoMethod.InsertProvidedKey(id, _, unsavedParam, _) =>
      code"def insert(${id.param}, $unsavedParam)(implicit c: ${sc.Type.Connection}): ${sc.Type.Unit}"
    case RepoMethod.InsertOnlyKey(id) =>
      code"def insert(${id.param})(implicit c: ${sc.Type.Connection}): ${sc.Type.Unit}"
    case RepoMethod.Delete(id) =>
      code"def delete(${id.param})(implicit c: ${sc.Type.Connection}): ${sc.Type.Boolean}"
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
        code"""$sql.as(${table.RowName}.$rowParserIdent.*)"""

      case RepoMethod.SelectById(id, _) =>
        val joinedColNames = table.cols.map(_.dbName.value).mkString(", ")
        val sql = interpolate(code"""select $joinedColNames from ${table.relationName} where ${matchId(id)}""")
        code"""$sql.as(${table.RowName}.$rowParserIdent.singleOpt)"""

      case RepoMethod.SelectAllByIds(unaryId, idsParam, _) =>
        val joinedColNames = table.cols.map(_.dbName.value).mkString(", ")
        val sql = interpolate(code"""select $joinedColNames from ${table.relationName} where ${matchAnyId(unaryId, idsParam)}""")
        code"""$sql.as(${table.RowName}.$rowParserIdent.*)"""

      case RepoMethod.SelectByUnique(_, _) => "???"

      case RepoMethod.SelectByFieldValues(param, _) =>
        val cases: Seq[sc.Code] =
          table.cols.map(col =>
            code"case ${table.FieldValueName}.${col.name}(value) => $NamedParameter(${sc.StrLit(col.dbName.value)}, $ParameterValue.from(value))"
          )

        code""""""
        val sql = interpolate(
          code"""select * from ${table.relationName} where $${namedParams.map(x => s"$${x.name} = {$${x.name}}").mkString(" AND ")}"""
        )
        code"""${param.name} match {
              |      case Nil => selectAll
              |      case nonEmpty =>
              |        val namedParams = nonEmpty.map{
              |          ${cases.mkCode("\n          ")}
              |        }
              |        $sql
              |          .on(namedParams: _*)
              |          .as(${table.RowName}.$rowParserIdent.*)
              |    }
              |""".stripMargin

      case RepoMethod.UpdateFieldValues(id, param) =>
        val cases: Seq[sc.Code] =
          table.cols.map { col =>
            code"case ${table.FieldValueName}.${col.name}(value) => $NamedParameter(${sc.StrLit(col.dbName.value)}, $ParameterValue.from(value))"
          }

        val sql = interpolate(
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
              |        $sql
              |          .on(namedParams: _*)
              |          .executeUpdate()
              |    }
              |""".stripMargin

      case RepoMethod.InsertDbGeneratedKey(id, colsUnsaved, unsavedParam, default) =>
        val maybeNamedParameters = colsUnsaved.map {
          case ColumnComputed(ident, sc.Type.TApply(default.DefaultedType, List(tpe)), dbName, _) =>
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
                 |      returning ${table.maybeId.get.cols.map(_.name.value.code).mkCode(", ")}
                 |      """.stripMargin
        )

        code"""|val namedParameters = List(
               |      ${maybeNamedParameters.mkCode(",\n      ")}
               |    ).flatten
               |
               |    $sql
               |      .on(namedParameters :_*)
               |      .executeInsert(${id.tpe}.$rowParserIdent.single)
               |"""

      case RepoMethod.InsertProvidedKey(id, colsUnsaved, unsavedParam, default) =>
        val maybeNamedParameters = colsUnsaved.map {
          case ColumnComputed(ident, sc.Type.TApply(default.DefaultedType, List(tpe)), dbName, _) =>
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
    }
}

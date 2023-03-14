package typo

import typo.sc.syntax.*

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

  override def instances(tpe: sc.Type, scalaFields: Seq[(sc.Ident, sc.Type, db.Col)]): List[sc.Code] = {
    val mappedValues = scalaFields.map { case (name, tpe, col) => code"$name = row[$tpe](${sc.StrLit(col.name.value)})" }
    val rowParser = code"""implicit val rowParser: ${RowParser(tpe)} = { row =>
            |    $Success(
            |      $tpe(
            |        ${mappedValues.mkCode(",\n        ")}
            |      )
            |    )
            |  }
            |""".stripMargin

    List(rowParser)
  }
  override def anyValInstances(wrapperType: sc.Type.Qualified, underlying: sc.Type): List[sc.Code] = {
    val toStatement = code"""implicit val toStatement: ${ToStatement(wrapperType)} = implicitly[${ToStatement(underlying)}].contramap(_.value)"""
    val column = code"""implicit val column: ${Column(wrapperType)} = implicitly[${Column(underlying)}].map($wrapperType.apply)"""
    List(toStatement, column)
  }

  override def repoSig(repoMethod: RepoMethod): sc.Code = repoMethod match {
    case RepoMethod.SelectAll(rowType) =>
      code"def selectAll(implicit c: ${sc.Type.Connection}): ${sc.Type.List(rowType)}"
    case RepoMethod.SelectById(idParam, rowType) =>
      code"def selectById($idParam)(implicit c: ${sc.Type.Connection}): ${sc.Type.Option(rowType)}"
    case RepoMethod.SelectAllByIds(idParam, rowType) =>
      code"def selectByIds($idParam)(implicit c: ${sc.Type.Connection}): ${sc.Type.List(rowType)}"
    case RepoMethod.SelectByUnique(params, rowType) =>
      code"def selectByUnique(${params.map(_.code).mkCode(", ")})(implicit c: ${sc.Type.Connection}): ${sc.Type.Option(rowType)}"
    case RepoMethod.SelectByFieldValues(param, rowType) =>
      code"def selectByFieldValues($param)(implicit c: ${sc.Type.Connection}): ${sc.Type.List(rowType)}"
    case RepoMethod.UpdateFieldValues(idParam, param) =>
      code"def updateFieldValues($idParam, $param)(implicit c: ${sc.Type.Connection}): ${sc.Type.Int}"
    case RepoMethod.InsertDbGeneratedKey(param, idType) =>
      code"def insert($param)(implicit c: ${sc.Type.Connection}): $idType"
    case RepoMethod.InsertProvidedKey(idParam, unsavedParam) =>
      code"def insert($idParam, $unsavedParam)(implicit c: ${sc.Type.Connection}): ${sc.Type.Unit}"
    case RepoMethod.InsertOnlyKey(idParam) =>
      code"def insert($idParam)(implicit c: ${sc.Type.Connection}): ${sc.Type.Unit}"
    case RepoMethod.Delete(idParam) =>
      code"def delete($idParam)(implicit c: ${sc.Type.Connection}): ${sc.Type.Boolean}"
  }

  override def repoImpl(table: TableComputed, default: DefaultComputed, repoMethod: RepoMethod): sc.Code =
    repoMethod match {
      case RepoMethod.SelectAll(_) =>
        val joinedColNames = table.table.cols.map(_.name.value).mkString(", ")
        val sql = interpolate(code"""select $joinedColNames from ${table.table.name.value}""")
        code"""$sql.as(${table.RowName}.rowParser.*)"""

      case RepoMethod.SelectById(idParam, _) =>
        val joinedColNames = table.table.cols.map(_.name.value).mkString(", ")
        val sql = interpolate(code"""select $joinedColNames from ${table.table.name.value} where ${table.maybeId.get.col.name.value} = $$${idParam.name}""")
        code"""$sql.as(${table.RowName}.rowParser.singleOpt)"""

      case RepoMethod.SelectAllByIds(idsParam, _) =>
        val joinedColNames = table.table.cols.map(_.name.value).mkString(", ")
        val sql = interpolate(code"""select $joinedColNames from ${table.table.name.value} where ${table.maybeId.get.col.name.value} in $$${idsParam.name}""")
        code"""$sql.as(${table.RowName}.rowParser.*)"""

      case RepoMethod.SelectByUnique(_, _) => "???"

      case RepoMethod.SelectByFieldValues(param, _) =>
        val cases: Seq[sc.Code] =
          table.scalaFields.map { case (name, _, col) =>
            code"case ${table.FieldValueName}.$name(value) => $NamedParameter(${sc.StrLit(col.name.value)}, $ParameterValue.from(value))"
          }

        code""""""
        val sql = interpolate(
          code"""select * from ${table.table.name.value} where $${namedParams.map(x => s"$${x.name} = {$${x.name}}").mkString(" AND ")}"""
        )
        code"""${param.name} match {
              |      case Nil => selectAll
              |      case nonEmpty =>
              |        val namedParams = nonEmpty.map{
              |          ${cases.mkCode("\n          ")}
              |        }
              |        $sql
              |          .on(namedParams: _*)
              |          .as(${table.RowName}.rowParser.*)
              |    }
              |""".stripMargin

      case RepoMethod.UpdateFieldValues(idParam, param) =>
        val cases: Seq[sc.Code] =
          table.scalaFields.map { case (name, _, col) =>
            code"case ${table.FieldValueName}.$name(value) => $NamedParameter(${sc.StrLit(col.name.value)}, $ParameterValue.from(value))"
          }

        val sql = interpolate(
          code"""update ${table.table.name.value}
                   |          set $${namedParams.map(x => s"$${x.name} = {$${x.name}}").mkString(", ")}
                   |          where ${table.maybeId.get.col.name.value} = $${${idParam.name}}}""".stripMargin
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

      case RepoMethod.InsertDbGeneratedKey(unsavedParam, idType) =>
        val maybeNamedParameters = table.scalaFieldsUnsaved.map {
          case (ident, sc.Type.TApply(default.DefaultedType, List(tpe)), col) =>
            code"""|${unsavedParam.name}.$ident match {
                   |        case ${default.UseDefault} => None
                   |        case ${default.Provided}(value) => Some($NamedParameter(${sc.StrLit(col.name.value)}, $ParameterValue.from[$tpe](value)))
                   |      }"""
          case (ident, _, col) =>
            code"""Some($NamedParameter(${sc.StrLit(col.name.value)}, $ParameterValue.from(${unsavedParam.name}.$ident)))"""
        }

        val sql = interpolate(
          code"""|insert into ${table.table.name.value}($${namedParameters.map(_.name).mkString(", ")})
                 |      values ($${namedParameters.map(np => s"{$${np.name}}").mkString(", ")})
                 |      returning ${table.maybeId.get.col.name.value}
                 |      """.stripMargin
        )

        code"""|val namedParameters = List(
               |      ${maybeNamedParameters.mkCode(",\n      ")}
               |    ).flatten
               |
               |    $sql
               |      .on(namedParameters :_*)
               |      .executeInsert($SqlParser.get[$idType](${sc.StrLit({ table.maybeId.get.col.name.value })}).single)
               |"""
      case RepoMethod.InsertProvidedKey(idParam, unsavedParam) =>
        val maybeNamedParameters = table.scalaFieldsUnsaved.map {
          case (ident, sc.Type.TApply(default.DefaultedType, List(tpe)), col) =>
            code"""|${unsavedParam.name}.$ident match {
                   |        case ${default.UseDefault} => None
                   |        case ${default.Provided}(value) => Some($NamedParameter(${sc.StrLit(col.name.value)}, $ParameterValue.from[$tpe](value)))
                   |      }"""
          case (ident, _, col) =>
            code"""Some($NamedParameter(${sc.StrLit(col.name.value)}, $ParameterValue.from(${unsavedParam.name}.$ident)))"""
        }

        val sql = interpolate(
          code"""|insert into ${table.table.name.value}(${table.maybeId.get.col.name.value}, $${namedParameters.map(_.name).mkString(", ")})
                 |      values ($${${idParam.name}}, $${namedParameters.map(np => s"{$${np.name}}").mkString(", ")})
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
      case RepoMethod.Delete(idParam) =>
        val sql = interpolate(
          code"""delete from ${table.table.name.value} where ${table.maybeId.get.col.name.value} = $${${idParam.name}}}"""
        )
        code"$sql.executeUpdate() > 0"
    }
}

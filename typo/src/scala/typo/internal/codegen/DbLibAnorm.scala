package typo
package internal
package codegen

import typo.internal.analysis.MaybeReturnsRows

class DbLibAnorm(pkg: sc.QIdent, inlineImplicits: Boolean, default: ComputedDefault, enableStreamingInserts: Boolean) extends DbLib {

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
  val arrayParameterMetaDataName = sc.Ident("arrayParameterMetaData")
  val textSupport: Option[DbLibTextSupport] =
    if (enableStreamingInserts) Some(new DbLibTextSupport(pkg, inlineImplicits, None, default)) else None

  override val additionalFiles: List[typo.sc.File] =
    textSupport match {
      case Some(textSupport) =>
        List(
          sc.File(textSupport.Text, DbLibTextImplementations.Text, Nil),
          sc.File(textSupport.streamingInsert, DbLibTextImplementations.streamingInsertAnorm(textSupport.Text), Nil)
        )
      case None => Nil
    }

  def runtimeInterpolateValue(name: sc.Code, tpe: sc.Type, forbidInline: Boolean = false): sc.Code =
    if (inlineImplicits && !forbidInline)
      code"$${$ParameterValue($name, null, ${lookupToStatementFor(tpe)})}"
    else code"$${$name}"

  def dbNames(cols: NonEmptyList[ComputedColumn], isRead: Boolean): sc.Code =
    cols
      .map(c => c.dbName.code ++ (if (isRead) SqlCast.fromPgCode(c) else sc.Code.Empty))
      .mkCode(", ")

  def matchId(id: IdComputed): sc.Code =
    id match {
      case id: IdComputed.Unary =>
        code"${id.col.dbName.code} = ${runtimeInterpolateValue(id.paramName, id.tpe)}"
      case composite: IdComputed.Composite =>
        code"${composite.cols.map(cc => code"${cc.dbName.code} = ${runtimeInterpolateValue(code"${composite.paramName}.${cc.name}", cc.tpe)}").mkCode(" AND ")}"
    }

  /** Resolve known implicits at generation-time instead of at compile-time */
  def lookupColumnFor(tpe: sc.Type): sc.Code =
    if (!inlineImplicits) sc.Summon(Column.of(tpe)).code
    else
      sc.Type.base(tpe) match {
        case TypesScala.BigDecimal     => code"$Column.columnToScalaBigDecimal"
        case TypesScala.Boolean        => code"$Column.columnToBoolean"
        case TypesScala.Byte           => code"$Column.columnToByte"
        case TypesScala.Double         => code"$Column.columnToDouble"
        case TypesScala.Float          => code"$Column.columnToFloat"
        case TypesScala.Int            => code"$Column.columnToInt"
        case TypesScala.Long           => code"$Column.columnToLong"
        case TypesJava.String          => code"$Column.columnToString"
        case TypesJava.UUID            => code"$Column.columnToUUID"
        case TypesScala.Optional(targ) => code"$Column.columnToOption(${lookupColumnFor(targ)})"
        // generated type
        case x: sc.Type.Qualified if x.value.idents.startsWith(pkg.idents) =>
          code"$tpe.$columnName"
        // customized type mapping
        case x if missingInstancesByType.contains(Column.of(x)) =>
          code"${missingInstancesByType(Column.of(x))}"
        // generated array type
        case sc.Type.ArrayOf(targ: sc.Type.Qualified) if targ.value.idents.startsWith(pkg.idents) =>
          code"$targ.$arrayColumnName"
        case sc.Type.ArrayOf(TypesScala.Byte) => code"$Column.columnToByteArray"
        // fallback array case. implementation looks loco, but I guess it works
        case sc.Type.ArrayOf(targ) => code"$Column.columnToArray[$targ](${lookupColumnFor(targ)}, implicitly)"
        case other                 => sc.Summon(Column.of(other)).code
      }

  /** Resolve known implicits at generation-time instead of at compile-time */
  def lookupParameterMetaDataFor(tpe: sc.Type): sc.Code =
    if (!inlineImplicits) sc.Summon(ParameterMetaData.of(tpe)).code
    else
      sc.Type.base(tpe) match {
        case TypesScala.BigDecimal => code"$ParameterMetaData.BigDecimalParameterMetaData"
        case TypesScala.Boolean    => code"$ParameterMetaData.BooleanParameterMetaData"
        case TypesScala.Byte       => code"$ParameterMetaData.ByteParameterMetaData"
        case TypesScala.Double     => code"$ParameterMetaData.DoubleParameterMetaData"
        case TypesScala.Float      => code"$ParameterMetaData.FloatParameterMetaData"
        case TypesScala.Int        => code"$ParameterMetaData.IntParameterMetaData"
        case TypesScala.Long       => code"$ParameterMetaData.LongParameterMetaData"
        case TypesJava.String      => code"$ParameterMetaData.StringParameterMetaData"
        case TypesJava.UUID        => code"$ParameterMetaData.UUIDParameterMetaData"
//        case ScalaTypes.Optional(targ) => lookupParameterMetaDataFor(targ)
        // generated type
        case x: sc.Type.Qualified if x.value.idents.startsWith(pkg.idents) =>
          code"$tpe.$parameterMetadataName"
        // customized type mapping
        case x if missingInstancesByType.contains(ParameterMetaData.of(x)) =>
          code"${missingInstancesByType(ParameterMetaData.of(x))}"
        // generated array type
//        case sc.Type.TApply(ScalaTypes.Array, List(targ: sc.Type.Qualified)) if targ.value.idents.startsWith(pkg.idents) =>
//          code"$targ.$arrayColumnName"
        case sc.Type.ArrayOf(TypesScala.Byte) => code"$ParameterMetaData.ByteArrayParameterMetaData"
        // fallback array case.
        case sc.Type.ArrayOf(targ) => code"${pkg / arrayParameterMetaDataName}(${lookupParameterMetaDataFor(targ)})"
        case other                 => sc.Summon(ParameterMetaData.of(other)).code
      }

  /** Resolve known implicits at generation-time instead of at compile-time */
  def lookupToStatementFor(tpe: sc.Type): sc.Code =
    if (!inlineImplicits) sc.Summon(ToStatement.of(tpe)).code
    else
      sc.Type.base(tpe) match {
        case TypesScala.BigDecimal     => code"$ToStatement.scalaBigDecimalToStatement"
        case TypesScala.Boolean        => code"$ToStatement.booleanToStatement"
        case TypesScala.Byte           => code"$ToStatement.byteToStatement"
        case TypesScala.Double         => code"$ToStatement.doubleToStatement"
        case TypesScala.Float          => code"$ToStatement.floatToStatement"
        case TypesScala.Int            => code"$ToStatement.intToStatement"
        case TypesScala.Long           => code"$ToStatement.longToStatement"
        case TypesJava.String          => code"$ToStatement.stringToStatement"
        case TypesJava.UUID            => code"$ToStatement.uuidToStatement"
        case TypesScala.Optional(targ) => code"$ToStatement.optionToStatement(${lookupToStatementFor(targ)}, ${lookupParameterMetaDataFor(targ)})"
        // generated type
        case x: sc.Type.Qualified if x.value.idents.startsWith(pkg.idents) =>
          code"$tpe.$toStatementName"
        // customized type mapping
        case x if missingInstancesByType.contains(ToStatement.of(x)) =>
          code"${missingInstancesByType(ToStatement.of(x))}"
        case sc.Type.ArrayOf(TypesScala.Byte) => code"$ToStatement.byteArrayToStatement"
        // generated array type
        case sc.Type.ArrayOf(targ: sc.Type.Qualified) if targ.value.idents.startsWith(pkg.idents) =>
          code"$targ.$arrayToStatementName"
        // fallback array case.
        case sc.Type.ArrayOf(targ) =>
          // `ToStatement.arrayToParameter` does not work for arbitrary types. if it's a user-defined type, user needs to provide this too
          if (sc.Type.containsUserDefined(tpe)) // should be `targ`, but this information is stripped in `sc.Type.base` above
            code"$targ.arrayToStatement"
          else code"$ToStatement.arrayToParameter(${lookupParameterMetaDataFor(targ)})"
        case other => sc.Summon(ToStatement.of(other)).code
      }

  override def repoSig(repoMethod: RepoMethod): sc.Code = {
    val name = repoMethod.methodName
    repoMethod match {
      case RepoMethod.SelectBuilder(_, fieldsType, rowType) =>
        code"def $name: ${sc.Type.dsl.SelectBuilder.of(fieldsType, rowType)}"
      case RepoMethod.SelectAll(_, _, rowType) =>
        code"def $name(implicit c: ${TypesJava.Connection}): ${TypesScala.List.of(rowType)}"
      case RepoMethod.SelectById(_, _, id, rowType) =>
        code"def $name(${id.param})(implicit c: ${TypesJava.Connection}): ${TypesScala.Option.of(rowType)}"
      case RepoMethod.SelectByIds(_, _, idComputed, idsParam, rowType) =>
        val usedDefineds = idComputed.userDefinedCols.zipWithIndex.map { case (col, i) => sc.Param(sc.Ident(s"toStatement$i"), ToStatement.of(sc.Type.ArrayOf(col.tpe)), None) }
        val params = sc.Param(sc.Ident("c"), TypesJava.Connection, None) :: usedDefineds
        code"def $name($idsParam)(implicit ${params.map(_.code).mkCode(", ")}): ${TypesScala.List.of(rowType)}"
      case RepoMethod.SelectByIdsTracked(x) =>
        val usedDefineds = x.idComputed.userDefinedCols.zipWithIndex.map { case (col, i) => sc.Param(sc.Ident(s"toStatement$i"), ToStatement.of(sc.Type.ArrayOf(col.tpe)), None) }
        val params = sc.Param(sc.Ident("c"), TypesJava.Connection, None) :: usedDefineds
        code"def $name(${x.idsParam})(implicit ${params.map(_.code).mkCode(", ")}): ${TypesScala.Map.of(x.idComputed.tpe, TypesScala.Option.of(x.rowType))}"
      case RepoMethod.SelectByUnique(_, keyColumns, _, rowType) =>
        code"def $name(${keyColumns.map(_.param.code).mkCode(", ")})(implicit c: ${TypesJava.Connection}): ${TypesScala.Option.of(rowType)}"
      case RepoMethod.SelectByFieldValues(_, _, _, fieldValueOrIdsParam, rowType) =>
        code"def $name($fieldValueOrIdsParam)(implicit c: ${TypesJava.Connection}): ${TypesScala.List.of(rowType)}"
      case RepoMethod.UpdateBuilder(_, fieldsType, rowType) =>
        code"def $name: ${sc.Type.dsl.UpdateBuilder.of(fieldsType, rowType)}"
      case RepoMethod.UpdateFieldValues(_, id, varargs, _, _, _) =>
        code"def $name(${id.param}, $varargs)(implicit c: ${TypesJava.Connection}): ${TypesScala.Boolean}"
      case RepoMethod.Update(_, _, _, param, _) =>
        code"def $name($param)(implicit c: ${TypesJava.Connection}): ${TypesScala.Boolean}"
      case RepoMethod.Insert(_, _, unsavedParam, rowType) =>
        code"def $name($unsavedParam)(implicit c: ${TypesJava.Connection}): $rowType"
      case RepoMethod.InsertStreaming(_, _, rowType) =>
        code"def $name(unsaved: ${TypesScala.Iterator.of(rowType)}, batchSize: ${TypesScala.Int})(implicit c: ${TypesJava.Connection}): ${TypesScala.Long}"
      case RepoMethod.Upsert(_, _, _, unsavedParam, rowType) =>
        code"def $name($unsavedParam)(implicit c: ${TypesJava.Connection}): $rowType"
      case RepoMethod.InsertUnsaved(_, _, _, unsavedParam, _, rowType) =>
        code"def $name($unsavedParam)(implicit c: ${TypesJava.Connection}): $rowType"
      case RepoMethod.InsertUnsavedStreaming(_, unsaved) =>
        code"def $name(unsaved: ${TypesScala.Iterator.of(unsaved.tpe)}, batchSize: ${TypesScala.Int})(implicit c: ${TypesJava.Connection}): ${TypesScala.Long}"
      case RepoMethod.DeleteBuilder(_, fieldsType, rowType) =>
        code"def $name: ${sc.Type.dsl.DeleteBuilder.of(fieldsType, rowType)}"
      case RepoMethod.Delete(_, id) =>
        code"def $name(${id.param})(implicit c: ${TypesJava.Connection}): ${TypesScala.Boolean}"
      case RepoMethod.DeleteByIds(_, idComputed, idsParam) =>
        val usedDefineds = idComputed.userDefinedCols.zipWithIndex.map { case (col, i) => sc.Param(sc.Ident(s"toStatement$i"), ToStatement.of(sc.Type.ArrayOf(col.tpe)), None) }
        val params = sc.Param(sc.Ident("c"), TypesJava.Connection, None) :: usedDefineds
        code"def $name($idsParam)(implicit ${params.map(_.code).mkCode(", ")}): ${TypesScala.Int}"
      case RepoMethod.SqlFile(sqlScript) =>
        val params = sc.Params(sqlScript.params.map(p => sc.Param(p.name, p.tpe, None)))
        val retType = sqlScript.maybeRowName match {
          case MaybeReturnsRows.Query(rowName) => TypesScala.List.of(rowName)
          case MaybeReturnsRows.Update         => TypesScala.Int
        }
        code"def $name$params(implicit c: ${TypesJava.Connection}): $retType"
    }
  }

  override def repoImpl(repoMethod: RepoMethod): sc.Code =
    repoMethod match {
      case RepoMethod.SelectBuilder(relName, fieldsType, rowType) =>
        code"""${sc.Type.dsl.SelectBuilderSql}(${sc.StrLit(relName.value)}, $fieldsType.structure, $rowType.rowParser)"""
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

      case RepoMethod.SelectByIds(relName, cols, computedId, idsParam, rowType) =>
        val joinedColNames = dbNames(cols, isRead = true)
        computedId match {
          case x: IdComputed.Composite =>
            val vals = x.cols.map(col => code"val ${col.name} = ${idsParam.name}.map(_.${col.name})").mkCode("\n")
            val sql = SQL {
              code"""|select ${joinedColNames}
                     |from $relName
                     |where (${x.cols.map(col => col.dbCol.name.code).mkCode(", ")}) 
                     |in (select ${x.cols.map(col => code"unnest(${runtimeInterpolateValue(col.name, col.tpe, forbidInline = true)})").mkCode(", ")})
                     |""".stripMargin
            }
            code"""|$vals
                   |$sql.as(${rowParserFor(rowType)}.*)
                   |""".stripMargin

          case x: IdComputed.Unary =>
            val sql = SQL {
              code"""|select ${joinedColNames}
                     |from $relName
                     |where ${x.col.dbName.code} = ANY(${runtimeInterpolateValue(idsParam.name, idsParam.tpe, forbidInline = true)})
                     |""".stripMargin
            }

            code"""|$sql.as(${rowParserFor(rowType)}.*)
                   |""".stripMargin
        }
      case RepoMethod.SelectByIdsTracked(x) =>
        code"""|val byId = ${x.methodName}(${x.idsParam.name}).view.map(x => (x.${x.idComputed.paramName}, x)).toMap
               |${x.idsParam.name}.view.map(id => (id, byId.get(id))).toMap""".stripMargin

      case RepoMethod.UpdateBuilder(relName, fieldsType, rowType) =>
        code"${sc.Type.dsl.UpdateBuilder}(${sc.StrLit(relName.value)}, $fieldsType.structure, $rowType.rowParser)"

      case RepoMethod.SelectByUnique(relName, keyColumns, allCols, rowType) =>
        val sql = SQL {
          code"""|select ${dbNames(allCols, isRead = true)}
                 |from $relName
                 |where ${keyColumns.map(c => code"${c.dbName.code} = ${runtimeInterpolateValue(c.name, c.tpe)}").mkCode(" AND ")}
                 |""".stripMargin
        }

        code"""|$sql.as(${rowParserFor(rowType)}.singleOpt)
               |""".stripMargin

      case RepoMethod.SelectByFieldValues(relName, cols, fieldValue, fieldValueOrIdsParam, rowType) =>
        val cases: NonEmptyList[sc.Code] =
          cols.map { col =>
            code"case $fieldValue.${col.name}(value) => $NamedParameter(${sc.StrLit(col.dbName.value)}, $ParameterValue(value, null, ${lookupToStatementFor(col.tpe)}))"
          }

        val sql = sc.s {
          code"""|select ${dbNames(cols, isRead = true)}
                 |from $relName
                 |where $${namedParameters.map(x => s"$$quote$${x.name}$$quote = {$${x.name}}").mkString(" AND ")}
                 |""".stripMargin
        }
        // the weird block and wildcard import is to avoid warnings in scala 2 and 3, and to get the implicit `on` in scala 3
        code"""${fieldValueOrIdsParam.name} match {
              |  case Nil => selectAll
              |  case nonEmpty =>
              |    val namedParameters = nonEmpty.map{
              |      ${cases.mkCode("\n")}
              |    }
              |    val quote = '"'.toString
              |    val q = $sql
              |    $SimpleSql($SQL(q), namedParameters.map(_.tupled).toMap, $RowParser.successful)
              |      .as(${rowParserFor(rowType)}.*)
              |}
              |""".stripMargin

      case RepoMethod.UpdateFieldValues(relName, id, varargs, fieldValue, cases0, _) =>
        val cases: NonEmptyList[sc.Code] =
          cases0.map { col =>
            code"case $fieldValue.${col.name}(value) => $NamedParameter(${sc.StrLit(col.dbName.value)}, $ParameterValue(value, null, ${lookupToStatementFor(col.tpe)}))"
          }
        val where: sc.Code =
          id.cols.map { col => code"${col.dbName.code} = {${col.name}}" }.mkCode(" AND ")

        val idCases: NonEmptyList[sc.Code] =
          id match {
            case unary: IdComputed.Unary =>
              NonEmptyList(code"(${sc.StrLit(unary.col.dbName.value)}, $ParameterValue(${id.paramName}, null, ${lookupToStatementFor(id.tpe)}))")
            case IdComputed.Composite(cols, _, paramName) =>
              cols.map { col =>
                code"(${sc.StrLit(col.dbName.value)}, $ParameterValue($paramName.${col.name}, null, ${lookupToStatementFor(col.tpe)}))"
              }
          }

        val sql = sc.s {
          code"""update $relName
                |set $${namedParameters.map(x => s"$$quote$${x.name}$$quote = {$${x.name}}").mkString(", ")}
                |where $where
                |""".stripMargin
        }

        // the weird block and wildcard import is to avoid warnings in scala 2 and 3, and to get the implicit `on` in scala 3
        code"""${varargs.name} match {
              |  case Nil => false
              |  case nonEmpty =>
              |    val namedParameters = nonEmpty.map{
              |      ${cases.mkCode("\n")}
              |    }
              |    val quote = '"'.toString
              |    val q = $sql
              |    $SimpleSql($SQL(q), namedParameters.map(_.tupled).toMap ++ ${TypesScala.List}(${idCases.mkCode(", ")}), $RowParser.successful)
              |      .executeUpdate() > 0
              |}
              |""".stripMargin
      case RepoMethod.Update(relName, _, id, param, colsUnsaved) =>
        val sql = SQL {
          val setCols = colsUnsaved.map { col =>
            code"${col.dbName.code} = ${runtimeInterpolateValue(code"${param.name}.${col.name}", col.tpe)}${SqlCast.toPgCode(col)}"
          }
          code"""|update $relName
                 |set ${setCols.mkCode(",\n")}
                 |where ${matchId(id)}
                 |""".stripMargin
        }
        code"""|val ${id.paramName} = ${param.name}.${id.paramName}
               |$sql.executeUpdate() > 0"""

      case RepoMethod.Insert(relName, cols, unsavedParam, rowType) =>
        val values = cols.map { c =>
          runtimeInterpolateValue(code"${unsavedParam.name}.${c.name}", c.tpe).code ++ SqlCast.toPgCode(c)
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
          runtimeInterpolateValue(code"${unsavedParam.name}.${c.name}", c.tpe).code ++ SqlCast.toPgCode(c)
        }

        val pickExcludedCols = cols.toList
          .filterNot(c => id.cols.exists(_.name == c.name))
          .map { c => code"${c.dbName.code} = EXCLUDED.${c.dbName.code}" }

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
          val colCast = sc.StrLit(SqlCast.toPg(col.dbCol).fold("")(_.withColons))
          code"""Some(($NamedParameter(${sc.StrLit(col.dbName.value)}, $ParameterValue(${unsavedParam.name}.${col.name}, null, ${lookupToStatementFor(col.tpe)})), $colCast))"""
        }
        val cases1 = unsaved.defaultCols.map { case (col @ ComputedColumn(_, ident, _, dbCol), origType) =>
          val dbName = sc.StrLit(dbCol.name.value)
          val colCast = sc.StrLit(SqlCast.toPg(col.dbCol).fold("")(_.withColons))

          code"""|${unsavedParam.name}.$ident match {
                   |  case ${default.Defaulted}.${default.UseDefault} => None
                   |  case ${default.Defaulted}.${default.Provided}(value) => Some(($NamedParameter($dbName, $ParameterValue(value, null, ${lookupToStatementFor(origType)})), $colCast))
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
               |  $SimpleSql($SQL(q), namedParameters.map { case (np, _) => np.tupled }.toMap, $RowParser.successful)
               |    .executeInsert(${rowParserFor(rowType)}.single)
               |}
               |"""
      case RepoMethod.InsertStreaming(relName, cols, rowType) =>
        val sql = sc.s(code"COPY $relName(${dbNames(cols, isRead = false)}) FROM STDIN")
        code"${textSupport.get.streamingInsert}($sql, batchSize, unsaved)(${textSupport.get.lookupTextFor(rowType)}, c)"
      case RepoMethod.InsertUnsavedStreaming(relName, unsaved) =>
        val sql = sc.s(code"COPY $relName(${dbNames(unsaved.allCols, isRead = false)}) FROM STDIN (DEFAULT '${textSupport.get.DefaultValue}')")
        code"${textSupport.get.streamingInsert}($sql, batchSize, unsaved)(${textSupport.get.lookupTextFor(unsaved.tpe)}, c)"

      case RepoMethod.DeleteBuilder(relName, fieldsType, _) =>
        code"${sc.Type.dsl.DeleteBuilder}(${sc.StrLit(relName.value)}, $fieldsType.structure)"
      case RepoMethod.Delete(relName, id) =>
        val sql = SQL {
          code"""delete from $relName where ${matchId(id)}"""
        }
        code"$sql.executeUpdate() > 0"
      case RepoMethod.DeleteByIds(relName, computedId, idsParam) =>
        computedId match {
          case x: IdComputed.Composite =>
            val vals = x.cols.map(col => code"val ${col.name} = ${idsParam.name}.map(_.${col.name})").mkCode("\n")
            val sql = SQL {
              code"""|delete
                     |from $relName
                     |where (${x.cols.map(col => col.dbCol.name.code).mkCode(", ")})
                     |in (select ${x.cols.map(col => code"unnest(${runtimeInterpolateValue(col.name, col.tpe, forbidInline = true)})").mkCode(", ")})
                     |""".stripMargin
            }
            code"""|$vals
                   |$sql.executeUpdate()
                   |""".stripMargin

          case x: IdComputed.Unary =>
            val sql = SQL {
              code"""|delete
                     |from $relName
                     |where ${x.col.dbName.code} = ANY(${runtimeInterpolateValue(idsParam.name, x.tpe, forbidInline = true)})
                     |""".stripMargin
            }

            code"""|$sql.executeUpdate()
                   |""".stripMargin
        }

      case RepoMethod.SqlFile(sqlScript) =>
        val renderedScript: sc.Code = sqlScript.sqlFile.decomposedSql.renderCode { (paramAtIndex: Int) =>
          val param = sqlScript.params.find(_.indices.contains(paramAtIndex)).get
          val cast = SqlCast.toPg(param).fold("")(_.withColons)
          code"${runtimeInterpolateValue(param.name, param.tpe)}$cast"
        }
        val ret = for {
          cols <- sqlScript.maybeCols.toOption
          rowName <- sqlScript.maybeRowName.toOption
        } yield {
          // this is necessary to make custom types work with sql scripts, unfortunately.
          val renderedWithCasts: sc.Code =
            cols.toList.flatMap(c => SqlCast.fromPg(c.dbCol)) match {
              case Nil => renderedScript.code
              case _ =>
                val row = sc.Ident("row")

                code"""|with $row as (
                       |  $renderedScript
                       |)
                       |select ${cols.map(c => code"$row.${c.dbCol.parsedName.originalName.code}${SqlCast.fromPgCode(c)}").mkCode(", ")}
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

  override def mockRepoImpl(id: IdComputed, repoMethod: RepoMethod, maybeToRow: Option[sc.Param]): sc.Code = {
    repoMethod match {
      case RepoMethod.SelectBuilder(_, fieldsType, _) =>
        code"${sc.Type.dsl.SelectBuilderMock}($fieldsType.structure, () => map.values.toList, ${sc.Type.dsl.SelectParams}.empty)"
      case RepoMethod.SelectAll(_, _, _) =>
        code"map.values.toList"
      case RepoMethod.SelectById(_, _, id, _) =>
        code"map.get(${id.paramName})"
      case RepoMethod.SelectByIds(_, _, _, idsParam, _) =>
        code"${idsParam.name}.flatMap(map.get).toList"
      case RepoMethod.SelectByIdsTracked(x) =>
        code"""|val byId = ${x.methodName}(${x.idsParam.name}).view.map(x => (x.${x.idComputed.paramName}, x)).toMap
               |${x.idsParam.name}.view.map(id => (id, byId.get(id))).toMap""".stripMargin
      case RepoMethod.SelectByUnique(_, keyColumns, _, _) =>
        code"map.values.find(v => ${keyColumns.map(c => code"${c.name} == v.${c.name}").mkCode(" && ")})"

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
               |  case ${TypesScala.Some}(oldRow) =>
               |    val updatedRow = ${varargs.name}.foldLeft(oldRow) {
               |      ${cases.mkCode("\n")}
               |    }
               |    if (updatedRow != oldRow) {
               |      map.put(${id.paramName}, updatedRow): @${TypesScala.nowarn}
               |      true
               |    } else {
               |      false
               |    }
               |  case ${TypesScala.None} => false
               |}""".stripMargin
      case RepoMethod.UpdateBuilder(_, fieldsType, _) =>
        code"${sc.Type.dsl.UpdateBuilderMock}(${sc.Type.dsl.UpdateParams}.empty, $fieldsType.structure.fields, map)"
      case RepoMethod.Update(_, _, _, param, _) =>
        code"""map.get(${param.name}.${id.paramName}) match {
              |  case ${TypesScala.Some}(`${param.name}`) => false
              |  case ${TypesScala.Some}(_) =>
              |    map.put(${param.name}.${id.paramName}, ${param.name}): @${TypesScala.nowarn}
              |    true
              |  case ${TypesScala.None} => false
              |}""".stripMargin
      case RepoMethod.Insert(_, _, unsavedParam, _) =>
        code"""|val _ = if (map.contains(${unsavedParam.name}.${id.paramName}))
               |  sys.error(s"id $${${unsavedParam.name}.${id.paramName}} already exists")
               |else
               |  map.put(${unsavedParam.name}.${id.paramName}, ${unsavedParam.name})
               |
               |${unsavedParam.name}"""
      case RepoMethod.Upsert(_, _, _, unsavedParam, _) =>
        code"""|map.put(${unsavedParam.name}.${id.paramName}, ${unsavedParam.name}): @${TypesScala.nowarn}
               |${unsavedParam.name}"""
      case RepoMethod.InsertUnsaved(_, _, _, unsavedParam, _, _) =>
        code"insert(${maybeToRow.get.name}(${unsavedParam.name}))"
      case RepoMethod.InsertStreaming(_, _, _) =>
        code"""|unsaved.foreach { row =>
               |  map += (row.${id.paramName} -> row)
               |}
               |unsaved.size.toLong""".stripMargin
      case RepoMethod.InsertUnsavedStreaming(_, _) =>
        code"""|unsaved.foreach { unsavedRow =>
               |  val row = ${maybeToRow.get.name}(unsavedRow)
               |  map += (row.${id.paramName} -> row)
               |}
               |unsaved.size.toLong""".stripMargin
      case RepoMethod.DeleteBuilder(_, fieldsType, _) =>
        code"${sc.Type.dsl.DeleteBuilderMock}(${sc.Type.dsl.DeleteParams}.empty, $fieldsType.structure.fields, map)"
      case RepoMethod.Delete(_, id) =>
        code"map.remove(${id.paramName}).isDefined"
      case RepoMethod.DeleteByIds(_, _, idsParam) =>
        code"${idsParam.name}.map(id => map.remove(id)).count(_.isDefined)"
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
      List(sc.Param(sc.Ident("c"), TypesJava.Connection, None)),
      x.table.names.RowName,
      code"(new ${x.table.names.RepoImplName}).insert(new ${x.cls}(${x.params.map(p => code"${p.name} = ${p.name}").mkCode(", ")}))"
    )

  override val defaultedInstance: List[sc.Given] =
    textSupport.map(_.defaultedInstance).toList

  override def stringEnumInstances(wrapperType: sc.Type, underlying: sc.Type): List[sc.Given] =
    List(
      Some(
        sc.Given(
          tparams = Nil,
          name = arrayColumnName,
          implicitParams = Nil,
          tpe = Column.of(sc.Type.ArrayOf(wrapperType)),
          body = code"""$Column.columnToArray($columnName, implicitly)"""
        )
      ),
      Some(
        sc.Given(
          tparams = Nil,
          name = columnName,
          implicitParams = Nil,
          tpe = Column.of(wrapperType),
          body = code"""${lookupColumnFor(underlying)}.mapResult(str => $wrapperType(str).left.map($SqlMappingError.apply))"""
        )
      ),
      Some(
        sc.Given(
          tparams = Nil,
          name = toStatementName,
          implicitParams = Nil,
          tpe = ToStatement.of(wrapperType),
          body = code"${lookupToStatementFor(underlying)}.contramap(_.value)"
        )
      ),
      Some(
        sc.Given(
          tparams = Nil,
          name = arrayToStatementName,
          implicitParams = Nil,
          tpe = ToStatement.of(sc.Type.ArrayOf(wrapperType)),
          body = code"${lookupToStatementFor(sc.Type.ArrayOf(underlying))}.contramap(_.map(_.value))"
        )
      ),
      Some(
        sc.Given(
          tparams = Nil,
          name = parameterMetadataName,
          implicitParams = Nil,
          tpe = ParameterMetaData.of(wrapperType),
          body = code"""|new $ParameterMetaData[$wrapperType] {
                 |  override def sqlType: ${TypesJava.String} = ${lookupParameterMetaDataFor(underlying)}.sqlType
                 |  override def jdbcType: ${TypesScala.Int} = ${lookupParameterMetaDataFor(underlying)}.jdbcType
                 |}""".stripMargin
        )
      ),
      textSupport.map(_.anyValInstance(wrapperType, underlying))
    ).flatten

  override def wrapperTypeInstances(wrapperType: sc.Type.Qualified, underlying: sc.Type): List[sc.Given] =
    List(
      Some(
        sc.Given(
          tparams = Nil,
          name = toStatementName,
          implicitParams = Nil,
          tpe = ToStatement.of(wrapperType),
          body = code"${lookupToStatementFor(underlying)}.contramap(_.value)"
        )
      ),
      Some(
        sc.Given(
          tparams = Nil,
          name = arrayToStatementName,
          implicitParams = Nil,
          tpe = ToStatement.of(sc.Type.ArrayOf(wrapperType)),
          body = code"${lookupToStatementFor(sc.Type.ArrayOf(underlying))}.contramap(_.map(_.value))"
        )
      ),
      Some(
        sc.Given(
          tparams = Nil,
          name = arrayColumnName,
          implicitParams = Nil,
          tpe = Column.of(sc.Type.ArrayOf(wrapperType)),
          body = code"$Column.columnToArray($columnName, implicitly)"
        )
      ),
      Some(
        sc.Given(
          tparams = Nil,
          name = columnName,
          implicitParams = Nil,
          tpe = Column.of(wrapperType),
          body = code"${lookupColumnFor(underlying)}.map($wrapperType.apply)"
        )
      ),
      Some(
        sc.Given(
          tparams = Nil,
          name = parameterMetadataName,
          implicitParams = Nil,
          tpe = ParameterMetaData.of(wrapperType),
          body = code"""|new ${ParameterMetaData.of(wrapperType)} {
                      |  override def sqlType: String = ${lookupParameterMetaDataFor(underlying)}.sqlType
                      |  override def jdbcType: Int = ${lookupParameterMetaDataFor(underlying)}.jdbcType
                      |}""".stripMargin
        )
      ),
      textSupport.map(_.anyValInstance(wrapperType, underlying))
    ).flatten

  override val missingInstances: List[sc.ClassMember] = {
    val arrayInstances = List[(sc.Type.Qualified, sc.StrLit)](
      (TypesScala.Float, sc.StrLit("float4")),
      (TypesScala.Short, sc.StrLit("int2")),
      (TypesScala.Int, sc.StrLit("int4")),
      (TypesScala.Long, sc.StrLit("int8")),
      (TypesScala.Boolean, sc.StrLit("bool")),
      (TypesScala.Double, sc.StrLit("float8"))
    ).flatMap { case (tpe, elemType) =>
      val arrayType = sc.Type.ArrayOf(tpe)
      val boxedType = TypesScala.boxedType(tpe).getOrElse(TypesScala.AnyRef)
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
        ToStatement.of(sc.Type.ArrayOf(TypesScala.BigDecimal)),
        code"${ToStatement.of(sc.Type.ArrayOf(TypesScala.BigDecimal))}((ps, index, v) => ps.setArray(index, ps.getConnection.createArrayOf(${sc.StrLit("numeric")}, v.map(v => v.bigDecimal))))"
      )

    val arrayParameterMetaData = {
      val T = sc.Type.Abstract(sc.Ident("T"))
      sc.Given(
        List(T),
        arrayParameterMetaDataName,
        List(sc.Param(T.value, ParameterMetaData.of(T), None)),
        ParameterMetaData.of(sc.Type.ArrayOf(T)),
        code"""|new ${ParameterMetaData.of(sc.Type.ArrayOf(T))} {
               |  override def sqlType: ${TypesJava.String} = ${sc.StrLit("_")} + $T.sqlType
               |  override def jdbcType: ${TypesScala.Int} = ${TypesJava.SqlTypes}.ARRAY
               |}""".stripMargin
      )
    }

    arrayInstances ++ List(arrayParameterMetaData, bigDecimalArrayToStatement)
  }

  val missingInstancesByType: Map[sc.Type, sc.QIdent] =
    missingInstances.collect { case x: sc.Given => (x.tpe, pkg / x.name) }.toMap

  override def rowInstances(tpe: sc.Type, cols: NonEmptyList[ComputedColumn], rowType: DbLib.RowType): List[sc.ClassMember] = {
    val text = textSupport.map(_.rowInstance(tpe, cols))
    val rowParser = {
      val mappedValues = cols.zipWithIndex.map { case (x, num) => code"${x.name} = row(idx + $num)(${lookupColumnFor(x.tpe)})" }
      sc.Value(
        Nil,
        rowParserName,
        params = List(sc.Param(sc.Ident("idx"), TypesScala.Int, None)),
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
    rowType match {
      case DbLib.RowType.Writable      => text.toList
      case DbLib.RowType.ReadWriteable => List(rowParser) ++ text
      case DbLib.RowType.Readable      => List(rowParser)
    }
  }

  override def customTypeInstances(ct: CustomType): List[sc.Given] = {
    val tpe = ct.typoType
    val v = sc.Ident("v")
    val normal = List(
      Some(
        sc.Given(
          Nil,
          toStatementName,
          Nil,
          ToStatement.of(tpe),
          code"${ToStatement.of(tpe)}((s, index, v) => s.setObject(index, ${ct.fromTypo0(v)}))"
        )
      ),
      Some(
        sc.Given(
          Nil,
          parameterMetadataName,
          Nil,
          ParameterMetaData.of(tpe),
          code"""|new ${ParameterMetaData.of(tpe)} {
               |  override def sqlType: ${TypesJava.String} = ${sc.StrLit(ct.sqlType)}
               |  override def jdbcType: ${TypesScala.Int} = ${TypesJava.SqlTypes}.OTHER
               |}""".stripMargin
        )
      ),
      Some(
        sc.Given(
          Nil,
          columnName,
          Nil,
          Column.of(tpe),
          code"""|$Column.nonNull[$tpe]((v1: ${TypesScala.Any}, _) =>
               |  v1 match {
               |    case $v: ${ct.toTypo.jdbcType} => ${TypesScala.Right}(${ct.toTypo0(v)})
               |    case other => ${TypesScala.Left}($TypeDoesNotMatch(s"Expected instance of ${ct.toTypo.jdbcType.render.asString}, got $${other.getClass.getName}"))
               |  }
               |)""".stripMargin
        )
      ),
      textSupport.map(_.customTypeInstance(ct))
    ).flatten

    val array =
      if (ct.forbidArray) Nil
      else {
        val fromTypo = ct.fromTypoInArray.getOrElse(ct.fromTypo)
        val toTypo = ct.toTypoInArray.getOrElse(ct.toTypo)

        List(
          sc.Given(
            Nil,
            arrayToStatementName,
            Nil,
            ToStatement.of(sc.Type.ArrayOf(tpe)),
            code"${ToStatement.of(sc.Type.ArrayOf(tpe))}((s, index, v) => s.setArray(index, s.getConnection.createArrayOf(${sc.StrLit(ct.sqlType)}, $v.map(v => ${fromTypo.fromTypo0(v)}))))"
          ),
          sc.Given(
            Nil,
            arrayColumnName,
            Nil,
            Column.of(sc.Type.ArrayOf(tpe)),
            code"""|$Column.nonNull[${sc.Type.ArrayOf(tpe)}]((v1: ${TypesScala.Any}, _) =>
                 |  v1 match {
                 |      case $v: ${TypesJava.PgArray} =>
                 |       $v.getArray match {
                 |         case $v: ${sc.Type.ArrayOf(sc.Type.Wildcard)} =>
                 |           ${TypesScala.Right}($v.map($v => ${toTypo.toTypo(code"$v.asInstanceOf[${toTypo.jdbcType}]", ct.typoType)}))
                 |         case other => ${TypesScala.Left}($TypeDoesNotMatch(s"Expected one-dimensional array from JDBC to produce an array of ${ct.typoType}, got $${other.getClass.getName}"))
                 |       }
                 |    case other => ${TypesScala.Left}($TypeDoesNotMatch(s"Expected instance of ${TypesJava.PgArray.render.asString}, got $${other.getClass.getName}"))
                 |  }
                 |)""".stripMargin
          )
        )
      }
    normal ++ array
  }
}

package typo.internal.codegen

import typo.internal.*
import typo.internal.analysis.MaybeReturnsRows
import typo.sc.Type
import typo.{NonEmptyList, sc}

class DbLibZioJdbc(pkg: sc.QIdent, inlineImplicits: Boolean, dslEnabled: Boolean, default: ComputedDefault, enableStreamingInserts: Boolean) extends DbLib {
  private val ZConnection = sc.Type.Qualified("zio.jdbc.ZConnection")
  private val Throwable = sc.Type.Qualified("java.lang.Throwable")
  private val ZStream = sc.Type.Qualified("zio.stream.ZStream")
  private val ZIO = sc.Type.Qualified("zio.ZIO")
  private val JdbcEncoder = sc.Type.Qualified("zio.jdbc.JdbcEncoder")
  private val JdbcDecoder = sc.Type.Qualified("zio.jdbc.JdbcDecoder")
  private val SqlFragment = sc.Type.Qualified("zio.jdbc.SqlFragment")
  private val Segment = sc.Type.Qualified("zio.jdbc.SqlFragment.Segment")
  private val Setter = sc.Type.Qualified("zio.jdbc.SqlFragment.Setter")
  private val UpdateResult = sc.Type.Qualified("zio.jdbc.UpdateResult")
  private val Chunk = sc.Type.Qualified("zio.Chunk")
  private val NonEmptyChunk = sc.Type.Qualified("zio.NonEmptyChunk")
  private val sqlInterpolator = sc.Type.Qualified("zio.jdbc.sqlInterpolator")
  private val JdbcDecoderError = sc.Type.Qualified("zio.jdbc.JdbcDecoderError")

  val textSupport: Option[DbLibTextSupport] =
    if (enableStreamingInserts) Some(new DbLibTextSupport(pkg, inlineImplicits, None, default)) else None

  override val additionalFiles: List[typo.sc.File] =
    textSupport match {
      case Some(textSupport) =>
        List(
          sc.File(textSupport.Text, DbLibTextImplementations.Text, Nil),
          sc.File(textSupport.streamingInsert, DbLibTextImplementations.streamingInsertZio(textSupport.Text), Nil)
        )
      case None => Nil
    }

  /** This type is basically a mapping from scala type to jdbc type name. zio-jdbc seems to use jdbc type number instead of the (potentially database-specific) type name. In the DSL we need to
    * generate some sql casts based on scala type, so it's unavoidable to have this mapping.
    *
    * A bit unfortunate maybe, but it's not the end of the world to provide it ourselves.
    */
  private val ParameterMetaData = sc.Type.Qualified("typo.dsl.ParameterMetaData")

  def ifDsl(g: sc.Given): Option[sc.Given] =
    if (dslEnabled) Some(g) else None

  private def SQL(content: sc.Code) = sc.StringInterpolate(sqlInterpolator, sc.Ident("sql"), content)

  private val arraySetterName: sc.Ident = sc.Ident("arraySetter")
  private val arrayJdbcDecoderName: sc.Ident = sc.Ident("arrayJdbcDecoder")
  private val arrayJdbcEncoderName: sc.Ident = sc.Ident("arrayJdbcEncoder")
  private val jdbcDecoderName: sc.Ident = sc.Ident("jdbcDecoder")
  private val jdbcEncoderName: sc.Ident = sc.Ident("jdbcEncoder")
  private val setterName: sc.Ident = sc.Ident("setter")
  private val parameterMetadataName: sc.Ident = sc.Ident("parameterMetadata")

  private def dbNames(cols: NonEmptyList[ComputedColumn], isRead: Boolean): sc.Code =
    cols
      .map(c => code"${c.dbName}" ++ (if (isRead) sqlCast.fromPgCode(c) else sc.Code.Empty))
      .mkCode(", ")

  private val missingInstancesByType: Map[sc.Type, sc.QIdent] =
    missingInstances.collect { case x: sc.Given => (x.tpe, pkg / x.name) }.toMap

  /** Resolve known implicits at generation-time instead of at compile-time */
  private def lookupJdbcDecoder(tpe: sc.Type): sc.Code =
    if (!inlineImplicits) JdbcDecoder.of(tpe)
    else
      sc.Type.base(tpe) match {
        case sc.Type.BigDecimal                                => code"$JdbcDecoder.bigDecimalDecoderScala"
        case sc.Type.Boolean                                   => code"$JdbcDecoder.booleanDecoder"
        case sc.Type.Byte                                      => code"$JdbcDecoder.byteDecoder"
        case sc.Type.Double                                    => code"$JdbcDecoder.doubleDecoder"
        case sc.Type.Float                                     => code"$JdbcDecoder.floatDecoder"
        case sc.Type.Int                                       => code"$JdbcDecoder.intDecoder"
        case sc.Type.Long                                      => code"$JdbcDecoder.longDecoder"
        case sc.Type.String                                    => code"$JdbcDecoder.stringDecoder"
        case sc.Type.UUID                                      => code"$JdbcDecoder.uuidDecoder"
        case sc.Type.Optional(targ)                            => code"$JdbcDecoder.optionDecoder(${lookupJdbcDecoder(targ)})"
        case sc.Type.TApply(sc.Type.Array, List(sc.Type.Byte)) => code"$JdbcDecoder.byteArrayDecoder"
        // generated type
        case x: sc.Type.Qualified if x.value.idents.startsWith(pkg.idents) =>
          code"$x.$jdbcDecoderName"
        case x if missingInstancesByType.contains(JdbcDecoder.of(x)) =>
          code"${missingInstancesByType(JdbcDecoder.of(x))}"
        case other =>
          code"${JdbcDecoder.of(other)}"
      }

  /** Resolve known implicits at generation-time instead of at compile-time */
  private def lookupJdbcEncoder(tpe: sc.Type): sc.Code =
    if (!inlineImplicits) JdbcEncoder.of(tpe)
    else
      sc.Type.base(tpe) match {
        case sc.Type.BigDecimal                                => code"$JdbcEncoder.bigDecimalEncoderScala"
        case sc.Type.Boolean                                   => code"$JdbcEncoder.booleanEncoder"
        case sc.Type.Byte                                      => code"$JdbcEncoder.byteEncoder"
        case sc.Type.Double                                    => code"$JdbcEncoder.doubleEncoder"
        case sc.Type.Float                                     => code"$JdbcEncoder.floatEncoder"
        case sc.Type.Int                                       => code"$JdbcEncoder.intEncoder"
        case sc.Type.Long                                      => code"$JdbcEncoder.longEncoder"
        case sc.Type.String                                    => code"$JdbcEncoder.stringEncoder"
        case sc.Type.UUID                                      => code"$JdbcEncoder.uuidEncoder"
        case sc.Type.Optional(targ)                            => code"$JdbcEncoder.optionEncoder(${lookupJdbcEncoder(targ)})"
        case sc.Type.TApply(sc.Type.Array, List(sc.Type.Byte)) => code"$JdbcEncoder.byteArrayEncoder"
        // generated type
        case x: sc.Type.Qualified if x.value.idents.startsWith(pkg.idents) =>
          code"$x.$jdbcEncoderName"
        case x if missingInstancesByType.contains(JdbcDecoder.of(x)) =>
          code"${missingInstancesByType(JdbcEncoder.of(x))}"
        case other =>
          code"${JdbcEncoder.of(other)}"
      }

  /** Resolve known implicits at generation-time instead of at compile-time */
  private def lookupSetter(tpe: sc.Type): sc.Code =
    if (!inlineImplicits) Setter.of(tpe)
    else
      sc.Type.base(tpe) match {
        case sc.Type.BigDecimal                                => code"$Setter.bigDecimalScalaSetter"
        case sc.Type.Boolean                                   => code"$Setter.booleanSetter"
        case sc.Type.Byte                                      => code"$Setter.byteSetter"
        case sc.Type.Double                                    => code"$Setter.doubleSetter"
        case sc.Type.Float                                     => code"$Setter.floatSetter"
        case sc.Type.Int                                       => code"$Setter.intSetter"
        case sc.Type.Long                                      => code"$Setter.longSetter"
        case sc.Type.String                                    => code"$Setter.stringSetter"
        case sc.Type.UUID                                      => code"$Setter.uuidParamSetter"
        case sc.Type.Optional(targ)                            => code"$Setter.optionParamSetter(${lookupSetter(targ)})"
        case sc.Type.TApply(sc.Type.Array, List(sc.Type.Byte)) => code"$Setter.byteArraySetter"
        // generated type
        case x: sc.Type.Qualified if x.value.idents.startsWith(pkg.idents) =>
          code"$tpe.$setterName"
        case sc.Type.TApply(sc.Type.Array, List(targ: sc.Type.Qualified)) if targ.value.idents.startsWith(pkg.idents) =>
          code"$targ.$arraySetterName"
        case x if missingInstancesByType.contains(Setter.of(x)) =>
          code"${missingInstancesByType(Setter.of(x))}"
        case other =>
          code"${Setter.of(other)}"
      }

  /** Resolve known implicits at generation-time instead of at compile-time */
  def lookupParameterMetaDataFor(tpe: sc.Type): sc.Code =
    if (!inlineImplicits) sc.Summon(ParameterMetaData.of(tpe)).code
    else
      sc.Type.base(tpe) match {
        case sc.Type.BigDecimal => code"$ParameterMetaData.BigDecimalParameterMetaData"
        case sc.Type.Boolean    => code"$ParameterMetaData.BooleanParameterMetaData"
        case sc.Type.Byte       => code"$ParameterMetaData.ByteParameterMetaData"
        case sc.Type.Double     => code"$ParameterMetaData.DoubleParameterMetaData"
        case sc.Type.Float      => code"$ParameterMetaData.FloatParameterMetaData"
        case sc.Type.Int        => code"$ParameterMetaData.IntParameterMetaData"
        case sc.Type.Long       => code"$ParameterMetaData.LongParameterMetaData"
        case sc.Type.String     => code"$ParameterMetaData.StringParameterMetaData"
        case sc.Type.UUID       => code"$ParameterMetaData.UUIDParameterMetaData"
        //        case sc.Type.Optional(targ) => lookupParameterMetaDataFor(targ)
        // generated type
        case x: sc.Type.Qualified if x.value.idents.startsWith(pkg.idents) =>
          code"$tpe.$parameterMetadataName"
        // customized type mapping
        case x if missingInstancesByType.contains(ParameterMetaData.of(x)) =>
          code"${missingInstancesByType(ParameterMetaData.of(x))}"
        case sc.Type.TApply(sc.Type.Array, List(sc.Type.Byte)) => code"$ParameterMetaData.ByteArrayParameterMetaData"
        // fallback array case.
        case sc.Type.TApply(sc.Type.Array, List(targ)) => code"$ParameterMetaData.arrayParameterMetaData(${lookupParameterMetaDataFor(targ)})"
        case other                                     => sc.Summon(ParameterMetaData.of(other)).code
      }

  private def runtimeInterpolateValue(name: sc.Code, tpe: sc.Type, forbidInline: Boolean = false): sc.Code = {
    if (inlineImplicits && !forbidInline)
      code"$${$Segment.paramSegment($name)(${lookupSetter(tpe)})}"
    else code"$$$name"
  }

  private def matchId(id: IdComputed): sc.Code =
    id match {
      case id: IdComputed.Unary =>
        code"${id.col.dbName} = ${runtimeInterpolateValue(id.paramName, id.tpe)}"
      case composite: IdComputed.Composite =>
        code"${composite.cols.map(cc => code"${cc.dbName} = ${runtimeInterpolateValue(code"${composite.paramName}.${cc.name}", cc.tpe)}").mkCode(" AND ")}"
    }

  override def repoSig(repoMethod: RepoMethod): sc.Code = repoMethod match {
    case RepoMethod.SelectBuilder(_, fieldsType, rowType) =>
      code"def select: ${sc.Type.dsl.SelectBuilder.of(fieldsType, rowType)}"
    case RepoMethod.SelectAll(_, _, rowType) =>
      code"def selectAll: ${ZStream.of(ZConnection, Throwable, rowType)}"
    case RepoMethod.SelectById(_, _, id, rowType) =>
      code"def selectById(${id.param}): ${ZIO.of(ZConnection, Throwable, sc.Type.Option.of(rowType))}"
    case RepoMethod.SelectAllByIds(_, _, unaryId, idsParam, rowType) =>
      unaryId match {
        case IdComputed.UnaryUserSpecified(_, tpe) =>
          code"def selectByIds($idsParam)(implicit encoder: ${JdbcEncoder.of(sc.Type.Array.of(tpe))}): ${ZStream.of(ZConnection, Throwable, rowType)}"
        case _ =>
          code"def selectByIds($idsParam): ${ZStream.of(ZConnection, Throwable, rowType)}"
      }
    case RepoMethod.SelectByUnique(_, params, rowType) =>
      code"def selectByUnique(${params.map(_.param.code).mkCode(", ")}): ${ZIO.of(ZConnection, Throwable, sc.Type.Option.of(rowType))}"
    case RepoMethod.SelectByFieldValues(_, _, _, fieldValueOrIdsParam, rowType) =>
      code"def selectByFieldValues($fieldValueOrIdsParam): ${ZStream.of(ZConnection, Throwable, rowType)}"
    case RepoMethod.UpdateBuilder(_, fieldsType, rowType) =>
      code"def update: ${sc.Type.dsl.UpdateBuilder.of(fieldsType, rowType)}"
    case RepoMethod.UpdateFieldValues(_, id, varargs, _, _, _) =>
      code"def updateFieldValues(${id.param}, $varargs): ${ZIO.of(ZConnection, Throwable, sc.Type.Boolean)}"
    case RepoMethod.Update(_, _, _, param, _) =>
      code"def update($param): ${ZIO.of(ZConnection, Throwable, sc.Type.Boolean)}"
    case RepoMethod.Insert(_, _, unsavedParam, rowType) =>
      code"def insert($unsavedParam): ${ZIO.of(ZConnection, Throwable, rowType)}"
    case RepoMethod.InsertUnsaved(_, _, _, unsavedParam, _, rowType) =>
      code"def insert($unsavedParam): ${ZIO.of(ZConnection, Throwable, rowType)}"
    case RepoMethod.InsertStreaming(_, _, rowType) =>
      val in = ZStream.of(ZConnection, sc.Type.Throwable, rowType)
      val out = ZIO.of(ZConnection, sc.Type.Throwable, sc.Type.Long)
      code"def insertStreaming(unsaved: $in, batchSize: Int): $out"
    case RepoMethod.Upsert(_, _, _, unsavedParam, rowType) =>
      code"def upsert($unsavedParam): ${ZIO.of(ZConnection, Throwable, UpdateResult.of(rowType))}"
    case RepoMethod.InsertUnsavedStreaming(_, unsaved) =>
      val in = ZStream.of(ZConnection, sc.Type.Throwable, unsaved.tpe)
      val out = ZIO.of(ZConnection, sc.Type.Throwable, sc.Type.Long)
      code"def insertUnsavedStreaming(unsaved: $in, batchSize: Int): $out"
    case RepoMethod.DeleteBuilder(_, fieldsType, rowType) =>
      code"def delete: ${sc.Type.dsl.DeleteBuilder.of(fieldsType, rowType)}"
    case RepoMethod.Delete(_, id) =>
      code"def delete(${id.param}): ${ZIO.of(ZConnection, Throwable, sc.Type.Boolean)}"
    case RepoMethod.SqlFile(sqlScript) =>
      val params = sc.Params(sqlScript.params.map(p => sc.Param(p.name, p.tpe, None)))

      val retType = sqlScript.maybeRowName match {
        case MaybeReturnsRows.Query(rowName) => ZStream.of(ZConnection, Throwable, rowName)
        case MaybeReturnsRows.Update         => ZIO.of(ZConnection, Throwable, sc.Type.Long)
      }

      code"def apply$params: $retType"
  }

  override def repoImpl(repoMethod: RepoMethod): sc.Code =
    repoMethod match {
      case RepoMethod.SelectBuilder(relName, fieldsType, rowType) =>
        code"""${sc.Type.dsl.SelectBuilderSql}(${sc.StrLit(relName.value)}, $fieldsType, ${lookupJdbcDecoder(rowType)})"""

      case RepoMethod.SelectAll(relName, cols, rowType) =>
        val joinedColNames = dbNames(cols, isRead = true)
        val sql = SQL(code"""select $joinedColNames from $relName""")
        code"""$sql.query(${lookupJdbcDecoder(rowType)}).selectStream"""

      case RepoMethod.SelectById(relName, cols, id, rowType) =>
        val joinedColNames = dbNames(cols, isRead = true)
        val sql = SQL(code"""select $joinedColNames from $relName where ${matchId(id)}""")
        code"""$sql.query(${lookupJdbcDecoder(rowType)}).selectOne"""

      case RepoMethod.SelectAllByIds(relName, cols, unaryId, idsParam, rowType) =>
        val joinedColNames = dbNames(cols, isRead = true)

        val sql = SQL(
          code"""select $joinedColNames from $relName where ${unaryId.col.dbName} = ANY(${runtimeInterpolateValue(idsParam.name, idsParam.tpe)})"""
        )
        code"""$sql.query(${lookupJdbcDecoder(rowType)}).selectStream"""
      case RepoMethod.SelectByUnique(relName, cols, rowType) =>
        val sql = SQL {
          code"""|select ${dbNames(cols, isRead = true)}
                 |from $relName
                 |where ${cols.map(c => code"${c.dbName} = ${runtimeInterpolateValue(c.name, c.tpe)}").mkCode(" AND ")}
                 |""".stripMargin
        }
        code"""$sql.query(${lookupJdbcDecoder(rowType)}).selectOne"""

      case RepoMethod.SelectByFieldValues(relName, cols, fieldValue, fieldValueOrIdsParam, rowType) =>
        val cases =
          cols.map { col =>
            val fr = SQL(code"${col.dbName} = ${runtimeInterpolateValue(sc.Ident("value"), col.tpe)}")
            code"case $fieldValue.${col.name}(value) => $fr"
          }

        code"""${fieldValueOrIdsParam.name} match {
              |  case Nil      => selectAll
              |  case nonEmpty =>
              |    val wheres = $SqlFragment.empty.and(
              |      nonEmpty.map {
              |        ${cases.mkCode("\n")}
              |      }
              |    )
              |    ${SQL(code"""select ${dbNames(cols, isRead = true)} from $relName where $$wheres""")}.query(${lookupJdbcDecoder(rowType)}).selectStream
              |}""".stripMargin

      case RepoMethod.UpdateFieldValues(relName, id, varargs, fieldValue, cases0, _) =>
        val cases: NonEmptyList[sc.Code] =
          cases0.map { col =>
            val sql = SQL(code"${col.dbName} = ${runtimeInterpolateValue(sc.Ident("value"), col.tpe)}${sqlCast.toPgCode(col)}")
            code"case $fieldValue.${col.name}(value) => $sql"
          }

        val sql = SQL {
          code"""|update $relName
                 |set $$updates
                 |where ${matchId(id)}
                 |""".stripMargin
        }

        code"""$NonEmptyChunk.fromIterableOption(${varargs.name}) match {
              |  case None           => $ZIO.succeed(false)
              |  case Some(nonEmpty) =>
              |    val updates = nonEmpty.map { ${cases.mkCode("\n")} }.mkFragment($SqlFragment(", "))
              |    $sql.update.map(_ > 0)
              |}""".stripMargin

      case RepoMethod.UpdateBuilder(relName, fieldsType, rowType) =>
        code"${sc.Type.dsl.UpdateBuilder}(${sc.StrLit(relName.value)}, $fieldsType, ${lookupJdbcDecoder(rowType)})"

      case RepoMethod.Update(relName, _, id, param, colsNotId) =>
        val sql = SQL(
          code"""update $relName
                |set ${colsNotId.map { col => code"${col.dbName} = ${runtimeInterpolateValue(code"${param.name}.${col.name}", col.tpe)}${sqlCast.toPgCode(col)}" }.mkCode(",\n")}
                |where ${matchId(id)}""".stripMargin
        )
        code"""|val ${id.paramName} = ${param.name}.${id.paramName}
               |$sql.update.map(_ > 0)"""

      case RepoMethod.InsertUnsaved(relName, cols, unsaved, unsavedParam, default, rowType) =>
        val cases0 = unsaved.restCols.map { col =>
          val set = SQL(code"${runtimeInterpolateValue(code"${unsavedParam.name}.${col.name}", col.tpe)}${sqlCast.toPgCode(col)}")
          code"""Some((${SQL(col.dbName)}, $set))"""
        }
        val cases1 = unsaved.defaultCols.map { case (col @ ComputedColumn(_, ident, _, _), origType) =>
          val setValue = SQL(code"${runtimeInterpolateValue(code"value: $origType", origType)}${sqlCast.toPgCode(col)}")
          code"""|${unsavedParam.name}.$ident match {
                 |  case ${default.Defaulted}.${default.UseDefault} => None
                 |  case ${default.Defaulted}.${default.Provided}(value) => Some((${SQL(col.dbName)}, $setValue))
                 |}"""
        }

        val sqlEmpty = SQL {
          code"""|insert into $relName default values
                 |returning ${dbNames(cols, isRead = true)}
                 |""".stripMargin
        }

        code"""|val fs = List(
               |  ${(cases0 ++ cases1.toList).mkCode(",\n")}
               |).flatten
               |
               |val q = if (fs.isEmpty) {
               |  $sqlEmpty
               |} else {
               |  val names  = fs.map { case (n, _) => n }.mkFragment($SqlFragment(", "))
               |  val values = fs.map { case (_, f) => f }.mkFragment($SqlFragment(", "))
               |  ${SQL(code"insert into $relName($$names) values ($$values) returning ${dbNames(cols, isRead = true)}")}
               |}
               |q.insertReturning(${lookupJdbcDecoder(rowType)}).map(_.updatedKeys.head)
               |"""
      case RepoMethod.Upsert(relName, cols, id, unsavedParam, rowType) =>
        val values = cols.map { c =>
          code"${runtimeInterpolateValue(code"${unsavedParam.name}.${c.name}", c.tpe)}${sqlCast.toPgCode(c)}"
        }

        val pickExcludedCols = cols.toList
          .filterNot(c => id.cols.exists(_.name == c.name))
          .map { c => code"${c.dbName} = EXCLUDED.${c.dbName}" }

        val base: sc.Code =
          code"""|insert into $relName(${dbNames(cols, isRead = false)})
                 |values (
                 |  ${values.mkCode(",\n")}
                 |)
                 |on conflict (${dbNames(id.cols, isRead = false)})""".stripMargin

        val exclusion: Option[sc.Code] =
          if (pickExcludedCols.isEmpty) None
          else
            Some {
              code"""|do update set
                   |  ${pickExcludedCols.mkCode(",\n")}""".stripMargin
            }

        val returning: sc.Code = code"returning ${dbNames(cols, isRead = true)}"

        val sql = SQL {
          List(
            Some(base),
            exclusion,
            Some(returning)
          ).flatten.mkCode("\n")
        }

        code"$sql.insertReturning(${lookupJdbcDecoder(rowType)})"

      case RepoMethod.Insert(relName, cols, unsavedParam, rowType) =>
        val values = cols.map { c =>
          code"${runtimeInterpolateValue(code"${unsavedParam.name}.${c.name}", c.tpe)}${sqlCast.toPgCode(c)}"
        }
        val sql = SQL {
          code"""|insert into $relName(${dbNames(cols, isRead = false)})
                 |values (${values.mkCode(", ")})
                 |returning ${dbNames(cols, isRead = true)}
                 |""".stripMargin
        }

        code"$sql.insertReturning(${lookupJdbcDecoder(rowType)}).map(_.updatedKeys.head)"
      case RepoMethod.InsertStreaming(relName, cols, rowType) =>
        val sql = sc.s(code"COPY $relName(${dbNames(cols, isRead = false)}) FROM STDIN")
        code"${textSupport.get.streamingInsert}($sql, batchSize, unsaved)(${textSupport.get.lookupTextFor(rowType)})"
      case RepoMethod.InsertUnsavedStreaming(relName, unsaved) =>
        val sql = sc.s(code"COPY $relName(${dbNames(unsaved.allCols, isRead = false)}) FROM STDIN (DEFAULT '${textSupport.get.DefaultValue}')")
        code"${textSupport.get.streamingInsert}($sql, batchSize, unsaved)(${textSupport.get.lookupTextFor(unsaved.tpe)})"

      case RepoMethod.DeleteBuilder(relName, fieldsType, _) =>
        code"${sc.Type.dsl.DeleteBuilder}(${sc.StrLit(relName.value)}, $fieldsType)"
      case RepoMethod.Delete(relName, id) =>
        val sql = SQL(code"""delete from $relName where ${matchId(id)}""")
        code"$sql.delete.map(_ > 0)"

      case RepoMethod.SqlFile(sqlScript) =>
        val renderedScript: sc.Code = sqlScript.sqlFile.decomposedSql.renderCode { (paramAtIndex: Int) =>
          val param = sqlScript.params.find(_.indices.contains(paramAtIndex)).get
          val cast = sqlCast.toPg(param).fold("")(udtType => s"::$udtType")
          code"${runtimeInterpolateValue(param.name, param.tpe)}$cast"
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
                       |select ${cols.map(c => code"$row.${c.dbCol.parsedName.originalName.code}${sqlCast.fromPgCode(c)}").mkCode(", ")}
                       |from $row""".stripMargin
            }

          code"""|val sql =
                 |  ${SQL(renderedWithCasts)}
                 |sql.query(${lookupJdbcDecoder(rowName)}).selectStream""".stripMargin
        }
        ret.getOrElse {
          code"${SQL(renderedScript)}.update"
        }
    }

  override def mockRepoImpl(id: IdComputed, repoMethod: RepoMethod, maybeToRow: Option[sc.Param]): sc.Code =
    repoMethod match {
      case RepoMethod.SelectBuilder(_, fieldsType, _) =>
        code"${sc.Type.dsl.SelectBuilderMock}($fieldsType, $ZIO.succeed($Chunk.fromIterable(map.values)), ${sc.Type.dsl.SelectParams}.empty)"
      case RepoMethod.SelectAll(_, _, _) =>
        code"$ZStream.fromIterable(map.values)"
      case RepoMethod.SelectById(_, _, id, _) =>
        code"$ZIO.succeed(map.get(${id.paramName}))"
      case RepoMethod.SelectAllByIds(_, _, _, idsParam, _) =>
        code"$ZStream.fromIterable(${idsParam.name}.flatMap(map.get))"
      case RepoMethod.SelectByUnique(_, cols, _) =>
        code"$ZIO.succeed(map.values.find(v => ${cols.map(c => code"${c.name} == v.${c.name}").mkCode(" && ")}))"

      case RepoMethod.SelectByFieldValues(_, cols, fieldValue, fieldValueOrIdsParam, _) =>
        val cases = cols.map { col =>
          code"case (acc, $fieldValue.${col.name}(value)) => acc.filter(_.${col.name} == value)"
        }
        code"""$ZStream.fromIterable {
              |  ${fieldValueOrIdsParam.name}.foldLeft(map.values) {
              |    ${cases.mkCode("\n")}
              |  }
              |}""".stripMargin
      case RepoMethod.UpdateBuilder(_, fieldsType, _) =>
        code"${sc.Type.dsl.UpdateBuilderMock}(${sc.Type.dsl.UpdateParams}.empty, $fieldsType, map)"
      case RepoMethod.UpdateFieldValues(_, id, varargs, fieldValue, cases0, _) =>
        val cases = cases0.map { col =>
          code"case (acc, $fieldValue.${col.name}(value)) => acc.copy(${col.name} = value)"
        }

        code"""|$ZIO.succeed {
               |  map.get(${id.paramName}) match {
               |    case ${sc.Type.Some}(oldRow) =>
               |      val updatedRow = ${varargs.name}.foldLeft(oldRow) {
               |        ${cases.mkCode("\n")}
               |      }
               |      if (updatedRow != oldRow) {
               |        map.put(${id.paramName}, updatedRow): @${sc.Type.nowarn}
               |        true
               |      } else {
               |        false
               |      }
               |    case ${sc.Type.None} => false
               |  }
               |}""".stripMargin
      case RepoMethod.Update(_, _, _, param, _) =>
        code"""$ZIO.succeed {
              |  map.get(${param.name}.${id.paramName}) match {
              |    case ${sc.Type.Some}(`${param.name}`) => false
              |    case ${sc.Type.Some}(_) =>
              |      map.put(${param.name}.${id.paramName}, ${param.name}): @${sc.Type.nowarn}
              |      true
              |    case ${sc.Type.None} => false
              |  }
              |}""".stripMargin
      case RepoMethod.Insert(_, _, unsavedParam, _) =>
        code"""|$ZIO.succeed {
               |  val _ =
               |    if (map.contains(${unsavedParam.name}.${id.paramName}))
               |      sys.error(s"id $${${unsavedParam.name}.${id.paramName}} already exists")
               |    else
               |      map.put(${unsavedParam.name}.${id.paramName}, ${unsavedParam.name})
               |
               |  ${unsavedParam.name}
               |}"""
      case RepoMethod.Upsert(_, _, _, unsavedParam, _) =>
        code"""|$ZIO.succeed {
               |  map.put(${unsavedParam.name}.${id.paramName}, ${unsavedParam.name}): @${sc.Type.nowarn}
               |  $UpdateResult(1, $Chunk.single(${unsavedParam.name}))
               |}""".stripMargin
      case RepoMethod.InsertUnsaved(_, _, _, unsavedParam, _, _) =>
        code"insert(${maybeToRow.get.name}(${unsavedParam.name}))"

      case RepoMethod.DeleteBuilder(_, fieldsType, _) =>
        code"${sc.Type.dsl.DeleteBuilderMock}(${sc.Type.dsl.DeleteParams}.empty, $fieldsType, map)"
      case RepoMethod.Delete(_, id) =>
        code"$ZIO.succeed(map.remove(${id.paramName}).isDefined)"
      case RepoMethod.InsertStreaming(_, _, _) =>
        code"""|unsaved.scanZIO(0L) { case (acc, row) =>
               |  ZIO.succeed {
               |    map += (row.${id.paramName} -> row)
               |    acc + 1
               |  }
               |}.runLast.map(_.getOrElse(0L))""".stripMargin
      case RepoMethod.InsertUnsavedStreaming(_, _) =>
        code"""|unsaved.scanZIO(0L) { case (acc, unsavedRow) =>
               |  ZIO.succeed {
               |    val row = toRow(unsavedRow)
               |    map += (row.${id.paramName} -> row)
               |    acc + 1
               |  }
               |}.runLast.map(_.getOrElse(0L))""".stripMargin
      case RepoMethod.SqlFile(_) =>
        // should not happen (tm)
        code"???"
    }

  override def testInsertMethod(x: ComputedTestInserts.InsertMethod): sc.Value =
    sc.Value(
      Nil,
      x.name,
      x.params,
      Nil,
      ZIO.of(ZConnection, Throwable, x.table.names.RowName),
      code"${x.table.names.RepoImplName}.insert(new ${x.cls}(${x.params.map(p => code"${p.name} = ${p.name}").mkCode(", ")}))"
    )

  override val defaultedInstance: List[sc.Given] =
    textSupport.map(_.defaultedInstance).toList

  override def stringEnumInstances(wrapperType: sc.Type, underlying: sc.Type): List[sc.ClassMember] = {
    val arraySetter = sc.Given(
      tparams = Nil,
      name = arraySetterName,
      implicitParams = Nil,
      tpe = Setter.of(sc.Type.Array.of(wrapperType)),
      body = code"""${lookupSetter(sc.Type.Array.of(underlying))}.contramap(_.map(_.value))"""
    )
    val arrayJdbcDecoder = sc.Given(
      tparams = Nil,
      name = arrayJdbcDecoderName,
      implicitParams = Nil,
      tpe = JdbcDecoder.of(sc.Type.Array.of(wrapperType)),
      body = code"""${lookupJdbcDecoder(sc.Type.Array.of(underlying))}.map(a => if (a == null) null else a.map(force))"""
    )
    val arrayJdbcEncoder = sc.Given(
      tparams = Nil,
      name = arrayJdbcEncoderName,
      implicitParams = Nil,
      tpe = JdbcEncoder.of(sc.Type.Array.of(wrapperType)),
      // JdbcEncoder for unary types defined in terms of `Setter`
      body = code"""$JdbcEncoder.singleParamEncoder(${arraySetterName})"""
    )
    val jdbcEncoder = sc.Given(
      tparams = Nil,
      name = jdbcEncoderName,
      implicitParams = Nil,
      tpe = JdbcEncoder.of(wrapperType),
      body = code"""${lookupJdbcEncoder(underlying)}.contramap(_.value)"""
    )
    val jdbcDecoder = {
      val body =
        code"""|${lookupJdbcDecoder(underlying)}.flatMap { s =>
               |  new ${JdbcDecoder.of(wrapperType)} {
               |    override def unsafeDecode(columIndex: ${sc.Type.Int}, rs: ${sc.Type.ResultSet}): (${sc.Type.Int}, $wrapperType) = {
               |      def error(msg: ${sc.Type.String}): $JdbcDecoderError =
               |        $JdbcDecoderError(
               |          message = s"Error decoding $wrapperType from ResultSet",
               |          cause = new RuntimeException(msg),
               |          metadata = rs.getMetaData,
               |          row = rs.getRow
               |        )
               |
               |      $wrapperType.apply(s).fold(e => throw error(e), (columIndex, _))
               |    }
               |  }
               |}""".stripMargin
      sc.Given(tparams = Nil, name = jdbcDecoderName, implicitParams = Nil, tpe = JdbcDecoder.of(wrapperType), body = body)
    }

    val setter = sc.Given(
      tparams = Nil,
      name = setterName,
      implicitParams = Nil,
      tpe = Setter.of(wrapperType),
      body = code"""${lookupSetter(underlying)}.contramap(_.value)"""
    )

    val parameterMetadata = {
      val body =
        code"$ParameterMetaData.instance[$wrapperType](${lookupParameterMetaDataFor(underlying)}.sqlType, ${lookupParameterMetaDataFor(underlying)}.jdbcType)"
      sc.Given(tparams = Nil, name = parameterMetadataName, implicitParams = Nil, tpe = ParameterMetaData.of(wrapperType), body = body)
    }

    val text = textSupport.map(_.anyValInstance(wrapperType, underlying))

    List(
      Option(arraySetter),
      Option(arrayJdbcDecoder),
      Option(arrayJdbcEncoder),
      Option(jdbcEncoder),
      Option(jdbcDecoder),
      Option(setter),
      ifDsl(parameterMetadata),
      text
    ).flatten
  }

  override def anyValInstances(wrapperType: Type.Qualified, underlying: sc.Type): List[sc.ClassMember] =
    List(
      Option(
        sc.Given(
          tparams = Nil,
          name = jdbcEncoderName,
          implicitParams = Nil,
          tpe = JdbcEncoder.of(wrapperType),
          body = code"""${lookupJdbcEncoder(underlying)}.contramap(_.value)"""
        )
      ),
      Option(
        sc.Given(
          tparams = Nil,
          name = jdbcDecoderName,
          implicitParams = Nil,
          tpe = JdbcDecoder.of(wrapperType),
          body = code"""${lookupJdbcDecoder(underlying)}.map($wrapperType.apply)"""
        )
      ),
      Option(
        sc.Given(
          tparams = Nil,
          name = setterName,
          implicitParams = Nil,
          tpe = Setter.of(wrapperType),
          body = code"""${lookupSetter(underlying)}.contramap(_.value)"""
        )
      ),
      Option(
        sc.Given(
          tparams = Nil,
          name = arraySetterName,
          implicitParams = Nil,
          tpe = Setter.of(sc.Type.Array.of(wrapperType)),
          body = code"""${lookupSetter(sc.Type.Array.of(underlying))}.contramap(_.map(_.value))"""
        )
      ),
      ifDsl(
        sc.Given(
          tparams = Nil,
          name = parameterMetadataName,
          implicitParams = Nil,
          tpe = ParameterMetaData.of(wrapperType),
          body = code"$ParameterMetaData.instance[$wrapperType](${lookupParameterMetaDataFor(underlying)}.sqlType, ${lookupParameterMetaDataFor(underlying)}.jdbcType)"
        )
      ),
      textSupport.map(_.anyValInstance(wrapperType, underlying))
    ).flatten

  override def missingInstances: List[sc.ClassMember] = {

    /** Adapted from Quill implementation
      *
      * Works for primitive types but not for more complex types
      */
    def primitiveArrayDecoder(T: sc.Type.Qualified) = {
      val body =
        code"""|new ${JdbcDecoder.of(sc.Type.Array.of(T))} {
               |  override def unsafeDecode(columIndex: ${sc.Type.Int}, rs: ${sc.Type.ResultSet}): (${sc.Type.Int}, ${sc.Type.Array.of(T)}) = {
               |    val arr = rs.getArray(columIndex)
               |    if (arr eq null) columIndex -> null
               |    else {
               |      columIndex ->
               |        arr
               |          .getArray
               |          .asInstanceOf[Array[Any]]
               |          .foldLeft(Array.newBuilder[${T.value}]) {
               |            case (b, x: ${T.value}) => b += x
               |            case (b, x: java.lang.Number) => b += x.asInstanceOf[${T.value}]
               |            case (_, x) =>
               |              throw $JdbcDecoderError(
               |                message = s"Error decoding ${sc.Type.Array}(${T.value}) from ResultSet",
               |                cause = new IllegalStateException(
               |                  s"Retrieved $${x.getClass.getCanonicalName} type from JDBC array, but expected (${T.value}). Re-check your decoder implementation"
               |                ),
               |                metadata = rs.getMetaData,
               |                row = rs.getRow
               |              )
               |          }
               |          .result()
               |    }
               |  }
               |}""".stripMargin
      sc.Given(tparams = Nil, name = sc.Ident(s"${T.name.value}ArrayDecoder"), implicitParams = Nil, tpe = JdbcDecoder.of(sc.Type.Array.of(T)), body = body)
    }

    def primitiveArraySetter(T: sc.Type.Qualified, sqlType: sc.StrLit, asAnyRef: sc.Code => sc.Code) = {
      val v = sc.Ident("v")
      val body =
        code"""|$Setter.forSqlType[${sc.Type.Array.of(T)}](
               |  (ps, i, $v) => {
               |    ps.setArray(i, ps.getConnection.createArrayOf($sqlType, ${asAnyRef(v)}))
               |  },
               |  ${sc.Type.Types}.ARRAY
               |)""".stripMargin
      sc.Given(tparams = Nil, name = sc.Ident(s"${T.name.value}ArraySetter"), implicitParams = Nil, tpe = Setter.of(sc.Type.Array.of(T)), body = body)
    }

    def primitiveArrayEncoder(T: sc.Type.Qualified) = {
      sc.Given(
        tparams = Nil,
        name = sc.Ident(s"${T.name.value}ArrayEncoder"),
        implicitParams = Nil,
        tpe = JdbcEncoder.of(sc.Type.Array.of(T)),
        body = code"""$JdbcEncoder.singleParamEncoder(${T.name.value}ArraySetter)"""
      )
    }

    def ScalaBigDecimal =
      List(
        sc.Given(
          tparams = Nil,
          name = sc.Ident("ScalaBigDecimalArrayEncoder"),
          implicitParams = Nil,
          tpe = JdbcEncoder.of(sc.Type.Array.of(sc.Type.BigDecimal)),
          body = code"""BigDecimalArrayEncoder.contramap(_.map(_.bigDecimal))"""
        ),
        sc.Given(
          tparams = Nil,
          name = sc.Ident("ScalaBigDecimalArrayDecoder"),
          implicitParams = Nil,
          tpe = JdbcDecoder.of(sc.Type.Array.of(sc.Type.BigDecimal)),
          body = code"""BigDecimalArrayDecoder.map(v => if (v eq null) null else v.map(${sc.Type.BigDecimal}.apply))"""
        ),
        sc.Given(
          tparams = Nil,
          name = sc.Ident("ScalaBigDecimalArraySetter"),
          implicitParams = Nil,
          tpe = Setter.of(sc.Type.Array.of(sc.Type.BigDecimal)),
          body = code"""BigDecimalArraySetter.contramap(_.map(_.bigDecimal))"""
        )
      )

    def all(T: sc.Type.Qualified, sqlType: String, asAnyRef: sc.Code => sc.Code) = {
      List(primitiveArrayDecoder(T), primitiveArraySetter(T, sc.StrLit(sqlType), asAnyRef), primitiveArrayEncoder(T))
    }

    all(sc.Type.String, "varchar", array => code"$array.map(x => x: ${sc.Type.AnyRef})") ++
      all(sc.Type.Int, "int4", array => code"$array.map(x => int2Integer(x): ${sc.Type.AnyRef})") ++
      all(sc.Type.Long, "int8", array => code"$array.map(x => long2Long(x): ${sc.Type.AnyRef})") ++
      all(sc.Type.Float, "float4", array => code"$array.map(x => float2Float(x): ${sc.Type.AnyRef})") ++
      all(sc.Type.Double, "float8", array => code"$array.map(x => double2Double(x): ${sc.Type.AnyRef})") ++
      all(sc.Type.JavaBigDecimal, "numeric", array => code"$array.map(x => x: ${sc.Type.AnyRef})") ++
      ScalaBigDecimal ++
      all(sc.Type.Boolean, "bool", array => code"$array.map(x => boolean2Boolean(x): ${sc.Type.AnyRef})")
  }

  override def rowInstances(tpe: sc.Type, cols: NonEmptyList[ComputedColumn], rowType: DbLib.RowType): List[sc.ClassMember] = {
    val text = textSupport.map(_.rowInstance(tpe, cols))
    val decoder = {
      val body =
        if (cols.length == 1)
          code"""${lookupJdbcDecoder(cols.head.tpe)}.map(v => $tpe(${cols.head.name} = v))""".stripMargin
        else {
          val namedParams = cols.zipWithIndex.map { case (c, idx) =>
            code"${c.name} = ${lookupJdbcDecoder(c.tpe)}.unsafeDecode(columIndex + $idx, rs)._2"
          }

          code"""|new ${JdbcDecoder.of(tpe)} {
                 |  override def unsafeDecode(columIndex: ${sc.Type.Int}, rs: ${sc.Type.ResultSet}): (${sc.Type.Int}, $tpe) =
                 |    columIndex + ${cols.length - 1} ->
                 |      $tpe(
                 |        ${namedParams.mkCode(",\n")}
                 |      )
                 |}""".stripMargin
        }
      sc.Given(tparams = Nil, name = jdbcDecoderName, implicitParams = Nil, tpe = JdbcDecoder.of(tpe), body = body)
    }
    rowType match {
      case DbLib.RowType.Writable      => text.toList
      case DbLib.RowType.ReadWriteable => List(decoder) ++ text
      case DbLib.RowType.Readable      => List(decoder)
    }
  }

  override def customTypeInstances(ct: CustomType): List[sc.ClassMember] =
    customTypeOne(ct) ++ (if (ct.forbidArray) Nil else customTypeArray(ct))

  def customTypeOne(ct: CustomType): List[sc.Given] = {

    val jdbcEncoder = sc.Given(
      tparams = Nil,
      name = jdbcEncoderName,
      implicitParams = Nil,
      tpe = JdbcEncoder.of(ct.typoType),
      // JdbcEncoder for unary types defined in terms of `Setter`
      body = code"""$JdbcEncoder.singleParamEncoder($setterName)"""
    )

    val jdbcDecoder = {
      val expectedType = sc.StrLit(ct.fromTypo.jdbcType.render.asString)
      val body =
        code"""|${JdbcDecoder.of(ct.typoType)}(
               |  (rs: ${sc.Type.ResultSet}) => (i: ${sc.Type.Int}) => {
               |    val v = rs.getObject(i)
               |    if (v eq null) null else ${ct.toTypo0(code"v.asInstanceOf[${ct.toTypo.jdbcType}]")}
               |  },
               |  $expectedType
               |)""".stripMargin
      sc.Given(tparams = Nil, name = jdbcDecoderName, implicitParams = Nil, tpe = JdbcDecoder.of(ct.typoType), body = body)
    }

    val setter = {
      val v = sc.Ident("v")
      val body =
        code"""|$Setter.other(
               |  (ps, i, $v) => {
               |    ps.setObject(
               |      i,
               |      ${ct.fromTypo0(v)}
               |    )
               |  },
               |  ${sc.StrLit(ct.sqlType)}
               |)""".stripMargin
      sc.Given(tparams = Nil, name = setterName, implicitParams = Nil, tpe = Setter.of(ct.typoType), body = body)
    }

    val parameterMetadata = {
      val body = code"$ParameterMetaData.instance[${ct.typoType}](${sc.StrLit(ct.sqlType)}, ${sc.Type.Types}.OTHER)"
      sc.Given(Nil, parameterMetadataName, Nil, ParameterMetaData.of(ct.typoType), body)
    }
    val text = textSupport.map(_.customTypeInstance(ct))

    List(Option(jdbcEncoder), Option(jdbcDecoder), Option(setter), ifDsl(parameterMetadata), text).flatten
  }

  def customTypeArray(ct: CustomType): List[sc.Given] = {
    val fromTypo = ct.fromTypoInArray.getOrElse(ct.fromTypo)
    val toTypo = ct.toTypoInArray.getOrElse(ct.toTypo)
    val jdbcEncoder = sc.Given(
      tparams = Nil,
      name = arrayJdbcEncoderName,
      implicitParams = Nil,
      tpe = JdbcEncoder.of(sc.Type.Array.of(ct.typoType)),
      // JdbcEncoder for unary types defined in terms of `Setter`
      body = code"""$JdbcEncoder.singleParamEncoder(${arraySetterName})"""
    )

    val jdbcDecoder = {
      val expectedType = sc.StrLit(sc.Type.Array.of(ct.fromTypo.jdbcType).render.asString)
      val body =
        code"""|${JdbcDecoder.of(sc.Type.Array.of(ct.typoType))}((rs: ${sc.Type.ResultSet}) => (i: ${sc.Type.Int}) =>
               |  rs.getArray(i) match {
               |    case null => null
               |    case arr => arr.getArray.asInstanceOf[Array[AnyRef]].map(x => ${toTypo.toTypo(code"x.asInstanceOf[${toTypo.jdbcType}]", ct.typoType)})
               |  },
               |  $expectedType
               |)""".stripMargin
      sc.Given(tparams = Nil, name = arrayJdbcDecoderName, implicitParams = Nil, tpe = JdbcDecoder.of(sc.Type.Array.of(ct.typoType)), body = body)
    }

    val setter = {
      val v = sc.Ident("v")
      val vv = sc.Ident("vv")
      val body =
        code"""|$Setter.forSqlType((ps, i, $v) =>
               |  ps.setArray(
               |    i,
               |    ps.getConnection.createArrayOf(
               |      ${sc.StrLit(ct.sqlType)},
               |      $v.map { $vv =>
               |        ${fromTypo.fromTypo0(vv)}
               |      }
               |    )
               |  ),
               |  ${sc.Type.Types}.ARRAY
               |)""".stripMargin

      sc.Given(tparams = Nil, name = arraySetterName, implicitParams = Nil, tpe = Setter.of(sc.Type.Array.of(ct.typoType)), body = body)
    }

    List(jdbcEncoder, jdbcDecoder, setter)
  }
}

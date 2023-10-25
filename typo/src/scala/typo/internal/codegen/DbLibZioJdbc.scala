package typo.internal.codegen

import typo.internal.*
import typo.internal.analysis.MaybeReturnsRows
import typo.sc.Type
import typo.{NonEmptyList, sc}

class DbLibZioJdbc(pkg: sc.QIdent, inlineImplicits: Boolean) extends DbLib {
  private val ZConnection = sc.Type.Qualified("zio.jdbc.ZConnection")
  private val Throwable = sc.Type.Qualified("java.lang.Throwable")
  private val ZStream = sc.Type.Qualified("zio.stream.ZStream")
  private val ZIO = sc.Type.Qualified("zio.ZIO")
  private val `ZIO.succeed` = sc.Type.Qualified("zio.ZIO.succeed")
  private val JdbcEncoder = sc.Type.Qualified("zio.jdbc.JdbcEncoder")
  private val JdbcDecoder = sc.Type.Qualified("zio.jdbc.JdbcDecoder")
  private val SqlFragment = sc.Type.Qualified("zio.jdbc.SqlFragment")
  private val Segment = sc.Type.Qualified("zio.jdbc.SqlFragment.Segment")
  private val Setter = sc.Type.Qualified("zio.jdbc.SqlFragment.Setter")
  private val UpdateResult = sc.Type.Qualified("zio.jdbc.UpdateResult")
  private val NonEmptyChunk = sc.Type.Qualified("zio.NonEmptyChunk")
  private val sqlInterpolator = sc.Type.Qualified("zio.jdbc.sqlInterpolator")
  private val JdbcDecoderError = sc.Type.Qualified("zio.jdbc.JdbcDecoderError")

  private def SQL(content: sc.Code) = sc.StringInterpolate(sqlInterpolator, sc.Ident("sql"), content)

  private val jdbcDecoderName: sc.Ident = sc.Ident("jdbcDecoder")
  private val jdbcEncoderName: sc.Ident = sc.Ident("jdbcEncoder")
  private val jdbcSetterName: sc.Ident = sc.Ident("jdbcSetter")

  private def dbNames(cols: NonEmptyList[ComputedColumn], isRead: Boolean): sc.Code =
    cols
      .map(c => code"${c.dbName.value}" ++ (if (isRead) sqlCast.fromPgCode(c) else sc.Code.Empty))
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
          code"${JdbcDecoder.of(x)}" // TODO: Can we do better?
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
          code"${JdbcEncoder.of(x)}" // TODO: Can we do better?
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
          code"${Setter.of(x)}" // TODO: Can we do better?
        case x if missingInstancesByType.contains(Setter.of(x)) =>
          code"${missingInstancesByType(Setter.of(x))}"
        case other =>
          code"${Setter.of(other)}"
      }

  private def runtimeInterpolateValue(name: sc.Code, tpe: sc.Type, forbidInline: Boolean = false): sc.Code = {
    if (inlineImplicits && !forbidInline)
      code"$${$Segment.paramSegment($name)(${lookupSetter(tpe)})}"
    else code"$name"
  }

  private def matchId(id: IdComputed): sc.Code =
    id match {
      case id: IdComputed.Unary =>
        code"${id.col.dbName.value} = ${runtimeInterpolateValue(id.paramName, id.tpe)}"
      case composite: IdComputed.Composite =>
        code"${composite.cols.map(cc => code"${cc.dbName.value} = ${runtimeInterpolateValue(code"${composite.paramName}.${cc.name}", cc.tpe)}").mkCode(" AND ")}"
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
      code"def insert($unsavedParam): ${ZIO.of(ZConnection, Throwable, UpdateResult.of(rowType))}"
    case RepoMethod.InsertUnsaved(_, _, _, unsavedParam, _, rowType) =>
      code"def insert($unsavedParam): ${ZIO.of(ZConnection, Throwable, UpdateResult.of(rowType))}"
    case RepoMethod.Upsert(_, _, _, unsavedParam, rowType) =>
      code"def upsert($unsavedParam): ${ZIO.of(ZConnection, Throwable, UpdateResult.of(rowType))}"
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
          code"""select $joinedColNames from $relName where ${code"${unaryId.col.dbName.value} = ANY(${runtimeInterpolateValue(idsParam.name, idsParam.tpe, forbidInline = true)})"}"""
        )
        code"""$sql.query(${lookupJdbcDecoder(rowType)}).selectStream"""
      case RepoMethod.SelectByUnique(relName, cols, rowType) =>
        val sql = SQL {
          code"""|select ${dbNames(cols, isRead = true)}
                 |from $relName
                 |where ${cols.map(c => code"${c.dbName.value} = ${runtimeInterpolateValue(c.name, c.tpe)}").mkCode(" AND ")}
                 |""".stripMargin
        }
        code"""$sql.query(${lookupJdbcDecoder(rowType)}).selectOne"""

      case RepoMethod.SelectByFieldValues(relName, cols, fieldValue, fieldValueOrIdsParam, rowType) =>
        val cases =
          cols.map { col =>
            val fr = SQL(code"${col.dbName.value} = ${runtimeInterpolateValue(sc.Ident("value"), col.tpe)}")
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
              |    ${SQL(code"""select ${dbNames(cols, isRead = true)} from $relName""")}.where(wheres).query(${lookupJdbcDecoder(rowType)}).selectStream
              |}
              |""".stripMargin

      case RepoMethod.UpdateFieldValues(relName, id, varargs, fieldValue, cases0, _) =>
        val cases: NonEmptyList[sc.Code] =
          cases0.map { col =>
            val sql = SQL(code"${col.dbName.value} = ${runtimeInterpolateValue(sc.Ident("value"), col.tpe)}${sqlCast.toPgCode(col)}")
            code"case $fieldValue.${col.name}(value) => $sql"
          }

        val sql = SQL {
          code"""|update $relName
                 |set $$updates
                 |where ${matchId(id)}
                 |""".stripMargin
        }

        code"""$NonEmptyChunk.fromIterableOption(${varargs.name}) match {
              |  case None           => ${`ZIO.succeed`}(false)
              |  case Some(nonEmpty) =>
              |    import zio.prelude.ForEachOps
              |    implicit val identity: zio.prelude.Identity[SqlFragment] = new zio.prelude.Identity[SqlFragment] {
              |      override def identity: SqlFragment                                      = SqlFragment.empty
              |      override def combine(l: => SqlFragment, r: => SqlFragment): SqlFragment = l ++ r
              |    }
              |    val updates = nonEmpty.map {
              |      ${cases.mkCode("\n")}
              |    }.intersperse(${SQL(code", ")})
              |    $sql.update.map(_ > 0)
              |}""".stripMargin

      case RepoMethod.UpdateBuilder(relName, fieldsType, rowType) =>
        code"${sc.Type.dsl.UpdateBuilder}(${sc.StrLit(relName.value)}, $fieldsType, ${lookupJdbcDecoder(rowType)})"

      case RepoMethod.Update(relName, _, id, param, colsNotId) =>
        val sql = SQL(
          code"""update $relName
                |set ${colsNotId.map { col => code"${col.dbName.value} = ${runtimeInterpolateValue(code"${param.name}.${col.name}", col.tpe)}${sqlCast.toPgCode(col)}" }.mkCode(",\n")}
                |where ${matchId(id)}""".stripMargin
        )
        code"""|val ${id.paramName} = ${param.name}.${id.paramName}
               |$sql.update.map(_ > 0)"""

      case RepoMethod.InsertUnsaved(relName, cols, unsaved, unsavedParam, default, rowType) =>
        val cases0 = unsaved.restCols.map { col =>
          val set = SQL(code"${runtimeInterpolateValue(code"${unsavedParam.name}.${col.name}", col.tpe)}${sqlCast.toPgCode(col)}")
          code"""Some(($SqlFragment(${sc.s(col.dbName.value)}), $set))"""
        }
        val cases1 = unsaved.defaultCols.map { case (col @ ComputedColumn(_, ident, _, _), origType) =>
          val setValue = SQL(code"${runtimeInterpolateValue(code"value: $origType", origType)}${sqlCast.toPgCode(col)}")
          code"""|${unsavedParam.name}.$ident match {
                 |  case ${default.Defaulted}.${default.UseDefault} => None
                 |  case ${default.Defaulted}.${default.Provided}(value) => Some(($SqlFragment(${sc.s(col.dbName.value)}), $setValue))
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
               |  import zio.prelude.ForEachOps
               |  @scala.annotation.nowarn("msg=local val identitySqlFragment in value q is never used")
               |  val identitySqlFragment: zio.prelude.Identity[SqlFragment] = new zio.prelude.Identity[SqlFragment] {
               |    override def identity: SqlFragment                                      = SqlFragment.empty
               |    override def combine(l: => SqlFragment, r: => SqlFragment): SqlFragment = l ++ r
               |  }
               |  val names  = fs.map { case (n, _) => n }.intersperse(${SQL(code", ")})(zio.prelude.Invariant.ListForEach, identitySqlFragment)
               |  val values = fs.map { case (_, f) => f }.intersperse(${SQL(code", ")})(zio.prelude.Invariant.ListForEach, identitySqlFragment)
               |  ${SQL(code"insert into $relName($$names) values ($$values) returning ${dbNames(cols, isRead = true)}")}
               |}: @scala.annotation.nowarn("msg=Unused import")
               |q.insertReturning(${lookupJdbcDecoder(rowType)})
               |"""
      case RepoMethod.Upsert(relName, cols, id, unsavedParam, rowType) =>
        val values = cols.map { c =>
          code"${runtimeInterpolateValue(code"${unsavedParam.name}.${c.name}", c.tpe)}${sqlCast.toPgCode(c)}"
        }

        val pickExcludedCols = cols.toList
          .filterNot(c => id.cols.exists(_.name == c.name))
          .map { c => code"${c.dbName.value} = EXCLUDED.${c.dbName.value}" }

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

        code"$sql.insertReturning(${lookupJdbcDecoder(rowType)})"

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

  override def mockRepoImpl(id: IdComputed, repoMethod: RepoMethod, maybeToRow: Option[sc.Param]): sc.Code = code"???" // TODO Jules

  override def testInsertMethod(x: ComputedTestInserts.InsertMethod): sc.Value =
    sc.Value(
      Nil,
      x.name,
      x.params,
      Nil,
      ZIO.of(ZConnection, Throwable, UpdateResult.of(x.table.names.RowName)),
      code"${x.table.names.RepoImplName}.insert(new ${x.cls}(${x.params.map(p => code"${p.name} = ${p.name}").mkCode(", ")}))"
    )

  override def stringEnumInstances(wrapperType: sc.Type, underlying: sc.Type): List[sc.ClassMember] =
    List(
      sc.Given(
        tparams = Nil,
        name = jdbcEncoderName,
        implicitParams = Nil,
        tpe = JdbcEncoder.of(wrapperType),
        body = code"""${lookupJdbcEncoder(underlying)}.contramap(_.value)"""
      ),
      sc.Given(
        tparams = Nil,
        name = jdbcDecoderName,
        implicitParams = Nil,
        tpe = JdbcDecoder.of(wrapperType),
        body = code"""|${lookupJdbcDecoder(underlying)}.flatMap { s =>
                 |  new ${JdbcDecoder.of(wrapperType)} {
                 |    override def unsafeDecode(columIndex: ${sc.Type.Int}, rs: ${sc.Type.ResultSet}): (${sc.Type.Int}, $wrapperType) = {
                 |      def error(msg: ${sc.Type.String}): $JdbcDecoderError =
                 |        $JdbcDecoderError(
                 |          message = s"Error decoding '$wrapperType' from ResultSet",
                 |          cause = new RuntimeException(msg),
                 |          metadata = rs.getMetaData,
                 |          row = rs.getRow
                 |        )
                 |
                 |      $wrapperType.apply(s).fold(e => throw error(e), (columIndex, _))
                 |    }
                 |  }
                 |}""".stripMargin
      ),
      sc.Given(
        tparams = Nil,
        name = jdbcSetterName,
        implicitParams = Nil,
        tpe = Setter.of(wrapperType),
        body = code"""${lookupSetter(underlying)}.contramap(_.value)"""
      )
    )

  override def anyValInstances(wrapperType: Type.Qualified, underlying: sc.Type): List[sc.ClassMember] =
    List(
      sc.Given(
        tparams = Nil,
        name = jdbcEncoderName,
        implicitParams = Nil,
        tpe = JdbcEncoder.of(wrapperType),
        body = code"""${lookupJdbcEncoder(underlying)}.contramap(_.value)"""
      ),
      sc.Given(
        tparams = Nil,
        name = jdbcDecoderName,
        implicitParams = Nil,
        tpe = JdbcDecoder.of(wrapperType),
        body = code"""${lookupJdbcDecoder(underlying)}.map($wrapperType.apply)"""
      ),
      sc.Given(
        tparams = Nil,
        name = jdbcSetterName,
        implicitParams = Nil,
        tpe = Setter.of(wrapperType),
        body = code"""${lookupSetter(underlying)}.contramap(_.value)"""
      )
    )

  override def missingInstances: List[sc.ClassMember] = List.empty // TODO Jules

  override def rowInstances(tpe: sc.Type, cols: NonEmptyList[ComputedColumn]): List[sc.ClassMember] = {
    val decoder =
      sc.Given(
        tparams = Nil,
        name = jdbcDecoderName,
        implicitParams = Nil,
        tpe = JdbcDecoder.of(tpe),
        body = {
          val namedParams = cols.zipWithIndex.map { case (c, idx) =>
            code"${c.name} = ${lookupJdbcDecoder(c.tpe)}.unsafeDecode(columIndex + $idx, rs)._2"
          }

          code"""|new ${JdbcDecoder.of(tpe)} {
               |  override def unsafeDecode(columIndex: ${sc.Type.Int}, rs: ${sc.Type.ResultSet}): (${sc.Type.Int}, $tpe) =
               |    columIndex ->
               |      $tpe(
               |        ${namedParams.mkCode(",\n")}
               |      )
               |}
          """.stripMargin
        }
      )

    /** Inspired by `JdbcEncoder.caseClassEncoder`
      */
    val encoder =
      sc.Given(
        tparams = Nil,
        name = jdbcEncoderName,
        implicitParams = Nil,
        tpe = JdbcEncoder.of(tpe),
        body = {
          code"""|new ${JdbcEncoder.of(tpe)} {
                 |  private final val comma = ${SQL(code", ")}
                 |
                 |  override def encode(value: $tpe): $SqlFragment =
                 |    ${cols.map(c => code"${lookupJdbcEncoder(c.tpe)}.encode(value.${c.name})").mkCode(code" ++ comma ++\n")}
                 |}
                  """.stripMargin
        }
      )

    List(decoder, encoder)
  }

  override def customTypeInstances(ct: CustomType): List[sc.ClassMember] = {
    if (ct.params.length == 1) anyValInstances(ct.typoType, ct.params.head.tpe)
    else {
      val decoder =
        sc.Given(
          tparams = Nil,
          name = jdbcDecoderName,
          implicitParams = Nil,
          tpe = JdbcDecoder.of(ct.typoType),
          body = {
            val namedParams = ct.params.zipWithIndex.map { case (c, idx) =>
              code"${c.name} = ${lookupJdbcDecoder(c.tpe)}.unsafeDecode(columIndex + $idx, rs)._2"
            }

            code"""|new ${JdbcDecoder.of(ct.typoType)} {
                   |  override def unsafeDecode(columIndex: ${sc.Type.Int}, rs: ${sc.Type.ResultSet}): (${sc.Type.Int}, ${ct.typoType}) =
                   |    columIndex ->
                   |      ${ct.typoType}(
                   |        ${namedParams.mkCode(",\n")}
                   |      )
                   |}
                """.stripMargin
          }
        )

      /** Inspired by `JdbcEncoder.caseClassEncoder`
        */
      val encoder =
        sc.Given(
          tparams = Nil,
          name = jdbcEncoderName,
          implicitParams = Nil,
          tpe = JdbcEncoder.of(ct.typoType),
          body = {
            code"""|new ${JdbcEncoder.of(ct.typoType)} {
                   |  private final val comma = ${SQL(code", ")}
                   |
                   |  override def encode(value: ${ct.typoType}): $SqlFragment = {
                   |    ${ct.params.map(c => code"${lookupJdbcEncoder(c.tpe)}.encode(value.${c.name})").mkCode(code" ++ comma ++\n")}
                   |  }
                   |}
                        """.stripMargin
          }
        )

      List(decoder, encoder)
    }
  }
}

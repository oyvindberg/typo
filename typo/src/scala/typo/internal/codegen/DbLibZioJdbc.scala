package typo.internal.codegen

import typo.internal.*
import typo.internal.analysis.MaybeReturnsRows
import typo.sc.Type
import typo.{NonEmptyList, sc}

import scala.annotation.nowarn

class DbLibZioJdbc(pkg: sc.QIdent, inlineImplicits: Boolean) extends DbLib {
  private val ZConnection = sc.Type.Qualified("zio.jdbc.ZConnection")
  private val Throwable = sc.Type.Qualified("java.lang.Throwable")
  private val ZStream = sc.Type.Qualified("zio.stream.ZStream")
  private val ZIO = sc.Type.Qualified("zio.ZIO")
  private val `ZIO.succeed` = sc.Type.Qualified("zio.ZIO.succeed")
  private val JdbcEncoder = sc.Type.Qualified("zio.jdbc.JdbcEncoder")
  private val Fragment = sc.Type.Qualified("zio.jdbc.SqlFragment")
  private val NonEmptyChunk = sc.Type.Qualified("zio.NonEmptyChunk")

  private val SqlInterpolator = sc.Type.Qualified("zio.jdbc.SqlInterpolator")
  private def SQL(content: sc.Code) = sc.StringInterpolate(SqlInterpolator, sc.Ident("sql"), content)

  private def dbNames(cols: NonEmptyList[ComputedColumn], isRead: Boolean): sc.Code =
    cols
      .map(c => c.dbName.code ++ (if (isRead) sqlCast.fromPgCode(c) else sc.Code.Empty))
      .mkCode(", ")

  private val missingInstancesByType: Map[sc.Type, sc.QIdent] =
    missingInstances.collect { case x: sc.Given => (x.tpe, pkg / x.name) }.toMap

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
          code"???" // TODO Jules: What should I put here?
        case sc.Type.TApply(sc.Type.Array, List(targ: sc.Type.Qualified)) if targ.value.idents.startsWith(pkg.idents) =>
          code"???" // TODO Jules: What should I put here?
        case x if missingInstancesByType.contains(JdbcEncoder.of(x)) =>
          code"${missingInstancesByType(JdbcEncoder.of(x))}"
        case other =>
          code"${JdbcEncoder.of(other)}"
      }

  @nowarn
  private def runtimeInterpolateValue(name: sc.Code, tpe: sc.Type, forbidInline: Boolean = false): sc.Code =
    if (inlineImplicits && !forbidInline)
      code"???" // TODO Jules: What should I put here?
    else code"$${$name}"

  private def matchId(id: IdComputed): sc.Code =
    id match {
      case id: IdComputed.Unary =>
        code"${id.col.dbName.code} = ${runtimeInterpolateValue(id.paramName, id.tpe)}"
      case composite: IdComputed.Composite =>
        code"${composite.cols.map(cc => code"${cc.dbName.code} = ${runtimeInterpolateValue(code"${composite.paramName}.${cc.name}", cc.tpe)}").mkCode(" AND ")}"
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
          code"def selectByIds($idsParam)(implicit puts: ${JdbcEncoder.of(sc.Type.Array.of(tpe))}): ${ZStream.of(ZConnection, Throwable, rowType)}"
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
    case RepoMethod.Upsert(_, _, _, unsavedParam, rowType) =>
      code"def upsert($unsavedParam): ${ZIO.of(ZConnection, Throwable, rowType)}"
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
        code"""${sc.Type.dsl.SelectBuilderSql}(${sc.StrLit(relName.value)}, $fieldsType, $rowType.read)"""

      case RepoMethod.SelectAll(relName, cols, rowType) =>
        val joinedColNames = dbNames(cols, isRead = true)
        val sql = SQL(code"""select $joinedColNames from $relName""")
        code"""$sql.query[$rowType].selectStream"""

      case RepoMethod.SelectById(relName, cols, id, rowType) =>
        val joinedColNames = dbNames(cols, isRead = true)
        val sql = SQL(code"""select $joinedColNames from $relName where ${matchId(id)}""")
        code"""$sql.query[$rowType].selectOne"""

      case RepoMethod.SelectAllByIds(relName, cols, unaryId, idsParam, rowType) =>
        val joinedColNames = dbNames(cols, isRead = true)

        val sql = SQL(
          code"""select $joinedColNames from $relName where ${code"${unaryId.col.dbName.code} = ANY(${runtimeInterpolateValue(idsParam.name, idsParam.tpe, forbidInline = true)})"}"""
        )
        code"""$sql.query[$rowType].selectStream"""
      case RepoMethod.SelectByUnique(relName, cols, rowType) =>
        val sql = SQL {
          code"""|select ${dbNames(cols, isRead = true)}
                 |from $relName
                 |where ${cols.map(c => code"${c.dbName.code} = ${runtimeInterpolateValue(c.name, c.tpe)}").mkCode(" AND ")}
                 |""".stripMargin
        }
        code"""$sql.query[$rowType].selectOne"""

      case RepoMethod.SelectByFieldValues(relName, cols, fieldValue, fieldValueOrIdsParam, rowType) =>
        val cases =
          cols.map { col =>
            val fr = SQL(code"${col.dbName.code} = ${runtimeInterpolateValue(sc.Ident("value"), col.tpe)}")
            code"case $fieldValue.${col.name}(value) => $fr"
          }

        val sql = SQL(code"""select ${dbNames(cols, isRead = true)} from $relName $$where""")
        code"""${fieldValueOrIdsParam.name} match {
              |  case Nil => $sql.query(${lookupJdbcEncoder(rowType)}).selectStream
              |  case nonEmpty =>
              |    val where = nonEmpty.map {
              |      ${cases.mkCode("\n")}
              |    }
              |    $sql.where(where).query(${lookupJdbcEncoder(rowType)}).selectStream
              |}
              |""".stripMargin

      case RepoMethod.UpdateFieldValues(relName, id, varargs, _, _, _) =>
        // val cases: NonEmptyList[sc.Code] =
        //  cases0.map { col =>
        //    val fr = SQL(code"${col.dbName.code} = ${runtimeInterpolateValue(sc.Ident("value"), col.tpe)}${sqlCast.toPgCode(col)}")
        //    code"case $fieldValue.${col.name}(value) => $fr"
        //  }
//
        val sql = SQL {
          code"""|update $relName
                 |$$updates
                 |where ${matchId(id)}""".stripMargin
        }

        // TODO Jules: What should I put here?
        code"""$NonEmptyChunk.fromIterableOption(${varargs.name}) match {
              |  case None => ${`ZIO.succeed`}(false)
              |  case Some(nonEmpty) =>
              |    val updates = ??? // TODO Jules: What should I put here?
              |    $sql.update.map(_ > 0)
              |}""".stripMargin

      case RepoMethod.UpdateBuilder(relName, fieldsType, rowType) =>
        code"${sc.Type.dsl.UpdateBuilder}(${sc.StrLit(relName.value)}, $fieldsType, $rowType.read)"

      case RepoMethod.Update(relName, _, id, param, colsNotId) =>
        val sql = SQL(
          code"""update $relName
                |set ${colsNotId.map { col => code"${col.dbName.code} = ${runtimeInterpolateValue(code"${param.name}.${col.name}", col.tpe)}${sqlCast.toPgCode(col)}" }.mkCode(",\n")}
                |where ${matchId(id)}""".stripMargin
        )
        code"""|val ${id.paramName} = ${param.name}.${id.paramName}
               |$sql.update.map(_ > 0)"""

      case RepoMethod.InsertUnsaved(relName, cols, unsaved, unsavedParam, default, rowType) =>
        val cases0 = unsaved.restCols.map { col =>
          val set = SQL(code"${runtimeInterpolateValue(code"${unsavedParam.name}.${col.name}", col.tpe)}${sqlCast.toPgCode(col)}")
          code"""Some(($Fragment(${sc.s(col.dbName.code)}), $set))"""
        }
        val cases1 = unsaved.defaultCols.map { case (col @ ComputedColumn(_, ident, _, _), origType) =>
          val setValue = SQL(code"${runtimeInterpolateValue(code"value: $origType", origType)}${sqlCast.toPgCode(col)}")
          code"""|${unsavedParam.name}.$ident match {
                 |  case ${default.Defaulted}.${default.UseDefault} => None
                 |  case ${default.Defaulted}.${default.Provided}(value) => Some(($Fragment(${sc.s(col.dbName.code)}), $setValue))
                 |}"""
        }

        val sql = SQL {
          code"""|insert into $relName($${fs.map { case (n, _) => n }.intercalate(${SQL(code", ")})})
                 |values ($${fs.map { case (_, f) => f }.intercalate(${SQL(code", ")})})
                 |returning ${dbNames(cols, isRead = true)}
                 |""".stripMargin
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
               |  import cats.syntax.foldable.toFoldableOps
               |  $sql
               |}
               |q.query(${lookupJdbcEncoder(rowType)}).unique
               |"""
      case RepoMethod.Upsert(relName, cols, id, unsavedParam, rowType) =>
        val values = cols.map { c =>
          code"${runtimeInterpolateValue(code"${unsavedParam.name}.${c.name}", c.tpe)}${sqlCast.toPgCode(c)}"
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

        code"$sql.query(${lookupJdbcEncoder(rowType)}).unique"

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

        code"$sql.query(${lookupJdbcEncoder(rowType)}).update.map(_ > 0)"

      case RepoMethod.DeleteBuilder(relName, fieldsType, _) =>
        code"${sc.Type.dsl.DeleteBuilder}(${sc.StrLit(relName.value)}, $fieldsType)"
      case RepoMethod.Delete(relName, id) =>
        val sql = SQL(code"""delete from $relName where ${matchId(id)}""")
        code"$sql.update.map(_ > 0)"

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
                 |sql.query(${lookupJdbcEncoder(rowName)}).selectStream""".stripMargin
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
      ZIO.of(ZConnection, Throwable, x.table.names.RowName),
      code"${x.table.names.RepoImplName}.insert(new ${x.cls}(${x.params.map(p => code"${p.name} = ${p.name}").mkCode(", ")}))"
    )

  override def stringEnumInstances(wrapperType: sc.Type, underlying: sc.Type): List[sc.ClassMember] = List.empty // TODO Jules

  override def anyValInstances(wrapperType: Type.Qualified, underlying: sc.Type): List[sc.ClassMember] = List.empty // TODO Jules

  override def missingInstances: List[sc.ClassMember] = List.empty // TODO Jules

  override def rowInstances(tpe: sc.Type, cols: NonEmptyList[ComputedColumn]): List[sc.ClassMember] = List.empty // TODO Jules

  override def customTypeInstances(ct: CustomType): List[sc.ClassMember] = List.empty // TODO Jules
}
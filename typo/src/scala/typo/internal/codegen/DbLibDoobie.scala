package typo
package internal
package codegen

import typo.internal.analysis.MaybeReturnsRows

class DbLibDoobie(pkg: sc.QIdent, inlineImplicits: Boolean, default: ComputedDefault, enableStreamingInserts: Boolean, fixVerySlowImplicit: Boolean) extends DbLib {

  val SqlInterpolator = sc.Type.Qualified("doobie.syntax.string.toSqlInterpolator")
  def SQL(content: sc.Code) =
    sc.StringInterpolate(SqlInterpolator, sc.Ident("sql"), content)
  def frInterpolate(content: sc.Code) =
    sc.StringInterpolate(SqlInterpolator, sc.Ident("fr"), content)
  val Meta = sc.Type.Qualified("doobie.util.meta.Meta")
  val Put = sc.Type.Qualified("doobie.util.Put")
  val Get = sc.Type.Qualified("doobie.util.Get")
  val Write = sc.Type.Qualified("doobie.util.Write")
  val Read = sc.Type.Qualified("doobie.util.Read")
  val ConnectionIO = sc.Type.Qualified("doobie.free.connection.ConnectionIO")
  val Nullability = sc.Type.Qualified("doobie.enumerated.Nullability")
  val Fragments = sc.Type.Qualified("doobie.util.fragments")
  val Fragment = sc.Type.Qualified("doobie.util.fragment.Fragment")
  val pureCIO = sc.Type.Qualified("doobie.free.connection.pure")
  val delayCIO = sc.Type.Qualified("doobie.free.connection.delay")
  val fs2Stream = sc.Type.Qualified("fs2.Stream")
  val NonEmptyList = sc.Type.Qualified("cats.data.NonEmptyList")
  val fromWrite = sc.Type.Qualified("doobie.syntax.SqlInterpolator.SingleFragment.fromWrite")
  val FragmentOps = sc.Type.Qualified("doobie.postgres.syntax.FragmentOps")

  val arrayGetName: sc.Ident = sc.Ident("arrayGet")
  val arrayPutName: sc.Ident = sc.Ident("arrayPut")
  val getName: sc.Ident = sc.Ident("get")
  val putName: sc.Ident = sc.Ident("put")
  val readName: sc.Ident = sc.Ident("read")
  val writeName = sc.Ident("write")

  val textSupport: Option[DbLibTextSupport] =
    if (enableStreamingInserts) Some(new DbLibTextSupport(pkg, inlineImplicits, Some(sc.Type.Qualified("doobie.postgres.Text")), default)) else None

  def dbNames(cols: NonEmptyList[ComputedColumn], isRead: Boolean): sc.Code =
    cols
      .map(c => c.dbName.code ++ (if (isRead) SqlCast.fromPgCode(c) else sc.Code.Empty))
      .mkCode(", ")

  def runtimeInterpolateValue(name: sc.Code, tpe: sc.Type, forbidInline: Boolean = false): sc.Code =
    if (inlineImplicits && !forbidInline)
      tpe match {
        case TypesScala.Optional(underlying) =>
          code"$${$fromWrite($name)($Write.fromPutOption(${lookupPutFor(underlying)}))}"
        case other =>
          code"$${$fromWrite($name)($Write.fromPut(${lookupPutFor(other)}))}"
      }
    else code"$${$name}"

  def matchId(id: IdComputed): sc.Code =
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
      code"def selectAll: ${fs2Stream.of(ConnectionIO, rowType)}"
    case RepoMethod.SelectById(_, _, id, rowType) =>
      code"def selectById(${id.param}): ${ConnectionIO.of(TypesScala.Option.of(rowType))}"
    case RepoMethod.SelectAllByIds(_, _, unaryId, idsParam, rowType) =>
      unaryId match {
        case IdComputed.UnaryUserSpecified(_, tpe) =>
          code"def selectByIds($idsParam)(implicit puts: ${Put.of(TypesScala.Array.of(tpe))}): ${fs2Stream.of(ConnectionIO, rowType)}"
        case _ =>
          code"def selectByIds($idsParam): ${fs2Stream.of(ConnectionIO, rowType)}"
      }
    case RepoMethod.SelectByUnique(_, params, rowType) =>
      code"def selectByUnique(${params.map(_.param.code).mkCode(", ")}): ${ConnectionIO.of(TypesScala.Option.of(rowType))}"
    case RepoMethod.SelectByFieldValues(_, _, _, fieldValueOrIdsParam, rowType) =>
      code"def selectByFieldValues($fieldValueOrIdsParam): ${fs2Stream.of(ConnectionIO, rowType)}"
    case RepoMethod.UpdateBuilder(_, fieldsType, rowType) =>
      code"def update: ${sc.Type.dsl.UpdateBuilder.of(fieldsType, rowType)}"
    case RepoMethod.UpdateFieldValues(_, id, varargs, _, _, _) =>
      code"def updateFieldValues(${id.param}, $varargs): ${ConnectionIO.of(TypesScala.Boolean)}"
    case RepoMethod.Update(_, _, _, param, _) =>
      code"def update($param): ${ConnectionIO.of(TypesScala.Boolean)}"
    case RepoMethod.Insert(_, _, unsavedParam, rowType) =>
      code"def insert($unsavedParam): ${ConnectionIO.of(rowType)}"
    case RepoMethod.InsertUnsaved(_, _, _, unsavedParam, _, rowType) =>
      code"def insert($unsavedParam): ${ConnectionIO.of(rowType)}"
    case RepoMethod.InsertStreaming(_, _, rowType) =>
      code"def insertStreaming(unsaved: ${fs2Stream.of(ConnectionIO, rowType)}, batchSize: ${TypesScala.Int}): ${ConnectionIO.of(TypesScala.Long)}"
    case RepoMethod.InsertUnsavedStreaming(_, unsaved) =>
      code"def insertUnsavedStreaming(unsaved: ${fs2Stream.of(ConnectionIO, unsaved.tpe)}, batchSize: ${TypesScala.Int}): ${ConnectionIO.of(TypesScala.Long)}"
    case RepoMethod.Upsert(_, _, _, unsavedParam, rowType) =>
      code"def upsert($unsavedParam): ${ConnectionIO.of(rowType)}"
    case RepoMethod.DeleteBuilder(_, fieldsType, rowType) =>
      code"def delete: ${sc.Type.dsl.DeleteBuilder.of(fieldsType, rowType)}"
    case RepoMethod.Delete(_, id) =>
      code"def delete(${id.param}): ${ConnectionIO.of(TypesScala.Boolean)}"
    case RepoMethod.SqlFile(sqlScript) =>
      val params = sc.Params(sqlScript.params.map(p => sc.Param(p.name, p.tpe, None)))

      val retType = sqlScript.maybeRowName match {
        case MaybeReturnsRows.Query(rowName) => fs2Stream.of(ConnectionIO, rowName)
        case MaybeReturnsRows.Update         => ConnectionIO.of(TypesScala.Int)
      }

      code"def apply$params: $retType"
  }

  def query(sql: sc.Code, rowType: sc.Type): sc.Code =
    if (fixVerySlowImplicit) code"$sql.query($rowType.$readName)"
    else code"$sql.query[$rowType]"

  override def repoImpl(repoMethod: RepoMethod): sc.Code =
    repoMethod match {
      case RepoMethod.SelectBuilder(relName, fieldsType, rowType) =>
        code"""${sc.Type.dsl.SelectBuilderSql}(${sc.StrLit(relName.value)}, $fieldsType, $rowType.read)"""

      case RepoMethod.SelectAll(relName, cols, rowType) =>
        val joinedColNames = dbNames(cols, isRead = true)
        val sql = SQL(code"""select $joinedColNames from $relName""")
        code"""${query(sql, rowType)}.stream"""

      case RepoMethod.SelectById(relName, cols, id, rowType) =>
        val joinedColNames = dbNames(cols, isRead = true)
        val sql = SQL(code"""select $joinedColNames from $relName where ${matchId(id)}""")
        code"""${query(sql, rowType)}.option"""

      case RepoMethod.SelectAllByIds(relName, cols, unaryId, idsParam, rowType) =>
        val joinedColNames = dbNames(cols, isRead = true)

        val sql = SQL(
          code"""select $joinedColNames from $relName where ${code"${unaryId.col.dbName.code} = ANY(${runtimeInterpolateValue(idsParam.name, idsParam.tpe, forbidInline = true)})"}"""
        )
        code"""${query(sql, rowType)}.stream"""
      case RepoMethod.SelectByUnique(relName, cols, rowType) =>
        val sql = SQL {
          code"""|select ${dbNames(cols, isRead = true)}
                 |from $relName
                 |where ${cols.map(c => code"${c.dbName.code} = ${runtimeInterpolateValue(c.name, c.tpe)}").mkCode(" AND ")}
                 |""".stripMargin
        }
        code"""${query(sql, rowType)}.option"""

      case RepoMethod.SelectByFieldValues(relName, cols, fieldValue, fieldValueOrIdsParam, rowType) =>
        val cases: NonEmptyList[sc.Code] =
          cols.map { col =>
            val fr = frInterpolate(code"${col.dbName.code} = ${runtimeInterpolateValue(sc.Ident("value"), col.tpe)}")
            code"case $fieldValue.${col.name}(value) => $fr"
          }

        val sql = SQL(code"""select ${dbNames(cols, isRead = true)} from $relName $$where""")
        code"""val where = $Fragments.whereAndOpt(
              |  ${fieldValueOrIdsParam.name}.map {
              |    ${cases.mkCode("\n")}
              |  }
              |)
              |${query(sql, rowType)}.stream""".stripMargin

      case RepoMethod.UpdateFieldValues(relName, id, varargs, fieldValue, cases0, _) =>
        val cases: NonEmptyList[sc.Code] =
          cases0.map { col =>
            val fr = frInterpolate(code"${col.dbName.code} = ${runtimeInterpolateValue(sc.Ident("value"), col.tpe)}${SqlCast.toPgCode(col)}")
            code"case $fieldValue.${col.name}(value) => $fr"
          }

        val sql = SQL {
          code"""|update $relName
                 |$$updates
                 |where ${matchId(id)}""".stripMargin
        }

        code"""$NonEmptyList.fromList(${varargs.name}) match {
              |  case None => $pureCIO(false)
              |  case Some(nonEmpty) =>
              |    val updates = $Fragments.set(
              |      nonEmpty.map {
              |        ${cases.mkCode("\n")}
              |      }
              |    )
              |    $sql.update.run.map(_ > 0)
              |}""".stripMargin

      case RepoMethod.UpdateBuilder(relName, fieldsType, rowType) =>
        code"${sc.Type.dsl.UpdateBuilder}(${sc.StrLit(relName.value)}, $fieldsType, $rowType.read)"

      case RepoMethod.Update(relName, _, id, param, colsNotId) =>
        val sql = SQL(
          code"""update $relName
                |set ${colsNotId.map { col => code"${col.dbName.code} = ${runtimeInterpolateValue(code"${param.name}.${col.name}", col.tpe)}${SqlCast.toPgCode(col)}" }.mkCode(",\n")}
                |where ${matchId(id)}""".stripMargin
        )
        code"""|val ${id.paramName} = ${param.name}.${id.paramName}
               |$sql
               |  .update
               |  .run
               |  .map(_ > 0)"""

      case RepoMethod.InsertUnsaved(relName, cols, unsaved, unsavedParam, default, rowType) =>
        val cases0 = unsaved.restCols.map { col =>
          val set = frInterpolate(code"${runtimeInterpolateValue(code"${unsavedParam.name}.${col.name}", col.tpe)}${SqlCast.toPgCode(col)}")
          code"""Some(($Fragment.const(${sc.s(col.dbName.code)}), $set))"""
        }
        val cases1 = unsaved.defaultCols.map { case (col @ ComputedColumn(_, ident, _, _), origType) =>
          val setValue = frInterpolate(code"${runtimeInterpolateValue(code"value: $origType", origType)}${SqlCast.toPgCode(col)}")
          code"""|${unsavedParam.name}.$ident match {
                 |  case ${default.Defaulted}.${default.UseDefault} => None
                 |  case ${default.Defaulted}.${default.Provided}(value) => Some(($Fragment.const(${sc.s(col.dbName.code)}), $setValue))
                 |}"""
        }

        val sql = SQL {
          code"""|insert into $relName($${CommaSeparate.combineAllOption(fs.map { case (n, _) => n }).get})
                 |values ($${CommaSeparate.combineAllOption(fs.map { case (_, f) => f }).get})
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
               |  val CommaSeparate = $Fragment.FragmentMonoid.intercalate(fr", ")
               |  $sql
               |}
               |${query(sc.Ident("q"), rowType)}.unique
               |"""

      case RepoMethod.InsertStreaming(relName, cols, rowType) =>
        val sql = SQL(code"COPY $relName(${dbNames(cols, isRead = false)}) FROM STDIN")

        if (fixVerySlowImplicit) code"new $FragmentOps($sql).copyIn(unsaved, batchSize)(${textSupport.get.lookupTextFor(rowType)})"
        else code"new $FragmentOps($sql).copyIn[$rowType](unsaved, batchSize)"
      case RepoMethod.InsertUnsavedStreaming(relName, unsaved) =>
        val sql = SQL(code"COPY $relName(${dbNames(unsaved.allCols, isRead = false)}) FROM STDIN (DEFAULT '${textSupport.get.DefaultValue}')")

        if (fixVerySlowImplicit) code"new $FragmentOps($sql).copyIn(unsaved, batchSize)(${textSupport.get.lookupTextFor(unsaved.tpe)})"
        else code"new $FragmentOps($sql).copyIn[${unsaved.tpe}](unsaved, batchSize)"

      case RepoMethod.Upsert(relName, cols, id, unsavedParam, rowType) =>
        val values = cols.map { c =>
          code"${runtimeInterpolateValue(code"${unsavedParam.name}.${c.name}", c.tpe)}${SqlCast.toPgCode(c)}"
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

        code"${query(sql, rowType)}.unique"

      case RepoMethod.Insert(relName, cols, unsavedParam, rowType) =>
        val values = cols.map { c =>
          code"${runtimeInterpolateValue(code"${unsavedParam.name}.${c.name}", c.tpe)}${SqlCast.toPgCode(c)}"
        }
        val sql = SQL {
          code"""|insert into $relName(${dbNames(cols, isRead = false)})
                 |values (${values.mkCode(", ")})
                 |returning ${dbNames(cols, isRead = true)}
                 |""".stripMargin
        }

        code"${query(sql, rowType)}.unique"

      case RepoMethod.DeleteBuilder(relName, fieldsType, _) =>
        code"${sc.Type.dsl.DeleteBuilder}(${sc.StrLit(relName.value)}, $fieldsType)"
      case RepoMethod.Delete(relName, id) =>
        val sql = SQL(code"""delete from $relName where ${matchId(id)}""")
        code"$sql.update.run.map(_ > 0)"

      case RepoMethod.SqlFile(sqlScript) =>
        val renderedScript: sc.Code = sqlScript.sqlFile.decomposedSql.renderCode { (paramAtIndex: Int) =>
          val param = sqlScript.params.find(_.indices.contains(paramAtIndex)).get
          val cast = SqlCast.toPg(param).fold("")(_.withColons)
          code"${runtimeInterpolateValue(param.name, param.tpe)}$cast"
        }
        val ret = for {
          cols <- sqlScript.maybeCols.toOption
          rowType <- sqlScript.maybeRowName.toOption
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
                 |${query(sc.Ident("sql"), rowType)}.stream""".stripMargin

        }
        ret.getOrElse {
          code"${SQL(renderedScript)}.update.run"
        }
    }

  override def mockRepoImpl(id: IdComputed, repoMethod: RepoMethod, maybeToRow: Option[sc.Param]): sc.Code = {
    repoMethod match {
      case RepoMethod.SelectBuilder(_, fieldsType, _) =>
        code"${sc.Type.dsl.SelectBuilderMock}($fieldsType, $delayCIO(map.values.toList), ${sc.Type.dsl.SelectParams}.empty)"
      case RepoMethod.SelectAll(_, _, _) =>
        code"$fs2Stream.emits(map.values.toList)"
      case RepoMethod.SelectById(_, _, id, _) =>
        code"$delayCIO(map.get(${id.paramName}))"
      case RepoMethod.SelectAllByIds(_, _, _, idsParam, _) =>
        code"$fs2Stream.emits(${idsParam.name}.flatMap(map.get).toList)"
      case RepoMethod.SelectByUnique(_, cols, _) =>
        code"${delayCIO}(map.values.find(v => ${cols.map(c => code"${c.name} == v.${c.name}").mkCode(" && ")}))"

      case RepoMethod.SelectByFieldValues(_, cols, fieldValue, fieldValueOrIdsParam, _) =>
        val cases = cols.map { col =>
          code"case (acc, $fieldValue.${col.name}(value)) => acc.filter(_.${col.name} == value)"
        }
        code"""$fs2Stream.emits {
              |  ${fieldValueOrIdsParam.name}.foldLeft(map.values) {
              |    ${cases.mkCode("\n")}
              |  }.toList
              |}""".stripMargin
      case RepoMethod.UpdateBuilder(_, fieldsType, _) =>
        code"${sc.Type.dsl.UpdateBuilderMock}(${sc.Type.dsl.UpdateParams}.empty, $fieldsType, map)"
      case RepoMethod.UpdateFieldValues(_, id, varargs, fieldValue, cases0, _) =>
        val cases = cases0.map { col =>
          code"case (acc, $fieldValue.${col.name}(value)) => acc.copy(${col.name} = value)"
        }

        code"""|$delayCIO {
               |  map.get(${id.paramName}) match {
               |    case ${TypesScala.Some}(oldRow) =>
               |      val updatedRow = ${varargs.name}.foldLeft(oldRow) {
               |        ${cases.mkCode("\n")}
               |      }
               |      if (updatedRow != oldRow) {
               |        map.put(${id.paramName}, updatedRow): @${TypesScala.nowarn}
               |        true
               |      } else {
               |        false
               |      }
               |    case ${TypesScala.None} => false
               |  }
               |}""".stripMargin
      case RepoMethod.Update(_, _, _, param, _) =>
        code"""$delayCIO {
              |  map.get(${param.name}.${id.paramName}) match {
              |    case ${TypesScala.Some}(`${param.name}`) => false
              |    case ${TypesScala.Some}(_) =>
              |      map.put(${param.name}.${id.paramName}, ${param.name}): @${TypesScala.nowarn}
              |      true
              |    case ${TypesScala.None} => false
              |  }
              |}""".stripMargin
      case RepoMethod.Insert(_, _, unsavedParam, _) =>
        code"""|$delayCIO {
               |  val _ = if (map.contains(${unsavedParam.name}.${id.paramName}))
               |    sys.error(s"id $${${unsavedParam.name}.${id.paramName}} already exists")
               |  else
               |    map.put(${unsavedParam.name}.${id.paramName}, ${unsavedParam.name})
               |
               |  ${unsavedParam.name}
               |}"""
      case RepoMethod.Upsert(_, _, _, unsavedParam, _) =>
        code"""|$delayCIO {
               |  map.put(${unsavedParam.name}.${id.paramName}, ${unsavedParam.name}): @${TypesScala.nowarn}
               |  ${unsavedParam.name}
               |}""".stripMargin
      case RepoMethod.InsertUnsaved(_, _, _, unsavedParam, _, _) =>
        code"insert(${maybeToRow.get.name}(${unsavedParam.name}))"
      case RepoMethod.InsertStreaming(_, _, _) =>
        code"""|unsaved.compile.toList.map { rows =>
               |  var num = 0L
               |  rows.foreach { row =>
               |    map += (row.${id.paramName} -> row)
               |    num += 1
               |  }
               |  num
               |}""".stripMargin
      case RepoMethod.InsertUnsavedStreaming(_, _) =>
        code"""|unsaved.compile.toList.map { unsavedRows =>
               |  var num = 0L
               |  unsavedRows.foreach { unsavedRow =>
               |    val row = ${maybeToRow.get.name}(unsavedRow)
               |    map += (row.${id.paramName} -> row)
               |    num += 1
               |  }
               |  num
               |}""".stripMargin

      case RepoMethod.DeleteBuilder(_, fieldsType, _) =>
        code"${sc.Type.dsl.DeleteBuilderMock}(${sc.Type.dsl.DeleteParams}.empty, $fieldsType, map)"
      case RepoMethod.Delete(_, id) =>
        code"$delayCIO(map.remove(${id.paramName}).isDefined)"
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
      Nil,
      ConnectionIO.of(x.table.names.RowName),
      code"(new ${x.table.names.RepoImplName}).insert(new ${x.cls}(${x.params.map(p => code"${p.name} = ${p.name}").mkCode(", ")}))"
    )

  override val defaultedInstance: List[sc.Given] =
    textSupport.map(_.defaultedInstance).toList

  override def stringEnumInstances(wrapperType: sc.Type, underlying: sc.Type): List[sc.Given] =
    List(
      Some(
        sc.Given(
          tparams = Nil,
          name = putName,
          implicitParams = Nil,
          tpe = Put.of(wrapperType),
          body = code"""${lookupPutFor(underlying)}.contramap(_.value)"""
        )
      ),
      Some(
        sc.Given(
          tparams = Nil,
          name = arrayPutName,
          implicitParams = Nil,
          tpe = Put.of(TypesScala.Array.of(wrapperType)),
          body = code"""${lookupPutFor(TypesScala.Array.of(underlying))}.contramap(_.map(_.value))"""
        )
      ),
      Some(
        sc.Given(
          tparams = Nil,
          name = getName,
          implicitParams = Nil,
          tpe = Get.of(wrapperType),
          body = code"""${lookupGetFor(underlying)}.temap($wrapperType.apply)"""
        )
      ),
      Some(
        sc.Given(
          tparams = Nil,
          name = arrayGetName,
          implicitParams = Nil,
          tpe = Get.of(TypesScala.Array.of(wrapperType)),
          body = code"""${lookupGetFor(TypesScala.Array.of(underlying))}.map(_.map(force))"""
        )
      ),
      Some(
        sc.Given(
          tparams = Nil,
          name = writeName,
          implicitParams = Nil,
          tpe = Write.of(wrapperType),
          body = code"""$Write.fromPut($putName)"""
        )
      ),
      Some(
        sc.Given(
          tparams = Nil,
          name = readName,
          implicitParams = Nil,
          tpe = Read.of(wrapperType),
          body = code"""$Read.fromGet($getName)"""
        )
      ),
      textSupport.map(_.anyValInstance(wrapperType, underlying))
    ).flatten

  override def anyValInstances(wrapperType: sc.Type.Qualified, underlying: sc.Type): List[sc.Given] =
    List(
      Some(
        sc.Given(
          tparams = Nil,
          name = putName,
          implicitParams = Nil,
          tpe = Put.of(wrapperType),
          body = code"${lookupPutFor(underlying)}.contramap(_.value)"
        )
      ),
      Some(
        sc.Given(
          tparams = Nil,
          name = getName,
          implicitParams = Nil,
          tpe = Get.of(wrapperType),
          body = code"${lookupGetFor(underlying)}.map($wrapperType.apply)"
        )
      ),
      Some(
        sc.Given(
          tparams = Nil,
          name = arrayPutName,
          implicitParams = Nil,
          tpe = Put.of(TypesScala.Array.of(wrapperType)),
          body = code"${lookupPutFor(TypesScala.Array.of(underlying))}.contramap(_.map(_.value))"
        )
      ),
      Some(
        sc.Given(
          tparams = Nil,
          name = arrayGetName,
          implicitParams = Nil,
          tpe = Get.of(TypesScala.Array.of(wrapperType)),
          body = code"${lookupGetFor(TypesScala.Array.of(underlying))}.map(_.map($wrapperType.apply))"
        )
      ),
      textSupport.map(_.anyValInstance(wrapperType, underlying))
    ).flatten

  override val missingInstances: List[sc.ClassMember] = {
    def i(name: String, metaType: sc.Type, qident: String) =
      sc.Given(Nil, sc.Ident(name), Nil, Meta.of(metaType), sc.QIdent(qident))

    List(
      i("UUIDMeta", TypesJava.UUID, "doobie.postgres.implicits.UuidType"),
      i("UUIDArrayMeta", TypesScala.Array.of(TypesJava.UUID), "doobie.postgres.implicits.unliftedUUIDArrayType"),
      i("StringArrayMeta", TypesScala.Array.of(TypesJava.String), "doobie.postgres.implicits.unliftedStringArrayType"),
      i("BooleanArrayMeta", TypesScala.Array.of(TypesScala.Boolean), "doobie.postgres.implicits.unliftedUnboxedBooleanArrayType"),
      i("IntegerArrayMeta", TypesScala.Array.of(TypesScala.Int), "doobie.postgres.implicits.unliftedUnboxedIntegerArrayType"),
      i("LongArrayMeta", TypesScala.Array.of(TypesScala.Long), "doobie.postgres.implicits.unliftedUnboxedLongArrayType"),
      i("FloatArrayMeta", TypesScala.Array.of(TypesScala.Float), "doobie.postgres.implicits.unliftedUnboxedFloatArrayType"),
      i("DoubleArrayMeta", TypesScala.Array.of(TypesScala.Double), "doobie.postgres.implicits.unliftedUnboxedDoubleArrayType"),
      i("BigDecimalMeta", TypesScala.Array.of(TypesScala.BigDecimal), "doobie.postgres.implicits.bigDecimalMeta")
    )
  }

  val missingInstancesByType: Map[sc.Type, sc.QIdent] =
    missingInstances.collect { case x: sc.Given => (x.tpe, pkg / x.name) }.toMap

  /** Resolve known implicits at generation-time instead of at compile-time */
  def lookupGetFor(tpe: sc.Type): sc.Code =
    if (!inlineImplicits) Get.of(tpe).code
    else
      sc.Type.base(tpe) match {
        case TypesScala.BigDecimal => code"$Meta.ScalaBigDecimalMeta.get"
        case TypesScala.Boolean    => code"$Meta.BooleanMeta.get"
        case TypesScala.Byte       => code"$Meta.ByteMeta.get"
        case TypesScala.Double     => code"$Meta.DoubleMeta.get"
        case TypesScala.Float      => code"$Meta.FloatMeta.get"
        case TypesScala.Int        => code"$Meta.IntMeta.get"
        case TypesScala.Long       => code"$Meta.LongMeta.get"
        case TypesJava.String      => code"$Meta.StringMeta.get"
        case sc.Type.TApply(TypesScala.Array, List(TypesScala.Byte)) =>
          code"$Meta.ByteArrayMeta.get"
        case x: sc.Type.Qualified if x.value.idents.startsWith(pkg.idents) =>
          code"$tpe.$getName"
        case sc.Type.TApply(TypesScala.Array, List(targ: sc.Type.Qualified)) if targ.value.idents.startsWith(pkg.idents) =>
          code"$targ.$arrayGetName"
        case x if missingInstancesByType.contains(Meta.of(x)) =>
          code"${missingInstancesByType(Meta.of(x))}.get"
        case other =>
          code"${Get.of(other)}"
      }

  /** Resolve known implicits at generation-time instead of at compile-time */
  def lookupPutFor(tpe: sc.Type): sc.Code =
    if (!inlineImplicits) Put.of(tpe).code
    else
      sc.Type.base(tpe) match {
        case TypesScala.BigDecimal => code"$Meta.ScalaBigDecimalMeta.put"
        case TypesScala.Boolean    => code"$Meta.BooleanMeta.put"
        case TypesScala.Byte       => code"$Meta.ByteMeta.put"
        case TypesScala.Double     => code"$Meta.DoubleMeta.put"
        case TypesScala.Float      => code"$Meta.FloatMeta.put"
        case TypesScala.Int        => code"$Meta.IntMeta.put"
        case TypesScala.Long       => code"$Meta.LongMeta.put"
        case TypesJava.String      => code"$Meta.StringMeta.put"
        case sc.Type.TApply(TypesScala.Array, List(TypesScala.Byte)) =>
          code"$Meta.ByteArrayMeta.put"
        case x: sc.Type.Qualified if x.value.idents.startsWith(pkg.idents) =>
          code"$tpe.$putName"
        case sc.Type.TApply(TypesScala.Array, List(targ: sc.Type.Qualified)) if targ.value.idents.startsWith(pkg.idents) =>
          code"$targ.$arrayPutName"
        case x if missingInstancesByType.contains(Meta.of(x)) =>
          code"${missingInstancesByType(Meta.of(x))}.put"
        case other =>
          code"${Put.of(other)}"
      }

  override def rowInstances(tpe: sc.Type, cols: NonEmptyList[ComputedColumn], rowType: DbLib.RowType): List[sc.Given] = {
    val text = textSupport.map(_.rowInstance(tpe, cols))

    val read = {
      val getCols = cols.map { c =>
        c.tpe match {
          case TypesScala.Optional(underlying) => code"(${lookupGetFor(underlying)}, $Nullability.Nullable)"
          case other                           => code"(${lookupGetFor(other)}, $Nullability.NoNulls)"
        }
      }

      val namedParams = cols.zipWithIndex.map { case (c, idx) =>
        c.tpe match {
          case TypesScala.Optional(underlying) =>
            code"${c.name} = ${lookupGetFor(underlying)}.unsafeGetNullable(rs, i + $idx)"
          case other =>
            code"${c.name} = ${lookupGetFor(other)}.unsafeGetNonNullable(rs, i + $idx)"
        }
      }

      val body =
        code"""|new ${Read.of(tpe)}(
               |  gets = ${TypesScala.List}(
               |    ${getCols.mkCode(",\n")}
               |  ),
               |  unsafeGet = (rs: ${TypesJava.ResultSet}, i: ${TypesScala.Int}) => $tpe(
               |    ${namedParams.mkCode(",\n")}
               |  )
               |)
               |""".stripMargin

      sc.Given(tparams = Nil, name = readName, implicitParams = Nil, tpe = Read.of(tpe), body = body)
    }
    rowType match {
      case DbLib.RowType.Writable      => text.toList
      case DbLib.RowType.ReadWriteable => List(read) ++ text
      case DbLib.RowType.Readable      => List(read)
    }
  }

  override def customTypeInstances(ct: CustomType): List[sc.ClassMember] = {

    val v = sc.Ident("v")
    val sqlTypeLit = sc.StrLit(ct.sqlType)
    val single =
      List(
        Some(
          sc.Given(
            tparams = Nil,
            name = getName,
            implicitParams = Nil,
            tpe = Get.of(ct.typoType),
            body = code"""|$Get.Advanced.other[${ct.toTypo.jdbcType}]($NonEmptyList.one($sqlTypeLit))
                          |  .map($v => ${ct.toTypo0(v)})""".stripMargin
          )
        ),
        Some(
          sc.Given(
            tparams = Nil,
            name = putName,
            implicitParams = Nil,
            tpe = Put.of(ct.typoType),
            body = code"$Put.Advanced.other[${ct.fromTypo.jdbcType}]($NonEmptyList.one($sqlTypeLit)).contramap($v => ${ct.fromTypo0(v)})"
          )
        ),
        textSupport.map(_.customTypeInstance(ct))
      ).flatten

    val array =
      if (ct.forbidArray) Nil
      else {
        val fromTypo = ct.fromTypoInArray.getOrElse(ct.fromTypo)
        val toTypo = ct.toTypoInArray.getOrElse(ct.toTypo)
        val sqlArrayTypeLit = sc.StrLit("_" + ct.sqlType)
        val arrayType = TypesScala.Array.of(ct.typoType)
        List(
          sc.Given(
            tparams = Nil,
            name = arrayGetName,
            implicitParams = Nil,
            tpe = Get.of(arrayType),
            body = code"""|$Get.Advanced.array[${TypesScala.AnyRef}]($NonEmptyList.one($sqlArrayTypeLit))
                        |  .map(_.map($v => ${toTypo.toTypo(code"$v.asInstanceOf[${toTypo.jdbcType}]", ct.typoType)}))""".stripMargin
          ),
          sc.Given(
            tparams = Nil,
            name = arrayPutName,
            implicitParams = Nil,
            tpe = Put.of(arrayType),
            body = code"""|$Put.Advanced.array[${TypesScala.AnyRef}]($NonEmptyList.one($sqlArrayTypeLit), $sqlTypeLit)
                        |  .contramap(_.map($v => ${fromTypo.fromTypo0(v)}))""".stripMargin
          )
        )
      }

    single ++ array
  }

  val additionalFiles: List[typo.sc.File] = Nil
}

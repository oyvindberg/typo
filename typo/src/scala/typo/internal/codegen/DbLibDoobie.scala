package typo
package internal
package codegen

import typo.internal.sqlfiles.MaybeReturnsRows

class DbLibDoobie(pkg: sc.QIdent, inlineImplicits: Boolean) extends DbLib {

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

  val arrayGetName: sc.Ident = sc.Ident("arrayGet")
  val arrayPutName: sc.Ident = sc.Ident("arrayPut")
  val getName: sc.Ident = sc.Ident("get")
  val putName: sc.Ident = sc.Ident("put")
  val readName: sc.Ident = sc.Ident("read")
  val writeName = sc.Ident("write")

  def dbNames(cols: NonEmptyList[ComputedColumn], isRead: Boolean): sc.Code =
    cols
      .map(c => maybeQuoted(c.dbName) ++ (if (isRead) sqlCast.fromPgCode(c) else sc.Code.Empty))
      .mkCode(", ")

  def runtimeInterpolateValue(name: sc.Code, tpe: sc.Type, forbidInline: Boolean = false): sc.Code =
    if (inlineImplicits && !forbidInline)
      tpe match {
        case sc.Type.Optional(underlying) =>
          code"$${$fromWrite($name)($Write.fromPutOption(${lookupPutFor(underlying)}))}"
        case other =>
          code"$${$fromWrite($name)($Write.fromPut(${lookupPutFor(other)}))}"
      }
    else code"$${$name}"

  def matchId(id: IdComputed): sc.Code =
    id match {
      case id: IdComputed.Unary =>
        code"${maybeQuoted(id.col.dbName)} = ${runtimeInterpolateValue(id.paramName, id.tpe)}"
      case composite: IdComputed.Composite =>
        code"${composite.cols.map(cc => code"${maybeQuoted(cc.dbName)} = ${runtimeInterpolateValue(code"${composite.paramName}.${cc.name}", cc.tpe)}").mkCode(" AND ")}"
    }

  override def repoSig(repoMethod: RepoMethod): sc.Code = repoMethod match {
    case RepoMethod.SelectBuilder(_, fieldsType, rowType) =>
      code"def select: ${sc.Type.dsl.SelectBuilder.of(fieldsType, rowType)}"
    case RepoMethod.SelectAll(_, _, rowType) =>
      code"def selectAll: ${fs2Stream.of(ConnectionIO, rowType)}"
    case RepoMethod.SelectById(_, _, id, rowType) =>
      code"def selectById(${id.param}): ${ConnectionIO.of(sc.Type.Option.of(rowType))}"
    case RepoMethod.SelectAllByIds(_, _, unaryId, idsParam, rowType) =>
      unaryId match {
        case IdComputed.UnaryUserSpecified(_, tpe) =>
          code"def selectByIds($idsParam)(implicit puts: ${Put.of(sc.Type.Array.of(tpe))}): ${fs2Stream.of(ConnectionIO, rowType)}"
        case _ =>
          code"def selectByIds($idsParam): ${fs2Stream.of(ConnectionIO, rowType)}"
      }
    case RepoMethod.SelectByUnique(params, _, rowType) =>
      val ident = Naming.camelCaseIdent(Array("selectByUnique"))
      code"def $ident(${params.map(_.param.code).mkCode(", ")}): ${ConnectionIO.of(sc.Type.Option.of(rowType))}"
    case RepoMethod.SelectByFieldValues(_, _, _, fieldValueOrIdsParam, rowType) =>
      code"def selectByFieldValues($fieldValueOrIdsParam): ${fs2Stream.of(ConnectionIO, rowType)}"
    case RepoMethod.UpdateBuilder(_, fieldsType, rowType) =>
      code"def update: ${sc.Type.dsl.UpdateBuilder.of(fieldsType, rowType)}"
    case RepoMethod.UpdateFieldValues(_, id, varargs, _, _, _) =>
      code"def updateFieldValues(${id.param}, $varargs): ${ConnectionIO.of(sc.Type.Boolean)}"
    case RepoMethod.Update(_, _, _, param, _) =>
      code"def update($param): ${ConnectionIO.of(sc.Type.Boolean)}"
    case RepoMethod.Insert(_, _, unsavedParam, rowType) =>
      code"def insert($unsavedParam): ${ConnectionIO.of(rowType)}"
    case RepoMethod.InsertUnsaved(_, _, _, unsavedParam, _, rowType) =>
      code"def insert($unsavedParam): ${ConnectionIO.of(rowType)}"
    case RepoMethod.Upsert(_, _, _, unsavedParam, rowType) =>
      code"def upsert($unsavedParam): ${ConnectionIO.of(rowType)}"
    case RepoMethod.DeleteBuilder(_, fieldsType, rowType) =>
      code"def delete: ${sc.Type.dsl.DeleteBuilder.of(fieldsType, rowType)}"
    case RepoMethod.Delete(_, id) =>
      code"def delete(${id.param}): ${ConnectionIO.of(sc.Type.Boolean)}"
    case RepoMethod.SqlFile(sqlScript) =>
      val params = sc.Params(sqlScript.params.map(p => sc.Param(p.name, p.tpe, None)))

      val retType = sqlScript.maybeRowName match {
        case MaybeReturnsRows.Query(rowName) => fs2Stream.of(ConnectionIO, rowName)
        case MaybeReturnsRows.Update         => ConnectionIO.of(sc.Type.Int)
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
        code"""$sql.query($rowType.$readName).stream"""

      case RepoMethod.SelectById(relName, cols, id, rowType) =>
        val joinedColNames = dbNames(cols, isRead = true)
        val sql = SQL(code"""select $joinedColNames from $relName where ${matchId(id)}""")
        code"""$sql.query($rowType.$readName).option"""

      case RepoMethod.SelectAllByIds(relName, cols, unaryId, idsParam, rowType) =>
        val joinedColNames = dbNames(cols, isRead = true)

        val sql = SQL(
          code"""select $joinedColNames from $relName where ${code"${maybeQuoted(unaryId.col.dbName)} = ANY(${runtimeInterpolateValue(idsParam.name, idsParam.tpe, forbidInline = true)})"}"""
        )
        code"""$sql.query($rowType.$readName).stream"""
      case RepoMethod.SelectByUnique(params, fieldValue, _) =>
        val args = params.map { param => code"$fieldValue.${param.name}(${param.name})" }.mkCode(", ")
        code"""selectByFieldValues(${sc.Type.List}($args)).compile.last"""

      case RepoMethod.SelectByFieldValues(relName, cols, fieldValue, fieldValueOrIdsParam, rowType) =>
        val cases: NonEmptyList[sc.Code] =
          cols.map { col =>
            val fr = frInterpolate(code"${maybeQuoted(col.dbName)} = ${runtimeInterpolateValue(sc.Ident("value"), col.tpe)}")
            code"case $fieldValue.${col.name}(value) => $fr"
          }

        val sql = SQL(code"""select ${dbNames(cols, isRead = true)} from $relName $$where""")
        code"""val where = $Fragments.whereAndOpt(
              |  ${fieldValueOrIdsParam.name}.map {
              |    ${cases.mkCode("\n")}
              |  }
              |)
              |$sql.query($rowType.$readName).stream""".stripMargin

      case RepoMethod.UpdateFieldValues(relName, id, varargs, fieldValue, cases0, _) =>
        val cases: NonEmptyList[sc.Code] =
          cases0.map { col =>
            val fr = frInterpolate(code"${maybeQuoted(col.dbName)} = ${runtimeInterpolateValue(sc.Ident("value"), col.tpe)}${sqlCast.toPgCode(col)}")
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
                |set ${colsNotId.map { col => code"${maybeQuoted(col.dbName)} = ${runtimeInterpolateValue(code"${param.name}.${col.name}", col.tpe)}${sqlCast.toPgCode(col)}" }.mkCode(",\n")}
                |where ${matchId(id)}""".stripMargin
        )
        code"""|val ${id.paramName} = ${param.name}.${id.paramName}
               |$sql
               |  .update
               |  .run
               |  .map(_ > 0)"""

      case RepoMethod.InsertUnsaved(relName, cols, unsaved, unsavedParam, default, rowType) =>
        val cases0 = unsaved.restCols.map { col =>
          val set = frInterpolate(code"${runtimeInterpolateValue(code"${unsavedParam.name}.${col.name}", col.tpe)}${sqlCast.toPgCode(col)}")
          code"""Some(($Fragment.const(${sc.s(maybeQuoted(col.dbName))}), $set))"""
        }
        val cases1 = unsaved.defaultCols.map { case (col @ ComputedColumn(_, ident, _, _), origType) =>
          val setValue = frInterpolate(code"${runtimeInterpolateValue(code"value: $origType", origType)}${sqlCast.toPgCode(col)}")
          code"""|${unsavedParam.name}.$ident match {
                 |  case ${default.Defaulted}.${default.UseDefault} => None
                 |  case ${default.Defaulted}.${default.Provided}(value) => Some(($Fragment.const(${sc.s(maybeQuoted(col.dbName))}), $setValue))
                 |}"""
        }

        val sql = SQL {
          code"""|insert into $relName($${fs.map { case (n, _) => n }.intercalate(${frInterpolate(code", ")})})
                 |values ($${fs.map { case (_, f) => f }.intercalate(${frInterpolate(code", ")})})
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
               |q.query($rowType.$readName).unique
               |"""
      case RepoMethod.Upsert(relName, cols, id, unsavedParam, rowType) =>
        val values = cols.map { c =>
          code"${runtimeInterpolateValue(code"${unsavedParam.name}.${c.name}", c.tpe)}${sqlCast.toPgCode(c)}"
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

        code"$sql.query($rowType.$readName).unique"

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

        code"$sql.query($rowType.$readName).unique"

      case RepoMethod.DeleteBuilder(relName, fieldsType, _) =>
        code"${sc.Type.dsl.DeleteBuilder}(${sc.StrLit(relName.value)}, $fieldsType)"
      case RepoMethod.Delete(relName, id) =>
        val sql = SQL(code"""delete from $relName where ${matchId(id)}""")
        code"$sql.update.run.map(_ > 0)"

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
                       |select ${cols.map(c => code"$row.${maybeQuoted(c.dbName)}${sqlCast.fromPgCode(c)}").mkCode(", ")}
                       |from $row""".stripMargin
            }

          code"""|val sql =
                 |  ${SQL(renderedWithCasts)}
                 |sql.query($rowName.$readName).stream""".stripMargin

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
      case RepoMethod.SelectByUnique(params, fieldValue, _) =>
        val args = params.map { param =>
          code"$fieldValue.${param.name}(${param.name})"
        }
        code"""selectByFieldValues(${sc.Type.List}(${args.mkCode(", ")})).compile.last"""

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
               |    case ${sc.Type.Some}(oldRow) =>
               |      val updatedRow = ${varargs.name}.foldLeft(oldRow) {
               |        ${cases.mkCode("\n")}
               |      }
               |      if (updatedRow != oldRow) {
               |        map.put(${id.paramName}, updatedRow)
               |        true
               |      } else {
               |        false
               |      }
               |    case ${sc.Type.None} => false
               |  }
               |}""".stripMargin
      case RepoMethod.Update(_, _, _, param, _) =>
        code"""$delayCIO {
              |  map.get(${param.name}.${id.paramName}) match {
              |    case ${sc.Type.Some}(`${param.name}`) => false
              |    case ${sc.Type.Some}(_) =>
              |      map.put(${param.name}.${id.paramName}, ${param.name})
              |      true
              |    case ${sc.Type.None} => false
              |  }
              |}""".stripMargin
      case RepoMethod.Insert(_, _, unsavedParam, _) =>
        code"""|$delayCIO {
               |  if (map.contains(${unsavedParam.name}.${id.paramName}))
               |    sys.error(s"id $${${unsavedParam.name}.${id.paramName}} already exists")
               |  else
               |    map.put(${unsavedParam.name}.${id.paramName}, ${unsavedParam.name})
               |  ${unsavedParam.name}
               |}"""
      case RepoMethod.Upsert(_, _, _, unsavedParam, _) =>
        code"""|$delayCIO {
               |  map.put(${unsavedParam.name}.${id.paramName}, ${unsavedParam.name})
               |  ${unsavedParam.name}
               |}""".stripMargin
      case RepoMethod.InsertUnsaved(_, _, _, unsavedParam, _, _) =>
        code"insert(${maybeToRow.get.name}(${unsavedParam.name}))"

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
      code"${x.table.names.RepoImplName}.insert(new ${x.cls}(${x.params.map(p => code"${p.name} = ${p.name}").mkCode(", ")}))"
    )

  override def stringEnumInstances(wrapperType: sc.Type, underlying: sc.Type): List[sc.Given] = {
    List(
      sc.Given(
        tparams = Nil,
        name = putName,
        implicitParams = Nil,
        tpe = Put.of(wrapperType),
        body = code"""${lookupPutFor(underlying)}.contramap(_.value)"""
      ),
      sc.Given(
        tparams = Nil,
        name = arrayPutName,
        implicitParams = Nil,
        tpe = Put.of(sc.Type.Array.of(wrapperType)),
        body = code"""${lookupPutFor(sc.Type.Array.of(underlying))}.contramap(_.map(_.value))"""
      ),
      sc.Given(
        tparams = Nil,
        name = getName,
        implicitParams = Nil,
        tpe = Get.of(wrapperType),
        body = code"""${lookupGetFor(underlying)}.temap($wrapperType.apply)"""
      ),
      sc.Given(
        tparams = Nil,
        name = arrayGetName,
        implicitParams = Nil,
        tpe = Get.of(sc.Type.Array.of(wrapperType)),
        body = code"""${lookupGetFor(sc.Type.Array.of(underlying))}.map(_.map(force))"""
      ),
      sc.Given(
        tparams = Nil,
        name = writeName,
        implicitParams = Nil,
        tpe = Write.of(wrapperType),
        body = code"""$Write.fromPut($putName)"""
      ),
      sc.Given(
        tparams = Nil,
        name = readName,
        implicitParams = Nil,
        tpe = Read.of(wrapperType),
        body = code"""$Read.fromGet($getName)"""
      )
    )
  }

  override def anyValInstances(wrapperType: sc.Type.Qualified, underlying: sc.Type): List[sc.Given] =
    List(
      sc.Given(
        tparams = Nil,
        name = putName,
        implicitParams = Nil,
        tpe = Put.of(wrapperType),
        body = code"${lookupPutFor(underlying)}.contramap(_.value)"
      ),
      sc.Given(
        tparams = Nil,
        name = getName,
        implicitParams = Nil,
        tpe = Get.of(wrapperType),
        body = code"${lookupGetFor(underlying)}.map($wrapperType.apply)"
      ),
      sc.Given(
        tparams = Nil,
        name = arrayPutName,
        implicitParams = Nil,
        tpe = Put.of(sc.Type.Array.of(wrapperType)),
        body = code"${lookupPutFor(sc.Type.Array.of(underlying))}.contramap(_.map(_.value))"
      ),
      sc.Given(
        tparams = Nil,
        name = arrayGetName,
        implicitParams = Nil,
        tpe = Get.of(sc.Type.Array.of(wrapperType)),
        body = code"${lookupGetFor(sc.Type.Array.of(underlying))}.map(_.map($wrapperType.apply))"
      )
    )

  override val missingInstances: List[sc.ClassMember] = {
    def i(name: String, metaType: sc.Type, qident: String) =
      sc.Given(Nil, sc.Ident(name), Nil, Meta.of(metaType), sc.QIdent(qident))

    List(
      i("UUIDMeta", sc.Type.UUID, "doobie.postgres.implicits.UuidType"),
      i("UUIDArrayMeta", sc.Type.Array.of(sc.Type.UUID), "doobie.postgres.implicits.unliftedUUIDArrayType"),
      i("StringArrayMeta", sc.Type.Array.of(sc.Type.String), "doobie.postgres.implicits.unliftedStringArrayType"),
      i("BooleanArrayMeta", sc.Type.Array.of(sc.Type.Boolean), "doobie.postgres.implicits.unliftedUnboxedBooleanArrayType"),
      i("IntegerArrayMeta", sc.Type.Array.of(sc.Type.Int), "doobie.postgres.implicits.unliftedUnboxedIntegerArrayType"),
      i("LongArrayMeta", sc.Type.Array.of(sc.Type.Long), "doobie.postgres.implicits.unliftedUnboxedLongArrayType"),
      i("FloatArrayMeta", sc.Type.Array.of(sc.Type.Float), "doobie.postgres.implicits.unliftedUnboxedFloatArrayType"),
      i("DoubleArrayMeta", sc.Type.Array.of(sc.Type.Double), "doobie.postgres.implicits.unliftedUnboxedDoubleArrayType"),
      i("BigDecimalMeta", sc.Type.Array.of(sc.Type.BigDecimal), "doobie.postgres.implicits.bigDecimalMeta")
    )
  }

  val missingInstancesByType: Map[sc.Type, sc.QIdent] =
    missingInstances.collect { case x: sc.Given => (x.tpe, pkg / x.name) }.toMap

  /** Resolve known implicits at generation-time instead of at compile-time */
  def lookupGetFor(tpe: sc.Type): sc.Code =
    if (!inlineImplicits) Get.of(tpe).code
    else
      sc.Type.base(tpe) match {
        case sc.Type.BigDecimal => code"$Meta.ScalaBigDecimalMeta.get"
        case sc.Type.Boolean    => code"$Meta.BooleanMeta.get"
        case sc.Type.Byte       => code"$Meta.ByteMeta.get"
        case sc.Type.Double     => code"$Meta.DoubleMeta.get"
        case sc.Type.Float      => code"$Meta.FloatMeta.get"
        case sc.Type.Int        => code"$Meta.IntMeta.get"
        case sc.Type.Long       => code"$Meta.LongMeta.get"
        case sc.Type.String     => code"$Meta.StringMeta.get"
        case sc.Type.TApply(sc.Type.Array, List(sc.Type.Byte)) =>
          code"$Meta.ByteArrayMeta.get"
        case x: sc.Type.Qualified if x.value.idents.startsWith(pkg.idents) =>
          code"$tpe.$getName"
        case sc.Type.TApply(sc.Type.Array, List(targ: sc.Type.Qualified)) if targ.value.idents.startsWith(pkg.idents) =>
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
        case sc.Type.BigDecimal => code"$Meta.ScalaBigDecimalMeta.put"
        case sc.Type.Boolean    => code"$Meta.BooleanMeta.put"
        case sc.Type.Byte       => code"$Meta.ByteMeta.put"
        case sc.Type.Double     => code"$Meta.DoubleMeta.put"
        case sc.Type.Float      => code"$Meta.FloatMeta.put"
        case sc.Type.Int        => code"$Meta.IntMeta.put"
        case sc.Type.Long       => code"$Meta.LongMeta.put"
        case sc.Type.String     => code"$Meta.StringMeta.put"
        case sc.Type.TApply(sc.Type.Array, List(sc.Type.Byte)) =>
          code"$Meta.ByteArrayMeta.put"
        case x: sc.Type.Qualified if x.value.idents.startsWith(pkg.idents) =>
          code"$tpe.$putName"
        case sc.Type.TApply(sc.Type.Array, List(targ: sc.Type.Qualified)) if targ.value.idents.startsWith(pkg.idents) =>
          code"$targ.$arrayPutName"
        case x if missingInstancesByType.contains(Meta.of(x)) =>
          code"${missingInstancesByType(Meta.of(x))}.put"
        case other =>
          code"${Put.of(other)}"
      }

  override def rowInstances(tpe: sc.Type, cols: NonEmptyList[ComputedColumn]): List[sc.Given] = {
    val getCols = cols.map { c =>
      c.tpe match {
        case sc.Type.Optional(underlying) => code"(${lookupGetFor(underlying)}, $Nullability.Nullable)"
        case other                        => code"(${lookupGetFor(other)}, $Nullability.NoNulls)"
      }
    }

    val namedParams = cols.zipWithIndex.map { case (c, idx) =>
      c.tpe match {
        case sc.Type.Optional(underlying) =>
          code"${c.name} = ${lookupGetFor(underlying)}.unsafeGetNullable(rs, i + $idx)"
        case other =>
          code"${c.name} = ${lookupGetFor(other)}.unsafeGetNonNullable(rs, i + $idx)"
      }
    }

    val body =
      code"""|new ${Read.of(tpe)}(
             |  gets = ${sc.Type.List}(
             |    ${getCols.mkCode(",\n")}
             |  ),
             |  unsafeGet = (rs: ${sc.Type.ResultSet}, i: ${sc.Type.Int}) => $tpe(
             |    ${namedParams.mkCode(",\n")}
             |  )
             |)
             |""".stripMargin

    val instance = sc.Given(tparams = Nil, name = readName, implicitParams = Nil, tpe = Read.of(tpe), body = body)

    List(instance)
  }

  override def customTypeInstances(ct: CustomType): List[sc.ClassMember] = {

    val v = sc.Ident("v")
    val sqlTypeLit = sc.StrLit(ct.sqlType)
    val single = {
      List(
        sc.Given(
          tparams = Nil,
          name = getName,
          implicitParams = Nil,
          tpe = Get.of(ct.typoType),
          body = code"""|$Get.Advanced.other[${ct.toTypo.jdbcType}]($NonEmptyList.one($sqlTypeLit))
                        |  .map($v => ${ct.toTypo0(v)})""".stripMargin
        ),
        sc.Given(
          tparams = Nil,
          name = putName,
          implicitParams = Nil,
          tpe = Put.of(ct.typoType),
          body = code"$Put.Advanced.other[${ct.fromTypo.jdbcType}]($NonEmptyList.one($sqlTypeLit)).contramap($v => ${ct.fromTypo0(v)})"
        )
      )
    }
    val array = {
      val fromTypo = ct.fromTypoInArray.getOrElse(ct.fromTypo)
      val toTypo = ct.toTypoInArray.getOrElse(ct.toTypo)
      val sqlArrayTypeLit = sc.StrLit("_" + ct.sqlType)
      val arrayType = sc.Type.Array.of(ct.typoType)
      List(
        sc.Given(
          tparams = Nil,
          name = arrayGetName,
          implicitParams = Nil,
          tpe = Get.of(arrayType),
          body = code"""|$Get.Advanced.array[${sc.Type.AnyRef}]($NonEmptyList.one($sqlArrayTypeLit))
                        |  .map(_.map($v => ${toTypo.toTypo(code"$v.asInstanceOf[${toTypo.jdbcType}]", ct.typoType)}))""".stripMargin
        ),
        sc.Given(
          tparams = Nil,
          name = arrayPutName,
          implicitParams = Nil,
          tpe = Put.of(arrayType),
          body = code"""|$Put.Advanced.array[${sc.Type.AnyRef}]($NonEmptyList.one($sqlArrayTypeLit), $sqlTypeLit)
                        |  .contramap(_.map($v => ${fromTypo.fromTypo0(v)}))""".stripMargin
        )
      )
    }

    single ++ array
  }
}

package typo
package internal
package codegen

object DbLibDoobie extends DbLib {

  val SqlInterpolator = sc.Type.Qualified("doobie.syntax.string.toSqlInterpolator")
  def SQL(content: sc.Code) =
    sc.StringInterpolate(SqlInterpolator, sc.Ident("sql"), content)
  def frInterpolate(content: sc.Code) =
    sc.StringInterpolate(SqlInterpolator, sc.Ident("fr"), content)
  val Meta = sc.Type.Qualified("doobie.Meta")
  val Put = sc.Type.Qualified("doobie.Put")
  val Get = sc.Type.Qualified("doobie.Get")
  val Write = sc.Type.Qualified("doobie.Write")
  val Read = sc.Type.Qualified("doobie.Read")
  val ConnectionIO = sc.Type.Qualified("doobie.free.connection.ConnectionIO")
  val Query0 = sc.Type.Qualified("doobie.util.query.Query0")
  val Nullability = sc.Type.Qualified("doobie.enumerated.Nullability")
  val Fragments = sc.Type.Qualified("doobie.util.fragments")
  val Fragment = sc.Type.Qualified("doobie.util.fragment.Fragment")
  val pureCIO = sc.Type.Qualified("doobie.free.connection.pure")
  val delayCIO = sc.Type.Qualified("doobie.free.connection.delay")
  val fs2Stream = sc.Type.Qualified("fs2.Stream")
  val NonEmptyList = sc.Type.Qualified("cats.data.NonEmptyList")

  def dbNames(cols: NonEmptyList[ComputedColumn], isRead: Boolean): sc.Code =
    cols
      .map { c =>
        val cast = (isRead, c.dbCol.tpe) match {
          case (true, db.Type.PGmoney)                => code"::numeric"
          case (true, db.Type.Array(db.Type.PGmoney)) => code"::numeric[]"
          case _                                      => sc.Code.Empty
        }
        maybeQuoted(c.dbName) ++ cast
      }
      .mkCode(", ")

  override def stringEnumInstances(wrapperType: sc.Type, underlying: sc.Type, lookup: sc.Ident): List[sc.Code] = {
    List(
      code"""implicit val put: ${Put.of(wrapperType)} = ${Put.of(underlying)}.contramap(_.value)""",
      code"""implicit val get: ${Get.of(wrapperType)} = ${Get.of(
          underlying
        )}.temap { str => ByName.get(str).toRight(s"$$str was not among $${ByName.keys}") }""",
      code"""implicit val write: ${Write.of(wrapperType)} = ${Write.of(underlying)}.contramap(_.value)""",
      code"""implicit val read: ${Read.of(wrapperType)} = ${Read.of(
          underlying
        )}.map(x => ByName.getOrElse(x, throw new IllegalArgumentException(s"$$x was not among $${ByName.keys}")))"""
    )
  }

  override def anyValInstances(wrapperType: sc.Type.Qualified, underlying: sc.Type): List[sc.Code] = {
    val maybeArrayMeta = sc.Type.base(underlying) match {
      case sc.Type.Long       => Some(Meta.of(sc.Type.Array.of(underlying)).code)
      case sc.Type.Int        => Some(Meta.of(sc.Type.Array.of(underlying)).code)
      case sc.Type.Boolean    => Some(Meta.of(sc.Type.Array.of(underlying)).code)
      case sc.Type.Float      => Some(Meta.of(sc.Type.Array.of(underlying)).code)
      case sc.Type.Double     => Some(Meta.of(sc.Type.Array.of(underlying)).code)
      case sc.Type.BigDecimal => Some(Meta.of(sc.Type.Array.of(underlying)).code)
      case sc.Type.UUID       => Some(Meta.of(sc.Type.Array.of(underlying)).code)
      case sc.Type.String     => Some(Meta.of(sc.Type.Array.of(underlying)).code)
      case _                  => None
    }

    val arrayInstances = maybeArrayMeta.map { arrayMeta =>
      code"implicit val metaArray: ${Meta.of(sc.Type.Array.of(wrapperType))} = $arrayMeta.imap(_.map($wrapperType.apply))(_.map(_.value))"
    }

    arrayInstances.toList ++ List(
      code"""implicit val meta: ${Meta.of(wrapperType)} = ${Meta.of(underlying)}.imap($wrapperType.apply)(_.value)"""
    )
  }

  override def repoSig(repoMethod: RepoMethod): sc.Code = repoMethod match {
    case RepoMethod.SelectAll(_, _, rowType) =>
      code"def selectAll: ${fs2Stream.of(ConnectionIO, rowType)}"
    case RepoMethod.SelectById(_, _, id, rowType) =>
      code"def selectById(${id.param}): ${ConnectionIO.of(sc.Type.Option.of(rowType))}"
    case RepoMethod.SelectAllByIds(_, _, unaryId, idsParam, rowType) =>
      unaryId match {
        case IdComputed.UnaryUserSpecified(_, tpe) =>
          code"def selectByIds($idsParam)(implicit puts: ${Put.of(sc.Type.Array.of(tpe))}): ${sc.Type.List.of(rowType)}"
        case _ =>
          code"def selectByIds($idsParam): ${fs2Stream.of(ConnectionIO, rowType)}"
      }
    case RepoMethod.SelectByUnique(params, _, rowType) =>
      val ident = Naming.camelCase(Array("selectByUnique"))
      code"def $ident(${params.map(_.param.code).mkCode(", ")}): ${ConnectionIO.of(sc.Type.Option.of(rowType))}"
    case RepoMethod.SelectByFieldValues(_, _, _, fieldValueOrIdsParam, rowType) =>
      code"def selectByFieldValues($fieldValueOrIdsParam): ${fs2Stream.of(ConnectionIO, rowType)}"
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
    case RepoMethod.Delete(_, id) =>
      code"def delete(${id.param}): ${ConnectionIO.of(sc.Type.Boolean)}"
    case RepoMethod.SqlFile(sqlScript) =>
      val params = sqlScript.params match {
        case Nil      => sc.Code.Empty
        case nonEmpty => nonEmpty.map { param => sc.Param(param.name, param.tpe, None).code }.mkCode(",\n")
      }
      code"def apply($params): ${fs2Stream.of(ConnectionIO, sqlScript.RowName)}"
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

  def cast(c: ComputedColumn): sc.Code =
    c.dbCol.tpe match {
      case db.Type.EnumRef(name)                               => code"::$name"
      case db.Type.DomainRef(name)                             => code"::$name"
      case db.Type.PGmoney                                     => code"::numeric"
      case db.Type.Array(db.Type.PGmoney)                      => code"::numeric[]"
      case db.Type.Boolean | db.Type.Text | db.Type.VarChar(_) => sc.Code.Empty
      case _ =>
        c.dbCol.udtName match {
          case Some(value) => code"::$value"
          case None        => sc.Code.Empty
        }
    }

  override def repoImpl(repoMethod: RepoMethod): sc.Code =
    repoMethod match {
      case RepoMethod.SelectAll(relName, cols, rowType) =>
        val joinedColNames = cols.map(c => maybeQuoted(c.dbName)).mkCode(", ")
        val sql = SQL(code"""select $joinedColNames from $relName""")
        code"""$sql.query[$rowType].stream"""

      case RepoMethod.SelectById(relName, cols, id, rowType) =>
        val joinedColNames = cols.map(c => maybeQuoted(c.dbName)).mkCode(", ")
        val sql = SQL(code"""select $joinedColNames from $relName where ${matchId(id)}""")
        code"""$sql.query[$rowType].option"""

      case RepoMethod.SelectAllByIds(relName, cols, unaryId, idsParam, rowType) =>
        val joinedColNames = cols.map(c => maybeQuoted(c.dbName)).mkCode(", ")

        val sql = SQL(code"""select $joinedColNames from $relName where ${matchAnyId(unaryId, idsParam)}""")
        code"""$sql.query[$rowType].stream"""

      case RepoMethod.SelectByUnique(params, fieldValue, _) =>
        val args = params.map { param => code"$fieldValue.${param.name}(${param.name})" }.mkCode(", ")
        code"""selectByFieldValues(${sc.Type.List}($args)).compile.last"""

      case RepoMethod.SelectByFieldValues(relName, cols, fieldValue, fieldValueOrIdsParam, rowType) =>
        val cases: NonEmptyList[sc.Code] =
          cols.map { col =>
            val fr = frInterpolate(code"${maybeQuoted(col.dbName)} = $$value")
            code"case $fieldValue.${col.name}(value) => $fr"
          }

        val sql = SQL(code"""select * from $relName $$where""")
        code"""val where = $Fragments.whereAnd(
              |  ${fieldValueOrIdsParam.name}.map {
              |    ${cases.mkCode("\n")}
              |  } :_*
              |)
              |$sql.query[$rowType].stream
              |""".stripMargin

      case RepoMethod.UpdateFieldValues(relName, id, varargs, fieldValue, cases0, _) =>
        val cases: NonEmptyList[sc.Code] =
          cases0.map { col =>
            val fr = frInterpolate(code"${maybeQuoted(col.dbName)} = $$value")
            code"case $fieldValue.${col.name}(value) => $fr"
          }

        val sql = SQL {
          code"""|update $relName
                 |$$updates
                 |where ${matchId(id)}""".stripMargin
        }

        code"""${varargs.name} match {
              |  case Nil => $pureCIO(false)
              |  case nonEmpty =>
              |    val updates = $Fragments.set(
              |      nonEmpty.map {
              |        ${cases.mkCode("\n")}
              |      } :_*
              |    )
              |    $sql.update.run.map(_ > 0)
              |}""".stripMargin

      case RepoMethod.Update(relName, _, id, param, colsNotId) =>
        val sql = SQL(
          code"""update $relName
                |set ${colsNotId.map { col => code"${maybeQuoted(col.dbName)} = $${${param.name}.${col.name}}${cast(col)}" }.mkCode(",\n")}
                |where ${matchId(id)}""".stripMargin
        )
        code"""|val ${id.paramName} = ${param.name}.${id.paramName}
               |$sql
               |  .update
               |  .run
               |  .map(_ > 0)"""

      case RepoMethod.InsertUnsaved(relName, cols, unsaved, unsavedParam, default, rowType) =>
        val cases0 = unsaved.restCols.map { col =>
          val colCast = cast(col).render
          val set = frInterpolate(code"$${${unsavedParam.name}.${col.name}}$colCast")
          code"""Some(($Fragment.const(${sc.s(maybeQuoted(col.dbName))}), $set))"""
        }
        val cases1 = unsaved.defaultCols.map { case (col @ ComputedColumn(_, ident, _, _), origType) =>
          val colCast = cast(col).render
          val setValue = frInterpolate(code"$${value: $origType}$colCast")
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
               |q.query[$rowType].unique
               |"""
      case RepoMethod.Upsert(relName, cols, id, unsavedParam, rowType) =>
        val values = cols.map { c =>
          code"$${${unsavedParam.name}.${c.name}}${cast(c)}"
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

        code"$sql.query[$rowType].unique"

      case RepoMethod.Insert(relName, cols, unsavedParam, rowType) =>
        val values = cols.map { c =>
          code"$${${unsavedParam.name}.${c.name}}${cast(c)}"
        }
        val sql = SQL {
          code"""|insert into $relName(${dbNames(cols, isRead = false)})
                 |values (${values.mkCode(", ")})
                 |returning ${dbNames(cols, isRead = true)}
                 |""".stripMargin
        }

        code"$sql.query[$rowType].unique"

      case RepoMethod.Delete(relName, id) =>
        val sql = SQL(code"""delete from $relName where ${matchId(id)}""")
        code"$sql.update.run.map(_ > 0)"

      case RepoMethod.SqlFile(sqlScript) =>
        val renderedScript = sqlScript.sqlFile.decomposedSql.render { (paramAtIndex: Int) =>
          val param = sqlScript.params.find(_.underlying.indices.contains(paramAtIndex)).get
          s"$$${param.name.value}"
        }
        code"""|val sql =
               |  ${SQL(renderedScript)}
               |sql.query[${sqlScript.RowName}].stream
               |""".stripMargin
    }

  override def missingInstances: List[sc.Code] = {
    List(
      code"implicit val UuidType: $Meta[java.util.UUID] = doobie.postgres.implicits.UuidType",
      code"implicit val InetType: $Meta[java.net.InetAddress] = doobie.postgres.implicits.InetType",
      code"implicit val unliftedBooleanArrayType: $Meta[Array[java.lang.Boolean]] = doobie.postgres.implicits.unliftedBooleanArrayType",
      code"implicit val liftedBooleanArrayType: $Meta[Array[Option[java.lang.Boolean]]] = doobie.postgres.implicits.liftedBooleanArrayType",
      code"implicit val unliftedIntegerArrayType: $Meta[Array[java.lang.Integer]] = doobie.postgres.implicits.unliftedIntegerArrayType",
      code"implicit val liftedIntegerArrayType: $Meta[Array[Option[java.lang.Integer]]] = doobie.postgres.implicits.liftedIntegerArrayType",
      code"implicit val unliftedLongArrayType: $Meta[Array[java.lang.Long]] = doobie.postgres.implicits.unliftedLongArrayType",
      code"implicit val liftedLongArrayType: $Meta[Array[Option[java.lang.Long]]] = doobie.postgres.implicits.liftedLongArrayType",
      code"implicit val unliftedFloatArrayType: $Meta[Array[java.lang.Float]] = doobie.postgres.implicits.unliftedFloatArrayType",
      code"implicit val liftedFloatArrayType: $Meta[Array[Option[java.lang.Float]]] = doobie.postgres.implicits.liftedFloatArrayType",
      code"implicit val unliftedDoubleArrayType: $Meta[Array[java.lang.Double]] = doobie.postgres.implicits.unliftedDoubleArrayType",
      code"implicit val liftedDoubleArrayType: $Meta[Array[Option[java.lang.Double]]] = doobie.postgres.implicits.liftedDoubleArrayType",
      code"implicit val unliftedStringArrayType: $Meta[Array[java.lang.String]] = doobie.postgres.implicits.unliftedStringArrayType",
      code"implicit val liftedStringArrayType: $Meta[Array[Option[java.lang.String]]] = doobie.postgres.implicits.liftedStringArrayType",
      code"implicit val unliftedUUIDArrayType: $Meta[Array[java.util.UUID]] = doobie.postgres.implicits.unliftedUUIDArrayType",
      code"implicit val liftedUUIDArrayType: $Meta[Array[Option[java.util.UUID]]] = doobie.postgres.implicits.liftedUUIDArrayType",
      code"implicit val unliftedBigDecimalArrayType: $Meta[Array[java.math.BigDecimal]] = doobie.postgres.implicits.unliftedBigDecimalArrayType",
      code"implicit val iftedBigDecimalArrayType: $Meta[Array[Option[java.math.BigDecimal]]] = doobie.postgres.implicits.iftedBigDecimalArrayType",
      code"implicit val unliftedUnboxedBooleanArrayType: $Meta[Array[scala.Boolean]] = doobie.postgres.implicits.unliftedUnboxedBooleanArrayType",
      code"implicit val liftedUnboxedBooleanArrayType: $Meta[Array[Option[scala.Boolean]]] = doobie.postgres.implicits.liftedUnboxedBooleanArrayType",
      code"implicit val unliftedUnboxedIntegerArrayType: $Meta[Array[scala.Int]] = doobie.postgres.implicits.unliftedUnboxedIntegerArrayType",
      code"implicit val liftedUnboxedIntegerArrayType: $Meta[Array[Option[scala.Int]]] = doobie.postgres.implicits.liftedUnboxedIntegerArrayType",
      code"implicit val unliftedUnboxedLongArrayType: $Meta[Array[scala.Long]] = doobie.postgres.implicits.unliftedUnboxedLongArrayType",
      code"implicit val liftedUnboxedLongArrayType: $Meta[Array[Option[scala.Long]]] = doobie.postgres.implicits.liftedUnboxedLongArrayType",
      code"implicit val unliftedUnboxedFloatArrayType: $Meta[Array[scala.Float]] = doobie.postgres.implicits.unliftedUnboxedFloatArrayType",
      code"implicit val liftedUnboxedFloatArrayType: $Meta[Array[Option[scala.Float]]] = doobie.postgres.implicits.liftedUnboxedFloatArrayType",
      code"implicit val unliftedUnboxedDoubleArrayType: $Meta[Array[scala.Double]] = doobie.postgres.implicits.unliftedUnboxedDoubleArrayType",
      code"implicit val liftedUnboxedDoubleArrayType: $Meta[Array[Option[scala.Double]]] = doobie.postgres.implicits.liftedUnboxedDoubleArrayType",
      code"implicit val bigDecimalMeta: $Meta[Array[BigDecimal]] = doobie.postgres.implicits.bigDecimalMeta",
      code"implicit val optionBigDecimalMeta: $Meta[Array[Option[BigDecimal]]] = doobie.postgres.implicits.optionBigDecimalMeta",
      // time
      code"implicit val JavaTimeOffsetDateTimeMeta: $Meta[java.time.OffsetDateTime] = doobie.postgres.implicits.JavaTimeOffsetDateTimeMeta",
      code"implicit val JavaTimeInstantMeta: $Meta[java.time.Instant] = doobie.postgres.implicits.JavaTimeInstantMeta",
      code"implicit val JavaTimeZonedDateTimeMeta: $Meta[java.time.ZonedDateTime] = doobie.postgres.implicits.JavaTimeZonedDateTimeMeta",
      code"implicit val JavaTimeLocalDateTimeMeta: $Meta[java.time.LocalDateTime] = doobie.postgres.implicits.JavaTimeLocalDateTimeMeta",
      code"implicit val JavaTimeLocalDateMeta: $Meta[java.time.LocalDate] = doobie.postgres.implicits.JavaTimeLocalDateMeta",
      code"implicit val JavaTimeLocalTimeMeta: $Meta[java.time.LocalTime] = doobie.postgres.implicits.JavaTimeLocalTimeMeta"
    )
  }

  def readInstanceFor(tpe: sc.Type, cols: NonEmptyList[ComputedColumn]): sc.Code = {
    val getCols = cols.map { c =>
      c.tpe match {
        case sc.Type.Optional(underlying) => code"(${Get.of(underlying)}, $Nullability.Nullable)"
        case other                        => code"(${Get.of(other)}, $Nullability.NoNulls)"
      }
    }

    val unsafeGetCols = cols.zipWithIndex.map { case (c, idx) =>
      c.tpe match {
        case sc.Type.Optional(underlying) =>
          code"${c.name} = ${Get.of(underlying)}.unsafeGetNullable(rs, i + $idx)"
        case other =>
          code"${c.name} = ${Get.of(other)}.unsafeGetNonNullable(rs, i + $idx)"
      }
    }

    code"""|new ${Read.of(tpe)}(
           |  gets = List(
           |    ${getCols.mkCode(",\n")}
           |  ),
           |  unsafeGet = (rs: ${sc.Type.ResultSet}, i: ${sc.Type.Int}) => $tpe(
           |    ${unsafeGetCols.mkCode(",\n")}
           |  )
           |)
           |""".stripMargin
  }

  def rowInstances(maybeId: Option[IdComputed], tpe: sc.Type, cols: NonEmptyList[ComputedColumn]): List[sc.Code] = {
    val readInstance =
      code"""|implicit val read: ${Read.of(tpe)} =
             |  ${readInstanceFor(tpe, cols)}
             |""".stripMargin

    List(readInstance)
  }

  override def mockRepoImpl(id: IdComputed, repoMethod: RepoMethod, maybeToRow: Option[sc.Param]): sc.Code = {
    repoMethod match {
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
      case RepoMethod.UpdateFieldValues(_, _, varargs, fieldValue, cases0, _) =>
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

      case RepoMethod.Delete(_, id) =>
        code"$delayCIO(map.remove(${id.paramName}).isDefined)"
      case RepoMethod.SqlFile(_) =>
        // should not happen (tm)
        code"???"
    }
  }

  override def customTypeInstances(ct: CustomType): List[sc.Code] = {

    val tpe = ct.typoType

    val v = sc.Ident("v")
    val sqlTypeLit = sc.StrLit(ct.sqlType)
    val single = {
      List(
        code"""|implicit val ${tpe.value.name}Get: ${Get.of(tpe)} =
               |  $Get.Advanced.other[${ct.toTypo.jdbcType}](cats.data.NonEmptyList.one($sqlTypeLit))
               |    .map($v => ${ct.toTypo0(v)})
               |""".stripMargin,
        code"""|implicit val ${tpe.value.name}Put: ${Put.of(tpe)} =
               |  $Put.Advanced.other[${ct.fromTypo.jdbcType}]($NonEmptyList.one($sqlTypeLit))
               |    .contramap($v => ${ct.fromTypo0(v)})
               |""".stripMargin
      )
    }
    val array = {
      val fromTypo = ct.fromTypoInArray.getOrElse(ct.fromTypo)
      val toTypo = ct.toTypoInArray.getOrElse(ct.toTypo)
      val sqlArrayTypeLit = sc.StrLit("_" + ct.sqlType)
      List(
        code"""|implicit val ${tpe.value.name}GetArray: ${Get.of(sc.Type.Array.of(tpe))} =
               |  $Get.Advanced.array[${sc.Type.AnyRef}]($NonEmptyList.one($sqlArrayTypeLit))
               |    .map(_.map($v => ${toTypo.toTypo(code"$v.asInstanceOf[${toTypo.jdbcType}]", ct.typoType)}))
               |""".stripMargin,
        code"""|implicit val ${tpe.value.name}PutArray: ${Put.of(sc.Type.Array.of(tpe))} =
               |  $Put.Advanced.array[${sc.Type.AnyRef}]($NonEmptyList.one($sqlArrayTypeLit), $sqlTypeLit)
               |    .contramap(_.map($v => ${fromTypo.fromTypo0(v)}))
               |""".stripMargin
      )
    }

    single ++ array
  }
}

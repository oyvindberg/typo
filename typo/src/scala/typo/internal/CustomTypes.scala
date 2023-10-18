package typo
package internal

import typo.internal.codegen.*

import scala.collection.mutable

class CustomTypes(pkg: sc.QIdent) {

  lazy val TypoBox = CustomType(
    comment = "This represents the box datatype in PostgreSQL",
    sqlType = "box",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoBox")),
    params = NonEmptyList(
      sc.Param(sc.Ident("x1"), sc.Type.Double, None),
      sc.Param(sc.Ident("y1"), sc.Type.Double, None),
      sc.Param(sc.Ident("x2"), sc.Type.Double, None),
      sc.Param(sc.Ident("y2"), sc.Type.Double, None)
    ),
    isNull = p => code"$p.isNull",
    toTypo = CustomType.ToTypo(
      jdbcType = sc.Type.PGbox,
      toTypo = (expr, target) => code"$target($expr.point(0).x, $expr.point(0).y, $expr.point(1).x, $expr.point(1).y)"
    ),
    fromTypo = CustomType.FromTypo(
      jdbcType = sc.Type.PGbox,
      fromTypo = (expr, target) => code"new $target($expr.x1, $expr.y1, $expr.x2, $expr.y2)"
    )
  )

  lazy val TypoBytea = CustomType(
    comment = "This represents the bytea datatype in PostgreSQL",
    sqlType = "bytea",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoBytea")),
    params = NonEmptyList(
      sc.Param(sc.Ident("value"), sc.Type.Array.of(sc.Type.Byte), None)
    ),
    isNull = p => code"$p != null",
    toTypo = CustomType.ToTypo(
      jdbcType = sc.Type.Array.of(sc.Type.Byte),
      toTypo = (expr, target) => code"$target($expr)"
    ),
    fromTypo = CustomType.FromTypo(
      jdbcType = sc.Type.Array.of(sc.Type.Byte),
      fromTypo = (expr, _) => code"$expr.value"
    )
  )

  lazy val TypoLocalDate = CustomType(
    comment = "This is `java.time.LocalDate`, but transferred to and from postgres as strings. The reason is that postgres driver and db libs are broken",
    sqlType = "date",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoLocalDate")),
    params = NonEmptyList(
      sc.Param(sc.Ident("value"), sc.Type.LocalDate, None)
    ),
    isNull = p => code"$p != null",
    toTypo = CustomType.ToTypo(
      jdbcType = sc.Type.String,
      toTypo = (expr, target) => code"$target(${sc.Type.LocalDate}.parse($expr))"
    ),
    fromTypo = CustomType.FromTypo(
      jdbcType = sc.Type.String,
      fromTypo = (expr, _) => code"$expr.value.toString"
    ),
    objBody = Some { target =>
      code"""|def now = $target(${sc.Type.LocalDate}.now)
             |def apply(str: ${sc.Type.String}): $target = $target(${sc.Type.LocalDate}.parse(str))""".stripMargin
    }
  )
  lazy val TypoLocalTime = CustomType(
    comment = "This is `java.time.LocalTime`, but with microsecond precision and transferred to and from postgres as strings. The reason is that postgres driver and db libs are broken",
    sqlType = "time",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoLocalTime")),
    params = NonEmptyList(
      sc.Param(sc.Ident("value"), sc.Type.LocalTime, None)
    ),
    isNull = p => code"$p != null",
    toTypo = CustomType.ToTypo(
      jdbcType = sc.Type.String,
      toTypo = (expr, target) => code"$target(${sc.Type.LocalTime}.parse($expr))"
    ),
    fromTypo = CustomType.FromTypo(
      jdbcType = sc.Type.String,
      fromTypo = (expr, _) => code"$expr.value.toString"
    ),
    objBody = Some { target =>
      code"""|def apply(value: ${sc.Type.LocalTime}): $target = new $target(value.truncatedTo(${sc.Type.ChronoUnit}.MICROS))
             |def apply(str: ${sc.Type.String}): $target = apply(${sc.Type.LocalTime}.parse(str))
             |def now: $target = $target(${sc.Type.LocalTime}.now)""".stripMargin
    }
  )

  lazy val TypoLocalDateTime = CustomType(
    comment = "This is `java.time.LocalDateTime`, but with microsecond precision and transferred to and from postgres as strings. The reason is that postgres driver and db libs are broken",
    sqlType = "timestamp",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoLocalDateTime")),
    params = NonEmptyList(
      sc.Param(sc.Ident("value"), sc.Type.LocalDateTime, None)
    ),
    isNull = p => code"$p != null",
    toTypo = CustomType.ToTypo(
      jdbcType = sc.Type.String,
      toTypo = (expr, target) => code"$target(${sc.Type.LocalDateTime}.parse($expr, parser))"
    ),
    fromTypo = CustomType.FromTypo(
      jdbcType = sc.Type.String,
      fromTypo = (expr, _) => code"$expr.value.toString"
    ),
    objBody = Some { target =>
      code"""|val parser: ${sc.Type.DateTimeFormatter} = new ${sc.Type.DateTimeFormatterBuilder}().appendPattern("yyyy-MM-dd HH:mm:ss").appendFraction(${sc.Type.ChronoField}.MICRO_OF_SECOND, 0, 6, true).toFormatter
             |def apply(value: ${sc.Type.LocalDateTime}): $target = new $target(value.truncatedTo(${sc.Type.ChronoUnit}.MICROS))
             |def apply(str: ${sc.Type.String}): $target = apply(${sc.Type.LocalDateTime}.parse(str, parser))
             |def now = $target(${sc.Type.LocalDateTime}.now)
             |""".stripMargin
    }
  )

  lazy val TypoInstant = CustomType(
    comment = "This is `java.time.TypoInstant`, but with microsecond precision and transferred to and from postgres as strings. The reason is that postgres driver and db libs are broken",
    sqlType = "timestamptz",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoInstant")),
    params = NonEmptyList(
      sc.Param(sc.Ident("value"), sc.Type.Instant, None)
    ),
    isNull = p => code"$p != null",
    toTypo = CustomType.ToTypo(
      jdbcType = sc.Type.String,
      toTypo = (expr, target) => code"$target($expr)"
    ),
    fromTypo = CustomType.FromTypo(
      jdbcType = sc.Type.String,
      fromTypo = (expr, _) => code"$expr.value.toString"
    ),
    objBody = Some { target =>
      code"""|val parser: ${sc.Type.DateTimeFormatter} = new ${sc.Type.DateTimeFormatterBuilder}().appendPattern("yyyy-MM-dd HH:mm:ss").appendFraction(${sc.Type.ChronoField}.MICRO_OF_SECOND, 0, 6, true).appendPattern("X").toFormatter
             |def apply(value: ${sc.Type.Instant}): $target = new $target(value.truncatedTo(${sc.Type.ChronoUnit}.MICROS))
             |def apply(str: ${sc.Type.String}): $target = apply(${sc.Type.OffsetDateTime}.parse(str, parser).toInstant)
             |def now = $target(${sc.Type.Instant}.now)
             |""".stripMargin
    }
  )

  lazy val TypoOffsetTime = CustomType(
    comment = "This is `java.time.OffsetTime`, but with microsecond precision and transferred to and from postgres as strings. The reason is that postgres driver and db libs are broken",
    sqlType = "timetz",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoOffsetTime")),
    params = NonEmptyList(
      sc.Param(sc.Ident("value"), sc.Type.OffsetTime, None)
    ),
    isNull = p => code"$p != null",
    toTypo = CustomType.ToTypo(
      jdbcType = sc.Type.String,
      toTypo = (expr, target) => code"$target(${sc.Type.OffsetTime}.parse($expr, parser))"
    ),
    fromTypo = CustomType.FromTypo(
      jdbcType = sc.Type.String,
      fromTypo = (expr, _) => code"$expr.value.toString"
    ),
    objBody = Some { target =>
      code"""|val parser: ${sc.Type.DateTimeFormatter} = new ${sc.Type.DateTimeFormatterBuilder}().appendPattern("HH:mm:ss").appendFraction(${sc.Type.ChronoField}.MICRO_OF_SECOND, 0, 6, true).appendPattern("X").toFormatter
             |def apply(value: ${sc.Type.OffsetTime}): $target = new $target(value.truncatedTo(${sc.Type.ChronoUnit}.MICROS))
             |def apply(str: ${sc.Type.String}): $target = apply(${sc.Type.OffsetTime}.parse(str, parser))
             |def now = $target(${sc.Type.OffsetTime}.now)
             |""".stripMargin
    }
  )

  lazy val TypoCircle = CustomType(
    comment = "This represents circle datatype in PostgreSQL, consisting of a point and a radius",
    sqlType = "circle",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoCircle")),
    params = NonEmptyList(
      sc.Param(sc.Ident("center"), TypoPoint.typoType, None),
      sc.Param(sc.Ident("radius"), sc.Type.Double, None)
    ),
    isNull = p => code"$p.center == null",
    toTypo = CustomType.ToTypo(
      jdbcType = sc.Type.PGcircle,
      toTypo = (expr, target) => code"$target(TypoPoint($expr.center.x, $expr.center.y), $expr.radius)"
    ),
    fromTypo = CustomType.FromTypo(
      jdbcType = sc.Type.PGcircle,
      fromTypo = (expr, target) => code"new $target($expr.center.x, $expr.center.y, $expr.radius)"
    )
  )

  lazy val TypoLine = CustomType(
    comment = "This implements a line represented by the linear equation Ax + By + C = 0",
    sqlType = "line",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoLine")),
    params = NonEmptyList(
      sc.Param(sc.Ident("a"), sc.Type.Double, None),
      sc.Param(sc.Ident("b"), sc.Type.Double, None),
      sc.Param(sc.Ident("c"), sc.Type.Double, None)
    ),
    isNull = p => code"$p.isNull",
    toTypo = CustomType.ToTypo(
      jdbcType = sc.Type.PGline,
      toTypo = (expr, target) => code"$target($expr.a, $expr.b, $expr.c)"
    ),
    fromTypo = CustomType.FromTypo(
      jdbcType = sc.Type.PGline,
      fromTypo = (expr, target) => code"new $target($expr.a, $expr.b, $expr.c)"
    )
  )

  lazy val TypoLineSegment = CustomType(
    comment = "This implements a line represented by the linear equation Ax + By + C = 0",
    sqlType = "lseg",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoLineSegment")),
    params = NonEmptyList(
      sc.Param(sc.Ident("p1"), TypoPoint.typoType, None),
      sc.Param(sc.Ident("p2"), TypoPoint.typoType, None)
    ),
    isNull = p => code"$p.point == null",
    toTypo = CustomType.ToTypo(
      jdbcType = sc.Type.PGlseg,
      toTypo = (expr, target) => code"$target(${TypoPoint.toTypo0(code"$expr.point(0)")}, ${TypoPoint.toTypo0(code"$expr.point(1)")})"
    ),
    fromTypo = CustomType.FromTypo(
      jdbcType = sc.Type.PGlseg,
      fromTypo = (expr, target) => code"new $target(${TypoPoint.fromTypo0(code"$expr.p1")}, ${TypoPoint.fromTypo0(code"$expr.p2")})"
    )
  )

  lazy val TypoPath = CustomType(
    comment = "This implements a path (a multiple segmented line, which may be closed)",
    sqlType = "path",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoPath")),
    params = NonEmptyList(
      sc.Param(sc.Ident("open"), sc.Type.Boolean, None),
      sc.Param(sc.Ident("points"), sc.Type.List.of(TypoPoint.typoType), None)
    ),
    isNull = p => code"$p.points == null",
    toTypo = CustomType.ToTypo(
      jdbcType = sc.Type.PGpath,
      toTypo = (expr, target) => code"$target($expr.isOpen, $expr.points.map(p => ${TypoPoint.typoType}(p.x, p.y)).toList)"
    ),
    fromTypo = CustomType.FromTypo(
      jdbcType = sc.Type.PGpath,
      fromTypo = (expr, target) => code"new $target($expr.points.map(p => new ${sc.Type.PGpoint}(p.x, p.y)).toArray, $expr.open)"
    )
  )

  lazy val TypoPoint = CustomType(
    comment = "Point datatype in PostgreSQL",
    sqlType = "point",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoPoint")),
    params = NonEmptyList(
      sc.Param(sc.Ident("x"), sc.Type.Double, None),
      sc.Param(sc.Ident("y"), sc.Type.Double, None)
    ),
    isNull = p => code"$p.isNull",
    toTypo = CustomType.ToTypo(
      jdbcType = sc.Type.PGpoint,
      toTypo = (expr, target) => code"$target($expr.x, $expr.y)"
    ),
    fromTypo = CustomType.FromTypo(
      jdbcType = sc.Type.PGpoint,
      fromTypo = (expr, target) => code"new $target($expr.x, $expr.y)"
    )
  )

  lazy val TypoPolygon = CustomType(
    comment = "Polygon datatype in PostgreSQL",
    sqlType = "polygon",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoPolygon")),
    params = NonEmptyList(
      sc.Param(sc.Ident("points"), sc.Type.List.of(TypoPoint.typoType), None)
    ),
    isNull = p => code"$p.points == null",
    toTypo = CustomType.ToTypo(
      jdbcType = sc.Type.PGpolygon,
      toTypo = (expr, target) => code"$target($expr.points.map(p => ${TypoPoint.typoType}(p.x, p.y)).toList)"
    ),
    fromTypo = CustomType.FromTypo(
      jdbcType = sc.Type.PGpolygon,
      fromTypo = (expr, target) => code"new $target($expr.points.map(p => new ${sc.Type.PGpoint}(p.x, p.y)).toArray)"
    )
  )

  lazy val TypoInterval = CustomType(
    comment = "Interval type in PostgreSQL",
    sqlType = "interval",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoInterval")),
    params = NonEmptyList(
      sc.Param(sc.Ident("years"), sc.Type.Int, None),
      sc.Param(sc.Ident("months"), sc.Type.Int, None),
      sc.Param(sc.Ident("days"), sc.Type.Int, None),
      sc.Param(sc.Ident("hours"), sc.Type.Int, None),
      sc.Param(sc.Ident("minutes"), sc.Type.Int, None),
      sc.Param(sc.Ident("seconds"), sc.Type.Double, None)
    ),
    isNull = p => code"$p.isNull",
    toTypo = CustomType.ToTypo(
      jdbcType = sc.Type.PGInterval,
      toTypo = (expr, target) => code"$target($expr.getYears, $expr.getMonths, $expr.getDays, $expr.getHours, $expr.getMinutes, $expr.getSeconds)"
    ),
    fromTypo = CustomType.FromTypo(
      jdbcType = sc.Type.PGInterval,
      fromTypo = (expr, target) => code"new $target($expr.years, $expr.months, $expr.days, $expr.hours, $expr.minutes, $expr.seconds)"
    )
  )

  lazy val TypoHStore = CustomType(
    comment = "The text representation of an hstore, used for input and output, includes zero or more key => value pairs separated by commas",
    sqlType = "hstore",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoHStore")),
    params = NonEmptyList(
      sc.Param(sc.Ident("value"), sc.Type.Map.of(sc.Type.String, sc.Type.String), None)
    ),
    isNull = p => code"$p == null",
    toTypo = CustomType.ToTypo(
      jdbcType = sc.Type.JavaMap.of(sc.Type.Wildcard, sc.Type.Wildcard),
      toTypo = (expr, target) => {
        code"""|{
               |  val b = ${sc.Type.Map}.newBuilder[${sc.Type.String}, ${sc.Type.String}]
               |  $expr.forEach { case (k, v) => b += k.asInstanceOf[${sc.Type.String}] -> v.asInstanceOf[${sc.Type.String}]}
               |  $target(b.result())
               |}""".stripMargin
      }
    ),
    fromTypo = CustomType.FromTypo(
      jdbcType = sc.Type.JavaMap.of(sc.Type.String, sc.Type.String),
      fromTypo = (expr, _) => {
        code"""|{
               |  val b = new ${sc.Type.JavaHashMap}[${sc.Type.String}, ${sc.Type.String}]
               |  $expr.value.foreach { case (k, v) => b.put(k, v)}
               |  b
               |}""".stripMargin
      }
    )
  )

  lazy val TypoMoney = CustomType(
    comment = "Money and cash types in PostgreSQL",
    sqlType = "money",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoMoney")),
    params = NonEmptyList(
      sc.Param(sc.Ident("value"), sc.Type.BigDecimal, None)
    ),
    isNull = p => code"$p.isNull",
    toTypo = CustomType.ToTypo(
      jdbcType = sc.Type.JavaBigDecimal,
      toTypo = (expr, target) => code"$target(${sc.Type.BigDecimal}($expr))"
    ),
    fromTypo = CustomType.FromTypo(
      jdbcType = sc.Type.JavaBigDecimal,
      fromTypo = (expr, _) => code"$expr.value.bigDecimal"
    )
  )

  lazy val TypoShort = CustomType(
    comment = "Short primitive",
    sqlType = "int2",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoShort")),
    params = NonEmptyList(
      sc.Param(sc.Ident("value"), sc.Type.Short, None)
    ),
    isNull = p => code"$p != null",
    toTypo = CustomType.ToTypo(
      jdbcType = sc.Type.JavaInteger,
      toTypo = (expr, target) => code"$target($expr.toShort)"
    ),
    fromTypo = CustomType.FromTypo(
      jdbcType = sc.Type.JavaInteger,
      fromTypo = (expr, _) => code"$expr.value.toInt"
    ),
    toTypoInArray = Some(
      CustomType.ToTypo(
        jdbcType = sc.Type.JavaShort,
        toTypo = (expr, target) => code"$target($expr)"
      )
    ),
    fromTypoInArray = Some(
      CustomType.FromTypo(
        jdbcType = sc.Type.JavaShort,
        fromTypo = (expr, _) => code"$expr.value: ${sc.Type.JavaShort}"
      )
    ),
    objBody = Some(target => {
      val numericOfTarget = sc.Type.Numeric.of(target)
      code"""|implicit object numeric extends $numericOfTarget {
               |    override def compare(x: $target, y: $target): ${sc.Type.Int} = ${sc.Type.JavaShort}.compare(x.value, y.value)
               |    override def plus(x: $target, y: $target): $target = $target((x.value + y.value).toShort)
               |    override def minus(x: $target, y: $target): $target = $target((x.value - y.value).toShort)
               |    override def times(x: $target, y: $target): $target = $target((x.value * y.value).toShort)
               |    override def negate(x: $target): $target = $target((-x.value).toShort)
               |    override def fromInt(x: Int): $target = $target(x.toShort)
               |    override def toInt(x: $target): ${sc.Type.Int} = x.toInt
               |    override def toLong(x: $target): ${sc.Type.Long} = x.toLong
               |    override def toFloat(x: $target): ${sc.Type.Float} = x.toFloat
               |    override def toDouble(x: $target): ${sc.Type.Double} = x.toDouble
               |    def parseString(str: String): Option[$target] = (str, Option.empty[$target])._2 // sorry mac, this was too much trouble to implement for 2.12
               |  }
               |""".stripMargin
    })
  )

  lazy val TypoUUID = CustomType(
    comment = "UUID",
    sqlType = "uuid",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoUUID")),
    params = NonEmptyList(
      sc.Param(sc.Ident("value"), sc.Type.UUID, None)
    ),
    isNull = p => code"$p.getString == null",
    toTypo = CustomType.ToTypo(
      jdbcType = sc.Type.UUID,
      toTypo = (expr, target) => code"$target($expr)"
    ),
    fromTypo = CustomType.FromTypo(
      jdbcType = sc.Type.UUID,
      fromTypo = (expr, _) => code"$expr.value"
    ),
    objBody = Some(target => code"""|def apply(str: ${sc.Type.String}): $target = $target(${sc.Type.UUID}.fromString(str))
             |def randomUUID: $target = $target(${sc.Type.UUID}.randomUUID())""".stripMargin)
  )

  lazy val TypoXml = CustomType(
    comment = "XML",
    sqlType = "xml",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoXml")),
    params = NonEmptyList(
      sc.Param(sc.Ident("value"), sc.Type.String, None)
    ),
    isNull = p => code"$p.getString == null",
    toTypo = CustomType.ToTypo(
      jdbcType = sc.Type.PgSQLXML,
      toTypo = (expr, target) => code"$target($expr.getString)"
    ),
    fromTypo = CustomType.FromTypo(
      jdbcType = sc.Type.String,
      fromTypo = (expr, _) => code"$expr.value"
    ),
    fromTypoInArray = Some(
      CustomType.FromTypo(
        jdbcType = sc.Type.PGobject,
        fromTypo = (expr, target) => {
          code"""|{
                 |  val obj = new $target
                 |  obj.setType("xml")
                 |  obj.setValue($expr.value)
                 |  obj
                 |}""".stripMargin
        }
      )
    ),
    toTypoInArray = Some(
      CustomType.ToTypo(
        jdbcType = sc.Type.PGobject,
        toTypo = (expr, target) => code"$target($expr.getValue)"
      )
    )
  )

  def obj(sqlType: String, name: String): CustomType =
    CustomType(
      comment = s"$sqlType (via PGObject)",
      sqlType = sqlType,
      typoType = sc.Type.Qualified(pkg / sc.Ident(name)),
      params = NonEmptyList(
        sc.Param(sc.Ident("value"), sc.Type.String, None)
      ),
      isNull = p => code"$p.getValue == null",
      toTypo = CustomType.ToTypo(
        jdbcType = sc.Type.PGobject,
        toTypo = (expr, target) => code"$target($expr.getValue)"
      ),
      fromTypo = CustomType.FromTypo(
        jdbcType = sc.Type.PGobject,
        fromTypo = (expr, target) => code"""|{
                 |  val obj = new $target
                 |  obj.setType(${sc.StrLit(sqlType)})
                 |  obj.setValue($expr.value)
                 |  obj
                 |}""".stripMargin
      ),
      toTypoInArray = Some(
        CustomType.ToTypo(
          jdbcType = sc.Type.String,
          toTypo = (expr, target) => code"$target($expr)"
        )
      )
    )
  lazy val TypoJson = obj("json", "TypoJson")
  lazy val TypoJsonb = obj("jsonb", "TypoJsonb")
  lazy val TypoInet = obj("inet", "TypoInet").copy(toTypoInArray = None)
  lazy val TypoAclItem = obj("aclitem", "TypoAclItem").copy(toTypoInArray = None)
  lazy val TypoAnyArray = obj("anyarray", "TypoAnyArray")
  lazy val TypoInt2Vector = obj("int2vector", "TypoInt2Vector")
  lazy val TypoOidVector = obj("oidvector", "TypoOidVector")
  lazy val TypoPgNodeTree = obj("pg_node_tree", "TypoPgNodeTree")
  lazy val TypoRecord = obj("record", "TypoRecord").copy(toTypoInArray = None)
  lazy val TypoRegclass = obj("regclass", "TypoRegclass")
  lazy val TypoRegconfig = obj("regconfig", "TypoRegconfig")
  lazy val TypoRegdictionary = obj("regdictionary", "TypoRegdictionary")
  lazy val TypoRegnamespace = obj("regnamespace", "TypoRegnamespace")
  lazy val TypoRegoper = obj("regoper", "TypoRegoper")
  lazy val TypoRegoperator = obj("regoperator", "TypoRegoperator")
  lazy val TypoRegproc = obj("regproc", "TypoRegproc")
  lazy val TypoRegprocedure = obj("regprocedure", "TypoRegprocedure")
  lazy val TypoRegrole = obj("regrole", "TypoRegrole")
  lazy val TypoRegtype = obj("regtype", "TypoRegtype")
  lazy val TypoXid = obj("xid", "TypoXid")

  val All: mutable.Map[sc.Type, CustomType] =
    mutable.Map(
      List(
        TypoAclItem,
        TypoAnyArray,
        TypoBox,
        TypoBytea,
        TypoCircle,
        TypoHStore,
        TypoInet,
        TypoInet,
        TypoInt2Vector,
        TypoInterval,
        TypoJson,
        TypoJson,
        TypoJsonb,
        TypoJsonb,
        TypoLine,
        TypoLineSegment,
        TypoLocalDate,
        TypoLocalDateTime,
        TypoLocalTime,
        TypoMoney,
        TypoInstant,
        TypoOffsetTime,
        TypoOidVector,
        TypoPath,
        TypoPgNodeTree,
        TypoPoint,
        TypoPolygon,
        TypoRecord,
        TypoRegclass,
        TypoRegconfig,
        TypoRegdictionary,
        TypoRegnamespace,
        TypoRegoper,
        TypoRegoperator,
        TypoRegproc,
        TypoRegprocedure,
        TypoRegrole,
        TypoRegtype,
        TypoShort,
        TypoUUID,
        TypoXid,
        TypoXml
      ).map(ct => (ct.typoType, ct))*
    )

  def TypoUnknown(sqlType: String): CustomType = {
    val ct = CustomType(
      comment = "This is a type typo does not know how to handle yet. This falls back to casting to string and crossing fingers. Time to file an issue! :]",
      sqlType = sqlType,
      typoType = sc.Type.Qualified(pkg / sc.Ident(s"TypoUnknown${Naming.titleCase(sqlType)}")),
      params = NonEmptyList(sc.Param(sc.Ident("value"), sc.Type.String, None)),
      isNull = p => code"$p == null",
      toTypo = CustomType.ToTypo(
        jdbcType = sc.Type.String,
        toTypo = (expr, target) => code"$target($expr)"
      ),
      fromTypo = CustomType.FromTypo(
        jdbcType = sc.Type.String,
        fromTypo = (expr, _) => code"$expr.value"
      )
    )
    All(ct.typoType) = ct
    ct
  }
}

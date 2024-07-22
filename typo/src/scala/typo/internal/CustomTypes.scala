package typo
package internal

import typo.internal.codegen.*

import scala.collection.mutable

class CustomTypes(pkg: sc.QIdent, lang: Lang) {

  lazy val TypoBox = CustomType(
    comment = "This represents the box datatype in PostgreSQL",
    sqlType = "box",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoBox")),
    params = NonEmptyList(
      sc.Param(sc.Ident("x1"), lang.Double),
      sc.Param(sc.Ident("y1"), lang.Double),
      sc.Param(sc.Ident("x2"), lang.Double),
      sc.Param(sc.Ident("y2"), lang.Double)
    ),
    toTypo = CustomType.ToTypo(
      jdbcType = TypesJava.PGbox,
      toTypo = (expr, target) => code"$target($expr.point(0).x, $expr.point(0).y, $expr.point(1).x, $expr.point(1).y)"
    ),
    fromTypo = CustomType.FromTypo(
      jdbcType = TypesJava.PGbox,
      fromTypo = (expr, target) => code"new $target($expr.x1, $expr.y1, $expr.x2, $expr.y2)"
    ),
    toText = CustomType.Text.string(expr => sc.s(code"(($${$expr.x1},$${$expr.y1}),($${$expr.x2},$${$expr.y2}))"))
  )

  lazy val TypoBytea = CustomType(
    comment = "This represents the bytea datatype in PostgreSQL",
    sqlType = "bytea",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoBytea")),
    params = NonEmptyList(
      sc.Param(sc.Ident("value"), sc.Type.ArrayOf(lang.Byte))
    ),
    toTypo = CustomType.ToTypo(
      jdbcType = sc.Type.ArrayOf(lang.Byte),
      toTypo = (expr, target) => code"$target($expr)"
    ),
    fromTypo = CustomType.FromTypo(
      jdbcType = sc.Type.ArrayOf(lang.Byte),
      fromTypo = (expr, _) => code"$expr.value"
    ),
    forbidArray = true,
    toText = CustomType.Text(sc.Type.ArrayOf(lang.Byte), expr => code"$expr.value")
  )

  lazy val TypoLocalDate = CustomType(
    comment = "This is `java.time.LocalDate`, but transferred to and from postgres as strings. The reason is that postgres driver and db libs are broken",
    sqlType = "date",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoLocalDate")),
    params = NonEmptyList(
      sc.Param(sc.Ident("value"), TypesJava.LocalDate)
    ),
    toTypo = CustomType.ToTypo(
      jdbcType = TypesJava.String,
      toTypo = (expr, target) => code"new $target(${TypesJava.LocalDate}.parse($expr))"
    ),
    fromTypo = CustomType.FromTypo(
      jdbcType = TypesJava.String,
      fromTypo = (expr, _) => code"$expr.value.toString()"
    ),
    toText = CustomType.Text.string(expr => code"$expr.value.toString()"),
    objBody = target =>
      List(
        sc.Method(
          comments = sc.Comments.Empty,
          tparams = Nil,
          name = sc.Ident("now"),
          params = Nil,
          implicitParams = Nil,
          tpe = target,
          body = Some(code"new $target(${TypesJava.LocalDate}.now())")
        ),
        sc.Method(
          comments = sc.Comments.Empty,
          tparams = Nil,
          name = sc.Ident("apply"),
          params = List(sc.Param(sc.Ident("str"), TypesJava.String)),
          implicitParams = Nil,
          tpe = target,
          body = Some(code"new $target(${TypesJava.LocalDate}.parse(str))")
        )
      )
  )
  lazy val TypoLocalTime = CustomType(
    comment = "This is `java.time.LocalTime`, but with microsecond precision and transferred to and from postgres as strings. The reason is that postgres driver and db libs are broken",
    sqlType = "time",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoLocalTime")),
    params = NonEmptyList(
      sc.Param(sc.Ident("value"), TypesJava.LocalTime)
    ),
    toTypo = CustomType.ToTypo(
      jdbcType = TypesJava.String,
      toTypo = (expr, target) => code"new $target(${TypesJava.LocalTime}.parse($expr))"
    ),
    fromTypo = CustomType.FromTypo(
      jdbcType = TypesJava.String,
      fromTypo = (expr, _) => code"$expr.value.toString"
    ),
    toText = CustomType.Text.string(expr => code"$expr.value.toString"),
    objBody = target =>
      List(
        sc.Method(
          comments = sc.Comments.Empty,
          tparams = Nil,
          name = sc.Ident("apply"),
          params = List(sc.Param(sc.Ident("value"), TypesJava.LocalTime)),
          implicitParams = Nil,
          tpe = target,
          body = Some(code"new $target(value.truncatedTo(${TypesJava.ChronoUnit}.MICROS))")
        ),
        sc.Method(
          comments = sc.Comments.Empty,
          tparams = Nil,
          name = sc.Ident("apply"),
          params = List(sc.Param(sc.Ident("str"), TypesJava.String)),
          implicitParams = Nil,
          tpe = target,
          body = Some(code"apply(${TypesJava.LocalTime}.parse(str))")
        ),
        sc.Method(
          comments = sc.Comments.Empty,
          tparams = Nil,
          name = sc.Ident("now"),
          params = Nil,
          implicitParams = Nil,
          tpe = target,
          body = Some(code"new $target(${TypesJava.LocalTime}.now())")
        )
      )
  )

  lazy val TypoLocalDateTime = CustomType(
    comment = "This is `java.time.LocalDateTime`, but with microsecond precision and transferred to and from postgres as strings. The reason is that postgres driver and db libs are broken",
    sqlType = "timestamp",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoLocalDateTime")),
    params = NonEmptyList(
      sc.Param(sc.Ident("value"), TypesJava.LocalDateTime)
    ),
    toTypo = CustomType.ToTypo(
      jdbcType = TypesJava.String,
      toTypo = (expr, target) => code"$target(${TypesJava.LocalDateTime}.parse($expr, parser))"
    ),
    fromTypo = CustomType.FromTypo(
      jdbcType = TypesJava.String,
      fromTypo = (expr, _) => code"$expr.value.toString"
    ),
    toText = CustomType.Text.string(expr => code"$expr.value.toString"),
    objBody = target =>
      List(
        sc.Value(
          name = sc.Ident("parser"),
          tpe = TypesJava.DateTimeFormatter,
          body = Some(code"""new ${TypesJava.DateTimeFormatterBuilder}().appendPattern("yyyy-MM-dd HH:mm:ss").appendFraction(${TypesJava.ChronoField}.MICRO_OF_SECOND, 0, 6, true).toFormatter()""")
        ),
        sc.Method(
          comments = sc.Comments.Empty,
          tparams = Nil,
          name = sc.Ident("apply"),
          params = List(sc.Param(sc.Ident("value"), TypesJava.LocalDateTime)),
          implicitParams = Nil,
          tpe = target,
          body = Some(code"new $target(value.truncatedTo(${TypesJava.ChronoUnit}.MICROS))")
        ),
        sc.Method(
          comments = sc.Comments.Empty,
          tparams = Nil,
          name = sc.Ident("apply"),
          params = List(sc.Param(sc.Ident("str"), TypesJava.String)),
          implicitParams = Nil,
          tpe = target,
          body = Some(code"apply(${TypesJava.LocalDateTime}.parse(str, parser))")
        ),
        sc.Method(
          comments = sc.Comments.Empty,
          tparams = Nil,
          name = sc.Ident("now"),
          params = Nil,
          implicitParams = Nil,
          tpe = target,
          body = Some(code"new $target(${TypesJava.LocalDateTime}.now())")
        )
      )
  )

  lazy val TypoInstant = CustomType(
    comment = "This is `java.time.TypoInstant`, but with microsecond precision and transferred to and from postgres as strings. The reason is that postgres driver and db libs are broken",
    sqlType = "timestamptz",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoInstant")),
    params = NonEmptyList(
      sc.Param(sc.Ident("value"), TypesJava.Instant)
    ),
    toTypo = CustomType.ToTypo(
      jdbcType = TypesJava.String,
      toTypo = (expr, target) => code"$target($expr)"
    ),
    fromTypo = CustomType.FromTypo(
      jdbcType = TypesJava.String,
      fromTypo = (expr, _) => code"$expr.value.toString"
    ),
    toText = CustomType.Text.string(expr => code"$expr.value.toString"),
    objBody = target =>
      List(
        sc.Value(
          name = sc.Ident("parser"),
          tpe = TypesJava.DateTimeFormatter,
          body = Some(
            code"""new ${TypesJava.DateTimeFormatterBuilder}().appendPattern("yyyy-MM-dd HH:mm:ss").appendFraction(${TypesJava.ChronoField}.MICRO_OF_SECOND, 0, 6, true).appendPattern("X").toFormatter()"""
          )
        ),
        sc.Method(
          comments = sc.Comments.Empty,
          tparams = Nil,
          name = sc.Ident("apply"),
          params = List(sc.Param(sc.Ident("value"), TypesJava.Instant)),
          implicitParams = Nil,
          tpe = target,
          body = Some(code"new $target(value.truncatedTo(${TypesJava.ChronoUnit}.MICROS))")
        ),
        sc.Method(
          comments = sc.Comments.Empty,
          tparams = Nil,
          name = sc.Ident("apply"),
          params = List(sc.Param(sc.Ident("str"), TypesJava.String)),
          implicitParams = Nil,
          tpe = target,
          body = Some(code"apply(${TypesJava.OffsetDateTime}.parse(str, parser).toInstant())")
        ),
        sc.Method(
          comments = sc.Comments.Empty,
          tparams = Nil,
          name = sc.Ident("now"),
          params = Nil,
          implicitParams = Nil,
          tpe = target,
          body = Some(code"new $target(${TypesJava.Instant}.now())")
        )
      )
  )

  lazy val TypoOffsetTime = CustomType(
    comment = "This is `java.time.OffsetTime`, but with microsecond precision and transferred to and from postgres as strings. The reason is that postgres driver and db libs are broken",
    sqlType = "timetz",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoOffsetTime")),
    params = NonEmptyList(
      sc.Param(sc.Ident("value"), TypesJava.OffsetTime)
    ),
    toTypo = CustomType.ToTypo(
      jdbcType = TypesJava.String,
      toTypo = (expr, target) => code"$target(${TypesJava.OffsetTime}.parse($expr, parser))"
    ),
    fromTypo = CustomType.FromTypo(
      jdbcType = TypesJava.String,
      fromTypo = (expr, _) => code"$expr.value.toString"
    ),
    toText = CustomType.Text.string(expr => code"$expr.value.toString"),
    objBody = target =>
      List(
        sc.Value(
          name = sc.Ident("parser"),
          tpe = TypesJava.DateTimeFormatter,
          body =
            Some(code"""new ${TypesJava.DateTimeFormatterBuilder}().appendPattern("HH:mm:ss").appendFraction(${TypesJava.ChronoField}.MICRO_OF_SECOND, 0, 6, true).appendPattern("X").toFormatter()""")
        ),
        sc.Method(
          comments = sc.Comments.Empty,
          tparams = Nil,
          name = sc.Ident("apply"),
          params = List(sc.Param(sc.Ident("value"), TypesJava.OffsetTime)),
          implicitParams = Nil,
          tpe = target,
          body = Some(code"new $target(value.truncatedTo(${TypesJava.ChronoUnit}.MICROS))")
        ),
        sc.Method(
          comments = sc.Comments.Empty,
          tparams = Nil,
          name = sc.Ident("apply"),
          params = List(sc.Param(sc.Ident("str"), TypesJava.String)),
          implicitParams = Nil,
          tpe = target,
          body = Some(code"apply(${TypesJava.OffsetTime}.parse(str, parser))")
        ),
        sc.Method(
          comments = sc.Comments.Empty,
          tparams = Nil,
          name = sc.Ident("now"),
          params = Nil,
          implicitParams = Nil,
          tpe = target,
          body = Some(code"new $target(${TypesJava.OffsetTime}.now())")
        )
      )
  )

  lazy val TypoCircle = CustomType(
    comment = "This represents circle datatype in PostgreSQL, consisting of a point and a radius",
    sqlType = "circle",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoCircle")),
    params = NonEmptyList(
      sc.Param(sc.Ident("center"), TypoPoint.typoType),
      sc.Param(sc.Ident("radius"), lang.Double)
    ),
    toTypo = CustomType.ToTypo(
      jdbcType = TypesJava.PGcircle,
      toTypo = (expr, target) => code"$target(TypoPoint($expr.center.x, $expr.center.y), $expr.radius)"
    ),
    fromTypo = CustomType.FromTypo(
      jdbcType = TypesJava.PGcircle,
      fromTypo = (expr, target) => code"new $target($expr.center.x, $expr.center.y, $expr.radius)"
    ),
    toText = CustomType.Text.string(expr => sc.s(code"<($${$expr.center.x},$${$expr.center.y}),$${$expr.radius}>"))
  )

  lazy val TypoLine = CustomType(
    comment = "This implements a line represented by the linear equation Ax + By + C = 0",
    sqlType = "line",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoLine")),
    params = NonEmptyList(
      sc.Param(sc.Ident("a"), lang.Double),
      sc.Param(sc.Ident("b"), lang.Double),
      sc.Param(sc.Ident("c"), lang.Double)
    ),
    toTypo = CustomType.ToTypo(
      jdbcType = TypesJava.PGline,
      toTypo = (expr, target) => code"$target($expr.a, $expr.b, $expr.c)"
    ),
    fromTypo = CustomType.FromTypo(
      jdbcType = TypesJava.PGline,
      fromTypo = (expr, target) => code"new $target($expr.a, $expr.b, $expr.c)"
    ),
    toText = CustomType.Text.string(expr => sc.s(code"{$${$expr.a},$${$expr.b},$${$expr.c}}"))
  )

  lazy val TypoLineSegment = CustomType(
    comment = "This implements a line represented by the linear equation Ax + By + C = 0",
    sqlType = "lseg",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoLineSegment")),
    params = NonEmptyList(
      sc.Param(sc.Ident("p1"), TypoPoint.typoType),
      sc.Param(sc.Ident("p2"), TypoPoint.typoType)
    ),
    toTypo = CustomType.ToTypo(
      jdbcType = TypesJava.PGlseg,
      toTypo = (expr, target) => code"$target(${TypoPoint.toTypo0(code"$expr.point(0)")}, ${TypoPoint.toTypo0(code"$expr.point(1)")})"
    ),
    fromTypo = CustomType.FromTypo(
      jdbcType = TypesJava.PGlseg,
      fromTypo = (expr, target) => code"new $target(${TypoPoint.fromTypo0(code"$expr.p1")}, ${TypoPoint.fromTypo0(code"$expr.p2")})"
    ),
    toText = CustomType.Text.string(expr => sc.s(code"(($${$expr.p1.x},$${$expr.p1.y}),($${$expr.p2.x},$${$expr.p2.y}))"))
  )

  lazy val TypoPath = CustomType(
    comment = "This implements a path (a multiple segmented line, which may be closed)",
    sqlType = "path",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoPath")),
    params = NonEmptyList(
      sc.Param(sc.Ident("open"), lang.Boolean),
      sc.Param(sc.Ident("points"), lang.ListType.of(TypoPoint.typoType))
    ),
    toTypo = CustomType.ToTypo(
      jdbcType = TypesJava.PGpath,
      toTypo = (expr, target) => code"$target($expr.isOpen, $expr.points.map(p => ${TypoPoint.typoType}(p.x, p.y)).toList)"
    ),
    fromTypo = CustomType.FromTypo(
      jdbcType = TypesJava.PGpath,
      fromTypo = (expr, target) => code"new $target($expr.points.map(p => new ${TypesJava.PGpoint}(p.x, p.y)).toArray, $expr.open)"
    ),
    toText = CustomType.Text.string { expr =>
      val init = code"""$${if ($expr.open) "[" else "("}"""
      val points = code"""$${$expr.points.map(p => s"$${p.x}, $${p.y}").mkString(",")}"""
      val end = code"""$${if ($expr.open) "]" else ")"}"""
      sc.s(init ++ points ++ end)
    }
  )

  lazy val TypoPoint = CustomType(
    comment = "Point datatype in PostgreSQL",
    sqlType = "point",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoPoint")),
    params = NonEmptyList(
      sc.Param(sc.Ident("x"), lang.Double),
      sc.Param(sc.Ident("y"), lang.Double)
    ),
    toTypo = CustomType.ToTypo(
      jdbcType = TypesJava.PGpoint,
      toTypo = (expr, target) => code"$target($expr.x, $expr.y)"
    ),
    fromTypo = CustomType.FromTypo(
      jdbcType = TypesJava.PGpoint,
      fromTypo = (expr, target) => code"new $target($expr.x, $expr.y)"
    ),
    toText = CustomType.Text.string(expr => sc.s(code"($${$expr.x},$${$expr.y})"))
  )

  lazy val TypoPolygon = CustomType(
    comment = "Polygon datatype in PostgreSQL",
    sqlType = "polygon",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoPolygon")),
    params = NonEmptyList(
      sc.Param(sc.Ident("points"), lang.ListType.of(TypoPoint.typoType))
    ),
    toTypo = CustomType.ToTypo(
      jdbcType = TypesJava.PGpolygon,
      toTypo = (expr, target) => code"$target($expr.points.map(p => ${TypoPoint.typoType}(p.x, p.y)).toList)"
    ),
    fromTypo = CustomType.FromTypo(
      jdbcType = TypesJava.PGpolygon,
      fromTypo = (expr, target) => code"new $target($expr.points.map(p => new ${TypesJava.PGpoint}(p.x, p.y)).toArray)"
    ),
    toText = CustomType.Text.string { expr =>
      val points = code"""$${$expr.points.map(p => s"$${p.x}, $${p.y}").mkString(",")}"""
      sc.s(code"($points)")
    }
  )

  lazy val TypoInterval = CustomType(
    comment = "Interval type in PostgreSQL",
    sqlType = "interval",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoInterval")),
    params = NonEmptyList(
      sc.Param(sc.Ident("years"), lang.Int),
      sc.Param(sc.Ident("months"), lang.Int),
      sc.Param(sc.Ident("days"), lang.Int),
      sc.Param(sc.Ident("hours"), lang.Int),
      sc.Param(sc.Ident("minutes"), lang.Int),
      sc.Param(sc.Ident("seconds"), lang.Double)
    ),
    toTypo = CustomType.ToTypo(
      jdbcType = TypesJava.PGInterval,
      toTypo = (expr, target) => code"$target($expr.getYears, $expr.getMonths, $expr.getDays, $expr.getHours, $expr.getMinutes, $expr.getSeconds)"
    ),
    fromTypo = CustomType.FromTypo(
      jdbcType = TypesJava.PGInterval,
      fromTypo = (expr, target) => code"new $target($expr.years, $expr.months, $expr.days, $expr.hours, $expr.minutes, $expr.seconds)"
    ),
    toText = CustomType.Text.string { expr =>
      sc.s(code"P$${$expr.years}Y$${$expr.months}M$${$expr.days}DT$${$expr.hours}H$${$expr.minutes}M$${$expr.seconds}S")
    }
  )

  lazy val TypoHStore = CustomType(
    comment = "The text representation of an hstore, used for input and output, includes zero or more key => value pairs separated by commas",
    sqlType = "hstore",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoHStore")),
    params = NonEmptyList(
      sc.Param(sc.Ident("value"), lang.MapType.of(TypesJava.String, TypesJava.String))
    ),
    toTypo = CustomType.ToTypo(
      jdbcType = TypesJava.Map.of(sc.Type.Wildcard, sc.Type.Wildcard),
      toTypo = (expr, target) => {
        lang match {
          case LangJava => code"new $target((${lang.MapType.of(TypesJava.String, TypesJava.String)}) $expr)"
          case LangScala =>
            code"""|{
                   |  val b = ${lang.MapType}.newBuilder[${TypesJava.String}, ${TypesJava.String}]
                   |  $expr.forEach { case (k, v) => b += k.asInstanceOf[${TypesJava.String}] -> v.asInstanceOf[${TypesJava.String}]}
                   |  $target(b.result())
                   |}""".stripMargin
          case other => sys.error("Unsupported language: " + other)
        }
      }
    ),
    fromTypo = CustomType.FromTypo(
      jdbcType = TypesJava.Map.of(TypesJava.String, TypesJava.String),
      fromTypo = (expr, _) => {
        code"""|{
               |  val b = new ${TypesJava.HashMap}[${TypesJava.String}, ${TypesJava.String}]
               |  $expr.value.foreach { case (k, v) => b.put(k, v)}
               |  b
               |}""".stripMargin
      }
    ),
    forbidArray = true,
    toText = CustomType.Text.string { expr =>
      code"""$expr.value.map { case (k, v) => s"$$k => $$v" }.mkString(",")"""
    }
  )

  lazy val TypoMoney = CustomType(
    comment = "Money and cash types in PostgreSQL",
    sqlType = "money",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoMoney")),
    params = NonEmptyList(
      sc.Param(sc.Ident("value"), lang.BigDecimal)
    ),
    toTypo = CustomType.ToTypo(
      jdbcType = TypesJava.BigDecimal,
      toTypo = (expr, target) =>
        lang match {
          case LangJava  => code"$target($expr)"
          case LangScala => code"$target(${lang.BigDecimal}($expr))"
          case other     => sys.error("Unsupported language: " + other)
        }
    ),
    fromTypo = CustomType.FromTypo(
      jdbcType = TypesJava.BigDecimal,
      fromTypo = (expr, _) =>
        lang match {
          case LangJava  => code"$expr"
          case LangScala => code"$expr.value.bigDecimal"
          case other     => sys.error("Unsupported language: " + other)
        }
    ),
    toText = CustomType.Text(
      lang.BigDecimal,
      expr =>
        lang match {
          case LangJava  => code"$expr"
          case LangScala => code"$expr.value"
          case other     => sys.error("Unsupported language: " + other)
        }
    )
  )

  lazy val TypoShort = CustomType(
    comment = "Short primitive",
    sqlType = "int2",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoShort")),
    params = NonEmptyList(
      sc.Param(sc.Ident("value"), lang.Short)
    ),
    toTypo = CustomType.ToTypo(
      jdbcType = TypesJava.Integer,
      toTypo = (expr, target) => code"$target($expr.toShort)"
    ),
    fromTypo = CustomType.FromTypo(
      jdbcType = TypesJava.Integer,
      fromTypo = (expr, _) => code"$expr.value.toInt"
    ),
    toText = CustomType.Text(lang.Short, expr => code"$expr.value"),
    toTypoInArray = Some(
      CustomType.ToTypo(
        jdbcType = TypesJava.Short,
        toTypo = (expr, target) => code"$target($expr)"
      )
    ),
    fromTypoInArray = Some(
      CustomType.FromTypo(
        jdbcType = TypesJava.Short,
        fromTypo = (expr, _) => code"$expr.value: ${TypesJava.Short}"
      )
    ),
    objBody = target => {
      lang match {
        case LangJava => Nil
        case LangScala =>
          val numericOfTarget = TypesScala.Numeric.of(target)
          val numericBody =
            code"""|new $numericOfTarget {
                   |  override def compare(x: $target, y: $target): ${lang.Int} = ${TypesJava.Short}.compare(x.value, y.value)
                   |  override def plus(x: $target, y: $target): $target = $target((x.value + y.value).toShort)
                   |  override def minus(x: $target, y: $target): $target = $target((x.value - y.value).toShort)
                   |  override def times(x: $target, y: $target): $target = $target((x.value * y.value).toShort)
                   |  override def negate(x: $target): $target = $target((-x.value).toShort)
                   |  override def fromInt(x: Int): $target = $target(x.toShort)
                   |  override def toInt(x: $target): ${lang.Int} = x.value.toInt
                   |  override def toLong(x: $target): ${lang.Long} = x.value.toLong
                   |  override def toFloat(x: $target): ${lang.Float} = x.value.toFloat
                   |  override def toDouble(x: $target): ${lang.Double} = x.value.toDouble
                   |  def parseString(str: String): Option[$target] = str.toShortOption.map($target.apply)
                   |  locally{val _ = parseString("1")}
                   |}"""
          List(sc.Given(Nil, sc.Ident("numeric"), Nil, numericOfTarget, body = numericBody.stripMargin))
        case other => sys.error("Unsupported language: " + other)
      }
    }
  )

  lazy val TypoUUID = CustomType(
    comment = "UUID",
    sqlType = "uuid",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoUUID")),
    params = NonEmptyList(
      sc.Param(sc.Ident("value"), TypesJava.UUID)
    ),
    toTypo = CustomType.ToTypo(
      jdbcType = TypesJava.UUID,
      toTypo = (expr, target) => code"$target($expr)"
    ),
    fromTypo = CustomType.FromTypo(
      jdbcType = TypesJava.UUID,
      fromTypo = (expr, _) => code"$expr.value"
    ),
    toText = CustomType.Text.string(expr => code"$expr.value.toString"),
    objBody = target =>
      List(
        sc.Method(
          comments = sc.Comments.Empty,
          tparams = Nil,
          name = sc.Ident("apply"),
          params = List(sc.Param(sc.Ident("str"), TypesJava.String)),
          implicitParams = Nil,
          tpe = target,
          body = Some(code"new $target(${TypesJava.UUID}.fromString(str))")
        ),
        sc.Method(
          comments = sc.Comments.Empty,
          tparams = Nil,
          name = sc.Ident("randomUUID"),
          params = Nil,
          implicitParams = Nil,
          tpe = target,
          body = Some(code"new $target(${TypesJava.UUID}.randomUUID())")
        )
      )
  )

  lazy val TypoVector = CustomType(
    comment = "extension: https://github.com/pgvector/pgvector",
    sqlType = "vector",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoVector")),
    params = NonEmptyList(
      sc.Param(sc.Ident("value"), sc.Type.ArrayOf(lang.Float))
    ),
    toTypo = CustomType.ToTypo(
      jdbcType = TypesJava.PgArray,
      toTypo = (expr, target) => code"$target($expr.getArray.asInstanceOf[${sc.Type.ArrayOf(TypesJava.Float)}].map(Float2float))"
    ),
    fromTypo = CustomType.FromTypo(
      jdbcType = sc.Type.ArrayOf(TypesJava.Float),
      fromTypo = (expr, _) => code"$expr.value.map(x => x: ${TypesJava.Float})"
    ),
    forbidArray = true,
    toText = CustomType.Text.string(expr => code"""$expr.value.mkString("[", ",", "]")""")
  )

  lazy val TypoXml = CustomType(
    comment = "XML",
    sqlType = "xml",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoXml")),
    params = NonEmptyList(
      sc.Param(sc.Ident("value"), TypesJava.String)
    ),
    toTypo = CustomType.ToTypo(
      jdbcType = TypesJava.PgSQLXML,
      toTypo = (expr, target) => code"$target($expr.getString)"
    ),
    fromTypo = CustomType.FromTypo(
      jdbcType = TypesJava.String,
      fromTypo = (expr, _) => code"$expr.value"
    ),
    toText = CustomType.Text.string(expr => code"$expr.value.toString"),
    fromTypoInArray = Some(
      CustomType.FromTypo(
        jdbcType = TypesJava.PGobject,
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
        jdbcType = TypesJava.PGobject,
        toTypo = (expr, target) => code"$target($expr.getValue)"
      )
    )
  )

  def obj(sqlType: String, name: String): CustomType =
    CustomType(
      comment = s"$sqlType (via PGObject)",
      sqlType = sqlType,
      typoType = sc.Type.Qualified(pkg / sc.Ident(name)),
      params = NonEmptyList(sc.Param(sc.Ident("value"), TypesJava.String)),
      toTypo = CustomType.ToTypo(
        jdbcType = TypesJava.PGobject,
        toTypo = (expr, target) => code"$target($expr.getValue)"
      ),
      fromTypo = CustomType.FromTypo(
        jdbcType = TypesJava.PGobject,
        fromTypo = (expr, target) => code"""|{
                 |  val obj = new $target
                 |  obj.setType(${sc.StrLit(sqlType)})
                 |  obj.setValue($expr.value)
                 |  obj
                 |}""".stripMargin
      ),
      toText = CustomType.Text.string(expr => code"$expr.value"),
      toTypoInArray = Some(
        CustomType.ToTypo(
          jdbcType = TypesJava.String,
          toTypo = (expr, target) => code"$target($expr)"
        )
      )
    )
  lazy val TypoJson = obj("json", "TypoJson")
  lazy val TypoJsonb = obj("jsonb", "TypoJsonb")
  lazy val TypoInet = obj("inet", "TypoInet").copy(toTypoInArray = None)
  lazy val TypoAclItem = obj("aclitem", "TypoAclItem").copy(toTypoInArray = None)
  lazy val TypoAnyArray = obj("anyarray", "TypoAnyArray")
  lazy val TypoInt2Vector = obj("int2vector", "TypoInt2Vector").copy(toTypoInArray = None).withComment(""". Valid syntax: `TypoInt2Vector("1 2 3")""")
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
        TypoVector,
        TypoXid,
        TypoXml
      ).map(ct => (ct.typoType, ct))*
    )

  def TypoUnknown(sqlType: String): CustomType = {
    val ct = CustomType(
      comment = "This is a type typo does not know how to handle yet. This falls back to casting to string and crossing fingers. Time to file an issue! :]",
      sqlType = sqlType,
      typoType = sc.Type.Qualified(pkg / sc.Ident(s"TypoUnknown${Naming.titleCase(sqlType)}")),
      params = NonEmptyList(sc.Param(sc.Ident("value"), TypesJava.String)),
      toTypo = CustomType.ToTypo(
        jdbcType = TypesJava.String,
        toTypo = (expr, target) => code"$target($expr)"
      ),
      fromTypo = CustomType.FromTypo(
        jdbcType = TypesJava.String,
        fromTypo = (expr, _) => code"$expr.value"
      ),
      toText = CustomType.Text.string(expr => code"$expr.value.toString")
    )
    All(ct.typoType) = ct
    ct
  }
}

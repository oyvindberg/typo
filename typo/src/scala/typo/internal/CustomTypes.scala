package typo
package internal

import typo.internal.codegen.*

class CustomTypes(pkg: sc.QIdent) {

  lazy val TypoBox = CustomType(
    comment = "This represents the box datatype in PostgreSQL",
    sqlType = "box",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoBox")),
    params = List(
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

  lazy val TypoCircle = CustomType(
    comment = "This represents circle datatype in PostgreSQL, consisting of a point and a radius",
    sqlType = "circle",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoCircle")),
    params = List(
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
    params = List(
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
    params = List(
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
    params = List(
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
    params = List(
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
    params = List(
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
    params = List(
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
    params = List(
      sc.Param(sc.Ident("value"), sc.Type.Map.of(sc.Type.String, sc.Type.String), None)
    ),
    isNull = p => code"$p == null",
    toTypo = CustomType.ToTypo(
      jdbcType = sc.Type.JavaMap.of(sc.Type.Wildcard, sc.Type.Wildcard),
      toTypo = (expr, target) => {
        code"""|{
               |  val b = ${sc.Type.Map}.newBuilder[${sc.Type.String}, ${sc.Type.String}]
               |  $expr.forEach((k, v) => b += k.asInstanceOf[${sc.Type.String}] -> v.asInstanceOf[${sc.Type.String}])
               |  $target(b.result())
               |}""".stripMargin
      }
    ),
    fromTypo = CustomType.FromTypo(
      jdbcType = sc.Type.JavaMap.of(sc.Type.String, sc.Type.String),
      fromTypo = (expr, _) => {
        code"""|{
               |  val b = new ${sc.Type.JavaHashMap}[${sc.Type.String}, ${sc.Type.String}]
               |  $expr.value.foreach((k, v) => b.put(k, v))
               |  b
               |}""".stripMargin
      }
    )
  )

  lazy val TypoMoney = CustomType(
    comment = "Money and cash types in PostgreSQL",
    sqlType = "money",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoMoney")),
    params = List(
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

  lazy val TypoXml = CustomType(
    comment = "XML",
    sqlType = "xml",
    typoType = sc.Type.Qualified(pkg / sc.Ident("TypoXml")),
    params = List(
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
      params = List(
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

  val All: List[CustomType] =
    List(
      TypoBox,
      TypoCircle,
      TypoLine,
      TypoLineSegment,
      TypoPath,
      TypoPoint,
      TypoPolygon,
      TypoInterval,
      TypoMoney,
      TypoXml,
      TypoJson,
      TypoJsonb,
      TypoHStore,
      TypoInet
    )
}

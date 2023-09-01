---
title: Typo types
---

typo generates some helper types if they are needed by your database schema.

There are multiple reasons why this is sometimes necessary:
- postgres driver has broken handling of offset date/time types. need to go through string in order to not lose offset/precision
- money type is broken in many interesting ways
- possibility of transferring instances of a `Row` type to a platform which doesn't have the postgres driver jar available
- a type to hang otherwise orphan type classes on


The full list is here:

```scala mdoc
import java.time.*

/** aclitem (via PGObject) */
case class TypoAclItem(value: String)
/** anyarray (via PGObject) */
case class TypoAnyArray(value: String)
/** This represents the box datatype in PostgreSQL */
case class TypoBox(x1: Double, y1: Double, x2: Double, y2: Double)
/** This represents circle datatype in PostgreSQL, consisting of a point and a radius */
case class TypoCircle(center: TypoPoint, radius: Double)
/** The text representation of an hstore, used for input and output, includes zero or more key => value pairs separated by commas */
case class TypoHStore(value: Map[String, String])
/** inet (via PGObject) */
case class TypoInet(value: String)
/** int2vector (via PGObject) */
case class TypoInt2Vector(value: String)
/** Interval type in PostgreSQL */
case class TypoInterval(years: Int, months: Int, days: Int, hours: Int, minutes: Int, seconds: Double)
/** json (via PGObject) */
case class TypoJson(value: String)
/** jsonb (via PGObject) */
case class TypoJsonb(value: String)
/** This implements a line represented by the linear equation Ax + By + C = 0 */
case class TypoLine(a: Double, b: Double, c: Double)
/** This implements a line represented by the linear equation Ax + By + C = 0 */
case class TypoLineSegment(p1: TypoPoint, p2: TypoPoint)
/** This is `java.time.LocalDate`, but transferred to and from postgres as strings. The reason is that postgres driver and db libs are broken */
case class TypoLocalDate(value: LocalDate)
/** This is `java.time.LocalDateTime`, but with microsecond precision and transferred to and from postgres as strings. The reason is that postgres driver and db libs are broken */
case class TypoLocalDateTime(value: LocalDateTime)
/** This is `java.time.LocalTime`, but with microsecond precision and transferred to and from postgres as strings. The reason is that postgres driver and db libs are broken */
case class TypoLocalTime(value: LocalTime)
/** Money and cash types in PostgreSQL */
case class TypoMoney(value: BigDecimal)
/** This is `java.time.OffsetDateTime`, but with microsecond precision and transferred to and from postgres as strings. The reason is that postgres driver and db libs are broken */
case class TypoOffsetDateTime(value: OffsetDateTime)
/** This is `java.time.OffsetTime`, but with microsecond precision and transferred to and from postgres as strings. The reason is that postgres driver and db libs are broken */
case class TypoOffsetTime(value: OffsetTime)
/** oidvector (via PGObject) */
case class TypoOidVector(value: String)
/** This implements a path (a multiple segmented line, which may be closed) */
case class TypoPath(open: Boolean, points: List[TypoPoint])
/** pg_node_tree (via PGObject) */
case class TypoPgNodeTree(value: String)
/** Point datatype in PostgreSQL */
case class TypoPoint(x: Double, y: Double)
/** Polygon datatype in PostgreSQL */
case class TypoPolygon(points: List[TypoPoint])
/** regproc (via PGObject) */
case class TypoRegproc(value: String)
/** regtype (via PGObject) */
case class TypoRegtype(value: String)
/** Short primitive */
case class TypoShort(value: Short)
/** xid (via PGObject) */
case class TypoXid(value: String)
/** XML */
case class TypoXml(value: String)
```
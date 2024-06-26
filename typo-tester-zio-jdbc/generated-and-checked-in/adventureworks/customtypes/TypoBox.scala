/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package customtypes

import java.sql.ResultSet
import java.sql.Types
import org.postgresql.geometric.PGbox
import typo.dsl.PGType
import zio.jdbc.JdbcDecoder
import zio.jdbc.JdbcEncoder
import zio.jdbc.SqlFragment.Setter
import zio.json.JsonDecoder
import zio.json.JsonEncoder
import zio.json.ast.Json
import zio.json.internal.Write

/** This represents the box datatype in PostgreSQL */
case class TypoBox(x1: Double, y1: Double, x2: Double, y2: Double)

object TypoBox {
  implicit lazy val arrayJdbcDecoder: JdbcDecoder[Array[TypoBox]] = JdbcDecoder[Array[TypoBox]]((rs: ResultSet) => (i: Int) =>
    rs.getArray(i) match {
      case null => null
      case arr => arr.getArray.asInstanceOf[Array[AnyRef]].map(x => TypoBox(x.asInstanceOf[PGbox].point(0).x, x.asInstanceOf[PGbox].point(0).y, x.asInstanceOf[PGbox].point(1).x, x.asInstanceOf[PGbox].point(1).y))
    },
    "Array[org.postgresql.geometric.PGbox]"
  )
  implicit lazy val arrayJdbcEncoder: JdbcEncoder[Array[TypoBox]] = JdbcEncoder.singleParamEncoder(using arraySetter)
  implicit lazy val arraySetter: Setter[Array[TypoBox]] = Setter.forSqlType((ps, i, v) =>
    ps.setArray(
      i,
      ps.getConnection.createArrayOf(
        "box",
        v.map { vv =>
          new PGbox(vv.x1, vv.y1, vv.x2, vv.y2)
        }
      )
    ),
    Types.ARRAY
  )
  implicit lazy val jdbcDecoder: JdbcDecoder[TypoBox] = JdbcDecoder[TypoBox](
    (rs: ResultSet) => (i: Int) => {
      val v = rs.getObject(i)
      if (v eq null) null else TypoBox(v.asInstanceOf[PGbox].point(0).x, v.asInstanceOf[PGbox].point(0).y, v.asInstanceOf[PGbox].point(1).x, v.asInstanceOf[PGbox].point(1).y)
    },
    "org.postgresql.geometric.PGbox"
  )
  implicit lazy val jdbcEncoder: JdbcEncoder[TypoBox] = JdbcEncoder.singleParamEncoder(using setter)
  implicit lazy val jsonDecoder: JsonDecoder[TypoBox] = JsonDecoder[Json.Obj].mapOrFail { jsonObj =>
    val x1 = jsonObj.get("x1").toRight("Missing field 'x1'").flatMap(_.as(JsonDecoder.double))
    val y1 = jsonObj.get("y1").toRight("Missing field 'y1'").flatMap(_.as(JsonDecoder.double))
    val x2 = jsonObj.get("x2").toRight("Missing field 'x2'").flatMap(_.as(JsonDecoder.double))
    val y2 = jsonObj.get("y2").toRight("Missing field 'y2'").flatMap(_.as(JsonDecoder.double))
    if (x1.isRight && y1.isRight && x2.isRight && y2.isRight)
      Right(TypoBox(x1 = x1.toOption.get, y1 = y1.toOption.get, x2 = x2.toOption.get, y2 = y2.toOption.get))
    else Left(List[Either[String, Any]](x1, y1, x2, y2).flatMap(_.left.toOption).mkString(", "))
  }
  implicit lazy val jsonEncoder: JsonEncoder[TypoBox] = new JsonEncoder[TypoBox] {
    override def unsafeEncode(a: TypoBox, indent: Option[Int], out: Write): Unit = {
      out.write("{")
      out.write(""""x1":""")
      JsonEncoder.double.unsafeEncode(a.x1, indent, out)
      out.write(",")
      out.write(""""y1":""")
      JsonEncoder.double.unsafeEncode(a.y1, indent, out)
      out.write(",")
      out.write(""""x2":""")
      JsonEncoder.double.unsafeEncode(a.x2, indent, out)
      out.write(",")
      out.write(""""y2":""")
      JsonEncoder.double.unsafeEncode(a.y2, indent, out)
      out.write("}")
    }
  }
  implicit lazy val ordering: Ordering[TypoBox] = Ordering.by(x => (x.x1, x.y1, x.x2, x.y2))
  implicit lazy val pgType: PGType[TypoBox] = PGType.instance[TypoBox]("box", Types.OTHER)
  implicit lazy val setter: Setter[TypoBox] = Setter.other(
    (ps, i, v) => {
      ps.setObject(
        i,
        new PGbox(v.x1, v.y1, v.x2, v.y2)
      )
    },
    "box"
  )
  implicit lazy val text: Text[TypoBox] = new Text[TypoBox] {
    override def unsafeEncode(v: TypoBox, sb: StringBuilder) = Text.stringInstance.unsafeEncode(s"((${v.x1},${v.y1}),(${v.x2},${v.y2}))", sb)
    override def unsafeArrayEncode(v: TypoBox, sb: StringBuilder) = Text.stringInstance.unsafeArrayEncode(s"((${v.x1},${v.y1}),(${v.x2},${v.y2}))", sb)
  }
}

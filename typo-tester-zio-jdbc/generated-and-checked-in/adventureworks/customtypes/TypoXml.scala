/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package customtypes

import java.sql.ResultSet
import java.sql.Types
import org.postgresql.jdbc.PgSQLXML
import org.postgresql.util.PGobject
import typo.dsl.Bijection
import typo.dsl.PGType
import zio.jdbc.JdbcDecoder
import zio.jdbc.JdbcEncoder
import zio.jdbc.SqlFragment.Setter
import zio.json.JsonDecoder
import zio.json.JsonEncoder

/** XML */
case class TypoXml(value: String)

object TypoXml {
  implicit lazy val arrayJdbcDecoder: JdbcDecoder[Array[TypoXml]] = JdbcDecoder[Array[TypoXml]]((rs: ResultSet) => (i: Int) =>
    rs.getArray(i) match {
      case null => null
      case arr => arr.getArray.asInstanceOf[Array[AnyRef]].map(x => TypoXml(x.asInstanceOf[PGobject].getValue))
    },
    "Array[java.lang.String]"
  )
  implicit lazy val arrayJdbcEncoder: JdbcEncoder[Array[TypoXml]] = JdbcEncoder.singleParamEncoder(using arraySetter)
  implicit lazy val arraySetter: Setter[Array[TypoXml]] = Setter.forSqlType((ps, i, v) =>
    ps.setArray(
      i,
      ps.getConnection.createArrayOf(
        "xml",
        v.map { vv =>
          {
            val obj = new PGobject
            obj.setType("xml")
            obj.setValue(vv.value)
            obj
          }
        }
      )
    ),
    Types.ARRAY
  )
  implicit lazy val bijection: Bijection[TypoXml, String] = Bijection[TypoXml, String](_.value)(TypoXml.apply)
  implicit lazy val jdbcDecoder: JdbcDecoder[TypoXml] = JdbcDecoder[TypoXml](
    (rs: ResultSet) => (i: Int) => {
      val v = rs.getObject(i)
      if (v eq null) null else TypoXml(v.asInstanceOf[PgSQLXML].getString)
    },
    "java.lang.String"
  )
  implicit lazy val jdbcEncoder: JdbcEncoder[TypoXml] = JdbcEncoder.singleParamEncoder(using setter)
  implicit lazy val jsonDecoder: JsonDecoder[TypoXml] = JsonDecoder.string.map(TypoXml.apply)
  implicit lazy val jsonEncoder: JsonEncoder[TypoXml] = JsonEncoder.string.contramap(_.value)
  implicit lazy val ordering: Ordering[TypoXml] = Ordering.by(_.value)
  implicit lazy val pgType: PGType[TypoXml] = PGType.instance[TypoXml]("xml", Types.OTHER)
  implicit lazy val setter: Setter[TypoXml] = Setter.other(
    (ps, i, v) => {
      ps.setObject(
        i,
        v.value
      )
    },
    "xml"
  )
  implicit lazy val text: Text[TypoXml] = new Text[TypoXml] {
    override def unsafeEncode(v: TypoXml, sb: StringBuilder) = Text.stringInstance.unsafeEncode(v.value.toString, sb)
    override def unsafeArrayEncode(v: TypoXml, sb: StringBuilder) = Text.stringInstance.unsafeArrayEncode(v.value.toString, sb)
  }
}

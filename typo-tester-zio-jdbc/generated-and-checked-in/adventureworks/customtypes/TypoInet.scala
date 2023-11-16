/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package customtypes

import adventureworks.Text
import java.sql.ResultSet
import java.sql.Types
import org.postgresql.util.PGobject
import typo.dsl.Bijection
import typo.dsl.ParameterMetaData
import zio.jdbc.JdbcDecoder
import zio.jdbc.JdbcEncoder
import zio.jdbc.SqlFragment.Setter
import zio.json.JsonDecoder
import zio.json.JsonEncoder

/** inet (via PGObject) */
case class TypoInet(value: String)

object TypoInet {
  implicit lazy val arrayJdbcDecoder: JdbcDecoder[Array[TypoInet]] = JdbcDecoder[Array[TypoInet]]((rs: ResultSet) => (i: Int) =>
    rs.getArray(i) match {
      case null => null
      case arr => arr.getArray.asInstanceOf[Array[AnyRef]].map(x => TypoInet(x.asInstanceOf[PGobject].getValue))
    },
    "scala.Array[org.postgresql.util.PGobject]"
  )
  implicit lazy val arrayJdbcEncoder: JdbcEncoder[Array[TypoInet]] = JdbcEncoder.singleParamEncoder(arraySetter)
  implicit lazy val arraySetter: Setter[Array[TypoInet]] = Setter.forSqlType((ps, i, v) =>
    ps.setArray(
      i,
      ps.getConnection.createArrayOf(
        "inet",
        v.map { vv =>
          {
            val obj = new PGobject
            obj.setType("inet")
            obj.setValue(vv.value)
            obj
          }
        }
      )
    ),
    Types.ARRAY
  )
  implicit lazy val bijection: Bijection[TypoInet, String] = Bijection[TypoInet, String](_.value)(TypoInet.apply)
  implicit lazy val jdbcDecoder: JdbcDecoder[TypoInet] = JdbcDecoder[TypoInet](
    (rs: ResultSet) => (i: Int) => {
      val v = rs.getObject(i)
      if (v eq null) null else TypoInet(v.asInstanceOf[PGobject].getValue)
    },
    "org.postgresql.util.PGobject"
  )
  implicit lazy val jdbcEncoder: JdbcEncoder[TypoInet] = JdbcEncoder.singleParamEncoder(setter)
  implicit lazy val jsonDecoder: JsonDecoder[TypoInet] = JsonDecoder.string.map(TypoInet.apply)
  implicit lazy val jsonEncoder: JsonEncoder[TypoInet] = JsonEncoder.string.contramap(_.value)
  implicit lazy val ordering: Ordering[TypoInet] = Ordering.by(_.value)
  implicit lazy val parameterMetadata: ParameterMetaData[TypoInet] = ParameterMetaData.instance[TypoInet]("inet", Types.OTHER)
  implicit lazy val setter: Setter[TypoInet] = Setter.other(
    (ps, i, v) => {
      ps.setObject(
        i,
        {
          val obj = new PGobject
          obj.setType("inet")
          obj.setValue(v.value)
          obj
        }
      )
    },
    "inet"
  )
  implicit lazy val text: Text[TypoInet] = new Text[TypoInet] {
    override def unsafeEncode(v: TypoInet, sb: StringBuilder) = Text.stringInstance.unsafeEncode(v.value, sb)
    override def unsafeArrayEncode(v: TypoInet, sb: StringBuilder) = Text.stringInstance.unsafeArrayEncode(v.value, sb)
  }
}
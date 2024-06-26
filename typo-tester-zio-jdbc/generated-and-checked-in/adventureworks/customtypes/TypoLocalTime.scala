/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package customtypes

import java.sql.ResultSet
import java.sql.Types
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import typo.dsl.Bijection
import typo.dsl.PGType
import zio.jdbc.JdbcDecoder
import zio.jdbc.JdbcEncoder
import zio.jdbc.SqlFragment.Setter
import zio.json.JsonDecoder
import zio.json.JsonEncoder

/** This is `java.time.LocalTime`, but with microsecond precision and transferred to and from postgres as strings. The reason is that postgres driver and db libs are broken */
case class TypoLocalTime(value: LocalTime)

object TypoLocalTime {
  def apply(value: LocalTime): TypoLocalTime = new TypoLocalTime(value.truncatedTo(ChronoUnit.MICROS))
  def apply(str: String): TypoLocalTime = apply(LocalTime.parse(str))
  def now: TypoLocalTime = TypoLocalTime(LocalTime.now)
  implicit lazy val arrayJdbcDecoder: JdbcDecoder[Array[TypoLocalTime]] = JdbcDecoder[Array[TypoLocalTime]]((rs: ResultSet) => (i: Int) =>
    rs.getArray(i) match {
      case null => null
      case arr => arr.getArray.asInstanceOf[Array[AnyRef]].map(x => TypoLocalTime(LocalTime.parse(x.asInstanceOf[String])))
    },
    "Array[java.lang.String]"
  )
  implicit lazy val arrayJdbcEncoder: JdbcEncoder[Array[TypoLocalTime]] = JdbcEncoder.singleParamEncoder(using arraySetter)
  implicit lazy val arraySetter: Setter[Array[TypoLocalTime]] = Setter.forSqlType((ps, i, v) =>
    ps.setArray(
      i,
      ps.getConnection.createArrayOf(
        "time",
        v.map { vv =>
          vv.value.toString
        }
      )
    ),
    Types.ARRAY
  )
  implicit lazy val bijection: Bijection[TypoLocalTime, LocalTime] = Bijection[TypoLocalTime, LocalTime](_.value)(TypoLocalTime.apply)
  implicit lazy val jdbcDecoder: JdbcDecoder[TypoLocalTime] = JdbcDecoder[TypoLocalTime](
    (rs: ResultSet) => (i: Int) => {
      val v = rs.getObject(i)
      if (v eq null) null else TypoLocalTime(LocalTime.parse(v.asInstanceOf[String]))
    },
    "java.lang.String"
  )
  implicit lazy val jdbcEncoder: JdbcEncoder[TypoLocalTime] = JdbcEncoder.singleParamEncoder(using setter)
  implicit lazy val jsonDecoder: JsonDecoder[TypoLocalTime] = JsonDecoder.localTime.map(TypoLocalTime.apply)
  implicit lazy val jsonEncoder: JsonEncoder[TypoLocalTime] = JsonEncoder.localTime.contramap(_.value)
  implicit lazy val ordering: Ordering[TypoLocalTime] = Ordering.by(_.value)
  implicit lazy val pgType: PGType[TypoLocalTime] = PGType.instance[TypoLocalTime]("time", Types.OTHER)
  implicit lazy val setter: Setter[TypoLocalTime] = Setter.other(
    (ps, i, v) => {
      ps.setObject(
        i,
        v.value.toString
      )
    },
    "time"
  )
  implicit lazy val text: Text[TypoLocalTime] = new Text[TypoLocalTime] {
    override def unsafeEncode(v: TypoLocalTime, sb: StringBuilder) = Text.stringInstance.unsafeEncode(v.value.toString, sb)
    override def unsafeArrayEncode(v: TypoLocalTime, sb: StringBuilder) = Text.stringInstance.unsafeArrayEncode(v.value.toString, sb)
  }
}

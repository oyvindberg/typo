/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package salesterritory

import adventureworks.Text
import typo.dsl.Bijection
import typo.dsl.ParameterMetaData
import zio.jdbc.JdbcDecoder
import zio.jdbc.JdbcEncoder
import zio.jdbc.SqlFragment.Setter
import zio.json.JsonDecoder
import zio.json.JsonEncoder

/** Type for the primary key of table `sales.salesterritory` */
case class SalesterritoryId(value: Int) extends AnyVal
object SalesterritoryId {
  implicit lazy val arraySetter: Setter[Array[SalesterritoryId]] = adventureworks.IntArraySetter.contramap(_.map(_.value))
  implicit lazy val bijection: Bijection[SalesterritoryId, Int] = Bijection[SalesterritoryId, Int](_.value)(SalesterritoryId.apply)
  implicit lazy val jdbcDecoder: JdbcDecoder[SalesterritoryId] = JdbcDecoder.intDecoder.map(SalesterritoryId.apply)
  implicit lazy val jdbcEncoder: JdbcEncoder[SalesterritoryId] = JdbcEncoder.intEncoder.contramap(_.value)
  implicit lazy val jsonDecoder: JsonDecoder[SalesterritoryId] = JsonDecoder.int.map(SalesterritoryId.apply)
  implicit lazy val jsonEncoder: JsonEncoder[SalesterritoryId] = JsonEncoder.int.contramap(_.value)
  implicit lazy val ordering: Ordering[SalesterritoryId] = Ordering.by(_.value)
  implicit lazy val parameterMetadata: ParameterMetaData[SalesterritoryId] = ParameterMetaData.instance[SalesterritoryId](ParameterMetaData.IntParameterMetaData.sqlType, ParameterMetaData.IntParameterMetaData.jdbcType)
  implicit lazy val setter: Setter[SalesterritoryId] = Setter.intSetter.contramap(_.value)
  implicit lazy val text: Text[SalesterritoryId] = new Text[SalesterritoryId] {
    override def unsafeEncode(v: SalesterritoryId, sb: StringBuilder) = Text.intInstance.unsafeEncode(v.value, sb)
    override def unsafeArrayEncode(v: SalesterritoryId, sb: StringBuilder) = Text.intInstance.unsafeArrayEncode(v.value, sb)
  }
}

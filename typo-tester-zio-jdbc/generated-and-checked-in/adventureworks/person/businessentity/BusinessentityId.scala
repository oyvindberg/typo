/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package businessentity

import typo.dsl.Bijection
import typo.dsl.PGType
import zio.jdbc.JdbcDecoder
import zio.jdbc.JdbcEncoder
import zio.jdbc.SqlFragment.Setter
import zio.json.JsonDecoder
import zio.json.JsonEncoder

/** Type for the primary key of table `person.businessentity` */
case class BusinessentityId(value: Int) extends AnyVal
object BusinessentityId {
  implicit lazy val arrayJdbcDecoder: JdbcDecoder[Array[BusinessentityId]] = adventureworks.IntArrayDecoder.map(_.map(BusinessentityId.apply))
  implicit lazy val arrayJdbcEncoder: JdbcEncoder[Array[BusinessentityId]] = adventureworks.IntArrayEncoder.contramap(_.map(_.value))
  implicit lazy val arraySetter: Setter[Array[BusinessentityId]] = adventureworks.IntArraySetter.contramap(_.map(_.value))
  implicit lazy val bijection: Bijection[BusinessentityId, Int] = Bijection[BusinessentityId, Int](_.value)(BusinessentityId.apply)
  implicit lazy val jdbcDecoder: JdbcDecoder[BusinessentityId] = JdbcDecoder.intDecoder.map(BusinessentityId.apply)
  implicit lazy val jdbcEncoder: JdbcEncoder[BusinessentityId] = JdbcEncoder.intEncoder.contramap(_.value)
  implicit lazy val jsonDecoder: JsonDecoder[BusinessentityId] = JsonDecoder.int.map(BusinessentityId.apply)
  implicit lazy val jsonEncoder: JsonEncoder[BusinessentityId] = JsonEncoder.int.contramap(_.value)
  implicit lazy val ordering: Ordering[BusinessentityId] = Ordering.by(_.value)
  implicit lazy val pgType: PGType[BusinessentityId] = PGType.PGTypeInt.as
  implicit lazy val setter: Setter[BusinessentityId] = Setter.intSetter.contramap(_.value)
  implicit lazy val text: Text[BusinessentityId] = new Text[BusinessentityId] {
    override def unsafeEncode(v: BusinessentityId, sb: StringBuilder) = Text.intInstance.unsafeEncode(v.value, sb)
    override def unsafeArrayEncode(v: BusinessentityId, sb: StringBuilder) = Text.intInstance.unsafeArrayEncode(v.value, sb)
  }
}

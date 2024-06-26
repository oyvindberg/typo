/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package addresstype

import typo.dsl.Bijection
import typo.dsl.PGType
import zio.jdbc.JdbcDecoder
import zio.jdbc.JdbcEncoder
import zio.jdbc.SqlFragment.Setter
import zio.json.JsonDecoder
import zio.json.JsonEncoder

/** Type for the primary key of table `person.addresstype` */
case class AddresstypeId(value: Int) extends AnyVal
object AddresstypeId {
  implicit lazy val arrayJdbcDecoder: JdbcDecoder[Array[AddresstypeId]] = adventureworks.IntArrayDecoder.map(_.map(AddresstypeId.apply))
  implicit lazy val arrayJdbcEncoder: JdbcEncoder[Array[AddresstypeId]] = adventureworks.IntArrayEncoder.contramap(_.map(_.value))
  implicit lazy val arraySetter: Setter[Array[AddresstypeId]] = adventureworks.IntArraySetter.contramap(_.map(_.value))
  implicit lazy val bijection: Bijection[AddresstypeId, Int] = Bijection[AddresstypeId, Int](_.value)(AddresstypeId.apply)
  implicit lazy val jdbcDecoder: JdbcDecoder[AddresstypeId] = JdbcDecoder.intDecoder.map(AddresstypeId.apply)
  implicit lazy val jdbcEncoder: JdbcEncoder[AddresstypeId] = JdbcEncoder.intEncoder.contramap(_.value)
  implicit lazy val jsonDecoder: JsonDecoder[AddresstypeId] = JsonDecoder.int.map(AddresstypeId.apply)
  implicit lazy val jsonEncoder: JsonEncoder[AddresstypeId] = JsonEncoder.int.contramap(_.value)
  implicit lazy val ordering: Ordering[AddresstypeId] = Ordering.by(_.value)
  implicit lazy val pgType: PGType[AddresstypeId] = PGType.PGTypeInt.as
  implicit lazy val setter: Setter[AddresstypeId] = Setter.intSetter.contramap(_.value)
  implicit lazy val text: Text[AddresstypeId] = new Text[AddresstypeId] {
    override def unsafeEncode(v: AddresstypeId, sb: StringBuilder) = Text.intInstance.unsafeEncode(v.value, sb)
    override def unsafeArrayEncode(v: AddresstypeId, sb: StringBuilder) = Text.intInstance.unsafeArrayEncode(v.value, sb)
  }
}

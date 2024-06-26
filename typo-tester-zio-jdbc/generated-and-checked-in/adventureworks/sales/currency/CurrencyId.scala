/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package currency

import typo.dsl.Bijection
import typo.dsl.PGType
import zio.jdbc.JdbcDecoder
import zio.jdbc.JdbcEncoder
import zio.jdbc.SqlFragment.Setter
import zio.json.JsonDecoder
import zio.json.JsonEncoder

/** Type for the primary key of table `sales.currency` */
case class CurrencyId(value: /* bpchar, max 3 chars */ String) extends AnyVal
object CurrencyId {
  implicit lazy val arrayJdbcDecoder: JdbcDecoder[Array[CurrencyId]] = adventureworks.StringArrayDecoder.map(_.map(CurrencyId.apply))
  implicit lazy val arrayJdbcEncoder: JdbcEncoder[Array[CurrencyId]] = adventureworks.StringArrayEncoder.contramap(_.map(_.value))
  implicit lazy val arraySetter: Setter[Array[CurrencyId]] = adventureworks.StringArraySetter.contramap(_.map(_.value))
  implicit lazy val bijection: Bijection[CurrencyId, /* bpchar, max 3 chars */ String] = Bijection[CurrencyId, /* bpchar, max 3 chars */ String](_.value)(CurrencyId.apply)
  implicit lazy val jdbcDecoder: JdbcDecoder[CurrencyId] = JdbcDecoder.stringDecoder.map(CurrencyId.apply)
  implicit lazy val jdbcEncoder: JdbcEncoder[CurrencyId] = JdbcEncoder.stringEncoder.contramap(_.value)
  implicit lazy val jsonDecoder: JsonDecoder[CurrencyId] = JsonDecoder.string.map(CurrencyId.apply)
  implicit lazy val jsonEncoder: JsonEncoder[CurrencyId] = JsonEncoder.string.contramap(_.value)
  implicit lazy val ordering: Ordering[CurrencyId] = Ordering.by(_.value)
  implicit lazy val pgType: PGType[CurrencyId] = PGType.PGTypeString.as
  implicit lazy val setter: Setter[CurrencyId] = Setter.stringSetter.contramap(_.value)
  implicit lazy val text: Text[CurrencyId] = new Text[CurrencyId] {
    override def unsafeEncode(v: CurrencyId, sb: StringBuilder) = Text.stringInstance.unsafeEncode(v.value, sb)
    override def unsafeArrayEncode(v: CurrencyId, sb: StringBuilder) = Text.stringInstance.unsafeArrayEncode(v.value, sb)
  }
}

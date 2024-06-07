/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package public

import java.sql.Types
import typo.dsl.Bijection
import typo.dsl.PGType
import zio.jdbc.JdbcDecoder
import zio.jdbc.JdbcEncoder
import zio.jdbc.SqlFragment.Setter
import zio.json.JsonDecoder
import zio.json.JsonEncoder

/** Domain `public.AccountNumber`
  * No constraint
  */
case class AccountNumber(value: String)
object AccountNumber {
  implicit lazy val arrayJdbcDecoder: JdbcDecoder[Array[AccountNumber]] = adventureworks.StringArrayDecoder.map(_.map(AccountNumber.apply))
  implicit lazy val arrayJdbcEncoder: JdbcEncoder[Array[AccountNumber]] = adventureworks.StringArrayEncoder.contramap(_.map(_.value))
  implicit lazy val arraySetter: Setter[Array[AccountNumber]] = adventureworks.StringArraySetter.contramap(_.map(_.value))
  implicit lazy val bijection: Bijection[AccountNumber, String] = Bijection[AccountNumber, String](_.value)(AccountNumber.apply)
  implicit lazy val jdbcDecoder: JdbcDecoder[AccountNumber] = JdbcDecoder.stringDecoder.map(AccountNumber.apply)
  implicit lazy val jdbcEncoder: JdbcEncoder[AccountNumber] = JdbcEncoder.stringEncoder.contramap(_.value)
  implicit lazy val jsonDecoder: JsonDecoder[AccountNumber] = JsonDecoder.string.map(AccountNumber.apply)
  implicit lazy val jsonEncoder: JsonEncoder[AccountNumber] = JsonEncoder.string.contramap(_.value)
  implicit lazy val ordering: Ordering[AccountNumber] = Ordering.by(_.value)
  implicit lazy val pgType: PGType[AccountNumber] = PGType.instance(""""public"."AccountNumber"""", Types.OTHER)
  implicit lazy val setter: Setter[AccountNumber] = Setter.stringSetter.contramap(_.value)
  implicit lazy val text: Text[AccountNumber] = new Text[AccountNumber] {
    override def unsafeEncode(v: AccountNumber, sb: StringBuilder) = Text.stringInstance.unsafeEncode(v.value, sb)
    override def unsafeArrayEncode(v: AccountNumber, sb: StringBuilder) = Text.stringInstance.unsafeArrayEncode(v.value, sb)
  }
}
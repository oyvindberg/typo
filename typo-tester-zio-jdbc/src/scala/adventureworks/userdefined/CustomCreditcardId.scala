/** File has been automatically generated by `typo`.
  *
  * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
  */
package adventureworks.userdefined

import typo.dsl.Bijection
import zio.jdbc.SqlFragment.Setter
import zio.jdbc.{JdbcDecoder, JdbcEncoder}
import zio.json.*

/** Type for the primary key of table `sales.creditcard` */
case class CustomCreditcardId(value: Int) extends AnyVal
object CustomCreditcardId {
  implicit lazy val bijection: Bijection[CustomCreditcardId, Int] = Bijection[CustomCreditcardId, Int](_.value)(CustomCreditcardId.apply)
  implicit lazy val jsonDecoder: JsonDecoder[CustomCreditcardId] = DeriveJsonDecoder.gen[CustomCreditcardId]
  implicit lazy val jsonEncoder: JsonEncoder[CustomCreditcardId] = DeriveJsonEncoder.gen[CustomCreditcardId]
  implicit lazy val jdbcDecoder: JdbcDecoder[CustomCreditcardId] = JdbcDecoder.intDecoder.map(CustomCreditcardId.apply)
  implicit lazy val jdbcEncoder: JdbcEncoder[CustomCreditcardId] = JdbcEncoder.intEncoder.contramap(_.value)
  implicit lazy val jdbcSetter: Setter[CustomCreditcardId] = Setter.intSetter.contramap(_.value)
  implicit lazy val ordering: Ordering[CustomCreditcardId] = Ordering.by(_.value)
}
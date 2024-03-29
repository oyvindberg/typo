/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package transactionhistoryarchive

import adventureworks.Text
import typo.dsl.Bijection
import typo.dsl.ParameterMetaData
import zio.jdbc.JdbcDecoder
import zio.jdbc.JdbcEncoder
import zio.jdbc.SqlFragment.Setter
import zio.json.JsonDecoder
import zio.json.JsonEncoder

/** Type for the primary key of table `production.transactionhistoryarchive` */
case class TransactionhistoryarchiveId(value: Int) extends AnyVal
object TransactionhistoryarchiveId {
  implicit lazy val arraySetter: Setter[Array[TransactionhistoryarchiveId]] = adventureworks.IntArraySetter.contramap(_.map(_.value))
  implicit lazy val bijection: Bijection[TransactionhistoryarchiveId, Int] = Bijection[TransactionhistoryarchiveId, Int](_.value)(TransactionhistoryarchiveId.apply)
  implicit lazy val jdbcDecoder: JdbcDecoder[TransactionhistoryarchiveId] = JdbcDecoder.intDecoder.map(TransactionhistoryarchiveId.apply)
  implicit lazy val jdbcEncoder: JdbcEncoder[TransactionhistoryarchiveId] = JdbcEncoder.intEncoder.contramap(_.value)
  implicit lazy val jsonDecoder: JsonDecoder[TransactionhistoryarchiveId] = JsonDecoder.int.map(TransactionhistoryarchiveId.apply)
  implicit lazy val jsonEncoder: JsonEncoder[TransactionhistoryarchiveId] = JsonEncoder.int.contramap(_.value)
  implicit lazy val ordering: Ordering[TransactionhistoryarchiveId] = Ordering.by(_.value)
  implicit lazy val parameterMetadata: ParameterMetaData[TransactionhistoryarchiveId] = ParameterMetaData.instance[TransactionhistoryarchiveId](ParameterMetaData.IntParameterMetaData.sqlType, ParameterMetaData.IntParameterMetaData.jdbcType)
  implicit lazy val setter: Setter[TransactionhistoryarchiveId] = Setter.intSetter.contramap(_.value)
  implicit lazy val text: Text[TransactionhistoryarchiveId] = new Text[TransactionhistoryarchiveId] {
    override def unsafeEncode(v: TransactionhistoryarchiveId, sb: StringBuilder) = Text.intInstance.unsafeEncode(v.value, sb)
    override def unsafeArrayEncode(v: TransactionhistoryarchiveId, sb: StringBuilder) = Text.intInstance.unsafeArrayEncode(v.value, sb)
  }
}

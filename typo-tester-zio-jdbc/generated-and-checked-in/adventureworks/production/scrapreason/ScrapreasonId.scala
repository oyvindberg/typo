/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package scrapreason

import typo.dsl.Bijection
import typo.dsl.PGType
import zio.jdbc.JdbcDecoder
import zio.jdbc.JdbcEncoder
import zio.jdbc.SqlFragment.Setter
import zio.json.JsonDecoder
import zio.json.JsonEncoder

/** Type for the primary key of table `production.scrapreason` */
case class ScrapreasonId(value: Int) extends AnyVal
object ScrapreasonId {
  implicit lazy val arrayJdbcDecoder: JdbcDecoder[Array[ScrapreasonId]] = adventureworks.IntArrayDecoder.map(_.map(ScrapreasonId.apply))
  implicit lazy val arrayJdbcEncoder: JdbcEncoder[Array[ScrapreasonId]] = adventureworks.IntArrayEncoder.contramap(_.map(_.value))
  implicit lazy val arraySetter: Setter[Array[ScrapreasonId]] = adventureworks.IntArraySetter.contramap(_.map(_.value))
  implicit lazy val bijection: Bijection[ScrapreasonId, Int] = Bijection[ScrapreasonId, Int](_.value)(ScrapreasonId.apply)
  implicit lazy val jdbcDecoder: JdbcDecoder[ScrapreasonId] = JdbcDecoder.intDecoder.map(ScrapreasonId.apply)
  implicit lazy val jdbcEncoder: JdbcEncoder[ScrapreasonId] = JdbcEncoder.intEncoder.contramap(_.value)
  implicit lazy val jsonDecoder: JsonDecoder[ScrapreasonId] = JsonDecoder.int.map(ScrapreasonId.apply)
  implicit lazy val jsonEncoder: JsonEncoder[ScrapreasonId] = JsonEncoder.int.contramap(_.value)
  implicit lazy val ordering: Ordering[ScrapreasonId] = Ordering.by(_.value)
  implicit lazy val pgType: PGType[ScrapreasonId] = PGType.PGTypeInt.as
  implicit lazy val setter: Setter[ScrapreasonId] = Setter.intSetter.contramap(_.value)
  implicit lazy val text: Text[ScrapreasonId] = new Text[ScrapreasonId] {
    override def unsafeEncode(v: ScrapreasonId, sb: StringBuilder) = Text.intInstance.unsafeEncode(v.value, sb)
    override def unsafeArrayEncode(v: ScrapreasonId, sb: StringBuilder) = Text.intInstance.unsafeArrayEncode(v.value, sb)
  }
}

/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package productreview

import typo.dsl.Bijection
import typo.dsl.PGType
import zio.jdbc.JdbcDecoder
import zio.jdbc.JdbcEncoder
import zio.jdbc.SqlFragment.Setter
import zio.json.JsonDecoder
import zio.json.JsonEncoder

/** Type for the primary key of table `production.productreview` */
case class ProductreviewId(value: Int) extends AnyVal
object ProductreviewId {
  implicit lazy val arrayJdbcDecoder: JdbcDecoder[Array[ProductreviewId]] = adventureworks.IntArrayDecoder.map(_.map(ProductreviewId.apply))
  implicit lazy val arrayJdbcEncoder: JdbcEncoder[Array[ProductreviewId]] = adventureworks.IntArrayEncoder.contramap(_.map(_.value))
  implicit lazy val arraySetter: Setter[Array[ProductreviewId]] = adventureworks.IntArraySetter.contramap(_.map(_.value))
  implicit lazy val bijection: Bijection[ProductreviewId, Int] = Bijection[ProductreviewId, Int](_.value)(ProductreviewId.apply)
  implicit lazy val jdbcDecoder: JdbcDecoder[ProductreviewId] = JdbcDecoder.intDecoder.map(ProductreviewId.apply)
  implicit lazy val jdbcEncoder: JdbcEncoder[ProductreviewId] = JdbcEncoder.intEncoder.contramap(_.value)
  implicit lazy val jsonDecoder: JsonDecoder[ProductreviewId] = JsonDecoder.int.map(ProductreviewId.apply)
  implicit lazy val jsonEncoder: JsonEncoder[ProductreviewId] = JsonEncoder.int.contramap(_.value)
  implicit lazy val ordering: Ordering[ProductreviewId] = Ordering.by(_.value)
  implicit lazy val pgType: PGType[ProductreviewId] = PGType.PGTypeInt.as
  implicit lazy val setter: Setter[ProductreviewId] = Setter.intSetter.contramap(_.value)
  implicit lazy val text: Text[ProductreviewId] = new Text[ProductreviewId] {
    override def unsafeEncode(v: ProductreviewId, sb: StringBuilder) = Text.intInstance.unsafeEncode(v.value, sb)
    override def unsafeArrayEncode(v: ProductreviewId, sb: StringBuilder) = Text.intInstance.unsafeArrayEncode(v.value, sb)
  }
}
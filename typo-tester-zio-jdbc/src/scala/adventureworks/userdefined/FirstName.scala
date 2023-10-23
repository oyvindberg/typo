package adventureworks.userdefined

import typo.dsl.Bijection
import zio.json.*

case class FirstName(value: String) extends AnyVal
object FirstName {
  implicit lazy val bijection: Bijection[FirstName, String] = Bijection[FirstName, String](_.value)(FirstName.apply)
  implicit lazy val decoder: JsonDecoder[FirstName] = DeriveJsonDecoder.gen[FirstName]
  implicit lazy val encoder: JsonEncoder[FirstName] = DeriveJsonEncoder.gen[FirstName]
  implicit lazy val ordering: Ordering[FirstName] = Ordering.by(_.value)
}

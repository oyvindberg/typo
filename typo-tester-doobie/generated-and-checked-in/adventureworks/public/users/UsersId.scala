/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package public
package users

import adventureworks.customtypes.TypoUUID
import doobie.postgres.Text
import doobie.util.Get
import doobie.util.Put
import io.circe.Decoder
import io.circe.Encoder
import typo.dsl.Bijection

/** Type for the primary key of table `public.users` */
case class UsersId(value: TypoUUID) extends AnyVal
object UsersId {
  implicit lazy val arrayGet: Get[Array[UsersId]] = TypoUUID.arrayGet.map(_.map(UsersId.apply))
  implicit lazy val arrayPut: Put[Array[UsersId]] = TypoUUID.arrayPut.contramap(_.map(_.value))
  implicit lazy val bijection: Bijection[UsersId, TypoUUID] = Bijection[UsersId, TypoUUID](_.value)(UsersId.apply)
  implicit lazy val decoder: Decoder[UsersId] = TypoUUID.decoder.map(UsersId.apply)
  implicit lazy val encoder: Encoder[UsersId] = TypoUUID.encoder.contramap(_.value)
  implicit lazy val get: Get[UsersId] = TypoUUID.get.map(UsersId.apply)
  implicit def ordering(implicit O0: Ordering[TypoUUID]): Ordering[UsersId] = Ordering.by(_.value)
  implicit lazy val put: Put[UsersId] = TypoUUID.put.contramap(_.value)
  implicit lazy val text: Text[UsersId] = new Text[UsersId] {
    override def unsafeEncode(v: UsersId, sb: StringBuilder) = TypoUUID.text.unsafeEncode(v.value, sb)
    override def unsafeArrayEncode(v: UsersId, sb: StringBuilder) = TypoUUID.text.unsafeArrayEncode(v.value, sb)
  }
}

/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package public
package issue142

import doobie.postgres.Text
import doobie.util.Get
import doobie.util.Put
import doobie.util.Read
import doobie.util.Write
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.Encoder

/** Type for the primary key of table `public.issue142`. It has some known values: 
  *  - aa
  *  - bb
  */
sealed abstract class Issue142Id(val value: String)

object Issue142Id {
  def apply(underlying: String): Issue142Id =
    ByName.getOrElse(underlying, Unknown(underlying))
  case object aa extends Issue142Id("aa")
  case object bb extends Issue142Id("bb")
  case class Unknown(override val value: String) extends Issue142Id(value)
  val All: List[Issue142Id] = List(aa, bb)
  val ByName: Map[String, Issue142Id] = All.map(x => (x.value, x)).toMap
              
  implicit lazy val arrayGet: Get[Array[Issue142Id]] = adventureworks.StringArrayMeta.get.map(_.map(Issue142Id.apply))
  implicit lazy val arrayPut: Put[Array[Issue142Id]] = adventureworks.StringArrayMeta.put.contramap(_.map(_.value))
  implicit lazy val decoder: Decoder[Issue142Id] = Decoder.decodeString.map(Issue142Id.apply)
  implicit lazy val encoder: Encoder[Issue142Id] = Encoder.encodeString.contramap(_.value)
  implicit lazy val get: Get[Issue142Id] = Meta.StringMeta.get.map(Issue142Id.apply)
  implicit lazy val ordering: Ordering[Issue142Id] = Ordering.by(_.value)
  implicit lazy val put: Put[Issue142Id] = Meta.StringMeta.put.contramap(_.value)
  implicit lazy val read: Read[Issue142Id] = new Read.Single(get)
  implicit lazy val text: Text[Issue142Id] = new Text[Issue142Id] {
    override def unsafeEncode(v: Issue142Id, sb: StringBuilder) = Text.stringInstance.unsafeEncode(v.value, sb)
    override def unsafeArrayEncode(v: Issue142Id, sb: StringBuilder) = Text.stringInstance.unsafeArrayEncode(v.value, sb)
  }
  implicit lazy val write: Write[Issue142Id] = new Write.Single(put)
}

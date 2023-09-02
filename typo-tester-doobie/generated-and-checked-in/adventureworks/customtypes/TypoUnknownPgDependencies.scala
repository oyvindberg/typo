/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package customtypes

import cats.data.NonEmptyList
import doobie.util.Get
import doobie.util.Put
import io.circe.Decoder
import io.circe.Encoder
import typo.dsl.Bijection

/** This is a type typo does not know how to handle yet. This falls back to casting to string and crossing fingers. Time to file an issue! :] */
case class TypoUnknownPgDependencies(value: String)

object TypoUnknownPgDependencies {
  implicit lazy val arrayGet: Get[Array[TypoUnknownPgDependencies]] = Get.Advanced.array[AnyRef](NonEmptyList.one("_pg_dependencies"))
    .map(_.map(v => TypoUnknownPgDependencies(v.asInstanceOf[String])))
  implicit lazy val arrayPut: Put[Array[TypoUnknownPgDependencies]] = Put.Advanced.array[AnyRef](NonEmptyList.one("_pg_dependencies"), "pg_dependencies")
    .contramap(_.map(v => v.value))
  implicit lazy val bijection: Bijection[TypoUnknownPgDependencies, String] = Bijection[TypoUnknownPgDependencies, String](_.value)(TypoUnknownPgDependencies.apply)
  implicit lazy val decoder: Decoder[TypoUnknownPgDependencies] = Decoder.decodeString.map(TypoUnknownPgDependencies.apply)
  implicit lazy val encoder: Encoder[TypoUnknownPgDependencies] = Encoder.encodeString.contramap(_.value)
  implicit lazy val get: Get[TypoUnknownPgDependencies] = Get.Advanced.other[String](NonEmptyList.one("pg_dependencies"))
    .map(v => TypoUnknownPgDependencies(v))
  implicit lazy val ordering: Ordering[TypoUnknownPgDependencies] = Ordering.by(_.value)
  implicit lazy val put: Put[TypoUnknownPgDependencies] = Put.Advanced.other[String](NonEmptyList.one("pg_dependencies")).contramap(v => v.value)
}
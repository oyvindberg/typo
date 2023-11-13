/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package countryregion

import typo.dsl.Bijection
import typo.dsl.ParameterMetaData
import zio.jdbc.JdbcDecoder
import zio.jdbc.JdbcEncoder
import zio.jdbc.SqlFragment.Setter
import zio.json.JsonDecoder
import zio.json.JsonEncoder

/** Type for the primary key of table `person.countryregion` */
case class CountryregionId(value: /* max 3 chars */ String) extends AnyVal
object CountryregionId {
  implicit lazy val arraySetter: Setter[Array[CountryregionId]] = adventureworks.StringArraySetter.contramap(_.map(_.value))
  implicit lazy val bijection: Bijection[CountryregionId, /* max 3 chars */ String] = Bijection[CountryregionId, /* max 3 chars */ String](_.value)(CountryregionId.apply)
  implicit lazy val jdbcDecoder: JdbcDecoder[CountryregionId] = JdbcDecoder.stringDecoder.map(CountryregionId.apply)
  implicit lazy val jdbcEncoder: JdbcEncoder[CountryregionId] = JdbcEncoder.stringEncoder.contramap(_.value)
  implicit lazy val jsonDecoder: JsonDecoder[CountryregionId] = JsonDecoder.string.map(CountryregionId.apply)
  implicit lazy val jsonEncoder: JsonEncoder[CountryregionId] = JsonEncoder.string.contramap(_.value)
  implicit lazy val ordering: Ordering[CountryregionId] = Ordering.by(_.value)
  implicit lazy val parameterMetadata: ParameterMetaData[CountryregionId] = ParameterMetaData.instance[CountryregionId](ParameterMetaData.StringParameterMetaData.sqlType, ParameterMetaData.StringParameterMetaData.jdbcType)
  implicit lazy val setter: Setter[CountryregionId] = Setter.stringSetter.contramap(_.value)
}
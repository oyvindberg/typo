/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package currency

import anorm.Column
import anorm.ParameterMetaData
import anorm.ToStatement
import play.api.libs.json.Reads
import play.api.libs.json.Writes
import typo.dsl.Bijection

/** Type for the primary key of table `sales.currency` */
case class CurrencyId(value: /* bpchar */ String) extends AnyVal
object CurrencyId {
  implicit lazy val arrayColumn: Column[Array[CurrencyId]] = Column.columnToArray(column, implicitly)
  implicit lazy val arrayToStatement: ToStatement[Array[CurrencyId]] = implicitly[ToStatement[Array[/* bpchar */ String]]].contramap(_.map(_.value))
  implicit lazy val bijection: Bijection[CurrencyId, /* bpchar */ String] = Bijection[CurrencyId, /* bpchar */ String](_.value)(CurrencyId.apply)
  implicit lazy val column: Column[CurrencyId] = implicitly[Column[/* bpchar */ String]].map(CurrencyId.apply)
  implicit lazy val ordering: Ordering[CurrencyId] = Ordering.by(_.value)
  implicit lazy val parameterMetadata: ParameterMetaData[CurrencyId] = new ParameterMetaData[CurrencyId] {
    override def sqlType: String = implicitly[ParameterMetaData[/* bpchar */ String]].sqlType
    override def jdbcType: Int = implicitly[ParameterMetaData[/* bpchar */ String]].jdbcType
  }
  implicit lazy val reads: Reads[CurrencyId] = Reads.StringReads.map(CurrencyId.apply)
  implicit lazy val toStatement: ToStatement[CurrencyId] = implicitly[ToStatement[/* bpchar */ String]].contramap(_.value)
  implicit lazy val writes: Writes[CurrencyId] = Writes.StringWrites.contramap(_.value)
}
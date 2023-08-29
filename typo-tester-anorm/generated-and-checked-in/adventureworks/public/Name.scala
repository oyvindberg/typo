/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package public

import anorm.Column
import anorm.ParameterMetaData
import anorm.ToStatement
import play.api.libs.json.Reads
import play.api.libs.json.Writes
import typo.dsl.Bijection

/** Domain `public.Name`
  * No constraint
  */
case class Name(value: String) extends AnyVal
object Name {
  implicit lazy val arrayColumn: Column[Array[Name]] = Column.columnToArray(column, implicitly)
  implicit lazy val arrayToStatement: ToStatement[Array[Name]] = ToStatement.arrayToParameter(ParameterMetaData.StringParameterMetaData).contramap(_.map(_.value))
  implicit lazy val bijection: Bijection[Name, String] = Bijection[Name, String](_.value)(Name.apply)
  implicit lazy val column: Column[Name] = Column.columnToString.map(Name.apply)
  implicit lazy val ordering: Ordering[Name] = Ordering.by(_.value)
  implicit lazy val parameterMetadata: ParameterMetaData[Name] = new ParameterMetaData[Name] {
    override def sqlType: String = ParameterMetaData.StringParameterMetaData.sqlType
    override def jdbcType: Int = ParameterMetaData.StringParameterMetaData.jdbcType
  }
  implicit lazy val reads: Reads[Name] = Reads.StringReads.map(Name.apply)
  implicit lazy val toStatement: ToStatement[Name] = ToStatement.stringToStatement.contramap(_.value)
  implicit lazy val writes: Writes[Name] = Writes.StringWrites.contramap(_.value)
}
/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package salesreason

import anorm.Column
import anorm.ParameterMetaData
import anorm.RowParser
import anorm.SqlParser
import anorm.ToStatement
import play.api.libs.json.Format

case class SalesreasonId(value: Int) extends AnyVal
object SalesreasonId {
  implicit val ordering: Ordering[SalesreasonId] = Ordering.by(_.value)
  implicit val format: Format[SalesreasonId] = implicitly[Format[Int]].bimap(SalesreasonId.apply, _.value)
  implicit val toStatement: ToStatement[SalesreasonId] = implicitly[ToStatement[Int]].contramap(_.value)
  implicit val column: Column[SalesreasonId] = implicitly[Column[Int]].map(SalesreasonId.apply)
  def rowParser(prefix: String): RowParser[SalesreasonId] = SqlParser.get[SalesreasonId](prefix + "salesreasonid")
  implicit val parameterMetadata: ParameterMetaData[SalesreasonId] = new ParameterMetaData[SalesreasonId] {
    override def sqlType: String = implicitly[ParameterMetaData[Int]].sqlType
    override def jdbcType: Int = implicitly[ParameterMetaData[Int]].jdbcType
  }

}
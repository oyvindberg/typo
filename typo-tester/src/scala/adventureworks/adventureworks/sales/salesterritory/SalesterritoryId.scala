/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package salesterritory

import anorm.Column
import anorm.ParameterMetaData
import anorm.RowParser
import anorm.SqlParser
import anorm.ToStatement
import play.api.libs.json.Format

case class SalesterritoryId(value: Int) extends AnyVal
object SalesterritoryId {
  implicit val ordering: Ordering[SalesterritoryId] = Ordering.by(_.value)
  implicit val format: Format[SalesterritoryId] = implicitly[Format[Int]].bimap(SalesterritoryId.apply, _.value)
  implicit val toStatement: ToStatement[SalesterritoryId] = implicitly[ToStatement[Int]].contramap(_.value)
  implicit val column: Column[SalesterritoryId] = implicitly[Column[Int]].map(SalesterritoryId.apply)
  def rowParser(prefix: String): RowParser[SalesterritoryId] = SqlParser.get[SalesterritoryId](prefix + "territoryid")
  implicit val parameterMetadata: ParameterMetaData[SalesterritoryId] = new ParameterMetaData[SalesterritoryId] {
    override def sqlType: String = implicitly[ParameterMetaData[Int]].sqlType
    override def jdbcType: Int = implicitly[ParameterMetaData[Int]].jdbcType
  }

}
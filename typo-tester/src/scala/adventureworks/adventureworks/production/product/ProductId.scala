/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package product

import anorm.Column
import anorm.ParameterMetaData
import anorm.RowParser
import anorm.SqlParser
import anorm.ToStatement
import play.api.libs.json.Format

case class ProductId(value: Int) extends AnyVal
object ProductId {
  implicit val ordering: Ordering[ProductId] = Ordering.by(_.value)
  implicit val format: Format[ProductId] = implicitly[Format[Int]].bimap(ProductId.apply, _.value)
  implicit val toStatement: ToStatement[ProductId] = implicitly[ToStatement[Int]].contramap(_.value)
  implicit val column: Column[ProductId] = implicitly[Column[Int]].map(ProductId.apply)
  def rowParser(prefix: String): RowParser[ProductId] = SqlParser.get[ProductId](prefix + "productid")
  implicit val parameterMetadata: ParameterMetaData[ProductId] = new ParameterMetaData[ProductId] {
    override def sqlType: String = implicitly[ParameterMetaData[Int]].sqlType
    override def jdbcType: Int = implicitly[ParameterMetaData[Int]].jdbcType
  }

}
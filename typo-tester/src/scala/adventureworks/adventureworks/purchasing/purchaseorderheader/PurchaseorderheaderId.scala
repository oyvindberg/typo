/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package purchasing
package purchaseorderheader

import anorm.Column
import anorm.ParameterMetaData
import anorm.RowParser
import anorm.SqlParser
import anorm.ToStatement
import play.api.libs.json.Format

case class PurchaseorderheaderId(value: Int) extends AnyVal
object PurchaseorderheaderId {
  implicit val ordering: Ordering[PurchaseorderheaderId] = Ordering.by(_.value)
  implicit val format: Format[PurchaseorderheaderId] = implicitly[Format[Int]].bimap(PurchaseorderheaderId.apply, _.value)
  implicit val toStatement: ToStatement[PurchaseorderheaderId] = implicitly[ToStatement[Int]].contramap(_.value)
  implicit val column: Column[PurchaseorderheaderId] = implicitly[Column[Int]].map(PurchaseorderheaderId.apply)
  def rowParser(prefix: String): RowParser[PurchaseorderheaderId] = SqlParser.get[PurchaseorderheaderId](prefix + "purchaseorderid")
  implicit val parameterMetadata: ParameterMetaData[PurchaseorderheaderId] = new ParameterMetaData[PurchaseorderheaderId] {
    override def sqlType: String = implicitly[ParameterMetaData[Int]].sqlType
    override def jdbcType: Int = implicitly[ParameterMetaData[Int]].jdbcType
  }

}
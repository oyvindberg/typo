/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package purchasing
package purchaseorderdetail

import java.sql.Connection
import typo.dsl.SelectBuilder

trait PurchaseorderdetailRepo {
  def select: SelectBuilder[PurchaseorderdetailFields, PurchaseorderdetailRow]
  def selectAll(implicit c: Connection): List[PurchaseorderdetailRow]
  def selectById(compositeId: PurchaseorderdetailId)(implicit c: Connection): Option[PurchaseorderdetailRow]
}

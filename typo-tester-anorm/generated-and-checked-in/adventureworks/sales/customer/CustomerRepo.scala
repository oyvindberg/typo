/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package customer

import java.sql.Connection
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.UpdateBuilder

trait CustomerRepo {
  def delete(customerid: CustomerId)(implicit c: Connection): Boolean
  def delete: DeleteBuilder[CustomerFields, CustomerRow]
  def insert(unsaved: CustomerRow)(implicit c: Connection): CustomerRow
  def insertStreaming(unsaved: Iterator[CustomerRow], batchSize: Int)(implicit c: Connection): Long
  def insert(unsaved: CustomerRowUnsaved)(implicit c: Connection): CustomerRow
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  def insertUnsavedStreaming(unsaved: Iterator[CustomerRowUnsaved], batchSize: Int)(implicit c: Connection): Long
  def select: SelectBuilder[CustomerFields, CustomerRow]
  def selectAll(implicit c: Connection): List[CustomerRow]
  def selectById(customerid: CustomerId)(implicit c: Connection): Option[CustomerRow]
  def selectByIds(customerids: Array[CustomerId])(implicit c: Connection): List[CustomerRow]
  def update(row: CustomerRow)(implicit c: Connection): Boolean
  def update: UpdateBuilder[CustomerFields, CustomerRow]
  def upsert(unsaved: CustomerRow)(implicit c: Connection): CustomerRow
}

/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package purchasing
package vendor

import adventureworks.person.businessentity.BusinessentityId
import java.sql.Connection
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.UpdateBuilder

trait VendorRepo {
  def delete: DeleteBuilder[VendorFields, VendorRow]
  def deleteById(businessentityid: BusinessentityId)(implicit c: Connection): Boolean
  def deleteByIds(businessentityids: Array[BusinessentityId])(implicit c: Connection): Int
  def insert(unsaved: VendorRow)(implicit c: Connection): VendorRow
  def insert(unsaved: VendorRowUnsaved)(implicit c: Connection): VendorRow
  def insertStreaming(unsaved: Iterator[VendorRow], batchSize: Int = 10000)(implicit c: Connection): Long
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  def insertUnsavedStreaming(unsaved: Iterator[VendorRowUnsaved], batchSize: Int = 10000)(implicit c: Connection): Long
  def select: SelectBuilder[VendorFields, VendorRow]
  def selectAll(implicit c: Connection): List[VendorRow]
  def selectById(businessentityid: BusinessentityId)(implicit c: Connection): Option[VendorRow]
  def selectByIds(businessentityids: Array[BusinessentityId])(implicit c: Connection): List[VendorRow]
  def selectByIdsTracked(businessentityids: Array[BusinessentityId])(implicit c: Connection): Map[BusinessentityId, VendorRow]
  def update: UpdateBuilder[VendorFields, VendorRow]
  def update(row: VendorRow)(implicit c: Connection): Option[VendorRow]
  def upsert(unsaved: VendorRow)(implicit c: Connection): VendorRow
  def upsertBatch(unsaved: Iterable[VendorRow])(implicit c: Connection): List[VendorRow]
  /* NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements */
  def upsertStreaming(unsaved: Iterator[VendorRow], batchSize: Int = 10000)(implicit c: Connection): Int
}

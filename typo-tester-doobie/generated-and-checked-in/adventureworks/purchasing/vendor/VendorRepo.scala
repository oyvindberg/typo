/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package purchasing
package vendor

import adventureworks.person.businessentity.BusinessentityId
import doobie.free.connection.ConnectionIO
import fs2.Stream
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.UpdateBuilder

trait VendorRepo {
  def delete: DeleteBuilder[VendorFields, VendorRow]
  def deleteById(businessentityid: BusinessentityId): ConnectionIO[Boolean]
  def deleteByIds(businessentityids: Array[BusinessentityId]): ConnectionIO[Int]
  def insert(unsaved: VendorRow): ConnectionIO[VendorRow]
  def insert(unsaved: VendorRowUnsaved): ConnectionIO[VendorRow]
  def insertStreaming(unsaved: Stream[ConnectionIO, VendorRow], batchSize: Int = 10000): ConnectionIO[Long]
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  def insertUnsavedStreaming(unsaved: Stream[ConnectionIO, VendorRowUnsaved], batchSize: Int = 10000): ConnectionIO[Long]
  def select: SelectBuilder[VendorFields, VendorRow]
  def selectAll: Stream[ConnectionIO, VendorRow]
  def selectById(businessentityid: BusinessentityId): ConnectionIO[Option[VendorRow]]
  def selectByIds(businessentityids: Array[BusinessentityId]): Stream[ConnectionIO, VendorRow]
  def selectByIdsTracked(businessentityids: Array[BusinessentityId]): ConnectionIO[Map[BusinessentityId, VendorRow]]
  def update: UpdateBuilder[VendorFields, VendorRow]
  def update(row: VendorRow): ConnectionIO[Option[VendorRow]]
  def upsert(unsaved: VendorRow): ConnectionIO[VendorRow]
  def upsertBatch(unsaved: List[VendorRow]): Stream[ConnectionIO, VendorRow]
  /* NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements */
  def upsertStreaming(unsaved: Stream[ConnectionIO, VendorRow], batchSize: Int = 10000): ConnectionIO[Int]
}

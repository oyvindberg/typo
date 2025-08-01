/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package password

import adventureworks.person.businessentity.BusinessentityId
import doobie.free.connection.ConnectionIO
import fs2.Stream
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.UpdateBuilder

trait PasswordRepo {
  def delete: DeleteBuilder[PasswordFields, PasswordRow]
  def deleteById(businessentityid: BusinessentityId): ConnectionIO[Boolean]
  def deleteByIds(businessentityids: Array[BusinessentityId]): ConnectionIO[Int]
  def insert(unsaved: PasswordRow): ConnectionIO[PasswordRow]
  def insert(unsaved: PasswordRowUnsaved): ConnectionIO[PasswordRow]
  def insertStreaming(unsaved: Stream[ConnectionIO, PasswordRow], batchSize: Int = 10000): ConnectionIO[Long]
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  def insertUnsavedStreaming(unsaved: Stream[ConnectionIO, PasswordRowUnsaved], batchSize: Int = 10000): ConnectionIO[Long]
  def select: SelectBuilder[PasswordFields, PasswordRow]
  def selectAll: Stream[ConnectionIO, PasswordRow]
  def selectById(businessentityid: BusinessentityId): ConnectionIO[Option[PasswordRow]]
  def selectByIds(businessentityids: Array[BusinessentityId]): Stream[ConnectionIO, PasswordRow]
  def selectByIdsTracked(businessentityids: Array[BusinessentityId]): ConnectionIO[Map[BusinessentityId, PasswordRow]]
  def update: UpdateBuilder[PasswordFields, PasswordRow]
  def update(row: PasswordRow): ConnectionIO[Option[PasswordRow]]
  def upsert(unsaved: PasswordRow): ConnectionIO[PasswordRow]
  def upsertBatch(unsaved: List[PasswordRow]): Stream[ConnectionIO, PasswordRow]
  /* NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements */
  def upsertStreaming(unsaved: Stream[ConnectionIO, PasswordRow], batchSize: Int = 10000): ConnectionIO[Int]
}

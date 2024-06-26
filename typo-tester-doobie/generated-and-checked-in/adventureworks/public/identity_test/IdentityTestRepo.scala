/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package public
package identity_test

import doobie.free.connection.ConnectionIO
import fs2.Stream
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.UpdateBuilder

trait IdentityTestRepo {
  def delete: DeleteBuilder[IdentityTestFields, IdentityTestRow]
  def deleteById(name: IdentityTestId): ConnectionIO[Boolean]
  def deleteByIds(names: Array[IdentityTestId]): ConnectionIO[Int]
  def insert(unsaved: IdentityTestRow): ConnectionIO[IdentityTestRow]
  def insert(unsaved: IdentityTestRowUnsaved): ConnectionIO[IdentityTestRow]
  def insertStreaming(unsaved: Stream[ConnectionIO, IdentityTestRow], batchSize: Int = 10000): ConnectionIO[Long]
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  def insertUnsavedStreaming(unsaved: Stream[ConnectionIO, IdentityTestRowUnsaved], batchSize: Int = 10000): ConnectionIO[Long]
  def select: SelectBuilder[IdentityTestFields, IdentityTestRow]
  def selectAll: Stream[ConnectionIO, IdentityTestRow]
  def selectById(name: IdentityTestId): ConnectionIO[Option[IdentityTestRow]]
  def selectByIds(names: Array[IdentityTestId]): Stream[ConnectionIO, IdentityTestRow]
  def selectByIdsTracked(names: Array[IdentityTestId]): ConnectionIO[Map[IdentityTestId, IdentityTestRow]]
  def update: UpdateBuilder[IdentityTestFields, IdentityTestRow]
  def update(row: IdentityTestRow): ConnectionIO[Boolean]
  def upsert(unsaved: IdentityTestRow): ConnectionIO[IdentityTestRow]
}

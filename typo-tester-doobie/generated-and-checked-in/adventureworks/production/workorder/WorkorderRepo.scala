/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package workorder

import doobie.free.connection.ConnectionIO
import fs2.Stream
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.UpdateBuilder

trait WorkorderRepo {
  def delete(workorderid: WorkorderId): ConnectionIO[Boolean]
  def delete: DeleteBuilder[WorkorderFields, WorkorderRow]
  def insert(unsaved: WorkorderRow): ConnectionIO[WorkorderRow]
  def insertStreaming(unsaved: Stream[ConnectionIO, WorkorderRow], batchSize: Int): ConnectionIO[Long]
  def insert(unsaved: WorkorderRowUnsaved): ConnectionIO[WorkorderRow]
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  def insertUnsavedStreaming(unsaved: Stream[ConnectionIO, WorkorderRowUnsaved], batchSize: Int): ConnectionIO[Long]
  def select: SelectBuilder[WorkorderFields, WorkorderRow]
  def selectAll: Stream[ConnectionIO, WorkorderRow]
  def selectById(workorderid: WorkorderId): ConnectionIO[Option[WorkorderRow]]
  def selectByIds(workorderids: Array[WorkorderId]): Stream[ConnectionIO, WorkorderRow]
  def update(row: WorkorderRow): ConnectionIO[Boolean]
  def update: UpdateBuilder[WorkorderFields, WorkorderRow]
  def upsert(unsaved: WorkorderRow): ConnectionIO[WorkorderRow]
}

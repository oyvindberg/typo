/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package humanresources
package employeedepartmenthistory

import doobie.free.connection.ConnectionIO
import fs2.Stream
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.UpdateBuilder

trait EmployeedepartmenthistoryRepo {
  def delete(compositeId: EmployeedepartmenthistoryId): ConnectionIO[Boolean]
  def delete: DeleteBuilder[EmployeedepartmenthistoryFields, EmployeedepartmenthistoryRow]
  def insert(unsaved: EmployeedepartmenthistoryRow): ConnectionIO[EmployeedepartmenthistoryRow]
  def insertStreaming(unsaved: Stream[ConnectionIO, EmployeedepartmenthistoryRow], batchSize: Int): ConnectionIO[Long]
  def insert(unsaved: EmployeedepartmenthistoryRowUnsaved): ConnectionIO[EmployeedepartmenthistoryRow]
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  def insertUnsavedStreaming(unsaved: Stream[ConnectionIO, EmployeedepartmenthistoryRowUnsaved], batchSize: Int): ConnectionIO[Long]
  def select: SelectBuilder[EmployeedepartmenthistoryFields, EmployeedepartmenthistoryRow]
  def selectAll: Stream[ConnectionIO, EmployeedepartmenthistoryRow]
  def selectById(compositeId: EmployeedepartmenthistoryId): ConnectionIO[Option[EmployeedepartmenthistoryRow]]
  def update(row: EmployeedepartmenthistoryRow): ConnectionIO[Boolean]
  def update: UpdateBuilder[EmployeedepartmenthistoryFields, EmployeedepartmenthistoryRow]
  def upsert(unsaved: EmployeedepartmenthistoryRow): ConnectionIO[EmployeedepartmenthistoryRow]
}

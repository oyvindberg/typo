/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package humanresources
package employee

import adventureworks.person.businessentity.BusinessentityId
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.UpdateBuilder
import zio.ZIO
import zio.jdbc.UpdateResult
import zio.jdbc.ZConnection
import zio.stream.ZStream

trait EmployeeRepo {
  def delete(businessentityid: BusinessentityId): ZIO[ZConnection, Throwable, Boolean]
  def delete: DeleteBuilder[EmployeeFields, EmployeeRow]
  def insert(unsaved: EmployeeRow): ZIO[ZConnection, Throwable, EmployeeRow]
  def insertStreaming(unsaved: ZStream[ZConnection, Throwable, EmployeeRow], batchSize: Int): ZIO[ZConnection, Throwable, Long]
  def insert(unsaved: EmployeeRowUnsaved): ZIO[ZConnection, Throwable, EmployeeRow]
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  def insertUnsavedStreaming(unsaved: ZStream[ZConnection, Throwable, EmployeeRowUnsaved], batchSize: Int): ZIO[ZConnection, Throwable, Long]
  def select: SelectBuilder[EmployeeFields, EmployeeRow]
  def selectAll: ZStream[ZConnection, Throwable, EmployeeRow]
  def selectById(businessentityid: BusinessentityId): ZIO[ZConnection, Throwable, Option[EmployeeRow]]
  def selectByIds(businessentityids: Array[BusinessentityId]): ZStream[ZConnection, Throwable, EmployeeRow]
  def update(row: EmployeeRow): ZIO[ZConnection, Throwable, Boolean]
  def update: UpdateBuilder[EmployeeFields, EmployeeRow]
  def upsert(unsaved: EmployeeRow): ZIO[ZConnection, Throwable, UpdateResult[EmployeeRow]]
}
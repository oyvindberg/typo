/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package businessentityaddress

import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.UpdateBuilder
import zio.ZIO
import zio.jdbc.UpdateResult
import zio.jdbc.ZConnection
import zio.stream.ZStream

trait BusinessentityaddressRepo {
  def delete(compositeId: BusinessentityaddressId): ZIO[ZConnection, Throwable, Boolean]
  def delete: DeleteBuilder[BusinessentityaddressFields, BusinessentityaddressRow]
  def insert(unsaved: BusinessentityaddressRow): ZIO[ZConnection, Throwable, BusinessentityaddressRow]
  def insertStreaming(unsaved: ZStream[ZConnection, Throwable, BusinessentityaddressRow], batchSize: Int): ZIO[ZConnection, Throwable, Long]
  def insert(unsaved: BusinessentityaddressRowUnsaved): ZIO[ZConnection, Throwable, BusinessentityaddressRow]
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  def insertUnsavedStreaming(unsaved: ZStream[ZConnection, Throwable, BusinessentityaddressRowUnsaved], batchSize: Int): ZIO[ZConnection, Throwable, Long]
  def select: SelectBuilder[BusinessentityaddressFields, BusinessentityaddressRow]
  def selectAll: ZStream[ZConnection, Throwable, BusinessentityaddressRow]
  def selectById(compositeId: BusinessentityaddressId): ZIO[ZConnection, Throwable, Option[BusinessentityaddressRow]]
  def update(row: BusinessentityaddressRow): ZIO[ZConnection, Throwable, Boolean]
  def update: UpdateBuilder[BusinessentityaddressFields, BusinessentityaddressRow]
  def upsert(unsaved: BusinessentityaddressRow): ZIO[ZConnection, Throwable, UpdateResult[BusinessentityaddressRow]]
}
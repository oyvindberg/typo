/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package businessentitycontact

import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.UpdateBuilder
import zio.ZIO
import zio.jdbc.UpdateResult
import zio.jdbc.ZConnection
import zio.stream.ZStream

trait BusinessentitycontactRepo {
  def delete(compositeId: BusinessentitycontactId): ZIO[ZConnection, Throwable, Boolean]
  def delete: DeleteBuilder[BusinessentitycontactFields, BusinessentitycontactRow]
  def insert(unsaved: BusinessentitycontactRow): ZIO[ZConnection, Throwable, BusinessentitycontactRow]
  def insertStreaming(unsaved: ZStream[ZConnection, Throwable, BusinessentitycontactRow], batchSize: Int): ZIO[ZConnection, Throwable, Long]
  def insert(unsaved: BusinessentitycontactRowUnsaved): ZIO[ZConnection, Throwable, BusinessentitycontactRow]
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  def insertUnsavedStreaming(unsaved: ZStream[ZConnection, Throwable, BusinessentitycontactRowUnsaved], batchSize: Int): ZIO[ZConnection, Throwable, Long]
  def select: SelectBuilder[BusinessentitycontactFields, BusinessentitycontactRow]
  def selectAll: ZStream[ZConnection, Throwable, BusinessentitycontactRow]
  def selectById(compositeId: BusinessentitycontactId): ZIO[ZConnection, Throwable, Option[BusinessentitycontactRow]]
  def update(row: BusinessentitycontactRow): ZIO[ZConnection, Throwable, Boolean]
  def update: UpdateBuilder[BusinessentitycontactFields, BusinessentitycontactRow]
  def upsert(unsaved: BusinessentitycontactRow): ZIO[ZConnection, Throwable, UpdateResult[BusinessentitycontactRow]]
}
/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package salesreason

import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.UpdateBuilder
import zio.ZIO
import zio.jdbc.UpdateResult
import zio.jdbc.ZConnection
import zio.stream.ZStream

trait SalesreasonRepo {
  def delete(salesreasonid: SalesreasonId): ZIO[ZConnection, Throwable, Boolean]
  def delete: DeleteBuilder[SalesreasonFields, SalesreasonRow]
  def insert(unsaved: SalesreasonRow): ZIO[ZConnection, Throwable, SalesreasonRow]
  def insertStreaming(unsaved: ZStream[ZConnection, Throwable, SalesreasonRow], batchSize: Int): ZIO[ZConnection, Throwable, Long]
  def insert(unsaved: SalesreasonRowUnsaved): ZIO[ZConnection, Throwable, SalesreasonRow]
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  def insertUnsavedStreaming(unsaved: ZStream[ZConnection, Throwable, SalesreasonRowUnsaved], batchSize: Int): ZIO[ZConnection, Throwable, Long]
  def select: SelectBuilder[SalesreasonFields, SalesreasonRow]
  def selectAll: ZStream[ZConnection, Throwable, SalesreasonRow]
  def selectById(salesreasonid: SalesreasonId): ZIO[ZConnection, Throwable, Option[SalesreasonRow]]
  def selectByIds(salesreasonids: Array[SalesreasonId]): ZStream[ZConnection, Throwable, SalesreasonRow]
  def update(row: SalesreasonRow): ZIO[ZConnection, Throwable, Boolean]
  def update: UpdateBuilder[SalesreasonFields, SalesreasonRow]
  def upsert(unsaved: SalesreasonRow): ZIO[ZConnection, Throwable, UpdateResult[SalesreasonRow]]
}
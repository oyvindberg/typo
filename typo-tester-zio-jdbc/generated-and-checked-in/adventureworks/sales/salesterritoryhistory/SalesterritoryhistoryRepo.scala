/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package salesterritoryhistory

import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.UpdateBuilder
import zio.ZIO
import zio.jdbc.UpdateResult
import zio.jdbc.ZConnection
import zio.stream.ZStream

trait SalesterritoryhistoryRepo {
  def delete(compositeId: SalesterritoryhistoryId): ZIO[ZConnection, Throwable, Boolean]
  def delete: DeleteBuilder[SalesterritoryhistoryFields, SalesterritoryhistoryRow]
  def insert(unsaved: SalesterritoryhistoryRow): ZIO[ZConnection, Throwable, SalesterritoryhistoryRow]
  def insertStreaming(unsaved: ZStream[ZConnection, Throwable, SalesterritoryhistoryRow], batchSize: Int): ZIO[ZConnection, Throwable, Long]
  def insert(unsaved: SalesterritoryhistoryRowUnsaved): ZIO[ZConnection, Throwable, SalesterritoryhistoryRow]
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  def insertUnsavedStreaming(unsaved: ZStream[ZConnection, Throwable, SalesterritoryhistoryRowUnsaved], batchSize: Int): ZIO[ZConnection, Throwable, Long]
  def select: SelectBuilder[SalesterritoryhistoryFields, SalesterritoryhistoryRow]
  def selectAll: ZStream[ZConnection, Throwable, SalesterritoryhistoryRow]
  def selectById(compositeId: SalesterritoryhistoryId): ZIO[ZConnection, Throwable, Option[SalesterritoryhistoryRow]]
  def update(row: SalesterritoryhistoryRow): ZIO[ZConnection, Throwable, Boolean]
  def update: UpdateBuilder[SalesterritoryhistoryFields, SalesterritoryhistoryRow]
  def upsert(unsaved: SalesterritoryhistoryRow): ZIO[ZConnection, Throwable, UpdateResult[SalesterritoryhistoryRow]]
}

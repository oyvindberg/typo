/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package transactionhistoryarchive

import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.UpdateBuilder
import zio.ZIO
import zio.jdbc.UpdateResult
import zio.jdbc.ZConnection
import zio.stream.ZStream

trait TransactionhistoryarchiveRepo {
  def delete: DeleteBuilder[TransactionhistoryarchiveFields, TransactionhistoryarchiveRow]
  def deleteById(transactionid: TransactionhistoryarchiveId): ZIO[ZConnection, Throwable, Boolean]
  def deleteByIds(transactionids: Array[TransactionhistoryarchiveId]): ZIO[ZConnection, Throwable, Long]
  def insert(unsaved: TransactionhistoryarchiveRow): ZIO[ZConnection, Throwable, TransactionhistoryarchiveRow]
  def insert(unsaved: TransactionhistoryarchiveRowUnsaved): ZIO[ZConnection, Throwable, TransactionhistoryarchiveRow]
  def insertStreaming(unsaved: ZStream[ZConnection, Throwable, TransactionhistoryarchiveRow], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long]
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  def insertUnsavedStreaming(unsaved: ZStream[ZConnection, Throwable, TransactionhistoryarchiveRowUnsaved], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long]
  def select: SelectBuilder[TransactionhistoryarchiveFields, TransactionhistoryarchiveRow]
  def selectAll: ZStream[ZConnection, Throwable, TransactionhistoryarchiveRow]
  def selectById(transactionid: TransactionhistoryarchiveId): ZIO[ZConnection, Throwable, Option[TransactionhistoryarchiveRow]]
  def selectByIds(transactionids: Array[TransactionhistoryarchiveId]): ZStream[ZConnection, Throwable, TransactionhistoryarchiveRow]
  def selectByIdsTracked(transactionids: Array[TransactionhistoryarchiveId]): ZIO[ZConnection, Throwable, Map[TransactionhistoryarchiveId, TransactionhistoryarchiveRow]]
  def update: UpdateBuilder[TransactionhistoryarchiveFields, TransactionhistoryarchiveRow]
  def update(row: TransactionhistoryarchiveRow): ZIO[ZConnection, Throwable, Option[TransactionhistoryarchiveRow]]
  def upsert(unsaved: TransactionhistoryarchiveRow): ZIO[ZConnection, Throwable, UpdateResult[TransactionhistoryarchiveRow]]
  // Not implementable for zio-jdbc: upsertBatch
  /* NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements */
  def upsertStreaming(unsaved: ZStream[ZConnection, Throwable, TransactionhistoryarchiveRow], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long]
}

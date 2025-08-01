/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package productinventory

import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.UpdateBuilder
import zio.ZIO
import zio.jdbc.UpdateResult
import zio.jdbc.ZConnection
import zio.stream.ZStream

trait ProductinventoryRepo {
  def delete: DeleteBuilder[ProductinventoryFields, ProductinventoryRow]
  def deleteById(compositeId: ProductinventoryId): ZIO[ZConnection, Throwable, Boolean]
  def deleteByIds(compositeIds: Array[ProductinventoryId]): ZIO[ZConnection, Throwable, Long]
  def insert(unsaved: ProductinventoryRow): ZIO[ZConnection, Throwable, ProductinventoryRow]
  def insert(unsaved: ProductinventoryRowUnsaved): ZIO[ZConnection, Throwable, ProductinventoryRow]
  def insertStreaming(unsaved: ZStream[ZConnection, Throwable, ProductinventoryRow], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long]
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  def insertUnsavedStreaming(unsaved: ZStream[ZConnection, Throwable, ProductinventoryRowUnsaved], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long]
  def select: SelectBuilder[ProductinventoryFields, ProductinventoryRow]
  def selectAll: ZStream[ZConnection, Throwable, ProductinventoryRow]
  def selectById(compositeId: ProductinventoryId): ZIO[ZConnection, Throwable, Option[ProductinventoryRow]]
  def selectByIds(compositeIds: Array[ProductinventoryId]): ZStream[ZConnection, Throwable, ProductinventoryRow]
  def selectByIdsTracked(compositeIds: Array[ProductinventoryId]): ZIO[ZConnection, Throwable, Map[ProductinventoryId, ProductinventoryRow]]
  def update: UpdateBuilder[ProductinventoryFields, ProductinventoryRow]
  def update(row: ProductinventoryRow): ZIO[ZConnection, Throwable, Option[ProductinventoryRow]]
  def upsert(unsaved: ProductinventoryRow): ZIO[ZConnection, Throwable, UpdateResult[ProductinventoryRow]]
  // Not implementable for zio-jdbc: upsertBatch
  /* NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements */
  def upsertStreaming(unsaved: ZStream[ZConnection, Throwable, ProductinventoryRow], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long]
}

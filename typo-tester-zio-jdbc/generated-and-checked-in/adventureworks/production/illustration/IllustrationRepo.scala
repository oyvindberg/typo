/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package illustration

import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.UpdateBuilder
import zio.ZIO
import zio.jdbc.UpdateResult
import zio.jdbc.ZConnection
import zio.stream.ZStream

trait IllustrationRepo {
  def delete: DeleteBuilder[IllustrationFields, IllustrationRow]
  def deleteById(illustrationid: IllustrationId): ZIO[ZConnection, Throwable, Boolean]
  def deleteByIds(illustrationids: Array[IllustrationId]): ZIO[ZConnection, Throwable, Long]
  def insert(unsaved: IllustrationRow): ZIO[ZConnection, Throwable, IllustrationRow]
  def insert(unsaved: IllustrationRowUnsaved): ZIO[ZConnection, Throwable, IllustrationRow]
  def insertStreaming(unsaved: ZStream[ZConnection, Throwable, IllustrationRow], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long]
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  def insertUnsavedStreaming(unsaved: ZStream[ZConnection, Throwable, IllustrationRowUnsaved], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long]
  def select: SelectBuilder[IllustrationFields, IllustrationRow]
  def selectAll: ZStream[ZConnection, Throwable, IllustrationRow]
  def selectById(illustrationid: IllustrationId): ZIO[ZConnection, Throwable, Option[IllustrationRow]]
  def selectByIds(illustrationids: Array[IllustrationId]): ZStream[ZConnection, Throwable, IllustrationRow]
  def selectByIdsTracked(illustrationids: Array[IllustrationId]): ZIO[ZConnection, Throwable, Map[IllustrationId, IllustrationRow]]
  def update: UpdateBuilder[IllustrationFields, IllustrationRow]
  def update(row: IllustrationRow): ZIO[ZConnection, Throwable, Option[IllustrationRow]]
  def upsert(unsaved: IllustrationRow): ZIO[ZConnection, Throwable, UpdateResult[IllustrationRow]]
  // Not implementable for zio-jdbc: upsertBatch
  /* NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements */
  def upsertStreaming(unsaved: ZStream[ZConnection, Throwable, IllustrationRow], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long]
}

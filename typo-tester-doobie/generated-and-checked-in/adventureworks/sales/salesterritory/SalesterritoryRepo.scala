/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package salesterritory

import doobie.free.connection.ConnectionIO
import fs2.Stream
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.UpdateBuilder

trait SalesterritoryRepo {
  def delete: DeleteBuilder[SalesterritoryFields, SalesterritoryRow]
  def deleteById(territoryid: SalesterritoryId): ConnectionIO[Boolean]
  def deleteByIds(territoryids: Array[SalesterritoryId]): ConnectionIO[Int]
  def insert(unsaved: SalesterritoryRow): ConnectionIO[SalesterritoryRow]
  def insert(unsaved: SalesterritoryRowUnsaved): ConnectionIO[SalesterritoryRow]
  def insertStreaming(unsaved: Stream[ConnectionIO, SalesterritoryRow], batchSize: Int = 10000): ConnectionIO[Long]
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  def insertUnsavedStreaming(unsaved: Stream[ConnectionIO, SalesterritoryRowUnsaved], batchSize: Int = 10000): ConnectionIO[Long]
  def select: SelectBuilder[SalesterritoryFields, SalesterritoryRow]
  def selectAll: Stream[ConnectionIO, SalesterritoryRow]
  def selectById(territoryid: SalesterritoryId): ConnectionIO[Option[SalesterritoryRow]]
  def selectByIds(territoryids: Array[SalesterritoryId]): Stream[ConnectionIO, SalesterritoryRow]
  def selectByIdsTracked(territoryids: Array[SalesterritoryId]): ConnectionIO[Map[SalesterritoryId, SalesterritoryRow]]
  def update: UpdateBuilder[SalesterritoryFields, SalesterritoryRow]
  def update(row: SalesterritoryRow): ConnectionIO[Option[SalesterritoryRow]]
  def upsert(unsaved: SalesterritoryRow): ConnectionIO[SalesterritoryRow]
  def upsertBatch(unsaved: List[SalesterritoryRow]): Stream[ConnectionIO, SalesterritoryRow]
  /* NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements */
  def upsertStreaming(unsaved: Stream[ConnectionIO, SalesterritoryRow], batchSize: Int = 10000): ConnectionIO[Int]
}

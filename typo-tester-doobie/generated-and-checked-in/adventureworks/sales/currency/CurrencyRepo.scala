/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package currency

import doobie.free.connection.ConnectionIO
import fs2.Stream
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.UpdateBuilder

trait CurrencyRepo {
  def delete: DeleteBuilder[CurrencyFields, CurrencyRow]
  def deleteById(currencycode: CurrencyId): ConnectionIO[Boolean]
  def deleteByIds(currencycodes: Array[CurrencyId]): ConnectionIO[Int]
  def insert(unsaved: CurrencyRow): ConnectionIO[CurrencyRow]
  def insert(unsaved: CurrencyRowUnsaved): ConnectionIO[CurrencyRow]
  def insertStreaming(unsaved: Stream[ConnectionIO, CurrencyRow], batchSize: Int = 10000): ConnectionIO[Long]
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  def insertUnsavedStreaming(unsaved: Stream[ConnectionIO, CurrencyRowUnsaved], batchSize: Int = 10000): ConnectionIO[Long]
  def select: SelectBuilder[CurrencyFields, CurrencyRow]
  def selectAll: Stream[ConnectionIO, CurrencyRow]
  def selectById(currencycode: CurrencyId): ConnectionIO[Option[CurrencyRow]]
  def selectByIds(currencycodes: Array[CurrencyId]): Stream[ConnectionIO, CurrencyRow]
  def selectByIdsTracked(currencycodes: Array[CurrencyId]): ConnectionIO[Map[CurrencyId, CurrencyRow]]
  def update: UpdateBuilder[CurrencyFields, CurrencyRow]
  def update(row: CurrencyRow): ConnectionIO[Option[CurrencyRow]]
  def upsert(unsaved: CurrencyRow): ConnectionIO[CurrencyRow]
  def upsertBatch(unsaved: List[CurrencyRow]): Stream[ConnectionIO, CurrencyRow]
  /* NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements */
  def upsertStreaming(unsaved: Stream[ConnectionIO, CurrencyRow], batchSize: Int = 10000): ConnectionIO[Int]
}

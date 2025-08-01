/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package creditcard

import adventureworks.userdefined.CustomCreditcardId
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.UpdateBuilder
import zio.ZIO
import zio.jdbc.JdbcEncoder
import zio.jdbc.UpdateResult
import zio.jdbc.ZConnection
import zio.stream.ZStream

trait CreditcardRepo {
  def delete: DeleteBuilder[CreditcardFields, CreditcardRow]
  def deleteById(creditcardid: /* user-picked */ CustomCreditcardId): ZIO[ZConnection, Throwable, Boolean]
  def deleteByIds(creditcardids: Array[/* user-picked */ CustomCreditcardId])(implicit encoder0: JdbcEncoder[Array[/* user-picked */ CustomCreditcardId]]): ZIO[ZConnection, Throwable, Long]
  def insert(unsaved: CreditcardRow): ZIO[ZConnection, Throwable, CreditcardRow]
  def insert(unsaved: CreditcardRowUnsaved): ZIO[ZConnection, Throwable, CreditcardRow]
  def insertStreaming(unsaved: ZStream[ZConnection, Throwable, CreditcardRow], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long]
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  def insertUnsavedStreaming(unsaved: ZStream[ZConnection, Throwable, CreditcardRowUnsaved], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long]
  def select: SelectBuilder[CreditcardFields, CreditcardRow]
  def selectAll: ZStream[ZConnection, Throwable, CreditcardRow]
  def selectById(creditcardid: /* user-picked */ CustomCreditcardId): ZIO[ZConnection, Throwable, Option[CreditcardRow]]
  def selectByIds(creditcardids: Array[/* user-picked */ CustomCreditcardId])(implicit encoder0: JdbcEncoder[Array[/* user-picked */ CustomCreditcardId]]): ZStream[ZConnection, Throwable, CreditcardRow]
  def selectByIdsTracked(creditcardids: Array[/* user-picked */ CustomCreditcardId])(implicit encoder0: JdbcEncoder[Array[/* user-picked */ CustomCreditcardId]]): ZIO[ZConnection, Throwable, Map[/* user-picked */ CustomCreditcardId, CreditcardRow]]
  def update: UpdateBuilder[CreditcardFields, CreditcardRow]
  def update(row: CreditcardRow): ZIO[ZConnection, Throwable, Option[CreditcardRow]]
  def upsert(unsaved: CreditcardRow): ZIO[ZConnection, Throwable, UpdateResult[CreditcardRow]]
  // Not implementable for zio-jdbc: upsertBatch
  /* NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements */
  def upsertStreaming(unsaved: ZStream[ZConnection, Throwable, CreditcardRow], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long]
}

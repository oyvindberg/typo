/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package public
package test_sak_soknadsalternativ

import doobie.free.connection.ConnectionIO
import fs2.Stream
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.UpdateBuilder

trait TestSakSoknadsalternativRepo {
  def delete: DeleteBuilder[TestSakSoknadsalternativFields, TestSakSoknadsalternativRow]
  def deleteById(compositeId: TestSakSoknadsalternativId): ConnectionIO[Boolean]
  def deleteByIds(compositeIds: Array[TestSakSoknadsalternativId]): ConnectionIO[Int]
  def insert(unsaved: TestSakSoknadsalternativRow): ConnectionIO[TestSakSoknadsalternativRow]
  def insertStreaming(unsaved: Stream[ConnectionIO, TestSakSoknadsalternativRow], batchSize: Int = 10000): ConnectionIO[Long]
  def select: SelectBuilder[TestSakSoknadsalternativFields, TestSakSoknadsalternativRow]
  def selectAll: Stream[ConnectionIO, TestSakSoknadsalternativRow]
  def selectById(compositeId: TestSakSoknadsalternativId): ConnectionIO[Option[TestSakSoknadsalternativRow]]
  def selectByIds(compositeIds: Array[TestSakSoknadsalternativId]): Stream[ConnectionIO, TestSakSoknadsalternativRow]
  def selectByIdsTracked(compositeIds: Array[TestSakSoknadsalternativId]): ConnectionIO[Map[TestSakSoknadsalternativId, TestSakSoknadsalternativRow]]
  def update: UpdateBuilder[TestSakSoknadsalternativFields, TestSakSoknadsalternativRow]
  def update(row: TestSakSoknadsalternativRow): ConnectionIO[Option[TestSakSoknadsalternativRow]]
  def upsert(unsaved: TestSakSoknadsalternativRow): ConnectionIO[TestSakSoknadsalternativRow]
  def upsertBatch(unsaved: List[TestSakSoknadsalternativRow]): Stream[ConnectionIO, TestSakSoknadsalternativRow]
  /* NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements */
  def upsertStreaming(unsaved: Stream[ConnectionIO, TestSakSoknadsalternativRow], batchSize: Int = 10000): ConnectionIO[Int]
}

/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package humanresources
package jobcandidate

import doobie.free.connection.ConnectionIO
import fs2.Stream
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.UpdateBuilder

trait JobcandidateRepo {
  def delete(jobcandidateid: JobcandidateId): ConnectionIO[Boolean]
  def delete: DeleteBuilder[JobcandidateFields, JobcandidateRow]
  def insert(unsaved: JobcandidateRow): ConnectionIO[JobcandidateRow]
  def insertStreaming(unsaved: Stream[ConnectionIO, JobcandidateRow], batchSize: Int): ConnectionIO[Long]
  def insert(unsaved: JobcandidateRowUnsaved): ConnectionIO[JobcandidateRow]
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  def insertUnsavedStreaming(unsaved: Stream[ConnectionIO, JobcandidateRowUnsaved], batchSize: Int): ConnectionIO[Long]
  def select: SelectBuilder[JobcandidateFields, JobcandidateRow]
  def selectAll: Stream[ConnectionIO, JobcandidateRow]
  def selectById(jobcandidateid: JobcandidateId): ConnectionIO[Option[JobcandidateRow]]
  def selectByIds(jobcandidateids: Array[JobcandidateId]): Stream[ConnectionIO, JobcandidateRow]
  def update(row: JobcandidateRow): ConnectionIO[Boolean]
  def update: UpdateBuilder[JobcandidateFields, JobcandidateRow]
  def upsert(unsaved: JobcandidateRow): ConnectionIO[JobcandidateRow]
}

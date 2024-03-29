/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package public
package users

import adventureworks.customtypes.TypoUnknownCitext
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.UpdateBuilder
import zio.ZIO
import zio.jdbc.UpdateResult
import zio.jdbc.ZConnection
import zio.stream.ZStream

trait UsersRepo {
  def delete(userId: UsersId): ZIO[ZConnection, Throwable, Boolean]
  def delete: DeleteBuilder[UsersFields, UsersRow]
  def insert(unsaved: UsersRow): ZIO[ZConnection, Throwable, UsersRow]
  def insertStreaming(unsaved: ZStream[ZConnection, Throwable, UsersRow], batchSize: Int): ZIO[ZConnection, Throwable, Long]
  def insert(unsaved: UsersRowUnsaved): ZIO[ZConnection, Throwable, UsersRow]
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  def insertUnsavedStreaming(unsaved: ZStream[ZConnection, Throwable, UsersRowUnsaved], batchSize: Int): ZIO[ZConnection, Throwable, Long]
  def select: SelectBuilder[UsersFields, UsersRow]
  def selectAll: ZStream[ZConnection, Throwable, UsersRow]
  def selectById(userId: UsersId): ZIO[ZConnection, Throwable, Option[UsersRow]]
  def selectByIds(userIds: Array[UsersId]): ZStream[ZConnection, Throwable, UsersRow]
  def selectByUniqueEmail(email: TypoUnknownCitext): ZIO[ZConnection, Throwable, Option[UsersRow]]
  def update(row: UsersRow): ZIO[ZConnection, Throwable, Boolean]
  def update: UpdateBuilder[UsersFields, UsersRow]
  def upsert(unsaved: UsersRow): ZIO[ZConnection, Throwable, UpdateResult[UsersRow]]
}

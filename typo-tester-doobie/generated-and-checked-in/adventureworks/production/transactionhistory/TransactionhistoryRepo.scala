/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package transactionhistory

import doobie.free.connection.ConnectionIO
import fs2.Stream
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.UpdateBuilder

trait TransactionhistoryRepo {
  def delete(transactionid: TransactionhistoryId): ConnectionIO[Boolean]
  def delete: DeleteBuilder[TransactionhistoryFields, TransactionhistoryRow]
  def insert(unsaved: TransactionhistoryRow): ConnectionIO[TransactionhistoryRow]
  def insert(unsaved: TransactionhistoryRowUnsaved): ConnectionIO[TransactionhistoryRow]
  def select: SelectBuilder[TransactionhistoryFields, TransactionhistoryRow]
  def selectAll: Stream[ConnectionIO, TransactionhistoryRow]
  def selectById(transactionid: TransactionhistoryId): ConnectionIO[Option[TransactionhistoryRow]]
  def selectByIds(transactionids: Array[TransactionhistoryId]): Stream[ConnectionIO, TransactionhistoryRow]
  def update(row: TransactionhistoryRow): ConnectionIO[Boolean]
  def update: UpdateBuilder[TransactionhistoryFields, TransactionhistoryRow]
  def upsert(unsaved: TransactionhistoryRow): ConnectionIO[TransactionhistoryRow]
}
/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package transactionhistoryarchive

import doobie.free.connection.ConnectionIO
import fs2.Stream
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.UpdateBuilder

trait TransactionhistoryarchiveRepo {
  def delete(transactionid: TransactionhistoryarchiveId): ConnectionIO[Boolean]
  def delete: DeleteBuilder[TransactionhistoryarchiveFields, TransactionhistoryarchiveRow]
  def insert(unsaved: TransactionhistoryarchiveRow): ConnectionIO[TransactionhistoryarchiveRow]
  def insert(unsaved: TransactionhistoryarchiveRowUnsaved): ConnectionIO[TransactionhistoryarchiveRow]
  def select: SelectBuilder[TransactionhistoryarchiveFields, TransactionhistoryarchiveRow]
  def selectAll: Stream[ConnectionIO, TransactionhistoryarchiveRow]
  def selectById(transactionid: TransactionhistoryarchiveId): ConnectionIO[Option[TransactionhistoryarchiveRow]]
  def selectByIds(transactionids: Array[TransactionhistoryarchiveId]): Stream[ConnectionIO, TransactionhistoryarchiveRow]
  def update(row: TransactionhistoryarchiveRow): ConnectionIO[Boolean]
  def update: UpdateBuilder[TransactionhistoryarchiveFields, TransactionhistoryarchiveRow]
  def upsert(unsaved: TransactionhistoryarchiveRow): ConnectionIO[TransactionhistoryarchiveRow]
}
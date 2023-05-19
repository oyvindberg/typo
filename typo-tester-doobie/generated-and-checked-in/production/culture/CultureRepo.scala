/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package culture

import doobie.free.connection.ConnectionIO
import fs2.Stream

trait CultureRepo {
  def delete(cultureid: CultureId): ConnectionIO[Boolean]
  def insert(unsaved: CultureRow): ConnectionIO[CultureRow]
  def insert(unsaved: CultureRowUnsaved): ConnectionIO[CultureRow]
  def selectAll: Stream[ConnectionIO, CultureRow]
  def selectByFieldValues(fieldValues: List[CultureFieldOrIdValue[_]]): Stream[ConnectionIO, CultureRow]
  def selectById(cultureid: CultureId): ConnectionIO[Option[CultureRow]]
  def selectByIds(cultureids: Array[CultureId]): Stream[ConnectionIO, CultureRow]
  def update(row: CultureRow): ConnectionIO[Boolean]
  def updateFieldValues(cultureid: CultureId, fieldValues: List[CultureFieldValue[_]]): ConnectionIO[Boolean]
  def upsert(unsaved: CultureRow): ConnectionIO[CultureRow]
}
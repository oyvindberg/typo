/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_authid

import doobie.free.connection.ConnectionIO
import fs2.Stream
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.UpdateBuilder

trait PgAuthidRepo {
  def delete(oid: PgAuthidId): ConnectionIO[Boolean]
  def delete: DeleteBuilder[PgAuthidFields, PgAuthidRow]
  def insert(unsaved: PgAuthidRow): ConnectionIO[PgAuthidRow]
  def select: SelectBuilder[PgAuthidFields, PgAuthidRow]
  def selectAll: Stream[ConnectionIO, PgAuthidRow]
  def selectById(oid: PgAuthidId): ConnectionIO[Option[PgAuthidRow]]
  def selectByIds(oids: Array[PgAuthidId]): Stream[ConnectionIO, PgAuthidRow]
  def update(row: PgAuthidRow): ConnectionIO[Boolean]
  def update: UpdateBuilder[PgAuthidFields, PgAuthidRow]
  def upsert(unsaved: PgAuthidRow): ConnectionIO[PgAuthidRow]
}
/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_opclass

import doobie.free.connection.ConnectionIO
import fs2.Stream
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.UpdateBuilder

trait PgOpclassRepo {
  def delete(oid: PgOpclassId): ConnectionIO[Boolean]
  def delete: DeleteBuilder[PgOpclassFields, PgOpclassRow]
  def insert(unsaved: PgOpclassRow): ConnectionIO[PgOpclassRow]
  def select: SelectBuilder[PgOpclassFields, PgOpclassRow]
  def selectAll: Stream[ConnectionIO, PgOpclassRow]
  def selectById(oid: PgOpclassId): ConnectionIO[Option[PgOpclassRow]]
  def selectByIds(oids: Array[PgOpclassId]): Stream[ConnectionIO, PgOpclassRow]
  def update(row: PgOpclassRow): ConnectionIO[Boolean]
  def update: UpdateBuilder[PgOpclassFields, PgOpclassRow]
  def upsert(unsaved: PgOpclassRow): ConnectionIO[PgOpclassRow]
}
/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_ts_template

import doobie.free.connection.ConnectionIO
import fs2.Stream
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.UpdateBuilder

trait PgTsTemplateRepo {
  def delete(oid: PgTsTemplateId): ConnectionIO[Boolean]
  def delete: DeleteBuilder[PgTsTemplateFields, PgTsTemplateRow]
  def insert(unsaved: PgTsTemplateRow): ConnectionIO[PgTsTemplateRow]
  def select: SelectBuilder[PgTsTemplateFields, PgTsTemplateRow]
  def selectAll: Stream[ConnectionIO, PgTsTemplateRow]
  def selectById(oid: PgTsTemplateId): ConnectionIO[Option[PgTsTemplateRow]]
  def selectByIds(oids: Array[PgTsTemplateId]): Stream[ConnectionIO, PgTsTemplateRow]
  def update(row: PgTsTemplateRow): ConnectionIO[Boolean]
  def update: UpdateBuilder[PgTsTemplateFields, PgTsTemplateRow]
  def upsert(unsaved: PgTsTemplateRow): ConnectionIO[PgTsTemplateRow]
}
/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_transform

import doobie.free.connection.ConnectionIO
import fs2.Stream
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.UpdateBuilder

trait PgTransformRepo {
  def delete(oid: PgTransformId): ConnectionIO[Boolean]
  def delete: DeleteBuilder[PgTransformFields, PgTransformRow]
  def insert(unsaved: PgTransformRow): ConnectionIO[PgTransformRow]
  def select: SelectBuilder[PgTransformFields, PgTransformRow]
  def selectAll: Stream[ConnectionIO, PgTransformRow]
  def selectById(oid: PgTransformId): ConnectionIO[Option[PgTransformRow]]
  def selectByIds(oids: Array[PgTransformId]): Stream[ConnectionIO, PgTransformRow]
  def selectByUnique(trftype: /* oid */ Long, trflang: /* oid */ Long): ConnectionIO[Option[PgTransformRow]]
  def update(row: PgTransformRow): ConnectionIO[Boolean]
  def update: UpdateBuilder[PgTransformFields, PgTransformRow]
  def upsert(unsaved: PgTransformRow): ConnectionIO[PgTransformRow]
}
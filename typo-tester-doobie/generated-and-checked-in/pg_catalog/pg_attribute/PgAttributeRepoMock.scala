/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_attribute

import doobie.free.connection.ConnectionIO
import doobie.free.connection.delay
import fs2.Stream
import typo.dsl.DeleteBuilder
import typo.dsl.DeleteBuilder.DeleteBuilderMock
import typo.dsl.DeleteParams
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderMock
import typo.dsl.SelectParams
import typo.dsl.UpdateBuilder
import typo.dsl.UpdateBuilder.UpdateBuilderMock
import typo.dsl.UpdateParams

class PgAttributeRepoMock(map: scala.collection.mutable.Map[PgAttributeId, PgAttributeRow] = scala.collection.mutable.Map.empty) extends PgAttributeRepo {
  override def delete(compositeId: PgAttributeId): ConnectionIO[Boolean] = {
    delay(map.remove(compositeId).isDefined)
  }
  override def delete: DeleteBuilder[PgAttributeFields, PgAttributeRow] = {
    DeleteBuilderMock(DeleteParams.empty, PgAttributeFields, map)
  }
  override def insert(unsaved: PgAttributeRow): ConnectionIO[PgAttributeRow] = {
    delay {
      if (map.contains(unsaved.compositeId))
        sys.error(s"id ${unsaved.compositeId} already exists")
      else
        map.put(unsaved.compositeId, unsaved)
      unsaved
    }
  }
  override def select: SelectBuilder[PgAttributeFields, PgAttributeRow] = {
    SelectBuilderMock(PgAttributeFields, delay(map.values.toList), SelectParams.empty)
  }
  override def selectAll: Stream[ConnectionIO, PgAttributeRow] = {
    Stream.emits(map.values.toList)
  }
  override def selectById(compositeId: PgAttributeId): ConnectionIO[Option[PgAttributeRow]] = {
    delay(map.get(compositeId))
  }
  override def update(row: PgAttributeRow): ConnectionIO[Boolean] = {
    delay {
      map.get(row.compositeId) match {
        case Some(`row`) => false
        case Some(_) =>
          map.put(row.compositeId, row)
          true
        case None => false
      }
    }
  }
  override def update: UpdateBuilder[PgAttributeFields, PgAttributeRow] = {
    UpdateBuilderMock(UpdateParams.empty, PgAttributeFields, map)
  }
  override def upsert(unsaved: PgAttributeRow): ConnectionIO[PgAttributeRow] = {
    delay {
      map.put(unsaved.compositeId, unsaved)
      unsaved
    }
  }
}
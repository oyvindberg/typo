/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_authid

import doobie.free.connection.ConnectionIO
import doobie.free.connection.delay
import fs2.Stream
import scala.annotation.nowarn
import typo.dsl.DeleteBuilder
import typo.dsl.DeleteBuilder.DeleteBuilderMock
import typo.dsl.DeleteParams
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderMock
import typo.dsl.SelectParams
import typo.dsl.UpdateBuilder
import typo.dsl.UpdateBuilder.UpdateBuilderMock
import typo.dsl.UpdateParams

class PgAuthidRepoMock(map: scala.collection.mutable.Map[PgAuthidId, PgAuthidRow] = scala.collection.mutable.Map.empty) extends PgAuthidRepo {
  override def delete(oid: PgAuthidId): ConnectionIO[Boolean] = {
    delay(map.remove(oid).isDefined)
  }
  override def delete: DeleteBuilder[PgAuthidFields, PgAuthidRow] = {
    DeleteBuilderMock(DeleteParams.empty, PgAuthidFields, map)
  }
  override def insert(unsaved: PgAuthidRow): ConnectionIO[PgAuthidRow] = {
    delay {
      val _ = if (map.contains(unsaved.oid))
        sys.error(s"id ${unsaved.oid} already exists")
      else
        map.put(unsaved.oid, unsaved)
    
      unsaved
    }
  }
  override def select: SelectBuilder[PgAuthidFields, PgAuthidRow] = {
    SelectBuilderMock(PgAuthidFields, delay(map.values.toList), SelectParams.empty)
  }
  override def selectAll: Stream[ConnectionIO, PgAuthidRow] = {
    Stream.emits(map.values.toList)
  }
  override def selectById(oid: PgAuthidId): ConnectionIO[Option[PgAuthidRow]] = {
    delay(map.get(oid))
  }
  override def selectByIds(oids: Array[PgAuthidId]): Stream[ConnectionIO, PgAuthidRow] = {
    Stream.emits(oids.flatMap(map.get).toList)
  }
  override def selectByUnique(rolname: String): ConnectionIO[Option[PgAuthidRow]] = {
    delay(map.values.find(v => rolname == v.rolname))
  }
  override def update(row: PgAuthidRow): ConnectionIO[Boolean] = {
    delay {
      map.get(row.oid) match {
        case Some(`row`) => false
        case Some(_) =>
          map.put(row.oid, row): @nowarn
          true
        case None => false
      }
    }
  }
  override def update: UpdateBuilder[PgAuthidFields, PgAuthidRow] = {
    UpdateBuilderMock(UpdateParams.empty, PgAuthidFields, map)
  }
  override def upsert(unsaved: PgAuthidRow): ConnectionIO[PgAuthidRow] = {
    delay {
      map.put(unsaved.oid, unsaved): @nowarn
      unsaved
    }
  }
}
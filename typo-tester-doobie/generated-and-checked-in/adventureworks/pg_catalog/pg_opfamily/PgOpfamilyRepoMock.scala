/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_opfamily

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

class PgOpfamilyRepoMock(map: scala.collection.mutable.Map[PgOpfamilyId, PgOpfamilyRow] = scala.collection.mutable.Map.empty) extends PgOpfamilyRepo {
  override def delete(oid: PgOpfamilyId): ConnectionIO[Boolean] = {
    delay(map.remove(oid).isDefined)
  }
  override def delete: DeleteBuilder[PgOpfamilyFields, PgOpfamilyRow] = {
    DeleteBuilderMock(DeleteParams.empty, PgOpfamilyFields, map)
  }
  override def insert(unsaved: PgOpfamilyRow): ConnectionIO[PgOpfamilyRow] = {
    delay {
      val _ = if (map.contains(unsaved.oid))
        sys.error(s"id ${unsaved.oid} already exists")
      else
        map.put(unsaved.oid, unsaved)
    
      unsaved
    }
  }
  override def select: SelectBuilder[PgOpfamilyFields, PgOpfamilyRow] = {
    SelectBuilderMock(PgOpfamilyFields, delay(map.values.toList), SelectParams.empty)
  }
  override def selectAll: Stream[ConnectionIO, PgOpfamilyRow] = {
    Stream.emits(map.values.toList)
  }
  override def selectById(oid: PgOpfamilyId): ConnectionIO[Option[PgOpfamilyRow]] = {
    delay(map.get(oid))
  }
  override def selectByIds(oids: Array[PgOpfamilyId]): Stream[ConnectionIO, PgOpfamilyRow] = {
    Stream.emits(oids.flatMap(map.get).toList)
  }
  override def selectByUnique(opfmethod: /* oid */ Long, opfname: String, opfnamespace: /* oid */ Long): ConnectionIO[Option[PgOpfamilyRow]] = {
    delay(map.values.find(v => opfmethod == v.opfmethod && opfname == v.opfname && opfnamespace == v.opfnamespace))
  }
  override def update(row: PgOpfamilyRow): ConnectionIO[Boolean] = {
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
  override def update: UpdateBuilder[PgOpfamilyFields, PgOpfamilyRow] = {
    UpdateBuilderMock(UpdateParams.empty, PgOpfamilyFields, map)
  }
  override def upsert(unsaved: PgOpfamilyRow): ConnectionIO[PgOpfamilyRow] = {
    delay {
      map.put(unsaved.oid, unsaved): @nowarn
      unsaved
    }
  }
}
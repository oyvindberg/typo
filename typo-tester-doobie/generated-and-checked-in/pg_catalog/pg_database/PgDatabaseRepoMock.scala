/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_database

import doobie.free.connection.ConnectionIO
import doobie.free.connection.delay
import fs2.Stream

class PgDatabaseRepoMock(map: scala.collection.mutable.Map[PgDatabaseId, PgDatabaseRow] = scala.collection.mutable.Map.empty) extends PgDatabaseRepo {
  override def delete(oid: PgDatabaseId): ConnectionIO[Boolean] = {
    delay(map.remove(oid).isDefined)
  }
  override def insert(unsaved: PgDatabaseRow): ConnectionIO[PgDatabaseRow] = {
    delay {
      if (map.contains(unsaved.oid))
        sys.error(s"id ${unsaved.oid} already exists")
      else
        map.put(unsaved.oid, unsaved)
      unsaved
    }
  }
  override def selectAll: Stream[ConnectionIO, PgDatabaseRow] = {
    Stream.emits(map.values.toList)
  }
  override def selectById(oid: PgDatabaseId): ConnectionIO[Option[PgDatabaseRow]] = {
    delay(map.get(oid))
  }
  override def selectByIds(oids: Array[PgDatabaseId]): Stream[ConnectionIO, PgDatabaseRow] = {
    Stream.emits(oids.flatMap(map.get).toList)
  }
  override def update(row: PgDatabaseRow): ConnectionIO[Boolean] = {
    delay {
      map.get(row.oid) match {
        case Some(`row`) => false
        case Some(_) =>
          map.put(row.oid, row)
          true
        case None => false
      }
    }
  }
  override def upsert(unsaved: PgDatabaseRow): ConnectionIO[PgDatabaseRow] = {
    delay {
      map.put(unsaved.oid, unsaved)
      unsaved
    }
  }
}
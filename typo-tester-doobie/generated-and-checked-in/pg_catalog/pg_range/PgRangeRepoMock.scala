/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_range

import doobie.free.connection.ConnectionIO
import doobie.free.connection.delay
import fs2.Stream

class PgRangeRepoMock(map: scala.collection.mutable.Map[PgRangeId, PgRangeRow] = scala.collection.mutable.Map.empty) extends PgRangeRepo {
  override def delete(rngtypid: PgRangeId): ConnectionIO[Boolean] = {
    delay(map.remove(rngtypid).isDefined)
  }
  override def insert(unsaved: PgRangeRow): ConnectionIO[PgRangeRow] = {
    delay {
      if (map.contains(unsaved.rngtypid))
        sys.error(s"id ${unsaved.rngtypid} already exists")
      else
        map.put(unsaved.rngtypid, unsaved)
      unsaved
    }
  }
  override def selectAll: Stream[ConnectionIO, PgRangeRow] = {
    Stream.emits(map.values.toList)
  }
  override def selectById(rngtypid: PgRangeId): ConnectionIO[Option[PgRangeRow]] = {
    delay(map.get(rngtypid))
  }
  override def selectByIds(rngtypids: Array[PgRangeId]): Stream[ConnectionIO, PgRangeRow] = {
    Stream.emits(rngtypids.flatMap(map.get).toList)
  }
  override def update(row: PgRangeRow): ConnectionIO[Boolean] = {
    delay {
      map.get(row.rngtypid) match {
        case Some(`row`) => false
        case Some(_) =>
          map.put(row.rngtypid, row)
          true
        case None => false
      }
    }
  }
  override def upsert(unsaved: PgRangeRow): ConnectionIO[PgRangeRow] = {
    delay {
      map.put(unsaved.rngtypid, unsaved)
      unsaved
    }
  }
}
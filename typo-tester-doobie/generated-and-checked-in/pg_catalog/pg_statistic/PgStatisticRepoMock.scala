/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_statistic

import doobie.free.connection.ConnectionIO
import doobie.free.connection.delay
import fs2.Stream

class PgStatisticRepoMock(map: scala.collection.mutable.Map[PgStatisticId, PgStatisticRow] = scala.collection.mutable.Map.empty) extends PgStatisticRepo {
  override def delete(compositeId: PgStatisticId): ConnectionIO[Boolean] = {
    delay(map.remove(compositeId).isDefined)
  }
  override def insert(unsaved: PgStatisticRow): ConnectionIO[PgStatisticRow] = {
    delay {
      if (map.contains(unsaved.compositeId))
        sys.error(s"id ${unsaved.compositeId} already exists")
      else
        map.put(unsaved.compositeId, unsaved)
      unsaved
    }
  }
  override def selectAll: Stream[ConnectionIO, PgStatisticRow] = {
    Stream.emits(map.values.toList)
  }
  override def selectById(compositeId: PgStatisticId): ConnectionIO[Option[PgStatisticRow]] = {
    delay(map.get(compositeId))
  }
  override def update(row: PgStatisticRow): ConnectionIO[Boolean] = {
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
  override def upsert(unsaved: PgStatisticRow): ConnectionIO[PgStatisticRow] = {
    delay {
      map.put(unsaved.compositeId, unsaved)
      unsaved
    }
  }
}
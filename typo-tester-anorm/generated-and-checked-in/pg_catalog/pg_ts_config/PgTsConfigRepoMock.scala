/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_ts_config

import java.sql.Connection

class PgTsConfigRepoMock(map: scala.collection.mutable.Map[PgTsConfigId, PgTsConfigRow] = scala.collection.mutable.Map.empty) extends PgTsConfigRepo {
  override def delete(oid: PgTsConfigId)(implicit c: Connection): Boolean = {
    map.remove(oid).isDefined
  }
  override def insert(unsaved: PgTsConfigRow)(implicit c: Connection): PgTsConfigRow = {
    if (map.contains(unsaved.oid))
      sys.error(s"id ${unsaved.oid} already exists")
    else
      map.put(unsaved.oid, unsaved)
    unsaved
  }
  override def selectAll(implicit c: Connection): List[PgTsConfigRow] = {
    map.values.toList
  }
  override def selectById(oid: PgTsConfigId)(implicit c: Connection): Option[PgTsConfigRow] = {
    map.get(oid)
  }
  override def selectByIds(oids: Array[PgTsConfigId])(implicit c: Connection): List[PgTsConfigRow] = {
    oids.flatMap(map.get).toList
  }
  override def update(row: PgTsConfigRow)(implicit c: Connection): Boolean = {
    map.get(row.oid) match {
      case Some(`row`) => false
      case Some(_) =>
        map.put(row.oid, row)
        true
      case None => false
    }
  }
  override def upsert(unsaved: PgTsConfigRow)(implicit c: Connection): PgTsConfigRow = {
    map.put(unsaved.oid, unsaved)
    unsaved
  }
}
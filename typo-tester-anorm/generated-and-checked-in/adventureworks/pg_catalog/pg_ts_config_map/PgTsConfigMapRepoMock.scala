/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_ts_config_map

import java.sql.Connection
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

class PgTsConfigMapRepoMock(map: scala.collection.mutable.Map[PgTsConfigMapId, PgTsConfigMapRow] = scala.collection.mutable.Map.empty) extends PgTsConfigMapRepo {
  override def delete(compositeId: PgTsConfigMapId)(implicit c: Connection): Boolean = {
    map.remove(compositeId).isDefined
  }
  override def delete: DeleteBuilder[PgTsConfigMapFields, PgTsConfigMapRow] = {
    DeleteBuilderMock(DeleteParams.empty, PgTsConfigMapFields, map)
  }
  override def insert(unsaved: PgTsConfigMapRow)(implicit c: Connection): PgTsConfigMapRow = {
    val _ = if (map.contains(unsaved.compositeId))
      sys.error(s"id ${unsaved.compositeId} already exists")
    else
      map.put(unsaved.compositeId, unsaved)
    
    unsaved
  }
  override def select: SelectBuilder[PgTsConfigMapFields, PgTsConfigMapRow] = {
    SelectBuilderMock(PgTsConfigMapFields, () => map.values.toList, SelectParams.empty)
  }
  override def selectAll(implicit c: Connection): List[PgTsConfigMapRow] = {
    map.values.toList
  }
  override def selectById(compositeId: PgTsConfigMapId)(implicit c: Connection): Option[PgTsConfigMapRow] = {
    map.get(compositeId)
  }
  override def update(row: PgTsConfigMapRow)(implicit c: Connection): Boolean = {
    map.get(row.compositeId) match {
      case Some(`row`) => false
      case Some(_) =>
        map.put(row.compositeId, row): @nowarn
        true
      case None => false
    }
  }
  override def update: UpdateBuilder[PgTsConfigMapFields, PgTsConfigMapRow] = {
    UpdateBuilderMock(UpdateParams.empty, PgTsConfigMapFields, map)
  }
  override def upsert(unsaved: PgTsConfigMapRow)(implicit c: Connection): PgTsConfigMapRow = {
    map.put(unsaved.compositeId, unsaved): @nowarn
    unsaved
  }
}
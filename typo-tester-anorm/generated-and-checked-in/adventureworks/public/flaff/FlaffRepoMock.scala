/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package public
package flaff

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

class FlaffRepoMock(map: scala.collection.mutable.Map[FlaffId, FlaffRow] = scala.collection.mutable.Map.empty) extends FlaffRepo {
  override def delete: DeleteBuilder[FlaffFields, FlaffRow] = {
    DeleteBuilderMock(DeleteParams.empty, FlaffFields.structure, map)
  }
  override def deleteById(compositeId: FlaffId)(implicit c: Connection): Boolean = {
    map.remove(compositeId).isDefined
  }
  override def deleteByIds(compositeIds: Array[FlaffId])(implicit c: Connection): Int = {
    compositeIds.map(id => map.remove(id)).count(_.isDefined)
  }
  override def insert(unsaved: FlaffRow)(implicit c: Connection): FlaffRow = {
    val _ = if (map.contains(unsaved.compositeId))
      sys.error(s"id ${unsaved.compositeId} already exists")
    else
      map.put(unsaved.compositeId, unsaved)
    
    unsaved
  }
  override def insertStreaming(unsaved: Iterator[FlaffRow], batchSize: Int = 10000)(implicit c: Connection): Long = {
    unsaved.foreach { row =>
      map += (row.compositeId -> row)
    }
    unsaved.size.toLong
  }
  override def select: SelectBuilder[FlaffFields, FlaffRow] = {
    SelectBuilderMock(FlaffFields.structure, () => map.values.toList, SelectParams.empty)
  }
  override def selectAll(implicit c: Connection): List[FlaffRow] = {
    map.values.toList
  }
  override def selectById(compositeId: FlaffId)(implicit c: Connection): Option[FlaffRow] = {
    map.get(compositeId)
  }
  override def selectByIds(compositeIds: Array[FlaffId])(implicit c: Connection): List[FlaffRow] = {
    compositeIds.flatMap(map.get).toList
  }
  override def selectByIdsTracked(compositeIds: Array[FlaffId])(implicit c: Connection): Map[FlaffId, FlaffRow] = {
    val byId = selectByIds(compositeIds).view.map(x => (x.compositeId, x)).toMap
    compositeIds.view.flatMap(id => byId.get(id).map(x => (id, x))).toMap
  }
  override def update: UpdateBuilder[FlaffFields, FlaffRow] = {
    UpdateBuilderMock(UpdateParams.empty, FlaffFields.structure, map)
  }
  override def update(row: FlaffRow)(implicit c: Connection): Option[FlaffRow] = {
    map.get(row.compositeId).map { _ =>
      map.put(row.compositeId, row): @nowarn
      row
    }
  }
  override def upsert(unsaved: FlaffRow)(implicit c: Connection): FlaffRow = {
    map.put(unsaved.compositeId, unsaved): @nowarn
    unsaved
  }
  override def upsertBatch(unsaved: Iterable[FlaffRow])(implicit c: Connection): List[FlaffRow] = {
    unsaved.map { row =>
      map += (row.compositeId -> row)
      row
    }.toList
  }
  /* NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements */
  override def upsertStreaming(unsaved: Iterator[FlaffRow], batchSize: Int = 10000)(implicit c: Connection): Int = {
    unsaved.foreach { row =>
      map += (row.compositeId -> row)
    }
    unsaved.size
  }
}

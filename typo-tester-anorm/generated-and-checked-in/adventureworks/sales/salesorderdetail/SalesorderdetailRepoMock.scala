/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package salesorderdetail

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

class SalesorderdetailRepoMock(toRow: Function1[SalesorderdetailRowUnsaved, SalesorderdetailRow],
                               map: scala.collection.mutable.Map[SalesorderdetailId, SalesorderdetailRow] = scala.collection.mutable.Map.empty) extends SalesorderdetailRepo {
  override def delete: DeleteBuilder[SalesorderdetailFields, SalesorderdetailRow] = {
    DeleteBuilderMock(DeleteParams.empty, SalesorderdetailFields.structure, map)
  }
  override def deleteById(compositeId: SalesorderdetailId)(implicit c: Connection): Boolean = {
    map.remove(compositeId).isDefined
  }
  override def deleteByIds(compositeIds: Array[SalesorderdetailId])(implicit c: Connection): Int = {
    compositeIds.map(id => map.remove(id)).count(_.isDefined)
  }
  override def insert(unsaved: SalesorderdetailRow)(implicit c: Connection): SalesorderdetailRow = {
    val _ = if (map.contains(unsaved.compositeId))
      sys.error(s"id ${unsaved.compositeId} already exists")
    else
      map.put(unsaved.compositeId, unsaved)
    
    unsaved
  }
  override def insert(unsaved: SalesorderdetailRowUnsaved)(implicit c: Connection): SalesorderdetailRow = {
    insert(toRow(unsaved))
  }
  override def insertStreaming(unsaved: Iterator[SalesorderdetailRow], batchSize: Int = 10000)(implicit c: Connection): Long = {
    unsaved.foreach { row =>
      map += (row.compositeId -> row)
    }
    unsaved.size.toLong
  }
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  override def insertUnsavedStreaming(unsaved: Iterator[SalesorderdetailRowUnsaved], batchSize: Int = 10000)(implicit c: Connection): Long = {
    unsaved.foreach { unsavedRow =>
      val row = toRow(unsavedRow)
      map += (row.compositeId -> row)
    }
    unsaved.size.toLong
  }
  override def select: SelectBuilder[SalesorderdetailFields, SalesorderdetailRow] = {
    SelectBuilderMock(SalesorderdetailFields.structure, () => map.values.toList, SelectParams.empty)
  }
  override def selectAll(implicit c: Connection): List[SalesorderdetailRow] = {
    map.values.toList
  }
  override def selectById(compositeId: SalesorderdetailId)(implicit c: Connection): Option[SalesorderdetailRow] = {
    map.get(compositeId)
  }
  override def selectByIds(compositeIds: Array[SalesorderdetailId])(implicit c: Connection): List[SalesorderdetailRow] = {
    compositeIds.flatMap(map.get).toList
  }
  override def selectByIdsTracked(compositeIds: Array[SalesorderdetailId])(implicit c: Connection): Map[SalesorderdetailId, SalesorderdetailRow] = {
    val byId = selectByIds(compositeIds).view.map(x => (x.compositeId, x)).toMap
    compositeIds.view.flatMap(id => byId.get(id).map(x => (id, x))).toMap
  }
  override def update: UpdateBuilder[SalesorderdetailFields, SalesorderdetailRow] = {
    UpdateBuilderMock(UpdateParams.empty, SalesorderdetailFields.structure, map)
  }
  override def update(row: SalesorderdetailRow)(implicit c: Connection): Option[SalesorderdetailRow] = {
    map.get(row.compositeId).map { _ =>
      map.put(row.compositeId, row): @nowarn
      row
    }
  }
  override def upsert(unsaved: SalesorderdetailRow)(implicit c: Connection): SalesorderdetailRow = {
    map.put(unsaved.compositeId, unsaved): @nowarn
    unsaved
  }
  override def upsertBatch(unsaved: Iterable[SalesorderdetailRow])(implicit c: Connection): List[SalesorderdetailRow] = {
    unsaved.map { row =>
      map += (row.compositeId -> row)
      row
    }.toList
  }
  /* NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements */
  override def upsertStreaming(unsaved: Iterator[SalesorderdetailRow], batchSize: Int = 10000)(implicit c: Connection): Int = {
    unsaved.foreach { row =>
      map += (row.compositeId -> row)
    }
    unsaved.size
  }
}

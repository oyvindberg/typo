/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package salesreason

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

class SalesreasonRepoMock(toRow: Function1[SalesreasonRowUnsaved, SalesreasonRow],
                          map: scala.collection.mutable.Map[SalesreasonId, SalesreasonRow] = scala.collection.mutable.Map.empty) extends SalesreasonRepo {
  override def delete(salesreasonid: SalesreasonId)(implicit c: Connection): Boolean = {
    map.remove(salesreasonid).isDefined
  }
  override def delete: DeleteBuilder[SalesreasonFields, SalesreasonRow] = {
    DeleteBuilderMock(DeleteParams.empty, SalesreasonFields, map)
  }
  override def insert(unsaved: SalesreasonRow)(implicit c: Connection): SalesreasonRow = {
    val _ = if (map.contains(unsaved.salesreasonid))
      sys.error(s"id ${unsaved.salesreasonid} already exists")
    else
      map.put(unsaved.salesreasonid, unsaved)
    
    unsaved
  }
  override def insert(unsaved: SalesreasonRowUnsaved)(implicit c: Connection): SalesreasonRow = {
    insert(toRow(unsaved))
  }
  override def select: SelectBuilder[SalesreasonFields, SalesreasonRow] = {
    SelectBuilderMock(SalesreasonFields, () => map.values.toList, SelectParams.empty)
  }
  override def selectAll(implicit c: Connection): List[SalesreasonRow] = {
    map.values.toList
  }
  override def selectById(salesreasonid: SalesreasonId)(implicit c: Connection): Option[SalesreasonRow] = {
    map.get(salesreasonid)
  }
  override def selectByIds(salesreasonids: Array[SalesreasonId])(implicit c: Connection): List[SalesreasonRow] = {
    salesreasonids.flatMap(map.get).toList
  }
  override def update(row: SalesreasonRow)(implicit c: Connection): Boolean = {
    map.get(row.salesreasonid) match {
      case Some(`row`) => false
      case Some(_) =>
        map.put(row.salesreasonid, row): @nowarn
        true
      case None => false
    }
  }
  override def update: UpdateBuilder[SalesreasonFields, SalesreasonRow] = {
    UpdateBuilderMock(UpdateParams.empty, SalesreasonFields, map)
  }
  override def upsert(unsaved: SalesreasonRow)(implicit c: Connection): SalesreasonRow = {
    map.put(unsaved.salesreasonid, unsaved): @nowarn
    unsaved
  }
}
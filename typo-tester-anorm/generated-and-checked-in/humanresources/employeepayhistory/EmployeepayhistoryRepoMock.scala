/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package humanresources
package employeepayhistory

import java.sql.Connection
import typo.dsl.DeleteBuilder
import typo.dsl.DeleteBuilder.DeleteBuilderMock
import typo.dsl.DeleteParams
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderMock
import typo.dsl.SelectParams
import typo.dsl.UpdateBuilder
import typo.dsl.UpdateBuilder.UpdateBuilderMock
import typo.dsl.UpdateParams

class EmployeepayhistoryRepoMock(toRow: Function1[EmployeepayhistoryRowUnsaved, EmployeepayhistoryRow],
                                 map: scala.collection.mutable.Map[EmployeepayhistoryId, EmployeepayhistoryRow] = scala.collection.mutable.Map.empty) extends EmployeepayhistoryRepo {
  override def delete(compositeId: EmployeepayhistoryId)(implicit c: Connection): Boolean = {
    map.remove(compositeId).isDefined
  }
  override def delete: DeleteBuilder[EmployeepayhistoryFields, EmployeepayhistoryRow] = {
    DeleteBuilderMock(DeleteParams.empty, EmployeepayhistoryFields, map)
  }
  override def insert(unsaved: EmployeepayhistoryRow)(implicit c: Connection): EmployeepayhistoryRow = {
    if (map.contains(unsaved.compositeId))
      sys.error(s"id ${unsaved.compositeId} already exists")
    else
      map.put(unsaved.compositeId, unsaved)
    unsaved
  }
  override def insert(unsaved: EmployeepayhistoryRowUnsaved)(implicit c: Connection): EmployeepayhistoryRow = {
    insert(toRow(unsaved))
  }
  override def select: SelectBuilder[EmployeepayhistoryFields, EmployeepayhistoryRow] = {
    SelectBuilderMock(EmployeepayhistoryFields, () => map.values.toList, SelectParams.empty)
  }
  override def selectAll(implicit c: Connection): List[EmployeepayhistoryRow] = {
    map.values.toList
  }
  override def selectById(compositeId: EmployeepayhistoryId)(implicit c: Connection): Option[EmployeepayhistoryRow] = {
    map.get(compositeId)
  }
  override def update(row: EmployeepayhistoryRow)(implicit c: Connection): Boolean = {
    map.get(row.compositeId) match {
      case Some(`row`) => false
      case Some(_) =>
        map.put(row.compositeId, row)
        true
      case None => false
    }
  }
  override def update: UpdateBuilder[EmployeepayhistoryFields, EmployeepayhistoryRow] = {
    UpdateBuilderMock(UpdateParams.empty, EmployeepayhistoryFields, map)
  }
  override def upsert(unsaved: EmployeepayhistoryRow)(implicit c: Connection): EmployeepayhistoryRow = {
    map.put(unsaved.compositeId, unsaved)
    unsaved
  }
}
/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package personphone

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

class PersonphoneRepoMock(toRow: Function1[PersonphoneRowUnsaved, PersonphoneRow],
                          map: scala.collection.mutable.Map[PersonphoneId, PersonphoneRow] = scala.collection.mutable.Map.empty) extends PersonphoneRepo {
  override def delete(compositeId: PersonphoneId)(implicit c: Connection): Boolean = {
    map.remove(compositeId).isDefined
  }
  override def delete: DeleteBuilder[PersonphoneFields, PersonphoneRow] = {
    DeleteBuilderMock(DeleteParams.empty, PersonphoneFields, map)
  }
  override def insert(unsaved: PersonphoneRow)(implicit c: Connection): PersonphoneRow = {
    val _ = if (map.contains(unsaved.compositeId))
      sys.error(s"id ${unsaved.compositeId} already exists")
    else
      map.put(unsaved.compositeId, unsaved)
    
    unsaved
  }
  override def insert(unsaved: PersonphoneRowUnsaved)(implicit c: Connection): PersonphoneRow = {
    insert(toRow(unsaved))
  }
  override def select: SelectBuilder[PersonphoneFields, PersonphoneRow] = {
    SelectBuilderMock(PersonphoneFields, () => map.values.toList, SelectParams.empty)
  }
  override def selectAll(implicit c: Connection): List[PersonphoneRow] = {
    map.values.toList
  }
  override def selectById(compositeId: PersonphoneId)(implicit c: Connection): Option[PersonphoneRow] = {
    map.get(compositeId)
  }
  override def update(row: PersonphoneRow)(implicit c: Connection): Boolean = {
    map.get(row.compositeId) match {
      case Some(`row`) => false
      case Some(_) =>
        map.put(row.compositeId, row): @nowarn
        true
      case None => false
    }
  }
  override def update: UpdateBuilder[PersonphoneFields, PersonphoneRow] = {
    UpdateBuilderMock(UpdateParams.empty, PersonphoneFields, map)
  }
  override def upsert(unsaved: PersonphoneRow)(implicit c: Connection): PersonphoneRow = {
    map.put(unsaved.compositeId, unsaved): @nowarn
    unsaved
  }
}
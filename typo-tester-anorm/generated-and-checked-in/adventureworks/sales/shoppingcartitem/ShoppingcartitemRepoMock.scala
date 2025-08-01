/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package shoppingcartitem

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

class ShoppingcartitemRepoMock(toRow: Function1[ShoppingcartitemRowUnsaved, ShoppingcartitemRow],
                               map: scala.collection.mutable.Map[ShoppingcartitemId, ShoppingcartitemRow] = scala.collection.mutable.Map.empty) extends ShoppingcartitemRepo {
  override def delete: DeleteBuilder[ShoppingcartitemFields, ShoppingcartitemRow] = {
    DeleteBuilderMock(DeleteParams.empty, ShoppingcartitemFields.structure, map)
  }
  override def deleteById(shoppingcartitemid: ShoppingcartitemId)(implicit c: Connection): Boolean = {
    map.remove(shoppingcartitemid).isDefined
  }
  override def deleteByIds(shoppingcartitemids: Array[ShoppingcartitemId])(implicit c: Connection): Int = {
    shoppingcartitemids.map(id => map.remove(id)).count(_.isDefined)
  }
  override def insert(unsaved: ShoppingcartitemRow)(implicit c: Connection): ShoppingcartitemRow = {
    val _ = if (map.contains(unsaved.shoppingcartitemid))
      sys.error(s"id ${unsaved.shoppingcartitemid} already exists")
    else
      map.put(unsaved.shoppingcartitemid, unsaved)
    
    unsaved
  }
  override def insert(unsaved: ShoppingcartitemRowUnsaved)(implicit c: Connection): ShoppingcartitemRow = {
    insert(toRow(unsaved))
  }
  override def insertStreaming(unsaved: Iterator[ShoppingcartitemRow], batchSize: Int = 10000)(implicit c: Connection): Long = {
    unsaved.foreach { row =>
      map += (row.shoppingcartitemid -> row)
    }
    unsaved.size.toLong
  }
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  override def insertUnsavedStreaming(unsaved: Iterator[ShoppingcartitemRowUnsaved], batchSize: Int = 10000)(implicit c: Connection): Long = {
    unsaved.foreach { unsavedRow =>
      val row = toRow(unsavedRow)
      map += (row.shoppingcartitemid -> row)
    }
    unsaved.size.toLong
  }
  override def select: SelectBuilder[ShoppingcartitemFields, ShoppingcartitemRow] = {
    SelectBuilderMock(ShoppingcartitemFields.structure, () => map.values.toList, SelectParams.empty)
  }
  override def selectAll(implicit c: Connection): List[ShoppingcartitemRow] = {
    map.values.toList
  }
  override def selectById(shoppingcartitemid: ShoppingcartitemId)(implicit c: Connection): Option[ShoppingcartitemRow] = {
    map.get(shoppingcartitemid)
  }
  override def selectByIds(shoppingcartitemids: Array[ShoppingcartitemId])(implicit c: Connection): List[ShoppingcartitemRow] = {
    shoppingcartitemids.flatMap(map.get).toList
  }
  override def selectByIdsTracked(shoppingcartitemids: Array[ShoppingcartitemId])(implicit c: Connection): Map[ShoppingcartitemId, ShoppingcartitemRow] = {
    val byId = selectByIds(shoppingcartitemids).view.map(x => (x.shoppingcartitemid, x)).toMap
    shoppingcartitemids.view.flatMap(id => byId.get(id).map(x => (id, x))).toMap
  }
  override def update: UpdateBuilder[ShoppingcartitemFields, ShoppingcartitemRow] = {
    UpdateBuilderMock(UpdateParams.empty, ShoppingcartitemFields.structure, map)
  }
  override def update(row: ShoppingcartitemRow)(implicit c: Connection): Option[ShoppingcartitemRow] = {
    map.get(row.shoppingcartitemid).map { _ =>
      map.put(row.shoppingcartitemid, row): @nowarn
      row
    }
  }
  override def upsert(unsaved: ShoppingcartitemRow)(implicit c: Connection): ShoppingcartitemRow = {
    map.put(unsaved.shoppingcartitemid, unsaved): @nowarn
    unsaved
  }
  override def upsertBatch(unsaved: Iterable[ShoppingcartitemRow])(implicit c: Connection): List[ShoppingcartitemRow] = {
    unsaved.map { row =>
      map += (row.shoppingcartitemid -> row)
      row
    }.toList
  }
  /* NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements */
  override def upsertStreaming(unsaved: Iterator[ShoppingcartitemRow], batchSize: Int = 10000)(implicit c: Connection): Int = {
    unsaved.foreach { row =>
      map += (row.shoppingcartitemid -> row)
    }
    unsaved.size
  }
}

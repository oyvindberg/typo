/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package address

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

class AddressRepoMock(toRow: Function1[AddressRowUnsaved, AddressRow],
                      map: scala.collection.mutable.Map[AddressId, AddressRow] = scala.collection.mutable.Map.empty) extends AddressRepo {
  override def delete: DeleteBuilder[AddressFields, AddressRow] = {
    DeleteBuilderMock(DeleteParams.empty, AddressFields.structure, map)
  }
  override def deleteById(addressid: AddressId)(implicit c: Connection): Boolean = {
    map.remove(addressid).isDefined
  }
  override def deleteByIds(addressids: Array[AddressId])(implicit c: Connection): Int = {
    addressids.map(id => map.remove(id)).count(_.isDefined)
  }
  override def insert(unsaved: AddressRow)(implicit c: Connection): AddressRow = {
    val _ = if (map.contains(unsaved.addressid))
      sys.error(s"id ${unsaved.addressid} already exists")
    else
      map.put(unsaved.addressid, unsaved)
    
    unsaved
  }
  override def insert(unsaved: AddressRowUnsaved)(implicit c: Connection): AddressRow = {
    insert(toRow(unsaved))
  }
  override def insertStreaming(unsaved: Iterator[AddressRow], batchSize: Int = 10000)(implicit c: Connection): Long = {
    unsaved.foreach { row =>
      map += (row.addressid -> row)
    }
    unsaved.size.toLong
  }
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  override def insertUnsavedStreaming(unsaved: Iterator[AddressRowUnsaved], batchSize: Int = 10000)(implicit c: Connection): Long = {
    unsaved.foreach { unsavedRow =>
      val row = toRow(unsavedRow)
      map += (row.addressid -> row)
    }
    unsaved.size.toLong
  }
  override def select: SelectBuilder[AddressFields, AddressRow] = {
    SelectBuilderMock(AddressFields.structure, () => map.values.toList, SelectParams.empty)
  }
  override def selectAll(implicit c: Connection): List[AddressRow] = {
    map.values.toList
  }
  override def selectById(addressid: AddressId)(implicit c: Connection): Option[AddressRow] = {
    map.get(addressid)
  }
  override def selectByIds(addressids: Array[AddressId])(implicit c: Connection): List[AddressRow] = {
    addressids.flatMap(map.get).toList
  }
  override def selectByIdsTracked(addressids: Array[AddressId])(implicit c: Connection): Map[AddressId, AddressRow] = {
    val byId = selectByIds(addressids).view.map(x => (x.addressid, x)).toMap
    addressids.view.flatMap(id => byId.get(id).map(x => (id, x))).toMap
  }
  override def update: UpdateBuilder[AddressFields, AddressRow] = {
    UpdateBuilderMock(UpdateParams.empty, AddressFields.structure, map)
  }
  override def update(row: AddressRow)(implicit c: Connection): Option[AddressRow] = {
    map.get(row.addressid).map { _ =>
      map.put(row.addressid, row): @nowarn
      row
    }
  }
  override def upsert(unsaved: AddressRow)(implicit c: Connection): AddressRow = {
    map.put(unsaved.addressid, unsaved): @nowarn
    unsaved
  }
  override def upsertBatch(unsaved: Iterable[AddressRow])(implicit c: Connection): List[AddressRow] = {
    unsaved.map { row =>
      map += (row.addressid -> row)
      row
    }.toList
  }
  /* NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements */
  override def upsertStreaming(unsaved: Iterator[AddressRow], batchSize: Int = 10000)(implicit c: Connection): Int = {
    unsaved.foreach { row =>
      map += (row.addressid -> row)
    }
    unsaved.size
  }
}

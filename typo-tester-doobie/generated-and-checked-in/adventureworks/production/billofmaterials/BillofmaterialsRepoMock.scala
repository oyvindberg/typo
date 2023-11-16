/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package billofmaterials

import doobie.free.connection.ConnectionIO
import doobie.free.connection.delay
import fs2.Stream
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

class BillofmaterialsRepoMock(toRow: Function1[BillofmaterialsRowUnsaved, BillofmaterialsRow],
                              map: scala.collection.mutable.Map[BillofmaterialsId, BillofmaterialsRow] = scala.collection.mutable.Map.empty) extends BillofmaterialsRepo {
  override def delete(billofmaterialsid: BillofmaterialsId): ConnectionIO[Boolean] = {
    delay(map.remove(billofmaterialsid).isDefined)
  }
  override def delete: DeleteBuilder[BillofmaterialsFields, BillofmaterialsRow] = {
    DeleteBuilderMock(DeleteParams.empty, BillofmaterialsFields, map)
  }
  override def insert(unsaved: BillofmaterialsRow): ConnectionIO[BillofmaterialsRow] = {
    delay {
      val _ = if (map.contains(unsaved.billofmaterialsid))
        sys.error(s"id ${unsaved.billofmaterialsid} already exists")
      else
        map.put(unsaved.billofmaterialsid, unsaved)
    
      unsaved
    }
  }
  override def insertStreaming(unsaved: Stream[ConnectionIO, BillofmaterialsRow], batchSize: Int): ConnectionIO[Long] = {
    unsaved.compile.toList.map { rows =>
      var num = 0L
      rows.foreach { row =>
        map += (row.billofmaterialsid -> row)
        num += 1
      }
      num
    }
  }
  override def insert(unsaved: BillofmaterialsRowUnsaved): ConnectionIO[BillofmaterialsRow] = {
    insert(toRow(unsaved))
  }
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  override def insertUnsavedStreaming(unsaved: Stream[ConnectionIO, BillofmaterialsRowUnsaved], batchSize: Int): ConnectionIO[Long] = {
    unsaved.compile.toList.map { unsavedRows =>
      var num = 0L
      unsavedRows.foreach { unsavedRow =>
        val row = toRow(unsavedRow)
        map += (row.billofmaterialsid -> row)
        num += 1
      }
      num
    }
  }
  override def select: SelectBuilder[BillofmaterialsFields, BillofmaterialsRow] = {
    SelectBuilderMock(BillofmaterialsFields, delay(map.values.toList), SelectParams.empty)
  }
  override def selectAll: Stream[ConnectionIO, BillofmaterialsRow] = {
    Stream.emits(map.values.toList)
  }
  override def selectById(billofmaterialsid: BillofmaterialsId): ConnectionIO[Option[BillofmaterialsRow]] = {
    delay(map.get(billofmaterialsid))
  }
  override def selectByIds(billofmaterialsids: Array[BillofmaterialsId]): Stream[ConnectionIO, BillofmaterialsRow] = {
    Stream.emits(billofmaterialsids.flatMap(map.get).toList)
  }
  override def update(row: BillofmaterialsRow): ConnectionIO[Boolean] = {
    delay {
      map.get(row.billofmaterialsid) match {
        case Some(`row`) => false
        case Some(_) =>
          map.put(row.billofmaterialsid, row): @nowarn
          true
        case None => false
      }
    }
  }
  override def update: UpdateBuilder[BillofmaterialsFields, BillofmaterialsRow] = {
    UpdateBuilderMock(UpdateParams.empty, BillofmaterialsFields, map)
  }
  override def upsert(unsaved: BillofmaterialsRow): ConnectionIO[BillofmaterialsRow] = {
    delay {
      map.put(unsaved.billofmaterialsid, unsaved): @nowarn
      unsaved
    }
  }
}
/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package specialofferproduct

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

class SpecialofferproductRepoMock(toRow: Function1[SpecialofferproductRowUnsaved, SpecialofferproductRow],
                                  map: scala.collection.mutable.Map[SpecialofferproductId, SpecialofferproductRow] = scala.collection.mutable.Map.empty) extends SpecialofferproductRepo {
  override def delete(compositeId: SpecialofferproductId): ConnectionIO[Boolean] = {
    delay(map.remove(compositeId).isDefined)
  }
  override def delete: DeleteBuilder[SpecialofferproductFields, SpecialofferproductRow] = {
    DeleteBuilderMock(DeleteParams.empty, SpecialofferproductFields, map)
  }
  override def insert(unsaved: SpecialofferproductRow): ConnectionIO[SpecialofferproductRow] = {
    delay {
      val _ = if (map.contains(unsaved.compositeId))
        sys.error(s"id ${unsaved.compositeId} already exists")
      else
        map.put(unsaved.compositeId, unsaved)
    
      unsaved
    }
  }
  override def insertStreaming(unsaved: Stream[ConnectionIO, SpecialofferproductRow], batchSize: Int): ConnectionIO[Long] = {
    unsaved.compile.toList.map { rows =>
      var num = 0L
      rows.foreach { row =>
        map += (row.compositeId -> row)
        num += 1
      }
      num
    }
  }
  override def insert(unsaved: SpecialofferproductRowUnsaved): ConnectionIO[SpecialofferproductRow] = {
    insert(toRow(unsaved))
  }
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  override def insertUnsavedStreaming(unsaved: Stream[ConnectionIO, SpecialofferproductRowUnsaved], batchSize: Int): ConnectionIO[Long] = {
    unsaved.compile.toList.map { unsavedRows =>
      var num = 0L
      unsavedRows.foreach { unsavedRow =>
        val row = toRow(unsavedRow)
        map += (row.compositeId -> row)
        num += 1
      }
      num
    }
  }
  override def select: SelectBuilder[SpecialofferproductFields, SpecialofferproductRow] = {
    SelectBuilderMock(SpecialofferproductFields, delay(map.values.toList), SelectParams.empty)
  }
  override def selectAll: Stream[ConnectionIO, SpecialofferproductRow] = {
    Stream.emits(map.values.toList)
  }
  override def selectById(compositeId: SpecialofferproductId): ConnectionIO[Option[SpecialofferproductRow]] = {
    delay(map.get(compositeId))
  }
  override def update(row: SpecialofferproductRow): ConnectionIO[Boolean] = {
    delay {
      map.get(row.compositeId) match {
        case Some(`row`) => false
        case Some(_) =>
          map.put(row.compositeId, row): @nowarn
          true
        case None => false
      }
    }
  }
  override def update: UpdateBuilder[SpecialofferproductFields, SpecialofferproductRow] = {
    UpdateBuilderMock(UpdateParams.empty, SpecialofferproductFields, map)
  }
  override def upsert(unsaved: SpecialofferproductRow): ConnectionIO[SpecialofferproductRow] = {
    delay {
      map.put(unsaved.compositeId, unsaved): @nowarn
      unsaved
    }
  }
}
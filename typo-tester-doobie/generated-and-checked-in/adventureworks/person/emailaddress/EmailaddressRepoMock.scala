/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package emailaddress

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

class EmailaddressRepoMock(toRow: Function1[EmailaddressRowUnsaved, EmailaddressRow],
                           map: scala.collection.mutable.Map[EmailaddressId, EmailaddressRow] = scala.collection.mutable.Map.empty) extends EmailaddressRepo {
  override def delete(compositeId: EmailaddressId): ConnectionIO[Boolean] = {
    delay(map.remove(compositeId).isDefined)
  }
  override def delete: DeleteBuilder[EmailaddressFields, EmailaddressRow] = {
    DeleteBuilderMock(DeleteParams.empty, EmailaddressFields.structure.fields, map)
  }
  override def insert(unsaved: EmailaddressRow): ConnectionIO[EmailaddressRow] = {
    delay {
      val _ = if (map.contains(unsaved.compositeId))
        sys.error(s"id ${unsaved.compositeId} already exists")
      else
        map.put(unsaved.compositeId, unsaved)
    
      unsaved
    }
  }
  override def insertStreaming(unsaved: Stream[ConnectionIO, EmailaddressRow], batchSize: Int): ConnectionIO[Long] = {
    unsaved.compile.toList.map { rows =>
      var num = 0L
      rows.foreach { row =>
        map += (row.compositeId -> row)
        num += 1
      }
      num
    }
  }
  override def insert(unsaved: EmailaddressRowUnsaved): ConnectionIO[EmailaddressRow] = {
    insert(toRow(unsaved))
  }
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  override def insertUnsavedStreaming(unsaved: Stream[ConnectionIO, EmailaddressRowUnsaved], batchSize: Int): ConnectionIO[Long] = {
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
  override def select: SelectBuilder[EmailaddressFields, EmailaddressRow] = {
    SelectBuilderMock(EmailaddressFields.structure, delay(map.values.toList), SelectParams.empty)
  }
  override def selectAll: Stream[ConnectionIO, EmailaddressRow] = {
    Stream.emits(map.values.toList)
  }
  override def selectById(compositeId: EmailaddressId): ConnectionIO[Option[EmailaddressRow]] = {
    delay(map.get(compositeId))
  }
  override def update(row: EmailaddressRow): ConnectionIO[Boolean] = {
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
  override def update: UpdateBuilder[EmailaddressFields, EmailaddressRow] = {
    UpdateBuilderMock(UpdateParams.empty, EmailaddressFields.structure.fields, map)
  }
  override def upsert(unsaved: EmailaddressRow): ConnectionIO[EmailaddressRow] = {
    delay {
      map.put(unsaved.compositeId, unsaved): @nowarn
      unsaved
    }
  }
}
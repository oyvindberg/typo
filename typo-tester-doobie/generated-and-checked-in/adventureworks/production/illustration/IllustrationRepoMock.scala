/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package illustration

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

class IllustrationRepoMock(toRow: Function1[IllustrationRowUnsaved, IllustrationRow],
                           map: scala.collection.mutable.Map[IllustrationId, IllustrationRow] = scala.collection.mutable.Map.empty) extends IllustrationRepo {
  override def delete(illustrationid: IllustrationId): ConnectionIO[Boolean] = {
    delay(map.remove(illustrationid).isDefined)
  }
  override def delete: DeleteBuilder[IllustrationFields, IllustrationRow] = {
    DeleteBuilderMock(DeleteParams.empty, IllustrationFields.structure.fields, map)
  }
  override def insert(unsaved: IllustrationRow): ConnectionIO[IllustrationRow] = {
    delay {
      val _ = if (map.contains(unsaved.illustrationid))
        sys.error(s"id ${unsaved.illustrationid} already exists")
      else
        map.put(unsaved.illustrationid, unsaved)
    
      unsaved
    }
  }
  override def insertStreaming(unsaved: Stream[ConnectionIO, IllustrationRow], batchSize: Int): ConnectionIO[Long] = {
    unsaved.compile.toList.map { rows =>
      var num = 0L
      rows.foreach { row =>
        map += (row.illustrationid -> row)
        num += 1
      }
      num
    }
  }
  override def insert(unsaved: IllustrationRowUnsaved): ConnectionIO[IllustrationRow] = {
    insert(toRow(unsaved))
  }
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  override def insertUnsavedStreaming(unsaved: Stream[ConnectionIO, IllustrationRowUnsaved], batchSize: Int): ConnectionIO[Long] = {
    unsaved.compile.toList.map { unsavedRows =>
      var num = 0L
      unsavedRows.foreach { unsavedRow =>
        val row = toRow(unsavedRow)
        map += (row.illustrationid -> row)
        num += 1
      }
      num
    }
  }
  override def select: SelectBuilder[IllustrationFields, IllustrationRow] = {
    SelectBuilderMock(IllustrationFields.structure, delay(map.values.toList), SelectParams.empty)
  }
  override def selectAll: Stream[ConnectionIO, IllustrationRow] = {
    Stream.emits(map.values.toList)
  }
  override def selectById(illustrationid: IllustrationId): ConnectionIO[Option[IllustrationRow]] = {
    delay(map.get(illustrationid))
  }
  override def selectByIds(illustrationids: Array[IllustrationId]): Stream[ConnectionIO, IllustrationRow] = {
    Stream.emits(illustrationids.flatMap(map.get).toList)
  }
  override def update(row: IllustrationRow): ConnectionIO[Boolean] = {
    delay {
      map.get(row.illustrationid) match {
        case Some(`row`) => false
        case Some(_) =>
          map.put(row.illustrationid, row): @nowarn
          true
        case None => false
      }
    }
  }
  override def update: UpdateBuilder[IllustrationFields, IllustrationRow] = {
    UpdateBuilderMock(UpdateParams.empty, IllustrationFields.structure.fields, map)
  }
  override def upsert(unsaved: IllustrationRow): ConnectionIO[IllustrationRow] = {
    delay {
      map.put(unsaved.illustrationid, unsaved): @nowarn
      unsaved
    }
  }
}

/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package humanresources
package shift

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
import zio.Chunk
import zio.ZIO
import zio.jdbc.UpdateResult
import zio.jdbc.ZConnection
import zio.stream.ZStream

class ShiftRepoMock(toRow: Function1[ShiftRowUnsaved, ShiftRow],
                    map: scala.collection.mutable.Map[ShiftId, ShiftRow] = scala.collection.mutable.Map.empty) extends ShiftRepo {
  override def delete: DeleteBuilder[ShiftFields, ShiftRow] = {
    DeleteBuilderMock(DeleteParams.empty, ShiftFields.structure, map)
  }
  override def deleteById(shiftid: ShiftId): ZIO[ZConnection, Throwable, Boolean] = {
    ZIO.succeed(map.remove(shiftid).isDefined)
  }
  override def deleteByIds(shiftids: Array[ShiftId]): ZIO[ZConnection, Throwable, Long] = {
    ZIO.succeed(shiftids.map(id => map.remove(id)).count(_.isDefined).toLong)
  }
  override def insert(unsaved: ShiftRow): ZIO[ZConnection, Throwable, ShiftRow] = {
    ZIO.succeed {
      val _ =
        if (map.contains(unsaved.shiftid))
          sys.error(s"id ${unsaved.shiftid} already exists")
        else
          map.put(unsaved.shiftid, unsaved)
    
      unsaved
    }
  }
  override def insert(unsaved: ShiftRowUnsaved): ZIO[ZConnection, Throwable, ShiftRow] = {
    insert(toRow(unsaved))
  }
  override def insertStreaming(unsaved: ZStream[ZConnection, Throwable, ShiftRow], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long] = {
    unsaved.scanZIO(0L) { case (acc, row) =>
      ZIO.succeed {
        map += (row.shiftid -> row)
        acc + 1
      }
    }.runLast.map(_.getOrElse(0L))
  }
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  override def insertUnsavedStreaming(unsaved: ZStream[ZConnection, Throwable, ShiftRowUnsaved], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long] = {
    unsaved.scanZIO(0L) { case (acc, unsavedRow) =>
      ZIO.succeed {
        val row = toRow(unsavedRow)
        map += (row.shiftid -> row)
        acc + 1
      }
    }.runLast.map(_.getOrElse(0L))
  }
  override def select: SelectBuilder[ShiftFields, ShiftRow] = {
    SelectBuilderMock(ShiftFields.structure, ZIO.succeed(Chunk.fromIterable(map.values)), SelectParams.empty)
  }
  override def selectAll: ZStream[ZConnection, Throwable, ShiftRow] = {
    ZStream.fromIterable(map.values)
  }
  override def selectById(shiftid: ShiftId): ZIO[ZConnection, Throwable, Option[ShiftRow]] = {
    ZIO.succeed(map.get(shiftid))
  }
  override def selectByIds(shiftids: Array[ShiftId]): ZStream[ZConnection, Throwable, ShiftRow] = {
    ZStream.fromIterable(shiftids.flatMap(map.get))
  }
  override def selectByIdsTracked(shiftids: Array[ShiftId]): ZIO[ZConnection, Throwable, Map[ShiftId, ShiftRow]] = {
    selectByIds(shiftids).runCollect.map { rows =>
      val byId = rows.view.map(x => (x.shiftid, x)).toMap
      shiftids.view.flatMap(id => byId.get(id).map(x => (id, x))).toMap
    }
  }
  override def update: UpdateBuilder[ShiftFields, ShiftRow] = {
    UpdateBuilderMock(UpdateParams.empty, ShiftFields.structure, map)
  }
  override def update(row: ShiftRow): ZIO[ZConnection, Throwable, Option[ShiftRow]] = {
    ZIO.succeed {
      map.get(row.shiftid).map { _ =>
        map.put(row.shiftid, row): @nowarn
        row
      }
    }
  }
  override def upsert(unsaved: ShiftRow): ZIO[ZConnection, Throwable, UpdateResult[ShiftRow]] = {
    ZIO.succeed {
      map.put(unsaved.shiftid, unsaved): @nowarn
      UpdateResult(1, Chunk.single(unsaved))
    }
  }
  /* NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements */
  override def upsertStreaming(unsaved: ZStream[ZConnection, Throwable, ShiftRow], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long] = {
    unsaved.scanZIO(0L) { case (acc, row) =>
      ZIO.succeed {
        map += (row.shiftid -> row)
        acc + 1
      }
    }.runLast.map(_.getOrElse(0L))
  }
}

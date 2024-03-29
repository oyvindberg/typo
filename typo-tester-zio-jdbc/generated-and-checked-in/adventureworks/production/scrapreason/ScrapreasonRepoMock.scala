/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package scrapreason

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

class ScrapreasonRepoMock(toRow: Function1[ScrapreasonRowUnsaved, ScrapreasonRow],
                          map: scala.collection.mutable.Map[ScrapreasonId, ScrapreasonRow] = scala.collection.mutable.Map.empty) extends ScrapreasonRepo {
  override def delete(scrapreasonid: ScrapreasonId): ZIO[ZConnection, Throwable, Boolean] = {
    ZIO.succeed(map.remove(scrapreasonid).isDefined)
  }
  override def delete: DeleteBuilder[ScrapreasonFields, ScrapreasonRow] = {
    DeleteBuilderMock(DeleteParams.empty, ScrapreasonFields.structure.fields, map)
  }
  override def insert(unsaved: ScrapreasonRow): ZIO[ZConnection, Throwable, ScrapreasonRow] = {
    ZIO.succeed {
      val _ =
        if (map.contains(unsaved.scrapreasonid))
          sys.error(s"id ${unsaved.scrapreasonid} already exists")
        else
          map.put(unsaved.scrapreasonid, unsaved)
    
      unsaved
    }
  }
  override def insertStreaming(unsaved: ZStream[ZConnection, Throwable, ScrapreasonRow], batchSize: Int): ZIO[ZConnection, Throwable, Long] = {
    unsaved.scanZIO(0L) { case (acc, row) =>
      ZIO.succeed {
        map += (row.scrapreasonid -> row)
        acc + 1
      }
    }.runLast.map(_.getOrElse(0L))
  }
  override def insert(unsaved: ScrapreasonRowUnsaved): ZIO[ZConnection, Throwable, ScrapreasonRow] = {
    insert(toRow(unsaved))
  }
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  override def insertUnsavedStreaming(unsaved: ZStream[ZConnection, Throwable, ScrapreasonRowUnsaved], batchSize: Int): ZIO[ZConnection, Throwable, Long] = {
    unsaved.scanZIO(0L) { case (acc, unsavedRow) =>
      ZIO.succeed {
        val row = toRow(unsavedRow)
        map += (row.scrapreasonid -> row)
        acc + 1
      }
    }.runLast.map(_.getOrElse(0L))
  }
  override def select: SelectBuilder[ScrapreasonFields, ScrapreasonRow] = {
    SelectBuilderMock(ScrapreasonFields.structure, ZIO.succeed(Chunk.fromIterable(map.values)), SelectParams.empty)
  }
  override def selectAll: ZStream[ZConnection, Throwable, ScrapreasonRow] = {
    ZStream.fromIterable(map.values)
  }
  override def selectById(scrapreasonid: ScrapreasonId): ZIO[ZConnection, Throwable, Option[ScrapreasonRow]] = {
    ZIO.succeed(map.get(scrapreasonid))
  }
  override def selectByIds(scrapreasonids: Array[ScrapreasonId]): ZStream[ZConnection, Throwable, ScrapreasonRow] = {
    ZStream.fromIterable(scrapreasonids.flatMap(map.get))
  }
  override def update(row: ScrapreasonRow): ZIO[ZConnection, Throwable, Boolean] = {
    ZIO.succeed {
      map.get(row.scrapreasonid) match {
        case Some(`row`) => false
        case Some(_) =>
          map.put(row.scrapreasonid, row): @nowarn
          true
        case None => false
      }
    }
  }
  override def update: UpdateBuilder[ScrapreasonFields, ScrapreasonRow] = {
    UpdateBuilderMock(UpdateParams.empty, ScrapreasonFields.structure.fields, map)
  }
  override def upsert(unsaved: ScrapreasonRow): ZIO[ZConnection, Throwable, UpdateResult[ScrapreasonRow]] = {
    ZIO.succeed {
      map.put(unsaved.scrapreasonid, unsaved): @nowarn
      UpdateResult(1, Chunk.single(unsaved))
    }
  }
}

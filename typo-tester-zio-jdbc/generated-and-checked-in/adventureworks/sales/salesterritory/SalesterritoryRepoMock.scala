/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package salesterritory

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

class SalesterritoryRepoMock(toRow: Function1[SalesterritoryRowUnsaved, SalesterritoryRow],
                             map: scala.collection.mutable.Map[SalesterritoryId, SalesterritoryRow] = scala.collection.mutable.Map.empty) extends SalesterritoryRepo {
  override def delete(territoryid: SalesterritoryId): ZIO[ZConnection, Throwable, Boolean] = {
    ZIO.succeed(map.remove(territoryid).isDefined)
  }
  override def delete: DeleteBuilder[SalesterritoryFields, SalesterritoryRow] = {
    DeleteBuilderMock(DeleteParams.empty, SalesterritoryFields.structure.fields, map)
  }
  override def insert(unsaved: SalesterritoryRow): ZIO[ZConnection, Throwable, SalesterritoryRow] = {
    ZIO.succeed {
      val _ =
        if (map.contains(unsaved.territoryid))
          sys.error(s"id ${unsaved.territoryid} already exists")
        else
          map.put(unsaved.territoryid, unsaved)
    
      unsaved
    }
  }
  override def insertStreaming(unsaved: ZStream[ZConnection, Throwable, SalesterritoryRow], batchSize: Int): ZIO[ZConnection, Throwable, Long] = {
    unsaved.scanZIO(0L) { case (acc, row) =>
      ZIO.succeed {
        map += (row.territoryid -> row)
        acc + 1
      }
    }.runLast.map(_.getOrElse(0L))
  }
  override def insert(unsaved: SalesterritoryRowUnsaved): ZIO[ZConnection, Throwable, SalesterritoryRow] = {
    insert(toRow(unsaved))
  }
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  override def insertUnsavedStreaming(unsaved: ZStream[ZConnection, Throwable, SalesterritoryRowUnsaved], batchSize: Int): ZIO[ZConnection, Throwable, Long] = {
    unsaved.scanZIO(0L) { case (acc, unsavedRow) =>
      ZIO.succeed {
        val row = toRow(unsavedRow)
        map += (row.territoryid -> row)
        acc + 1
      }
    }.runLast.map(_.getOrElse(0L))
  }
  override def select: SelectBuilder[SalesterritoryFields, SalesterritoryRow] = {
    SelectBuilderMock(SalesterritoryFields.structure, ZIO.succeed(Chunk.fromIterable(map.values)), SelectParams.empty)
  }
  override def selectAll: ZStream[ZConnection, Throwable, SalesterritoryRow] = {
    ZStream.fromIterable(map.values)
  }
  override def selectById(territoryid: SalesterritoryId): ZIO[ZConnection, Throwable, Option[SalesterritoryRow]] = {
    ZIO.succeed(map.get(territoryid))
  }
  override def selectByIds(territoryids: Array[SalesterritoryId]): ZStream[ZConnection, Throwable, SalesterritoryRow] = {
    ZStream.fromIterable(territoryids.flatMap(map.get))
  }
  override def update(row: SalesterritoryRow): ZIO[ZConnection, Throwable, Boolean] = {
    ZIO.succeed {
      map.get(row.territoryid) match {
        case Some(`row`) => false
        case Some(_) =>
          map.put(row.territoryid, row): @nowarn
          true
        case None => false
      }
    }
  }
  override def update: UpdateBuilder[SalesterritoryFields, SalesterritoryRow] = {
    UpdateBuilderMock(UpdateParams.empty, SalesterritoryFields.structure.fields, map)
  }
  override def upsert(unsaved: SalesterritoryRow): ZIO[ZConnection, Throwable, UpdateResult[SalesterritoryRow]] = {
    ZIO.succeed {
      map.put(unsaved.territoryid, unsaved): @nowarn
      UpdateResult(1, Chunk.single(unsaved))
    }
  }
}

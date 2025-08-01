/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package salesreason

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

class SalesreasonRepoMock(toRow: Function1[SalesreasonRowUnsaved, SalesreasonRow],
                          map: scala.collection.mutable.Map[SalesreasonId, SalesreasonRow] = scala.collection.mutable.Map.empty) extends SalesreasonRepo {
  override def delete: DeleteBuilder[SalesreasonFields, SalesreasonRow] = {
    DeleteBuilderMock(DeleteParams.empty, SalesreasonFields.structure, map)
  }
  override def deleteById(salesreasonid: SalesreasonId): ZIO[ZConnection, Throwable, Boolean] = {
    ZIO.succeed(map.remove(salesreasonid).isDefined)
  }
  override def deleteByIds(salesreasonids: Array[SalesreasonId]): ZIO[ZConnection, Throwable, Long] = {
    ZIO.succeed(salesreasonids.map(id => map.remove(id)).count(_.isDefined).toLong)
  }
  override def insert(unsaved: SalesreasonRow): ZIO[ZConnection, Throwable, SalesreasonRow] = {
    ZIO.succeed {
      val _ =
        if (map.contains(unsaved.salesreasonid))
          sys.error(s"id ${unsaved.salesreasonid} already exists")
        else
          map.put(unsaved.salesreasonid, unsaved)
    
      unsaved
    }
  }
  override def insert(unsaved: SalesreasonRowUnsaved): ZIO[ZConnection, Throwable, SalesreasonRow] = {
    insert(toRow(unsaved))
  }
  override def insertStreaming(unsaved: ZStream[ZConnection, Throwable, SalesreasonRow], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long] = {
    unsaved.scanZIO(0L) { case (acc, row) =>
      ZIO.succeed {
        map += (row.salesreasonid -> row)
        acc + 1
      }
    }.runLast.map(_.getOrElse(0L))
  }
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  override def insertUnsavedStreaming(unsaved: ZStream[ZConnection, Throwable, SalesreasonRowUnsaved], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long] = {
    unsaved.scanZIO(0L) { case (acc, unsavedRow) =>
      ZIO.succeed {
        val row = toRow(unsavedRow)
        map += (row.salesreasonid -> row)
        acc + 1
      }
    }.runLast.map(_.getOrElse(0L))
  }
  override def select: SelectBuilder[SalesreasonFields, SalesreasonRow] = {
    SelectBuilderMock(SalesreasonFields.structure, ZIO.succeed(Chunk.fromIterable(map.values)), SelectParams.empty)
  }
  override def selectAll: ZStream[ZConnection, Throwable, SalesreasonRow] = {
    ZStream.fromIterable(map.values)
  }
  override def selectById(salesreasonid: SalesreasonId): ZIO[ZConnection, Throwable, Option[SalesreasonRow]] = {
    ZIO.succeed(map.get(salesreasonid))
  }
  override def selectByIds(salesreasonids: Array[SalesreasonId]): ZStream[ZConnection, Throwable, SalesreasonRow] = {
    ZStream.fromIterable(salesreasonids.flatMap(map.get))
  }
  override def selectByIdsTracked(salesreasonids: Array[SalesreasonId]): ZIO[ZConnection, Throwable, Map[SalesreasonId, SalesreasonRow]] = {
    selectByIds(salesreasonids).runCollect.map { rows =>
      val byId = rows.view.map(x => (x.salesreasonid, x)).toMap
      salesreasonids.view.flatMap(id => byId.get(id).map(x => (id, x))).toMap
    }
  }
  override def update: UpdateBuilder[SalesreasonFields, SalesreasonRow] = {
    UpdateBuilderMock(UpdateParams.empty, SalesreasonFields.structure, map)
  }
  override def update(row: SalesreasonRow): ZIO[ZConnection, Throwable, Option[SalesreasonRow]] = {
    ZIO.succeed {
      map.get(row.salesreasonid).map { _ =>
        map.put(row.salesreasonid, row): @nowarn
        row
      }
    }
  }
  override def upsert(unsaved: SalesreasonRow): ZIO[ZConnection, Throwable, UpdateResult[SalesreasonRow]] = {
    ZIO.succeed {
      map.put(unsaved.salesreasonid, unsaved): @nowarn
      UpdateResult(1, Chunk.single(unsaved))
    }
  }
  /* NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements */
  override def upsertStreaming(unsaved: ZStream[ZConnection, Throwable, SalesreasonRow], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long] = {
    unsaved.scanZIO(0L) { case (acc, row) =>
      ZIO.succeed {
        map += (row.salesreasonid -> row)
        acc + 1
      }
    }.runLast.map(_.getOrElse(0L))
  }
}

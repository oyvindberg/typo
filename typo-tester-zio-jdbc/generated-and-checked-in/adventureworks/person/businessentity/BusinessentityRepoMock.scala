/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package businessentity

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

class BusinessentityRepoMock(toRow: Function1[BusinessentityRowUnsaved, BusinessentityRow],
                             map: scala.collection.mutable.Map[BusinessentityId, BusinessentityRow] = scala.collection.mutable.Map.empty) extends BusinessentityRepo {
  override def delete: DeleteBuilder[BusinessentityFields, BusinessentityRow] = {
    DeleteBuilderMock(DeleteParams.empty, BusinessentityFields.structure, map)
  }
  override def deleteById(businessentityid: BusinessentityId): ZIO[ZConnection, Throwable, Boolean] = {
    ZIO.succeed(map.remove(businessentityid).isDefined)
  }
  override def deleteByIds(businessentityids: Array[BusinessentityId]): ZIO[ZConnection, Throwable, Long] = {
    ZIO.succeed(businessentityids.map(id => map.remove(id)).count(_.isDefined).toLong)
  }
  override def insert(unsaved: BusinessentityRow): ZIO[ZConnection, Throwable, BusinessentityRow] = {
    ZIO.succeed {
      val _ =
        if (map.contains(unsaved.businessentityid))
          sys.error(s"id ${unsaved.businessentityid} already exists")
        else
          map.put(unsaved.businessentityid, unsaved)
    
      unsaved
    }
  }
  override def insert(unsaved: BusinessentityRowUnsaved): ZIO[ZConnection, Throwable, BusinessentityRow] = {
    insert(toRow(unsaved))
  }
  override def insertStreaming(unsaved: ZStream[ZConnection, Throwable, BusinessentityRow], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long] = {
    unsaved.scanZIO(0L) { case (acc, row) =>
      ZIO.succeed {
        map += (row.businessentityid -> row)
        acc + 1
      }
    }.runLast.map(_.getOrElse(0L))
  }
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  override def insertUnsavedStreaming(unsaved: ZStream[ZConnection, Throwable, BusinessentityRowUnsaved], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long] = {
    unsaved.scanZIO(0L) { case (acc, unsavedRow) =>
      ZIO.succeed {
        val row = toRow(unsavedRow)
        map += (row.businessentityid -> row)
        acc + 1
      }
    }.runLast.map(_.getOrElse(0L))
  }
  override def select: SelectBuilder[BusinessentityFields, BusinessentityRow] = {
    SelectBuilderMock(BusinessentityFields.structure, ZIO.succeed(Chunk.fromIterable(map.values)), SelectParams.empty)
  }
  override def selectAll: ZStream[ZConnection, Throwable, BusinessentityRow] = {
    ZStream.fromIterable(map.values)
  }
  override def selectById(businessentityid: BusinessentityId): ZIO[ZConnection, Throwable, Option[BusinessentityRow]] = {
    ZIO.succeed(map.get(businessentityid))
  }
  override def selectByIds(businessentityids: Array[BusinessentityId]): ZStream[ZConnection, Throwable, BusinessentityRow] = {
    ZStream.fromIterable(businessentityids.flatMap(map.get))
  }
  override def selectByIdsTracked(businessentityids: Array[BusinessentityId]): ZIO[ZConnection, Throwable, Map[BusinessentityId, BusinessentityRow]] = {
    selectByIds(businessentityids).runCollect.map { rows =>
      val byId = rows.view.map(x => (x.businessentityid, x)).toMap
      businessentityids.view.flatMap(id => byId.get(id).map(x => (id, x))).toMap
    }
  }
  override def update: UpdateBuilder[BusinessentityFields, BusinessentityRow] = {
    UpdateBuilderMock(UpdateParams.empty, BusinessentityFields.structure, map)
  }
  override def update(row: BusinessentityRow): ZIO[ZConnection, Throwable, Option[BusinessentityRow]] = {
    ZIO.succeed {
      map.get(row.businessentityid).map { _ =>
        map.put(row.businessentityid, row): @nowarn
        row
      }
    }
  }
  override def upsert(unsaved: BusinessentityRow): ZIO[ZConnection, Throwable, UpdateResult[BusinessentityRow]] = {
    ZIO.succeed {
      map.put(unsaved.businessentityid, unsaved): @nowarn
      UpdateResult(1, Chunk.single(unsaved))
    }
  }
  /* NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements */
  override def upsertStreaming(unsaved: ZStream[ZConnection, Throwable, BusinessentityRow], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long] = {
    unsaved.scanZIO(0L) { case (acc, row) =>
      ZIO.succeed {
        map += (row.businessentityid -> row)
        acc + 1
      }
    }.runLast.map(_.getOrElse(0L))
  }
}

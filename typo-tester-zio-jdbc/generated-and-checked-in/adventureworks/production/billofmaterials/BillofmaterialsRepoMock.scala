/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package billofmaterials

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

class BillofmaterialsRepoMock(toRow: Function1[BillofmaterialsRowUnsaved, BillofmaterialsRow],
                              map: scala.collection.mutable.Map[BillofmaterialsId, BillofmaterialsRow] = scala.collection.mutable.Map.empty) extends BillofmaterialsRepo {
  override def delete(billofmaterialsid: BillofmaterialsId): ZIO[ZConnection, Throwable, Boolean] = {
    ZIO.succeed(map.remove(billofmaterialsid).isDefined)
  }
  override def delete: DeleteBuilder[BillofmaterialsFields, BillofmaterialsRow] = {
    DeleteBuilderMock(DeleteParams.empty, BillofmaterialsFields, map)
  }
  override def insert(unsaved: BillofmaterialsRow): ZIO[ZConnection, Throwable, BillofmaterialsRow] = {
    ZIO.succeed {
      val _ =
        if (map.contains(unsaved.billofmaterialsid))
          sys.error(s"id ${unsaved.billofmaterialsid} already exists")
        else
          map.put(unsaved.billofmaterialsid, unsaved)
    
      unsaved
    }
  }
  override def insertStreaming(unsaved: ZStream[ZConnection, Throwable, BillofmaterialsRow], batchSize: Int): ZIO[ZConnection, Throwable, Long] = {
    unsaved.scanZIO(0L) { case (acc, row) =>
      ZIO.succeed {
        map += (row.billofmaterialsid -> row)
        acc + 1
      }
    }.runLast.map(_.getOrElse(0L))
  }
  override def insert(unsaved: BillofmaterialsRowUnsaved): ZIO[ZConnection, Throwable, BillofmaterialsRow] = {
    insert(toRow(unsaved))
  }
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  override def insertUnsavedStreaming(unsaved: ZStream[ZConnection, Throwable, BillofmaterialsRowUnsaved], batchSize: Int): ZIO[ZConnection, Throwable, Long] = {
    unsaved.scanZIO(0L) { case (acc, unsavedRow) =>
      ZIO.succeed {
        val row = toRow(unsavedRow)
        map += (row.billofmaterialsid -> row)
        acc + 1
      }
    }.runLast.map(_.getOrElse(0L))
  }
  override def select: SelectBuilder[BillofmaterialsFields, BillofmaterialsRow] = {
    SelectBuilderMock(BillofmaterialsFields, ZIO.succeed(Chunk.fromIterable(map.values)), SelectParams.empty)
  }
  override def selectAll: ZStream[ZConnection, Throwable, BillofmaterialsRow] = {
    ZStream.fromIterable(map.values)
  }
  override def selectById(billofmaterialsid: BillofmaterialsId): ZIO[ZConnection, Throwable, Option[BillofmaterialsRow]] = {
    ZIO.succeed(map.get(billofmaterialsid))
  }
  override def selectByIds(billofmaterialsids: Array[BillofmaterialsId]): ZStream[ZConnection, Throwable, BillofmaterialsRow] = {
    ZStream.fromIterable(billofmaterialsids.flatMap(map.get))
  }
  override def update(row: BillofmaterialsRow): ZIO[ZConnection, Throwable, Boolean] = {
    ZIO.succeed {
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
  override def upsert(unsaved: BillofmaterialsRow): ZIO[ZConnection, Throwable, UpdateResult[BillofmaterialsRow]] = {
    ZIO.succeed {
      map.put(unsaved.billofmaterialsid, unsaved): @nowarn
      UpdateResult(1, Chunk.single(unsaved))
    }
  }
}
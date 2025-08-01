/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package shoppingcartitem

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

class ShoppingcartitemRepoMock(toRow: Function1[ShoppingcartitemRowUnsaved, ShoppingcartitemRow],
                               map: scala.collection.mutable.Map[ShoppingcartitemId, ShoppingcartitemRow] = scala.collection.mutable.Map.empty) extends ShoppingcartitemRepo {
  override def delete: DeleteBuilder[ShoppingcartitemFields, ShoppingcartitemRow] = {
    DeleteBuilderMock(DeleteParams.empty, ShoppingcartitemFields.structure, map)
  }
  override def deleteById(shoppingcartitemid: ShoppingcartitemId): ZIO[ZConnection, Throwable, Boolean] = {
    ZIO.succeed(map.remove(shoppingcartitemid).isDefined)
  }
  override def deleteByIds(shoppingcartitemids: Array[ShoppingcartitemId]): ZIO[ZConnection, Throwable, Long] = {
    ZIO.succeed(shoppingcartitemids.map(id => map.remove(id)).count(_.isDefined).toLong)
  }
  override def insert(unsaved: ShoppingcartitemRow): ZIO[ZConnection, Throwable, ShoppingcartitemRow] = {
    ZIO.succeed {
      val _ =
        if (map.contains(unsaved.shoppingcartitemid))
          sys.error(s"id ${unsaved.shoppingcartitemid} already exists")
        else
          map.put(unsaved.shoppingcartitemid, unsaved)
    
      unsaved
    }
  }
  override def insert(unsaved: ShoppingcartitemRowUnsaved): ZIO[ZConnection, Throwable, ShoppingcartitemRow] = {
    insert(toRow(unsaved))
  }
  override def insertStreaming(unsaved: ZStream[ZConnection, Throwable, ShoppingcartitemRow], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long] = {
    unsaved.scanZIO(0L) { case (acc, row) =>
      ZIO.succeed {
        map += (row.shoppingcartitemid -> row)
        acc + 1
      }
    }.runLast.map(_.getOrElse(0L))
  }
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  override def insertUnsavedStreaming(unsaved: ZStream[ZConnection, Throwable, ShoppingcartitemRowUnsaved], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long] = {
    unsaved.scanZIO(0L) { case (acc, unsavedRow) =>
      ZIO.succeed {
        val row = toRow(unsavedRow)
        map += (row.shoppingcartitemid -> row)
        acc + 1
      }
    }.runLast.map(_.getOrElse(0L))
  }
  override def select: SelectBuilder[ShoppingcartitemFields, ShoppingcartitemRow] = {
    SelectBuilderMock(ShoppingcartitemFields.structure, ZIO.succeed(Chunk.fromIterable(map.values)), SelectParams.empty)
  }
  override def selectAll: ZStream[ZConnection, Throwable, ShoppingcartitemRow] = {
    ZStream.fromIterable(map.values)
  }
  override def selectById(shoppingcartitemid: ShoppingcartitemId): ZIO[ZConnection, Throwable, Option[ShoppingcartitemRow]] = {
    ZIO.succeed(map.get(shoppingcartitemid))
  }
  override def selectByIds(shoppingcartitemids: Array[ShoppingcartitemId]): ZStream[ZConnection, Throwable, ShoppingcartitemRow] = {
    ZStream.fromIterable(shoppingcartitemids.flatMap(map.get))
  }
  override def selectByIdsTracked(shoppingcartitemids: Array[ShoppingcartitemId]): ZIO[ZConnection, Throwable, Map[ShoppingcartitemId, ShoppingcartitemRow]] = {
    selectByIds(shoppingcartitemids).runCollect.map { rows =>
      val byId = rows.view.map(x => (x.shoppingcartitemid, x)).toMap
      shoppingcartitemids.view.flatMap(id => byId.get(id).map(x => (id, x))).toMap
    }
  }
  override def update: UpdateBuilder[ShoppingcartitemFields, ShoppingcartitemRow] = {
    UpdateBuilderMock(UpdateParams.empty, ShoppingcartitemFields.structure, map)
  }
  override def update(row: ShoppingcartitemRow): ZIO[ZConnection, Throwable, Option[ShoppingcartitemRow]] = {
    ZIO.succeed {
      map.get(row.shoppingcartitemid).map { _ =>
        map.put(row.shoppingcartitemid, row): @nowarn
        row
      }
    }
  }
  override def upsert(unsaved: ShoppingcartitemRow): ZIO[ZConnection, Throwable, UpdateResult[ShoppingcartitemRow]] = {
    ZIO.succeed {
      map.put(unsaved.shoppingcartitemid, unsaved): @nowarn
      UpdateResult(1, Chunk.single(unsaved))
    }
  }
  /* NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements */
  override def upsertStreaming(unsaved: ZStream[ZConnection, Throwable, ShoppingcartitemRow], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long] = {
    unsaved.scanZIO(0L) { case (acc, row) =>
      ZIO.succeed {
        map += (row.shoppingcartitemid -> row)
        acc + 1
      }
    }.runLast.map(_.getOrElse(0L))
  }
}

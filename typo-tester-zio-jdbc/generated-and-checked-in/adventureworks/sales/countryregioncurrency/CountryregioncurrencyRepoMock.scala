/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package countryregioncurrency

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

class CountryregioncurrencyRepoMock(toRow: Function1[CountryregioncurrencyRowUnsaved, CountryregioncurrencyRow],
                                    map: scala.collection.mutable.Map[CountryregioncurrencyId, CountryregioncurrencyRow] = scala.collection.mutable.Map.empty) extends CountryregioncurrencyRepo {
  override def delete: DeleteBuilder[CountryregioncurrencyFields, CountryregioncurrencyRow] = {
    DeleteBuilderMock(DeleteParams.empty, CountryregioncurrencyFields.structure, map)
  }
  override def deleteById(compositeId: CountryregioncurrencyId): ZIO[ZConnection, Throwable, Boolean] = {
    ZIO.succeed(map.remove(compositeId).isDefined)
  }
  override def deleteByIds(compositeIds: Array[CountryregioncurrencyId]): ZIO[ZConnection, Throwable, Long] = {
    ZIO.succeed(compositeIds.map(id => map.remove(id)).count(_.isDefined).toLong)
  }
  override def insert(unsaved: CountryregioncurrencyRow): ZIO[ZConnection, Throwable, CountryregioncurrencyRow] = {
    ZIO.succeed {
      val _ =
        if (map.contains(unsaved.compositeId))
          sys.error(s"id ${unsaved.compositeId} already exists")
        else
          map.put(unsaved.compositeId, unsaved)
    
      unsaved
    }
  }
  override def insert(unsaved: CountryregioncurrencyRowUnsaved): ZIO[ZConnection, Throwable, CountryregioncurrencyRow] = {
    insert(toRow(unsaved))
  }
  override def insertStreaming(unsaved: ZStream[ZConnection, Throwable, CountryregioncurrencyRow], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long] = {
    unsaved.scanZIO(0L) { case (acc, row) =>
      ZIO.succeed {
        map += (row.compositeId -> row)
        acc + 1
      }
    }.runLast.map(_.getOrElse(0L))
  }
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  override def insertUnsavedStreaming(unsaved: ZStream[ZConnection, Throwable, CountryregioncurrencyRowUnsaved], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long] = {
    unsaved.scanZIO(0L) { case (acc, unsavedRow) =>
      ZIO.succeed {
        val row = toRow(unsavedRow)
        map += (row.compositeId -> row)
        acc + 1
      }
    }.runLast.map(_.getOrElse(0L))
  }
  override def select: SelectBuilder[CountryregioncurrencyFields, CountryregioncurrencyRow] = {
    SelectBuilderMock(CountryregioncurrencyFields.structure, ZIO.succeed(Chunk.fromIterable(map.values)), SelectParams.empty)
  }
  override def selectAll: ZStream[ZConnection, Throwable, CountryregioncurrencyRow] = {
    ZStream.fromIterable(map.values)
  }
  override def selectById(compositeId: CountryregioncurrencyId): ZIO[ZConnection, Throwable, Option[CountryregioncurrencyRow]] = {
    ZIO.succeed(map.get(compositeId))
  }
  override def selectByIds(compositeIds: Array[CountryregioncurrencyId]): ZStream[ZConnection, Throwable, CountryregioncurrencyRow] = {
    ZStream.fromIterable(compositeIds.flatMap(map.get))
  }
  override def selectByIdsTracked(compositeIds: Array[CountryregioncurrencyId]): ZIO[ZConnection, Throwable, Map[CountryregioncurrencyId, CountryregioncurrencyRow]] = {
    selectByIds(compositeIds).runCollect.map { rows =>
      val byId = rows.view.map(x => (x.compositeId, x)).toMap
      compositeIds.view.flatMap(id => byId.get(id).map(x => (id, x))).toMap
    }
  }
  override def update: UpdateBuilder[CountryregioncurrencyFields, CountryregioncurrencyRow] = {
    UpdateBuilderMock(UpdateParams.empty, CountryregioncurrencyFields.structure, map)
  }
  override def update(row: CountryregioncurrencyRow): ZIO[ZConnection, Throwable, Option[CountryregioncurrencyRow]] = {
    ZIO.succeed {
      map.get(row.compositeId).map { _ =>
        map.put(row.compositeId, row): @nowarn
        row
      }
    }
  }
  override def upsert(unsaved: CountryregioncurrencyRow): ZIO[ZConnection, Throwable, UpdateResult[CountryregioncurrencyRow]] = {
    ZIO.succeed {
      map.put(unsaved.compositeId, unsaved): @nowarn
      UpdateResult(1, Chunk.single(unsaved))
    }
  }
  /* NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements */
  override def upsertStreaming(unsaved: ZStream[ZConnection, Throwable, CountryregioncurrencyRow], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long] = {
    unsaved.scanZIO(0L) { case (acc, row) =>
      ZIO.succeed {
        map += (row.compositeId -> row)
        acc + 1
      }
    }.runLast.map(_.getOrElse(0L))
  }
}

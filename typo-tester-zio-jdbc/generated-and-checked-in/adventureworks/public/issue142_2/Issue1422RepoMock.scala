/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package public
package issue142_2

import adventureworks.public.issue142.Issue142Id
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

class Issue1422RepoMock(map: scala.collection.mutable.Map[Issue142Id, Issue1422Row] = scala.collection.mutable.Map.empty) extends Issue1422Repo {
  override def delete: DeleteBuilder[Issue1422Fields, Issue1422Row] = {
    DeleteBuilderMock(DeleteParams.empty, Issue1422Fields.structure, map)
  }
  override def deleteById(tabellkode: Issue142Id): ZIO[ZConnection, Throwable, Boolean] = {
    ZIO.succeed(map.remove(tabellkode).isDefined)
  }
  override def deleteByIds(tabellkodes: Array[Issue142Id]): ZIO[ZConnection, Throwable, Long] = {
    ZIO.succeed(tabellkodes.map(id => map.remove(id)).count(_.isDefined).toLong)
  }
  override def insert(unsaved: Issue1422Row): ZIO[ZConnection, Throwable, Issue1422Row] = {
    ZIO.succeed {
      val _ =
        if (map.contains(unsaved.tabellkode))
          sys.error(s"id ${unsaved.tabellkode} already exists")
        else
          map.put(unsaved.tabellkode, unsaved)
    
      unsaved
    }
  }
  override def insertStreaming(unsaved: ZStream[ZConnection, Throwable, Issue1422Row], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long] = {
    unsaved.scanZIO(0L) { case (acc, row) =>
      ZIO.succeed {
        map += (row.tabellkode -> row)
        acc + 1
      }
    }.runLast.map(_.getOrElse(0L))
  }
  override def select: SelectBuilder[Issue1422Fields, Issue1422Row] = {
    SelectBuilderMock(Issue1422Fields.structure, ZIO.succeed(Chunk.fromIterable(map.values)), SelectParams.empty)
  }
  override def selectAll: ZStream[ZConnection, Throwable, Issue1422Row] = {
    ZStream.fromIterable(map.values)
  }
  override def selectById(tabellkode: Issue142Id): ZIO[ZConnection, Throwable, Option[Issue1422Row]] = {
    ZIO.succeed(map.get(tabellkode))
  }
  override def selectByIds(tabellkodes: Array[Issue142Id]): ZStream[ZConnection, Throwable, Issue1422Row] = {
    ZStream.fromIterable(tabellkodes.flatMap(map.get))
  }
  override def selectByIdsTracked(tabellkodes: Array[Issue142Id]): ZIO[ZConnection, Throwable, Map[Issue142Id, Issue1422Row]] = {
    selectByIds(tabellkodes).runCollect.map { rows =>
      val byId = rows.view.map(x => (x.tabellkode, x)).toMap
      tabellkodes.view.flatMap(id => byId.get(id).map(x => (id, x))).toMap
    }
  }
  override def update: UpdateBuilder[Issue1422Fields, Issue1422Row] = {
    UpdateBuilderMock(UpdateParams.empty, Issue1422Fields.structure, map)
  }
  override def upsert(unsaved: Issue1422Row): ZIO[ZConnection, Throwable, UpdateResult[Issue1422Row]] = {
    ZIO.succeed {
      map.put(unsaved.tabellkode, unsaved): @nowarn
      UpdateResult(1, Chunk.single(unsaved))
    }
  }
  /* NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements */
  override def upsertStreaming(unsaved: ZStream[ZConnection, Throwable, Issue1422Row], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long] = {
    unsaved.scanZIO(0L) { case (acc, row) =>
      ZIO.succeed {
        map += (row.tabellkode -> row)
        acc + 1
      }
    }.runLast.map(_.getOrElse(0L))
  }
}
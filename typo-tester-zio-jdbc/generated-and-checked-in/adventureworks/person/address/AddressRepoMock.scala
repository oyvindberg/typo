/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package address

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

class AddressRepoMock(toRow: Function1[AddressRowUnsaved, AddressRow],
                      map: scala.collection.mutable.Map[AddressId, AddressRow] = scala.collection.mutable.Map.empty) extends AddressRepo {
  override def delete(addressid: AddressId): ZIO[ZConnection, Throwable, Boolean] = {
    ZIO.succeed(map.remove(addressid).isDefined)
  }
  override def delete: DeleteBuilder[AddressFields, AddressRow] = {
    DeleteBuilderMock(DeleteParams.empty, AddressFields.structure.fields, map)
  }
  override def insert(unsaved: AddressRow): ZIO[ZConnection, Throwable, AddressRow] = {
    ZIO.succeed {
      val _ =
        if (map.contains(unsaved.addressid))
          sys.error(s"id ${unsaved.addressid} already exists")
        else
          map.put(unsaved.addressid, unsaved)
    
      unsaved
    }
  }
  override def insertStreaming(unsaved: ZStream[ZConnection, Throwable, AddressRow], batchSize: Int): ZIO[ZConnection, Throwable, Long] = {
    unsaved.scanZIO(0L) { case (acc, row) =>
      ZIO.succeed {
        map += (row.addressid -> row)
        acc + 1
      }
    }.runLast.map(_.getOrElse(0L))
  }
  override def insert(unsaved: AddressRowUnsaved): ZIO[ZConnection, Throwable, AddressRow] = {
    insert(toRow(unsaved))
  }
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  override def insertUnsavedStreaming(unsaved: ZStream[ZConnection, Throwable, AddressRowUnsaved], batchSize: Int): ZIO[ZConnection, Throwable, Long] = {
    unsaved.scanZIO(0L) { case (acc, unsavedRow) =>
      ZIO.succeed {
        val row = toRow(unsavedRow)
        map += (row.addressid -> row)
        acc + 1
      }
    }.runLast.map(_.getOrElse(0L))
  }
  override def select: SelectBuilder[AddressFields, AddressRow] = {
    SelectBuilderMock(AddressFields.structure, ZIO.succeed(Chunk.fromIterable(map.values)), SelectParams.empty)
  }
  override def selectAll: ZStream[ZConnection, Throwable, AddressRow] = {
    ZStream.fromIterable(map.values)
  }
  override def selectById(addressid: AddressId): ZIO[ZConnection, Throwable, Option[AddressRow]] = {
    ZIO.succeed(map.get(addressid))
  }
  override def selectByIds(addressids: Array[AddressId]): ZStream[ZConnection, Throwable, AddressRow] = {
    ZStream.fromIterable(addressids.flatMap(map.get))
  }
  override def update(row: AddressRow): ZIO[ZConnection, Throwable, Boolean] = {
    ZIO.succeed {
      map.get(row.addressid) match {
        case Some(`row`) => false
        case Some(_) =>
          map.put(row.addressid, row): @nowarn
          true
        case None => false
      }
    }
  }
  override def update: UpdateBuilder[AddressFields, AddressRow] = {
    UpdateBuilderMock(UpdateParams.empty, AddressFields.structure.fields, map)
  }
  override def upsert(unsaved: AddressRow): ZIO[ZConnection, Throwable, UpdateResult[AddressRow]] = {
    ZIO.succeed {
      map.put(unsaved.addressid, unsaved): @nowarn
      UpdateResult(1, Chunk.single(unsaved))
    }
  }
}

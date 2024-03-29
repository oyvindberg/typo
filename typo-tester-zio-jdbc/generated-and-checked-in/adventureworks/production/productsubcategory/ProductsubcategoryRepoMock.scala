/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package productsubcategory

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

class ProductsubcategoryRepoMock(toRow: Function1[ProductsubcategoryRowUnsaved, ProductsubcategoryRow],
                                 map: scala.collection.mutable.Map[ProductsubcategoryId, ProductsubcategoryRow] = scala.collection.mutable.Map.empty) extends ProductsubcategoryRepo {
  override def delete(productsubcategoryid: ProductsubcategoryId): ZIO[ZConnection, Throwable, Boolean] = {
    ZIO.succeed(map.remove(productsubcategoryid).isDefined)
  }
  override def delete: DeleteBuilder[ProductsubcategoryFields, ProductsubcategoryRow] = {
    DeleteBuilderMock(DeleteParams.empty, ProductsubcategoryFields.structure.fields, map)
  }
  override def insert(unsaved: ProductsubcategoryRow): ZIO[ZConnection, Throwable, ProductsubcategoryRow] = {
    ZIO.succeed {
      val _ =
        if (map.contains(unsaved.productsubcategoryid))
          sys.error(s"id ${unsaved.productsubcategoryid} already exists")
        else
          map.put(unsaved.productsubcategoryid, unsaved)
    
      unsaved
    }
  }
  override def insertStreaming(unsaved: ZStream[ZConnection, Throwable, ProductsubcategoryRow], batchSize: Int): ZIO[ZConnection, Throwable, Long] = {
    unsaved.scanZIO(0L) { case (acc, row) =>
      ZIO.succeed {
        map += (row.productsubcategoryid -> row)
        acc + 1
      }
    }.runLast.map(_.getOrElse(0L))
  }
  override def insert(unsaved: ProductsubcategoryRowUnsaved): ZIO[ZConnection, Throwable, ProductsubcategoryRow] = {
    insert(toRow(unsaved))
  }
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  override def insertUnsavedStreaming(unsaved: ZStream[ZConnection, Throwable, ProductsubcategoryRowUnsaved], batchSize: Int): ZIO[ZConnection, Throwable, Long] = {
    unsaved.scanZIO(0L) { case (acc, unsavedRow) =>
      ZIO.succeed {
        val row = toRow(unsavedRow)
        map += (row.productsubcategoryid -> row)
        acc + 1
      }
    }.runLast.map(_.getOrElse(0L))
  }
  override def select: SelectBuilder[ProductsubcategoryFields, ProductsubcategoryRow] = {
    SelectBuilderMock(ProductsubcategoryFields.structure, ZIO.succeed(Chunk.fromIterable(map.values)), SelectParams.empty)
  }
  override def selectAll: ZStream[ZConnection, Throwable, ProductsubcategoryRow] = {
    ZStream.fromIterable(map.values)
  }
  override def selectById(productsubcategoryid: ProductsubcategoryId): ZIO[ZConnection, Throwable, Option[ProductsubcategoryRow]] = {
    ZIO.succeed(map.get(productsubcategoryid))
  }
  override def selectByIds(productsubcategoryids: Array[ProductsubcategoryId]): ZStream[ZConnection, Throwable, ProductsubcategoryRow] = {
    ZStream.fromIterable(productsubcategoryids.flatMap(map.get))
  }
  override def update(row: ProductsubcategoryRow): ZIO[ZConnection, Throwable, Boolean] = {
    ZIO.succeed {
      map.get(row.productsubcategoryid) match {
        case Some(`row`) => false
        case Some(_) =>
          map.put(row.productsubcategoryid, row): @nowarn
          true
        case None => false
      }
    }
  }
  override def update: UpdateBuilder[ProductsubcategoryFields, ProductsubcategoryRow] = {
    UpdateBuilderMock(UpdateParams.empty, ProductsubcategoryFields.structure.fields, map)
  }
  override def upsert(unsaved: ProductsubcategoryRow): ZIO[ZConnection, Throwable, UpdateResult[ProductsubcategoryRow]] = {
    ZIO.succeed {
      map.put(unsaved.productsubcategoryid, unsaved): @nowarn
      UpdateResult(1, Chunk.single(unsaved))
    }
  }
}

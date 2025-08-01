/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package productcategory

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

class ProductcategoryRepoMock(toRow: Function1[ProductcategoryRowUnsaved, ProductcategoryRow],
                              map: scala.collection.mutable.Map[ProductcategoryId, ProductcategoryRow] = scala.collection.mutable.Map.empty) extends ProductcategoryRepo {
  override def delete: DeleteBuilder[ProductcategoryFields, ProductcategoryRow] = {
    DeleteBuilderMock(DeleteParams.empty, ProductcategoryFields.structure, map)
  }
  override def deleteById(productcategoryid: ProductcategoryId): ZIO[ZConnection, Throwable, Boolean] = {
    ZIO.succeed(map.remove(productcategoryid).isDefined)
  }
  override def deleteByIds(productcategoryids: Array[ProductcategoryId]): ZIO[ZConnection, Throwable, Long] = {
    ZIO.succeed(productcategoryids.map(id => map.remove(id)).count(_.isDefined).toLong)
  }
  override def insert(unsaved: ProductcategoryRow): ZIO[ZConnection, Throwable, ProductcategoryRow] = {
    ZIO.succeed {
      val _ =
        if (map.contains(unsaved.productcategoryid))
          sys.error(s"id ${unsaved.productcategoryid} already exists")
        else
          map.put(unsaved.productcategoryid, unsaved)
    
      unsaved
    }
  }
  override def insert(unsaved: ProductcategoryRowUnsaved): ZIO[ZConnection, Throwable, ProductcategoryRow] = {
    insert(toRow(unsaved))
  }
  override def insertStreaming(unsaved: ZStream[ZConnection, Throwable, ProductcategoryRow], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long] = {
    unsaved.scanZIO(0L) { case (acc, row) =>
      ZIO.succeed {
        map += (row.productcategoryid -> row)
        acc + 1
      }
    }.runLast.map(_.getOrElse(0L))
  }
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  override def insertUnsavedStreaming(unsaved: ZStream[ZConnection, Throwable, ProductcategoryRowUnsaved], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long] = {
    unsaved.scanZIO(0L) { case (acc, unsavedRow) =>
      ZIO.succeed {
        val row = toRow(unsavedRow)
        map += (row.productcategoryid -> row)
        acc + 1
      }
    }.runLast.map(_.getOrElse(0L))
  }
  override def select: SelectBuilder[ProductcategoryFields, ProductcategoryRow] = {
    SelectBuilderMock(ProductcategoryFields.structure, ZIO.succeed(Chunk.fromIterable(map.values)), SelectParams.empty)
  }
  override def selectAll: ZStream[ZConnection, Throwable, ProductcategoryRow] = {
    ZStream.fromIterable(map.values)
  }
  override def selectById(productcategoryid: ProductcategoryId): ZIO[ZConnection, Throwable, Option[ProductcategoryRow]] = {
    ZIO.succeed(map.get(productcategoryid))
  }
  override def selectByIds(productcategoryids: Array[ProductcategoryId]): ZStream[ZConnection, Throwable, ProductcategoryRow] = {
    ZStream.fromIterable(productcategoryids.flatMap(map.get))
  }
  override def selectByIdsTracked(productcategoryids: Array[ProductcategoryId]): ZIO[ZConnection, Throwable, Map[ProductcategoryId, ProductcategoryRow]] = {
    selectByIds(productcategoryids).runCollect.map { rows =>
      val byId = rows.view.map(x => (x.productcategoryid, x)).toMap
      productcategoryids.view.flatMap(id => byId.get(id).map(x => (id, x))).toMap
    }
  }
  override def update: UpdateBuilder[ProductcategoryFields, ProductcategoryRow] = {
    UpdateBuilderMock(UpdateParams.empty, ProductcategoryFields.structure, map)
  }
  override def update(row: ProductcategoryRow): ZIO[ZConnection, Throwable, Option[ProductcategoryRow]] = {
    ZIO.succeed {
      map.get(row.productcategoryid).map { _ =>
        map.put(row.productcategoryid, row): @nowarn
        row
      }
    }
  }
  override def upsert(unsaved: ProductcategoryRow): ZIO[ZConnection, Throwable, UpdateResult[ProductcategoryRow]] = {
    ZIO.succeed {
      map.put(unsaved.productcategoryid, unsaved): @nowarn
      UpdateResult(1, Chunk.single(unsaved))
    }
  }
  /* NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements */
  override def upsertStreaming(unsaved: ZStream[ZConnection, Throwable, ProductcategoryRow], batchSize: Int = 10000): ZIO[ZConnection, Throwable, Long] = {
    unsaved.scanZIO(0L) { case (acc, row) =>
      ZIO.succeed {
        map += (row.productcategoryid -> row)
        acc + 1
      }
    }.runLast.map(_.getOrElse(0L))
  }
}

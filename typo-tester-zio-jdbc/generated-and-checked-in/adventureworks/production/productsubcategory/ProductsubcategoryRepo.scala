/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package productsubcategory

import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.UpdateBuilder
import zio.ZIO
import zio.jdbc.UpdateResult
import zio.jdbc.ZConnection
import zio.stream.ZStream

trait ProductsubcategoryRepo {
  def delete(productsubcategoryid: ProductsubcategoryId): ZIO[ZConnection, Throwable, Boolean]
  def delete: DeleteBuilder[ProductsubcategoryFields, ProductsubcategoryRow]
  def insert(unsaved: ProductsubcategoryRow): ZIO[ZConnection, Throwable, ProductsubcategoryRow]
  def insertStreaming(unsaved: ZStream[ZConnection, Throwable, ProductsubcategoryRow], batchSize: Int): ZIO[ZConnection, Throwable, Long]
  def insert(unsaved: ProductsubcategoryRowUnsaved): ZIO[ZConnection, Throwable, ProductsubcategoryRow]
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  def insertUnsavedStreaming(unsaved: ZStream[ZConnection, Throwable, ProductsubcategoryRowUnsaved], batchSize: Int): ZIO[ZConnection, Throwable, Long]
  def select: SelectBuilder[ProductsubcategoryFields, ProductsubcategoryRow]
  def selectAll: ZStream[ZConnection, Throwable, ProductsubcategoryRow]
  def selectById(productsubcategoryid: ProductsubcategoryId): ZIO[ZConnection, Throwable, Option[ProductsubcategoryRow]]
  def selectByIds(productsubcategoryids: Array[ProductsubcategoryId]): ZStream[ZConnection, Throwable, ProductsubcategoryRow]
  def update(row: ProductsubcategoryRow): ZIO[ZConnection, Throwable, Boolean]
  def update: UpdateBuilder[ProductsubcategoryFields, ProductsubcategoryRow]
  def upsert(unsaved: ProductsubcategoryRow): ZIO[ZConnection, Throwable, UpdateResult[ProductsubcategoryRow]]
}

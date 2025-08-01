/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package productcategory

import doobie.free.connection.ConnectionIO
import fs2.Stream
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.UpdateBuilder

trait ProductcategoryRepo {
  def delete: DeleteBuilder[ProductcategoryFields, ProductcategoryRow]
  def deleteById(productcategoryid: ProductcategoryId): ConnectionIO[Boolean]
  def deleteByIds(productcategoryids: Array[ProductcategoryId]): ConnectionIO[Int]
  def insert(unsaved: ProductcategoryRow): ConnectionIO[ProductcategoryRow]
  def insert(unsaved: ProductcategoryRowUnsaved): ConnectionIO[ProductcategoryRow]
  def insertStreaming(unsaved: Stream[ConnectionIO, ProductcategoryRow], batchSize: Int = 10000): ConnectionIO[Long]
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  def insertUnsavedStreaming(unsaved: Stream[ConnectionIO, ProductcategoryRowUnsaved], batchSize: Int = 10000): ConnectionIO[Long]
  def select: SelectBuilder[ProductcategoryFields, ProductcategoryRow]
  def selectAll: Stream[ConnectionIO, ProductcategoryRow]
  def selectById(productcategoryid: ProductcategoryId): ConnectionIO[Option[ProductcategoryRow]]
  def selectByIds(productcategoryids: Array[ProductcategoryId]): Stream[ConnectionIO, ProductcategoryRow]
  def selectByIdsTracked(productcategoryids: Array[ProductcategoryId]): ConnectionIO[Map[ProductcategoryId, ProductcategoryRow]]
  def update: UpdateBuilder[ProductcategoryFields, ProductcategoryRow]
  def update(row: ProductcategoryRow): ConnectionIO[Option[ProductcategoryRow]]
  def upsert(unsaved: ProductcategoryRow): ConnectionIO[ProductcategoryRow]
  def upsertBatch(unsaved: List[ProductcategoryRow]): Stream[ConnectionIO, ProductcategoryRow]
  /* NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements */
  def upsertStreaming(unsaved: Stream[ConnectionIO, ProductcategoryRow], batchSize: Int = 10000): ConnectionIO[Int]
}

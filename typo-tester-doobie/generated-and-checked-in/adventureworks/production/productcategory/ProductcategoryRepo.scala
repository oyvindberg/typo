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
  def delete(productcategoryid: ProductcategoryId): ConnectionIO[Boolean]
  def delete: DeleteBuilder[ProductcategoryFields, ProductcategoryRow]
  def insert(unsaved: ProductcategoryRow): ConnectionIO[ProductcategoryRow]
  def insertStreaming(unsaved: Stream[ConnectionIO, ProductcategoryRow], batchSize: Int): ConnectionIO[Long]
  def insert(unsaved: ProductcategoryRowUnsaved): ConnectionIO[ProductcategoryRow]
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  def insertUnsavedStreaming(unsaved: Stream[ConnectionIO, ProductcategoryRowUnsaved], batchSize: Int): ConnectionIO[Long]
  def select: SelectBuilder[ProductcategoryFields, ProductcategoryRow]
  def selectAll: Stream[ConnectionIO, ProductcategoryRow]
  def selectById(productcategoryid: ProductcategoryId): ConnectionIO[Option[ProductcategoryRow]]
  def selectByIds(productcategoryids: Array[ProductcategoryId]): Stream[ConnectionIO, ProductcategoryRow]
  def update(row: ProductcategoryRow): ConnectionIO[Boolean]
  def update: UpdateBuilder[ProductcategoryFields, ProductcategoryRow]
  def upsert(unsaved: ProductcategoryRow): ConnectionIO[ProductcategoryRow]
}

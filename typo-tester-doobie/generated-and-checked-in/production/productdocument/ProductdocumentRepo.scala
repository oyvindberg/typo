/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package productdocument

import doobie.free.connection.ConnectionIO
import fs2.Stream
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.UpdateBuilder

trait ProductdocumentRepo {
  def delete(compositeId: ProductdocumentId): ConnectionIO[Boolean]
  def delete: DeleteBuilder[ProductdocumentFields, ProductdocumentRow]
  def insert(unsaved: ProductdocumentRow): ConnectionIO[ProductdocumentRow]
  def insert(unsaved: ProductdocumentRowUnsaved): ConnectionIO[ProductdocumentRow]
  def select: SelectBuilder[ProductdocumentFields, ProductdocumentRow]
  def selectAll: Stream[ConnectionIO, ProductdocumentRow]
  def selectById(compositeId: ProductdocumentId): ConnectionIO[Option[ProductdocumentRow]]
  def update(row: ProductdocumentRow): ConnectionIO[Boolean]
  def update: UpdateBuilder[ProductdocumentFields, ProductdocumentRow]
  def upsert(unsaved: ProductdocumentRow): ConnectionIO[ProductdocumentRow]
}
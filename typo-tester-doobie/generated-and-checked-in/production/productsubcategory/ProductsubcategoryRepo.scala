/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package productsubcategory

import doobie.free.connection.ConnectionIO
import fs2.Stream

trait ProductsubcategoryRepo {
  def delete(productsubcategoryid: ProductsubcategoryId): ConnectionIO[Boolean]
  def insert(unsaved: ProductsubcategoryRow): ConnectionIO[ProductsubcategoryRow]
  def insert(unsaved: ProductsubcategoryRowUnsaved): ConnectionIO[ProductsubcategoryRow]
  def selectAll: Stream[ConnectionIO, ProductsubcategoryRow]
  def selectByFieldValues(fieldValues: List[ProductsubcategoryFieldOrIdValue[_]]): Stream[ConnectionIO, ProductsubcategoryRow]
  def selectById(productsubcategoryid: ProductsubcategoryId): ConnectionIO[Option[ProductsubcategoryRow]]
  def selectByIds(productsubcategoryids: Array[ProductsubcategoryId]): Stream[ConnectionIO, ProductsubcategoryRow]
  def update(row: ProductsubcategoryRow): ConnectionIO[Boolean]
  def updateFieldValues(productsubcategoryid: ProductsubcategoryId, fieldValues: List[ProductsubcategoryFieldValue[_]]): ConnectionIO[Boolean]
  def upsert(unsaved: ProductsubcategoryRow): ConnectionIO[ProductsubcategoryRow]
}
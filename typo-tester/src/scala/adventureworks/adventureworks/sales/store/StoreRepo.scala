/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package store

import java.sql.Connection

trait StoreRepo {
  def delete(businessentityid: StoreId)(implicit c: Connection): Boolean
  def insert(businessentityid: StoreId, unsaved: StoreRowUnsaved)(implicit c: Connection): Boolean
  def selectAll(implicit c: Connection): List[StoreRow]
  def selectByFieldValues(fieldValues: List[StoreFieldOrIdValue[_]])(implicit c: Connection): List[StoreRow]
  def selectById(businessentityid: StoreId)(implicit c: Connection): Option[StoreRow]
  def selectByIds(businessentityids: List[StoreId])(implicit c: Connection): List[StoreRow]
  def update(businessentityid: StoreId, row: StoreRow)(implicit c: Connection): Boolean
  def updateFieldValues(businessentityid: StoreId, fieldValues: List[StoreFieldValue[_]])(implicit c: Connection): Boolean
}
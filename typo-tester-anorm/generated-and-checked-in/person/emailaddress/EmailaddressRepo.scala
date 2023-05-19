/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package emailaddress

import java.sql.Connection

trait EmailaddressRepo {
  def delete(compositeId: EmailaddressId)(implicit c: Connection): Boolean
  def insert(unsaved: EmailaddressRow)(implicit c: Connection): EmailaddressRow
  def insert(unsaved: EmailaddressRowUnsaved)(implicit c: Connection): EmailaddressRow
  def selectAll(implicit c: Connection): List[EmailaddressRow]
  def selectByFieldValues(fieldValues: List[EmailaddressFieldOrIdValue[_]])(implicit c: Connection): List[EmailaddressRow]
  def selectById(compositeId: EmailaddressId)(implicit c: Connection): Option[EmailaddressRow]
  def update(row: EmailaddressRow)(implicit c: Connection): Boolean
  def updateFieldValues(compositeId: EmailaddressId, fieldValues: List[EmailaddressFieldValue[_]])(implicit c: Connection): Boolean
  def upsert(unsaved: EmailaddressRow)(implicit c: Connection): EmailaddressRow
}
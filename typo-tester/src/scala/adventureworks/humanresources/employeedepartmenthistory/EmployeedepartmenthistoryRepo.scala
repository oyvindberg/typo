/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package humanresources
package employeedepartmenthistory

import java.sql.Connection

trait EmployeedepartmenthistoryRepo {
  def delete(compositeId: EmployeedepartmenthistoryId)(implicit c: Connection): Boolean
  def insert(compositeId: EmployeedepartmenthistoryId, unsaved: EmployeedepartmenthistoryRowUnsaved)(implicit c: Connection): Boolean
  def selectAll(implicit c: Connection): List[EmployeedepartmenthistoryRow]
  def selectByFieldValues(fieldValues: List[EmployeedepartmenthistoryFieldOrIdValue[_]])(implicit c: Connection): List[EmployeedepartmenthistoryRow]
  def selectById(compositeId: EmployeedepartmenthistoryId)(implicit c: Connection): Option[EmployeedepartmenthistoryRow]
  def update(row: EmployeedepartmenthistoryRow)(implicit c: Connection): Boolean
  def updateFieldValues(compositeId: EmployeedepartmenthistoryId, fieldValues: List[EmployeedepartmenthistoryFieldValue[_]])(implicit c: Connection): Boolean
}
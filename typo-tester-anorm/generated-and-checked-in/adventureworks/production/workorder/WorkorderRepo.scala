/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package workorder

import java.sql.Connection
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.UpdateBuilder

trait WorkorderRepo {
  def delete(workorderid: WorkorderId)(implicit c: Connection): Boolean
  def delete: DeleteBuilder[WorkorderFields, WorkorderRow]
  def insert(unsaved: WorkorderRow)(implicit c: Connection): WorkorderRow
  def insertStreaming(unsaved: Iterator[WorkorderRow], batchSize: Int)(implicit c: Connection): Long
  def insert(unsaved: WorkorderRowUnsaved)(implicit c: Connection): WorkorderRow
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  def insertUnsavedStreaming(unsaved: Iterator[WorkorderRowUnsaved], batchSize: Int)(implicit c: Connection): Long
  def select: SelectBuilder[WorkorderFields, WorkorderRow]
  def selectAll(implicit c: Connection): List[WorkorderRow]
  def selectById(workorderid: WorkorderId)(implicit c: Connection): Option[WorkorderRow]
  def selectByIds(workorderids: Array[WorkorderId])(implicit c: Connection): List[WorkorderRow]
  def update(row: WorkorderRow)(implicit c: Connection): Boolean
  def update: UpdateBuilder[WorkorderFields, WorkorderRow]
  def upsert(unsaved: WorkorderRow)(implicit c: Connection): WorkorderRow
}
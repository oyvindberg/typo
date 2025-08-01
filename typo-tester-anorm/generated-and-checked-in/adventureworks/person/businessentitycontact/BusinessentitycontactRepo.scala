/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package businessentitycontact

import java.sql.Connection
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.UpdateBuilder

trait BusinessentitycontactRepo {
  def delete: DeleteBuilder[BusinessentitycontactFields, BusinessentitycontactRow]
  def deleteById(compositeId: BusinessentitycontactId)(implicit c: Connection): Boolean
  def deleteByIds(compositeIds: Array[BusinessentitycontactId])(implicit c: Connection): Int
  def insert(unsaved: BusinessentitycontactRow)(implicit c: Connection): BusinessentitycontactRow
  def insert(unsaved: BusinessentitycontactRowUnsaved)(implicit c: Connection): BusinessentitycontactRow
  def insertStreaming(unsaved: Iterator[BusinessentitycontactRow], batchSize: Int = 10000)(implicit c: Connection): Long
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  def insertUnsavedStreaming(unsaved: Iterator[BusinessentitycontactRowUnsaved], batchSize: Int = 10000)(implicit c: Connection): Long
  def select: SelectBuilder[BusinessentitycontactFields, BusinessentitycontactRow]
  def selectAll(implicit c: Connection): List[BusinessentitycontactRow]
  def selectById(compositeId: BusinessentitycontactId)(implicit c: Connection): Option[BusinessentitycontactRow]
  def selectByIds(compositeIds: Array[BusinessentitycontactId])(implicit c: Connection): List[BusinessentitycontactRow]
  def selectByIdsTracked(compositeIds: Array[BusinessentitycontactId])(implicit c: Connection): Map[BusinessentitycontactId, BusinessentitycontactRow]
  def update: UpdateBuilder[BusinessentitycontactFields, BusinessentitycontactRow]
  def update(row: BusinessentitycontactRow)(implicit c: Connection): Option[BusinessentitycontactRow]
  def upsert(unsaved: BusinessentitycontactRow)(implicit c: Connection): BusinessentitycontactRow
  def upsertBatch(unsaved: Iterable[BusinessentitycontactRow])(implicit c: Connection): List[BusinessentitycontactRow]
  /* NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements */
  def upsertStreaming(unsaved: Iterator[BusinessentitycontactRow], batchSize: Int = 10000)(implicit c: Connection): Int
}

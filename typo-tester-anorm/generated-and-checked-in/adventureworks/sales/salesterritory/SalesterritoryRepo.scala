/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package salesterritory

import java.sql.Connection
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.UpdateBuilder

trait SalesterritoryRepo {
  def delete(territoryid: SalesterritoryId)(implicit c: Connection): Boolean
  def delete: DeleteBuilder[SalesterritoryFields, SalesterritoryRow]
  def insert(unsaved: SalesterritoryRow)(implicit c: Connection): SalesterritoryRow
  def insertStreaming(unsaved: Iterator[SalesterritoryRow], batchSize: Int)(implicit c: Connection): Long
  def insert(unsaved: SalesterritoryRowUnsaved)(implicit c: Connection): SalesterritoryRow
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  def insertUnsavedStreaming(unsaved: Iterator[SalesterritoryRowUnsaved], batchSize: Int)(implicit c: Connection): Long
  def select: SelectBuilder[SalesterritoryFields, SalesterritoryRow]
  def selectAll(implicit c: Connection): List[SalesterritoryRow]
  def selectById(territoryid: SalesterritoryId)(implicit c: Connection): Option[SalesterritoryRow]
  def selectByIds(territoryids: Array[SalesterritoryId])(implicit c: Connection): List[SalesterritoryRow]
  def update(row: SalesterritoryRow)(implicit c: Connection): Boolean
  def update: UpdateBuilder[SalesterritoryFields, SalesterritoryRow]
  def upsert(unsaved: SalesterritoryRow)(implicit c: Connection): SalesterritoryRow
}
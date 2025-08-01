/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package billofmaterials

import java.sql.Connection
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.UpdateBuilder

trait BillofmaterialsRepo {
  def delete: DeleteBuilder[BillofmaterialsFields, BillofmaterialsRow]
  def deleteById(billofmaterialsid: Int)(implicit c: Connection): Boolean
  def deleteByIds(billofmaterialsids: Array[Int])(implicit c: Connection): Int
  def insert(unsaved: BillofmaterialsRow)(implicit c: Connection): BillofmaterialsRow
  def insert(unsaved: BillofmaterialsRowUnsaved)(implicit c: Connection): BillofmaterialsRow
  def insertStreaming(unsaved: Iterator[BillofmaterialsRow], batchSize: Int = 10000)(implicit c: Connection): Long
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  def insertUnsavedStreaming(unsaved: Iterator[BillofmaterialsRowUnsaved], batchSize: Int = 10000)(implicit c: Connection): Long
  def select: SelectBuilder[BillofmaterialsFields, BillofmaterialsRow]
  def selectAll(implicit c: Connection): List[BillofmaterialsRow]
  def selectById(billofmaterialsid: Int)(implicit c: Connection): Option[BillofmaterialsRow]
  def selectByIds(billofmaterialsids: Array[Int])(implicit c: Connection): List[BillofmaterialsRow]
  def selectByIdsTracked(billofmaterialsids: Array[Int])(implicit c: Connection): Map[Int, BillofmaterialsRow]
  def update: UpdateBuilder[BillofmaterialsFields, BillofmaterialsRow]
  def update(row: BillofmaterialsRow)(implicit c: Connection): Option[BillofmaterialsRow]
  def upsert(unsaved: BillofmaterialsRow)(implicit c: Connection): BillofmaterialsRow
  def upsertBatch(unsaved: Iterable[BillofmaterialsRow])(implicit c: Connection): List[BillofmaterialsRow]
  /* NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements */
  def upsertStreaming(unsaved: Iterator[BillofmaterialsRow], batchSize: Int = 10000)(implicit c: Connection): Int
}

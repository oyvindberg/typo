/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_statistic_ext_data

import java.sql.Connection
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.UpdateBuilder

trait PgStatisticExtDataRepo {
  def delete(stxoid: PgStatisticExtDataId)(implicit c: Connection): Boolean
  def delete: DeleteBuilder[PgStatisticExtDataFields, PgStatisticExtDataRow]
  def insert(unsaved: PgStatisticExtDataRow)(implicit c: Connection): PgStatisticExtDataRow
  def select: SelectBuilder[PgStatisticExtDataFields, PgStatisticExtDataRow]
  def selectAll(implicit c: Connection): List[PgStatisticExtDataRow]
  def selectById(stxoid: PgStatisticExtDataId)(implicit c: Connection): Option[PgStatisticExtDataRow]
  def selectByIds(stxoids: Array[PgStatisticExtDataId])(implicit c: Connection): List[PgStatisticExtDataRow]
  def update(row: PgStatisticExtDataRow)(implicit c: Connection): Boolean
  def update: UpdateBuilder[PgStatisticExtDataFields, PgStatisticExtDataRow]
  def upsert(unsaved: PgStatisticExtDataRow)(implicit c: Connection): PgStatisticExtDataRow
}
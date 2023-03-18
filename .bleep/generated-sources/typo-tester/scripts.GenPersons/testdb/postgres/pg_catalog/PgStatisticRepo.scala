package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgStatisticRepo {
  def selectAll(implicit c: Connection): List[PgStatisticRow]
  def selectById(compositeId: PgStatisticId)(implicit c: Connection): Option[PgStatisticRow]
  def selectByFieldValues(fieldValues: List[PgStatisticFieldValue[_]])(implicit c: Connection): List[PgStatisticRow]
  def updateFieldValues(compositeId: PgStatisticId, fieldValues: List[PgStatisticFieldValue[_]])(implicit c: Connection): Int
  def insert(compositeId: PgStatisticId, unsaved: PgStatisticRowUnsaved)(implicit c: Connection): Unit
  def delete(compositeId: PgStatisticId)(implicit c: Connection): Boolean
}

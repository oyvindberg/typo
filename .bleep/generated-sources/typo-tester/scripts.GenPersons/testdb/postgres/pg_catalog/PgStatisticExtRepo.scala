package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgStatisticExtRepo {
  def selectAll(implicit c: Connection): List[PgStatisticExtRow]
  def selectById(oid: PgStatisticExtId)(implicit c: Connection): Option[PgStatisticExtRow]
  def selectByIds(oids: List[PgStatisticExtId])(implicit c: Connection): List[PgStatisticExtRow]
  def selectByFieldValues(fieldValues: List[PgStatisticExtFieldValue[_]])(implicit c: Connection): List[PgStatisticExtRow]
  def updateFieldValues(oid: PgStatisticExtId, fieldValues: List[PgStatisticExtFieldValue[_]])(implicit c: Connection): Int
  def insert(oid: PgStatisticExtId, unsaved: PgStatisticExtRowUnsaved)(implicit c: Connection): Unit
  def delete(oid: PgStatisticExtId)(implicit c: Connection): Boolean
  def selectByUnique(stxname: String, stxnamespace: Long)(implicit c: Connection): Option[PgStatisticExtRow]
}

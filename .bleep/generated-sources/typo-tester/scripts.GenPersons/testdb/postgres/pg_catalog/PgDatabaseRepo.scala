package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgDatabaseRepo {
  def selectAll(implicit c: Connection): List[PgDatabaseRow]
  def selectById(oid: PgDatabaseId)(implicit c: Connection): Option[PgDatabaseRow]
  def selectByIds(oids: List[PgDatabaseId])(implicit c: Connection): List[PgDatabaseRow]
  def selectByFieldValues(fieldValues: List[PgDatabaseFieldValue[_]])(implicit c: Connection): List[PgDatabaseRow]
  def updateFieldValues(oid: PgDatabaseId, fieldValues: List[PgDatabaseFieldValue[_]])(implicit c: Connection): Int
  def insert(oid: PgDatabaseId, unsaved: PgDatabaseRowUnsaved)(implicit c: Connection): Unit
  def delete(oid: PgDatabaseId)(implicit c: Connection): Boolean
  def selectByUnique(datname: String)(implicit c: Connection): Option[PgDatabaseRow]
}

package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgForeignServerRepo {
  def selectAll(implicit c: Connection): List[PgForeignServerRow]
  def selectById(oid: PgForeignServerId)(implicit c: Connection): Option[PgForeignServerRow]
  def selectByIds(oids: List[PgForeignServerId])(implicit c: Connection): List[PgForeignServerRow]
  def selectByFieldValues(fieldValues: List[PgForeignServerFieldValue[_]])(implicit c: Connection): List[PgForeignServerRow]
  def updateFieldValues(oid: PgForeignServerId, fieldValues: List[PgForeignServerFieldValue[_]])(implicit c: Connection): Int
  def insert(oid: PgForeignServerId, unsaved: PgForeignServerRowUnsaved)(implicit c: Connection): Unit
  def delete(oid: PgForeignServerId)(implicit c: Connection): Boolean
  def selectByUnique(srvname: String)(implicit c: Connection): Option[PgForeignServerRow]
}
